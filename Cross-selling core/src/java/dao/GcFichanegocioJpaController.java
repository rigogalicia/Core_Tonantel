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
 * @author Desarrollo
 */
public class GcFichanegocioJpaController implements Serializable {

    public GcFichanegocioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcFichanegocio gcFichanegocio) {
        if (gcFichanegocio.getGcLogprocesonegocioList() == null) {
            gcFichanegocio.setGcLogprocesonegocioList(new ArrayList<GcLogprocesonegocio>());
        }
        if (gcFichanegocio.getGcVentajasList() == null) {
            gcFichanegocio.setGcVentajasList(new ArrayList<GcVentajas>());
        }
        if (gcFichanegocio.getGcDesventajasList() == null) {
            gcFichanegocio.setGcDesventajasList(new ArrayList<GcDesventajas>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcSolicitud solicitudNumeroSolicitud = gcFichanegocio.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud = em.getReference(solicitudNumeroSolicitud.getClass(), solicitudNumeroSolicitud.getNumeroSolicitud());
                gcFichanegocio.setSolicitudNumeroSolicitud(solicitudNumeroSolicitud);
            }
            List<GcLogprocesonegocio> attachedGcLogprocesonegocioList = new ArrayList<GcLogprocesonegocio>();
            for (GcLogprocesonegocio gcLogprocesonegocioListGcLogprocesonegocioToAttach : gcFichanegocio.getGcLogprocesonegocioList()) {
                gcLogprocesonegocioListGcLogprocesonegocioToAttach = em.getReference(gcLogprocesonegocioListGcLogprocesonegocioToAttach.getClass(), gcLogprocesonegocioListGcLogprocesonegocioToAttach.getId());
                attachedGcLogprocesonegocioList.add(gcLogprocesonegocioListGcLogprocesonegocioToAttach);
            }
            gcFichanegocio.setGcLogprocesonegocioList(attachedGcLogprocesonegocioList);
            List<GcVentajas> attachedGcVentajasList = new ArrayList<GcVentajas>();
            for (GcVentajas gcVentajasListGcVentajasToAttach : gcFichanegocio.getGcVentajasList()) {
                gcVentajasListGcVentajasToAttach = em.getReference(gcVentajasListGcVentajasToAttach.getClass(), gcVentajasListGcVentajasToAttach.getId());
                attachedGcVentajasList.add(gcVentajasListGcVentajasToAttach);
            }
            gcFichanegocio.setGcVentajasList(attachedGcVentajasList);
            List<GcDesventajas> attachedGcDesventajasList = new ArrayList<GcDesventajas>();
            for (GcDesventajas gcDesventajasListGcDesventajasToAttach : gcFichanegocio.getGcDesventajasList()) {
                gcDesventajasListGcDesventajasToAttach = em.getReference(gcDesventajasListGcDesventajasToAttach.getClass(), gcDesventajasListGcDesventajasToAttach.getId());
                attachedGcDesventajasList.add(gcDesventajasListGcDesventajasToAttach);
            }
            gcFichanegocio.setGcDesventajasList(attachedGcDesventajasList);
            em.persist(gcFichanegocio);
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcFichanegocioList().add(gcFichanegocio);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            for (GcLogprocesonegocio gcLogprocesonegocioListGcLogprocesonegocio : gcFichanegocio.getGcLogprocesonegocioList()) {
                GcFichanegocio oldFichanegocioIdOfGcLogprocesonegocioListGcLogprocesonegocio = gcLogprocesonegocioListGcLogprocesonegocio.getFichanegocioId();
                gcLogprocesonegocioListGcLogprocesonegocio.setFichanegocioId(gcFichanegocio);
                gcLogprocesonegocioListGcLogprocesonegocio = em.merge(gcLogprocesonegocioListGcLogprocesonegocio);
                if (oldFichanegocioIdOfGcLogprocesonegocioListGcLogprocesonegocio != null) {
                    oldFichanegocioIdOfGcLogprocesonegocioListGcLogprocesonegocio.getGcLogprocesonegocioList().remove(gcLogprocesonegocioListGcLogprocesonegocio);
                    oldFichanegocioIdOfGcLogprocesonegocioListGcLogprocesonegocio = em.merge(oldFichanegocioIdOfGcLogprocesonegocioListGcLogprocesonegocio);
                }
            }
            for (GcVentajas gcVentajasListGcVentajas : gcFichanegocio.getGcVentajasList()) {
                GcFichanegocio oldFichanegocioIdOfGcVentajasListGcVentajas = gcVentajasListGcVentajas.getFichanegocioId();
                gcVentajasListGcVentajas.setFichanegocioId(gcFichanegocio);
                gcVentajasListGcVentajas = em.merge(gcVentajasListGcVentajas);
                if (oldFichanegocioIdOfGcVentajasListGcVentajas != null) {
                    oldFichanegocioIdOfGcVentajasListGcVentajas.getGcVentajasList().remove(gcVentajasListGcVentajas);
                    oldFichanegocioIdOfGcVentajasListGcVentajas = em.merge(oldFichanegocioIdOfGcVentajasListGcVentajas);
                }
            }
            for (GcDesventajas gcDesventajasListGcDesventajas : gcFichanegocio.getGcDesventajasList()) {
                GcFichanegocio oldFichanegocioIdOfGcDesventajasListGcDesventajas = gcDesventajasListGcDesventajas.getFichanegocioId();
                gcDesventajasListGcDesventajas.setFichanegocioId(gcFichanegocio);
                gcDesventajasListGcDesventajas = em.merge(gcDesventajasListGcDesventajas);
                if (oldFichanegocioIdOfGcDesventajasListGcDesventajas != null) {
                    oldFichanegocioIdOfGcDesventajasListGcDesventajas.getGcDesventajasList().remove(gcDesventajasListGcDesventajas);
                    oldFichanegocioIdOfGcDesventajasListGcDesventajas = em.merge(oldFichanegocioIdOfGcDesventajasListGcDesventajas);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcFichanegocio gcFichanegocio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcFichanegocio persistentGcFichanegocio = em.find(GcFichanegocio.class, gcFichanegocio.getId());
            GcSolicitud solicitudNumeroSolicitudOld = persistentGcFichanegocio.getSolicitudNumeroSolicitud();
            GcSolicitud solicitudNumeroSolicitudNew = gcFichanegocio.getSolicitudNumeroSolicitud();
            List<GcLogprocesonegocio> gcLogprocesonegocioListOld = persistentGcFichanegocio.getGcLogprocesonegocioList();
            List<GcLogprocesonegocio> gcLogprocesonegocioListNew = gcFichanegocio.getGcLogprocesonegocioList();
            List<GcVentajas> gcVentajasListOld = persistentGcFichanegocio.getGcVentajasList();
            List<GcVentajas> gcVentajasListNew = gcFichanegocio.getGcVentajasList();
            List<GcDesventajas> gcDesventajasListOld = persistentGcFichanegocio.getGcDesventajasList();
            List<GcDesventajas> gcDesventajasListNew = gcFichanegocio.getGcDesventajasList();
            List<String> illegalOrphanMessages = null;
            for (GcLogprocesonegocio gcLogprocesonegocioListOldGcLogprocesonegocio : gcLogprocesonegocioListOld) {
                if (!gcLogprocesonegocioListNew.contains(gcLogprocesonegocioListOldGcLogprocesonegocio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcLogprocesonegocio " + gcLogprocesonegocioListOldGcLogprocesonegocio + " since its fichanegocioId field is not nullable.");
                }
            }
            for (GcVentajas gcVentajasListOldGcVentajas : gcVentajasListOld) {
                if (!gcVentajasListNew.contains(gcVentajasListOldGcVentajas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcVentajas " + gcVentajasListOldGcVentajas + " since its fichanegocioId field is not nullable.");
                }
            }
            for (GcDesventajas gcDesventajasListOldGcDesventajas : gcDesventajasListOld) {
                if (!gcDesventajasListNew.contains(gcDesventajasListOldGcDesventajas)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcDesventajas " + gcDesventajasListOldGcDesventajas + " since its fichanegocioId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (solicitudNumeroSolicitudNew != null) {
                solicitudNumeroSolicitudNew = em.getReference(solicitudNumeroSolicitudNew.getClass(), solicitudNumeroSolicitudNew.getNumeroSolicitud());
                gcFichanegocio.setSolicitudNumeroSolicitud(solicitudNumeroSolicitudNew);
            }
            List<GcLogprocesonegocio> attachedGcLogprocesonegocioListNew = new ArrayList<GcLogprocesonegocio>();
            for (GcLogprocesonegocio gcLogprocesonegocioListNewGcLogprocesonegocioToAttach : gcLogprocesonegocioListNew) {
                gcLogprocesonegocioListNewGcLogprocesonegocioToAttach = em.getReference(gcLogprocesonegocioListNewGcLogprocesonegocioToAttach.getClass(), gcLogprocesonegocioListNewGcLogprocesonegocioToAttach.getId());
                attachedGcLogprocesonegocioListNew.add(gcLogprocesonegocioListNewGcLogprocesonegocioToAttach);
            }
            gcLogprocesonegocioListNew = attachedGcLogprocesonegocioListNew;
            gcFichanegocio.setGcLogprocesonegocioList(gcLogprocesonegocioListNew);
            List<GcVentajas> attachedGcVentajasListNew = new ArrayList<GcVentajas>();
            for (GcVentajas gcVentajasListNewGcVentajasToAttach : gcVentajasListNew) {
                gcVentajasListNewGcVentajasToAttach = em.getReference(gcVentajasListNewGcVentajasToAttach.getClass(), gcVentajasListNewGcVentajasToAttach.getId());
                attachedGcVentajasListNew.add(gcVentajasListNewGcVentajasToAttach);
            }
            gcVentajasListNew = attachedGcVentajasListNew;
            gcFichanegocio.setGcVentajasList(gcVentajasListNew);
            List<GcDesventajas> attachedGcDesventajasListNew = new ArrayList<GcDesventajas>();
            for (GcDesventajas gcDesventajasListNewGcDesventajasToAttach : gcDesventajasListNew) {
                gcDesventajasListNewGcDesventajasToAttach = em.getReference(gcDesventajasListNewGcDesventajasToAttach.getClass(), gcDesventajasListNewGcDesventajasToAttach.getId());
                attachedGcDesventajasListNew.add(gcDesventajasListNewGcDesventajasToAttach);
            }
            gcDesventajasListNew = attachedGcDesventajasListNew;
            gcFichanegocio.setGcDesventajasList(gcDesventajasListNew);
            gcFichanegocio = em.merge(gcFichanegocio);
            if (solicitudNumeroSolicitudOld != null && !solicitudNumeroSolicitudOld.equals(solicitudNumeroSolicitudNew)) {
                solicitudNumeroSolicitudOld.getGcFichanegocioList().remove(gcFichanegocio);
                solicitudNumeroSolicitudOld = em.merge(solicitudNumeroSolicitudOld);
            }
            if (solicitudNumeroSolicitudNew != null && !solicitudNumeroSolicitudNew.equals(solicitudNumeroSolicitudOld)) {
                solicitudNumeroSolicitudNew.getGcFichanegocioList().add(gcFichanegocio);
                solicitudNumeroSolicitudNew = em.merge(solicitudNumeroSolicitudNew);
            }
            for (GcLogprocesonegocio gcLogprocesonegocioListNewGcLogprocesonegocio : gcLogprocesonegocioListNew) {
                if (!gcLogprocesonegocioListOld.contains(gcLogprocesonegocioListNewGcLogprocesonegocio)) {
                    GcFichanegocio oldFichanegocioIdOfGcLogprocesonegocioListNewGcLogprocesonegocio = gcLogprocesonegocioListNewGcLogprocesonegocio.getFichanegocioId();
                    gcLogprocesonegocioListNewGcLogprocesonegocio.setFichanegocioId(gcFichanegocio);
                    gcLogprocesonegocioListNewGcLogprocesonegocio = em.merge(gcLogprocesonegocioListNewGcLogprocesonegocio);
                    if (oldFichanegocioIdOfGcLogprocesonegocioListNewGcLogprocesonegocio != null && !oldFichanegocioIdOfGcLogprocesonegocioListNewGcLogprocesonegocio.equals(gcFichanegocio)) {
                        oldFichanegocioIdOfGcLogprocesonegocioListNewGcLogprocesonegocio.getGcLogprocesonegocioList().remove(gcLogprocesonegocioListNewGcLogprocesonegocio);
                        oldFichanegocioIdOfGcLogprocesonegocioListNewGcLogprocesonegocio = em.merge(oldFichanegocioIdOfGcLogprocesonegocioListNewGcLogprocesonegocio);
                    }
                }
            }
            for (GcVentajas gcVentajasListNewGcVentajas : gcVentajasListNew) {
                if (!gcVentajasListOld.contains(gcVentajasListNewGcVentajas)) {
                    GcFichanegocio oldFichanegocioIdOfGcVentajasListNewGcVentajas = gcVentajasListNewGcVentajas.getFichanegocioId();
                    gcVentajasListNewGcVentajas.setFichanegocioId(gcFichanegocio);
                    gcVentajasListNewGcVentajas = em.merge(gcVentajasListNewGcVentajas);
                    if (oldFichanegocioIdOfGcVentajasListNewGcVentajas != null && !oldFichanegocioIdOfGcVentajasListNewGcVentajas.equals(gcFichanegocio)) {
                        oldFichanegocioIdOfGcVentajasListNewGcVentajas.getGcVentajasList().remove(gcVentajasListNewGcVentajas);
                        oldFichanegocioIdOfGcVentajasListNewGcVentajas = em.merge(oldFichanegocioIdOfGcVentajasListNewGcVentajas);
                    }
                }
            }
            for (GcDesventajas gcDesventajasListNewGcDesventajas : gcDesventajasListNew) {
                if (!gcDesventajasListOld.contains(gcDesventajasListNewGcDesventajas)) {
                    GcFichanegocio oldFichanegocioIdOfGcDesventajasListNewGcDesventajas = gcDesventajasListNewGcDesventajas.getFichanegocioId();
                    gcDesventajasListNewGcDesventajas.setFichanegocioId(gcFichanegocio);
                    gcDesventajasListNewGcDesventajas = em.merge(gcDesventajasListNewGcDesventajas);
                    if (oldFichanegocioIdOfGcDesventajasListNewGcDesventajas != null && !oldFichanegocioIdOfGcDesventajasListNewGcDesventajas.equals(gcFichanegocio)) {
                        oldFichanegocioIdOfGcDesventajasListNewGcDesventajas.getGcDesventajasList().remove(gcDesventajasListNewGcDesventajas);
                        oldFichanegocioIdOfGcDesventajasListNewGcDesventajas = em.merge(oldFichanegocioIdOfGcDesventajasListNewGcDesventajas);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = gcFichanegocio.getId();
                if (findGcFichanegocio(id) == null) {
                    throw new NonexistentEntityException("The gcFichanegocio with id " + id + " no longer exists.");
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
            GcFichanegocio gcFichanegocio;
            try {
                gcFichanegocio = em.getReference(GcFichanegocio.class, id);
                gcFichanegocio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcFichanegocio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcLogprocesonegocio> gcLogprocesonegocioListOrphanCheck = gcFichanegocio.getGcLogprocesonegocioList();
            for (GcLogprocesonegocio gcLogprocesonegocioListOrphanCheckGcLogprocesonegocio : gcLogprocesonegocioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcFichanegocio (" + gcFichanegocio + ") cannot be destroyed since the GcLogprocesonegocio " + gcLogprocesonegocioListOrphanCheckGcLogprocesonegocio + " in its gcLogprocesonegocioList field has a non-nullable fichanegocioId field.");
            }
            List<GcVentajas> gcVentajasListOrphanCheck = gcFichanegocio.getGcVentajasList();
            for (GcVentajas gcVentajasListOrphanCheckGcVentajas : gcVentajasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcFichanegocio (" + gcFichanegocio + ") cannot be destroyed since the GcVentajas " + gcVentajasListOrphanCheckGcVentajas + " in its gcVentajasList field has a non-nullable fichanegocioId field.");
            }
            List<GcDesventajas> gcDesventajasListOrphanCheck = gcFichanegocio.getGcDesventajasList();
            for (GcDesventajas gcDesventajasListOrphanCheckGcDesventajas : gcDesventajasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcFichanegocio (" + gcFichanegocio + ") cannot be destroyed since the GcDesventajas " + gcDesventajasListOrphanCheckGcDesventajas + " in its gcDesventajasList field has a non-nullable fichanegocioId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            GcSolicitud solicitudNumeroSolicitud = gcFichanegocio.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getGcFichanegocioList().remove(gcFichanegocio);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            em.remove(gcFichanegocio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcFichanegocio> findGcFichanegocioEntities() {
        return findGcFichanegocioEntities(true, -1, -1);
    }

    public List<GcFichanegocio> findGcFichanegocioEntities(int maxResults, int firstResult) {
        return findGcFichanegocioEntities(false, maxResults, firstResult);
    }

    private List<GcFichanegocio> findGcFichanegocioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcFichanegocio.class));
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

    public GcFichanegocio findGcFichanegocio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcFichanegocio.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcFichanegocioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcFichanegocio> rt = cq.from(GcFichanegocio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
