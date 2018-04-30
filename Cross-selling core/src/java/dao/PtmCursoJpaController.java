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
public class PtmCursoJpaController implements Serializable {

    public PtmCursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmCurso ptmCurso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmColaborador ptmColaboradorDpi = ptmCurso.getPtmColaboradorDpi();
            if (ptmColaboradorDpi != null) {
                ptmColaboradorDpi = em.getReference(ptmColaboradorDpi.getClass(), ptmColaboradorDpi.getDpi());
                ptmCurso.setPtmColaboradorDpi(ptmColaboradorDpi);
            }
            em.persist(ptmCurso);
            if (ptmColaboradorDpi != null) {
                ptmColaboradorDpi.getPtmCursoList().add(ptmCurso);
                ptmColaboradorDpi = em.merge(ptmColaboradorDpi);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmCurso ptmCurso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmCurso persistentPtmCurso = em.find(PtmCurso.class, ptmCurso.getIdcurso());
            PtmColaborador ptmColaboradorDpiOld = persistentPtmCurso.getPtmColaboradorDpi();
            PtmColaborador ptmColaboradorDpiNew = ptmCurso.getPtmColaboradorDpi();
            if (ptmColaboradorDpiNew != null) {
                ptmColaboradorDpiNew = em.getReference(ptmColaboradorDpiNew.getClass(), ptmColaboradorDpiNew.getDpi());
                ptmCurso.setPtmColaboradorDpi(ptmColaboradorDpiNew);
            }
            ptmCurso = em.merge(ptmCurso);
            if (ptmColaboradorDpiOld != null && !ptmColaboradorDpiOld.equals(ptmColaboradorDpiNew)) {
                ptmColaboradorDpiOld.getPtmCursoList().remove(ptmCurso);
                ptmColaboradorDpiOld = em.merge(ptmColaboradorDpiOld);
            }
            if (ptmColaboradorDpiNew != null && !ptmColaboradorDpiNew.equals(ptmColaboradorDpiOld)) {
                ptmColaboradorDpiNew.getPtmCursoList().add(ptmCurso);
                ptmColaboradorDpiNew = em.merge(ptmColaboradorDpiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ptmCurso.getIdcurso();
                if (findPtmCurso(id) == null) {
                    throw new NonexistentEntityException("The ptmCurso with id " + id + " no longer exists.");
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
            PtmCurso ptmCurso;
            try {
                ptmCurso = em.getReference(PtmCurso.class, id);
                ptmCurso.getIdcurso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmCurso with id " + id + " no longer exists.", enfe);
            }
            PtmColaborador ptmColaboradorDpi = ptmCurso.getPtmColaboradorDpi();
            if (ptmColaboradorDpi != null) {
                ptmColaboradorDpi.getPtmCursoList().remove(ptmCurso);
                ptmColaboradorDpi = em.merge(ptmColaboradorDpi);
            }
            em.remove(ptmCurso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmCurso> findPtmCursoEntities() {
        return findPtmCursoEntities(true, -1, -1);
    }

    public List<PtmCurso> findPtmCursoEntities(int maxResults, int firstResult) {
        return findPtmCursoEntities(false, maxResults, firstResult);
    }

    private List<PtmCurso> findPtmCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmCurso.class));
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

    public PtmCurso findPtmCurso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmCurso.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmCurso> rt = cq.from(PtmCurso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
