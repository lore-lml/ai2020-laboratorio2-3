package it.ai.polito.lab2;

import it.ai.polito.lab2.service.TeamService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.*;

@SpringBootApplication
public class Lab2Application {

    @Bean
    public CommandLineRunner runner(TeamService teamService){
        return args -> {
            /*File file = new File("src/main/resources/static/students.csv");
            try(Reader reader = new BufferedReader(new FileReader(file))){
                teamService.addAndEnroll(reader, "Programmazione di Sistema");
            }catch (Exception ex) {
                System.out.println("An error occured while processing csv files: " + ex.getMessage());

            }*/
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
