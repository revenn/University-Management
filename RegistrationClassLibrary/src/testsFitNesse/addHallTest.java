
package testsFitNesse;

import fit.ColumnFixture;
import model.entity.Hall;
import util.ModelMapper;

public class addHallTest extends ColumnFixture {
    String data[];
    
    public boolean addHall() {
        ModelMapper modelMapper = new ModelMapper();
        Hall hall = new Hall(
                data[0],
                data[1],
                Integer.parseInt(data[2])
        );
        boolean result = SetUp.facade.addHall(modelMapper.createDTO(hall));
        return result;
    }
}
