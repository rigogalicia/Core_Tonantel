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
public class GcDestinoJpaController implements Serializable {

    public GcDestinoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcDestino gcDestino) {
        if (gcDestino.getGcSolicitudList() == null) {
            gcDestino.setGcSolicitudList(new ArrayList<GcSolicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GcSolicitud> attachedGcSolicitudList = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListGcSolicitudToAttach : gcDestino.getGcSolicitudList()) {
                gcSolicitudListGcSolicitudToAttach = em.getReference(gcSolicitudListGcSolicitudToAttach.getClass(), gcSolicitudListGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudList.add(gcSolicitudListGcSolicitudToAttach);
            }
            gcDestino.setGcSolicitudList(attachedGcSolicitudList);
            em.persist(gcDestino);
            for (GcSolicitud gcSolicitudListGcSolicitud : gcDestino.getGcSolicitudList()) {
                GcDestino oldDestinoIdOfGcSolicitudListGcSolicitud = gcSolicitudListGcSolicitud.getDestinoId();
                gcSolicitudListGcSolicitud.setDestinoId(gcDestino);
                gcSolicitudListGcSolicitud = em.merge(gcSolicitudListGcSolicitud);
                if (oldDestinoIdOfGcSolicitudListGcSolicitud != null) {
                    oldDestinoIdOfGcSolicitudListGcSolicitud.getGcSolicitudList().remove(gcSolicitudListGcSolicitud);
                    oldDestinoIdOfGcSolicitudListGcSolicitud = em.merge(oldDestinoIdOfGcSolicitudListGcSolicitud);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcDestino gcDestino) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcDestino persistentGcDestino = em.find(GcDestino.class, gcDestino.getId());
            List<GcSolicitud> gcSolicitudListOld = persistentGcDestino.getGcSolicitudList();
            List<GcSolicitud> gcSolicitudListNew = gcDestino.getGcSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (GcSolicitud gcSolicitudListOldGcSolicitud : gcSolicitudListOld) {
                if (!gcSolicitudListNew.contains(gcSolicitudListOldGcSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcSolicitud " + gcSolicitudListOldGcSolicitud + " since its destinoId field is not nullable.");
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
            gcDestino.setGcSolicitudList(gcSolicitudListNew);
            gcDestino = em.merge(gcDestino);
            for (GcSolicitud gcSolicitudListNewGcSolicitud : gcSolicitudListNew) {
                if (!gcSolicitudListOld.contains(gcSolicitudListNewGcSolicitud)) {
                    GcDestino oldDestinoIdOfGcSolicitudListNewGcSolicitud = gcSolicitudListNewGcSolicitud.getDestinoId();
                    gcSolicitudListNewGcSolicitud.setDestinoId(gcDestino);
                    gcSolicitudListNewGcSolicitud = em.merge(gcSolicitudListNewGcSolicitud);
                    if (oldDestinoIdOfGcSolicitudListNewGcSolicitud != null && !oldDestinoIdOfGcSolicitudListNewGcSolicitud.equals(gcDestino)) {
                        oldDestinoIdOfGcSolicitudListNewGcSolicitud.getGcSolicitudList().remove(gcSolicitudListNewGcSolicitud);
                        oldDestinoIdOfGcSolicitudListNewGcSolicitud = em.merge(oldDestinoIdOfGcSolicitudListNewGcSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcDestino.getId();
                if (findGcDestino(id) == null) {
                    throw new NonexistentEntityException("The gcDestino with id " + id + " no longer exists.");
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
            GcDestino gcDestino;
            try {
                gcDestino = em.getReference(GcDestino.class, id);
                gcDestino.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcDestino with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcSolicitud> gcSolicitudListOrphanCheck = gcDestino.getGcSolicitudList();
            for (GcSolicitud gcSolicitudListOrphanCheckGcSolicitud : gcSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcDestino (" + gcDestino + ") cannot be destroyed since the GcSolicitud " + gcSolicitudListOrphanCheckGcSolicitud + " in its gcSolicitudList field has a non-nullable destinoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(gcDestino);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcDestino> findGcDestinoEntities() {
        return findGcDestinoEntities(true, -1, -1);
    }

    public List<GcDestino> findGcDestinoEntities(int maxResults, int firstResult) {
        return findGcDestinoEntities(false, maxResults, firstResult);
    }

    private List<GcDestino> findGcDestinoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcDestino.class));
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

    public GcDestino findGcDestino(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcDestino.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcDestinoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcDestino> rt = cq.from(GcDestino.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
