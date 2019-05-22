/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessTier;

import IntegrationTier.FacultyFacadeLocal;
import IntegrationTier.FieldOfStudyFacadeLocal;
import controller.Facade;
import java.util.LinkedList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.swing.JOptionPane;
import model.dto.*;
import model.entity.Faculty;
import model.entity.FieldOfStudy;
import util.ModelMapper;

@Stateless
public class Facade_ejb implements Facade_ejbRemote {

    @EJB
    private FieldOfStudyFacadeLocal fieldOfStudyFacade;

    @EJB
    private FacultyFacadeLocal facultyFacade;
    
    Facade facade = new Facade();
    
    ModelMapper modelMapper = new ModelMapper();
    
    @Override
    public boolean addFieldsOfStudy_db(){
        fieldOfStudyFacade.addFieldsOfStudy(facade.getFieldsOfStudyAsList());
        return true;
    }
    @Override
    public boolean addFaculties_db(){   
        facultyFacade.addFaculties(facade.getFaculties());
        return true;
    }
    
    @Override
    public List<FieldOfStudyDTO> getFieldOfStudyDTOs_db(){
        List<FieldOfStudy> fieldsOfStudy = fieldOfStudyFacade.findAll();
        return modelMapper.createFieldOfStudyDTOsList(fieldsOfStudy);
    }
    
    @Override
    public List<FacultyDTO> getFacultyDTOs_db(){
        List<Faculty> faculty = facultyFacade.findAll();
        return modelMapper.createFacultyDTOsList(faculty);
    }
    
    @Override
    public boolean addClasses(ClassesDTO classesDTO) {
        return facade.addClasses(classesDTO);
    }

    @Override
    public boolean addSpecialty(SpecialtyDTO specialtyDTO) {
        return facade.addSpecialty(specialtyDTO);
    }
   
    @Override
    public boolean addCourse(CourseDTO courseDTO) {
       return facade.addCourse(courseDTO);
    }

    @Override
    public boolean addCoursesGroup(CoursesGroupDTO coursesGroupDTO) {
        return facade.addCoursesGroup(coursesGroupDTO);
    }
        
    @Override
    public boolean addLecturer(LecturerDTO lecturerDTO) {
         return facade.addLecturer(lecturerDTO);
    }
       
    @Override
    public boolean addStudent(StudentDTO studentDTO){
        return facade.addStudent(studentDTO);
    }

    @Override
    public boolean addStudent(StudentDTO studentDTO, FieldOfStudyDTO fieldOfStudyDTO){
        return facade.addStudent(studentDTO, fieldOfStudyDTO);
    }

    @Override
    public boolean addHall(HallDTO hallDTO) {
        return facade.addHall(hallDTO);
    }

    @Override
    public boolean addFieldOfStudy(FieldOfStudyDTO fieldOfStudyDTO) {
        return facade.addFieldOfStudy(fieldOfStudyDTO);
    }

    @Override
    public boolean addFaculty(FacultyDTO faculty) {
        return facade.addFaculty(faculty);
    }
    
    @Override
    public boolean generateStudentSchedule(StudentDTO studentDTO) {
        return facade.generateStudentSchedule(studentDTO);
    }
    
    @Override
    public void generateAllStudentsSchedule() {
        facade.generateAllStudentsSchedule();
    }
   
    @Override
    public Object[][] getHallsAsArray(){
        return facade.getHallsAsArray();
    }
    
    @Override
    public Object[][] getScheduleAsArray(){
        return facade.getScheduleAsArray();
    }
    @Override
    public Object[][] getClassesAsArray(){
        return facade.getClassesAsArray();
    }
    @Override
    public Object[][] getLecturerAsArray(){
        return facade.getLecturerAsArray();
    }

    @Override
    public Object[][] getFieldsOfStudyAsArray() {
        return facade.getFieldsOfStudyAsArray();
    }

    @Override
    public Object[][] getCoursesGroupsAsArray() {
        return facade.getCoursesGroupsAsArray();
    }

    @Override
    public LinkedList<FacultyDTO> getFacultyDTOs() {
        return facade.getFacultyDTOs();
    }

    @Override
    public List<FieldOfStudyDTO> getFieldsOfStudyDTOs() {
        return facade.getFieldsOfStudyDTOs();
    }

    @Override  
    public Object[][] getFacultiesAsArray(){
        return facade.getFacultiesAsArray();
    }
    
    @Override
    public Object[][] getStudentsAsArray(){
        return facade.getStudentsAsArray();
    }

    
    @Override
    public void initializeWithSampleData(){
        facade.initializeWithSampleData();
    }

    @Override
    public boolean addCompleteCoursesGroup(Object[] coursesGroupData, List<Object[]> partialCoursesData) {
        return facade.addCompleteCoursesGroup(coursesGroupData, partialCoursesData);
    }

    @Override
    public List<LecturerDTO> getLecturerDTOs() {
        return facade.getLecturerDTOs();
    }

    @Override
    public List<HallDTO> getHallDTOs() {
        return facade.getHallDTOs();
    }

    @Override
    public List<CourseDTO> getCourseDTOs() {
        return facade.getCourseDTOs();
    }

    @Override
    public List<StudentDTO> getStudentDTOs() {
        return facade.getStudentDTOs();
    }

    @Override
    public Object[][] getScheduleAsArray(String indexNumber) {
        return facade.getScheduleAsArray(indexNumber);
    }

    @Override
    public Object[][] getClassesAsArray(String index) {
        return facade.getClassesAsArray(index);
    }

    @Override
    public List<ClassesDTO> getClassesDTOs(String index) {
        return facade.getClassesDTOs(index);
    }

    @Override
    public boolean addClassesToStudent(String indexNumber, ClassesDTO classesDTO) {
        return facade.addClassesToStudent(indexNumber, classesDTO);
    }
}
