package it.ai.polito.lab2.repositories;

import it.ai.polito.lab2.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    @Query("SELECT c.name FROM Student s INNER JOIN s.courses c WHERE s.id=:studentId")
    List<String> getCourseNames(String studentId);
}
