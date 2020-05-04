package it.ai.polito.lab2.controllers;

import it.ai.polito.lab2.dtos.CourseDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import lombok.Data;
import org.springframework.hateoas.Link;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

public class ModelHelper {

    public static CourseDTO enrich(CourseDTO course){
        Link self = linkTo(CourseController.class).slash(course.getName()).withSelfRel();
        Link enrolled = linkTo(CourseController.class).slash(course.getName())
                .slash("enrolled").withRel("enrolled");
        course.add(self);
        course.add(enrolled);
        return course;
    }

    public static StudentDTO enrich(StudentDTO student){
        Link self = linkTo(StudentController.class).slash(student.getId()).withSelfRel();
        student.add(self);
        return student;
    }

    @Data
    public static class TeamProposal {
        private String teamName;
        private List<String> memberIds = new ArrayList<>();
    }
}
