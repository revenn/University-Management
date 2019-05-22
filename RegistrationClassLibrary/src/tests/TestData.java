/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import model.entity.*;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;
import model.dto.FacultyDTO;
import model.misc.CourseType;

/**
 *
 * @author Bartek
 */
public class TestData {
    
    public String hallData[][] = new String[][] {
        {"C4","20","30"},
        {"C4","20","22"},
        {"C16","20","20"},
        {"C4","18","22"}
    };
    
    public String hallDataToString[] = new String[] {
        "Hall{buildingName=C4, hallName=20, size=30}",
        "Hall{buildingName=C4, hallName=20, size=22}",
        "Hall{buildingName=C16, hallName=20, size=20}",
        "Hall{buildingName=C4, hallName=18, size=22}",
    };
    
    public Hall halls[] = new Hall[] {
        new Hall("C4","20",30), new Hall("C4","20",22), new Hall("C16","20",20), new Hall("C4","18",1) 
    };
    public Lecturer lecturers[] = new Lecturer[]{
        new Lecturer("Jan", "Kowalski", "84521477414", "aadres", "Male", "Mgr. inz"),
        new Lecturer("Maciek", "Kowalski", "84521477414", "aadres", "Male", "Mgr. inz"),
        new Lecturer("Jan", "Kowals", "84521477414", "aadres", "Male", "Mgr. inz"),
        new Lecturer("Jan", "Kowalski", "84121477414", "aadres", "Male", "Mgr. inz"),
        new Lecturer("Jan", "Kowalski", "84521477414", "ares", "Male", "Mgr. inz"),
        new Lecturer("Jan", "Kowalski", "84521477414", "aadres", "Female", "Mgr. inz"),
        new Lecturer("Jan", "Kowalski", "84521477414", "aadres", "Male", "Dr. inz"),
        new Lecturer("Jan", "Kowalski", "84521477414", "aadres", "Male", "Mgr. inz"),};

    public String lecturerDataToString[] = new String[]{
        "Lecturer{ firstName=Jan, lastName=Kowalski, title=Mgr. inz, socialSecurity=84521477414}",
        "Lecturer{ firstName=Maciek, lastName=Kowalski, title=Mgr. inz, socialSecurity=84521477414}",
        "Lecturer{ firstName=Jan, lastName=Kowals, title=Mgr. inz, socialSecurity=84521477414}"
    };

    public String lecturerData[][] = new String[][]{
        {"Jan","Kowalski","84521477414","aadres","Male","Mgr. inz"},
        {"Maciek","Kowalski","84521477414","aadres","Male","Mgr. inz"},
        {"Jan","Kowals","84521477414","aadres","Male","Mgr. inz"},
        {"Jan","Kowals","84521477414","aadres","Male","Mgr. inz"}
    };
    
    public FacultyDTO[] FacultyData = new FacultyDTO[] {
        new FacultyDTO(1l, "Weka", "Elektronika", "sssd")
    };
    

    
    public FieldOfStudy[] FieldOfStudySample = new FieldOfStudy[] {
        new FieldOfStudy("INF", "Informatyka", LevelOfStudy.ENGINEER, ModeOfStudy.FULL_TIME, null)
        
    };
    
        public String termData[][] = new String[][] {
        {hallData[0][0], hallData[0][1],hallData[0][2],"Parzysty","Poniedzialek","9","15"},
        {hallData[1][0], hallData[1][1],hallData[1][2],"Parzysty","Wtorek","7","30"},
        {hallData[2][0], hallData[2][1],hallData[2][2],"Nieparzysty","Piatek","13","15"},
        {hallData[3][0], hallData[3][1],hallData[3][2],"Nieparzysty","Sroda","18","35"},
    };
    
    public String termDataToString[] = new String[] {
        "Term{Hall{buildingName=C4, hallName=20, size=30}, weekParity=Parzysty, dayOfTheWeek=Poniedzialek, time=9:15}",
        "Term{Hall{buildingName=C4, hallName=20, size=22}, weekParity=Parzysty, dayOfTheWeek=Wtorek, time=7:30}",
        "Term{Hall{buildingName=C16, hallName=20, size=20}, weekParity=Nieparzysty, dayOfTheWeek=Piatek, time=13:15}",
        "Term{Hall{buildingName=C4, hallName=18, size=22}, weekParity=Nieparzysty, dayOfTheWeek=Sroda, time=18:35}",
    };
    
    public Term terms[] = new Term[] {
        new Term(halls[0], "Parzysty", "Poniedzialek", 9, 15),
        new Term(halls[1], "Parzysty", "Wtorek", 7, 30),
        new Term(halls[2], "Nieparzysty", "Piatek", 13, 15),
        new Term(halls[3], "Nieparzysty", "Sroda", 18, 35),
    };
    
    public Classes classes[] = new Classes[] {
        new Classes(null,lecturers[0],terms[0]),
        new Classes(null,lecturers[1],terms[0]),
        new Classes(null,lecturers[3],terms[1]),
        new Classes(null, lecturers[2],terms[2]),
        new Classes(null, lecturers[2],terms[3])
    };
    
    
    public CoursesGroup groups[] = new CoursesGroup[] {
      new CoursesGroup("mata",5,2),
      new CoursesGroup("przyra",5,2),
     
    };
    
    public Course courses[] = new Course[]{
        new Course("mata", CourseType.LECTURE, groups[0]), 
        new Course("mata", CourseType.EXERCISES, groups[0]),
        new Course("przyta", CourseType.LECTURE, groups[1]),
    };
    
    public Student students[] = new Student[] {
      new Student("111222", "aaa", "bbb", "11111111111", "qqqq", "male"),
      new Student("666777", "www", "bbb", "22233344444", "qqww", "male")
    };
    
   
}
