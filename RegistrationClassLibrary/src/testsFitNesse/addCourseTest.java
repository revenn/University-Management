package testsFitNesse;

import fit.ColumnFixture;
import model.entity.CoursesGroup;

public class addCourseTest extends ColumnFixture {

    String[] data;
    String[] courseGroupData;

    public boolean addCourse(){
        Object[] dataForTest = new Object[]{
                data[0],
                data[1],
                new Object(),
                new CoursesGroup(
                        courseGroupData[0],
                        Integer.parseInt(courseGroupData[1]),
                        Integer.parseInt(courseGroupData[2])
                )
        };

        return SetUp.facade.addCourse(dataForTest);
    }
}
