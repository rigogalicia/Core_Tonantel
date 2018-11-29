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
 * @author r29galicia
 */
public class GcTipoclienteJpaController implements Serializable {

    public GcTipoclienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcTipocliente gcTipocliente) {
        if (gcTipocliente.getGcSolicitudList() == null) {
            gcTipocliente.setGcSolicitudList(new ArrayList<GcSolicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GcSolicitud> attachedGcSolicitudList = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListGcSolicitudToAttach : gcTipocliente.getGcSolicitudList()) {
                gcSolicitudListGcSolicitudToAttach = em.getReference(gcSolicitudListGcSolicitudToAttach.getClass(), gcSolicitudListGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudList.add(gcSolicitudListGcSolicitudToAttach);
            }
            gcTipocliente.setGcSolicitudList(attachedGcSolicitudList);
            em.persist(gcTipocliente);
            for (GcSolicitud gcSolicitudListGcSolicitud : gcTipocliente.getGcSolicitudList()) {
                GcTipocliente oldTipoclienteIdOfGcSolicitudListGcSolicitud = gcSolicitudListGcSolicitud.getTipoclienteId();
                gcSolicitudListGcSolicitud.setTipoclienteId(gcTipocliente);
                gcSolicitudListGcSolicitud = em.merge(gcSolicitudListGcSolicitud);
                if (oldTipoclienteIdOfGcSolicitudListGcSolicitud != null) {
                    oldTipoclienteIdOfGcSolicitudListGcSolicitud.getGcSolicitudList().remove(gcSolicitudListGcSolicitud);
                    oldTipoclienteIdOfGcSolicitudListGcSolicitud = em.merge(oldTipoclienteIdOfGcSolicitudListGcSolicitud);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcTipocliente gcTipocliente) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcTipocliente persistentGcTipocliente = em.find(GcTipocliente.class, gcTipocliente.getId());
            List<GcSolicitud> gcSolicitudListOld = persistentGcTipocliente.getGcSolicitudList();
            List<GcSolicitud> gcSolicitudListNew = gcTipocliente.getGcSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (GcSolicitud gcSolicitudListOldGcSolicitud : gcSolicitudListOld) {
                if (!gcSolicitudListNew.contains(gcSolicitudListOldGcSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcSolicitud " + gcSolicitudListOldGcSolicitud + " since its tipoclienteId field is not nullable.");
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
            gcTipocliente.setGcSolicitudList(gcSolicitudListNew);
            gcTipocliente = em.merge(gcTipocliente);
            for (GcSolicitud gcSolicitudListNewGcSolicitud : gcSolicitudListNew) {
                if (!gcSolicitudListOld.contains(gcSolicitudListNewGcSolicitud)) {
                    GcTipocliente oldTipoclienteIdOfGcSolicitudListNewGcSolicitud = gcSolicitudListNewGcSolicitud.getTipoclienteId();
                    gcSolicitudListNewGcSolicitud.setTipoclienteId(gcTipocliente);
                    gcSolicitudListNewGcSolicitud = em.merge(gcSolicitudListNewGcSolicitud);
                    if (oldTipoclienteIdOfGcSolicitudListNewGcSolicitud != null && !oldTipoclienteIdOfGcSolicitudListNewGcSolicitud.equals(gcTipocliente)) {
                        oldTipoclienteIdOfGcSolicitudListNewGcSolicitud.getGcSolicitudList().remove(gcSolicitudListNewGcSolicitud);
                        oldTipoclienteIdOfGcSolicitudListNewGcSolicitud = em.merge(oldTipoclienteIdOfGcSolicitudListNewGcSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcTipocliente.getId();
                if (findGcTipocliente(id) == null) {
                    throw new NonexistentEntityException("The gcTipocliente with id " + id + " no longer exists.");
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
            GcTipocliente gcTipocliente;
            try {
                gcTipocliente = em.getReference(GcTipocliente.class, id);
                gcTipocliente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcTipocliente with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcSolicitud> gcSolicitudListOrphanCheck = gcTipocliente.getGcSolicitudList();
            for (GcSolicitud gcSolicitudListOrphanCheckGcSolicitud : gcSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcTipocliente (" + gcTipocliente + ") cannot be destroyed since the GcSolicitud " + gcSolicitudListOrphanCheckGcSolicitud + " in its gcSolicitudList field has a non-nullable tipoclienteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(gcTipocliente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcTipocliente> findGcTipoclienteEntities() {
        return findGcTipoclienteEntities(true, -1, -1);
    }

    public List<GcTipocliente> findGcTipoclienteEntities(int maxResults, int firstResult) {
        return findGcTipoclienteEntities(false, maxResults, firstResult);
    }

    private List<GcTipocliente> findGcTipoclienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcTipocliente.class));
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

    public GcTipocliente findGcTipocliente(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcTipocliente.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcTipoclienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcTipocliente> rt = cq.from(GcTipocliente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
