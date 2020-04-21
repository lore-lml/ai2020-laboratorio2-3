package it.ai.polito.lab2;

import it.ai.polito.lab2.dtos.CourseDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.service.TeamService;
import it.ai.polito.lab2.service.exceptions.CourseNotFoundException;
import it.ai.polito.lab2.service.exceptions.StudentNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Lab2ApplicationTests {

    @Autowired
    private TeamService teamService;

    @Test
    void student() {
        List<StudentDTO> expected = Arrays.asList(
                new StudentDTO("s1", "Limoli", "Lorenzo"),
                new StudentDTO("s2", "Loscalzo", "Stefano")
        );

        expected.forEach(s-> assertTrue(teamService.addStudent(s)));
        expected.forEach(s-> assertFalse(teamService.addStudent(s)));

        assertEquals(Optional.of(expected.get(0)), teamService.getStudent("s1"));
        assertEquals(Optional.empty(), teamService.getStudent("fake"));

        List<StudentDTO> students = teamService.getAllStudents();
        assertEquals(new HashSet<>(expected), new HashSet<>(students));
    }

    @Test
    void course(){
        List<CourseDTO> expected = Arrays.asList(
                new CourseDTO("Applicazioni Internet", 2,4),
                new CourseDTO("Programmazione di Sistema", 3,4),
                new CourseDTO("OOP", 1,2),
                new CourseDTO("Reti", 2,3)
        );

        expected.forEach(c-> assertTrue(teamService.addCourse(c)));
        expected.forEach(c-> assertFalse(teamService.addCourse(c)));

        assertEquals(Optional.of(expected.get(2)), teamService.getCourse("OOP"));
        assertEquals(Optional.empty(), teamService.getCourse("FAKE"));

        List<CourseDTO> courses = teamService.getAllCourses();
        assertEquals(new HashSet<>(expected), new HashSet<>(courses));
    }

    @Test
    void enableDisableCourse(){
        try {
            String courseName = "Applicazioni Internet";
            teamService.enableCourse(courseName);
            CourseDTO course = teamService.getCourse(courseName).get();
            assertTrue(course.isEnabled());

            teamService.disableCourse(courseName);
            course = teamService.getCourse(courseName).get();
            assertFalse(course.isEnabled());
        }catch (NoSuchElementException e){
            fail();
        }

        try {
            teamService.enableCourse("fake");
            fail();
        }catch (CourseNotFoundException ignored){
        }

        try {
            teamService.disableCourse("fake");
            fail();
        }catch (CourseNotFoundException ignored){
        }
    }

    @Test
    void addStudentToCourse(){
        String courseName = "Applicazioni Internet";
        File file = new File("src/main/resources/static/students.csv");
        try(Reader reader = new BufferedReader(new FileReader(file))){
            teamService.enableCourse(courseName);
            List<Boolean> res = teamService.addAndEnroll(reader, courseName);
            assertEquals(Arrays.asList(true,true,true,true), res);
        }catch (Exception ex) {
            fail();
        }

        List<StudentDTO> expected = Arrays.asList(
                new StudentDTO("s1", "Limoli", "Lorenzo"),
                new StudentDTO("s2", "Loscalzo", "Stefano"),
                new StudentDTO("s3", "Matteotti", "Giacomo"),
                new StudentDTO("s4", "Rossi", "Marco")
        );
        assertEquals(new HashSet<>(expected), new HashSet<>(teamService.getEnrolledStudents(courseName)));

        try{
            teamService.getEnrolledStudents("fake");
            fail();
        }catch (CourseNotFoundException ignored){
        }

        assertFalse(teamService.addStudentToCourse(expected.get(0).getId(), courseName));
        try {
            teamService.addStudentToCourse("fake", courseName);
            fail();
        }catch (StudentNotFoundException ignored){}

        try {
            teamService.addStudentToCourse(expected.get(0).getId(), "FAKE");
            fail();
        }catch (CourseNotFoundException ignored){}

    }

}
