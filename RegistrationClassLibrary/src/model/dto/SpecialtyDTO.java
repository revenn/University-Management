package model.dto;

import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class SpecialtyDTO  implements Serializable{

    private String acronym;
    private String name;
    private FieldOfStudyDTO fieldOfStudy;
}
