/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemzarezervacijukarata.cs230.jpa_controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import sistemzarezervacijukarata.cs230.entities.Korisnik;
import sistemzarezervacijukarata.cs230.entities.Projekcija;
import sistemzarezervacijukarata.cs230.entities.Rezervacija;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.NonexistentEntityException;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.RollbackFailureException;

/**
 *
 * @author razvoj
 */
public class RezervacijaJpaController implements Serializable {

    public RezervacijaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rezervacija rezervacija) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Korisnik korisnikId = rezervacija.getKorisnikId();
            if (korisnikId != null) {
                korisnikId = em.getReference(korisnikId.getClass(), korisnikId.getId());
                rezervacija.setKorisnikId(korisnikId);
            }
            Projekcija projekcijaId = rezervacija.getProjekcijaId();
            if (projekcijaId != null) {
                projekcijaId = em.getReference(projekcijaId.getClass(), projekcijaId.getId());
                rezervacija.setProjekcijaId(projekcijaId);
            }
            em.persist(rezervacija);
            if (korisnikId != null) {
                korisnikId.getRezervacijaList().add(rezervacija);
                korisnikId = em.merge(korisnikId);
            }
            if (projekcijaId != null) {
                projekcijaId.getRezervacijaList().add(rezervacija);
                projekcijaId = em.merge(projekcijaId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rezervacija rezervacija) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Rezervacija persistentRezervacija = em.find(Rezervacija.class, rezervacija.getId());
            Korisnik korisnikIdOld = persistentRezervacija.getKorisnikId();
            Korisnik korisnikIdNew = rezervacija.getKorisnikId();
            Projekcija projekcijaIdOld = persistentRezervacija.getProjekcijaId();
            Projekcija projekcijaIdNew = rezervacija.getProjekcijaId();
            if (korisnikIdNew != null) {
                korisnikIdNew = em.getReference(korisnikIdNew.getClass(), korisnikIdNew.getId());
                rezervacija.setKorisnikId(korisnikIdNew);
            }
            if (projekcijaIdNew != null) {
                projekcijaIdNew = em.getReference(projekcijaIdNew.getClass(), projekcijaIdNew.getId());
                rezervacija.setProjekcijaId(projekcijaIdNew);
            }
            rezervacija = em.merge(rezervacija);
            if (korisnikIdOld != null && !korisnikIdOld.equals(korisnikIdNew)) {
                korisnikIdOld.getRezervacijaList().remove(rezervacija);
                korisnikIdOld = em.merge(korisnikIdOld);
            }
            if (korisnikIdNew != null && !korisnikIdNew.equals(korisnikIdOld)) {
                korisnikIdNew.getRezervacijaList().add(rezervacija);
                korisnikIdNew = em.merge(korisnikIdNew);
            }
            if (projekcijaIdOld != null && !projekcijaIdOld.equals(projekcijaIdNew)) {
                projekcijaIdOld.getRezervacijaList().remove(rezervacija);
                projekcijaIdOld = em.merge(projekcijaIdOld);
            }
            if (projekcijaIdNew != null && !projekcijaIdNew.equals(projekcijaIdOld)) {
                projekcijaIdNew.getRezervacijaList().add(rezervacija);
                projekcijaIdNew = em.merge(projekcijaIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rezervacija.getId();
                if (findRezervacija(id) == null) {
                    throw new NonexistentEntityException("The rezervacija with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Rezervacija rezervacija;
            try {
                rezervacija = em.getReference(Rezervacija.class, id);
                rezervacija.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rezervacija with id " + id + " no longer exists.", enfe);
            }
            Korisnik korisnikId = rezervacija.getKorisnikId();
            if (korisnikId != null) {
                korisnikId.getRezervacijaList().remove(rezervacija);
                korisnikId = em.merge(korisnikId);
            }
            Projekcija projekcijaId = rezervacija.getProjekcijaId();
            if (projekcijaId != null) {
                projekcijaId.getRezervacijaList().remove(rezervacija);
                projekcijaId = em.merge(projekcijaId);
            }
            em.remove(rezervacija);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rezervacija> findRezervacijaEntities() {
        return findRezervacijaEntities(true, -1, -1);
    }

    public List<Rezervacija> findRezervacijaEntities(int maxResults, int firstResult) {
        return findRezervacijaEntities(false, maxResults, firstResult);
    }

    private List<Rezervacija> findRezervacijaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rezervacija.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rezervacija findRezervacija(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rezervacija.class, id);
        } finally {
            em.close();
        }
    }

    public int getRezervacijaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rezervacija> rt = cq.from(Rezervacija.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
