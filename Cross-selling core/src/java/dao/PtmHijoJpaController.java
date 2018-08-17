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
public class PtmHijoJpaController implements Serializable {

    public PtmHijoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmHijo ptmHijo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmColaborador ptmColaboradorDpi = ptmHijo.getPtmColaboradorDpi();
            if (ptmColaboradorDpi != null) {
                ptmColaboradorDpi = em.getReference(ptmColaboradorDpi.getClass(), ptmColaboradorDpi.getDpi());
                ptmHijo.setPtmColaboradorDpi(ptmColaboradorDpi);
            }
            em.persist(ptmHijo);
            if (ptmColaboradorDpi != null) {
                ptmColaboradorDpi.getPtmHijoList().add(ptmHijo);
                ptmColaboradorDpi = em.merge(ptmColaboradorDpi);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmHijo ptmHijo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmHijo persistentPtmHijo = em.find(PtmHijo.class, ptmHijo.getIdhijo());
            PtmColaborador ptmColaboradorDpiOld = persistentPtmHijo.getPtmColaboradorDpi();
            PtmColaborador ptmColaboradorDpiNew = ptmHijo.getPtmColaboradorDpi();
            if (ptmColaboradorDpiNew != null) {
                ptmColaboradorDpiNew = em.getReference(ptmColaboradorDpiNew.getClass(), ptmColaboradorDpiNew.getDpi());
                ptmHijo.setPtmColaboradorDpi(ptmColaboradorDpiNew);
            }
            ptmHijo = em.merge(ptmHijo);
            if (ptmColaboradorDpiOld != null && !ptmColaboradorDpiOld.equals(ptmColaboradorDpiNew)) {
                ptmColaboradorDpiOld.getPtmHijoList().remove(ptmHijo);
                ptmColaboradorDpiOld = em.merge(ptmColaboradorDpiOld);
            }
            if (ptmColaboradorDpiNew != null && !ptmColaboradorDpiNew.equals(ptmColaboradorDpiOld)) {
                ptmColaboradorDpiNew.getPtmHijoList().add(ptmHijo);
                ptmColaboradorDpiNew = em.merge(ptmColaboradorDpiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ptmHijo.getIdhijo();
                if (findPtmHijo(id) == null) {
                    throw new NonexistentEntityException("The ptmHijo with id " + id + " no longer exists.");
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
            PtmHijo ptmHijo;
            try {
                ptmHijo = em.getReference(PtmHijo.class, id);
                ptmHijo.getIdhijo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmHijo with id " + id + " no longer exists.", enfe);
            }
            PtmColaborador ptmColaboradorDpi = ptmHijo.getPtmColaboradorDpi();
            if (ptmColaboradorDpi != null) {
                ptmColaboradorDpi.getPtmHijoList().remove(ptmHijo);
                ptmColaboradorDpi = em.merge(ptmColaboradorDpi);
            }
            em.remove(ptmHijo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmHijo> findPtmHijoEntities() {
        return findPtmHijoEntities(true, -1, -1);
    }

    public List<PtmHijo> findPtmHijoEntities(int maxResults, int firstResult) {
        return findPtmHijoEntities(false, maxResults, firstResult);
    }

    private List<PtmHijo> findPtmHijoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmHijo.class));
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

    public PtmHijo findPtmHijo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmHijo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmHijoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmHijo> rt = cq.from(PtmHijo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
