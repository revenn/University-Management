package testsFitNesse;

import fit.ColumnFixture;
import model.entity.Lecturer;
import util.ModelMapper;

public class addLecturerTest extends ColumnFixture {
    String data[];
    
    public boolean addLecturer() {
        ModelMapper modelMapper = new ModelMapper();
        Lecturer lecturer = new Lecturer(
                data[0],
                data[1],
                data[2],
                data[3],
                data[4],
                data[5]);
        
        boolean result = SetUp.facade.addLecturer(modelMapper.createDTO(lecturer));
        return result;
    }
}