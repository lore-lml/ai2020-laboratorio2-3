package it.ai.polito.lab2.service;

import it.ai.polito.lab2.dtos.CourseDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.entities.Course;
import it.ai.polito.lab2.entities.Student;
import it.ai.polito.lab2.exceptions.CourseNotFoundException;
import it.ai.polito.lab2.exceptions.StudentNotFoundException;
import it.ai.polito.lab2.repositories.CourseRepository;
import it.ai.polito.lab2.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private ModelMapper mapper;


    @Override
    public boolean addCourse(CourseDTO course) {
        if(course == null || courseRepository.findById(course.getName()).isPresent())
            return false;
        courseRepository.save(mapper.map(course, Course.class));
        return true;
    }

    @Override
    public Optional<CourseDTO> getCourse(String name) {
        Optional<Course> course = courseRepository.findById(name);
        return course.map(c -> mapper.map(c, CourseDTO.class));
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(c-> mapper.map(c, CourseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean addStudent(StudentDTO student) {
        if(student == null || studentRepository.findById(student.getName()).isPresent())
            return false;
        studentRepository.save(mapper.map(student, Student.class));
        return true;
    }

    @Override
    public Optional<StudentDTO> getStudent(String studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.map(s -> mapper.map(s, StudentDTO.class));
    }

    @Override
    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(s-> mapper.map(s, StudentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getEnrolledStudents(String courseName) {
        try {
            return courseRepository.findById(courseName).get().getStudents().stream()
                    .map(s-> mapper.map(s, StudentDTO.class)).collect(Collectors.toList());
        }catch (NoSuchElementException e){
            return null;
        }
    }

    @Override
    public boolean addStudentToCourse(String studentId, String courseName) {
        Student s;
        Course c;
        try{
            s = studentRepository.findById(studentId).get();
        }catch (NoSuchElementException e){
            throw new StudentNotFoundException(e);
        }
        try{
            c = courseRepository.findById(courseName).get();
        }catch (NoSuchElementException e){
            throw new CourseNotFoundException(e);
        }

        c.addStudent(s);
        courseRepository.save(c);
        return true;
    }

    @Override
    public void enableCourse(String courseName) {
        try{
            Course c = courseRepository.findById(courseName).get();
            c.setEnabled(true);
            courseRepository.save(c);
        }catch (NoSuchElementException e){
            throw new CourseNotFoundException(e);
        }
    }

    @Override
    public void disableCourse(String courseName) {
        try{
            Course c = courseRepository.findById(courseName).get();
            c.setEnabled(false);
            courseRepository.save(c);
        }catch (NoSuchElementException e){
            throw new CourseNotFoundException(e);
        }
    }
}
