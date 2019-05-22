package testsFitNesse;

import controller.Factory;
import fit.ColumnFixture;
import model.entity.Faculty;
import model.dto.FacultyDTO;
import model.entity.FieldOfStudy;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;
import util.ModelMapper;

public class addFieldOfStudyTest extends ColumnFixture {
    private String[] data;
    private String[] facultyData;

    public boolean addFieldOfStudy() {
        ModelMapper modelMapper = new ModelMapper();
        Factory factory = new Factory();
        FacultyDTO facultyDTO = modelMapper.createFacultyDTO(facultyData);
        Faculty faculty = factory.createFaculty(facultyDTO);
        SetUp.facade.addFaculty(facultyDTO);

        FieldOfStudy fieldOfStudy = new FieldOfStudy(
                data[0],
                data[1],
                LevelOfStudy.valueOf(data[2]),
                ModeOfStudy.valueOf(data[3]),
                faculty
        );

        return SetUp.facade.addFieldOfStudy(modelMapper.createDTO(fieldOfStudy));
    }
}
