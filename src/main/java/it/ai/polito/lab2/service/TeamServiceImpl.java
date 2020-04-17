package it.ai.polito.lab2.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import it.ai.polito.lab2.dtos.CourseDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.entities.Course;
import it.ai.polito.lab2.entities.Student;
import it.ai.polito.lab2.exceptions.CourseNotFoundException;
import it.ai.polito.lab2.exceptions.StudentNotFoundException;
import it.ai.polito.lab2.exceptions.TeamServiceException;
import it.ai.polito.lab2.repositories.CourseRepository;
import it.ai.polito.lab2.repositories.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Reader;
import java.util.ArrayList;
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
            throw new CourseNotFoundException();
        }
    }

    @Override
    public boolean addStudentToCourse(String studentId, String courseName) {
        Student s;
        Course c;
        try{
            s = studentRepository.findById(studentId).get();
        }catch (NoSuchElementException e){
            throw new StudentNotFoundException();
        }
        try{
            c = courseRepository.findById(courseName).get();
        }catch (NoSuchElementException e){
            throw new CourseNotFoundException();
        }

        return c.addStudent(s);
    }

    @Override
    public void enableCourse(String courseName) {
        try{
            Course c = courseRepository.findById(courseName).get();
            c.setEnabled(true);
        }catch (NoSuchElementException e){
            throw new CourseNotFoundException();
        }
    }

    @Override
    public void disableCourse(String courseName) {
        try{
            Course c = courseRepository.findById(courseName).get();
            c.setEnabled(false);
            courseRepository.save(c);
        }catch (NoSuchElementException e){
            throw new CourseNotFoundException();
        }
    }

    @Override
    public List<Boolean> addAll(List<StudentDTO> students) {
        List<Boolean> res = new ArrayList<>();
        students.forEach(s-> res.add(addStudent(s)));
        return res;
    }

    @Override
    public List<Boolean> enrollAll(List<String> studentIds, String courseName) {
        try {
            List<Boolean> res = new ArrayList<>();
            studentIds.forEach(s -> res.add(addStudentToCourse(s, courseName)));
            return res;
        }catch (TeamServiceException e){
            throw e;
        }
    }

    @Override
    public List<Boolean> addAndEnroll(Reader r, String courseName) {
        CsvToBean<StudentDTO> csvToBean = new CsvToBeanBuilder(r)
                .withType(StudentDTO.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        try {
            List<StudentDTO> students = csvToBean.parse();
            addAll(students);
            return enrollAll(students.stream()
                    .map(StudentDTO::getId)
                    .collect(Collectors.toList()),
                    courseName);
        }catch (TeamServiceException e){
            throw e;
        }
    }

    @Override
    public List<CourseDTO> getCourses(String studentId) {
        try{
            Student s = studentRepository.findById(studentId).get();
            return s.getCourses().stream().map(c-> mapper.map(c, CourseDTO.class)).collect(Collectors.toList());
        }catch (NoSuchElementException e){
            throw new StudentNotFoundException();
        }
    }
}
