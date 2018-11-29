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
 * @author Desarrollo
 */
public class GcTramiteJpaController implements Serializable {

    public GcTramiteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcTramite gcTramite) {
        if (gcTramite.getGcSolicitudList() == null) {
            gcTramite.setGcSolicitudList(new ArrayList<GcSolicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GcSolicitud> attachedGcSolicitudList = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListGcSolicitudToAttach : gcTramite.getGcSolicitudList()) {
                gcSolicitudListGcSolicitudToAttach = em.getReference(gcSolicitudListGcSolicitudToAttach.getClass(), gcSolicitudListGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudList.add(gcSolicitudListGcSolicitudToAttach);
            }
            gcTramite.setGcSolicitudList(attachedGcSolicitudList);
            em.persist(gcTramite);
            for (GcSolicitud gcSolicitudListGcSolicitud : gcTramite.getGcSolicitudList()) {
                GcTramite oldTramiteIdOfGcSolicitudListGcSolicitud = gcSolicitudListGcSolicitud.getTramiteId();
                gcSolicitudListGcSolicitud.setTramiteId(gcTramite);
                gcSolicitudListGcSolicitud = em.merge(gcSolicitudListGcSolicitud);
                if (oldTramiteIdOfGcSolicitudListGcSolicitud != null) {
                    oldTramiteIdOfGcSolicitudListGcSolicitud.getGcSolicitudList().remove(gcSolicitudListGcSolicitud);
                    oldTramiteIdOfGcSolicitudListGcSolicitud = em.merge(oldTramiteIdOfGcSolicitudListGcSolicitud);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcTramite gcTramite) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcTramite persistentGcTramite = em.find(GcTramite.class, gcTramite.getId());
            List<GcSolicitud> gcSolicitudListOld = persistentGcTramite.getGcSolicitudList();
            List<GcSolicitud> gcSolicitudListNew = gcTramite.getGcSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (GcSolicitud gcSolicitudListOldGcSolicitud : gcSolicitudListOld) {
                if (!gcSolicitudListNew.contains(gcSolicitudListOldGcSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcSolicitud " + gcSolicitudListOldGcSolicitud + " since its tramiteId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GcSolicitud> attachedGcSolicitudListNew = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListNewGcSolicitudToAttach : gcSolicitudListNew) {
                gcSolicitudListNewGcSolicitudToAttach = em.getReference(gcSolicitudListNewGcSolicitudToAttach.getClass(), gcSolicitudListNewGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudListNew.add(gcSolicitudListNewGcSolicitudToAttach);
            }
            gcSolicitudListNew = attachedGcSolicitudListNew;
            gcTramite.setGcSolicitudList(gcSolicitudListNew);
            gcTramite = em.merge(gcTramite);
            for (GcSolicitud gcSolicitudListNewGcSolicitud : gcSolicitudListNew) {
                if (!gcSolicitudListOld.contains(gcSolicitudListNewGcSolicitud)) {
                    GcTramite oldTramiteIdOfGcSolicitudListNewGcSolicitud = gcSolicitudListNewGcSolicitud.getTramiteId();
                    gcSolicitudListNewGcSolicitud.setTramiteId(gcTramite);
                    gcSolicitudListNewGcSolicitud = em.merge(gcSolicitudListNewGcSolicitud);
                    if (oldTramiteIdOfGcSolicitudListNewGcSolicitud != null && !oldTramiteIdOfGcSolicitudListNewGcSolicitud.equals(gcTramite)) {
                        oldTramiteIdOfGcSolicitudListNewGcSolicitud.getGcSolicitudList().remove(gcSolicitudListNewGcSolicitud);
                        oldTramiteIdOfGcSolicitudListNewGcSolicitud = em.merge(oldTramiteIdOfGcSolicitudListNewGcSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcTramite.getId();
                if (findGcTramite(id) == null) {
                    throw new NonexistentEntityException("The gcTramite with id " + id + " no longer exists.");
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
            GcTramite gcTramite;
            try {
                gcTramite = em.getReference(GcTramite.class, id);
                gcTramite.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcTramite with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcSolicitud> gcSolicitudListOrphanCheck = gcTramite.getGcSolicitudList();
            for (GcSolicitud gcSolicitudListOrphanCheckGcSolicitud : gcSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcTramite (" + gcTramite + ") cannot be destroyed since the GcSolicitud " + gcSolicitudListOrphanCheckGcSolicitud + " in its gcSolicitudList field has a non-nullable tramiteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(gcTramite);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcTramite> findGcTramiteEntities() {
        return findGcTramiteEntities(true, -1, -1);
    }

    public List<GcTramite> findGcTramiteEntities(int maxResults, int firstResult) {
        return findGcTramiteEntities(false, maxResults, firstResult);
    }

    private List<GcTramite> findGcTramiteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcTramite.class));
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

    public GcTramite findGcTramite(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcTramite.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcTramiteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcTramite> rt = cq.from(GcTramite.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
