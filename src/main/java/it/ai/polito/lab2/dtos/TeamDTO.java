package it.ai.polito.lab2.dtos;

import lombok.Data;

@Data
public class TeamDTO {
    private Long id;
    private String name;
    private int status;

    public TeamDTO() {}

    public TeamDTO(String name, int status) {
        this.name = name;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TeamDTO teamDTO = (TeamDTO) o;

        return id.equals(teamDTO.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
