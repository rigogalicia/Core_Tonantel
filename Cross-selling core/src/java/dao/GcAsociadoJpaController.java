/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dao.exceptions.PreexistingEntityException;
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
 * @author Rgalicia
 */
public class GcAsociadoJpaController implements Serializable {

    public GcAsociadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcAsociado gcAsociado) throws PreexistingEntityException, Exception {
        if (gcAsociado.getGcSolicitudList() == null) {
            gcAsociado.setGcSolicitudList(new ArrayList<GcSolicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GcSolicitud> attachedGcSolicitudList = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListGcSolicitudToAttach : gcAsociado.getGcSolicitudList()) {
                gcSolicitudListGcSolicitudToAttach = em.getReference(gcSolicitudListGcSolicitudToAttach.getClass(), gcSolicitudListGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudList.add(gcSolicitudListGcSolicitudToAttach);
            }
            gcAsociado.setGcSolicitudList(attachedGcSolicitudList);
            em.persist(gcAsociado);
            for (GcSolicitud gcSolicitudListGcSolicitud : gcAsociado.getGcSolicitudList()) {
                GcAsociado oldAsociadoCifOfGcSolicitudListGcSolicitud = gcSolicitudListGcSolicitud.getAsociadoCif();
                gcSolicitudListGcSolicitud.setAsociadoCif(gcAsociado);
                gcSolicitudListGcSolicitud = em.merge(gcSolicitudListGcSolicitud);
                if (oldAsociadoCifOfGcSolicitudListGcSolicitud != null) {
                    oldAsociadoCifOfGcSolicitudListGcSolicitud.getGcSolicitudList().remove(gcSolicitudListGcSolicitud);
                    oldAsociadoCifOfGcSolicitudListGcSolicitud = em.merge(oldAsociadoCifOfGcSolicitudListGcSolicitud);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGcAsociado(gcAsociado.getCif()) != null) {
                throw new PreexistingEntityException("GcAsociado " + gcAsociado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcAsociado gcAsociado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcAsociado persistentGcAsociado = em.find(GcAsociado.class, gcAsociado.getCif());
            List<GcSolicitud> gcSolicitudListOld = persistentGcAsociado.getGcSolicitudList();
            List<GcSolicitud> gcSolicitudListNew = gcAsociado.getGcSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (GcSolicitud gcSolicitudListOldGcSolicitud : gcSolicitudListOld) {
                if (!gcSolicitudListNew.contains(gcSolicitudListOldGcSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcSolicitud " + gcSolicitudListOldGcSolicitud + " since its asociadoCif field is not nullable.");
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
            gcAsociado.setGcSolicitudList(gcSolicitudListNew);
            gcAsociado = em.merge(gcAsociado);
            for (GcSolicitud gcSolicitudListNewGcSolicitud : gcSolicitudListNew) {
                if (!gcSolicitudListOld.contains(gcSolicitudListNewGcSolicitud)) {
                    GcAsociado oldAsociadoCifOfGcSolicitudListNewGcSolicitud = gcSolicitudListNewGcSolicitud.getAsociadoCif();
                    gcSolicitudListNewGcSolicitud.setAsociadoCif(gcAsociado);
                    gcSolicitudListNewGcSolicitud = em.merge(gcSolicitudListNewGcSolicitud);
                    if (oldAsociadoCifOfGcSolicitudListNewGcSolicitud != null && !oldAsociadoCifOfGcSolicitudListNewGcSolicitud.equals(gcAsociado)) {
                        oldAsociadoCifOfGcSolicitudListNewGcSolicitud.getGcSolicitudList().remove(gcSolicitudListNewGcSolicitud);
                        oldAsociadoCifOfGcSolicitudListNewGcSolicitud = em.merge(oldAsociadoCifOfGcSolicitudListNewGcSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = gcAsociado.getCif();
                if (findGcAsociado(id) == null) {
                    throw new NonexistentEntityException("The gcAsociado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcAsociado gcAsociado;
            try {
                gcAsociado = em.getReference(GcAsociado.class, id);
                gcAsociado.getCif();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcAsociado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcSolicitud> gcSolicitudListOrphanCheck = gcAsociado.getGcSolicitudList();
            for (GcSolicitud gcSolicitudListOrphanCheckGcSolicitud : gcSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcAsociado (" + gcAsociado + ") cannot be destroyed since the GcSolicitud " + gcSolicitudListOrphanCheckGcSolicitud + " in its gcSolicitudList field has a non-nullable asociadoCif field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(gcAsociado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcAsociado> findGcAsociadoEntities() {
        return findGcAsociadoEntities(true, -1, -1);
    }

    public List<GcAsociado> findGcAsociadoEntities(int maxResults, int firstResult) {
        return findGcAsociadoEntities(false, maxResults, firstResult);
    }

    private List<GcAsociado> findGcAsociadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcAsociado.class));
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

    public GcAsociado findGcAsociado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcAsociado.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcAsociadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcAsociado> rt = cq.from(GcAsociado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
