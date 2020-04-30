package it.ai.polito.lab2.controllers;

import it.ai.polito.lab2.dtos.CourseDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.service.TeamService;
import it.ai.polito.lab2.service.exceptions.CourseNotFoundException;
import it.ai.polito.lab2.service.exceptions.TeamServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/courses")
public class CourseController {

    @Autowired
    private TeamService teamService;

    @GetMapping({"", "/"})
    private List<CourseDTO> all(){
        return teamService.getAllCourses().stream().map(ModelHelper::enrich).collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    private CourseDTO getOne(@PathVariable String name){
        try{
            return ModelHelper.enrich(teamService.getCourse(name).get());
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Course not found: %s", name));
        }
    }

    @GetMapping("/{name}/enrolled")
    private List<StudentDTO> enrolledStudents(@PathVariable String name){
        return teamService.getEnrolledStudents(name).stream()
                .map(ModelHelper::enrich).collect(Collectors.toList());
    }

    @PostMapping({"", "/"})
    @ResponseStatus(value = HttpStatus.CREATED)
    private CourseDTO addCourse(@RequestBody CourseDTO course){
        if(teamService.addCourse(course)){
            return ModelHelper.enrich(course);
        }

        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Course already exist: %s", course.getName()));
    }

    @PostMapping("/{name}/enrollOne")
    private void enrollOneStudent(@PathVariable String courseName, @RequestBody String studentID){
        try {
            if(!teamService.addStudentToCourse(studentID, courseName))
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Student <%s> is already enrolled", studentID));
        }catch (TeamServiceException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    @PostMapping("/{name}/enrollMany")
    private void enrollStudents(@PathVariable String name, @RequestParam("file") MultipartFile file){
        /*if(file.getContentType().equals("text/csv"))
            throw new HttpClientErrorException.UnsupportedMediaType();*/
    }
}
