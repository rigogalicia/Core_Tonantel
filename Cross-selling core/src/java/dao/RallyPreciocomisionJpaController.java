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
public class RallyPreciocomisionJpaController implements Serializable {

    public RallyPreciocomisionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallyPreciocomision rallyPreciocomision) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyProducto rallyProductoIdproducto = rallyPreciocomision.getRallyProductoIdproducto();
            if (rallyProductoIdproducto != null) {
                rallyProductoIdproducto = em.getReference(rallyProductoIdproducto.getClass(), rallyProductoIdproducto.getIdproducto());
                rallyPreciocomision.setRallyProductoIdproducto(rallyProductoIdproducto);
            }
            em.persist(rallyPreciocomision);
            if (rallyProductoIdproducto != null) {
                rallyProductoIdproducto.getRallyPreciocomisionList().add(rallyPreciocomision);
                rallyProductoIdproducto = em.merge(rallyProductoIdproducto);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallyPreciocomision rallyPreciocomision) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyPreciocomision persistentRallyPreciocomision = em.find(RallyPreciocomision.class, rallyPreciocomision.getIdpreciocomision());
            RallyProducto rallyProductoIdproductoOld = persistentRallyPreciocomision.getRallyProductoIdproducto();
            RallyProducto rallyProductoIdproductoNew = rallyPreciocomision.getRallyProductoIdproducto();
            if (rallyProductoIdproductoNew != null) {
                rallyProductoIdproductoNew = em.getReference(rallyProductoIdproductoNew.getClass(), rallyProductoIdproductoNew.getIdproducto());
                rallyPreciocomision.setRallyProductoIdproducto(rallyProductoIdproductoNew);
            }
            rallyPreciocomision = em.merge(rallyPreciocomision);
            if (rallyProductoIdproductoOld != null && !rallyProductoIdproductoOld.equals(rallyProductoIdproductoNew)) {
                rallyProductoIdproductoOld.getRallyPreciocomisionList().remove(rallyPreciocomision);
                rallyProductoIdproductoOld = em.merge(rallyProductoIdproductoOld);
            }
            if (rallyProductoIdproductoNew != null && !rallyProductoIdproductoNew.equals(rallyProductoIdproductoOld)) {
                rallyProductoIdproductoNew.getRallyPreciocomisionList().add(rallyPreciocomision);
                rallyProductoIdproductoNew = em.merge(rallyProductoIdproductoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallyPreciocomision.getIdpreciocomision();
                if (findRallyPreciocomision(id) == null) {
                    throw new NonexistentEntityException("The rallyPreciocomision with id " + id + " no longer exists.");
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
            RallyPreciocomision rallyPreciocomision;
            try {
                rallyPreciocomision = em.getReference(RallyPreciocomision.class, id);
                rallyPreciocomision.getIdpreciocomision();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallyPreciocomision with id " + id + " no longer exists.", enfe);
            }
            RallyProducto rallyProductoIdproducto = rallyPreciocomision.getRallyProductoIdproducto();
            if (rallyProductoIdproducto != null) {
                rallyProductoIdproducto.getRallyPreciocomisionList().remove(rallyPreciocomision);
                rallyProductoIdproducto = em.merge(rallyProductoIdproducto);
            }
            em.remove(rallyPreciocomision);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallyPreciocomision> findRallyPreciocomisionEntities() {
        return findRallyPreciocomisionEntities(true, -1, -1);
    }

    public List<RallyPreciocomision> findRallyPreciocomisionEntities(int maxResults, int firstResult) {
        return findRallyPreciocomisionEntities(false, maxResults, firstResult);
    }

    private List<RallyPreciocomision> findRallyPreciocomisionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallyPreciocomision.class));
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

    public RallyPreciocomision findRallyPreciocomision(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallyPreciocomision.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallyPreciocomisionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallyPreciocomision> rt = cq.from(RallyPreciocomision.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
