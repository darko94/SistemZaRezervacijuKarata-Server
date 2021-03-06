/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemzarezervacijukarata.cs230.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sistemzarezervacijukarata.cs230.entities.Projekcija;

/**
 *
 * @author razvoj
 */
@Stateless
public class ProjekcijaFacade extends AbstractFacade<Projekcija> {

    @PersistenceContext(unitName = "SistemZaRezervacijuKarata-CS230PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjekcijaFacade() {
        super(Projekcija.class);
    }
    
}
