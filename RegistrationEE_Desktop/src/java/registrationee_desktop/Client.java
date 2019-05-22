package registrationee_desktop;

import BusinessTier.Facade_ejbRemote;
import javax.ejb.EJB;
import view.util.PanelUtil;

public class Client {

    @EJB
    private static Facade_ejbRemote facade_ejb;

    public static Facade_ejbRemote getFacade(){
        return facade_ejb;
    }


    public static void main(String[] args) {
        facade_ejb.initializeWithSampleData();
        java.awt.EventQueue.invokeLater(PanelUtil::createAndShowGUI);
    }
}
