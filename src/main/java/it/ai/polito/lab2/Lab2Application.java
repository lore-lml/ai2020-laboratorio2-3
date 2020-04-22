package it.ai.polito.lab2;

import it.ai.polito.lab2.dtos.CourseDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.entities.Team;
import it.ai.polito.lab2.repositories.TeamRepository;
import it.ai.polito.lab2.service.exceptions.TeamServiceException;
import it.ai.polito.lab2.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Lab2Application {

    //PER FARE I TEST HO UTILIZZATO Lab2ApplicationTests con JUNIT
    //Se si vuole runnare i test lasciare commentato il runner
    /*
    @Bean
    public CommandLineRunner runner(TeamService teamService){
        return args -> {

            //ADD AND ENROLL STUDENTS

            File file = new File("src/main/resources/static/students.csv");
            try(Reader reader = new BufferedReader(new FileReader(file))){
                System.out.println(teamService.addAndEnroll(reader, "Applicazioni Internet"));
            }catch (Exception ex) {
                System.out.println("An error occured while processing csv files: " + ex.getMessage());

            }

            System.out.println();

            //GETTERS
            System.out.println("GET SINGOLI:");
            System.out.println(teamService.getCourse("Applicazioni Internet"));
            System.out.println(teamService.getStudent("s1"));
            System.out.println();
            System.out.println("GET ALL");
            System.out.println(teamService.getAllCourses());
            System.out.println(teamService.getAllStudents());
            System.out.println("GET ENROLLED STUDENTS");
            System.out.println(teamService.getEnrolledStudents("Applicazioni Internet"));

            //ADD COURSE
            teamService.addCourse(new CourseDTO("OOP", 10, 300));

            teamService.addStudentToCourse("s1", "Programmazione di Sistema");
            teamService.addStudentToCourse("s1", "Reti");
            System.out.println("GET STUDENT COURSES:\n" + teamService.getCourses("s1"));
        };
    }
     */

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab2Application.class, args);
    }

}
