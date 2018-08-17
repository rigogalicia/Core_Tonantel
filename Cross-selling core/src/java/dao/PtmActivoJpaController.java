/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author r29galicia
 */
public class PtmActivoJpaController implements Serializable {

    public PtmActivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmActivo ptmActivo) {
        if (ptmActivo.getPtmEstadopatrimonialList() == null) {
            ptmActivo.setPtmEstadopatrimonialList(new ArrayList<PtmEstadopatrimonial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PtmEstadopatrimonial> attachedPtmEstadopatrimonialList = new ArrayList<PtmEstadopatrimonial>();
            for (PtmEstadopatrimonial ptmEstadopatrimonialListPtmEstadopatrimonialToAttach : ptmActivo.getPtmEstadopatrimonialList()) {
                ptmEstadopatrimonialListPtmEstadopatrimonialToAttach = em.getReference(ptmEstadopatrimonialListPtmEstadopatrimonialToAttach.getClass(), ptmEstadopatrimonialListPtmEstadopatrimonialToAttach.getPtmEstadopatrimonialPK());
                attachedPtmEstadopatrimonialList.add(ptmEstadopatrimonialListPtmEstadopatrimonialToAttach);
            }
            ptmActivo.setPtmEstadopatrimonialList(attachedPtmEstadopatrimonialList);
            em.persist(ptmActivo);
            for (PtmEstadopatrimonial ptmEstadopatrimonialListPtmEstadopatrimonial : ptmActivo.getPtmEstadopatrimonialList()) {
                PtmActivo oldPtmActivoIdactivoOfPtmEstadopatrimonialListPtmEstadopatrimonial = ptmEstadopatrimonialListPtmEstadopatrimonial.getPtmActivoIdactivo();
                ptmEstadopatrimonialListPtmEstadopatrimonial.setPtmActivoIdactivo(ptmActivo);
                ptmEstadopatrimonialListPtmEstadopatrimonial = em.merge(ptmEstadopatrimonialListPtmEstadopatrimonial);
                if (oldPtmActivoIdactivoOfPtmEstadopatrimonialListPtmEstadopatrimonial != null) {
                    oldPtmActivoIdactivoOfPtmEstadopatrimonialListPtmEstadopatrimonial.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonialListPtmEstadopatrimonial);
                    oldPtmActivoIdactivoOfPtmEstadopatrimonialListPtmEstadopatrimonial = em.merge(oldPtmActivoIdactivoOfPtmEstadopatrimonialListPtmEstadopatrimonial);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmActivo ptmActivo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmActivo persistentPtmActivo = em.find(PtmActivo.class, ptmActivo.getIdactivo());
            List<PtmEstadopatrimonial> ptmEstadopatrimonialListOld = persistentPtmActivo.getPtmEstadopatrimonialList();
            List<PtmEstadopatrimonial> ptmEstadopatrimonialListNew = ptmActivo.getPtmEstadopatrimonialList();
            List<String> illegalOrphanMessages = null;
            for (PtmEstadopatrimonial ptmEstadopatrimonialListOldPtmEstadopatrimonial : ptmEstadopatrimonialListOld) {
                if (!ptmEstadopatrimonialListNew.contains(ptmEstadopatrimonialListOldPtmEstadopatrimonial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PtmEstadopatrimonial " + ptmEstadopatrimonialListOldPtmEstadopatrimonial + " since its ptmActivoIdactivo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PtmEstadopatrimonial> attachedPtmEstadopatrimonialListNew = new ArrayList<PtmEstadopatrimonial>();
            for (PtmEstadopatrimonial ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach : ptmEstadopatrimonialListNew) {
                ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach = em.getReference(ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach.getClass(), ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach.getPtmEstadopatrimonialPK());
                attachedPtmEstadopatrimonialListNew.add(ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach);
            }
            ptmEstadopatrimonialListNew = attachedPtmEstadopatrimonialListNew;
            ptmActivo.setPtmEstadopatrimonialList(ptmEstadopatrimonialListNew);
            ptmActivo = em.merge(ptmActivo);
            for (PtmEstadopatrimonial ptmEstadopatrimonialListNewPtmEstadopatrimonial : ptmEstadopatrimonialListNew) {
                if (!ptmEstadopatrimonialListOld.contains(ptmEstadopatrimonialListNewPtmEstadopatrimonial)) {
                    PtmActivo oldPtmActivoIdactivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial = ptmEstadopatrimonialListNewPtmEstadopatrimonial.getPtmActivoIdactivo();
                    ptmEstadopatrimonialListNewPtmEstadopatrimonial.setPtmActivoIdactivo(ptmActivo);
                    ptmEstadopatrimonialListNewPtmEstadopatrimonial = em.merge(ptmEstadopatrimonialListNewPtmEstadopatrimonial);
                    if (oldPtmActivoIdactivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial != null && !oldPtmActivoIdactivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial.equals(ptmActivo)) {
                        oldPtmActivoIdactivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonialListNewPtmEstadopatrimonial);
                        oldPtmActivoIdactivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial = em.merge(oldPtmActivoIdactivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ptmActivo.getIdactivo();
                if (findPtmActivo(id) == null) {
                    throw new NonexistentEntityException("The ptmActivo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmActivo ptmActivo;
            try {
                ptmActivo = em.getReference(PtmActivo.class, id);
                ptmActivo.getIdactivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmActivo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PtmEstadopatrimonial> ptmEstadopatrimonialListOrphanCheck = ptmActivo.getPtmEstadopatrimonialList();
            for (PtmEstadopatrimonial ptmEstadopatrimonialListOrphanCheckPtmEstadopatrimonial : ptmEstadopatrimonialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PtmActivo (" + ptmActivo + ") cannot be destroyed since the PtmEstadopatrimonial " + ptmEstadopatrimonialListOrphanCheckPtmEstadopatrimonial + " in its ptmEstadopatrimonialList field has a non-nullable ptmActivoIdactivo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ptmActivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmActivo> findPtmActivoEntities() {
        return findPtmActivoEntities(true, -1, -1);
    }

    public List<PtmActivo> findPtmActivoEntities(int maxResults, int firstResult) {
        return findPtmActivoEntities(false, maxResults, firstResult);
    }

    private List<PtmActivo> findPtmActivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmActivo.class));
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

    public PtmActivo findPtmActivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmActivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmActivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmActivo> rt = cq.from(PtmActivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
