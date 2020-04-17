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

    public void addCourse(Course course){
        if(course == null)
            return;

        /*courses.add(course);

        List<Student> students = course.getStudents();
        if(!students.contains(this))
            students.add(this);*/

        courses.add(course);
        course.getStudents().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return id.equals(student.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
