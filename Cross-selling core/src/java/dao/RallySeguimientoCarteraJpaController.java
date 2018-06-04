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
public class RallySeguimientoCarteraJpaController implements Serializable {

    public RallySeguimientoCarteraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallySeguimientoCartera rallySeguimientoCartera) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rallySeguimientoCartera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallySeguimientoCartera rallySeguimientoCartera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rallySeguimientoCartera = em.merge(rallySeguimientoCartera);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallySeguimientoCartera.getId();
                if (findRallySeguimientoCartera(id) == null) {
                    throw new NonexistentEntityException("The rallySeguimientoCartera with id " + id + " no longer exists.");
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
            RallySeguimientoCartera rallySeguimientoCartera;
            try {
                rallySeguimientoCartera = em.getReference(RallySeguimientoCartera.class, id);
                rallySeguimientoCartera.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallySeguimientoCartera with id " + id + " no longer exists.", enfe);
            }
            em.remove(rallySeguimientoCartera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallySeguimientoCartera> findRallySeguimientoCarteraEntities() {
        return findRallySeguimientoCarteraEntities(true, -1, -1);
    }

    public List<RallySeguimientoCartera> findRallySeguimientoCarteraEntities(int maxResults, int firstResult) {
        return findRallySeguimientoCarteraEntities(false, maxResults, firstResult);
    }

    private List<RallySeguimientoCartera> findRallySeguimientoCarteraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallySeguimientoCartera.class));
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

    public RallySeguimientoCartera findRallySeguimientoCartera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallySeguimientoCartera.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallySeguimientoCarteraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallySeguimientoCartera> rt = cq.from(RallySeguimientoCartera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
