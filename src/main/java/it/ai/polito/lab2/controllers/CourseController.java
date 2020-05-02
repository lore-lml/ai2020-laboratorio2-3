package it.ai.polito.lab2.controllers;

import it.ai.polito.lab2.dtos.CourseDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.dtos.TeamDTO;
import it.ai.polito.lab2.service.NotificationService;
import it.ai.polito.lab2.service.TeamService;
import it.ai.polito.lab2.service.exceptions.CourseNotFoundException;
import it.ai.polito.lab2.service.exceptions.TeamServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/API/courses")
public class CourseController {

    @Autowired
    private TeamService teamService;
    @Autowired
    private NotificationService notificationService;

    @GetMapping({"", "/"})
    private List<CourseDTO> all(){
        List<String> ids = Arrays.asList("s1", "s2");
        TeamDTO team = teamService.proposeTeam("Applicazioni Internet", "DAMN", ids);
        notificationService.notifyTeam(team, ids);
        return teamService.getAllCourses().stream().map(ModelHelper::enrich).collect(Collectors.toList());
    }

    @GetMapping("/{courseName}")
    private CourseDTO getOne(@PathVariable String courseName){
        try{
            return ModelHelper.enrich(teamService.getCourse(courseName).get());
        }catch (NoSuchElementException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Course not found: %s", courseName));
        }
    }

    @GetMapping("/{courseName}/enrolled")
    private List<StudentDTO> enrolledStudents(@PathVariable String courseName){
        try {
            return teamService.getEnrolledStudents(courseName).stream()
                    .map(ModelHelper::enrich).collect(Collectors.toList());
        }catch (CourseNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping({"", "/"})
    @ResponseStatus(value = HttpStatus.CREATED)
    private CourseDTO addCourse(@RequestBody CourseDTO course){
        if(teamService.addCourse(course)){
            return ModelHelper.enrich(course);
        }

        throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Course already exist: %s", course.getName()));
    }

    @PostMapping("/{courseName}/enrollOne")
    @ResponseStatus(value = HttpStatus.CREATED)
    private void enrollOneStudent(@PathVariable String courseName, @RequestBody String studentID){
        try {
            if(!teamService.addStudentToCourse(studentID, courseName))
                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Student <%s> is already enrolled", studentID));
        }catch (TeamServiceException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }


    @PostMapping("/{courseName}/enrollMany")
    @ResponseStatus(value = HttpStatus.CREATED)
    private List<Boolean> enrollStudents(@PathVariable String courseName, @RequestParam("file") MultipartFile file){
        //TODO: handle MultipartExceptions

        if(!file.getContentType().equals("text/csv"))
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "text/csv file required");

        try(Reader r = new BufferedReader(new InputStreamReader(file.getInputStream()))){
            return teamService.addAndEnroll(r, courseName);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An error occurred while reading the file, please try again later");
        } catch (TeamServiceException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }
}
