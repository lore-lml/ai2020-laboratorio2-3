package it.ai.polito.lab2.service;

import it.ai.polito.lab2.dtos.TeamDTO;
import it.ai.polito.lab2.entities.Team;
import it.ai.polito.lab2.entities.Token;
import it.ai.polito.lab2.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
@EnableAsync
public class NotificationServiceImpl implements NotificationService{

    public static final String from = "applicazioni.internet.test@gmail.com";
    public static final String baseURL = "http://localhost:8080";
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    private TeamService teamService;

    @Override
    @Async
    public void sendMessage(String address, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("applicazioni.internet.test@gmail.com");
        message.setSubject(subject);
        message.setText(body);
        emailSender.send(message);
    }

    @Override
    @Transactional
    public boolean confirm(String token) {
        try {
            Token t = tokenRepository.findById(token).get();
            Long teamId = t.getTeamId();
            //Se il token è scaduto non può confermare
            if(Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault())).compareTo(t.getExpiryDate()) >= 0) {
                deleteTeamWithTokens(teamId);
                return false;
            }

            tokenRepository.delete(t);
            if(tokenRepository.countTokenByTeamId(teamId) == 0)
                teamService.setTeamStatus(teamId, Team.Status.ACTIVE);
        }catch (NoSuchElementException e){
            return false;
        }

        return true;
    }

    private void deleteTeamWithTokens(Long teamId){
        tokenRepository.deleteAllByTeamId(teamId);
        teamService.evictTeam(teamId);
    }

    @Override
    @Transactional
    public boolean reject(String token) {
        try {
            Token t = tokenRepository.findById(token).get();
            Long teamId = t.getTeamId();
            deleteTeamWithTokens(teamId);
        }catch (NoSuchElementException e){
            return false;
        }

        return true;
    }

    @Override
    @Async
    public void notifyTeam(TeamDTO dto, List<String> memberIds) {
        Timestamp expiryDate = Timestamp.valueOf(LocalDateTime.now(ZoneId.systemDefault()).plusHours(1));

        for(String id : memberIds){
            Token token = createAndSaveToken(UUID.randomUUID().toString(), dto.getId(), expiryDate);
            String message = buildMessage(token.getId(), dto.getName(), id);
            String email = String.format("s%s@studenti.polito.it", id);
            sendMessage(email, "Gruppo Applicazioni Internet", message);
        }
}

    private Token createAndSaveToken(String tokenId, Long teamId, Timestamp expiryDate) {
        Token token = new Token(tokenId, teamId, expiryDate);
        tokenRepository.save(token);
        return token;
    }

    private String buildMessage(String tokenId, String teamName, String memberId){
        String confirm = String.format("%s/notification/confirm/%s", baseURL, tokenId);
        String reject = String.format("%s/notification/reject/%s", baseURL, tokenId);

        return String.format("Congratulazioni %s!\nSei stato invitato a far parte del team %s.\n" +
                "Se sei interessato per favore conferma la tua partecipazione altrimenti puoi anche rifiutare l'invito immediatamente:\n\n" +
                "Accetta:\t%s\n\n" +
                "Rifiuta:\t%s \n", memberId, teamName, confirm, reject);
    }
}
