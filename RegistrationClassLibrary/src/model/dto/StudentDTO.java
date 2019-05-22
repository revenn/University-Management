package model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO extends PersonDTO  implements Serializable{

    String indexNumber;
    FieldOfStudyDTO fieldOfStudy;
    SpecialtyDTO specialty;
    int semester;
    List<ClassesDTO> classes;
    
    public StudentDTO(String indexNumber,int semester, String firstName, String lastName, String personalIdentityNumber, String address, String gender) {
        super(firstName, lastName, personalIdentityNumber, address, gender);
        this.indexNumber = indexNumber;
        this.semester = semester;
        //this.fieldOfStudy = null;
        //this.specialty = null;
        this.semester = semester;
        this.classes = new ArrayList<>();
    }
}
