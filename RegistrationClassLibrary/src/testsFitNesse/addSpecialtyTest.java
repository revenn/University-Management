package testsFitNesse;

import model.entity.Faculty;
import model.entity.FieldOfStudy;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;
import model.entity.Specialty;
import controller.Factory;
import fit.ColumnFixture;
import model.dto.FacultyDTO;
import util.ModelMapper;

public class addSpecialtyTest extends ColumnFixture{

    private String[] data;
    private String[] fieldOfStudyData;
    private String[] facultyData;

    public boolean addSpecialty(){
        ModelMapper modelMapper = new ModelMapper();
        Factory factory = new Factory();
        FacultyDTO facultyDTO = modelMapper.createFacultyDTO(facultyData);
        Faculty faculty = factory.createFaculty(facultyDTO);
        SetUp.facade.addFaculty(facultyDTO);

        FieldOfStudy fieldOfStudy = new FieldOfStudy(
                fieldOfStudyData[0],
                fieldOfStudyData[1],
                LevelOfStudy.valueOf(fieldOfStudyData[2]),
                ModeOfStudy.valueOf(fieldOfStudyData[3]),
                faculty
        );

        SetUp.facade.addFieldOfStudy(modelMapper.createDTO(fieldOfStudy));
        Specialty specialty = new Specialty(data[0], data[1], fieldOfStudy);

        return SetUp.facade.addSpecialty(modelMapper.createDTO(specialty));
    }
}
