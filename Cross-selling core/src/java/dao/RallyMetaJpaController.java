/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Rgalicia
 */
public class RallyMetaJpaController implements Serializable {

    public RallyMetaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallyMeta rallyMeta) {
        if (rallyMeta.getRallyAsignacionList() == null) {
            rallyMeta.setRallyAsignacionList(new ArrayList<RallyAsignacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RallyAsignacion> attachedRallyAsignacionList = new ArrayList<RallyAsignacion>();
            for (RallyAsignacion rallyAsignacionListRallyAsignacionToAttach : rallyMeta.getRallyAsignacionList()) {
                rallyAsignacionListRallyAsignacionToAttach = em.getReference(rallyAsignacionListRallyAsignacionToAttach.getClass(), rallyAsignacionListRallyAsignacionToAttach.getIdasignacion());
                attachedRallyAsignacionList.add(rallyAsignacionListRallyAsignacionToAttach);
            }
            rallyMeta.setRallyAsignacionList(attachedRallyAsignacionList);
            em.persist(rallyMeta);
            for (RallyAsignacion rallyAsignacionListRallyAsignacion : rallyMeta.getRallyAsignacionList()) {
                RallyMeta oldRallyMetaIdmetaOfRallyAsignacionListRallyAsignacion = rallyAsignacionListRallyAsignacion.getRallyMetaIdmeta();
                rallyAsignacionListRallyAsignacion.setRallyMetaIdmeta(rallyMeta);
                rallyAsignacionListRallyAsignacion = em.merge(rallyAsignacionListRallyAsignacion);
                if (oldRallyMetaIdmetaOfRallyAsignacionListRallyAsignacion != null) {
                    oldRallyMetaIdmetaOfRallyAsignacionListRallyAsignacion.getRallyAsignacionList().remove(rallyAsignacionListRallyAsignacion);
                    oldRallyMetaIdmetaOfRallyAsignacionListRallyAsignacion = em.merge(oldRallyMetaIdmetaOfRallyAsignacionListRallyAsignacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallyMeta rallyMeta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyMeta persistentRallyMeta = em.find(RallyMeta.class, rallyMeta.getIdmeta());
            List<RallyAsignacion> rallyAsignacionListOld = persistentRallyMeta.getRallyAsignacionList();
            List<RallyAsignacion> rallyAsignacionListNew = rallyMeta.getRallyAsignacionList();
            List<String> illegalOrphanMessages = null;
            for (RallyAsignacion rallyAsignacionListOldRallyAsignacion : rallyAsignacionListOld) {
                if (!rallyAsignacionListNew.contains(rallyAsignacionListOldRallyAsignacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RallyAsignacion " + rallyAsignacionListOldRallyAsignacion + " since its rallyMetaIdmeta field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RallyAsignacion> attachedRallyAsignacionListNew = new ArrayList<RallyAsignacion>();
            for (RallyAsignacion rallyAsignacionListNewRallyAsignacionToAttach : rallyAsignacionListNew) {
                rallyAsignacionListNewRallyAsignacionToAttach = em.getReference(rallyAsignacionListNewRallyAsignacionToAttach.getClass(), rallyAsignacionListNewRallyAsignacionToAttach.getIdasignacion());
                attachedRallyAsignacionListNew.add(rallyAsignacionListNewRallyAsignacionToAttach);
            }
            rallyAsignacionListNew = attachedRallyAsignacionListNew;
            rallyMeta.setRallyAsignacionList(rallyAsignacionListNew);
            rallyMeta = em.merge(rallyMeta);
            for (RallyAsignacion rallyAsignacionListNewRallyAsignacion : rallyAsignacionListNew) {
                if (!rallyAsignacionListOld.contains(rallyAsignacionListNewRallyAsignacion)) {
                    RallyMeta oldRallyMetaIdmetaOfRallyAsignacionListNewRallyAsignacion = rallyAsignacionListNewRallyAsignacion.getRallyMetaIdmeta();
                    rallyAsignacionListNewRallyAsignacion.setRallyMetaIdmeta(rallyMeta);
                    rallyAsignacionListNewRallyAsignacion = em.merge(rallyAsignacionListNewRallyAsignacion);
                    if (oldRallyMetaIdmetaOfRallyAsignacionListNewRallyAsignacion != null && !oldRallyMetaIdmetaOfRallyAsignacionListNewRallyAsignacion.equals(rallyMeta)) {
                        oldRallyMetaIdmetaOfRallyAsignacionListNewRallyAsignacion.getRallyAsignacionList().remove(rallyAsignacionListNewRallyAsignacion);
                        oldRallyMetaIdmetaOfRallyAsignacionListNewRallyAsignacion = em.merge(oldRallyMetaIdmetaOfRallyAsignacionListNewRallyAsignacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallyMeta.getIdmeta();
                if (findRallyMeta(id) == null) {
                    throw new NonexistentEntityException("The rallyMeta with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyMeta rallyMeta;
            try {
                rallyMeta = em.getReference(RallyMeta.class, id);
                rallyMeta.getIdmeta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallyMeta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RallyAsignacion> rallyAsignacionListOrphanCheck = rallyMeta.getRallyAsignacionList();
            for (RallyAsignacion rallyAsignacionListOrphanCheckRallyAsignacion : rallyAsignacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RallyMeta (" + rallyMeta + ") cannot be destroyed since the RallyAsignacion " + rallyAsignacionListOrphanCheckRallyAsignacion + " in its rallyAsignacionList field has a non-nullable rallyMetaIdmeta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rallyMeta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallyMeta> findRallyMetaEntities() {
        return findRallyMetaEntities(true, -1, -1);
    }

    public List<RallyMeta> findRallyMetaEntities(int maxResults, int firstResult) {
        return findRallyMetaEntities(false, maxResults, firstResult);
    }

    private List<RallyMeta> findRallyMetaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallyMeta.class));
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

    public RallyMeta findRallyMeta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallyMeta.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallyMetaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallyMeta> rt = cq.from(RallyMeta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
