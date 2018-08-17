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
public class GcDesventajasJpaController implements Serializable {

    public GcDesventajasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcDesventajas gcDesventajas) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcFichanegocio fichanegocioId = gcDesventajas.getFichanegocioId();
            if (fichanegocioId != null) {
                fichanegocioId = em.getReference(fichanegocioId.getClass(), fichanegocioId.getId());
                gcDesventajas.setFichanegocioId(fichanegocioId);
            }
            em.persist(gcDesventajas);
            if (fichanegocioId != null) {
                fichanegocioId.getGcDesventajasList().add(gcDesventajas);
                fichanegocioId = em.merge(fichanegocioId);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcDesventajas gcDesventajas) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcDesventajas persistentGcDesventajas = em.find(GcDesventajas.class, gcDesventajas.getId());
            GcFichanegocio fichanegocioIdOld = persistentGcDesventajas.getFichanegocioId();
            GcFichanegocio fichanegocioIdNew = gcDesventajas.getFichanegocioId();
            if (fichanegocioIdNew != null) {
                fichanegocioIdNew = em.getReference(fichanegocioIdNew.getClass(), fichanegocioIdNew.getId());
                gcDesventajas.setFichanegocioId(fichanegocioIdNew);
            }
            gcDesventajas = em.merge(gcDesventajas);
            if (fichanegocioIdOld != null && !fichanegocioIdOld.equals(fichanegocioIdNew)) {
                fichanegocioIdOld.getGcDesventajasList().remove(gcDesventajas);
                fichanegocioIdOld = em.merge(fichanegocioIdOld);
            }
            if (fichanegocioIdNew != null && !fichanegocioIdNew.equals(fichanegocioIdOld)) {
                fichanegocioIdNew.getGcDesventajasList().add(gcDesventajas);
                fichanegocioIdNew = em.merge(fichanegocioIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcDesventajas.getId();
                if (findGcDesventajas(id) == null) {
                    throw new NonexistentEntityException("The gcDesventajas with id " + id + " no longer exists.");
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
            GcDesventajas gcDesventajas;
            try {
                gcDesventajas = em.getReference(GcDesventajas.class, id);
                gcDesventajas.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcDesventajas with id " + id + " no longer exists.", enfe);
            }
            GcFichanegocio fichanegocioId = gcDesventajas.getFichanegocioId();
            if (fichanegocioId != null) {
                fichanegocioId.getGcDesventajasList().remove(gcDesventajas);
                fichanegocioId = em.merge(fichanegocioId);
            }
            em.remove(gcDesventajas);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcDesventajas> findGcDesventajasEntities() {
        return findGcDesventajasEntities(true, -1, -1);
    }

    public List<GcDesventajas> findGcDesventajasEntities(int maxResults, int firstResult) {
        return findGcDesventajasEntities(false, maxResults, firstResult);
    }

    private List<GcDesventajas> findGcDesventajasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcDesventajas.class));
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

    public GcDesventajas findGcDesventajas(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcDesventajas.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcDesventajasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcDesventajas> rt = cq.from(GcDesventajas.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
