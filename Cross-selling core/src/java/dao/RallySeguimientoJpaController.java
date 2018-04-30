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
public class RallySeguimientoJpaController implements Serializable {

    public RallySeguimientoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallySeguimiento rallySeguimiento) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyAsignacion rallyAsignacionIdasignacion = rallySeguimiento.getRallyAsignacionIdasignacion();
            if (rallyAsignacionIdasignacion != null) {
                rallyAsignacionIdasignacion = em.getReference(rallyAsignacionIdasignacion.getClass(), rallyAsignacionIdasignacion.getIdasignacion());
                rallySeguimiento.setRallyAsignacionIdasignacion(rallyAsignacionIdasignacion);
            }
            em.persist(rallySeguimiento);
            if (rallyAsignacionIdasignacion != null) {
                rallyAsignacionIdasignacion.getRallySeguimientoList().add(rallySeguimiento);
                rallyAsignacionIdasignacion = em.merge(rallyAsignacionIdasignacion);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallySeguimiento rallySeguimiento) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallySeguimiento persistentRallySeguimiento = em.find(RallySeguimiento.class, rallySeguimiento.getIdseguimiento());
            RallyAsignacion rallyAsignacionIdasignacionOld = persistentRallySeguimiento.getRallyAsignacionIdasignacion();
            RallyAsignacion rallyAsignacionIdasignacionNew = rallySeguimiento.getRallyAsignacionIdasignacion();
            if (rallyAsignacionIdasignacionNew != null) {
                rallyAsignacionIdasignacionNew = em.getReference(rallyAsignacionIdasignacionNew.getClass(), rallyAsignacionIdasignacionNew.getIdasignacion());
                rallySeguimiento.setRallyAsignacionIdasignacion(rallyAsignacionIdasignacionNew);
            }
            rallySeguimiento = em.merge(rallySeguimiento);
            if (rallyAsignacionIdasignacionOld != null && !rallyAsignacionIdasignacionOld.equals(rallyAsignacionIdasignacionNew)) {
                rallyAsignacionIdasignacionOld.getRallySeguimientoList().remove(rallySeguimiento);
                rallyAsignacionIdasignacionOld = em.merge(rallyAsignacionIdasignacionOld);
            }
            if (rallyAsignacionIdasignacionNew != null && !rallyAsignacionIdasignacionNew.equals(rallyAsignacionIdasignacionOld)) {
                rallyAsignacionIdasignacionNew.getRallySeguimientoList().add(rallySeguimiento);
                rallyAsignacionIdasignacionNew = em.merge(rallyAsignacionIdasignacionNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallySeguimiento.getIdseguimiento();
                if (findRallySeguimiento(id) == null) {
                    throw new NonexistentEntityException("The rallySeguimiento with id " + id + " no longer exists.");
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
            RallySeguimiento rallySeguimiento;
            try {
                rallySeguimiento = em.getReference(RallySeguimiento.class, id);
                rallySeguimiento.getIdseguimiento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallySeguimiento with id " + id + " no longer exists.", enfe);
            }
            RallyAsignacion rallyAsignacionIdasignacion = rallySeguimiento.getRallyAsignacionIdasignacion();
            if (rallyAsignacionIdasignacion != null) {
                rallyAsignacionIdasignacion.getRallySeguimientoList().remove(rallySeguimiento);
                rallyAsignacionIdasignacion = em.merge(rallyAsignacionIdasignacion);
            }
            em.remove(rallySeguimiento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallySeguimiento> findRallySeguimientoEntities() {
        return findRallySeguimientoEntities(true, -1, -1);
    }

    public List<RallySeguimiento> findRallySeguimientoEntities(int maxResults, int firstResult) {
        return findRallySeguimientoEntities(false, maxResults, firstResult);
    }

    private List<RallySeguimiento> findRallySeguimientoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallySeguimiento.class));
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

    public RallySeguimiento findRallySeguimiento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallySeguimiento.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallySeguimientoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallySeguimiento> rt = cq.from(RallySeguimiento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
