package it.ai.polito.lab2;

import it.ai.polito.lab2.security.entities.User;
import it.ai.polito.lab2.security.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class Lab2Application {
    @Bean

    public CommandLineRunner runner(UserRepository repository, PasswordEncoder passwordEncoder){
        return args -> {
            List<User> users = Arrays.asList(
                    User.builder().username("s1").password(passwordEncoder.encode("s1")).roles(Arrays.asList("ROLE_STUDENT")).build(),
                    User.builder().username("d1").password(passwordEncoder.encode("d1")).roles(Arrays.asList("ROLE_TEACHER")).build(),
                    User.builder().username("admin").password(passwordEncoder.encode("admin")).roles(Arrays.asList("ROLE_ADMIN")).build()
            );

            repository.saveAll(users);
            repository.flush();
            System.out.println(repository.findAll());
        };
    }

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

}
