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
import model.entity.FieldOfStudy;

@Stateless
public class FieldOfStudyFacade extends AbstractFacade<FieldOfStudy> implements FieldOfStudyFacadeLocal {

    @PersistenceContext(unitName = "RegistrationEE-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FieldOfStudyFacade() {
        super(FieldOfStudy.class);
    }
    
    @Override
    public void addFieldsOfStudy(List<FieldOfStudy> fieldsOfStudy) {
        fieldsOfStudy.forEach(fos -> {
            if(fos.getId() == null) {
                getEntityManager().persist(fos);
            }
        });
    }
    
}
