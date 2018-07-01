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
import sistemzarezervacijukarata.cs230.entities.Projekcija;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import sistemzarezervacijukarata.cs230.entities.Sala;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.NonexistentEntityException;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.RollbackFailureException;

/**
 *
 * @author razvoj
 */
public class SalaJpaController implements Serializable {

    public SalaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Sala sala) throws RollbackFailureException, Exception {
        if (sala.getProjekcijaList() == null) {
            sala.setProjekcijaList(new ArrayList<Projekcija>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Projekcija> attachedProjekcijaList = new ArrayList<Projekcija>();
            for (Projekcija projekcijaListProjekcijaToAttach : sala.getProjekcijaList()) {
                projekcijaListProjekcijaToAttach = em.getReference(projekcijaListProjekcijaToAttach.getClass(), projekcijaListProjekcijaToAttach.getId());
                attachedProjekcijaList.add(projekcijaListProjekcijaToAttach);
            }
            sala.setProjekcijaList(attachedProjekcijaList);
            em.persist(sala);
            for (Projekcija projekcijaListProjekcija : sala.getProjekcijaList()) {
                Sala oldSalaIdOfProjekcijaListProjekcija = projekcijaListProjekcija.getSalaId();
                projekcijaListProjekcija.setSalaId(sala);
                projekcijaListProjekcija = em.merge(projekcijaListProjekcija);
                if (oldSalaIdOfProjekcijaListProjekcija != null) {
                    oldSalaIdOfProjekcijaListProjekcija.getProjekcijaList().remove(projekcijaListProjekcija);
                    oldSalaIdOfProjekcijaListProjekcija = em.merge(oldSalaIdOfProjekcijaListProjekcija);
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

    public void edit(Sala sala) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Sala persistentSala = em.find(Sala.class, sala.getId());
            List<Projekcija> projekcijaListOld = persistentSala.getProjekcijaList();
            List<Projekcija> projekcijaListNew = sala.getProjekcijaList();
            List<Projekcija> attachedProjekcijaListNew = new ArrayList<Projekcija>();
            for (Projekcija projekcijaListNewProjekcijaToAttach : projekcijaListNew) {
                projekcijaListNewProjekcijaToAttach = em.getReference(projekcijaListNewProjekcijaToAttach.getClass(), projekcijaListNewProjekcijaToAttach.getId());
                attachedProjekcijaListNew.add(projekcijaListNewProjekcijaToAttach);
            }
            projekcijaListNew = attachedProjekcijaListNew;
            sala.setProjekcijaList(projekcijaListNew);
            sala = em.merge(sala);
            for (Projekcija projekcijaListOldProjekcija : projekcijaListOld) {
                if (!projekcijaListNew.contains(projekcijaListOldProjekcija)) {
                    projekcijaListOldProjekcija.setSalaId(null);
                    projekcijaListOldProjekcija = em.merge(projekcijaListOldProjekcija);
                }
            }
            for (Projekcija projekcijaListNewProjekcija : projekcijaListNew) {
                if (!projekcijaListOld.contains(projekcijaListNewProjekcija)) {
                    Sala oldSalaIdOfProjekcijaListNewProjekcija = projekcijaListNewProjekcija.getSalaId();
                    projekcijaListNewProjekcija.setSalaId(sala);
                    projekcijaListNewProjekcija = em.merge(projekcijaListNewProjekcija);
                    if (oldSalaIdOfProjekcijaListNewProjekcija != null && !oldSalaIdOfProjekcijaListNewProjekcija.equals(sala)) {
                        oldSalaIdOfProjekcijaListNewProjekcija.getProjekcijaList().remove(projekcijaListNewProjekcija);
                        oldSalaIdOfProjekcijaListNewProjekcija = em.merge(oldSalaIdOfProjekcijaListNewProjekcija);
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
                Integer id = sala.getId();
                if (findSala(id) == null) {
                    throw new NonexistentEntityException("The sala with id " + id + " no longer exists.");
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
            Sala sala;
            try {
                sala = em.getReference(Sala.class, id);
                sala.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The sala with id " + id + " no longer exists.", enfe);
            }
            List<Projekcija> projekcijaList = sala.getProjekcijaList();
            for (Projekcija projekcijaListProjekcija : projekcijaList) {
                projekcijaListProjekcija.setSalaId(null);
                projekcijaListProjekcija = em.merge(projekcijaListProjekcija);
            }
            em.remove(sala);
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

    public List<Sala> findSalaEntities() {
        return findSalaEntities(true, -1, -1);
    }

    public List<Sala> findSalaEntities(int maxResults, int firstResult) {
        return findSalaEntities(false, maxResults, firstResult);
    }

    private List<Sala> findSalaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Sala.class));
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

    public Sala findSala(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Sala.class, id);
        } finally {
            em.close();
        }
    }

    public int getSalaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Sala> rt = cq.from(Sala.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
