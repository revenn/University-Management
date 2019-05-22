/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import controller.Facade;
import java.util.ArrayList;
import java.util.List;
import model.dto.ClassesDTO;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import tests.TestData;
import util.ModelMapper;
/**
 *
 * @author Bartek
 */
public class StudentTest {
    
    TestData testData;
    
    @Before
    public void SetUp(){
        testData = new TestData();
    }

 

    /**
     * Test of addClass method, of class Student.
     */
    @Test
    public void testAddClass() {
        System.out.println("addClass");
        ArrayList<Student> students = new ArrayList();
        for(Student student : testData.students) {
            students.add(student);
        }
        
        CoursesGroup mata = testData.groups[0];
        CoursesGroup przyra = testData.groups[1];
        
        Course mataL = testData.courses[0];
        Course mataE = testData.courses[1];
        Course przyraL = testData.courses[2];
        
        mataL.addClasses(testData.classes[0],testData.classes[0].getLecturer());
        mataL.addClasses(testData.classes[3], testData.classes[3].getLecturer());
        mataL.addClasses(testData.classes[4], testData.classes[4].getLecturer());
        mataE.addClasses(testData.classes[1], testData.classes[1].getLecturer());
        przyraL.addClasses(testData.classes[2], testData.classes[2].getLecturer());
        
        mata.setMainCourse(mataL);
        mata.addPartialCourse(mataE);
        przyra.setMainCourse(przyraL);
        
        students.get(0).addCoursesGroupToSchedule(mata);
        students.get(0).addCoursesGroupToSchedule(przyra);
        students.get(1).addCoursesGroupToSchedule(mata);
        
        boolean expResult = true;
        //classes with mainCourse add to student
        boolean result = students.get(0).addClass(testData.classes[0]);
        assertEquals(expResult, result);
        
        
        // classes with partialCourse add to student
        result = students.get(1).addClass(testData.classes[1]);
        assertEquals(expResult, result);
        
        
        expResult = false;
        // add classes to student, schedule don't include these classes
        result = students.get(1).addClass(testData.classes[2]);
        assertEquals(expResult, result);
        
        // add classes to student, student has added these classes
        result = students.get(0).addClass(testData.classes[0]);
        assertEquals(expResult, result);
        
        // add classes to studet, student has classes with the same term
        result = students.get(0).addClass(testData.classes[1]);
        assertEquals(expResult, result);
        
        expResult = true;
        //add correct classes
        result = students.get(1).addClass(testData.classes[4]);
        assertEquals(expResult, result);

        expResult = false;
        // add classes to student, number of classes is equal to number of course from schedule
        result = students.get(1).addClass(testData.classes[3]);
        assertEquals(expResult, result);
        
        // add classes to student, no vacancies
        result = students.get(0).addClass(testData.classes[4]);
        assertEquals(expResult, result);

    }



    /**
     * Test of addCoursesGroupToSchedule method, of class Student.
     */
    @Test
    public void testAddCoursesGroupToSchedule() {
        System.out.println("addCoursesGroupToSchedule");
        Student student = testData.students[0];
        
        
        boolean expResult = true;
        boolean result = student.addCoursesGroupToSchedule(testData.groups[0]);
        assertEquals(expResult, result);

        result = student.addCoursesGroupToSchedule(testData.groups[1]);
        assertEquals(expResult, result);
        
        expResult = false;
        result = student.addCoursesGroupToSchedule(testData.groups[1]);
        assertEquals(expResult, result);
       
    }



    /**
     * Test of checkNumberOfClasses method, of class Student.
     */
    @Test
    public void testCheckNumberOfClasses() {
        System.out.println("checkNumberOfClasses");
        Student student = testData.students[0];
        CoursesGroup firstGroup = testData.groups[0];
        CoursesGroup secondGroup = testData.groups[1];
        
        firstGroup.setMainCourse(testData.courses[0]);
        secondGroup.setMainCourse(testData.courses[2]);
        
        student.addCoursesGroupToSchedule(firstGroup);
        student.addCoursesGroupToSchedule(secondGroup);
        
        ArrayList<Classes> classes = (ArrayList) student.getClasses();
        
        boolean expResult = true;
        boolean result = student.checkNumberOfClasses();
        assertEquals(expResult, result); //numberOfClasses = 0, numberOfCourses = 2
        
        classes.add(testData.classes[0]);
        result = student.checkNumberOfClasses(); //numberOfClasses = 1, numberOfCourse = 2
        assertEquals(expResult, result);
        
        classes.add(testData.classes[1]);
        expResult = false;
        result = student.checkNumberOfClasses(); //numberOfClasses = 2, numberOfCourse = 2
        assertEquals(expResult, result);
    }

    /**
     * Test of checkTerm method, of class Student.
     */
    @Test
    public void testCheckTerm() {
        System.out.println("checkTerm");
        
        Student student = testData.students[0];
        ArrayList<Classes> classes =(ArrayList) student.getClasses();
        classes.add(testData.classes[0]);
        
        Term term = testData.classes[1].getTerm();
        boolean expResult = false;
        boolean result = student.checkTerm(term);
        assertEquals(expResult, result);
        
        term = testData.classes[2].getTerm();
        expResult = true;
        result = student.checkTerm(term);
        assertEquals(expResult, result);
    }
    
}
