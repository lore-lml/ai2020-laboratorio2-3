package it.ai.polito.lab2.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CourseDTO {
    private String name;
    private int min;
    private int max;
    private boolean enabled;

    public CourseDTO(String name, int min, int max) {
        this.name = name;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CourseDTO courseDTO = (CourseDTO) o;

        return name.equals(courseDTO.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
