package it.ai.polito.lab2.service;

import it.ai.polito.lab2.dtos.ProfessorDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.entities.Course;
import it.ai.polito.lab2.entities.Professor;
import it.ai.polito.lab2.security.entities.Roles;
import it.ai.polito.lab2.security.entities.User;
import it.ai.polito.lab2.security.repositories.UserRepository;
import it.ai.polito.lab2.security.service.SecurityApiAuth;
import it.ai.polito.lab2.service.exceptions.CourseAlreadyOwnedException;
import it.ai.polito.lab2.service.exceptions.ProfessorNotFoundException;
import org.apache.commons.lang3.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
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
        /*if(!teamService.addStudent(studentDTO))
            return false;*/

        String username = String.format("%s@studenti.polito.it", studentDTO.getId());
        String userId = studentDTO.getId();
        String password = generateCommonLangPassword();

        User user = User.builder().id(userId).username(username).password(encoder.encode(password))
                .roles(Arrays.asList(Roles.ROLE_STUDENT.toString())).build();

        try {
            createUserAndSendMail(user, password, studentDTO.getFirstName());
        }catch (Exception e){
            return false;
        }

        return true;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public boolean createProfessorUser(ProfessorDTO professorDTO) {
        /*if(!teamService.addProfessor(professorDTO))
            return false;*/

        String username = String.format("%s@polito.it", professorDTO.getId());
        String userId = professorDTO.getId();
        String password = generateCommonLangPassword();

        User user = User.builder().id(userId).username(username).password(encoder.encode(password))
                .roles(Arrays.asList(Roles.ROLE_PROFESSOR.toString())).build();

        try {
            createUserAndSendMail(user, password, professorDTO.getFirstName());
        }catch (Exception e){
            return false;
        }

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

    private void createUserAndSendMail(User user, String plainPassword, String firstName){
        userRepository.save(user);
        sendPassword(user, plainPassword, firstName);
    }

    private void sendPassword(User user, String plainPassword, String firstName) {
        String subject = "[Applicazioni Internet] Teams";
        String body = String.format("Ciao %s, benvenuto su Teams!\n" +
                "Il tuo account è stato creato con successo!\n" +
                "Per accedere utilizza i dati forniti di seguito: \n" +
                "Username: %s\n" +
                "Passowrd: %s\n", firstName, user.getUsername(),plainPassword);
        notificationService.sendMessage(user.getUsername(), subject, body);
    }

}
