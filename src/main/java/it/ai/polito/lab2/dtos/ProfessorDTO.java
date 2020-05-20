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
    @Pattern(regexp = "d[0-9]+", message = "The id must be in the following format d<id>")
    private String id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String firstName;

    public String getEmail(){
        return String.format("%s@polito.it", id);
    }
}
