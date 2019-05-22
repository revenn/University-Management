/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import model.entity.Hall;
import tests.TestData;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class HallTest {
    
    TestData testData;

    @Before
    public void SetUp(){
        testData = new TestData();
    }


    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = testData.hallDataToString[0];
        
        for(int i = 0; i < testData.hallData.length; i++) {
            Hall instance = new Hall(testData.hallData[i][0],testData.hallData[i][1], Integer.parseInt(testData.hallData[i][2]));
            String expResul = testData.hallDataToString[i];
            String result = instance.toString();
            assertEquals(expResul, result);
        }
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        
        for(int i = 0; i < testData.halls.length; i++) {
            for(int j = 0; j < testData.halls.length; j++) {
                if((i == j) || (i == 0 && j == 1) || (i == 1 && j == 0)) {
                    assertTrue(testData.halls[i].equals(testData.halls[j]));
                }else {
                    assertFalse(testData.halls[i].equals(testData.halls[j]));
                }
                
            }
        }
    }
    
}
