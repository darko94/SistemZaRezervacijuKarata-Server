/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemzarezervacijukarata.cs230.facade;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import sistemzarezervacijukarata.cs230.entities.Rezervacija;

/**
 *
 * @author razvoj
 */
@Stateless
public class RezervacijaFacade extends AbstractFacade<Rezervacija> {

    @PersistenceContext(unitName = "SistemZaRezervacijuKarata-CS230PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RezervacijaFacade() {
        super(Rezervacija.class);
    }
    
}
