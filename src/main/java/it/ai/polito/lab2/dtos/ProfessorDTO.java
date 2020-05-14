package it.ai.polito.lab2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorDTO {
    @Pattern(regexp = "d[0-9]+")
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String firstName;
}
