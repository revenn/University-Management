/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import tests.TestData;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Bartek
 */
public class LecturerTest {
    
    TestData testData;
    
    @Before
    public void SetUp(){
        testData = new TestData();
    }

    

    /**
     * Test of equals method, of class Lecturer.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        
        for(int i = 0; i < testData.lecturers.length; i++) {
            for(int j = 0; j < testData.lecturers.length; j++) {
                if(i == j || (i == 0 && j == 7) || (i == 7 && j == 0)) {
                    assertTrue(testData.lecturers[i].equals(testData.lecturers[j]));
                } else {
                    assertFalse(testData.lecturers[i].equals(testData.lecturers[j]));
                }
            }
        }
    }


    
    @Test
    public void testToString() {
        System.out.println("toString");
        
        int testDataLength = testData.lecturerDataToString.length;
        for(int i = 0; i < testDataLength; i++) {
            String expResult = testData.lecturerDataToString[i].toString();
            String result = testData.lecturers[i].toString();
            assertEquals(expResult, result);
        }
    }
     
}
