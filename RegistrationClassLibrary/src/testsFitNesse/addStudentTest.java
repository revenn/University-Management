/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testsFitNesse;
import fit.ColumnFixture;
import model.entity.Faculty;
import model.entity.FieldOfStudy;
import model.entity.Student;
import model.misc.LevelOfStudy;
import model.misc.ModeOfStudy;
import util.ModelMapper;

public class addStudentTest extends ColumnFixture {
    String data[];
    FieldOfStudy fieldOfStudy;
    Faculty faculty;
    
    public boolean addStudent() {
        faculty = new Faculty("W4", "Wydzial Elektroniki", "Prof. dr hab. inż. Czesław Smutnicki");
        fieldOfStudy = new FieldOfStudy("INF", 
                "Informatyka",
                LevelOfStudy.ENGINEER,
                ModeOfStudy.FULL_TIME,
                faculty
        );
        ModelMapper modelMapper = new ModelMapper();
        Student student = new Student(data[0], data[1], data[2], data[3], data[4], data[5]);
        boolean result = SetUp.facade.addStudent(modelMapper.createDTO(student), modelMapper.createDTO(fieldOfStudy));
        return result;
    }
}
