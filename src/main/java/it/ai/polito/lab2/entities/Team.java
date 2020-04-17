package it.ai.polito.lab2.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Team {
    private static final String courseId = "course_id";

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int status;

    @ManyToOne
    @JoinColumn(name = courseId)
    private Course course;

    public void setCourse(Course course){
        if(this.course != null)
            this.course.getTeams().remove(this);

        if(course != null)
            course.getTeams().add(this);
        
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        return id.equals(team.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
