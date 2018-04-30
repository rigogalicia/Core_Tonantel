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
public class PtmBienesinmueblesJpaController implements Serializable {

    public PtmBienesinmueblesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmBienesinmuebles ptmBienesinmuebles) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmEstadopatrimonial ptmEstadopatrimonial = ptmBienesinmuebles.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial = em.getReference(ptmEstadopatrimonial.getClass(), ptmEstadopatrimonial.getPtmEstadopatrimonialPK());
                ptmBienesinmuebles.setPtmEstadopatrimonial(ptmEstadopatrimonial);
            }
            em.persist(ptmBienesinmuebles);
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial.getPtmBienesinmueblesList().add(ptmBienesinmuebles);
                ptmEstadopatrimonial = em.merge(ptmEstadopatrimonial);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmBienesinmuebles ptmBienesinmuebles) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmBienesinmuebles persistentPtmBienesinmuebles = em.find(PtmBienesinmuebles.class, ptmBienesinmuebles.getIdbienesinmuebles());
            PtmEstadopatrimonial ptmEstadopatrimonialOld = persistentPtmBienesinmuebles.getPtmEstadopatrimonial();
            PtmEstadopatrimonial ptmEstadopatrimonialNew = ptmBienesinmuebles.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonialNew != null) {
                ptmEstadopatrimonialNew = em.getReference(ptmEstadopatrimonialNew.getClass(), ptmEstadopatrimonialNew.getPtmEstadopatrimonialPK());
                ptmBienesinmuebles.setPtmEstadopatrimonial(ptmEstadopatrimonialNew);
            }
            ptmBienesinmuebles = em.merge(ptmBienesinmuebles);
            if (ptmEstadopatrimonialOld != null && !ptmEstadopatrimonialOld.equals(ptmEstadopatrimonialNew)) {
                ptmEstadopatrimonialOld.getPtmBienesinmueblesList().remove(ptmBienesinmuebles);
                ptmEstadopatrimonialOld = em.merge(ptmEstadopatrimonialOld);
            }
            if (ptmEstadopatrimonialNew != null && !ptmEstadopatrimonialNew.equals(ptmEstadopatrimonialOld)) {
                ptmEstadopatrimonialNew.getPtmBienesinmueblesList().add(ptmBienesinmuebles);
                ptmEstadopatrimonialNew = em.merge(ptmEstadopatrimonialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ptmBienesinmuebles.getIdbienesinmuebles();
                if (findPtmBienesinmuebles(id) == null) {
                    throw new NonexistentEntityException("The ptmBienesinmuebles with id " + id + " no longer exists.");
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
            PtmBienesinmuebles ptmBienesinmuebles;
            try {
                ptmBienesinmuebles = em.getReference(PtmBienesinmuebles.class, id);
                ptmBienesinmuebles.getIdbienesinmuebles();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmBienesinmuebles with id " + id + " no longer exists.", enfe);
            }
            PtmEstadopatrimonial ptmEstadopatrimonial = ptmBienesinmuebles.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial.getPtmBienesinmueblesList().remove(ptmBienesinmuebles);
                ptmEstadopatrimonial = em.merge(ptmEstadopatrimonial);
            }
            em.remove(ptmBienesinmuebles);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmBienesinmuebles> findPtmBienesinmueblesEntities() {
        return findPtmBienesinmueblesEntities(true, -1, -1);
    }

    public List<PtmBienesinmuebles> findPtmBienesinmueblesEntities(int maxResults, int firstResult) {
        return findPtmBienesinmueblesEntities(false, maxResults, firstResult);
    }

    private List<PtmBienesinmuebles> findPtmBienesinmueblesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmBienesinmuebles.class));
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

    public PtmBienesinmuebles findPtmBienesinmuebles(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmBienesinmuebles.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmBienesinmueblesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmBienesinmuebles> rt = cq.from(PtmBienesinmuebles.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
