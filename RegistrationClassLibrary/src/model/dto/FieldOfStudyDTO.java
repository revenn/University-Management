package model.dto;

import java.io.Serializable;
import lombok.*;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class FieldOfStudyDTO  implements Serializable{

    private Long id;
    private String acronym;
    private String name;
    private LevelOfStudy level;
    private ModeOfStudy mode;
    private FacultyDTO faculty;
    private List<SpecialtyDTO> specialties;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldOfStudyDTO that = (FieldOfStudyDTO) o;

        if (acronym != null ? !acronym.equals(that.acronym) : that.acronym != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (level != that.level) return false;
        if (mode != that.mode) return false;
        return faculty != null ? !faculty.equals(that.faculty) : that.faculty != null;
    }
}
