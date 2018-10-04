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
 * @author r29galicia
 */
public class AvColindanteJpaController implements Serializable {

    public AvColindanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvColindante avColindante) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvInmueble inmuebleId = avColindante.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId = em.getReference(inmuebleId.getClass(), inmuebleId.getId());
                avColindante.setInmuebleId(inmuebleId);
            }
            em.persist(avColindante);
            if (inmuebleId != null) {
                inmuebleId.getAvColindanteList().add(avColindante);
                inmuebleId = em.merge(inmuebleId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvColindante avColindante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvColindante persistentAvColindante = em.find(AvColindante.class, avColindante.getId());
            AvInmueble inmuebleIdOld = persistentAvColindante.getInmuebleId();
            AvInmueble inmuebleIdNew = avColindante.getInmuebleId();
            if (inmuebleIdNew != null) {
                inmuebleIdNew = em.getReference(inmuebleIdNew.getClass(), inmuebleIdNew.getId());
                avColindante.setInmuebleId(inmuebleIdNew);
            }
            avColindante = em.merge(avColindante);
            if (inmuebleIdOld != null && !inmuebleIdOld.equals(inmuebleIdNew)) {
                inmuebleIdOld.getAvColindanteList().remove(avColindante);
                inmuebleIdOld = em.merge(inmuebleIdOld);
            }
            if (inmuebleIdNew != null && !inmuebleIdNew.equals(inmuebleIdOld)) {
                inmuebleIdNew.getAvColindanteList().add(avColindante);
                inmuebleIdNew = em.merge(inmuebleIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avColindante.getId();
                if (findAvColindante(id) == null) {
                    throw new NonexistentEntityException("The avColindante with id " + id + " no longer exists.");
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
            AvColindante avColindante;
            try {
                avColindante = em.getReference(AvColindante.class, id);
                avColindante.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avColindante with id " + id + " no longer exists.", enfe);
            }
            AvInmueble inmuebleId = avColindante.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId.getAvColindanteList().remove(avColindante);
                inmuebleId = em.merge(inmuebleId);
            }
            em.remove(avColindante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvColindante> findAvColindanteEntities() {
        return findAvColindanteEntities(true, -1, -1);
    }

    public List<AvColindante> findAvColindanteEntities(int maxResults, int firstResult) {
        return findAvColindanteEntities(false, maxResults, firstResult);
    }

    private List<AvColindante> findAvColindanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvColindante.class));
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

    public AvColindante findAvColindante(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvColindante.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvColindanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvColindante> rt = cq.from(AvColindante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
