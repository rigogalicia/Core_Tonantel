/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

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
public class GcRiesgoJpaController implements Serializable {

    public GcRiesgoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcRiesgo gcRiesgo) {
        if (gcRiesgo.getGcGestionList() == null) {
            gcRiesgo.setGcGestionList(new ArrayList<GcGestion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GcGestion> attachedGcGestionList = new ArrayList<GcGestion>();
            for (GcGestion gcGestionListGcGestionToAttach : gcRiesgo.getGcGestionList()) {
                gcGestionListGcGestionToAttach = em.getReference(gcGestionListGcGestionToAttach.getClass(), gcGestionListGcGestionToAttach.getId());
                attachedGcGestionList.add(gcGestionListGcGestionToAttach);
            }
            gcRiesgo.setGcGestionList(attachedGcGestionList);
            em.persist(gcRiesgo);
            for (GcGestion gcGestionListGcGestion : gcRiesgo.getGcGestionList()) {
                GcRiesgo oldRiesgoIdOfGcGestionListGcGestion = gcGestionListGcGestion.getRiesgoId();
                gcGestionListGcGestion.setRiesgoId(gcRiesgo);
                gcGestionListGcGestion = em.merge(gcGestionListGcGestion);
                if (oldRiesgoIdOfGcGestionListGcGestion != null) {
                    oldRiesgoIdOfGcGestionListGcGestion.getGcGestionList().remove(gcGestionListGcGestion);
                    oldRiesgoIdOfGcGestionListGcGestion = em.merge(oldRiesgoIdOfGcGestionListGcGestion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcRiesgo gcRiesgo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcRiesgo persistentGcRiesgo = em.find(GcRiesgo.class, gcRiesgo.getId());
            List<GcGestion> gcGestionListOld = persistentGcRiesgo.getGcGestionList();
            List<GcGestion> gcGestionListNew = gcRiesgo.getGcGestionList();
            List<GcGestion> attachedGcGestionListNew = new ArrayList<GcGestion>();
            for (GcGestion gcGestionListNewGcGestionToAttach : gcGestionListNew) {
                gcGestionListNewGcGestionToAttach = em.getReference(gcGestionListNewGcGestionToAttach.getClass(), gcGestionListNewGcGestionToAttach.getId());
                attachedGcGestionListNew.add(gcGestionListNewGcGestionToAttach);
            }
            gcGestionListNew = attachedGcGestionListNew;
            gcRiesgo.setGcGestionList(gcGestionListNew);
            gcRiesgo = em.merge(gcRiesgo);
            for (GcGestion gcGestionListOldGcGestion : gcGestionListOld) {
                if (!gcGestionListNew.contains(gcGestionListOldGcGestion)) {
                    gcGestionListOldGcGestion.setRiesgoId(null);
                    gcGestionListOldGcGestion = em.merge(gcGestionListOldGcGestion);
                }
            }
            for (GcGestion gcGestionListNewGcGestion : gcGestionListNew) {
                if (!gcGestionListOld.contains(gcGestionListNewGcGestion)) {
                    GcRiesgo oldRiesgoIdOfGcGestionListNewGcGestion = gcGestionListNewGcGestion.getRiesgoId();
                    gcGestionListNewGcGestion.setRiesgoId(gcRiesgo);
                    gcGestionListNewGcGestion = em.merge(gcGestionListNewGcGestion);
                    if (oldRiesgoIdOfGcGestionListNewGcGestion != null && !oldRiesgoIdOfGcGestionListNewGcGestion.equals(gcRiesgo)) {
                        oldRiesgoIdOfGcGestionListNewGcGestion.getGcGestionList().remove(gcGestionListNewGcGestion);
                        oldRiesgoIdOfGcGestionListNewGcGestion = em.merge(oldRiesgoIdOfGcGestionListNewGcGestion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcRiesgo.getId();
                if (findGcRiesgo(id) == null) {
                    throw new NonexistentEntityException("The gcRiesgo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcRiesgo gcRiesgo;
            try {
                gcRiesgo = em.getReference(GcRiesgo.class, id);
                gcRiesgo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcRiesgo with id " + id + " no longer exists.", enfe);
            }
            List<GcGestion> gcGestionList = gcRiesgo.getGcGestionList();
            for (GcGestion gcGestionListGcGestion : gcGestionList) {
                gcGestionListGcGestion.setRiesgoId(null);
                gcGestionListGcGestion = em.merge(gcGestionListGcGestion);
            }
            em.remove(gcRiesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcRiesgo> findGcRiesgoEntities() {
        return findGcRiesgoEntities(true, -1, -1);
    }

    public List<GcRiesgo> findGcRiesgoEntities(int maxResults, int firstResult) {
        return findGcRiesgoEntities(false, maxResults, firstResult);
    }

    private List<GcRiesgo> findGcRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcRiesgo.class));
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

    public GcRiesgo findGcRiesgo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcRiesgo> rt = cq.from(GcRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
