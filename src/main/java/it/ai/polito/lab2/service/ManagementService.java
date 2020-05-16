package it.ai.polito.lab2.service;

import it.ai.polito.lab2.dtos.ProfessorDTO;
import it.ai.polito.lab2.dtos.StudentDTO;
import it.ai.polito.lab2.entities.Course;

import java.io.Reader;
import java.util.List;

public interface ManagementService {
    boolean createStudentUser(StudentDTO studentDTO);
    boolean createProfessorUser(ProfessorDTO professorDTO);
}
