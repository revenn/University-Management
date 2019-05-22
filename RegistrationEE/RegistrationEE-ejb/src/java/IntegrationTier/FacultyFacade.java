/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntegrationTier;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import model.entity.Faculty;

@Stateless
public class FacultyFacade extends AbstractFacade<Faculty> implements FacultyFacadeLocal {

    @PersistenceContext(unitName = "RegistrationEE-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FacultyFacade() {
        super(Faculty.class);
    }
    
    public Faculty[] getFaculties() {
        return findAll().toArray(new Faculty[0]);
    }
    
    
    public void addFaculties(List<Faculty> faculties) {
        faculties.forEach(faculty -> {
            if(faculty.getId() == null) {
                getEntityManager().persist(faculty);
            }
        });
    }  
}
