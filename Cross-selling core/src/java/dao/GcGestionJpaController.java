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
            GcSolicitud solicitudNumeroSolicitud = gcGestion.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud = em.getReference(solicitudNumeroSolicitud.getClass(), solicitudNumeroSolicitud.getNumeroSolicitud());
                gcGestion.setSolicitudNumeroSolicitud(solicitudNumeroSolicitud);
            }
            GcRiesgo riesgoId = gcGestion.getRiesgoId();
            if (riesgoId != null) {
                riesgoId = em.getReference(riesgoId.getClass(), riesgoId.getId());
                gcGestion.setRiesgoId(riesgoId);
            }
            em.persist(gcGestion);
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcGestionList().add(gcGestion);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            if (riesgoId != null) {
                riesgoId.getGcGestionList().add(gcGestion);
                riesgoId = em.merge(riesgoId);
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
            GcSolicitud solicitudNumeroSolicitudOld = persistentGcGestion.getSolicitudNumeroSolicitud();
            GcSolicitud solicitudNumeroSolicitudNew = gcGestion.getSolicitudNumeroSolicitud();
            GcRiesgo riesgoIdOld = persistentGcGestion.getRiesgoId();
            GcRiesgo riesgoIdNew = gcGestion.getRiesgoId();
            if (solicitudNumeroSolicitudNew != null) {
                solicitudNumeroSolicitudNew = em.getReference(solicitudNumeroSolicitudNew.getClass(), solicitudNumeroSolicitudNew.getNumeroSolicitud());
                gcGestion.setSolicitudNumeroSolicitud(solicitudNumeroSolicitudNew);
            }
            if (riesgoIdNew != null) {
                riesgoIdNew = em.getReference(riesgoIdNew.getClass(), riesgoIdNew.getId());
                gcGestion.setRiesgoId(riesgoIdNew);
            }
            gcGestion = em.merge(gcGestion);
            if (solicitudNumeroSolicitudOld != null && !solicitudNumeroSolicitudOld.equals(solicitudNumeroSolicitudNew)) {
                solicitudNumeroSolicitudOld.getGcGestionList().remove(gcGestion);
                solicitudNumeroSolicitudOld = em.merge(solicitudNumeroSolicitudOld);
            }
            if (solicitudNumeroSolicitudNew != null && !solicitudNumeroSolicitudNew.equals(solicitudNumeroSolicitudOld)) {
                solicitudNumeroSolicitudNew.getGcGestionList().add(gcGestion);
                solicitudNumeroSolicitudNew = em.merge(solicitudNumeroSolicitudNew);
            }
            if (riesgoIdOld != null && !riesgoIdOld.equals(riesgoIdNew)) {
                riesgoIdOld.getGcGestionList().remove(gcGestion);
                riesgoIdOld = em.merge(riesgoIdOld);
            }
            if (riesgoIdNew != null && !riesgoIdNew.equals(riesgoIdOld)) {
                riesgoIdNew.getGcGestionList().add(gcGestion);
                riesgoIdNew = em.merge(riesgoIdNew);
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
            GcSolicitud solicitudNumeroSolicitud = gcGestion.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcGestionList().remove(gcGestion);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            GcRiesgo riesgoId = gcGestion.getRiesgoId();
            if (riesgoId != null) {
                riesgoId.getGcGestionList().remove(gcGestion);
                riesgoId = em.merge(riesgoId);
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