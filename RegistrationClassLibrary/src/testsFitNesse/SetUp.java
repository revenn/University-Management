
package testsFitNesse;

import fit.Fixture;
import controller.Facade;

public class SetUp extends Fixture {
    static Facade facade;
    
    public SetUp() {
        facade = new Facade();
    }
    
}
