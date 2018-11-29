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
public class GcLogprocesonegocioJpaController implements Serializable {

    public GcLogprocesonegocioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcLogprocesonegocio gcLogprocesonegocio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcFichanegocio fichanegocioId = gcLogprocesonegocio.getFichanegocioId();
            if (fichanegocioId != null) {
                fichanegocioId = em.getReference(fichanegocioId.getClass(), fichanegocioId.getId());
                gcLogprocesonegocio.setFichanegocioId(fichanegocioId);
            }
            em.persist(gcLogprocesonegocio);
            if (fichanegocioId != null) {
                fichanegocioId.getGcLogprocesonegocioList().add(gcLogprocesonegocio);
                fichanegocioId = em.merge(fichanegocioId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcLogprocesonegocio gcLogprocesonegocio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcLogprocesonegocio persistentGcLogprocesonegocio = em.find(GcLogprocesonegocio.class, gcLogprocesonegocio.getId());
            GcFichanegocio fichanegocioIdOld = persistentGcLogprocesonegocio.getFichanegocioId();
            GcFichanegocio fichanegocioIdNew = gcLogprocesonegocio.getFichanegocioId();
            if (fichanegocioIdNew != null) {
                fichanegocioIdNew = em.getReference(fichanegocioIdNew.getClass(), fichanegocioIdNew.getId());
                gcLogprocesonegocio.setFichanegocioId(fichanegocioIdNew);
            }
            gcLogprocesonegocio = em.merge(gcLogprocesonegocio);
            if (fichanegocioIdOld != null && !fichanegocioIdOld.equals(fichanegocioIdNew)) {
                fichanegocioIdOld.getGcLogprocesonegocioList().remove(gcLogprocesonegocio);
                fichanegocioIdOld = em.merge(fichanegocioIdOld);
            }
            if (fichanegocioIdNew != null && !fichanegocioIdNew.equals(fichanegocioIdOld)) {
                fichanegocioIdNew.getGcLogprocesonegocioList().add(gcLogprocesonegocio);
                fichanegocioIdNew = em.merge(fichanegocioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcLogprocesonegocio.getId();
                if (findGcLogprocesonegocio(id) == null) {
                    throw new NonexistentEntityException("The gcLogprocesonegocio with id " + id + " no longer exists.");
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
            GcLogprocesonegocio gcLogprocesonegocio;
            try {
                gcLogprocesonegocio = em.getReference(GcLogprocesonegocio.class, id);
                gcLogprocesonegocio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcLogprocesonegocio with id " + id + " no longer exists.", enfe);
            }
            GcFichanegocio fichanegocioId = gcLogprocesonegocio.getFichanegocioId();
            if (fichanegocioId != null) {
                fichanegocioId.getGcLogprocesonegocioList().remove(gcLogprocesonegocio);
                fichanegocioId = em.merge(fichanegocioId);
            }
            em.remove(gcLogprocesonegocio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcLogprocesonegocio> findGcLogprocesonegocioEntities() {
        return findGcLogprocesonegocioEntities(true, -1, -1);
    }

    public List<GcLogprocesonegocio> findGcLogprocesonegocioEntities(int maxResults, int firstResult) {
        return findGcLogprocesonegocioEntities(false, maxResults, firstResult);
    }

    private List<GcLogprocesonegocio> findGcLogprocesonegocioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcLogprocesonegocio.class));
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

    public GcLogprocesonegocio findGcLogprocesonegocio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcLogprocesonegocio.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcLogprocesonegocioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcLogprocesonegocio> rt = cq.from(GcLogprocesonegocio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
