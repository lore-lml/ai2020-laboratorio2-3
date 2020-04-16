package it.ai.polito.lab2.dtos;

import lombok.Data;

@Data
public class CourseDTO {
    private String name;
    private int min;
    private int max;
    private boolean enabled;
}
