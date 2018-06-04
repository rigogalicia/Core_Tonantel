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
public class PtmBienesmueblesJpaController implements Serializable {

    public PtmBienesmueblesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmBienesmuebles ptmBienesmuebles) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmEstadopatrimonial ptmEstadopatrimonial = ptmBienesmuebles.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial = em.getReference(ptmEstadopatrimonial.getClass(), ptmEstadopatrimonial.getPtmEstadopatrimonialPK());
                ptmBienesmuebles.setPtmEstadopatrimonial(ptmEstadopatrimonial);
            }
            em.persist(ptmBienesmuebles);
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial.getPtmBienesmueblesList().add(ptmBienesmuebles);
                ptmEstadopatrimonial = em.merge(ptmEstadopatrimonial);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmBienesmuebles ptmBienesmuebles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmBienesmuebles persistentPtmBienesmuebles = em.find(PtmBienesmuebles.class, ptmBienesmuebles.getIdbienesmuebles());
            PtmEstadopatrimonial ptmEstadopatrimonialOld = persistentPtmBienesmuebles.getPtmEstadopatrimonial();
            PtmEstadopatrimonial ptmEstadopatrimonialNew = ptmBienesmuebles.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonialNew != null) {
                ptmEstadopatrimonialNew = em.getReference(ptmEstadopatrimonialNew.getClass(), ptmEstadopatrimonialNew.getPtmEstadopatrimonialPK());
                ptmBienesmuebles.setPtmEstadopatrimonial(ptmEstadopatrimonialNew);
            }
            ptmBienesmuebles = em.merge(ptmBienesmuebles);
            if (ptmEstadopatrimonialOld != null && !ptmEstadopatrimonialOld.equals(ptmEstadopatrimonialNew)) {
                ptmEstadopatrimonialOld.getPtmBienesmueblesList().remove(ptmBienesmuebles);
                ptmEstadopatrimonialOld = em.merge(ptmEstadopatrimonialOld);
            }
            if (ptmEstadopatrimonialNew != null && !ptmEstadopatrimonialNew.equals(ptmEstadopatrimonialOld)) {
                ptmEstadopatrimonialNew.getPtmBienesmueblesList().add(ptmBienesmuebles);
                ptmEstadopatrimonialNew = em.merge(ptmEstadopatrimonialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ptmBienesmuebles.getIdbienesmuebles();
                if (findPtmBienesmuebles(id) == null) {
                    throw new NonexistentEntityException("The ptmBienesmuebles with id " + id + " no longer exists.");
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
            PtmBienesmuebles ptmBienesmuebles;
            try {
                ptmBienesmuebles = em.getReference(PtmBienesmuebles.class, id);
                ptmBienesmuebles.getIdbienesmuebles();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmBienesmuebles with id " + id + " no longer exists.", enfe);
            }
            PtmEstadopatrimonial ptmEstadopatrimonial = ptmBienesmuebles.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial.getPtmBienesmueblesList().remove(ptmBienesmuebles);
                ptmEstadopatrimonial = em.merge(ptmEstadopatrimonial);
            }
            em.remove(ptmBienesmuebles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmBienesmuebles> findPtmBienesmueblesEntities() {
        return findPtmBienesmueblesEntities(true, -1, -1);
    }

    public List<PtmBienesmuebles> findPtmBienesmueblesEntities(int maxResults, int firstResult) {
        return findPtmBienesmueblesEntities(false, maxResults, firstResult);
    }

    private List<PtmBienesmuebles> findPtmBienesmueblesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmBienesmuebles.class));
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

    public PtmBienesmuebles findPtmBienesmuebles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmBienesmuebles.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmBienesmueblesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmBienesmuebles> rt = cq.from(PtmBienesmuebles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
