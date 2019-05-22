/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import model.entity.Faculty;
import tests.TestData;
import model.entity.Hall;
import model.entity.Lecturer;
import model.entity.Student;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class FactoryTest {
    
    TestData testData;

    @Before
    public void SetUp() {
        testData = new TestData();
    }

    
    @Test
    public void testCreateHall() {
        System.out.println("createHall");
        Factory instance = new Factory();
        for(int i = 0; i < testData.hallData.length; i++) {
            Hall result = instance.createHall(testData.hallData[i]);
            assertEquals(result, testData.halls[i]);
        }
    }
    
    @Test
    public void testCreateLecturer() {
        System.out.println("createLecturer");
        Factory instance = new Factory();
        int testDataLength = testData.lecturerData.length;
        for (int i = 0; i < testDataLength; i++) {
            Lecturer result = instance.createLecturer(testData.lecturerData[i]);
            if (i < testDataLength - 1) {
                assertEquals(result, testData.lecturers[i]);
            }
        }
    }
    
    @Test
    public void testCreateFaculty() {
        System.out.println("createFaculty");
        
        Factory factory = new Factory();
        Faculty faculty = factory.createFaculty(FacadeTestData.FacultyData[0]);
        
        assertEquals("W4", faculty.getAcronym());
        assertEquals("Wydzial Elektorniki", faculty.getName());
        assertEquals("prof. dr hab. inż. Czesław Smutnicki", faculty.getDean());
    }
    
    @Test
    public void testCreateStudent() {
        System.out.println("createStudent");
        
        Factory factory = new Factory();
        Student student = factory.createStudent(FacadeTestData.StudentData[0]);
        
        assertEquals("218168", student.getIndexNumber());
        assertEquals("Jakub", student.getFirstName());
        assertEquals("Małyjasiak", student.getLastName());
        assertEquals("12345678987", student.getPersonalIdentityNumber());
        assertEquals("Address123", student.getAddress());
        assertEquals("m", student.getGender());
    }

}
