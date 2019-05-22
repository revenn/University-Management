//package model;
//
//import controller.Factory;
//import org.junit.*;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//
//public class FacultyTest {
//
//    private Factory factory;
//    private List<Faculty> faculties;
//
//    @Before
//    public void setUp(){
//        factory = new Factory();
//        faculties = new FacultyRepository();
//    }
//
//    @Test
//    public void addSpecialty_SpecialtyExists_SpecialtyNotAdded() throws Exception {
//        Faculty faculty = factory.createFaculty(new String[]{"WEKA", "Elektronika", "rektor"});
//        assertTrue(facultyRepository.save(faculty));
//
//        FieldOfStudy fieldOfStudy = new FieldOfStudy(
//                "INF",
//                "Infomatyka",
//                LevelOfStudy.ENGINEER,
//                ModeOfStudy.FULL_TIME,
//                faculty
//        );
//
//        assertTrue(faculty.addFieldOfStudy(fieldOfStudy));
//        assertTrue(faculty.addSpecialty(fieldOfStudy, new Specialty("INS", "Inzynieria systemow", fieldOfStudy)));
//
//        faculty = factory.createFaculty(new String[]{"WEKA", "Elektronika", "rektor"});
//        fieldOfStudy = new FieldOfStudy(
//                "INF",
//                "Infomatyka",
//                LevelOfStudy.ENGINEER,
//                ModeOfStudy.FULL_TIME,
//                faculty
//        );
//
//        faculty = facultyRepository.findOne(faculty);
//        assertFalse(faculty.addSpecialty(fieldOfStudy, new Specialty("INS", "Inzynieria systemow", fieldOfStudy)));
//    }
//
//    @Test
//    public void addFieldOfStudy_FieldOfStudyExists_FieldOfStudyNotAdded() throws Exception {
//        Faculty faculty = factory.createFaculty(new String[]{"WEKA", "Elektronika", "rektor"});
//        assertTrue(facultyRepository.save(faculty));
//
//        FieldOfStudy fieldOfStudy = new FieldOfStudy(
//                "INF",
//                "Infomatyka",
//                LevelOfStudy.ENGINEER,
//                ModeOfStudy.FULL_TIME,
//                faculty
//        );
//
//        assertTrue(faculty.addFieldOfStudy(fieldOfStudy));
//
//        faculty = factory.createFaculty(new String[]{"WEKA", "Elektronika", "rektor"});
//        fieldOfStudy = new FieldOfStudy(
//                "INF",
//                "Infomatyka",
//                LevelOfStudy.ENGINEER,
//                ModeOfStudy.FULL_TIME,
//                faculty
//        );
//
//        faculty = facultyRepository.findOne(faculty);
//
//        assertFalse(faculty.addFieldOfStudy(fieldOfStudy));
//    }
//}