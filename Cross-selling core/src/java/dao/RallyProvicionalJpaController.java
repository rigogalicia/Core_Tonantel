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
public class RallyProvicionalJpaController implements Serializable {

    public RallyProvicionalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallyProvicional rallyProvicional) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(rallyProvicional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallyProvicional rallyProvicional) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            rallyProvicional = em.merge(rallyProvicional);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallyProvicional.getIdprovicional();
                if (findRallyProvicional(id) == null) {
                    throw new NonexistentEntityException("The rallyProvicional with id " + id + " no longer exists.");
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
            RallyProvicional rallyProvicional;
            try {
                rallyProvicional = em.getReference(RallyProvicional.class, id);
                rallyProvicional.getIdprovicional();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallyProvicional with id " + id + " no longer exists.", enfe);
            }
            em.remove(rallyProvicional);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallyProvicional> findRallyProvicionalEntities() {
        return findRallyProvicionalEntities(true, -1, -1);
    }

    public List<RallyProvicional> findRallyProvicionalEntities(int maxResults, int firstResult) {
        return findRallyProvicionalEntities(false, maxResults, firstResult);
    }

    private List<RallyProvicional> findRallyProvicionalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallyProvicional.class));
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

    public RallyProvicional findRallyProvicional(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallyProvicional.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallyProvicionalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallyProvicional> rt = cq.from(RallyProvicional.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
