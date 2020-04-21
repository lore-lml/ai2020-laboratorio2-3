package it.ai.polito.lab2.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TeamDTO {

    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private int status;

    public TeamDTO(String name, int status) {
        this.name = name;
        this.status = status;
    }
}
