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
public class RallyCarteraJpaController implements Serializable {

    public RallyCarteraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallyCartera rallyCartera) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rallyCartera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallyCartera rallyCartera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rallyCartera = em.merge(rallyCartera);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallyCartera.getIdcartera();
                if (findRallyCartera(id) == null) {
                    throw new NonexistentEntityException("The rallyCartera with id " + id + " no longer exists.");
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
            RallyCartera rallyCartera;
            try {
                rallyCartera = em.getReference(RallyCartera.class, id);
                rallyCartera.getIdcartera();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallyCartera with id " + id + " no longer exists.", enfe);
            }
            em.remove(rallyCartera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallyCartera> findRallyCarteraEntities() {
        return findRallyCarteraEntities(true, -1, -1);
    }

    public List<RallyCartera> findRallyCarteraEntities(int maxResults, int firstResult) {
        return findRallyCarteraEntities(false, maxResults, firstResult);
    }

    private List<RallyCartera> findRallyCarteraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallyCartera.class));
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

    public RallyCartera findRallyCartera(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallyCartera.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallyCarteraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallyCartera> rt = cq.from(RallyCartera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
