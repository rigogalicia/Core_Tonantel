/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
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
public class AvPuntocardinalJpaController implements Serializable {

    public AvPuntocardinalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvPuntocardinal avPuntocardinal) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(avPuntocardinal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAvPuntocardinal(avPuntocardinal.getId()) != null) {
                throw new PreexistingEntityException("AvPuntocardinal " + avPuntocardinal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvPuntocardinal avPuntocardinal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            avPuntocardinal = em.merge(avPuntocardinal);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = avPuntocardinal.getId();
                if (findAvPuntocardinal(id) == null) {
                    throw new NonexistentEntityException("The avPuntocardinal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvPuntocardinal avPuntocardinal;
            try {
                avPuntocardinal = em.getReference(AvPuntocardinal.class, id);
                avPuntocardinal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avPuntocardinal with id " + id + " no longer exists.", enfe);
            }
            em.remove(avPuntocardinal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvPuntocardinal> findAvPuntocardinalEntities() {
        return findAvPuntocardinalEntities(true, -1, -1);
    }

    public List<AvPuntocardinal> findAvPuntocardinalEntities(int maxResults, int firstResult) {
        return findAvPuntocardinalEntities(false, maxResults, firstResult);
    }

    private List<AvPuntocardinal> findAvPuntocardinalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvPuntocardinal.class));
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

    public AvPuntocardinal findAvPuntocardinal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvPuntocardinal.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvPuntocardinalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvPuntocardinal> rt = cq.from(AvPuntocardinal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
