package it.ai.polito.lab2.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Student {
    private static final String joinTable = "student_course";
    private static final String joinColumn = "student_id";
    private static final String inverseJoinCol = "course_name";
    @Id
    private String id;
    private String name;
    private String firstName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = joinTable,
            joinColumns = @JoinColumn(name = joinColumn),
            inverseJoinColumns = @JoinColumn(name = inverseJoinCol)
    )
    private List<Course> courses = new ArrayList<>();

    public boolean addCourse(Course course){
        if(!courses.contains(course))
            courses.add(course);

        List<Student> students = course.getStudents();
        if(!students.contains(this))
            students.add(this);

        //TODO: capire quando tornare false
        return true;
    }
}
