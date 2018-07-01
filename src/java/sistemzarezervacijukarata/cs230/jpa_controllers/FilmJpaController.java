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
import sistemzarezervacijukarata.cs230.entities.Film;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.NonexistentEntityException;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.RollbackFailureException;

/**
 *
 * @author razvoj
 */
public class FilmJpaController implements Serializable {

    public FilmJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Film film) throws RollbackFailureException, Exception {
        if (film.getProjekcijaList() == null) {
            film.setProjekcijaList(new ArrayList<Projekcija>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Projekcija> attachedProjekcijaList = new ArrayList<Projekcija>();
            for (Projekcija projekcijaListProjekcijaToAttach : film.getProjekcijaList()) {
                projekcijaListProjekcijaToAttach = em.getReference(projekcijaListProjekcijaToAttach.getClass(), projekcijaListProjekcijaToAttach.getId());
                attachedProjekcijaList.add(projekcijaListProjekcijaToAttach);
            }
            film.setProjekcijaList(attachedProjekcijaList);
            em.persist(film);
            for (Projekcija projekcijaListProjekcija : film.getProjekcijaList()) {
                Film oldFilmIdOfProjekcijaListProjekcija = projekcijaListProjekcija.getFilmId();
                projekcijaListProjekcija.setFilmId(film);
                projekcijaListProjekcija = em.merge(projekcijaListProjekcija);
                if (oldFilmIdOfProjekcijaListProjekcija != null) {
                    oldFilmIdOfProjekcijaListProjekcija.getProjekcijaList().remove(projekcijaListProjekcija);
                    oldFilmIdOfProjekcijaListProjekcija = em.merge(oldFilmIdOfProjekcijaListProjekcija);
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

    public void edit(Film film) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Film persistentFilm = em.find(Film.class, film.getId());
            List<Projekcija> projekcijaListOld = persistentFilm.getProjekcijaList();
            List<Projekcija> projekcijaListNew = film.getProjekcijaList();
            List<Projekcija> attachedProjekcijaListNew = new ArrayList<Projekcija>();
            for (Projekcija projekcijaListNewProjekcijaToAttach : projekcijaListNew) {
                projekcijaListNewProjekcijaToAttach = em.getReference(projekcijaListNewProjekcijaToAttach.getClass(), projekcijaListNewProjekcijaToAttach.getId());
                attachedProjekcijaListNew.add(projekcijaListNewProjekcijaToAttach);
            }
            projekcijaListNew = attachedProjekcijaListNew;
            film.setProjekcijaList(projekcijaListNew);
            film = em.merge(film);
            for (Projekcija projekcijaListOldProjekcija : projekcijaListOld) {
                if (!projekcijaListNew.contains(projekcijaListOldProjekcija)) {
                    projekcijaListOldProjekcija.setFilmId(null);
                    projekcijaListOldProjekcija = em.merge(projekcijaListOldProjekcija);
                }
            }
            for (Projekcija projekcijaListNewProjekcija : projekcijaListNew) {
                if (!projekcijaListOld.contains(projekcijaListNewProjekcija)) {
                    Film oldFilmIdOfProjekcijaListNewProjekcija = projekcijaListNewProjekcija.getFilmId();
                    projekcijaListNewProjekcija.setFilmId(film);
                    projekcijaListNewProjekcija = em.merge(projekcijaListNewProjekcija);
                    if (oldFilmIdOfProjekcijaListNewProjekcija != null && !oldFilmIdOfProjekcijaListNewProjekcija.equals(film)) {
                        oldFilmIdOfProjekcijaListNewProjekcija.getProjekcijaList().remove(projekcijaListNewProjekcija);
                        oldFilmIdOfProjekcijaListNewProjekcija = em.merge(oldFilmIdOfProjekcijaListNewProjekcija);
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
                Integer id = film.getId();
                if (findFilm(id) == null) {
                    throw new NonexistentEntityException("The film with id " + id + " no longer exists.");
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
            Film film;
            try {
                film = em.getReference(Film.class, id);
                film.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The film with id " + id + " no longer exists.", enfe);
            }
            List<Projekcija> projekcijaList = film.getProjekcijaList();
            for (Projekcija projekcijaListProjekcija : projekcijaList) {
                projekcijaListProjekcija.setFilmId(null);
                projekcijaListProjekcija = em.merge(projekcijaListProjekcija);
            }
            em.remove(film);
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

    public List<Film> findFilmEntities() {
        return findFilmEntities(true, -1, -1);
    }

    public List<Film> findFilmEntities(int maxResults, int firstResult) {
        return findFilmEntities(false, maxResults, firstResult);
    }

    private List<Film> findFilmEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Film.class));
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

    public Film findFilm(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Film.class, id);
        } finally {
            em.close();
        }
    }

    public int getFilmCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Film> rt = cq.from(Film.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
