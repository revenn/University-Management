package controller;

import dataMining.DataMining;
import dataMining.model.StatisticObject;
import model.dto.*;
import model.entity.*;
import model.generic.GenericUtils;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import model.dto.FacultyDTO;
import model.dto.FieldOfStudyDTO;
import model.dto.HallDTO;
import model.dto.LecturerDTO;
import model.dto.SpecialtyDTO;
import model.dto.StudentDTO;
import model.misc.CourseType;
import util.ModelMapper;

public class Facade {

    private List<Lecturer> lecturers;
    private List<Hall> halls;
    private List<Student> students;
    private List<Faculty> faculties;
    private ModelMapper modelMapper;

    public Facade() {
        lecturers = new ArrayList<>();
        halls = new ArrayList<>();
        students = new ArrayList<>();
        faculties = new ArrayList<>();
        modelMapper = new ModelMapper();
    }

    public boolean addClasses(ClassesDTO classesDTO) {
        boolean result = false;
        Classes classes = modelMapper.decodeDTO(classesDTO);
        Lecturer lecturer = searchLecturer(classes.getLecturer());
        if(lecturer == null){
            return false;
        }
        for(Faculty faculty : faculties) {
            result = faculty.addClasses(classes,lecturer);
            if(result) {
                break;
            }
        }
        return result;
    }
    
    public boolean addClassesToStudent(String indexNumber, ClassesDTO classesDTO){
        Student studentPattern = new Student(indexNumber);
        Student student = searchStudent(studentPattern);
        
        if(student == null) {
            return false;
        }
        Classes classesPattern = modelMapper.decodeDTO(classesDTO);
        return student.addClass(classesPattern);
    }

    public boolean addSpecialty(SpecialtyDTO specialtyDTO) {
        Specialty specialty = modelMapper.decodeDTO(specialtyDTO);
        Faculty faculty = specialty.getFieldOfStudy().getFaculty();

        return Optional.ofNullable(searchFaculty(faculty))
                .map(faculty1 -> faculty1.addSpecialty(specialty))
                .orElse(false);
    }

    //dla testow Fitnesse
    public boolean addCourse(Object[] data) {
        Factory factory = new Factory();
        Course course = factory.createCourse(data);
        CoursesGroup coursesGroup = (CoursesGroup)data[3];
        if(!coursesGroup.getAllCourses().contains(course)){
            coursesGroup.addPartialCourse(course);
            return true;
        }
        return false;
    }

    public boolean addCourse(CourseDTO courseDTO) {
        Course course = modelMapper.decodeDTO(courseDTO);
        FieldOfStudy fieldOfStudy = course.getCoursesGroup().getFieldOfStudy();

        return Optional.ofNullable( searchFaculty(fieldOfStudy.getFaculty()) )
                .map( faculty -> faculty.addCourse(course) )
                .orElse(false);
    }

    //dla danych inicjujacych oraz testow Fitnesse
    public boolean addCoursesGroup(Object[] data) {
        boolean result = false; 
        
        Factory factory = new Factory();
        CoursesGroup coursesGroup = factory.createCoursesGroup(data);
        FieldOfStudy fieldOfStudyPattern = modelMapper.decodeDTO((FieldOfStudyDTO)data[5]);
        FieldOfStudy fieldOfStudy = null;
        for ( Faculty f : faculties) {
            fieldOfStudy = f.searchFieldOfStudy(fieldOfStudyPattern);
        }
        
        if (fieldOfStudy == null) {
            return false;
        }
        
        Faculty faculty = fieldOfStudy.getFaculty();
        
        result = faculty.addCoursesGroup(coursesGroup);

        return result;
    }

    public boolean addCoursesGroup(CoursesGroupDTO coursesGroupDTO) {
        boolean result = false; 
        CoursesGroup coursesGroup = modelMapper.decodeDTO(coursesGroupDTO);
        FieldOfStudy fieldOfStudy = coursesGroup.getFieldOfStudy();
        Faculty faculty = searchFaculty(fieldOfStudy.getFaculty());
        result = faculty.addCoursesGroup(coursesGroup);

        return result;
    }
    
    public boolean addCompleteCoursesGroup(Object[] data, List<Object[]> partialCourses) {
        boolean result = false; 
        
        Factory factory = new Factory();
        CoursesGroup coursesGroup = factory.createCoursesGroup(data);
        FieldOfStudy fieldOfStudyPattern = modelMapper.decodeDTO((FieldOfStudyDTO)data[5]);
        FieldOfStudy fieldOfStudy = null;
        for(Faculty f : faculties) {
            fieldOfStudy = f.searchFieldOfStudy(fieldOfStudyPattern);
        }
        
        if (fieldOfStudy == null) {
            return false;
        }
        
        Faculty faculty = fieldOfStudy.getFaculty();
        
        result = faculty.addCoursesGroup(coursesGroup, partialCourses);

        return result;
    }
    
    public boolean addLecturer(LecturerDTO lecturerDTO) {
        Lecturer lecturer = modelMapper.decodeDTO(lecturerDTO);

        if (searchLecturer(lecturer) == null){
            lecturers.add(lecturer);
            return true;
        }
        return false;
    }
       
    public boolean addStudent(StudentDTO studentDTO){
        Student student = modelMapper.decodeDTO(studentDTO);

        if (searchStudent(student) == null){
            students.add(student);
            return true;
        }
        return false;
    }

    public boolean addStudent(StudentDTO studentDTO, FieldOfStudyDTO fieldOfStudyDTO){
        if(fieldOfStudyDTO == null) return false;
        
        Student student = modelMapper.decodeDTO(studentDTO);
        FieldOfStudy fieldOfStudyPattern = modelMapper.decodeDTO(fieldOfStudyDTO);
        FieldOfStudy fieldOfStudy = null;
        
        for(Faculty f : faculties) {
           fieldOfStudy =  f.searchFieldOfStudy(fieldOfStudyPattern);
        }
        
        if (fieldOfStudy == null) {
            return false;
        }
        student.setFieldOfStudy(fieldOfStudy);
        
        if (searchStudent(student) == null){
            students.add(student);
            return true;
        }
        return false;
    }

    public boolean addHall(HallDTO hallDTO) {
        Hall hall = modelMapper.decodeDTO(hallDTO);
       

        if (searchHall(hall) == null){
            halls.add(hall);
            return true;
        }
        return false;
    }

    public boolean addFieldOfStudy(FieldOfStudyDTO fieldOfStudyDTO) {
        FieldOfStudy fieldOfStudy = modelMapper.decodeDTO(fieldOfStudyDTO);

        return Optional.ofNullable(searchFaculty(fieldOfStudy.getFaculty()))
                .map(faculty -> faculty.addFieldOfStudy(fieldOfStudy))
                .orElse(false);
    }

    public boolean addFaculty(FacultyDTO faculty) {
        Factory factory = new Factory();
        Faculty newFaculty = factory.createFaculty(faculty);
        if (searchFaculty(newFaculty) == null){
            faculties.add(newFaculty);
            return true;
        }
        return false;
    }
    
    public boolean generateStudentSchedule(StudentDTO studentDTO) {
        Student studentPattern = modelMapper.decodeDTO(studentDTO);
        Student student = searchStudent(studentPattern);
        
        if(student == null) {
            return false;
        }
       
        FieldOfStudy fieldOfStudy = student.getFieldOfStudy();
        
        fieldOfStudy.generateStudentSchedule(student);
        return true;
    }
    
    public void generateAllStudentsSchedule() {
        for(Student student : students) {
            FieldOfStudy fieldOfStudy = student.getFieldOfStudy();
            fieldOfStudy.generateStudentSchedule(student);
        }
    }

    public Faculty searchFaculty(FacultyDTO faculty) {
        Factory factory = new Factory();
        Faculty newFaculty = factory.createFaculty(faculty);

        return searchFaculty(newFaculty);
    }
    
    public Faculty searchFaculty(Faculty facultyTemplate) {
        return GenericUtils.searchInList(faculties, facultyTemplate);
    }

    public Lecturer searchLecturer(Lecturer lecturerTemplate) {
        return GenericUtils.searchInList(lecturers, lecturerTemplate);
    }

    public Hall searchHall(Hall hallTemplate) {
        return GenericUtils.searchInList(halls, hallTemplate);
    }

    public Student searchStudent(Student studentTemplate) {
        return GenericUtils.searchInList(students, studentTemplate);
    }

    public void clearFacultyList(){
        faculties.clear();
    }
    
    public void clearStudentList(){
        students.clear();
    }

    public Object[][] getHallsAsArray(){
        Object[][] hallsArray = new Object[halls.size()][];
        for(int i = 0; i < halls.size(); i++) {
            Hall hall = halls.get(i);
            hallsArray[i] = hall.createStringArray();
        }
        return hallsArray;
    }
    
    public Object[][] getScheduleAsArray(){
        ArrayList<ArrayList<String>> allSchedule = new ArrayList();
        for(Student student : students) {
            student.createStringScheduleForAll(allSchedule);
        }
        Object[][] data = allSchedule.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        
        return data;
    }
    
    public Object[][] getClassesAsArray(){
        ArrayList<ArrayList<String>> allClasses = new ArrayList();
        for(Faculty f : faculties) {
            f.createStringClasses(allClasses);
        }
        Object[][] data = allClasses.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        return data;
    }
    
    public Object[][] getClassesAsArray(String index){
        Student studentPattern = new Student(index);
        Student student = searchStudent(studentPattern);
       
                
        if(student == null) {
            return null;
        }
        ArrayList<ArrayList<String>> classes = new ArrayList();
        student.createStringClasses(classes);
        Object[][] data = classes.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        return data;
    }
    
    public Object[][] getLecturerAsArray(){
        Object[][] lecturersArray = new Object[lecturers.size()][];
        for(int i = 0; i < lecturers.size(); i++) {
            Lecturer lecturer = lecturers.get(i);
            lecturersArray[i] = lecturer.creatrStringArray();
        }
        return lecturersArray;
    }

    public List<FieldOfStudy> getFieldsOfStudyAsList() {
        List<FieldOfStudy> allFieldsOfStudy = new ArrayList<>();
        faculties.forEach(faculty ->
                allFieldsOfStudy.addAll(faculty.getFieldsOfStudy())
        );

        return allFieldsOfStudy;
    }

    public Object[][] getFieldsOfStudyAsArray() {
        List<FieldOfStudy> fieldsOfStudy = getFieldsOfStudyAsList();
        Object[][] array = new Object[fieldsOfStudy.size()][5];

        for (int i = 0; i < fieldsOfStudy.size(); i++) {
            array[i][0] = fieldsOfStudy.get(i).getFaculty();
            array[i][1] = fieldsOfStudy.get(i).getName();
            array[i][2] = fieldsOfStudy.get(i).getLevel();
            array[i][3] = fieldsOfStudy.get(i).getMode();
            array[i][4] = fieldsOfStudy.get(i).getSpecialties();
        }

        return array;
    }

    //do testow
    public FieldOfStudy[] getFieldsOfStudy() {
        List<FieldOfStudy> fieldsOfStudy = getFieldsOfStudyAsList();
        return fieldsOfStudy.toArray(new FieldOfStudy[fieldsOfStudy.size()]);
    }

    public List<Course> getCoursesAsList() {
        List<CoursesGroup> coursesGroups = getCoursesGroupsAsList();
        List<Course> allCourses = new ArrayList<>();
        coursesGroups.forEach((group) -> {
            allCourses.addAll(group.getAllCourses());
        });
        return allCourses;
    }
    
    public List<CoursesGroup> getCoursesGroupsAsList() {
        List<CoursesGroup> coursesGroups = new ArrayList();
        getFieldsOfStudyAsList().forEach(field -> {
            coursesGroups.addAll(field.getCoursesGroups());
        });
        return coursesGroups;
    }

    public Object[][] getCoursesGroupsAsArray() {
        List<CoursesGroup> groups = getCoursesGroupsAsList();
        Object[][] array = new Object[groups.size()][5];
        for(int i = 0; i < groups.size(); i++) {
            array[i][0] = groups.get(i).getName();
            array[i][1] = groups.get(i).getEcts();
            array[i][2] = groups.get(i).getSemester();
            array[i][3] = groups.get(i).getMainCourse().getCourseType().toString();
            array[i][4] = groups.get(i).getPartialCoursesTypesAsString();
        }
        return array;
    }
    
    public Object[][] getScheduleAsArray(String indexNumber) {
        ArrayList<ArrayList<String>> schedule = new ArrayList();
        
        Student studentPattern = new Student(indexNumber);
        Student student = searchStudent(studentPattern);
        
        if(student == null) {
            return null;
        }
        student.createStringSchedule(schedule);
        
        Object[][] data = schedule.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);
        return data;
    }
    
    //for data synchronization purposes
    public List<Faculty> getFaculties() {
        return faculties;
    }
    
    public LinkedList<FacultyDTO> getFacultyDTOs() {
        LinkedList<FacultyDTO> facultyDTOS = new LinkedList<>();

        for (Faculty faculty : faculties) {
            facultyDTOS.add(modelMapper.createDTO(faculty));
        }
        return facultyDTOS;
    }

    public List<FieldOfStudyDTO> getFieldsOfStudyDTOs() {
        List<FieldOfStudyDTO> fieldOfStudyDTOS = new ArrayList<>();

        for (Faculty faculty : faculties) {
            fieldOfStudyDTOS.addAll(modelMapper.createFieldOfStudyDTOsList(faculty.getFieldsOfStudy()));
        }
        return fieldOfStudyDTOS;
    }

    public Object[][] getFacultiesAsArray(){
        Object[][] facultiesArray = new Object[faculties.size()][];
        for(int i = 0; i < faculties.size(); i++) {
            Faculty faculty = faculties.get(i);
            facultiesArray[i] = faculty.createStringArray();
        }
        return facultiesArray;
    }
    
    public Object[][] getStudentsAsArray(){
        Object[][] studentsArray = new Object[students.size()][];
        for(int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            studentsArray[i] = student.createStringArray();
        }
        return studentsArray;
    }

    public List<StudentDTO> getStudentDTOs() {
        List<StudentDTO> studentDTOs = new ArrayList();
        for(Student student : students) {
            studentDTOs.add(modelMapper.createDTO(student));
        }
        return studentDTOs;
    }
    
    public List<LecturerDTO> getLecturerDTOs() {
        List<LecturerDTO> lecturerDTOs = new ArrayList();
        
        for(Lecturer l : lecturers) {
            lecturerDTOs.add(modelMapper.createDTO(l));
        }
        return lecturerDTOs;
    }
    
    public List<HallDTO> getHallDTOs(){
        List<HallDTO> hallDTOs = new ArrayList();
        
        for(Hall h : halls) {
            hallDTOs.add(modelMapper.createDTO(h));
        }
        return hallDTOs;
    }
    
    public List<CourseDTO> getCourseDTOs(){
        List<CourseDTO> courseDTOs = new ArrayList();
        for(Faculty f : faculties) {
            f.getCourseDTOs(courseDTOs);
        }
        return courseDTOs;
    }
    
    public List<ClassesDTO> getClassesDTOs(String index){
        Student studentPattern = new Student(index);
        Student student = searchStudent(studentPattern);
        
        if(student == null) {
            return null;
        }
        List<ClassesDTO> classesDTOs = new ArrayList();
        student.getClassesDTOs(classesDTOs);
        return classesDTOs;
    }
    
    public void initializeWithSampleData(){

        Lecturer []lecturers =
        {
            new Lecturer("Jan","Kowalski","84121111621","", "Male", "Mgr inz."),
            new Lecturer("Jasn","Kowalski","84121111331", "", "Male", "Mgr inz.")
        };
        this.addLecturer(modelMapper.createDTO(lecturers[0]));
        this.addLecturer(modelMapper.createDTO(lecturers[1]));

        Hall []halls =
        {
            new Hall("C4","21",30),
            new Hall("C16","21",22),
        };
        this.addHall(modelMapper.createDTO(halls[0]));
        this.addHall(modelMapper.createDTO(halls[1]));


        String[][] types =
        {
            {"Stacjonarne"},
            {"Niestacjonarne"}
        };

        FacultyDTO[] faculties =
        {
            new FacultyDTO(null, "W4", "Wydzial Elektroniki", "Prof. dr hab. inż. Czesław Smutnicki")
        };
        this.addFaculty(faculties[0]);

        FieldOfStudy[] fieldsOfStudy =
        {
            new FieldOfStudy(
            "INF",
            "Informatyka",
            LevelOfStudy.ENGINEER,
            ModeOfStudy.FULL_TIME,
            searchFaculty(faculties[0]))
        };
        this.addFieldOfStudy(modelMapper.createDTO(fieldsOfStudy[0]));

        Specialty[] specialties = {
                        new Specialty("INS", "Inzynieria systemow", fieldsOfStudy[0]),
                        new Specialty("ISK", "Inzyniera sieci komputerowych", fieldsOfStudy[0])
                };
        this.addSpecialty(modelMapper.createDTO(specialties[0]));
        this.addSpecialty(modelMapper.createDTO(specialties[1]));

        Student[] students =
        {
            new Student("218168", "Jakub", "Małyjasiak", "12345678987", "Address123", "m"),
            new Student("123123", "Muhammad", "Ali", "99999999999", "GdziesWUsa", "m")
        };
        
        this.addStudent(modelMapper.createDTO(students[0]));
        this.addStudent(modelMapper.createDTO(students[1]));
        
        
        this.students.get(0).setFieldOfStudy(this.faculties.get(0).getFieldsOfStudy().get(0));
        this.students.get(1).setFieldOfStudy(this.faculties.get(0).getFieldsOfStudy().get(0));
        

        
        CourseType p = CourseType.LECTURE;
        Object[] coursesGroups = new Object[] {
            "Mata",4,2,"mataW",p,modelMapper.createDTO(fieldsOfStudy[0])
        };
        
        
        this.addCoursesGroup(coursesGroups);
       // this.generateStudentSchedule(modelMapper.createDTO(this.students.get(0)));
       // this.generateStudentSchedule(modelMapper.createDTO(this.students.get(1)));
        this.getScheduleAsArray();
        
        // ------ FOR DATA MINING -------
        
        String[] firstNames = {
            "Ada", "Adela", "Adelajda", "Adrianna", "Agata", "Agnieszka", "Aldona", "Aleksandra", "Alicja", "Alina", "Amanda", "Amelia", "Anastazja",
            "Bartłomiej", "Bartosz", "Benedykt", "Beniamin", "Bernard", "Błażej", "Bogdan", "Bogumił", "Bogusław", "Bolesław", "Borys", "Bronisław"
        };
        String[] lastNames = {
            "Kowalski", "Nowak", "Ryc", "Zuk", "Borsuk", "Wrona", "Jaskółka", "Panczo", "Paniczo", "Dywano", "Muczaczo"
        };
        
         FieldOfStudy simpleFieldOfStudy = new FieldOfStudy(
            "INF",
            "Informatyka",
            LevelOfStudy.ENGINEER,
            ModeOfStudy.FULL_TIME,
            searchFaculty(faculties[0]));
         
        Specialty[] simpleSpecialties = {
            new Specialty("INS", "Inzynieria Systemow Informatycznych", simpleFieldOfStudy),
            new Specialty("INT", "Inzynieria Internetowa", simpleFieldOfStudy),
            new Specialty("ISK", "Inzynieria Systemow i Sieci KOmputerowych", simpleFieldOfStudy)
        }; 
        
          Term terms[] = new Term[] {
            new Term(new Hall("C5", "321", 60), "Parzysty", "Poniedzialek", 9, 15),
            new Term(new Hall("C5", "321", 60), "Parzysty", "Wtorek", 7, 30),
            new Term(new Hall("C5", "321", 60), "Parzysty", "Wtorek", 11, 15),
            new Term(new Hall("C5", "321", 60), "Nieparzysty", "Piatek", 13, 15),
            new Term(new Hall("C5", "321", 60), "Parzysty", "Wtorek", 15, 15),
            new Term(new Hall("C5", "321", 60), "Nieparzysty", "Sroda", 17, 5),
        };
          
        Course courses[] = new Course[]{
            new Course("Analiza Matematyczna", CourseType.LECTURE, new CoursesGroup()), 
            new Course("Podstawy Programowania", CourseType.EXERCISES, new CoursesGroup()),
            new Course("Fizyka", CourseType.LECTURE, new CoursesGroup()),
            new Course("Chemia", CourseType.LECTURE, new CoursesGroup()),
            new Course("Filozofia", CourseType.LECTURE, new CoursesGroup()),
            new Course("Przyroda", CourseType.LECTURE, new CoursesGroup())
        }; 
          

        List<Student> studentsList = generateStudents(firstNames, lastNames, 30);
        List<Classes> classesList = generateClasses(courses, terms, lecturers, 20);
        
        for ( Student student: studentsList ) {
            this.addStudent(modelMapper.createDTO(student));
        }
        
        Random randomGenerator = new Random();
        for ( Student student: this.students ) {
            student.setFieldOfStudy(simpleFieldOfStudy);
            student.setSpecialty(simpleSpecialties[randomGenerator.nextInt(simpleSpecialties.length - 1)]);
            connectStudentWithClasses(student, classesList, 3);
        }
 
       }

    private List<Student> generateStudents(String[] firstNames, String[] lastNames, int count) {
        int indexNumber = 218000;
        Random randomGenerator = new Random();
        List<Student> students = new ArrayList<>();
        
        for ( int i = 0; i < count; i++ ) {
            String firstName = firstNames[randomGenerator.nextInt(firstNames.length - 1)];
            String lastName = lastNames[randomGenerator.nextInt(lastNames.length - 1)];
            Student newStudent = new Student(
                    indexNumber + "",
                    firstName,
                    lastName,
                    "123456789",
                    "Address",
                    firstName.charAt(firstName.length() - 1) == 'a' ? "f" : "m" );
            
            if ( !students.contains(newStudent) ) {
                students.add(newStudent);
                indexNumber++;
            } else {
                i--;
            }
        }
        
        return students;
    }
    
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.initializeWithSampleData();
        
        facade.runDataMining();
    }
    
    void runDataMining() {
        DataMining dataMining = new DataMining();
        
        StatisticObject statisticObject = new StatisticObject("Students");
        statisticObject.setStudentList(this.students);
        
        dataMining.getFieldOfStudyStudentStatistic(statisticObject)
                .getBranches()
                .stream()
                .map( statisticObj -> dataMining.getSpecialtyStudentStatistic(statisticObj).getBranches() )
                .flatMap( list -> list.stream() )
                .map( statisticObj -> dataMining.getGenderStudentStatistic(statisticObj).getBranches() )
                .flatMap( list -> list.stream() )
                .map( statisticObj -> dataMining.getCoursesTermsStudentStatistic(statisticObj).getBranches() )
                .flatMap( list -> list.stream() )
                .collect(Collectors.toList());

        showStatisticObject(statisticObject, 0);
        
        System.out.println("end data mining");
    }
    
    void showStatisticObject(StatisticObject statisticObject, int nesting) {
        for ( int i = 0; i < nesting; i++ ) {
            System.out.print("   ");
        }
        
        System.out.print(statisticObject.getName());
        
        List<StatisticObject> statisticList = statisticObject.getBranches();
        if ( statisticList.size() != 0 ) {
            System.out.println("");
            for ( StatisticObject statisticObj: statisticList ) {
                showStatisticObject(statisticObj, nesting + 1);
            }    
        } else {
            String data =  ": " + statisticObject.getStudentList().size() + "/" + this.students.size();
            System.out.println(data);
        }
        
    }

    private List<Classes> generateClasses(Course[] courses, Term[] terms, Lecturer[] lecturers, int count) {
        Random randomGenerator = new Random();
        List<Classes> classesList = new ArrayList<>();
        
        for (int i = 0; i < count ; i++ ) {
            Classes newClass = new Classes(
                    courses[randomGenerator.nextInt(courses.length - 1)],
                    lecturers[randomGenerator.nextInt(lecturers.length - 1)],
                    terms[randomGenerator.nextInt(terms.length - 1)]);
            if ( !classesList.contains(newClass) )     {
                classesList.add(newClass);
            } else {
                i--;
            }
        }
        
        return classesList;
    }

    private void connectStudentWithClasses(Student student, List<Classes> classesList, int count) {
        Random randomGenerator = new Random();
        for (int i = 0; i < count; i++ ) {
            student.addClasses(classesList.get(randomGenerator.nextInt(classesList.size() - 1)));
        }
    }
}
