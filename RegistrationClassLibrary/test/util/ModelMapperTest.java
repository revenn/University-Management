package util;

import model.dto.SpecialtyDTO;
import model.entity.Specialty;
import model.entity.FieldOfStudy;
import model.entity.Faculty;
import model.misc.ModeOfStudy;
import model.misc.LevelOfStudy;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ModelMapperTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void createDTO() throws Exception {
        Faculty faculty = new Faculty("Weka", "Elektronika", "rektor");
        FieldOfStudy fieldOfStudy = new FieldOfStudy("INF", "Informatyka", LevelOfStudy.ENGINEER, ModeOfStudy.FULL_TIME, faculty);
        assertTrue(faculty.addFieldOfStudy(fieldOfStudy));

        Specialty specialty = new Specialty("INS", "Inzynieria", fieldOfStudy);
        assertTrue(faculty.addSpecialty(specialty));

        modelMapper.createDTO(fieldOfStudy);
    }

    @Test
    public void createFieldOfStudyDTOsList() throws Exception {
        Faculty faculty = new Faculty("Weka", "Elektronika", "rektor");
        List<FieldOfStudy> fieldsOfStudy = new ArrayList<>();

        FieldOfStudy fieldOfStudy = new FieldOfStudy("INF", "Informatyka", LevelOfStudy.ENGINEER, ModeOfStudy.FULL_TIME, faculty);
        assertTrue(faculty.addFieldOfStudy(fieldOfStudy));
        fieldsOfStudy.add(fieldOfStudy);

        Specialty specialty = new Specialty("INS", "Inzynieria", fieldOfStudy);
        assertTrue(faculty.addSpecialty(specialty));

        fieldOfStudy = new FieldOfStudy("EKA", "Elektronika", LevelOfStudy.ENGINEER, ModeOfStudy.FULL_TIME, faculty);
        assertTrue(faculty.addFieldOfStudy(fieldOfStudy));

        fieldsOfStudy.add(fieldOfStudy);

        modelMapper.createFieldOfStudyDTOsList(fieldsOfStudy);
    }

    @Test
    public void createSpecialtyDTO_MutualReferencesMustMatch() throws Exception {
        Faculty faculty = new Faculty("Weka", "Elektronika", "rektor");

        FieldOfStudy fieldOfStudy = new FieldOfStudy("INF", "Informatyka", LevelOfStudy.ENGINEER, ModeOfStudy.FULL_TIME, faculty);
        assertTrue(faculty.addFieldOfStudy(fieldOfStudy));

        Specialty specialty = new Specialty("INS", "Inzynieria", fieldOfStudy);
        assertTrue(faculty.addSpecialty(specialty));

        SpecialtyDTO specialtyDTO = modelMapper.createDTO(specialty);

        SpecialtyDTO specialtyDTO1 = specialtyDTO.getFieldOfStudy().getSpecialties().get(0);
        assertTrue(specialtyDTO == specialtyDTO1);
    }
}