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
public class PtmTarjetacreditoJpaController implements Serializable {

    public PtmTarjetacreditoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmTarjetacredito ptmTarjetacredito) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmEstadopatrimonial ptmEstadopatrimonial = ptmTarjetacredito.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial = em.getReference(ptmEstadopatrimonial.getClass(), ptmEstadopatrimonial.getPtmEstadopatrimonialPK());
                ptmTarjetacredito.setPtmEstadopatrimonial(ptmEstadopatrimonial);
            }
            em.persist(ptmTarjetacredito);
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial.getPtmTarjetacreditoList().add(ptmTarjetacredito);
                ptmEstadopatrimonial = em.merge(ptmEstadopatrimonial);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmTarjetacredito ptmTarjetacredito) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmTarjetacredito persistentPtmTarjetacredito = em.find(PtmTarjetacredito.class, ptmTarjetacredito.getIdtarjetacredito());
            PtmEstadopatrimonial ptmEstadopatrimonialOld = persistentPtmTarjetacredito.getPtmEstadopatrimonial();
            PtmEstadopatrimonial ptmEstadopatrimonialNew = ptmTarjetacredito.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonialNew != null) {
                ptmEstadopatrimonialNew = em.getReference(ptmEstadopatrimonialNew.getClass(), ptmEstadopatrimonialNew.getPtmEstadopatrimonialPK());
                ptmTarjetacredito.setPtmEstadopatrimonial(ptmEstadopatrimonialNew);
            }
            ptmTarjetacredito = em.merge(ptmTarjetacredito);
            if (ptmEstadopatrimonialOld != null && !ptmEstadopatrimonialOld.equals(ptmEstadopatrimonialNew)) {
                ptmEstadopatrimonialOld.getPtmTarjetacreditoList().remove(ptmTarjetacredito);
                ptmEstadopatrimonialOld = em.merge(ptmEstadopatrimonialOld);
            }
            if (ptmEstadopatrimonialNew != null && !ptmEstadopatrimonialNew.equals(ptmEstadopatrimonialOld)) {
                ptmEstadopatrimonialNew.getPtmTarjetacreditoList().add(ptmTarjetacredito);
                ptmEstadopatrimonialNew = em.merge(ptmEstadopatrimonialNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ptmTarjetacredito.getIdtarjetacredito();
                if (findPtmTarjetacredito(id) == null) {
                    throw new NonexistentEntityException("The ptmTarjetacredito with id " + id + " no longer exists.");
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
            PtmTarjetacredito ptmTarjetacredito;
            try {
                ptmTarjetacredito = em.getReference(PtmTarjetacredito.class, id);
                ptmTarjetacredito.getIdtarjetacredito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmTarjetacredito with id " + id + " no longer exists.", enfe);
            }
            PtmEstadopatrimonial ptmEstadopatrimonial = ptmTarjetacredito.getPtmEstadopatrimonial();
            if (ptmEstadopatrimonial != null) {
                ptmEstadopatrimonial.getPtmTarjetacreditoList().remove(ptmTarjetacredito);
                ptmEstadopatrimonial = em.merge(ptmEstadopatrimonial);
            }
            em.remove(ptmTarjetacredito);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmTarjetacredito> findPtmTarjetacreditoEntities() {
        return findPtmTarjetacreditoEntities(true, -1, -1);
    }

    public List<PtmTarjetacredito> findPtmTarjetacreditoEntities(int maxResults, int firstResult) {
        return findPtmTarjetacreditoEntities(false, maxResults, firstResult);
    }

    private List<PtmTarjetacredito> findPtmTarjetacreditoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmTarjetacredito.class));
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

    public PtmTarjetacredito findPtmTarjetacredito(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmTarjetacredito.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmTarjetacreditoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmTarjetacredito> rt = cq.from(PtmTarjetacredito.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
