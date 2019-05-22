package model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonDTO  implements Serializable{
    
    String firstName;
    String lastName;
    String personalIdentityNumber;
    String address;
    String gender;  
}
