package it.ai.polito.lab2.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class StudentDTO {
    @CsvBindByName
    private String id;
    @CsvBindByName
    private String name;
    @CsvBindByName
    private String firstName;

    public StudentDTO() {}

    public StudentDTO(String id, String name, String firstName) {
        this.id = id;
        this.name = name;
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentDTO that = (StudentDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
