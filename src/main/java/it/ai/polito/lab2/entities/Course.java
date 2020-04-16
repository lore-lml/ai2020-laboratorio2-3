package it.ai.polito.lab2.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Course {
    @Id
    private String name;
    private int min;
    private int max;
    private boolean enabled;

    @ManyToMany(mappedBy = "courses")
    private List<Student> students = new ArrayList<>();

    public boolean addStudent(Student student){
        if(!students.contains(student))
            students.add(student);

        List<Course> courses = student.getCourses();
        if(!courses.contains(this))
            courses.add(this);

        //TODO: capire quando tornare false
        return true;
    }
}
