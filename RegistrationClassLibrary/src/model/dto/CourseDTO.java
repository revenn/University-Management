package model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.misc.CourseType;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO  implements Serializable{

    private String name;
    private CourseType courseType;
    private CoursesGroupDTO coursesGroupDTO;
    private List<ClassesDTO> classes;

}
