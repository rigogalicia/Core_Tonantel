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
 * @author Rgalicia
 */
public class GcGestionJpaController implements Serializable {

    public GcGestionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcGestion gcGestion) {
        if (gcGestion.getGcSeguimientoList() == null) {
            gcGestion.setGcSeguimientoList(new ArrayList<GcSeguimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcSolicitud solicitudNumeroSolicitud = gcGestion.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud = em.getReference(solicitudNumeroSolicitud.getClass(), solicitudNumeroSolicitud.getNumeroSolicitud());
                gcGestion.setSolicitudNumeroSolicitud(solicitudNumeroSolicitud);
            }
            GcEstado estadoId = gcGestion.getEstadoId();
            if (estadoId != null) {
                estadoId = em.getReference(estadoId.getClass(), estadoId.getId());
                gcGestion.setEstadoId(estadoId);
            }
            List<GcSeguimiento> attachedGcSeguimientoList = new ArrayList<GcSeguimiento>();
            for (GcSeguimiento gcSeguimientoListGcSeguimientoToAttach : gcGestion.getGcSeguimientoList()) {
                gcSeguimientoListGcSeguimientoToAttach = em.getReference(gcSeguimientoListGcSeguimientoToAttach.getClass(), gcSeguimientoListGcSeguimientoToAttach.getId());
                attachedGcSeguimientoList.add(gcSeguimientoListGcSeguimientoToAttach);
            }
            gcGestion.setGcSeguimientoList(attachedGcSeguimientoList);
            em.persist(gcGestion);
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcGestionList().add(gcGestion);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            if (estadoId != null) {
                estadoId.getGcGestionList().add(gcGestion);
                estadoId = em.merge(estadoId);
            }
            for (GcSeguimiento gcSeguimientoListGcSeguimiento : gcGestion.getGcSeguimientoList()) {
                GcGestion oldGestionIdOfGcSeguimientoListGcSeguimiento = gcSeguimientoListGcSeguimiento.getGestionId();
                gcSeguimientoListGcSeguimiento.setGestionId(gcGestion);
                gcSeguimientoListGcSeguimiento = em.merge(gcSeguimientoListGcSeguimiento);
                if (oldGestionIdOfGcSeguimientoListGcSeguimiento != null) {
                    oldGestionIdOfGcSeguimientoListGcSeguimiento.getGcSeguimientoList().remove(gcSeguimientoListGcSeguimiento);
                    oldGestionIdOfGcSeguimientoListGcSeguimiento = em.merge(oldGestionIdOfGcSeguimientoListGcSeguimiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcGestion gcGestion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcGestion persistentGcGestion = em.find(GcGestion.class, gcGestion.getId());
            GcSolicitud solicitudNumeroSolicitudOld = persistentGcGestion.getSolicitudNumeroSolicitud();
            GcSolicitud solicitudNumeroSolicitudNew = gcGestion.getSolicitudNumeroSolicitud();
            GcEstado estadoIdOld = persistentGcGestion.getEstadoId();
            GcEstado estadoIdNew = gcGestion.getEstadoId();
            List<GcSeguimiento> gcSeguimientoListOld = persistentGcGestion.getGcSeguimientoList();
            List<GcSeguimiento> gcSeguimientoListNew = gcGestion.getGcSeguimientoList();
            List<String> illegalOrphanMessages = null;
            for (GcSeguimiento gcSeguimientoListOldGcSeguimiento : gcSeguimientoListOld) {
                if (!gcSeguimientoListNew.contains(gcSeguimientoListOldGcSeguimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcSeguimiento " + gcSeguimientoListOldGcSeguimiento + " since its gestionId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (solicitudNumeroSolicitudNew != null) {
                solicitudNumeroSolicitudNew = em.getReference(solicitudNumeroSolicitudNew.getClass(), solicitudNumeroSolicitudNew.getNumeroSolicitud());
                gcGestion.setSolicitudNumeroSolicitud(solicitudNumeroSolicitudNew);
            }
            if (estadoIdNew != null) {
                estadoIdNew = em.getReference(estadoIdNew.getClass(), estadoIdNew.getId());
                gcGestion.setEstadoId(estadoIdNew);
            }
            List<GcSeguimiento> attachedGcSeguimientoListNew = new ArrayList<GcSeguimiento>();
            for (GcSeguimiento gcSeguimientoListNewGcSeguimientoToAttach : gcSeguimientoListNew) {
                gcSeguimientoListNewGcSeguimientoToAttach = em.getReference(gcSeguimientoListNewGcSeguimientoToAttach.getClass(), gcSeguimientoListNewGcSeguimientoToAttach.getId());
                attachedGcSeguimientoListNew.add(gcSeguimientoListNewGcSeguimientoToAttach);
            }
            gcSeguimientoListNew = attachedGcSeguimientoListNew;
            gcGestion.setGcSeguimientoList(gcSeguimientoListNew);
            gcGestion = em.merge(gcGestion);
            if (solicitudNumeroSolicitudOld != null && !solicitudNumeroSolicitudOld.equals(solicitudNumeroSolicitudNew)) {
                solicitudNumeroSolicitudOld.getGcGestionList().remove(gcGestion);
                solicitudNumeroSolicitudOld = em.merge(solicitudNumeroSolicitudOld);
            }
            if (solicitudNumeroSolicitudNew != null && !solicitudNumeroSolicitudNew.equals(solicitudNumeroSolicitudOld)) {
                solicitudNumeroSolicitudNew.getGcGestionList().add(gcGestion);
                solicitudNumeroSolicitudNew = em.merge(solicitudNumeroSolicitudNew);
            }
            if (estadoIdOld != null && !estadoIdOld.equals(estadoIdNew)) {
                estadoIdOld.getGcGestionList().remove(gcGestion);
                estadoIdOld = em.merge(estadoIdOld);
            }
            if (estadoIdNew != null && !estadoIdNew.equals(estadoIdOld)) {
                estadoIdNew.getGcGestionList().add(gcGestion);
                estadoIdNew = em.merge(estadoIdNew);
            }
            for (GcSeguimiento gcSeguimientoListNewGcSeguimiento : gcSeguimientoListNew) {
                if (!gcSeguimientoListOld.contains(gcSeguimientoListNewGcSeguimiento)) {
                    GcGestion oldGestionIdOfGcSeguimientoListNewGcSeguimiento = gcSeguimientoListNewGcSeguimiento.getGestionId();
                    gcSeguimientoListNewGcSeguimiento.setGestionId(gcGestion);
                    gcSeguimientoListNewGcSeguimiento = em.merge(gcSeguimientoListNewGcSeguimiento);
                    if (oldGestionIdOfGcSeguimientoListNewGcSeguimiento != null && !oldGestionIdOfGcSeguimientoListNewGcSeguimiento.equals(gcGestion)) {
                        oldGestionIdOfGcSeguimientoListNewGcSeguimiento.getGcSeguimientoList().remove(gcSeguimientoListNewGcSeguimiento);
                        oldGestionIdOfGcSeguimientoListNewGcSeguimiento = em.merge(oldGestionIdOfGcSeguimientoListNewGcSeguimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcGestion.getId();
                if (findGcGestion(id) == null) {
                    throw new NonexistentEntityException("The gcGestion with id " + id + " no longer exists.");
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
            GcGestion gcGestion;
            try {
                gcGestion = em.getReference(GcGestion.class, id);
                gcGestion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcGestion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcSeguimiento> gcSeguimientoListOrphanCheck = gcGestion.getGcSeguimientoList();
            for (GcSeguimiento gcSeguimientoListOrphanCheckGcSeguimiento : gcSeguimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcGestion (" + gcGestion + ") cannot be destroyed since the GcSeguimiento " + gcSeguimientoListOrphanCheckGcSeguimiento + " in its gcSeguimientoList field has a non-nullable gestionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            GcSolicitud solicitudNumeroSolicitud = gcGestion.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcGestionList().remove(gcGestion);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            GcEstado estadoId = gcGestion.getEstadoId();
            if (estadoId != null) {
                estadoId.getGcGestionList().remove(gcGestion);
                estadoId = em.merge(estadoId);
            }
            em.remove(gcGestion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcGestion> findGcGestionEntities() {
        return findGcGestionEntities(true, -1, -1);
    }

    public List<GcGestion> findGcGestionEntities(int maxResults, int firstResult) {
        return findGcGestionEntities(false, maxResults, firstResult);
    }

    private List<GcGestion> findGcGestionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcGestion.class));
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

    public GcGestion findGcGestion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcGestion.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcGestionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcGestion> rt = cq.from(GcGestion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
