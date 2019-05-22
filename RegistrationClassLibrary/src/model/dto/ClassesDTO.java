package model.dto;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassesDTO implements Serializable{
   
    private CourseDTO course;
    private LecturerDTO lecturer;
    private TermDTO term;
    private List<StudentDTO> students;
}
