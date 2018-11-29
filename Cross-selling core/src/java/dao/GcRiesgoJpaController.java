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
 * @author Desarrollo
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
        if (gcRiesgo.getGcSolicitudList() == null) {
            gcRiesgo.setGcSolicitudList(new ArrayList<GcSolicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GcSolicitud> attachedGcSolicitudList = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListGcSolicitudToAttach : gcRiesgo.getGcSolicitudList()) {
                gcSolicitudListGcSolicitudToAttach = em.getReference(gcSolicitudListGcSolicitudToAttach.getClass(), gcSolicitudListGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudList.add(gcSolicitudListGcSolicitudToAttach);
            }
            gcRiesgo.setGcSolicitudList(attachedGcSolicitudList);
            em.persist(gcRiesgo);
            for (GcSolicitud gcSolicitudListGcSolicitud : gcRiesgo.getGcSolicitudList()) {
                GcRiesgo oldRiesgoIdOfGcSolicitudListGcSolicitud = gcSolicitudListGcSolicitud.getRiesgoId();
                gcSolicitudListGcSolicitud.setRiesgoId(gcRiesgo);
                gcSolicitudListGcSolicitud = em.merge(gcSolicitudListGcSolicitud);
                if (oldRiesgoIdOfGcSolicitudListGcSolicitud != null) {
                    oldRiesgoIdOfGcSolicitudListGcSolicitud.getGcSolicitudList().remove(gcSolicitudListGcSolicitud);
                    oldRiesgoIdOfGcSolicitudListGcSolicitud = em.merge(oldRiesgoIdOfGcSolicitudListGcSolicitud);
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
            List<GcSolicitud> gcSolicitudListOld = persistentGcRiesgo.getGcSolicitudList();
            List<GcSolicitud> gcSolicitudListNew = gcRiesgo.getGcSolicitudList();
            List<GcSolicitud> attachedGcSolicitudListNew = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListNewGcSolicitudToAttach : gcSolicitudListNew) {
                gcSolicitudListNewGcSolicitudToAttach = em.getReference(gcSolicitudListNewGcSolicitudToAttach.getClass(), gcSolicitudListNewGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudListNew.add(gcSolicitudListNewGcSolicitudToAttach);
            }
            gcSolicitudListNew = attachedGcSolicitudListNew;
            gcRiesgo.setGcSolicitudList(gcSolicitudListNew);
            gcRiesgo = em.merge(gcRiesgo);
            for (GcSolicitud gcSolicitudListOldGcSolicitud : gcSolicitudListOld) {
                if (!gcSolicitudListNew.contains(gcSolicitudListOldGcSolicitud)) {
                    gcSolicitudListOldGcSolicitud.setRiesgoId(null);
                    gcSolicitudListOldGcSolicitud = em.merge(gcSolicitudListOldGcSolicitud);
                }
            }
            for (GcSolicitud gcSolicitudListNewGcSolicitud : gcSolicitudListNew) {
                if (!gcSolicitudListOld.contains(gcSolicitudListNewGcSolicitud)) {
                    GcRiesgo oldRiesgoIdOfGcSolicitudListNewGcSolicitud = gcSolicitudListNewGcSolicitud.getRiesgoId();
                    gcSolicitudListNewGcSolicitud.setRiesgoId(gcRiesgo);
                    gcSolicitudListNewGcSolicitud = em.merge(gcSolicitudListNewGcSolicitud);
                    if (oldRiesgoIdOfGcSolicitudListNewGcSolicitud != null && !oldRiesgoIdOfGcSolicitudListNewGcSolicitud.equals(gcRiesgo)) {
                        oldRiesgoIdOfGcSolicitudListNewGcSolicitud.getGcSolicitudList().remove(gcSolicitudListNewGcSolicitud);
                        oldRiesgoIdOfGcSolicitudListNewGcSolicitud = em.merge(oldRiesgoIdOfGcSolicitudListNewGcSolicitud);
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
            List<GcSolicitud> gcSolicitudList = gcRiesgo.getGcSolicitudList();
            for (GcSolicitud gcSolicitudListGcSolicitud : gcSolicitudList) {
                gcSolicitudListGcSolicitud.setRiesgoId(null);
                gcSolicitudListGcSolicitud = em.merge(gcSolicitudListGcSolicitud);
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
