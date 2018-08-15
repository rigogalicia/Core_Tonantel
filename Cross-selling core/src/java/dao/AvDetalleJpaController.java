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
public class AvDetalleJpaController implements Serializable {

    public AvDetalleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvDetalle avDetalle) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvAvaluo avaluoId = avDetalle.getAvaluoId();
            if (avaluoId != null) {
                avaluoId = em.getReference(avaluoId.getClass(), avaluoId.getId());
                avDetalle.setAvaluoId(avaluoId);
            }
            em.persist(avDetalle);
            if (avaluoId != null) {
                avaluoId.getAvDetalleList().add(avDetalle);
                avaluoId = em.merge(avaluoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvDetalle avDetalle) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvDetalle persistentAvDetalle = em.find(AvDetalle.class, avDetalle.getId());
            AvAvaluo avaluoIdOld = persistentAvDetalle.getAvaluoId();
            AvAvaluo avaluoIdNew = avDetalle.getAvaluoId();
            if (avaluoIdNew != null) {
                avaluoIdNew = em.getReference(avaluoIdNew.getClass(), avaluoIdNew.getId());
                avDetalle.setAvaluoId(avaluoIdNew);
            }
            avDetalle = em.merge(avDetalle);
            if (avaluoIdOld != null && !avaluoIdOld.equals(avaluoIdNew)) {
                avaluoIdOld.getAvDetalleList().remove(avDetalle);
                avaluoIdOld = em.merge(avaluoIdOld);
            }
            if (avaluoIdNew != null && !avaluoIdNew.equals(avaluoIdOld)) {
                avaluoIdNew.getAvDetalleList().add(avDetalle);
                avaluoIdNew = em.merge(avaluoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avDetalle.getId();
                if (findAvDetalle(id) == null) {
                    throw new NonexistentEntityException("The avDetalle with id " + id + " no longer exists.");
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
            AvDetalle avDetalle;
            try {
                avDetalle = em.getReference(AvDetalle.class, id);
                avDetalle.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avDetalle with id " + id + " no longer exists.", enfe);
            }
            AvAvaluo avaluoId = avDetalle.getAvaluoId();
            if (avaluoId != null) {
                avaluoId.getAvDetalleList().remove(avDetalle);
                avaluoId = em.merge(avaluoId);
            }
            em.remove(avDetalle);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvDetalle> findAvDetalleEntities() {
        return findAvDetalleEntities(true, -1, -1);
    }

    public List<AvDetalle> findAvDetalleEntities(int maxResults, int firstResult) {
        return findAvDetalleEntities(false, maxResults, firstResult);
    }

    private List<AvDetalle> findAvDetalleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvDetalle.class));
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

    public AvDetalle findAvDetalle(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvDetalle.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvDetalleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvDetalle> rt = cq.from(AvDetalle.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
