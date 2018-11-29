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
public class GcDetalleJpaController implements Serializable {

    public GcDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcDetalle gcDetalle) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcProceso procesoId = gcDetalle.getProcesoId();
            if (procesoId != null) {
                procesoId = em.getReference(procesoId.getClass(), procesoId.getId());
                gcDetalle.setProcesoId(procesoId);
            }
            em.persist(gcDetalle);
            if (procesoId != null) {
                procesoId.getGcDetalleList().add(gcDetalle);
                procesoId = em.merge(procesoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcDetalle gcDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcDetalle persistentGcDetalle = em.find(GcDetalle.class, gcDetalle.getId());
            GcProceso procesoIdOld = persistentGcDetalle.getProcesoId();
            GcProceso procesoIdNew = gcDetalle.getProcesoId();
            if (procesoIdNew != null) {
                procesoIdNew = em.getReference(procesoIdNew.getClass(), procesoIdNew.getId());
                gcDetalle.setProcesoId(procesoIdNew);
            }
            gcDetalle = em.merge(gcDetalle);
            if (procesoIdOld != null && !procesoIdOld.equals(procesoIdNew)) {
                procesoIdOld.getGcDetalleList().remove(gcDetalle);
                procesoIdOld = em.merge(procesoIdOld);
            }
            if (procesoIdNew != null && !procesoIdNew.equals(procesoIdOld)) {
                procesoIdNew.getGcDetalleList().add(gcDetalle);
                procesoIdNew = em.merge(procesoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcDetalle.getId();
                if (findGcDetalle(id) == null) {
                    throw new NonexistentEntityException("The gcDetalle with id " + id + " no longer exists.");
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
            GcDetalle gcDetalle;
            try {
                gcDetalle = em.getReference(GcDetalle.class, id);
                gcDetalle.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcDetalle with id " + id + " no longer exists.", enfe);
            }
            GcProceso procesoId = gcDetalle.getProcesoId();
            if (procesoId != null) {
                procesoId.getGcDetalleList().remove(gcDetalle);
                procesoId = em.merge(procesoId);
            }
            em.remove(gcDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcDetalle> findGcDetalleEntities() {
        return findGcDetalleEntities(true, -1, -1);
    }

    public List<GcDetalle> findGcDetalleEntities(int maxResults, int firstResult) {
        return findGcDetalleEntities(false, maxResults, firstResult);
    }

    private List<GcDetalle> findGcDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcDetalle.class));
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

    public GcDetalle findGcDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcDetalle> rt = cq.from(GcDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
