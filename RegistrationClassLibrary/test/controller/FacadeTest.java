
package controller;

import model.dto.*;
import model.entity.CoursesGroup;
import model.entity.Faculty;
import model.entity.FieldOfStudy;
import model.entity.Specialty;
import model.misc.CourseType;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;
import org.junit.Before;
import org.junit.Test;
import tests.TestData;
import util.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class FacadeTest {

    private TestData testData;
    private Facade facade;
    private Factory factory;
    private ModelMapper modelMapper;

    @Before
    public void SetUp() {
        factory = new Factory();
        testData = new TestData();
        facade = new Facade();
        modelMapper = new ModelMapper();
    }

    @Test
    public void testAddHall() {
        System.out.println("addHall");
        for (int i = 0; i < testData.halls.length; i++) {
            if (i == 1) {
                assertFalse(facade.addHall(modelMapper.createDTO(testData.halls[i])));
            } else {
                assertTrue(facade.addHall(modelMapper.createDTO(testData.halls[i])));
            }
        }
    }

    @Test
    public void addFieldOfStudyToListTest() throws Exception {
        String acronym = "ELE", name = "Elektronika";
        Faculty faculty = new Faculty(acronym, name, "Rektor");
        assertTrue(facade.addFaculty(modelMapper.createDTO(faculty)));

        FieldOfStudy fieldOfStudy = new FieldOfStudy(
                acronym,
                name,
                LevelOfStudy.ENGINEER,
                ModeOfStudy.FULL_TIME,
                faculty
        );

        assertEquals(0, facade.getFieldsOfStudyAsList().size());
        facade.addFieldOfStudy(modelMapper.createDTO(fieldOfStudy));
        assertEquals(1, facade.getFieldsOfStudyAsList().size());

        fieldOfStudy = facade.getFieldsOfStudyAsList().get(0);

        assertEquals(acronym, fieldOfStudy.getAcronym());
        assertEquals(name, fieldOfStudy.getName());
        assertEquals(LevelOfStudy.ENGINEER, fieldOfStudy.getLevel());
        assertEquals(ModeOfStudy.FULL_TIME, fieldOfStudy.getMode());
        assertEquals(faculty, fieldOfStudy.getFaculty());
    }

    @Test
    public void getFieldsOfStudyAsArrayTest() throws Exception {
        String acronym = "ELE", name = "Elektronika";
        Faculty faculty = new Faculty(acronym, name, "Rektor");
        assertTrue(facade.addFaculty(modelMapper.createDTO(faculty)));

        FieldOfStudy fieldOfStudy = new FieldOfStudy(
                acronym,
                name,
                LevelOfStudy.ENGINEER,
                ModeOfStudy.FULL_TIME,
                faculty
        );

        assertEquals(0, facade.getFieldsOfStudyAsList().size());
        
        facade.addFieldOfStudy(modelMapper.createDTO(fieldOfStudy));
        assertEquals(1, facade.getFieldsOfStudyAsList().size());

        Object[][] fieldsOfStudy = facade.getFieldsOfStudyAsArray();

        assertEquals(faculty, fieldsOfStudy[0][0]);
        assertEquals(name, fieldsOfStudy[0][1]);
        assertEquals(LevelOfStudy.ENGINEER, fieldsOfStudy[0][2]);
        assertEquals(ModeOfStudy.FULL_TIME, fieldsOfStudy[0][3]);
    }

    @Test
    public void addSpecialtyTest() throws Exception {
        Object[] data;
        String acronym = "Weka", name = "Elektronika";
        Faculty faculty = modelMapper.decodeDTO(testData.FacultyData[0]);

        data = new Object[]{
                acronym,
                name,
                LevelOfStudy.ENGINEER,
                ModeOfStudy.FULL_TIME,
                faculty
        };

        Factory factory = new Factory();

        FieldOfStudy fieldOfStudy = factory.createFieldOfStudy(data);

        Specialty specialty = new Specialty("INS", "Inzynieria", fieldOfStudy);

        assertEquals(0, fieldOfStudy.getSpecialties().size());
        fieldOfStudy.addSpecialty(specialty);
        assertEquals(1, fieldOfStudy.getSpecialties().size());
        assertEquals(specialty, fieldOfStudy.getSpecialties().get(0));
    }
    
    @Test
    public void testAddLecturer() {
        System.out.println("addLecturer");
        int testDataLength = testData.lecturers.length;
        for (int i = 0; i < testDataLength; i++) {
            if (i < testDataLength - 1) {
                assertTrue(facade.addLecturer(modelMapper.createDTO(testData.lecturers[i])));
            } else {
                assertFalse(facade.addLecturer(modelMapper.createDTO(testData.lecturers[i])));
            }
        }
    }
    
    @Test
    public void addCoursesGroupTest() throws Exception {
        FieldOfStudy fieldOfStudy = testData.FieldOfStudySample[0];

        Object[] data = new Object[]{
            "Analiza matematyczna",
            4,
            1,
            "Analiza matematyczna - " + CourseType.LECTURE,
            CourseType.LECTURE,
            fieldOfStudy
        };

        assertEquals(0, fieldOfStudy.getCoursesGroups().size());
        assertTrue(facade.addCoursesGroup(data));
        assertEquals(1, fieldOfStudy.getCoursesGroups().size());
    }

    @Test
    public void addSpecialty_SpecialtyExists_SpecialtyNotAdded() throws Exception {
        Faculty faculty = factory.createFaculty(testData.FacultyData[0]);
        assertTrue(facade.addFaculty(testData.FacultyData[0]));

        FieldOfStudy fieldOfStudy = new FieldOfStudy(
                "INF",
                "Infomatyka",
                LevelOfStudy.ENGINEER,
                ModeOfStudy.FULL_TIME,
                faculty
        );

        assertTrue(facade.addFieldOfStudy(modelMapper.createDTO(fieldOfStudy)));

        Specialty specialty = new Specialty("INS", "Inzynieria systemow", fieldOfStudy);

        assertTrue(facade.addSpecialty(modelMapper.createDTO(specialty)));
        assertFalse(facade.addSpecialty(modelMapper.createDTO(specialty)));

        specialty = new Specialty("INS", "Inzynieria systemow", fieldOfStudy);

        assertFalse("Specialty duplicated, should not be added", facade.addSpecialty(modelMapper.createDTO(specialty)));
    }

    @Test
    public void getFieldsOfStudyAsDTOs_FieldsOfStudyAddedAsDTOs_ListReturned() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Faculty faculty = factory.createFaculty(testData.FacultyData[0]);
        assertTrue(facade.addFaculty(testData.FacultyData[0]));

        FieldOfStudy fieldOfStudy = new FieldOfStudy("INF", "Informatyka", LevelOfStudy.ENGINEER, ModeOfStudy.FULL_TIME, faculty);
        FieldOfStudyDTO fieldOfStudyDTO = modelMapper.createDTO(fieldOfStudy);
        assertTrue(facade.addFieldOfStudy(fieldOfStudyDTO));

        Specialty specialty = new Specialty("INS", "Inzynieria", fieldOfStudy);
        SpecialtyDTO specialtyDTO = modelMapper.createDTO(specialty);
        assertTrue(facade.addSpecialty(specialtyDTO));

        fieldOfStudy = new FieldOfStudy("EKA", "Elektronika", LevelOfStudy.ENGINEER, ModeOfStudy.FULL_TIME, faculty);
        fieldOfStudyDTO = modelMapper.createDTO(fieldOfStudy);
        assertTrue(facade.addFieldOfStudy(fieldOfStudyDTO));

        facade.getFieldsOfStudyDTOs();
    }

    @Test
    public void shouldAddCourseGroupFromDTOWithAssociatedFieldOfStudy() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        FacultyDTO simpleFacultyDTO = testData.FacultyData[0];
        FieldOfStudyDTO simpleFieldOfStudyDTO = modelMapper.createDTO(
                new FieldOfStudy("INF", "Informatyka", LevelOfStudy.ENGINEER, ModeOfStudy.FULL_TIME, modelMapper.decodeDTO(simpleFacultyDTO))
        );
        CoursesGroupDTO coursesGroupDTO = new CoursesGroupDTO();
        CourseDTO mainCourse = new CourseDTO("Analiza matematyczna 1", CourseType.LECTURE, coursesGroupDTO, new ArrayList<>());

        coursesGroupDTO.setName("Analiza matematyczna 1");
        coursesGroupDTO.setMainCourseDTO(mainCourse);
        coursesGroupDTO.setFieldOfStudyDTO(simpleFieldOfStudyDTO);

        facade.addFaculty(simpleFacultyDTO);
        facade.addFieldOfStudy(simpleFieldOfStudyDTO);

        assertTrue(facade.addCoursesGroup(coursesGroupDTO));

        List<CoursesGroup> coursesGroups = facade.getCoursesGroupsAsList();
        FieldOfStudy fieldOfStudy = facade.getFieldsOfStudy()[0];
        CoursesGroup coursesGroup = modelMapper.decodeDTO(coursesGroupDTO);

        assertFalse(coursesGroups.isEmpty());
        assertEquals(fieldOfStudy, coursesGroups.get(0).getFieldOfStudy());
        assertFalse(fieldOfStudy.getCoursesGroups().isEmpty());
        assertTrue(fieldOfStudy.getCoursesGroups().contains(coursesGroup));
        assertFalse(facade.addCoursesGroup(coursesGroupDTO));
    }

    @Test
    public void shouldAddCourseFromDTOWithAssociatedCourseGroup() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        FacultyDTO simpleFacultyDTO = testData.FacultyData[0];
        FieldOfStudyDTO simpleFieldOfStudyDTO = modelMapper.createDTO(
                new FieldOfStudy("INF", "Informatyka", LevelOfStudy.ENGINEER, ModeOfStudy.FULL_TIME, modelMapper.decodeDTO(simpleFacultyDTO))
        );

        CoursesGroupDTO coursesGroupDTO = new CoursesGroupDTO();
        CourseDTO mainCourse = new CourseDTO("Analiza matematyczna 1", CourseType.LECTURE, coursesGroupDTO, new ArrayList<>());
        CourseDTO course1 = new CourseDTO("Analiza matematyczna 1", CourseType.EXERCISES, coursesGroupDTO, new ArrayList<>());

        coursesGroupDTO.setName("Analiza matematyczna 1");
        coursesGroupDTO.setMainCourseDTO(mainCourse);
        coursesGroupDTO.setFieldOfStudyDTO(simpleFieldOfStudyDTO);

        facade.addFaculty(simpleFacultyDTO);
        facade.addFieldOfStudy(simpleFieldOfStudyDTO);
        facade.addCoursesGroup(coursesGroupDTO);

        assertTrue(facade.addCourse(course1));
        assertFalse(facade.addCourse(course1));
        assertFalse(facade.addCourse(mainCourse));
    }

}
