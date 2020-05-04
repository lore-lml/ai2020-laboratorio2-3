package it.ai.polito.lab2.dtos;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends RepresentationModel<StudentDTO> {

    @CsvBindByName
    @NotEmpty
    private String id;

    @CsvBindByName
    @NotEmpty
    private String name;

    @CsvBindByName
    @NotEmpty
    private String firstName;
}
