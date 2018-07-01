/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemzarezervacijukarata.cs230.jpa_controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sistemzarezervacijukarata.cs230.entities.Rezervacija;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import sistemzarezervacijukarata.cs230.entities.Korisnik;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.NonexistentEntityException;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.RollbackFailureException;

/**
 *
 * @author razvoj
 */
public class KorisnikJpaController implements Serializable {

    public KorisnikJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Korisnik korisnik) throws RollbackFailureException, Exception {
        if (korisnik.getRezervacijaList() == null) {
            korisnik.setRezervacijaList(new ArrayList<Rezervacija>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Rezervacija> attachedRezervacijaList = new ArrayList<Rezervacija>();
            for (Rezervacija rezervacijaListRezervacijaToAttach : korisnik.getRezervacijaList()) {
                rezervacijaListRezervacijaToAttach = em.getReference(rezervacijaListRezervacijaToAttach.getClass(), rezervacijaListRezervacijaToAttach.getId());
                attachedRezervacijaList.add(rezervacijaListRezervacijaToAttach);
            }
            korisnik.setRezervacijaList(attachedRezervacijaList);
            em.persist(korisnik);
            for (Rezervacija rezervacijaListRezervacija : korisnik.getRezervacijaList()) {
                Korisnik oldKorisnikIdOfRezervacijaListRezervacija = rezervacijaListRezervacija.getKorisnikId();
                rezervacijaListRezervacija.setKorisnikId(korisnik);
                rezervacijaListRezervacija = em.merge(rezervacijaListRezervacija);
                if (oldKorisnikIdOfRezervacijaListRezervacija != null) {
                    oldKorisnikIdOfRezervacijaListRezervacija.getRezervacijaList().remove(rezervacijaListRezervacija);
                    oldKorisnikIdOfRezervacijaListRezervacija = em.merge(oldKorisnikIdOfRezervacijaListRezervacija);
                }
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

    public void edit(Korisnik korisnik) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Korisnik persistentKorisnik = em.find(Korisnik.class, korisnik.getId());
            List<Rezervacija> rezervacijaListOld = persistentKorisnik.getRezervacijaList();
            List<Rezervacija> rezervacijaListNew = korisnik.getRezervacijaList();
            List<Rezervacija> attachedRezervacijaListNew = new ArrayList<Rezervacija>();
            for (Rezervacija rezervacijaListNewRezervacijaToAttach : rezervacijaListNew) {
                rezervacijaListNewRezervacijaToAttach = em.getReference(rezervacijaListNewRezervacijaToAttach.getClass(), rezervacijaListNewRezervacijaToAttach.getId());
                attachedRezervacijaListNew.add(rezervacijaListNewRezervacijaToAttach);
            }
            rezervacijaListNew = attachedRezervacijaListNew;
            korisnik.setRezervacijaList(rezervacijaListNew);
            korisnik = em.merge(korisnik);
            for (Rezervacija rezervacijaListOldRezervacija : rezervacijaListOld) {
                if (!rezervacijaListNew.contains(rezervacijaListOldRezervacija)) {
                    rezervacijaListOldRezervacija.setKorisnikId(null);
                    rezervacijaListOldRezervacija = em.merge(rezervacijaListOldRezervacija);
                }
            }
            for (Rezervacija rezervacijaListNewRezervacija : rezervacijaListNew) {
                if (!rezervacijaListOld.contains(rezervacijaListNewRezervacija)) {
                    Korisnik oldKorisnikIdOfRezervacijaListNewRezervacija = rezervacijaListNewRezervacija.getKorisnikId();
                    rezervacijaListNewRezervacija.setKorisnikId(korisnik);
                    rezervacijaListNewRezervacija = em.merge(rezervacijaListNewRezervacija);
                    if (oldKorisnikIdOfRezervacijaListNewRezervacija != null && !oldKorisnikIdOfRezervacijaListNewRezervacija.equals(korisnik)) {
                        oldKorisnikIdOfRezervacijaListNewRezervacija.getRezervacijaList().remove(rezervacijaListNewRezervacija);
                        oldKorisnikIdOfRezervacijaListNewRezervacija = em.merge(oldKorisnikIdOfRezervacijaListNewRezervacija);
                    }
                }
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
                Integer id = korisnik.getId();
                if (findKorisnik(id) == null) {
                    throw new NonexistentEntityException("The korisnik with id " + id + " no longer exists.");
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
            Korisnik korisnik;
            try {
                korisnik = em.getReference(Korisnik.class, id);
                korisnik.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The korisnik with id " + id + " no longer exists.", enfe);
            }
            List<Rezervacija> rezervacijaList = korisnik.getRezervacijaList();
            for (Rezervacija rezervacijaListRezervacija : rezervacijaList) {
                rezervacijaListRezervacija.setKorisnikId(null);
                rezervacijaListRezervacija = em.merge(rezervacijaListRezervacija);
            }
            em.remove(korisnik);
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

    public List<Korisnik> findKorisnikEntities() {
        return findKorisnikEntities(true, -1, -1);
    }

    public List<Korisnik> findKorisnikEntities(int maxResults, int firstResult) {
        return findKorisnikEntities(false, maxResults, firstResult);
    }

    private List<Korisnik> findKorisnikEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Korisnik.class));
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

    public Korisnik findKorisnik(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Korisnik.class, id);
        } finally {
            em.close();
        }
    }

    public int getKorisnikCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Korisnik> rt = cq.from(Korisnik.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
