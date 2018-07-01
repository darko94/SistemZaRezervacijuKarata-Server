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
import sistemzarezervacijukarata.cs230.entities.UserRoles;
import sistemzarezervacijukarata.cs230.entities.Users;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.NonexistentEntityException;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.RollbackFailureException;

/**
 *
 * @author razvoj
 */
public class UserRolesJpaController implements Serializable {

    public UserRolesJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(UserRoles userRoles) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Users username = userRoles.getUsername();
            if (username != null) {
                username = em.getReference(username.getClass(), username.getUsername());
                userRoles.setUsername(username);
            }
            em.persist(userRoles);
            if (username != null) {
                username.getUserRolesList().add(userRoles);
                username = em.merge(username);
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

    public void edit(UserRoles userRoles) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            UserRoles persistentUserRoles = em.find(UserRoles.class, userRoles.getUserRoleId());
            Users usernameOld = persistentUserRoles.getUsername();
            Users usernameNew = userRoles.getUsername();
            if (usernameNew != null) {
                usernameNew = em.getReference(usernameNew.getClass(), usernameNew.getUsername());
                userRoles.setUsername(usernameNew);
            }
            userRoles = em.merge(userRoles);
            if (usernameOld != null && !usernameOld.equals(usernameNew)) {
                usernameOld.getUserRolesList().remove(userRoles);
                usernameOld = em.merge(usernameOld);
            }
            if (usernameNew != null && !usernameNew.equals(usernameOld)) {
                usernameNew.getUserRolesList().add(userRoles);
                usernameNew = em.merge(usernameNew);
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
                Integer id = userRoles.getUserRoleId();
                if (findUserRoles(id) == null) {
                    throw new NonexistentEntityException("The userRoles with id " + id + " no longer exists.");
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
            UserRoles userRoles;
            try {
                userRoles = em.getReference(UserRoles.class, id);
                userRoles.getUserRoleId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The userRoles with id " + id + " no longer exists.", enfe);
            }
            Users username = userRoles.getUsername();
            if (username != null) {
                username.getUserRolesList().remove(userRoles);
                username = em.merge(username);
            }
            em.remove(userRoles);
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

    public List<UserRoles> findUserRolesEntities() {
        return findUserRolesEntities(true, -1, -1);
    }

    public List<UserRoles> findUserRolesEntities(int maxResults, int firstResult) {
        return findUserRolesEntities(false, maxResults, firstResult);
    }

    private List<UserRoles> findUserRolesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(UserRoles.class));
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

    public UserRoles findUserRoles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(UserRoles.class, id);
        } finally {
            em.close();
        }
    }

    public int getUserRolesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<UserRoles> rt = cq.from(UserRoles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
