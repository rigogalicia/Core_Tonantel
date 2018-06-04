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
            GcGestion gestionId = gcSeguimiento.getGestionId();
            if (gestionId != null) {
                gestionId = em.getReference(gestionId.getClass(), gestionId.getId());
                gcSeguimiento.setGestionId(gestionId);
            }
            em.persist(gcSeguimiento);
            if (gestionId != null) {
                gestionId.getGcSeguimientoList().add(gcSeguimiento);
                gestionId = em.merge(gestionId);
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
            GcGestion gestionIdOld = persistentGcSeguimiento.getGestionId();
            GcGestion gestionIdNew = gcSeguimiento.getGestionId();
            if (gestionIdNew != null) {
                gestionIdNew = em.getReference(gestionIdNew.getClass(), gestionIdNew.getId());
                gcSeguimiento.setGestionId(gestionIdNew);
            }
            gcSeguimiento = em.merge(gcSeguimiento);
            if (gestionIdOld != null && !gestionIdOld.equals(gestionIdNew)) {
                gestionIdOld.getGcSeguimientoList().remove(gcSeguimiento);
                gestionIdOld = em.merge(gestionIdOld);
            }
            if (gestionIdNew != null && !gestionIdNew.equals(gestionIdOld)) {
                gestionIdNew.getGcSeguimientoList().add(gcSeguimiento);
                gestionIdNew = em.merge(gestionIdNew);
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
            GcGestion gestionId = gcSeguimiento.getGestionId();
            if (gestionId != null) {
                gestionId.getGcSeguimientoList().remove(gcSeguimiento);
                gestionId = em.merge(gestionId);
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
