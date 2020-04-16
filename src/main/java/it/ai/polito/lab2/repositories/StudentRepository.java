package it.ai.polito.lab2.repositories;

import it.ai.polito.lab2.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
