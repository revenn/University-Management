/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import util.ModelMapper;


/**
 *
 * @author patrykwitkowski
 */
public class FacadeJUnitTest {
    
    private static Facade facade;
    private ModelMapper modelMapper;
    public FacadeJUnitTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        facade = new Facade();
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        facade.clearFacultyList();
        facade.clearStudentList();
        modelMapper = new ModelMapper();
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void test_adding_not_existing_Faculty(){
        boolean result = facade.addFaculty(FacadeTestData.FacultyData[0]);
        assertTrue(result);
             
    }
    
    @Test
    public void test_adding_existing_Faculty(){
        facade.addFaculty(FacadeTestData.FacultyData[0]);
        
        boolean result = facade.addFaculty(FacadeTestData.FacultyData[0]);
        assertFalse(result);
             
    }
    
    @Test
    public void test_adding_not_existing_Student(){
        boolean result = facade.addStudent(FacadeTestData.StudentData[0], FacadeTestData.FieldOfStudyData[0]);
        assertTrue(result);
    }
    
    @Test
    public void test_adding_existing_Student(){
        facade.addStudent(FacadeTestData.StudentData[0], FacadeTestData.FieldOfStudyData[0]);
        
        boolean result = facade.addStudent(FacadeTestData.StudentData[0], FacadeTestData.FieldOfStudyData[0]);
        assertFalse(result);
    }
}
