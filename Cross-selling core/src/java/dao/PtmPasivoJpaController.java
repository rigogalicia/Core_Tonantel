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
 * @author Rgalicia
 */
public class PtmPasivoJpaController implements Serializable {

    public PtmPasivoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmPasivo ptmPasivo) {
        if (ptmPasivo.getPtmEstadopatrimonialList() == null) {
            ptmPasivo.setPtmEstadopatrimonialList(new ArrayList<PtmEstadopatrimonial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PtmEstadopatrimonial> attachedPtmEstadopatrimonialList = new ArrayList<PtmEstadopatrimonial>();
            for (PtmEstadopatrimonial ptmEstadopatrimonialListPtmEstadopatrimonialToAttach : ptmPasivo.getPtmEstadopatrimonialList()) {
                ptmEstadopatrimonialListPtmEstadopatrimonialToAttach = em.getReference(ptmEstadopatrimonialListPtmEstadopatrimonialToAttach.getClass(), ptmEstadopatrimonialListPtmEstadopatrimonialToAttach.getPtmEstadopatrimonialPK());
                attachedPtmEstadopatrimonialList.add(ptmEstadopatrimonialListPtmEstadopatrimonialToAttach);
            }
            ptmPasivo.setPtmEstadopatrimonialList(attachedPtmEstadopatrimonialList);
            em.persist(ptmPasivo);
            for (PtmEstadopatrimonial ptmEstadopatrimonialListPtmEstadopatrimonial : ptmPasivo.getPtmEstadopatrimonialList()) {
                PtmPasivo oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListPtmEstadopatrimonial = ptmEstadopatrimonialListPtmEstadopatrimonial.getPtmPasivoIdpasivo();
                ptmEstadopatrimonialListPtmEstadopatrimonial.setPtmPasivoIdpasivo(ptmPasivo);
                ptmEstadopatrimonialListPtmEstadopatrimonial = em.merge(ptmEstadopatrimonialListPtmEstadopatrimonial);
                if (oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListPtmEstadopatrimonial != null) {
                    oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListPtmEstadopatrimonial.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonialListPtmEstadopatrimonial);
                    oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListPtmEstadopatrimonial = em.merge(oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListPtmEstadopatrimonial);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmPasivo ptmPasivo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmPasivo persistentPtmPasivo = em.find(PtmPasivo.class, ptmPasivo.getIdpasivo());
            List<PtmEstadopatrimonial> ptmEstadopatrimonialListOld = persistentPtmPasivo.getPtmEstadopatrimonialList();
            List<PtmEstadopatrimonial> ptmEstadopatrimonialListNew = ptmPasivo.getPtmEstadopatrimonialList();
            List<String> illegalOrphanMessages = null;
            for (PtmEstadopatrimonial ptmEstadopatrimonialListOldPtmEstadopatrimonial : ptmEstadopatrimonialListOld) {
                if (!ptmEstadopatrimonialListNew.contains(ptmEstadopatrimonialListOldPtmEstadopatrimonial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PtmEstadopatrimonial " + ptmEstadopatrimonialListOldPtmEstadopatrimonial + " since its ptmPasivoIdpasivo field is not nullable.");
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
            ptmPasivo.setPtmEstadopatrimonialList(ptmEstadopatrimonialListNew);
            ptmPasivo = em.merge(ptmPasivo);
            for (PtmEstadopatrimonial ptmEstadopatrimonialListNewPtmEstadopatrimonial : ptmEstadopatrimonialListNew) {
                if (!ptmEstadopatrimonialListOld.contains(ptmEstadopatrimonialListNewPtmEstadopatrimonial)) {
                    PtmPasivo oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial = ptmEstadopatrimonialListNewPtmEstadopatrimonial.getPtmPasivoIdpasivo();
                    ptmEstadopatrimonialListNewPtmEstadopatrimonial.setPtmPasivoIdpasivo(ptmPasivo);
                    ptmEstadopatrimonialListNewPtmEstadopatrimonial = em.merge(ptmEstadopatrimonialListNewPtmEstadopatrimonial);
                    if (oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial != null && !oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial.equals(ptmPasivo)) {
                        oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonialListNewPtmEstadopatrimonial);
                        oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial = em.merge(oldPtmPasivoIdpasivoOfPtmEstadopatrimonialListNewPtmEstadopatrimonial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ptmPasivo.getIdpasivo();
                if (findPtmPasivo(id) == null) {
                    throw new NonexistentEntityException("The ptmPasivo with id " + id + " no longer exists.");
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
            PtmPasivo ptmPasivo;
            try {
                ptmPasivo = em.getReference(PtmPasivo.class, id);
                ptmPasivo.getIdpasivo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmPasivo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PtmEstadopatrimonial> ptmEstadopatrimonialListOrphanCheck = ptmPasivo.getPtmEstadopatrimonialList();
            for (PtmEstadopatrimonial ptmEstadopatrimonialListOrphanCheckPtmEstadopatrimonial : ptmEstadopatrimonialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PtmPasivo (" + ptmPasivo + ") cannot be destroyed since the PtmEstadopatrimonial " + ptmEstadopatrimonialListOrphanCheckPtmEstadopatrimonial + " in its ptmEstadopatrimonialList field has a non-nullable ptmPasivoIdpasivo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ptmPasivo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmPasivo> findPtmPasivoEntities() {
        return findPtmPasivoEntities(true, -1, -1);
    }

    public List<PtmPasivo> findPtmPasivoEntities(int maxResults, int firstResult) {
        return findPtmPasivoEntities(false, maxResults, firstResult);
    }

    private List<PtmPasivo> findPtmPasivoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmPasivo.class));
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

    public PtmPasivo findPtmPasivo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmPasivo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmPasivoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmPasivo> rt = cq.from(PtmPasivo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
