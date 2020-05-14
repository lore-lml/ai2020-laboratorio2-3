package it.ai.polito.lab2.service;

import it.ai.polito.lab2.dtos.ProfessorDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.security.entities.Roles;
import it.ai.polito.lab2.security.entities.User;
import it.ai.polito.lab2.security.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class ManagementServiceImpl implements ManagementService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamService teamService;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PasswordEncoder encoder;


    @PreAuthorize("hasAnyRole('ROLE_PROFESSOR', 'ROLE_ADMIN')")
    @Override
    public boolean createStudentUser(StudentDTO studentDTO) {
        if(!teamService.addStudent(studentDTO))
            return false;

        String username = String.format("%s@studenti.polito.it", studentDTO.getId());
        String userId = studentDTO.getId();
        String password = generateCommonLangPassword();

        User user = User.builder().id(userId).username(username).password(encoder.encode(password))
                .roles(Arrays.asList(Roles.ROLE_STUDENT.toString())).build();

        try {
            createUserAndSendMail(user, password);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public boolean createProfessorUser(ProfessorDTO professorDTO) {
        return true;
    }

    public String generateCommonLangPassword() {
        String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
        String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
        String numbers = RandomStringUtils.randomNumeric(2);
        String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
        String totalChars = RandomStringUtils.randomAlphanumeric(2);
        String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
                .concat(numbers)
                .concat(specialChar)
                .concat(totalChars);
        List<Character> pwdChars = combinedChars.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(pwdChars);
        String password = pwdChars.stream()
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
        return password;
    }

    private void createUserAndSendMail(User user, String password){
        userRepository.save(user);
        sendPassword(user.getUsername(), password);
    }

    private void sendPassword(String email, String password) {
        String subject = "[Applicazioni Internet] Teams";
        String body = String.format("Il tuo account è stato creato con successo!\n" +
                "Per accedere utilizza il tuo Id e la seguente password: " +
                "%s", password);
        notificationService.sendMessage(email, subject, body);
    }

}
