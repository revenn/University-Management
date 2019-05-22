package util;

import model.dto.*;
import model.entity.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ModelMapper {

    public FacultyDTO createDTO(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();

        facultyDTO.setAcronym(faculty.getAcronym());
        facultyDTO.setName(faculty.getName());
        facultyDTO.setDean(faculty.getDean());

        return facultyDTO;
    }

    public FieldOfStudyDTO createDTO(FieldOfStudy fieldOfStudy) {
        FieldOfStudyDTO fieldOfStudyDTO = new FieldOfStudyDTO();
        fieldOfStudyDTO.setId(fieldOfStudy.getId());
        fieldOfStudyDTO.setAcronym(fieldOfStudy.getAcronym());
        fieldOfStudyDTO.setName(fieldOfStudy.getName());
        fieldOfStudyDTO.setLevel(fieldOfStudy.getLevel());
        fieldOfStudyDTO.setMode(fieldOfStudy.getMode());
        fieldOfStudyDTO.setFaculty(createDTO(fieldOfStudy.getFaculty()));
        fieldOfStudyDTO.setSpecialties(createSpecialtyDTOsList(fieldOfStudy.getSpecialties(), fieldOfStudyDTO));

        return fieldOfStudyDTO;
    }

    private SpecialtyDTO createDTO(Specialty specialty, FieldOfStudyDTO fieldOfStudy) {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();

        specialtyDTO.setAcronym(specialty.getAcronym());
        specialtyDTO.setName(specialty.getName());
        specialtyDTO.setFieldOfStudy(fieldOfStudy);

        return specialtyDTO;
    }

    public SpecialtyDTO createDTO(Specialty specialty) {
        SpecialtyDTO specialtyDTO = new SpecialtyDTO();

        specialtyDTO.setAcronym(specialty.getAcronym());
        specialtyDTO.setName(specialty.getName());
        specialtyDTO.setFieldOfStudy(createDTO(specialty.getFieldOfStudy()));

        List<SpecialtyDTO> specialties = specialtyDTO.getFieldOfStudy().getSpecialties();
        specialties.remove(specialtyDTO);
        specialties.add(specialtyDTO);

        return specialtyDTO;
    }

    public Faculty decodeDTO(FacultyDTO facultyDTO) {
        Faculty faculty = new Faculty();

        faculty.setAcronym(facultyDTO.getAcronym());
        faculty.setName(facultyDTO.getName());
        faculty.setDean(facultyDTO.getDean());
        
        return faculty;
    }

    public FieldOfStudy decodeDTO(FieldOfStudyDTO fieldOfStudyDTO) {
        FieldOfStudy fieldOfStudy = new FieldOfStudy();

        fieldOfStudy.setAcronym(fieldOfStudyDTO.getAcronym());
        fieldOfStudy.setName(fieldOfStudyDTO.getName());
        fieldOfStudy.setLevel(fieldOfStudyDTO.getLevel());
        fieldOfStudy.setMode(fieldOfStudyDTO.getMode());
        fieldOfStudy.setFaculty(decodeDTO(fieldOfStudyDTO.getFaculty()));

        return fieldOfStudy;
    }

    public Specialty decodeDTO(SpecialtyDTO specialtyDTO) {
        Specialty specialty = new Specialty();

        specialty.setAcronym(specialtyDTO.getAcronym());
        specialty.setName(specialtyDTO.getName());
        specialty.setFieldOfStudy(decodeDTO(specialtyDTO.getFieldOfStudy()));

        return specialty;
    }

    private List<SpecialtyDTO> createSpecialtyDTOsList(List<Specialty> specialties, FieldOfStudyDTO fieldOfStudyDTO) {
        List<SpecialtyDTO> specialtyDTOS = new ArrayList<>();

        for (Specialty specialty : specialties) {
            specialtyDTOS.add(createDTO(specialty, fieldOfStudyDTO));
        }

        return specialtyDTOS;
    }

    public List<FieldOfStudyDTO> createFieldOfStudyDTOsList(List<FieldOfStudy> fieldsOfStudy) {
        List<FieldOfStudyDTO> fieldOfStudyDTOS = new ArrayList<>();

        for (FieldOfStudy fieldOfStudy : fieldsOfStudy) {
            fieldOfStudyDTOS.add(createDTO(fieldOfStudy));
        }
        return fieldOfStudyDTOS;
    }

    public HallDTO createDTO(Hall hall) {
        HallDTO hallDTO = new HallDTO();
        hallDTO.setBuildingName(hall.getBuildingName());
        hallDTO.setHallName(hall.getHallName());
        hallDTO.setSize(hall.getSize());
        return hallDTO;
    }

    public Hall decodeDTO(HallDTO hallDTO) {
        String buildingName = hallDTO.getBuildingName();
        String hallName = hallDTO.getHallName();
        Integer size = hallDTO.getSize();
        Hall hall = new Hall(buildingName,hallName,size);
        return hall;
    }

    public LecturerDTO createDTO(Lecturer lecturer) {
        LecturerDTO lecturerDTO = new LecturerDTO();
        lecturerDTO.setFirstName(lecturer.getFirstName());
        lecturerDTO.setLastName(lecturer.getLastName());
        lecturerDTO.setPersonalIdentityNumber(lecturer.getPersonalIdentityNumber());
        lecturerDTO.setAddress(lecturer.getAddress());
        lecturerDTO.setGender(lecturer.getGender());
        lecturerDTO.setTitle(lecturer.getTitle());
        return lecturerDTO;
    }

    public Lecturer decodeDTO(LecturerDTO lecturerDTO) {
        String firstName = lecturerDTO.getFirstName();
        String lastName = lecturerDTO.getLastName();
        String personalIdentityNumber = lecturerDTO.getPersonalIdentityNumber();
        String address = lecturerDTO.getAddress();
        String gender = lecturerDTO.getGender();
        String title = lecturerDTO.getTitle();
        Lecturer lecturer = new Lecturer(firstName, lastName, personalIdentityNumber, address, gender, title);
        return lecturer;
    }

    public StudentDTO createDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setAddress(student.getAddress());
        studentDTO.setClasses(createClassesDTOsList(student.getClasses()));
       // studentDTO.setFieldOfStudy(createDTO(student.getFieldOfStudy()));
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setGender(student.getGender());
        studentDTO.setIndexNumber(student.getIndexNumber());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setPersonalIdentityNumber(student.getPersonalIdentityNumber());
        studentDTO.setSemester(student.getSemester());
       // studentDTO.setSpecialty(createDTO(student.getSpecialty()));
        return studentDTO;
    }

    public Student decodeDTO(StudentDTO studentDTO) {
        String indexNumber = studentDTO.getIndexNumber();
        String firstName = studentDTO.getFirstName();
        String lastName = studentDTO.getLastName();
        String personalIdentityNumber = studentDTO.getPersonalIdentityNumber();
        String address = studentDTO.getAddress();
        String gender = studentDTO.getGender();

        Student student = new Student(indexNumber, firstName, lastName, personalIdentityNumber, address, gender);

        return student;
    }

    public List<ClassesDTO> createClassesDTOsList(List<Classes> classes) {
        List<ClassesDTO> classesDTOs = new ArrayList<>();

        for (Classes c : classes) {
            classesDTOs.add(createDTO(c));
        }

        return classesDTOs;
    }
    
    public List<ClassesDTO> createClassesDTOsList(List<Classes> classes, CourseDTO courseDTO) {
        List<ClassesDTO> classesDTOs = new ArrayList<>();

        for (Classes c : classes) {
            classesDTOs.add(createDTO(c,courseDTO));
        }

        return classesDTOs;
    }

    public ClassesDTO createDTO(Classes classes) {
        ClassesDTO classesDTO = new ClassesDTO();
        //classesDTO.setCourse(createDTO(classes.getCourse()));
        classesDTO.setLecturer(createDTO(classes.getLecturer()));
        classesDTO.setStudents(createStudentDTOsList(classes.getStudents()));
        classesDTO.setTerm(createDTO(classes.getTerm()));
        return classesDTO;
    }
    
    public ClassesDTO createDTO(Classes classes, CourseDTO courseDTO){
        ClassesDTO classesDTO = new ClassesDTO();
        classesDTO.setCourse(courseDTO);
        classesDTO.setLecturer(createDTO(classes.getLecturer()));
        classesDTO.setStudents(createStudentDTOsList(classes.getStudents()));
        classesDTO.setTerm(createDTO(classes.getTerm()));
        return classesDTO;
    }

    public CourseDTO createDTO(Course course,CoursesGroupDTO coursesGroupDTO){
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setClasses(createClassesDTOsList(course.getClasses(),courseDTO));
        courseDTO.setCourseType(course.getCourseType());
        courseDTO.setCoursesGroupDTO(coursesGroupDTO);
        courseDTO.setName(course.getName());
        return courseDTO;
    }
    
    

    public CoursesGroupDTO createDTO(CoursesGroup coursesGroup){
        CoursesGroupDTO coursesGroupDTO = new CoursesGroupDTO();
        coursesGroupDTO.setEcts(coursesGroup.getEcts());
        coursesGroupDTO.setMainCourseDTO(createDTO(coursesGroup.getMainCourse(), coursesGroupDTO));
        coursesGroupDTO.setName(coursesGroup.getName());
        coursesGroupDTO.setPartialCoursesDTOs(createCoursesDTOsList(coursesGroup.getPartialCourses(), coursesGroupDTO));
        coursesGroupDTO.setSemester(coursesGroup.getSemester());
        coursesGroupDTO.setFieldOfStudyDTO(createDTO(coursesGroup.getFieldOfStudy()));
        return coursesGroupDTO;
    }

    public CoursesGroup decodeDTO(CoursesGroupDTO coursesGroupDTO) {
        CoursesGroup coursesGroup = new CoursesGroup();
        coursesGroup.setName(coursesGroupDTO.getName());
        coursesGroup.setEcts(coursesGroupDTO.getEcts());
        coursesGroup.setSemester(coursesGroupDTO.getSemester());
        coursesGroup.setFieldOfStudy(decodeDTO(coursesGroupDTO.getFieldOfStudyDTO()));

        Course mainCourse = decodeLocalDTO(coursesGroupDTO.getMainCourseDTO());
        mainCourse.setCoursesGroup(coursesGroup);
        coursesGroup.setMainCourse(mainCourse);

        if (coursesGroupDTO.getPartialCoursesDTOs() != null) {
            coursesGroupDTO
                    .getPartialCoursesDTOs()
                    .stream()
                    .map(this::decodeLocalDTO)
                    .forEach(course -> {
                        course.setCoursesGroup(coursesGroup);
                        coursesGroup.addPartialCourse(course);
                    });
        }

        return coursesGroup;
    }

    private Course decodeLocalDTO(CourseDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setCourseType(courseDTO.getCourseType());

        return course;
    }

    public Course decodeDTO(CourseDTO courseDTO) {
        return new Course(
                courseDTO.getName(),
                courseDTO.getCourseType(),
                decodeDTO(courseDTO.getCoursesGroupDTO())
        );
    }

    public CoursesGroup decodeLocalDTO(CoursesGroupDTO coursesGroupDTO) {
        CoursesGroup coursesGroup = new CoursesGroup();
        coursesGroup.setName(coursesGroupDTO.getName());
        coursesGroup.setEcts(coursesGroupDTO.getEcts());
        coursesGroup.setSemester(coursesGroupDTO.getSemester());
        coursesGroup.setFieldOfStudy(decodeDTO(coursesGroupDTO.getFieldOfStudyDTO()));

        return coursesGroup;
    }
    

    public List<CourseDTO> createCoursesDTOsList(List<Course> courses,CoursesGroupDTO coursesGroupDTO) {
        List<CourseDTO> courseDTOs = new ArrayList<>();

        for (Course c : courses) {
            courseDTOs.add(createDTO(c,coursesGroupDTO));
        }
        return courseDTOs;
    }

    public List<StudentDTO> createStudentDTOsList(List<Student> students) {
        List<StudentDTO> studentDTOs = new ArrayList<>();

        for(Student s : students) {
            studentDTOs.add(createDTO(s));
        }
        return studentDTOs;
    }
    
    public List<Student> decodeStudentDTOsList (List<StudentDTO> studentDTOs) {
        List<Student> students = new ArrayList();
        
        for(StudentDTO s : studentDTOs) {
            students.add(decodeDTO(s));
        }
        return students;
    }

    public TermDTO createDTO(Term term) {
        TermDTO termDTO = new TermDTO();
        termDTO.setDayOfTheWeek(term.getDayOfTheWeek());
        termDTO.setHall(createDTO(term.getHall()));
        termDTO.setTimeTable(term.getTimeTable());
        termDTO.setWeekParity(term.getWeekParity());
        return termDTO;
    }
    
    public Term decodeDTO (TermDTO termDTO)
    {
        HallDTO hallDTO = termDTO.getHall();
        Hall hall = decodeDTO(hallDTO);
        
        String weekParity = termDTO.getWeekParity();
        String dayOfTheWeek = termDTO.getDayOfTheWeek();
        Integer timeTable[] = termDTO.getTimeTable();
        Term term = new Term(hall, weekParity, dayOfTheWeek, timeTable[0], timeTable[1]);
        return term;
    }

    public FacultyDTO createFacultyDTO(String[] data) {
        return new FacultyDTO(null, data[0], data[1], data[2]);
    }
    
    public Classes decodeDTO(ClassesDTO classesDTO) {
        CourseDTO courseDTO = classesDTO.getCourse();
        LecturerDTO lecturerDTO = classesDTO.getLecturer();
        TermDTO termDTO = classesDTO.getTerm();
        
       
        Course course = decodeLocalDTO(courseDTO);
        CoursesGroup coursesGroup = decodeDTO(courseDTO.getCoursesGroupDTO());
        course.setCoursesGroup(coursesGroup);
        Lecturer lecturer = decodeDTO(lecturerDTO);
        Term term = decodeDTO(termDTO);
        
        Classes classes = new Classes(course, lecturer, term);
        return classes;
    }

    public List<FacultyDTO> createFacultyDTOsList(List<Faculty> faculties) {
        List<FacultyDTO> facultyDTOs = new LinkedList<>();
        faculties.forEach(faculty -> facultyDTOs.add(createDTO(faculty)));
        return facultyDTOs;
    }
}
