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
public class GcProcesoJpaController implements Serializable {

    public GcProcesoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcProceso gcProceso) {
        if (gcProceso.getGcDetalleList() == null) {
            gcProceso.setGcDetalleList(new ArrayList<GcDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcAsociado asociadoCif = gcProceso.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif = em.getReference(asociadoCif.getClass(), asociadoCif.getCif());
                gcProceso.setAsociadoCif(asociadoCif);
            }
            List<GcDetalle> attachedGcDetalleList = new ArrayList<GcDetalle>();
            for (GcDetalle gcDetalleListGcDetalleToAttach : gcProceso.getGcDetalleList()) {
                gcDetalleListGcDetalleToAttach = em.getReference(gcDetalleListGcDetalleToAttach.getClass(), gcDetalleListGcDetalleToAttach.getId());
                attachedGcDetalleList.add(gcDetalleListGcDetalleToAttach);
            }
            gcProceso.setGcDetalleList(attachedGcDetalleList);
            em.persist(gcProceso);
            if (asociadoCif != null) {
                asociadoCif.getGcProcesoList().add(gcProceso);
                asociadoCif = em.merge(asociadoCif);
            }
            for (GcDetalle gcDetalleListGcDetalle : gcProceso.getGcDetalleList()) {
                GcProceso oldProcesoIdOfGcDetalleListGcDetalle = gcDetalleListGcDetalle.getProcesoId();
                gcDetalleListGcDetalle.setProcesoId(gcProceso);
                gcDetalleListGcDetalle = em.merge(gcDetalleListGcDetalle);
                if (oldProcesoIdOfGcDetalleListGcDetalle != null) {
                    oldProcesoIdOfGcDetalleListGcDetalle.getGcDetalleList().remove(gcDetalleListGcDetalle);
                    oldProcesoIdOfGcDetalleListGcDetalle = em.merge(oldProcesoIdOfGcDetalleListGcDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcProceso gcProceso) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcProceso persistentGcProceso = em.find(GcProceso.class, gcProceso.getId());
            GcAsociado asociadoCifOld = persistentGcProceso.getAsociadoCif();
            GcAsociado asociadoCifNew = gcProceso.getAsociadoCif();
            List<GcDetalle> gcDetalleListOld = persistentGcProceso.getGcDetalleList();
            List<GcDetalle> gcDetalleListNew = gcProceso.getGcDetalleList();
            List<String> illegalOrphanMessages = null;
            for (GcDetalle gcDetalleListOldGcDetalle : gcDetalleListOld) {
                if (!gcDetalleListNew.contains(gcDetalleListOldGcDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcDetalle " + gcDetalleListOldGcDetalle + " since its procesoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (asociadoCifNew != null) {
                asociadoCifNew = em.getReference(asociadoCifNew.getClass(), asociadoCifNew.getCif());
                gcProceso.setAsociadoCif(asociadoCifNew);
            }
            List<GcDetalle> attachedGcDetalleListNew = new ArrayList<GcDetalle>();
            for (GcDetalle gcDetalleListNewGcDetalleToAttach : gcDetalleListNew) {
                gcDetalleListNewGcDetalleToAttach = em.getReference(gcDetalleListNewGcDetalleToAttach.getClass(), gcDetalleListNewGcDetalleToAttach.getId());
                attachedGcDetalleListNew.add(gcDetalleListNewGcDetalleToAttach);
            }
            gcDetalleListNew = attachedGcDetalleListNew;
            gcProceso.setGcDetalleList(gcDetalleListNew);
            gcProceso = em.merge(gcProceso);
            if (asociadoCifOld != null && !asociadoCifOld.equals(asociadoCifNew)) {
                asociadoCifOld.getGcProcesoList().remove(gcProceso);
                asociadoCifOld = em.merge(asociadoCifOld);
            }
            if (asociadoCifNew != null && !asociadoCifNew.equals(asociadoCifOld)) {
                asociadoCifNew.getGcProcesoList().add(gcProceso);
                asociadoCifNew = em.merge(asociadoCifNew);
            }
            for (GcDetalle gcDetalleListNewGcDetalle : gcDetalleListNew) {
                if (!gcDetalleListOld.contains(gcDetalleListNewGcDetalle)) {
                    GcProceso oldProcesoIdOfGcDetalleListNewGcDetalle = gcDetalleListNewGcDetalle.getProcesoId();
                    gcDetalleListNewGcDetalle.setProcesoId(gcProceso);
                    gcDetalleListNewGcDetalle = em.merge(gcDetalleListNewGcDetalle);
                    if (oldProcesoIdOfGcDetalleListNewGcDetalle != null && !oldProcesoIdOfGcDetalleListNewGcDetalle.equals(gcProceso)) {
                        oldProcesoIdOfGcDetalleListNewGcDetalle.getGcDetalleList().remove(gcDetalleListNewGcDetalle);
                        oldProcesoIdOfGcDetalleListNewGcDetalle = em.merge(oldProcesoIdOfGcDetalleListNewGcDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcProceso.getId();
                if (findGcProceso(id) == null) {
                    throw new NonexistentEntityException("The gcProceso with id " + id + " no longer exists.");
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
            GcProceso gcProceso;
            try {
                gcProceso = em.getReference(GcProceso.class, id);
                gcProceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcProceso with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcDetalle> gcDetalleListOrphanCheck = gcProceso.getGcDetalleList();
            for (GcDetalle gcDetalleListOrphanCheckGcDetalle : gcDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcProceso (" + gcProceso + ") cannot be destroyed since the GcDetalle " + gcDetalleListOrphanCheckGcDetalle + " in its gcDetalleList field has a non-nullable procesoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            GcAsociado asociadoCif = gcProceso.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif.getGcProcesoList().remove(gcProceso);
                asociadoCif = em.merge(asociadoCif);
            }
            em.remove(gcProceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcProceso> findGcProcesoEntities() {
        return findGcProcesoEntities(true, -1, -1);
    }

    public List<GcProceso> findGcProcesoEntities(int maxResults, int firstResult) {
        return findGcProcesoEntities(false, maxResults, firstResult);
    }

    private List<GcProceso> findGcProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcProceso.class));
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

    public GcProceso findGcProceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcProceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcProceso> rt = cq.from(GcProceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
