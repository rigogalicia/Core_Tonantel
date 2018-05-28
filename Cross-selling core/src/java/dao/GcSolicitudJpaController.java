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
public class GcSolicitudJpaController implements Serializable {

    public GcSolicitudJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcSolicitud gcSolicitud) throws PreexistingEntityException, Exception {
        if (gcSolicitud.getGcGestionList() == null) {
            gcSolicitud.setGcGestionList(new ArrayList<GcGestion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcAsociado asociadoCif = gcSolicitud.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif = em.getReference(asociadoCif.getClass(), asociadoCif.getCif());
                gcSolicitud.setAsociadoCif(asociadoCif);
            }
            GcDestino destinoId = gcSolicitud.getDestinoId();
            if (destinoId != null) {
                destinoId = em.getReference(destinoId.getClass(), destinoId.getId());
                gcSolicitud.setDestinoId(destinoId);
            }
            GcTipo tipoId = gcSolicitud.getTipoId();
            if (tipoId != null) {
                tipoId = em.getReference(tipoId.getClass(), tipoId.getId());
                gcSolicitud.setTipoId(tipoId);
            }
            GcTramite tramiteId = gcSolicitud.getTramiteId();
            if (tramiteId != null) {
                tramiteId = em.getReference(tramiteId.getClass(), tramiteId.getId());
                gcSolicitud.setTramiteId(tramiteId);
            }
            List<GcGestion> attachedGcGestionList = new ArrayList<GcGestion>();
            for (GcGestion gcGestionListGcGestionToAttach : gcSolicitud.getGcGestionList()) {
                gcGestionListGcGestionToAttach = em.getReference(gcGestionListGcGestionToAttach.getClass(), gcGestionListGcGestionToAttach.getId());
                attachedGcGestionList.add(gcGestionListGcGestionToAttach);
            }
            gcSolicitud.setGcGestionList(attachedGcGestionList);
            em.persist(gcSolicitud);
            if (asociadoCif != null) {
                asociadoCif.getGcSolicitudList().add(gcSolicitud);
                asociadoCif = em.merge(asociadoCif);
            }
            if (destinoId != null) {
                destinoId.getGcSolicitudList().add(gcSolicitud);
                destinoId = em.merge(destinoId);
            }
            if (tipoId != null) {
                tipoId.getGcSolicitudList().add(gcSolicitud);
                tipoId = em.merge(tipoId);
            }
            if (tramiteId != null) {
                tramiteId.getGcSolicitudList().add(gcSolicitud);
                tramiteId = em.merge(tramiteId);
            }
            for (GcGestion gcGestionListGcGestion : gcSolicitud.getGcGestionList()) {
                GcSolicitud oldSolicitudNumeroSolicitudOfGcGestionListGcGestion = gcGestionListGcGestion.getSolicitudNumeroSolicitud();
                gcGestionListGcGestion.setSolicitudNumeroSolicitud(gcSolicitud);
                gcGestionListGcGestion = em.merge(gcGestionListGcGestion);
                if (oldSolicitudNumeroSolicitudOfGcGestionListGcGestion != null) {
                    oldSolicitudNumeroSolicitudOfGcGestionListGcGestion.getGcGestionList().remove(gcGestionListGcGestion);
                    oldSolicitudNumeroSolicitudOfGcGestionListGcGestion = em.merge(oldSolicitudNumeroSolicitudOfGcGestionListGcGestion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGcSolicitud(gcSolicitud.getNumeroSolicitud()) != null) {
                throw new PreexistingEntityException("GcSolicitud " + gcSolicitud + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcSolicitud gcSolicitud) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcSolicitud persistentGcSolicitud = em.find(GcSolicitud.class, gcSolicitud.getNumeroSolicitud());
            GcAsociado asociadoCifOld = persistentGcSolicitud.getAsociadoCif();
            GcAsociado asociadoCifNew = gcSolicitud.getAsociadoCif();
            GcDestino destinoIdOld = persistentGcSolicitud.getDestinoId();
            GcDestino destinoIdNew = gcSolicitud.getDestinoId();
            GcTipo tipoIdOld = persistentGcSolicitud.getTipoId();
            GcTipo tipoIdNew = gcSolicitud.getTipoId();
            GcTramite tramiteIdOld = persistentGcSolicitud.getTramiteId();
            GcTramite tramiteIdNew = gcSolicitud.getTramiteId();
            List<GcGestion> gcGestionListOld = persistentGcSolicitud.getGcGestionList();
            List<GcGestion> gcGestionListNew = gcSolicitud.getGcGestionList();
            List<String> illegalOrphanMessages = null;
            for (GcGestion gcGestionListOldGcGestion : gcGestionListOld) {
                if (!gcGestionListNew.contains(gcGestionListOldGcGestion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcGestion " + gcGestionListOldGcGestion + " since its solicitudNumeroSolicitud field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (asociadoCifNew != null) {
                asociadoCifNew = em.getReference(asociadoCifNew.getClass(), asociadoCifNew.getCif());
                gcSolicitud.setAsociadoCif(asociadoCifNew);
            }
            if (destinoIdNew != null) {
                destinoIdNew = em.getReference(destinoIdNew.getClass(), destinoIdNew.getId());
                gcSolicitud.setDestinoId(destinoIdNew);
            }
            if (tipoIdNew != null) {
                tipoIdNew = em.getReference(tipoIdNew.getClass(), tipoIdNew.getId());
                gcSolicitud.setTipoId(tipoIdNew);
            }
            if (tramiteIdNew != null) {
                tramiteIdNew = em.getReference(tramiteIdNew.getClass(), tramiteIdNew.getId());
                gcSolicitud.setTramiteId(tramiteIdNew);
            }
            List<GcGestion> attachedGcGestionListNew = new ArrayList<GcGestion>();
            for (GcGestion gcGestionListNewGcGestionToAttach : gcGestionListNew) {
                gcGestionListNewGcGestionToAttach = em.getReference(gcGestionListNewGcGestionToAttach.getClass(), gcGestionListNewGcGestionToAttach.getId());
                attachedGcGestionListNew.add(gcGestionListNewGcGestionToAttach);
            }
            gcGestionListNew = attachedGcGestionListNew;
            gcSolicitud.setGcGestionList(gcGestionListNew);
            gcSolicitud = em.merge(gcSolicitud);
            if (asociadoCifOld != null && !asociadoCifOld.equals(asociadoCifNew)) {
                asociadoCifOld.getGcSolicitudList().remove(gcSolicitud);
                asociadoCifOld = em.merge(asociadoCifOld);
            }
            if (asociadoCifNew != null && !asociadoCifNew.equals(asociadoCifOld)) {
                asociadoCifNew.getGcSolicitudList().add(gcSolicitud);
                asociadoCifNew = em.merge(asociadoCifNew);
            }
            if (destinoIdOld != null && !destinoIdOld.equals(destinoIdNew)) {
                destinoIdOld.getGcSolicitudList().remove(gcSolicitud);
                destinoIdOld = em.merge(destinoIdOld);
            }
            if (destinoIdNew != null && !destinoIdNew.equals(destinoIdOld)) {
                destinoIdNew.getGcSolicitudList().add(gcSolicitud);
                destinoIdNew = em.merge(destinoIdNew);
            }
            if (tipoIdOld != null && !tipoIdOld.equals(tipoIdNew)) {
                tipoIdOld.getGcSolicitudList().remove(gcSolicitud);
                tipoIdOld = em.merge(tipoIdOld);
            }
            if (tipoIdNew != null && !tipoIdNew.equals(tipoIdOld)) {
                tipoIdNew.getGcSolicitudList().add(gcSolicitud);
                tipoIdNew = em.merge(tipoIdNew);
            }
            if (tramiteIdOld != null && !tramiteIdOld.equals(tramiteIdNew)) {
                tramiteIdOld.getGcSolicitudList().remove(gcSolicitud);
                tramiteIdOld = em.merge(tramiteIdOld);
            }
            if (tramiteIdNew != null && !tramiteIdNew.equals(tramiteIdOld)) {
                tramiteIdNew.getGcSolicitudList().add(gcSolicitud);
                tramiteIdNew = em.merge(tramiteIdNew);
            }
            for (GcGestion gcGestionListNewGcGestion : gcGestionListNew) {
                if (!gcGestionListOld.contains(gcGestionListNewGcGestion)) {
                    GcSolicitud oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion = gcGestionListNewGcGestion.getSolicitudNumeroSolicitud();
                    gcGestionListNewGcGestion.setSolicitudNumeroSolicitud(gcSolicitud);
                    gcGestionListNewGcGestion = em.merge(gcGestionListNewGcGestion);
                    if (oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion != null && !oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion.equals(gcSolicitud)) {
                        oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion.getGcGestionList().remove(gcGestionListNewGcGestion);
                        oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion = em.merge(oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = gcSolicitud.getNumeroSolicitud();
                if (findGcSolicitud(id) == null) {
                    throw new NonexistentEntityException("The gcSolicitud with id " + id + " no longer exists.");
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
            GcSolicitud gcSolicitud;
            try {
                gcSolicitud = em.getReference(GcSolicitud.class, id);
                gcSolicitud.getNumeroSolicitud();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcSolicitud with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcGestion> gcGestionListOrphanCheck = gcSolicitud.getGcGestionList();
            for (GcGestion gcGestionListOrphanCheckGcGestion : gcGestionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcSolicitud (" + gcSolicitud + ") cannot be destroyed since the GcGestion " + gcGestionListOrphanCheckGcGestion + " in its gcGestionList field has a non-nullable solicitudNumeroSolicitud field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            GcAsociado asociadoCif = gcSolicitud.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif.getGcSolicitudList().remove(gcSolicitud);
                asociadoCif = em.merge(asociadoCif);
            }
            GcDestino destinoId = gcSolicitud.getDestinoId();
            if (destinoId != null) {
                destinoId.getGcSolicitudList().remove(gcSolicitud);
                destinoId = em.merge(destinoId);
            }
            GcTipo tipoId = gcSolicitud.getTipoId();
            if (tipoId != null) {
                tipoId.getGcSolicitudList().remove(gcSolicitud);
                tipoId = em.merge(tipoId);
            }
            GcTramite tramiteId = gcSolicitud.getTramiteId();
            if (tramiteId != null) {
                tramiteId.getGcSolicitudList().remove(gcSolicitud);
                tramiteId = em.merge(tramiteId);
            }
            em.remove(gcSolicitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcSolicitud> findGcSolicitudEntities() {
        return findGcSolicitudEntities(true, -1, -1);
    }

    public List<GcSolicitud> findGcSolicitudEntities(int maxResults, int firstResult) {
        return findGcSolicitudEntities(false, maxResults, firstResult);
    }

    private List<GcSolicitud> findGcSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcSolicitud.class));
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

    public GcSolicitud findGcSolicitud(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcSolicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcSolicitud> rt = cq.from(GcSolicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
