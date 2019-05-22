package model;
import model.entity.Hall;
import model.entity.Term;
import tests.TestData;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;


public class TermTest {

    private TestData testData;
    
    @Before
    public void SetUp(){
        testData = new TestData();
    }
    
    @Test
    public void testToString() {
        System.out.println("toString");
        String expResult = testData.termDataToString[0];
        
        for(int i = 0; i < testData.termData.length; i++) {
            Term instance = new Term(new Hall(testData.termData[i][0],testData.termData[i][1], 
                    Integer.parseInt(testData.termData[i][2])), testData.termData[i][3], testData.termData[i][4], 
                    Integer.parseInt(testData.termData[i][5]), Integer.parseInt(testData.termData[i][6]));
            String expResul = testData.termDataToString[i];
            String result = instance.toString();
            assertEquals(expResul, result);
        }
    }
     @Test
    public void testEquals() {
        System.out.println("equals");
        
        for(int i = 0; i < testData.terms.length; i++) {
            for(int j = 0; j < testData.terms.length; j++) {
                if((i == j)) {
                    assertTrue(testData.terms[i].equals(testData.terms[j]));
                }else {
                    assertFalse(testData.terms[i].equals(testData.terms[j]));
                }
                
            }
        }
    }
}
