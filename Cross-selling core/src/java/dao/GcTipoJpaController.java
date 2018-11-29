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
public class GcTipoJpaController implements Serializable {

    public GcTipoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcTipo gcTipo) {
        if (gcTipo.getGcSolicitudList() == null) {
            gcTipo.setGcSolicitudList(new ArrayList<GcSolicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GcSolicitud> attachedGcSolicitudList = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListGcSolicitudToAttach : gcTipo.getGcSolicitudList()) {
                gcSolicitudListGcSolicitudToAttach = em.getReference(gcSolicitudListGcSolicitudToAttach.getClass(), gcSolicitudListGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudList.add(gcSolicitudListGcSolicitudToAttach);
            }
            gcTipo.setGcSolicitudList(attachedGcSolicitudList);
            em.persist(gcTipo);
            for (GcSolicitud gcSolicitudListGcSolicitud : gcTipo.getGcSolicitudList()) {
                GcTipo oldTipoIdOfGcSolicitudListGcSolicitud = gcSolicitudListGcSolicitud.getTipoId();
                gcSolicitudListGcSolicitud.setTipoId(gcTipo);
                gcSolicitudListGcSolicitud = em.merge(gcSolicitudListGcSolicitud);
                if (oldTipoIdOfGcSolicitudListGcSolicitud != null) {
                    oldTipoIdOfGcSolicitudListGcSolicitud.getGcSolicitudList().remove(gcSolicitudListGcSolicitud);
                    oldTipoIdOfGcSolicitudListGcSolicitud = em.merge(oldTipoIdOfGcSolicitudListGcSolicitud);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcTipo gcTipo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcTipo persistentGcTipo = em.find(GcTipo.class, gcTipo.getId());
            List<GcSolicitud> gcSolicitudListOld = persistentGcTipo.getGcSolicitudList();
            List<GcSolicitud> gcSolicitudListNew = gcTipo.getGcSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (GcSolicitud gcSolicitudListOldGcSolicitud : gcSolicitudListOld) {
                if (!gcSolicitudListNew.contains(gcSolicitudListOldGcSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcSolicitud " + gcSolicitudListOldGcSolicitud + " since its tipoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GcSolicitud> attachedGcSolicitudListNew = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListNewGcSolicitudToAttach : gcSolicitudListNew) {
                gcSolicitudListNewGcSolicitudToAttach = em.getReference(gcSolicitudListNewGcSolicitudToAttach.getClass(), gcSolicitudListNewGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudListNew.add(gcSolicitudListNewGcSolicitudToAttach);
            }
            gcSolicitudListNew = attachedGcSolicitudListNew;
            gcTipo.setGcSolicitudList(gcSolicitudListNew);
            gcTipo = em.merge(gcTipo);
            for (GcSolicitud gcSolicitudListNewGcSolicitud : gcSolicitudListNew) {
                if (!gcSolicitudListOld.contains(gcSolicitudListNewGcSolicitud)) {
                    GcTipo oldTipoIdOfGcSolicitudListNewGcSolicitud = gcSolicitudListNewGcSolicitud.getTipoId();
                    gcSolicitudListNewGcSolicitud.setTipoId(gcTipo);
                    gcSolicitudListNewGcSolicitud = em.merge(gcSolicitudListNewGcSolicitud);
                    if (oldTipoIdOfGcSolicitudListNewGcSolicitud != null && !oldTipoIdOfGcSolicitudListNewGcSolicitud.equals(gcTipo)) {
                        oldTipoIdOfGcSolicitudListNewGcSolicitud.getGcSolicitudList().remove(gcSolicitudListNewGcSolicitud);
                        oldTipoIdOfGcSolicitudListNewGcSolicitud = em.merge(oldTipoIdOfGcSolicitudListNewGcSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcTipo.getId();
                if (findGcTipo(id) == null) {
                    throw new NonexistentEntityException("The gcTipo with id " + id + " no longer exists.");
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
            GcTipo gcTipo;
            try {
                gcTipo = em.getReference(GcTipo.class, id);
                gcTipo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcTipo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcSolicitud> gcSolicitudListOrphanCheck = gcTipo.getGcSolicitudList();
            for (GcSolicitud gcSolicitudListOrphanCheckGcSolicitud : gcSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcTipo (" + gcTipo + ") cannot be destroyed since the GcSolicitud " + gcSolicitudListOrphanCheckGcSolicitud + " in its gcSolicitudList field has a non-nullable tipoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(gcTipo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcTipo> findGcTipoEntities() {
        return findGcTipoEntities(true, -1, -1);
    }

    public List<GcTipo> findGcTipoEntities(int maxResults, int firstResult) {
        return findGcTipoEntities(false, maxResults, firstResult);
    }

    private List<GcTipo> findGcTipoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcTipo.class));
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

    public GcTipo findGcTipo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcTipo.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcTipoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcTipo> rt = cq.from(GcTipo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
