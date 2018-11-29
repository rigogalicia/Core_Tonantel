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
 * @author Desarrollo
 */
public class AvSolicitudJpaController implements Serializable {

    public AvSolicitudJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvSolicitud avSolicitud) throws PreexistingEntityException, Exception {
        if (avSolicitud.getAvAsignacionList() == null) {
            avSolicitud.setAvAsignacionList(new ArrayList<AvAsignacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvAsociado asociadoCif = avSolicitud.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif = em.getReference(asociadoCif.getClass(), asociadoCif.getCif());
                avSolicitud.setAsociadoCif(asociadoCif);
            }
            AvInmueble inmuebleId = avSolicitud.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId = em.getReference(inmuebleId.getClass(), inmuebleId.getId());
                avSolicitud.setInmuebleId(inmuebleId);
            }
            List<AvAsignacion> attachedAvAsignacionList = new ArrayList<AvAsignacion>();
            for (AvAsignacion avAsignacionListAvAsignacionToAttach : avSolicitud.getAvAsignacionList()) {
                avAsignacionListAvAsignacionToAttach = em.getReference(avAsignacionListAvAsignacionToAttach.getClass(), avAsignacionListAvAsignacionToAttach.getId());
                attachedAvAsignacionList.add(avAsignacionListAvAsignacionToAttach);
            }
            avSolicitud.setAvAsignacionList(attachedAvAsignacionList);
            em.persist(avSolicitud);
            if (asociadoCif != null) {
                asociadoCif.getAvSolicitudList().add(avSolicitud);
                asociadoCif = em.merge(asociadoCif);
            }
            if (inmuebleId != null) {
                inmuebleId.getAvSolicitudList().add(avSolicitud);
                inmuebleId = em.merge(inmuebleId);
            }
            for (AvAsignacion avAsignacionListAvAsignacion : avSolicitud.getAvAsignacionList()) {
                AvSolicitud oldSolicitudNumeroSolicitudOfAvAsignacionListAvAsignacion = avAsignacionListAvAsignacion.getSolicitudNumeroSolicitud();
                avAsignacionListAvAsignacion.setSolicitudNumeroSolicitud(avSolicitud);
                avAsignacionListAvAsignacion = em.merge(avAsignacionListAvAsignacion);
                if (oldSolicitudNumeroSolicitudOfAvAsignacionListAvAsignacion != null) {
                    oldSolicitudNumeroSolicitudOfAvAsignacionListAvAsignacion.getAvAsignacionList().remove(avAsignacionListAvAsignacion);
                    oldSolicitudNumeroSolicitudOfAvAsignacionListAvAsignacion = em.merge(oldSolicitudNumeroSolicitudOfAvAsignacionListAvAsignacion);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAvSolicitud(avSolicitud.getNumeroSolicitud()) != null) {
                throw new PreexistingEntityException("AvSolicitud " + avSolicitud + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvSolicitud avSolicitud) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvSolicitud persistentAvSolicitud = em.find(AvSolicitud.class, avSolicitud.getNumeroSolicitud());
            AvAsociado asociadoCifOld = persistentAvSolicitud.getAsociadoCif();
            AvAsociado asociadoCifNew = avSolicitud.getAsociadoCif();
            AvInmueble inmuebleIdOld = persistentAvSolicitud.getInmuebleId();
            AvInmueble inmuebleIdNew = avSolicitud.getInmuebleId();
            List<AvAsignacion> avAsignacionListOld = persistentAvSolicitud.getAvAsignacionList();
            List<AvAsignacion> avAsignacionListNew = avSolicitud.getAvAsignacionList();
            List<String> illegalOrphanMessages = null;
            for (AvAsignacion avAsignacionListOldAvAsignacion : avAsignacionListOld) {
                if (!avAsignacionListNew.contains(avAsignacionListOldAvAsignacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvAsignacion " + avAsignacionListOldAvAsignacion + " since its solicitudNumeroSolicitud field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (asociadoCifNew != null) {
                asociadoCifNew = em.getReference(asociadoCifNew.getClass(), asociadoCifNew.getCif());
                avSolicitud.setAsociadoCif(asociadoCifNew);
            }
            if (inmuebleIdNew != null) {
                inmuebleIdNew = em.getReference(inmuebleIdNew.getClass(), inmuebleIdNew.getId());
                avSolicitud.setInmuebleId(inmuebleIdNew);
            }
            List<AvAsignacion> attachedAvAsignacionListNew = new ArrayList<AvAsignacion>();
            for (AvAsignacion avAsignacionListNewAvAsignacionToAttach : avAsignacionListNew) {
                avAsignacionListNewAvAsignacionToAttach = em.getReference(avAsignacionListNewAvAsignacionToAttach.getClass(), avAsignacionListNewAvAsignacionToAttach.getId());
                attachedAvAsignacionListNew.add(avAsignacionListNewAvAsignacionToAttach);
            }
            avAsignacionListNew = attachedAvAsignacionListNew;
            avSolicitud.setAvAsignacionList(avAsignacionListNew);
            avSolicitud = em.merge(avSolicitud);
            if (asociadoCifOld != null && !asociadoCifOld.equals(asociadoCifNew)) {
                asociadoCifOld.getAvSolicitudList().remove(avSolicitud);
                asociadoCifOld = em.merge(asociadoCifOld);
            }
            if (asociadoCifNew != null && !asociadoCifNew.equals(asociadoCifOld)) {
                asociadoCifNew.getAvSolicitudList().add(avSolicitud);
                asociadoCifNew = em.merge(asociadoCifNew);
            }
            if (inmuebleIdOld != null && !inmuebleIdOld.equals(inmuebleIdNew)) {
                inmuebleIdOld.getAvSolicitudList().remove(avSolicitud);
                inmuebleIdOld = em.merge(inmuebleIdOld);
            }
            if (inmuebleIdNew != null && !inmuebleIdNew.equals(inmuebleIdOld)) {
                inmuebleIdNew.getAvSolicitudList().add(avSolicitud);
                inmuebleIdNew = em.merge(inmuebleIdNew);
            }
            for (AvAsignacion avAsignacionListNewAvAsignacion : avAsignacionListNew) {
                if (!avAsignacionListOld.contains(avAsignacionListNewAvAsignacion)) {
                    AvSolicitud oldSolicitudNumeroSolicitudOfAvAsignacionListNewAvAsignacion = avAsignacionListNewAvAsignacion.getSolicitudNumeroSolicitud();
                    avAsignacionListNewAvAsignacion.setSolicitudNumeroSolicitud(avSolicitud);
                    avAsignacionListNewAvAsignacion = em.merge(avAsignacionListNewAvAsignacion);
                    if (oldSolicitudNumeroSolicitudOfAvAsignacionListNewAvAsignacion != null && !oldSolicitudNumeroSolicitudOfAvAsignacionListNewAvAsignacion.equals(avSolicitud)) {
                        oldSolicitudNumeroSolicitudOfAvAsignacionListNewAvAsignacion.getAvAsignacionList().remove(avAsignacionListNewAvAsignacion);
                        oldSolicitudNumeroSolicitudOfAvAsignacionListNewAvAsignacion = em.merge(oldSolicitudNumeroSolicitudOfAvAsignacionListNewAvAsignacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = avSolicitud.getNumeroSolicitud();
                if (findAvSolicitud(id) == null) {
                    throw new NonexistentEntityException("The avSolicitud with id " + id + " no longer exists.");
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
            AvSolicitud avSolicitud;
            try {
                avSolicitud = em.getReference(AvSolicitud.class, id);
                avSolicitud.getNumeroSolicitud();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avSolicitud with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AvAsignacion> avAsignacionListOrphanCheck = avSolicitud.getAvAsignacionList();
            for (AvAsignacion avAsignacionListOrphanCheckAvAsignacion : avAsignacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvSolicitud (" + avSolicitud + ") cannot be destroyed since the AvAsignacion " + avAsignacionListOrphanCheckAvAsignacion + " in its avAsignacionList field has a non-nullable solicitudNumeroSolicitud field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AvAsociado asociadoCif = avSolicitud.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif.getAvSolicitudList().remove(avSolicitud);
                asociadoCif = em.merge(asociadoCif);
            }
            AvInmueble inmuebleId = avSolicitud.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId.getAvSolicitudList().remove(avSolicitud);
                inmuebleId = em.merge(inmuebleId);
            }
            em.remove(avSolicitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvSolicitud> findAvSolicitudEntities() {
        return findAvSolicitudEntities(true, -1, -1);
    }

    public List<AvSolicitud> findAvSolicitudEntities(int maxResults, int firstResult) {
        return findAvSolicitudEntities(false, maxResults, firstResult);
    }

    private List<AvSolicitud> findAvSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvSolicitud.class));
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

    public AvSolicitud findAvSolicitud(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvSolicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvSolicitud> rt = cq.from(AvSolicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
