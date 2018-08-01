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
public class CcBancoJpaController implements Serializable {

    public CcBancoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CcBanco ccBanco) {
        if (ccBanco.getCcChequeList() == null) {
            ccBanco.setCcChequeList(new ArrayList<CcCheque>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CcCheque> attachedCcChequeList = new ArrayList<CcCheque>();
            for (CcCheque ccChequeListCcChequeToAttach : ccBanco.getCcChequeList()) {
                ccChequeListCcChequeToAttach = em.getReference(ccChequeListCcChequeToAttach.getClass(), ccChequeListCcChequeToAttach.getNumero());
                attachedCcChequeList.add(ccChequeListCcChequeToAttach);
            }
            ccBanco.setCcChequeList(attachedCcChequeList);
            em.persist(ccBanco);
            for (CcCheque ccChequeListCcCheque : ccBanco.getCcChequeList()) {
                CcBanco oldBancoIdOfCcChequeListCcCheque = ccChequeListCcCheque.getBancoId();
                ccChequeListCcCheque.setBancoId(ccBanco);
                ccChequeListCcCheque = em.merge(ccChequeListCcCheque);
                if (oldBancoIdOfCcChequeListCcCheque != null) {
                    oldBancoIdOfCcChequeListCcCheque.getCcChequeList().remove(ccChequeListCcCheque);
                    oldBancoIdOfCcChequeListCcCheque = em.merge(oldBancoIdOfCcChequeListCcCheque);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CcBanco ccBanco) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CcBanco persistentCcBanco = em.find(CcBanco.class, ccBanco.getId());
            List<CcCheque> ccChequeListOld = persistentCcBanco.getCcChequeList();
            List<CcCheque> ccChequeListNew = ccBanco.getCcChequeList();
            List<String> illegalOrphanMessages = null;
            for (CcCheque ccChequeListOldCcCheque : ccChequeListOld) {
                if (!ccChequeListNew.contains(ccChequeListOldCcCheque)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CcCheque " + ccChequeListOldCcCheque + " since its bancoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<CcCheque> attachedCcChequeListNew = new ArrayList<CcCheque>();
            for (CcCheque ccChequeListNewCcChequeToAttach : ccChequeListNew) {
                ccChequeListNewCcChequeToAttach = em.getReference(ccChequeListNewCcChequeToAttach.getClass(), ccChequeListNewCcChequeToAttach.getNumero());
                attachedCcChequeListNew.add(ccChequeListNewCcChequeToAttach);
            }
            ccChequeListNew = attachedCcChequeListNew;
            ccBanco.setCcChequeList(ccChequeListNew);
            ccBanco = em.merge(ccBanco);
            for (CcCheque ccChequeListNewCcCheque : ccChequeListNew) {
                if (!ccChequeListOld.contains(ccChequeListNewCcCheque)) {
                    CcBanco oldBancoIdOfCcChequeListNewCcCheque = ccChequeListNewCcCheque.getBancoId();
                    ccChequeListNewCcCheque.setBancoId(ccBanco);
                    ccChequeListNewCcCheque = em.merge(ccChequeListNewCcCheque);
                    if (oldBancoIdOfCcChequeListNewCcCheque != null && !oldBancoIdOfCcChequeListNewCcCheque.equals(ccBanco)) {
                        oldBancoIdOfCcChequeListNewCcCheque.getCcChequeList().remove(ccChequeListNewCcCheque);
                        oldBancoIdOfCcChequeListNewCcCheque = em.merge(oldBancoIdOfCcChequeListNewCcCheque);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = ccBanco.getId();
                if (findCcBanco(id) == null) {
                    throw new NonexistentEntityException("The ccBanco with id " + id + " no longer exists.");
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
            CcBanco ccBanco;
            try {
                ccBanco = em.getReference(CcBanco.class, id);
                ccBanco.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ccBanco with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<CcCheque> ccChequeListOrphanCheck = ccBanco.getCcChequeList();
            for (CcCheque ccChequeListOrphanCheckCcCheque : ccChequeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CcBanco (" + ccBanco + ") cannot be destroyed since the CcCheque " + ccChequeListOrphanCheckCcCheque + " in its ccChequeList field has a non-nullable bancoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ccBanco);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CcBanco> findCcBancoEntities() {
        return findCcBancoEntities(true, -1, -1);
    }

    public List<CcBanco> findCcBancoEntities(int maxResults, int firstResult) {
        return findCcBancoEntities(false, maxResults, firstResult);
    }

    private List<CcBanco> findCcBancoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CcBanco.class));
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

    public CcBanco findCcBanco(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CcBanco.class, id);
        } finally {
            em.close();
        }
    }

    public int getCcBancoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CcBanco> rt = cq.from(CcBanco.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
