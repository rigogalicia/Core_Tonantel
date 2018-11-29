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
public class GcVentajasJpaController implements Serializable {

    public GcVentajasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcVentajas gcVentajas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcFichanegocio fichanegocioId = gcVentajas.getFichanegocioId();
            if (fichanegocioId != null) {
                fichanegocioId = em.getReference(fichanegocioId.getClass(), fichanegocioId.getId());
                gcVentajas.setFichanegocioId(fichanegocioId);
            }
            em.persist(gcVentajas);
            if (fichanegocioId != null) {
                fichanegocioId.getGcVentajasList().add(gcVentajas);
                fichanegocioId = em.merge(fichanegocioId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcVentajas gcVentajas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcVentajas persistentGcVentajas = em.find(GcVentajas.class, gcVentajas.getId());
            GcFichanegocio fichanegocioIdOld = persistentGcVentajas.getFichanegocioId();
            GcFichanegocio fichanegocioIdNew = gcVentajas.getFichanegocioId();
            if (fichanegocioIdNew != null) {
                fichanegocioIdNew = em.getReference(fichanegocioIdNew.getClass(), fichanegocioIdNew.getId());
                gcVentajas.setFichanegocioId(fichanegocioIdNew);
            }
            gcVentajas = em.merge(gcVentajas);
            if (fichanegocioIdOld != null && !fichanegocioIdOld.equals(fichanegocioIdNew)) {
                fichanegocioIdOld.getGcVentajasList().remove(gcVentajas);
                fichanegocioIdOld = em.merge(fichanegocioIdOld);
            }
            if (fichanegocioIdNew != null && !fichanegocioIdNew.equals(fichanegocioIdOld)) {
                fichanegocioIdNew.getGcVentajasList().add(gcVentajas);
                fichanegocioIdNew = em.merge(fichanegocioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcVentajas.getId();
                if (findGcVentajas(id) == null) {
                    throw new NonexistentEntityException("The gcVentajas with id " + id + " no longer exists.");
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
            GcVentajas gcVentajas;
            try {
                gcVentajas = em.getReference(GcVentajas.class, id);
                gcVentajas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcVentajas with id " + id + " no longer exists.", enfe);
            }
            GcFichanegocio fichanegocioId = gcVentajas.getFichanegocioId();
            if (fichanegocioId != null) {
                fichanegocioId.getGcVentajasList().remove(gcVentajas);
                fichanegocioId = em.merge(fichanegocioId);
            }
            em.remove(gcVentajas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcVentajas> findGcVentajasEntities() {
        return findGcVentajasEntities(true, -1, -1);
    }

    public List<GcVentajas> findGcVentajasEntities(int maxResults, int firstResult) {
        return findGcVentajasEntities(false, maxResults, firstResult);
    }

    private List<GcVentajas> findGcVentajasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcVentajas.class));
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

    public GcVentajas findGcVentajas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcVentajas.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcVentajasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcVentajas> rt = cq.from(GcVentajas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
