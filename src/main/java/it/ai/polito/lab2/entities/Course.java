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
        if(student == null || students.contains(student))
            return false;

        students.add(student);

        List<Course> courses = student.getCourses();
        if(!courses.contains(this))
            courses.add(this);

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Course course = (Course) o;

        return name.equals(course.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
