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
public class LecturerDTO extends PersonDTO  implements Serializable{

    private String title;
    private List<ClassesDTO> classes = new ArrayList<>();
    
    public LecturerDTO(String title, String firstName, String lastName, String personalIdentityNumber, String address, String gender) {
        super(firstName, lastName, personalIdentityNumber, address, gender);
        this.title = title;
    }
}

