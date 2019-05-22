package model.dto;

import java.io.Serializable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FacultyDTO implements Serializable {

    private Long id;
    private String acronym;
    private String name;
    private String dean;
}
