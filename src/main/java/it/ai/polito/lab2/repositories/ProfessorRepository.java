package it.ai.polito.lab2.repositories;

import it.ai.polito.lab2.entities.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, String> {
    @Query("SELECT c.name FROM Professor p INNER JOIN p.courses c WHERE p.id=:professorId")
    List<String> getCourseNames(String professorId);
}