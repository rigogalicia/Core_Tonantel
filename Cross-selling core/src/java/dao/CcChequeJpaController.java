/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
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
public class CcChequeJpaController implements Serializable {

    public CcChequeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CcCheque ccCheque) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CcBanco bancoId = ccCheque.getBancoId();
            if (bancoId != null) {
                bancoId = em.getReference(bancoId.getClass(), bancoId.getId());
                ccCheque.setBancoId(bancoId);
            }
            em.persist(ccCheque);
            if (bancoId != null) {
                bancoId.getCcChequeList().add(ccCheque);
                bancoId = em.merge(bancoId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCcCheque(ccCheque.getNumero()) != null) {
                throw new PreexistingEntityException("CcCheque " + ccCheque + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CcCheque ccCheque) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CcCheque persistentCcCheque = em.find(CcCheque.class, ccCheque.getNumero());
            CcBanco bancoIdOld = persistentCcCheque.getBancoId();
            CcBanco bancoIdNew = ccCheque.getBancoId();
            if (bancoIdNew != null) {
                bancoIdNew = em.getReference(bancoIdNew.getClass(), bancoIdNew.getId());
                ccCheque.setBancoId(bancoIdNew);
            }
            ccCheque = em.merge(ccCheque);
            if (bancoIdOld != null && !bancoIdOld.equals(bancoIdNew)) {
                bancoIdOld.getCcChequeList().remove(ccCheque);
                bancoIdOld = em.merge(bancoIdOld);
            }
            if (bancoIdNew != null && !bancoIdNew.equals(bancoIdOld)) {
                bancoIdNew.getCcChequeList().add(ccCheque);
                bancoIdNew = em.merge(bancoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ccCheque.getNumero();
                if (findCcCheque(id) == null) {
                    throw new NonexistentEntityException("The ccCheque with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CcCheque ccCheque;
            try {
                ccCheque = em.getReference(CcCheque.class, id);
                ccCheque.getNumero();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ccCheque with id " + id + " no longer exists.", enfe);
            }
            CcBanco bancoId = ccCheque.getBancoId();
            if (bancoId != null) {
                bancoId.getCcChequeList().remove(ccCheque);
                bancoId = em.merge(bancoId);
            }
            em.remove(ccCheque);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CcCheque> findCcChequeEntities() {
        return findCcChequeEntities(true, -1, -1);
    }

    public List<CcCheque> findCcChequeEntities(int maxResults, int firstResult) {
        return findCcChequeEntities(false, maxResults, firstResult);
    }

    private List<CcCheque> findCcChequeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CcCheque.class));
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

    public CcCheque findCcCheque(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CcCheque.class, id);
        } finally {
            em.close();
        }
    }

    public int getCcChequeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CcCheque> rt = cq.from(CcCheque.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
