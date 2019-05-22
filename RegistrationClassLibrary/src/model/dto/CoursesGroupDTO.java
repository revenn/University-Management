package model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoursesGroupDTO implements Serializable{

    private String name;
    private CourseDTO mainCourseDTO;
    private List<CourseDTO> partialCoursesDTOs;
    private int ects;
    private int semester;
    private FieldOfStudyDTO fieldOfStudyDTO;
}
