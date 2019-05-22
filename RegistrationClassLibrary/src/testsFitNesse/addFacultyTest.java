/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsFitNesse;

import fit.ColumnFixture;
import model.dto.FacultyDTO;
/**
 *
 * @author marcinokroy
 */
public class addFacultyTest extends ColumnFixture {
    FacultyDTO faculty;

    public boolean addFaculty() {
        boolean result = SetUp.facade.addFaculty(faculty);
        return result;
    }
}
