/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Rgalicia
 */
public class GcSeguimientoJpaController implements Serializable {

    public GcSeguimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcSeguimiento gcSeguimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcSolicitud solicitudNumeroSolicitud = gcSeguimiento.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud = em.getReference(solicitudNumeroSolicitud.getClass(), solicitudNumeroSolicitud.getNumeroSolicitud());
                gcSeguimiento.setSolicitudNumeroSolicitud(solicitudNumeroSolicitud);
            }
            em.persist(gcSeguimiento);
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcSeguimientoList().add(gcSeguimiento);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcSeguimiento gcSeguimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcSeguimiento persistentGcSeguimiento = em.find(GcSeguimiento.class, gcSeguimiento.getId());
            GcSolicitud solicitudNumeroSolicitudOld = persistentGcSeguimiento.getSolicitudNumeroSolicitud();
            GcSolicitud solicitudNumeroSolicitudNew = gcSeguimiento.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitudNew != null) {
                solicitudNumeroSolicitudNew = em.getReference(solicitudNumeroSolicitudNew.getClass(), solicitudNumeroSolicitudNew.getNumeroSolicitud());
                gcSeguimiento.setSolicitudNumeroSolicitud(solicitudNumeroSolicitudNew);
            }
            gcSeguimiento = em.merge(gcSeguimiento);
            if (solicitudNumeroSolicitudOld != null && !solicitudNumeroSolicitudOld.equals(solicitudNumeroSolicitudNew)) {
                solicitudNumeroSolicitudOld.getGcSeguimientoList().remove(gcSeguimiento);
                solicitudNumeroSolicitudOld = em.merge(solicitudNumeroSolicitudOld);
            }
            if (solicitudNumeroSolicitudNew != null && !solicitudNumeroSolicitudNew.equals(solicitudNumeroSolicitudOld)) {
                solicitudNumeroSolicitudNew.getGcSeguimientoList().add(gcSeguimiento);
                solicitudNumeroSolicitudNew = em.merge(solicitudNumeroSolicitudNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcSeguimiento.getId();
                if (findGcSeguimiento(id) == null) {
                    throw new NonexistentEntityException("The gcSeguimiento with id " + id + " no longer exists.");
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
            GcSeguimiento gcSeguimiento;
            try {
                gcSeguimiento = em.getReference(GcSeguimiento.class, id);
                gcSeguimiento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcSeguimiento with id " + id + " no longer exists.", enfe);
            }
            GcSolicitud solicitudNumeroSolicitud = gcSeguimiento.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcSeguimientoList().remove(gcSeguimiento);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            em.remove(gcSeguimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcSeguimiento> findGcSeguimientoEntities() {
        return findGcSeguimientoEntities(true, -1, -1);
    }

    public List<GcSeguimiento> findGcSeguimientoEntities(int maxResults, int firstResult) {
        return findGcSeguimientoEntities(false, maxResults, firstResult);
    }

    private List<GcSeguimiento> findGcSeguimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcSeguimiento.class));
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

    public GcSeguimiento findGcSeguimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcSeguimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcSeguimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcSeguimiento> rt = cq.from(GcSeguimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
