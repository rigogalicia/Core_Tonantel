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
 * @author Desarrollo
 */
public class GcGestionJpaController implements Serializable {

    public GcGestionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcGestion gcGestion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcEstado estadoId = gcGestion.getEstadoId();
            if (estadoId != null) {
                estadoId = em.getReference(estadoId.getClass(), estadoId.getId());
                gcGestion.setEstadoId(estadoId);
            }
            GcSolicitud solicitudNumeroSolicitud = gcGestion.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud = em.getReference(solicitudNumeroSolicitud.getClass(), solicitudNumeroSolicitud.getNumeroSolicitud());
                gcGestion.setSolicitudNumeroSolicitud(solicitudNumeroSolicitud);
            }
            em.persist(gcGestion);
            if (estadoId != null) {
                estadoId.getGcGestionList().add(gcGestion);
                estadoId = em.merge(estadoId);
            }
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcGestionList().add(gcGestion);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcGestion gcGestion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcGestion persistentGcGestion = em.find(GcGestion.class, gcGestion.getId());
            GcEstado estadoIdOld = persistentGcGestion.getEstadoId();
            GcEstado estadoIdNew = gcGestion.getEstadoId();
            GcSolicitud solicitudNumeroSolicitudOld = persistentGcGestion.getSolicitudNumeroSolicitud();
            GcSolicitud solicitudNumeroSolicitudNew = gcGestion.getSolicitudNumeroSolicitud();
            if (estadoIdNew != null) {
                estadoIdNew = em.getReference(estadoIdNew.getClass(), estadoIdNew.getId());
                gcGestion.setEstadoId(estadoIdNew);
            }
            if (solicitudNumeroSolicitudNew != null) {
                solicitudNumeroSolicitudNew = em.getReference(solicitudNumeroSolicitudNew.getClass(), solicitudNumeroSolicitudNew.getNumeroSolicitud());
                gcGestion.setSolicitudNumeroSolicitud(solicitudNumeroSolicitudNew);
            }
            gcGestion = em.merge(gcGestion);
            if (estadoIdOld != null && !estadoIdOld.equals(estadoIdNew)) {
                estadoIdOld.getGcGestionList().remove(gcGestion);
                estadoIdOld = em.merge(estadoIdOld);
            }
            if (estadoIdNew != null && !estadoIdNew.equals(estadoIdOld)) {
                estadoIdNew.getGcGestionList().add(gcGestion);
                estadoIdNew = em.merge(estadoIdNew);
            }
            if (solicitudNumeroSolicitudOld != null && !solicitudNumeroSolicitudOld.equals(solicitudNumeroSolicitudNew)) {
                solicitudNumeroSolicitudOld.getGcGestionList().remove(gcGestion);
                solicitudNumeroSolicitudOld = em.merge(solicitudNumeroSolicitudOld);
            }
            if (solicitudNumeroSolicitudNew != null && !solicitudNumeroSolicitudNew.equals(solicitudNumeroSolicitudOld)) {
                solicitudNumeroSolicitudNew.getGcGestionList().add(gcGestion);
                solicitudNumeroSolicitudNew = em.merge(solicitudNumeroSolicitudNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcGestion.getId();
                if (findGcGestion(id) == null) {
                    throw new NonexistentEntityException("The gcGestion with id " + id + " no longer exists.");
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
            GcGestion gcGestion;
            try {
                gcGestion = em.getReference(GcGestion.class, id);
                gcGestion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcGestion with id " + id + " no longer exists.", enfe);
            }
            GcEstado estadoId = gcGestion.getEstadoId();
            if (estadoId != null) {
                estadoId.getGcGestionList().remove(gcGestion);
                estadoId = em.merge(estadoId);
            }
            GcSolicitud solicitudNumeroSolicitud = gcGestion.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcGestionList().remove(gcGestion);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            em.remove(gcGestion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcGestion> findGcGestionEntities() {
        return findGcGestionEntities(true, -1, -1);
    }

    public List<GcGestion> findGcGestionEntities(int maxResults, int firstResult) {
        return findGcGestionEntities(false, maxResults, firstResult);
    }

    private List<GcGestion> findGcGestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcGestion.class));
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

    public GcGestion findGcGestion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcGestion.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcGestionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcGestion> rt = cq.from(GcGestion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
