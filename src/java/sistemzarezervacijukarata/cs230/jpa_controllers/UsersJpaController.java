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
import sistemzarezervacijukarata.cs230.entities.UserRoles;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import sistemzarezervacijukarata.cs230.entities.Users;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.IllegalOrphanException;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.NonexistentEntityException;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.PreexistingEntityException;
import sistemzarezervacijukarata.cs230.jpa_controllers.exceptions.RollbackFailureException;

/**
 *
 * @author razvoj
 */
public class UsersJpaController implements Serializable {

    public UsersJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Users users) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (users.getUserRolesList() == null) {
            users.setUserRolesList(new ArrayList<UserRoles>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<UserRoles> attachedUserRolesList = new ArrayList<UserRoles>();
            for (UserRoles userRolesListUserRolesToAttach : users.getUserRolesList()) {
                userRolesListUserRolesToAttach = em.getReference(userRolesListUserRolesToAttach.getClass(), userRolesListUserRolesToAttach.getUserRoleId());
                attachedUserRolesList.add(userRolesListUserRolesToAttach);
            }
            users.setUserRolesList(attachedUserRolesList);
            em.persist(users);
            for (UserRoles userRolesListUserRoles : users.getUserRolesList()) {
                Users oldUsernameOfUserRolesListUserRoles = userRolesListUserRoles.getUsername();
                userRolesListUserRoles.setUsername(users);
                userRolesListUserRoles = em.merge(userRolesListUserRoles);
                if (oldUsernameOfUserRolesListUserRoles != null) {
                    oldUsernameOfUserRolesListUserRoles.getUserRolesList().remove(userRolesListUserRoles);
                    oldUsernameOfUserRolesListUserRoles = em.merge(oldUsernameOfUserRolesListUserRoles);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findUsers(users.getUsername()) != null) {
                throw new PreexistingEntityException("Users " + users + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Users users) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Users persistentUsers = em.find(Users.class, users.getUsername());
            List<UserRoles> userRolesListOld = persistentUsers.getUserRolesList();
            List<UserRoles> userRolesListNew = users.getUserRolesList();
            List<String> illegalOrphanMessages = null;
            for (UserRoles userRolesListOldUserRoles : userRolesListOld) {
                if (!userRolesListNew.contains(userRolesListOldUserRoles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain UserRoles " + userRolesListOldUserRoles + " since its username field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<UserRoles> attachedUserRolesListNew = new ArrayList<UserRoles>();
            for (UserRoles userRolesListNewUserRolesToAttach : userRolesListNew) {
                userRolesListNewUserRolesToAttach = em.getReference(userRolesListNewUserRolesToAttach.getClass(), userRolesListNewUserRolesToAttach.getUserRoleId());
                attachedUserRolesListNew.add(userRolesListNewUserRolesToAttach);
            }
            userRolesListNew = attachedUserRolesListNew;
            users.setUserRolesList(userRolesListNew);
            users = em.merge(users);
            for (UserRoles userRolesListNewUserRoles : userRolesListNew) {
                if (!userRolesListOld.contains(userRolesListNewUserRoles)) {
                    Users oldUsernameOfUserRolesListNewUserRoles = userRolesListNewUserRoles.getUsername();
                    userRolesListNewUserRoles.setUsername(users);
                    userRolesListNewUserRoles = em.merge(userRolesListNewUserRoles);
                    if (oldUsernameOfUserRolesListNewUserRoles != null && !oldUsernameOfUserRolesListNewUserRoles.equals(users)) {
                        oldUsernameOfUserRolesListNewUserRoles.getUserRolesList().remove(userRolesListNewUserRoles);
                        oldUsernameOfUserRolesListNewUserRoles = em.merge(oldUsernameOfUserRolesListNewUserRoles);
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
                String id = users.getUsername();
                if (findUsers(id) == null) {
                    throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Users users;
            try {
                users = em.getReference(Users.class, id);
                users.getUsername();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<UserRoles> userRolesListOrphanCheck = users.getUserRolesList();
            for (UserRoles userRolesListOrphanCheckUserRoles : userRolesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Users (" + users + ") cannot be destroyed since the UserRoles " + userRolesListOrphanCheckUserRoles + " in its userRolesList field has a non-nullable username field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(users);
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

    public List<Users> findUsersEntities() {
        return findUsersEntities(true, -1, -1);
    }

    public List<Users> findUsersEntities(int maxResults, int firstResult) {
        return findUsersEntities(false, maxResults, firstResult);
    }

    private List<Users> findUsersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Users.class));
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

    public Users findUsers(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Users.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Users> rt = cq.from(Users.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
