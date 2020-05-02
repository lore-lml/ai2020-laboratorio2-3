package it.ai.polito.lab2.controllers;

import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/students")
public class StudentController {
    @Autowired
    private TeamService teamService;

    @GetMapping({"", "/"})
    private List<StudentDTO> all(){
        return teamService.getAllStudents().stream().map(ModelHelper::enrich).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    private StudentDTO getOne(@PathVariable String id){
        try{
            return ModelHelper.enrich(teamService.getStudent(id).get());
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Student not found: %s", id));
        }
    }

    @PostMapping({"","/"})
    @ResponseStatus(value = HttpStatus.CREATED)
    private StudentDTO addStudent(@RequestBody StudentDTO student){
        if(teamService.addStudent(student))
            return ModelHelper.enrich(student);

        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Student already exist: %s", student.getId()));
    }
}
