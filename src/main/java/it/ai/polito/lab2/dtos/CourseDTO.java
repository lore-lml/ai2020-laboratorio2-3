package it.ai.polito.lab2.dtos;

import lombok.Data;

@Data
public class CourseDTO {
    private String name;
    private int min;
    private int max;
    private boolean enabled;

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
