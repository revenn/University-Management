/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import model.dto.FieldOfStudyDTO;
import model.dto.StudentDTO;
import model.dto.FacultyDTO;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;

/**
 *
 * @author bye
 */
public class FacadeTestData {
    
    public static FacultyDTO FacultyData[] = new FacultyDTO[]{
        new FacultyDTO(
            null,
            "W4",
            "Wydzial Elektorniki",
            "prof. dr hab. inż. Czesław Smutnicki")
    };
    
    
    public static StudentDTO StudentData[] = new StudentDTO[]{
        new StudentDTO("218168",0 ,"Jakub","Małyjasiak","12345678987","Address123","m")
    };
    
    public static FieldOfStudyDTO FieldOfStudyData[] = new FieldOfStudyDTO[]{
        new FieldOfStudyDTO(
                null,
                "INF",
                "Informatyka",
                LevelOfStudy.ENGINEER,
                ModeOfStudy.FULL_TIME, 
                new FacultyDTO(null, "W4", "Wydzial Elektroniki", "Prof. dr hab. inż. Czesław Smutnicki"), 
                new ArrayList<>())  
    };
}
