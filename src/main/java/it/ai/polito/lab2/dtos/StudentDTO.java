package it.ai.polito.lab2.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends RepresentationModel<StudentDTO> {

    @CsvBindByName
    private String id;

    @CsvBindByName
    private String name;

    @CsvBindByName
    private String firstName;
}
