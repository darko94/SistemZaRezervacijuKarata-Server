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
import sistemzarezervacijukarata.cs230.entities.Sala;
import sistemzarezervacijukarata.cs230.entities.Film;
import sistemzarezervacijukarata.cs230.entities.Rezervacija;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import sistemzarezervacijukarata.cs230.entities.Projekcija;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.NonexistentEntityException;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.RollbackFailureException;

/**
 *
 * @author razvoj
 */
public class ProjekcijaJpaController implements Serializable {

    public ProjekcijaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Projekcija projekcija) throws RollbackFailureException, Exception {
        if (projekcija.getRezervacijaList() == null) {
            projekcija.setRezervacijaList(new ArrayList<Rezervacija>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sala salaId = projekcija.getSalaId();
            if (salaId != null) {
                salaId = em.getReference(salaId.getClass(), salaId.getId());
                projekcija.setSalaId(salaId);
            }
            Film filmId = projekcija.getFilmId();
            if (filmId != null) {
                filmId = em.getReference(filmId.getClass(), filmId.getId());
                projekcija.setFilmId(filmId);
            }
            List<Rezervacija> attachedRezervacijaList = new ArrayList<Rezervacija>();
            for (Rezervacija rezervacijaListRezervacijaToAttach : projekcija.getRezervacijaList()) {
                rezervacijaListRezervacijaToAttach = em.getReference(rezervacijaListRezervacijaToAttach.getClass(), rezervacijaListRezervacijaToAttach.getId());
                attachedRezervacijaList.add(rezervacijaListRezervacijaToAttach);
            }
            projekcija.setRezervacijaList(attachedRezervacijaList);
            em.persist(projekcija);
            if (salaId != null) {
                salaId.getProjekcijaList().add(projekcija);
                salaId = em.merge(salaId);
            }
            if (filmId != null) {
                filmId.getProjekcijaList().add(projekcija);
                filmId = em.merge(filmId);
            }
            for (Rezervacija rezervacijaListRezervacija : projekcija.getRezervacijaList()) {
                Projekcija oldProjekcijaIdOfRezervacijaListRezervacija = rezervacijaListRezervacija.getProjekcijaId();
                rezervacijaListRezervacija.setProjekcijaId(projekcija);
                rezervacijaListRezervacija = em.merge(rezervacijaListRezervacija);
                if (oldProjekcijaIdOfRezervacijaListRezervacija != null) {
                    oldProjekcijaIdOfRezervacijaListRezervacija.getRezervacijaList().remove(rezervacijaListRezervacija);
                    oldProjekcijaIdOfRezervacijaListRezervacija = em.merge(oldProjekcijaIdOfRezervacijaListRezervacija);
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

    public void edit(Projekcija projekcija) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Projekcija persistentProjekcija = em.find(Projekcija.class, projekcija.getId());
            Sala salaIdOld = persistentProjekcija.getSalaId();
            Sala salaIdNew = projekcija.getSalaId();
            Film filmIdOld = persistentProjekcija.getFilmId();
            Film filmIdNew = projekcija.getFilmId();
            List<Rezervacija> rezervacijaListOld = persistentProjekcija.getRezervacijaList();
            List<Rezervacija> rezervacijaListNew = projekcija.getRezervacijaList();
            if (salaIdNew != null) {
                salaIdNew = em.getReference(salaIdNew.getClass(), salaIdNew.getId());
                projekcija.setSalaId(salaIdNew);
            }
            if (filmIdNew != null) {
                filmIdNew = em.getReference(filmIdNew.getClass(), filmIdNew.getId());
                projekcija.setFilmId(filmIdNew);
            }
            List<Rezervacija> attachedRezervacijaListNew = new ArrayList<Rezervacija>();
            for (Rezervacija rezervacijaListNewRezervacijaToAttach : rezervacijaListNew) {
                rezervacijaListNewRezervacijaToAttach = em.getReference(rezervacijaListNewRezervacijaToAttach.getClass(), rezervacijaListNewRezervacijaToAttach.getId());
                attachedRezervacijaListNew.add(rezervacijaListNewRezervacijaToAttach);
            }
            rezervacijaListNew = attachedRezervacijaListNew;
            projekcija.setRezervacijaList(rezervacijaListNew);
            projekcija = em.merge(projekcija);
            if (salaIdOld != null && !salaIdOld.equals(salaIdNew)) {
                salaIdOld.getProjekcijaList().remove(projekcija);
                salaIdOld = em.merge(salaIdOld);
            }
            if (salaIdNew != null && !salaIdNew.equals(salaIdOld)) {
                salaIdNew.getProjekcijaList().add(projekcija);
                salaIdNew = em.merge(salaIdNew);
            }
            if (filmIdOld != null && !filmIdOld.equals(filmIdNew)) {
                filmIdOld.getProjekcijaList().remove(projekcija);
                filmIdOld = em.merge(filmIdOld);
            }
            if (filmIdNew != null && !filmIdNew.equals(filmIdOld)) {
                filmIdNew.getProjekcijaList().add(projekcija);
                filmIdNew = em.merge(filmIdNew);
            }
            for (Rezervacija rezervacijaListOldRezervacija : rezervacijaListOld) {
                if (!rezervacijaListNew.contains(rezervacijaListOldRezervacija)) {
                    rezervacijaListOldRezervacija.setProjekcijaId(null);
                    rezervacijaListOldRezervacija = em.merge(rezervacijaListOldRezervacija);
                }
            }
            for (Rezervacija rezervacijaListNewRezervacija : rezervacijaListNew) {
                if (!rezervacijaListOld.contains(rezervacijaListNewRezervacija)) {
                    Projekcija oldProjekcijaIdOfRezervacijaListNewRezervacija = rezervacijaListNewRezervacija.getProjekcijaId();
                    rezervacijaListNewRezervacija.setProjekcijaId(projekcija);
                    rezervacijaListNewRezervacija = em.merge(rezervacijaListNewRezervacija);
                    if (oldProjekcijaIdOfRezervacijaListNewRezervacija != null && !oldProjekcijaIdOfRezervacijaListNewRezervacija.equals(projekcija)) {
                        oldProjekcijaIdOfRezervacijaListNewRezervacija.getRezervacijaList().remove(rezervacijaListNewRezervacija);
                        oldProjekcijaIdOfRezervacijaListNewRezervacija = em.merge(oldProjekcijaIdOfRezervacijaListNewRezervacija);
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
                Integer id = projekcija.getId();
                if (findProjekcija(id) == null) {
                    throw new NonexistentEntityException("The projekcija with id " + id + " no longer exists.");
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
            Projekcija projekcija;
            try {
                projekcija = em.getReference(Projekcija.class, id);
                projekcija.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projekcija with id " + id + " no longer exists.", enfe);
            }
            Sala salaId = projekcija.getSalaId();
            if (salaId != null) {
                salaId.getProjekcijaList().remove(projekcija);
                salaId = em.merge(salaId);
            }
            Film filmId = projekcija.getFilmId();
            if (filmId != null) {
                filmId.getProjekcijaList().remove(projekcija);
                filmId = em.merge(filmId);
            }
            List<Rezervacija> rezervacijaList = projekcija.getRezervacijaList();
            for (Rezervacija rezervacijaListRezervacija : rezervacijaList) {
                rezervacijaListRezervacija.setProjekcijaId(null);
                rezervacijaListRezervacija = em.merge(rezervacijaListRezervacija);
            }
            em.remove(projekcija);
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

    public List<Projekcija> findProjekcijaEntities() {
        return findProjekcijaEntities(true, -1, -1);
    }

    public List<Projekcija> findProjekcijaEntities(int maxResults, int firstResult) {
        return findProjekcijaEntities(false, maxResults, firstResult);
    }

    private List<Projekcija> findProjekcijaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projekcija.class));
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

    public Projekcija findProjekcija(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projekcija.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjekcijaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projekcija> rt = cq.from(Projekcija.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
