package it.ai.polito.lab2.repositories;

import it.ai.polito.lab2.entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
