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
public class AvConstruccionJpaController implements Serializable {

    public AvConstruccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvConstruccion avConstruccion) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvInmueble inmuebleId = avConstruccion.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId = em.getReference(inmuebleId.getClass(), inmuebleId.getId());
                avConstruccion.setInmuebleId(inmuebleId);
            }
            em.persist(avConstruccion);
            if (inmuebleId != null) {
                inmuebleId.getAvConstruccionList().add(avConstruccion);
                inmuebleId = em.merge(inmuebleId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvConstruccion avConstruccion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvConstruccion persistentAvConstruccion = em.find(AvConstruccion.class, avConstruccion.getId());
            AvInmueble inmuebleIdOld = persistentAvConstruccion.getInmuebleId();
            AvInmueble inmuebleIdNew = avConstruccion.getInmuebleId();
            if (inmuebleIdNew != null) {
                inmuebleIdNew = em.getReference(inmuebleIdNew.getClass(), inmuebleIdNew.getId());
                avConstruccion.setInmuebleId(inmuebleIdNew);
            }
            avConstruccion = em.merge(avConstruccion);
            if (inmuebleIdOld != null && !inmuebleIdOld.equals(inmuebleIdNew)) {
                inmuebleIdOld.getAvConstruccionList().remove(avConstruccion);
                inmuebleIdOld = em.merge(inmuebleIdOld);
            }
            if (inmuebleIdNew != null && !inmuebleIdNew.equals(inmuebleIdOld)) {
                inmuebleIdNew.getAvConstruccionList().add(avConstruccion);
                inmuebleIdNew = em.merge(inmuebleIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avConstruccion.getId();
                if (findAvConstruccion(id) == null) {
                    throw new NonexistentEntityException("The avConstruccion with id " + id + " no longer exists.");
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
            AvConstruccion avConstruccion;
            try {
                avConstruccion = em.getReference(AvConstruccion.class, id);
                avConstruccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avConstruccion with id " + id + " no longer exists.", enfe);
            }
            AvInmueble inmuebleId = avConstruccion.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId.getAvConstruccionList().remove(avConstruccion);
                inmuebleId = em.merge(inmuebleId);
            }
            em.remove(avConstruccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvConstruccion> findAvConstruccionEntities() {
        return findAvConstruccionEntities(true, -1, -1);
    }

    public List<AvConstruccion> findAvConstruccionEntities(int maxResults, int firstResult) {
        return findAvConstruccionEntities(false, maxResults, firstResult);
    }

    private List<AvConstruccion> findAvConstruccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvConstruccion.class));
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

    public AvConstruccion findAvConstruccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvConstruccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvConstruccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvConstruccion> rt = cq.from(AvConstruccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
