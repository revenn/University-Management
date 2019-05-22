/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntegrationTier;

import java.util.List;
import javax.ejb.Local;
import model.entity.FieldOfStudy;

@Local
public interface FieldOfStudyFacadeLocal {

    void addFieldsOfStudy(List<FieldOfStudy> fieldsOfStudy);
    
    void create(FieldOfStudy fieldOfStudy);

    void edit(FieldOfStudy fieldOfStudy);

    void remove(FieldOfStudy fieldOfStudy);

    FieldOfStudy find(Object id);

    List<FieldOfStudy> findAll();

    List<FieldOfStudy> findRange(int[] range);

    int count();
    
}
