package it.ai.polito.lab2.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Team {

    private static final String courseId = "course_id";
    private static final String joinTable = "team_student";
    private static final String joinColumn = "team_id";
    private static final String inverseJoinCol = "student_id";

    @Id
    @GeneratedValue
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private int status;

    @ManyToOne
    @JoinColumn(name = courseId)
    private Course course;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = joinTable,
            joinColumns = @JoinColumn(name = joinColumn),
            inverseJoinColumns = @JoinColumn(name = inverseJoinCol)
    )
    private List<Student> members = new ArrayList<>();

    public void setCourse(Course course){
        if(this.course != null)
            this.course.getTeams().remove(this);

        if(course != null)
            course.getTeams().add(this);
        
        this.course = course;
    }

    public void addMember(Student member){
        if(member == null)
            return;

        members.add(member);
        member.getTeams().add(this);
    }

    public void removeMember(Student member){
        if(member == null)
            return;

        members.remove(member);
        member.getTeams().remove(this);
    }
}
