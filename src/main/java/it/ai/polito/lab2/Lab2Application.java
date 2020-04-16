package it.ai.polito.lab2;

import it.ai.polito.lab2.repositories.CourseRepository;
import it.ai.polito.lab2.repositories.StudentRepository;
import it.ai.polito.lab2.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Lab2Application {

    @Bean
    public CommandLineRunner runner(TeamService teamService){
        return args -> {
            teamService.getAllCourses().forEach(System.out::println);
            System.out.println();
            teamService.getAllStudents().forEach(System.out::println);
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
