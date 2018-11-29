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
public class PtmPrestamoJpaController implements Serializable {

    public PtmPrestamoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmPrestamo ptmPrestamo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmEstadopatrimonial ptmEstadopatrimonial = ptmPrestamo.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial = em.getReference(ptmEstadopatrimonial.getClass(), ptmEstadopatrimonial.getPtmEstadopatrimonialPK());
                ptmPrestamo.setPtmEstadopatrimonial(ptmEstadopatrimonial);
            }
            em.persist(ptmPrestamo);
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial.getPtmPrestamoList().add(ptmPrestamo);
                ptmEstadopatrimonial = em.merge(ptmEstadopatrimonial);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmPrestamo ptmPrestamo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmPrestamo persistentPtmPrestamo = em.find(PtmPrestamo.class, ptmPrestamo.getIdprestamo());
            PtmEstadopatrimonial ptmEstadopatrimonialOld = persistentPtmPrestamo.getPtmEstadopatrimonial();
            PtmEstadopatrimonial ptmEstadopatrimonialNew = ptmPrestamo.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonialNew != null) {
                ptmEstadopatrimonialNew = em.getReference(ptmEstadopatrimonialNew.getClass(), ptmEstadopatrimonialNew.getPtmEstadopatrimonialPK());
                ptmPrestamo.setPtmEstadopatrimonial(ptmEstadopatrimonialNew);
            }
            ptmPrestamo = em.merge(ptmPrestamo);
            if (ptmEstadopatrimonialOld != null && !ptmEstadopatrimonialOld.equals(ptmEstadopatrimonialNew)) {
                ptmEstadopatrimonialOld.getPtmPrestamoList().remove(ptmPrestamo);
                ptmEstadopatrimonialOld = em.merge(ptmEstadopatrimonialOld);
            }
            if (ptmEstadopatrimonialNew != null && !ptmEstadopatrimonialNew.equals(ptmEstadopatrimonialOld)) {
                ptmEstadopatrimonialNew.getPtmPrestamoList().add(ptmPrestamo);
                ptmEstadopatrimonialNew = em.merge(ptmEstadopatrimonialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ptmPrestamo.getIdprestamo();
                if (findPtmPrestamo(id) == null) {
                    throw new NonexistentEntityException("The ptmPrestamo with id " + id + " no longer exists.");
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
            PtmPrestamo ptmPrestamo;
            try {
                ptmPrestamo = em.getReference(PtmPrestamo.class, id);
                ptmPrestamo.getIdprestamo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmPrestamo with id " + id + " no longer exists.", enfe);
            }
            PtmEstadopatrimonial ptmEstadopatrimonial = ptmPrestamo.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial.getPtmPrestamoList().remove(ptmPrestamo);
                ptmEstadopatrimonial = em.merge(ptmEstadopatrimonial);
            }
            em.remove(ptmPrestamo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmPrestamo> findPtmPrestamoEntities() {
        return findPtmPrestamoEntities(true, -1, -1);
    }

    public List<PtmPrestamo> findPtmPrestamoEntities(int maxResults, int firstResult) {
        return findPtmPrestamoEntities(false, maxResults, firstResult);
    }

    private List<PtmPrestamo> findPtmPrestamoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmPrestamo.class));
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

    public PtmPrestamo findPtmPrestamo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmPrestamo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmPrestamoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmPrestamo> rt = cq.from(PtmPrestamo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
