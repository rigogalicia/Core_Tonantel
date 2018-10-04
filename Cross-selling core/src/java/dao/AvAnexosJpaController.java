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
public class AvAnexosJpaController implements Serializable {

    public AvAnexosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvAnexos avAnexos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvAvaluo avaluoId = avAnexos.getAvaluoId();
            if (avaluoId != null) {
                avaluoId = em.getReference(avaluoId.getClass(), avaluoId.getId());
                avAnexos.setAvaluoId(avaluoId);
            }
            em.persist(avAnexos);
            if (avaluoId != null) {
                avaluoId.getAvAnexosList().add(avAnexos);
                avaluoId = em.merge(avaluoId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvAnexos avAnexos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvAnexos persistentAvAnexos = em.find(AvAnexos.class, avAnexos.getId());
            AvAvaluo avaluoIdOld = persistentAvAnexos.getAvaluoId();
            AvAvaluo avaluoIdNew = avAnexos.getAvaluoId();
            if (avaluoIdNew != null) {
                avaluoIdNew = em.getReference(avaluoIdNew.getClass(), avaluoIdNew.getId());
                avAnexos.setAvaluoId(avaluoIdNew);
            }
            avAnexos = em.merge(avAnexos);
            if (avaluoIdOld != null && !avaluoIdOld.equals(avaluoIdNew)) {
                avaluoIdOld.getAvAnexosList().remove(avAnexos);
                avaluoIdOld = em.merge(avaluoIdOld);
            }
            if (avaluoIdNew != null && !avaluoIdNew.equals(avaluoIdOld)) {
                avaluoIdNew.getAvAnexosList().add(avAnexos);
                avaluoIdNew = em.merge(avaluoIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avAnexos.getId();
                if (findAvAnexos(id) == null) {
                    throw new NonexistentEntityException("The avAnexos with id " + id + " no longer exists.");
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
            AvAnexos avAnexos;
            try {
                avAnexos = em.getReference(AvAnexos.class, id);
                avAnexos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avAnexos with id " + id + " no longer exists.", enfe);
            }
            AvAvaluo avaluoId = avAnexos.getAvaluoId();
            if (avaluoId != null) {
                avaluoId.getAvAnexosList().remove(avAnexos);
                avaluoId = em.merge(avaluoId);
            }
            em.remove(avAnexos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvAnexos> findAvAnexosEntities() {
        return findAvAnexosEntities(true, -1, -1);
    }

    public List<AvAnexos> findAvAnexosEntities(int maxResults, int firstResult) {
        return findAvAnexosEntities(false, maxResults, firstResult);
    }

    private List<AvAnexos> findAvAnexosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvAnexos.class));
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

    public AvAnexos findAvAnexos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvAnexos.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvAnexosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvAnexos> rt = cq.from(AvAnexos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
