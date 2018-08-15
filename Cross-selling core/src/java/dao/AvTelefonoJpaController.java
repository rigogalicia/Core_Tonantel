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
public class AvTelefonoJpaController implements Serializable {

    public AvTelefonoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvTelefono avTelefono) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvAsociado asociadoCif = avTelefono.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif = em.getReference(asociadoCif.getClass(), asociadoCif.getCif());
                avTelefono.setAsociadoCif(asociadoCif);
            }
            em.persist(avTelefono);
            if (asociadoCif != null) {
                asociadoCif.getAvTelefonoList().add(avTelefono);
                asociadoCif = em.merge(asociadoCif);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvTelefono avTelefono) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvTelefono persistentAvTelefono = em.find(AvTelefono.class, avTelefono.getId());
            AvAsociado asociadoCifOld = persistentAvTelefono.getAsociadoCif();
            AvAsociado asociadoCifNew = avTelefono.getAsociadoCif();
            if (asociadoCifNew != null) {
                asociadoCifNew = em.getReference(asociadoCifNew.getClass(), asociadoCifNew.getCif());
                avTelefono.setAsociadoCif(asociadoCifNew);
            }
            avTelefono = em.merge(avTelefono);
            if (asociadoCifOld != null && !asociadoCifOld.equals(asociadoCifNew)) {
                asociadoCifOld.getAvTelefonoList().remove(avTelefono);
                asociadoCifOld = em.merge(asociadoCifOld);
            }
            if (asociadoCifNew != null && !asociadoCifNew.equals(asociadoCifOld)) {
                asociadoCifNew.getAvTelefonoList().add(avTelefono);
                asociadoCifNew = em.merge(asociadoCifNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avTelefono.getId();
                if (findAvTelefono(id) == null) {
                    throw new NonexistentEntityException("The avTelefono with id " + id + " no longer exists.");
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
            AvTelefono avTelefono;
            try {
                avTelefono = em.getReference(AvTelefono.class, id);
                avTelefono.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avTelefono with id " + id + " no longer exists.", enfe);
            }
            AvAsociado asociadoCif = avTelefono.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif.getAvTelefonoList().remove(avTelefono);
                asociadoCif = em.merge(asociadoCif);
            }
            em.remove(avTelefono);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvTelefono> findAvTelefonoEntities() {
        return findAvTelefonoEntities(true, -1, -1);
    }

    public List<AvTelefono> findAvTelefonoEntities(int maxResults, int firstResult) {
        return findAvTelefonoEntities(false, maxResults, firstResult);
    }

    private List<AvTelefono> findAvTelefonoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvTelefono.class));
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

    public AvTelefono findAvTelefono(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvTelefono.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvTelefonoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvTelefono> rt = cq.from(AvTelefono.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
