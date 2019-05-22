/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessTier;

import java.util.LinkedList;
import java.util.List;
import javax.ejb.Remote;
import model.dto.*;

/**
 *
 * @author Kuba
 */
@Remote
public interface Facade_ejbRemote {
        
    //DB
    boolean addFieldsOfStudy_db();
    boolean addFaculties_db();
    List<FieldOfStudyDTO> getFieldOfStudyDTOs_db();
    List<FacultyDTO> getFacultyDTOs_db();

    //CLASS LIBRARY
    boolean addClasses(ClassesDTO classesDTO);
    boolean addSpecialty(SpecialtyDTO specialtyDTO);
    boolean addCourse(CourseDTO courseDTO);
    boolean addCoursesGroup(CoursesGroupDTO coursesGroupDTO);
    boolean addLecturer(LecturerDTO lecturerDTO);
    boolean addStudent(StudentDTO studentDTO);
    boolean addStudent(StudentDTO studentDTO, FieldOfStudyDTO fieldOfStudyDTO);
    boolean addHall(HallDTO hallDTO);
    boolean addFieldOfStudy(FieldOfStudyDTO fieldOfStudyDTO);
    boolean addFaculty(FacultyDTO faculty);
    boolean generateStudentSchedule(StudentDTO studentDTO);
    void generateAllStudentsSchedule();
    Object[][] getHallsAsArray();
    Object[][] getScheduleAsArray();
    Object[][] getScheduleAsArray(String indexNumber);
    Object[][] getClassesAsArray();
    Object[][] getClassesAsArray(String index);
    Object[][] getLecturerAsArray();
    Object[][] getFieldsOfStudyAsArray();
    Object[][] getCoursesGroupsAsArray();
    LinkedList<FacultyDTO> getFacultyDTOs();
    List<FieldOfStudyDTO> getFieldsOfStudyDTOs();
    Object[][] getFacultiesAsArray();
    Object[][] getStudentsAsArray();
    List<StudentDTO> getStudentDTOs();
    List<LecturerDTO> getLecturerDTOs();
    List<HallDTO> getHallDTOs();
    List<CourseDTO> getCourseDTOs();
    List<ClassesDTO> getClassesDTOs(String index);
    void initializeWithSampleData();
    boolean addCompleteCoursesGroup(Object[] coursesGroupData, List<Object[]> partialCoursesData);
    boolean addClassesToStudent(String indexNumber, ClassesDTO classesDTO);
}
