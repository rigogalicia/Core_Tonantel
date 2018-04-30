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
public class RallyAsignacionJpaController implements Serializable {

    public RallyAsignacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallyAsignacion rallyAsignacion) {
        if (rallyAsignacion.getRallySeguimientoList() == null) {
            rallyAsignacion.setRallySeguimientoList(new ArrayList<RallySeguimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyMeta rallyMetaIdmeta = rallyAsignacion.getRallyMetaIdmeta();
            if (rallyMetaIdmeta != null) {
                rallyMetaIdmeta = em.getReference(rallyMetaIdmeta.getClass(), rallyMetaIdmeta.getIdmeta());
                rallyAsignacion.setRallyMetaIdmeta(rallyMetaIdmeta);
            }
            RallyReferencia rallyReferenciaIdreferencia = rallyAsignacion.getRallyReferenciaIdreferencia();
            if (rallyReferenciaIdreferencia != null) {
                rallyReferenciaIdreferencia = em.getReference(rallyReferenciaIdreferencia.getClass(), rallyReferenciaIdreferencia.getIdreferencia());
                rallyAsignacion.setRallyReferenciaIdreferencia(rallyReferenciaIdreferencia);
            }
            List<RallySeguimiento> attachedRallySeguimientoList = new ArrayList<RallySeguimiento>();
            for (RallySeguimiento rallySeguimientoListRallySeguimientoToAttach : rallyAsignacion.getRallySeguimientoList()) {
                rallySeguimientoListRallySeguimientoToAttach = em.getReference(rallySeguimientoListRallySeguimientoToAttach.getClass(), rallySeguimientoListRallySeguimientoToAttach.getIdseguimiento());
                attachedRallySeguimientoList.add(rallySeguimientoListRallySeguimientoToAttach);
            }
            rallyAsignacion.setRallySeguimientoList(attachedRallySeguimientoList);
            em.persist(rallyAsignacion);
            if (rallyMetaIdmeta != null) {
                rallyMetaIdmeta.getRallyAsignacionList().add(rallyAsignacion);
                rallyMetaIdmeta = em.merge(rallyMetaIdmeta);
            }
            if (rallyReferenciaIdreferencia != null) {
                rallyReferenciaIdreferencia.getRallyAsignacionList().add(rallyAsignacion);
                rallyReferenciaIdreferencia = em.merge(rallyReferenciaIdreferencia);
            }
            for (RallySeguimiento rallySeguimientoListRallySeguimiento : rallyAsignacion.getRallySeguimientoList()) {
                RallyAsignacion oldRallyAsignacionIdasignacionOfRallySeguimientoListRallySeguimiento = rallySeguimientoListRallySeguimiento.getRallyAsignacionIdasignacion();
                rallySeguimientoListRallySeguimiento.setRallyAsignacionIdasignacion(rallyAsignacion);
                rallySeguimientoListRallySeguimiento = em.merge(rallySeguimientoListRallySeguimiento);
                if (oldRallyAsignacionIdasignacionOfRallySeguimientoListRallySeguimiento != null) {
                    oldRallyAsignacionIdasignacionOfRallySeguimientoListRallySeguimiento.getRallySeguimientoList().remove(rallySeguimientoListRallySeguimiento);
                    oldRallyAsignacionIdasignacionOfRallySeguimientoListRallySeguimiento = em.merge(oldRallyAsignacionIdasignacionOfRallySeguimientoListRallySeguimiento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallyAsignacion rallyAsignacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyAsignacion persistentRallyAsignacion = em.find(RallyAsignacion.class, rallyAsignacion.getIdasignacion());
            RallyMeta rallyMetaIdmetaOld = persistentRallyAsignacion.getRallyMetaIdmeta();
            RallyMeta rallyMetaIdmetaNew = rallyAsignacion.getRallyMetaIdmeta();
            RallyReferencia rallyReferenciaIdreferenciaOld = persistentRallyAsignacion.getRallyReferenciaIdreferencia();
            RallyReferencia rallyReferenciaIdreferenciaNew = rallyAsignacion.getRallyReferenciaIdreferencia();
            List<RallySeguimiento> rallySeguimientoListOld = persistentRallyAsignacion.getRallySeguimientoList();
            List<RallySeguimiento> rallySeguimientoListNew = rallyAsignacion.getRallySeguimientoList();
            List<String> illegalOrphanMessages = null;
            for (RallySeguimiento rallySeguimientoListOldRallySeguimiento : rallySeguimientoListOld) {
                if (!rallySeguimientoListNew.contains(rallySeguimientoListOldRallySeguimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RallySeguimiento " + rallySeguimientoListOldRallySeguimiento + " since its rallyAsignacionIdasignacion field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rallyMetaIdmetaNew != null) {
                rallyMetaIdmetaNew = em.getReference(rallyMetaIdmetaNew.getClass(), rallyMetaIdmetaNew.getIdmeta());
                rallyAsignacion.setRallyMetaIdmeta(rallyMetaIdmetaNew);
            }
            if (rallyReferenciaIdreferenciaNew != null) {
                rallyReferenciaIdreferenciaNew = em.getReference(rallyReferenciaIdreferenciaNew.getClass(), rallyReferenciaIdreferenciaNew.getIdreferencia());
                rallyAsignacion.setRallyReferenciaIdreferencia(rallyReferenciaIdreferenciaNew);
            }
            List<RallySeguimiento> attachedRallySeguimientoListNew = new ArrayList<RallySeguimiento>();
            for (RallySeguimiento rallySeguimientoListNewRallySeguimientoToAttach : rallySeguimientoListNew) {
                rallySeguimientoListNewRallySeguimientoToAttach = em.getReference(rallySeguimientoListNewRallySeguimientoToAttach.getClass(), rallySeguimientoListNewRallySeguimientoToAttach.getIdseguimiento());
                attachedRallySeguimientoListNew.add(rallySeguimientoListNewRallySeguimientoToAttach);
            }
            rallySeguimientoListNew = attachedRallySeguimientoListNew;
            rallyAsignacion.setRallySeguimientoList(rallySeguimientoListNew);
            rallyAsignacion = em.merge(rallyAsignacion);
            if (rallyMetaIdmetaOld != null && !rallyMetaIdmetaOld.equals(rallyMetaIdmetaNew)) {
                rallyMetaIdmetaOld.getRallyAsignacionList().remove(rallyAsignacion);
                rallyMetaIdmetaOld = em.merge(rallyMetaIdmetaOld);
            }
            if (rallyMetaIdmetaNew != null && !rallyMetaIdmetaNew.equals(rallyMetaIdmetaOld)) {
                rallyMetaIdmetaNew.getRallyAsignacionList().add(rallyAsignacion);
                rallyMetaIdmetaNew = em.merge(rallyMetaIdmetaNew);
            }
            if (rallyReferenciaIdreferenciaOld != null && !rallyReferenciaIdreferenciaOld.equals(rallyReferenciaIdreferenciaNew)) {
                rallyReferenciaIdreferenciaOld.getRallyAsignacionList().remove(rallyAsignacion);
                rallyReferenciaIdreferenciaOld = em.merge(rallyReferenciaIdreferenciaOld);
            }
            if (rallyReferenciaIdreferenciaNew != null && !rallyReferenciaIdreferenciaNew.equals(rallyReferenciaIdreferenciaOld)) {
                rallyReferenciaIdreferenciaNew.getRallyAsignacionList().add(rallyAsignacion);
                rallyReferenciaIdreferenciaNew = em.merge(rallyReferenciaIdreferenciaNew);
            }
            for (RallySeguimiento rallySeguimientoListNewRallySeguimiento : rallySeguimientoListNew) {
                if (!rallySeguimientoListOld.contains(rallySeguimientoListNewRallySeguimiento)) {
                    RallyAsignacion oldRallyAsignacionIdasignacionOfRallySeguimientoListNewRallySeguimiento = rallySeguimientoListNewRallySeguimiento.getRallyAsignacionIdasignacion();
                    rallySeguimientoListNewRallySeguimiento.setRallyAsignacionIdasignacion(rallyAsignacion);
                    rallySeguimientoListNewRallySeguimiento = em.merge(rallySeguimientoListNewRallySeguimiento);
                    if (oldRallyAsignacionIdasignacionOfRallySeguimientoListNewRallySeguimiento != null && !oldRallyAsignacionIdasignacionOfRallySeguimientoListNewRallySeguimiento.equals(rallyAsignacion)) {
                        oldRallyAsignacionIdasignacionOfRallySeguimientoListNewRallySeguimiento.getRallySeguimientoList().remove(rallySeguimientoListNewRallySeguimiento);
                        oldRallyAsignacionIdasignacionOfRallySeguimientoListNewRallySeguimiento = em.merge(oldRallyAsignacionIdasignacionOfRallySeguimientoListNewRallySeguimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallyAsignacion.getIdasignacion();
                if (findRallyAsignacion(id) == null) {
                    throw new NonexistentEntityException("The rallyAsignacion with id " + id + " no longer exists.");
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
            RallyAsignacion rallyAsignacion;
            try {
                rallyAsignacion = em.getReference(RallyAsignacion.class, id);
                rallyAsignacion.getIdasignacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallyAsignacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RallySeguimiento> rallySeguimientoListOrphanCheck = rallyAsignacion.getRallySeguimientoList();
            for (RallySeguimiento rallySeguimientoListOrphanCheckRallySeguimiento : rallySeguimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RallyAsignacion (" + rallyAsignacion + ") cannot be destroyed since the RallySeguimiento " + rallySeguimientoListOrphanCheckRallySeguimiento + " in its rallySeguimientoList field has a non-nullable rallyAsignacionIdasignacion field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            RallyMeta rallyMetaIdmeta = rallyAsignacion.getRallyMetaIdmeta();
            if (rallyMetaIdmeta != null) {
                rallyMetaIdmeta.getRallyAsignacionList().remove(rallyAsignacion);
                rallyMetaIdmeta = em.merge(rallyMetaIdmeta);
            }
            RallyReferencia rallyReferenciaIdreferencia = rallyAsignacion.getRallyReferenciaIdreferencia();
            if (rallyReferenciaIdreferencia != null) {
                rallyReferenciaIdreferencia.getRallyAsignacionList().remove(rallyAsignacion);
                rallyReferenciaIdreferencia = em.merge(rallyReferenciaIdreferencia);
            }
            em.remove(rallyAsignacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallyAsignacion> findRallyAsignacionEntities() {
        return findRallyAsignacionEntities(true, -1, -1);
    }

    public List<RallyAsignacion> findRallyAsignacionEntities(int maxResults, int firstResult) {
        return findRallyAsignacionEntities(false, maxResults, firstResult);
    }

    private List<RallyAsignacion> findRallyAsignacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallyAsignacion.class));
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

    public RallyAsignacion findRallyAsignacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallyAsignacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallyAsignacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallyAsignacion> rt = cq.from(RallyAsignacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
