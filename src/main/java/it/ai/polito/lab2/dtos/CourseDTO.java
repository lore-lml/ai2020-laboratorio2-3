package it.ai.polito.lab2.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CourseDTO {

    @EqualsAndHashCode.Include
    private String name;
    private int min;
    private int max;
    private boolean enabled;

    public CourseDTO(String name, int min, int max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }
}
