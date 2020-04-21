package it.ai.polito.lab2.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StudentDTO {

    @CsvBindByName
    @EqualsAndHashCode.Include
    private String id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String firstName;
}
