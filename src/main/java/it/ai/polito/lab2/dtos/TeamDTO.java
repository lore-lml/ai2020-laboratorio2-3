package it.ai.polito.lab2.dtos;

import it.ai.polito.lab2.entities.Team;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamDTO {

    private Long id;
    private String name;
    private Team.Status status;

}
