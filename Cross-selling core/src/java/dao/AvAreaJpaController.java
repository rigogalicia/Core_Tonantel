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
public class AvAreaJpaController implements Serializable {

    public AvAreaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvArea avArea) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvInmueble inmuebleId = avArea.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId = em.getReference(inmuebleId.getClass(), inmuebleId.getId());
                avArea.setInmuebleId(inmuebleId);
            }
            em.persist(avArea);
            if (inmuebleId != null) {
                inmuebleId.getAvAreaList().add(avArea);
                inmuebleId = em.merge(inmuebleId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvArea avArea) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvArea persistentAvArea = em.find(AvArea.class, avArea.getId());
            AvInmueble inmuebleIdOld = persistentAvArea.getInmuebleId();
            AvInmueble inmuebleIdNew = avArea.getInmuebleId();
            if (inmuebleIdNew != null) {
                inmuebleIdNew = em.getReference(inmuebleIdNew.getClass(), inmuebleIdNew.getId());
                avArea.setInmuebleId(inmuebleIdNew);
            }
            avArea = em.merge(avArea);
            if (inmuebleIdOld != null && !inmuebleIdOld.equals(inmuebleIdNew)) {
                inmuebleIdOld.getAvAreaList().remove(avArea);
                inmuebleIdOld = em.merge(inmuebleIdOld);
            }
            if (inmuebleIdNew != null && !inmuebleIdNew.equals(inmuebleIdOld)) {
                inmuebleIdNew.getAvAreaList().add(avArea);
                inmuebleIdNew = em.merge(inmuebleIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avArea.getId();
                if (findAvArea(id) == null) {
                    throw new NonexistentEntityException("The avArea with id " + id + " no longer exists.");
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
            AvArea avArea;
            try {
                avArea = em.getReference(AvArea.class, id);
                avArea.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avArea with id " + id + " no longer exists.", enfe);
            }
            AvInmueble inmuebleId = avArea.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId.getAvAreaList().remove(avArea);
                inmuebleId = em.merge(inmuebleId);
            }
            em.remove(avArea);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvArea> findAvAreaEntities() {
        return findAvAreaEntities(true, -1, -1);
    }

    public List<AvArea> findAvAreaEntities(int maxResults, int firstResult) {
        return findAvAreaEntities(false, maxResults, firstResult);
    }

    private List<AvArea> findAvAreaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvArea.class));
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

    public AvArea findAvArea(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvArea.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvAreaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvArea> rt = cq.from(AvArea.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
