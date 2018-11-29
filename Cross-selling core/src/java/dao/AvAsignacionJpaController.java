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
public class AvAsignacionJpaController implements Serializable {

    public AvAsignacionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvAsignacion avAsignacion) {
        if (avAsignacion.getAvAvaluoList() == null) {
            avAsignacion.setAvAvaluoList(new ArrayList<AvAvaluo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvSolicitud solicitudNumeroSolicitud = avAsignacion.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud = em.getReference(solicitudNumeroSolicitud.getClass(), solicitudNumeroSolicitud.getNumeroSolicitud());
                avAsignacion.setSolicitudNumeroSolicitud(solicitudNumeroSolicitud);
            }
            List<AvAvaluo> attachedAvAvaluoList = new ArrayList<AvAvaluo>();
            for (AvAvaluo avAvaluoListAvAvaluoToAttach : avAsignacion.getAvAvaluoList()) {
                avAvaluoListAvAvaluoToAttach = em.getReference(avAvaluoListAvAvaluoToAttach.getClass(), avAvaluoListAvAvaluoToAttach.getId());
                attachedAvAvaluoList.add(avAvaluoListAvAvaluoToAttach);
            }
            avAsignacion.setAvAvaluoList(attachedAvAvaluoList);
            em.persist(avAsignacion);
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getAvAsignacionList().add(avAsignacion);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            for (AvAvaluo avAvaluoListAvAvaluo : avAsignacion.getAvAvaluoList()) {
                AvAsignacion oldAsignacionIdOfAvAvaluoListAvAvaluo = avAvaluoListAvAvaluo.getAsignacionId();
                avAvaluoListAvAvaluo.setAsignacionId(avAsignacion);
                avAvaluoListAvAvaluo = em.merge(avAvaluoListAvAvaluo);
                if (oldAsignacionIdOfAvAvaluoListAvAvaluo != null) {
                    oldAsignacionIdOfAvAvaluoListAvAvaluo.getAvAvaluoList().remove(avAvaluoListAvAvaluo);
                    oldAsignacionIdOfAvAvaluoListAvAvaluo = em.merge(oldAsignacionIdOfAvAvaluoListAvAvaluo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvAsignacion avAsignacion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvAsignacion persistentAvAsignacion = em.find(AvAsignacion.class, avAsignacion.getId());
            AvSolicitud solicitudNumeroSolicitudOld = persistentAvAsignacion.getSolicitudNumeroSolicitud();
            AvSolicitud solicitudNumeroSolicitudNew = avAsignacion.getSolicitudNumeroSolicitud();
            List<AvAvaluo> avAvaluoListOld = persistentAvAsignacion.getAvAvaluoList();
            List<AvAvaluo> avAvaluoListNew = avAsignacion.getAvAvaluoList();
            List<String> illegalOrphanMessages = null;
            for (AvAvaluo avAvaluoListOldAvAvaluo : avAvaluoListOld) {
                if (!avAvaluoListNew.contains(avAvaluoListOldAvAvaluo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvAvaluo " + avAvaluoListOldAvAvaluo + " since its asignacionId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (solicitudNumeroSolicitudNew != null) {
                solicitudNumeroSolicitudNew = em.getReference(solicitudNumeroSolicitudNew.getClass(), solicitudNumeroSolicitudNew.getNumeroSolicitud());
                avAsignacion.setSolicitudNumeroSolicitud(solicitudNumeroSolicitudNew);
            }
            List<AvAvaluo> attachedAvAvaluoListNew = new ArrayList<AvAvaluo>();
            for (AvAvaluo avAvaluoListNewAvAvaluoToAttach : avAvaluoListNew) {
                avAvaluoListNewAvAvaluoToAttach = em.getReference(avAvaluoListNewAvAvaluoToAttach.getClass(), avAvaluoListNewAvAvaluoToAttach.getId());
                attachedAvAvaluoListNew.add(avAvaluoListNewAvAvaluoToAttach);
            }
            avAvaluoListNew = attachedAvAvaluoListNew;
            avAsignacion.setAvAvaluoList(avAvaluoListNew);
            avAsignacion = em.merge(avAsignacion);
            if (solicitudNumeroSolicitudOld != null && !solicitudNumeroSolicitudOld.equals(solicitudNumeroSolicitudNew)) {
                solicitudNumeroSolicitudOld.getAvAsignacionList().remove(avAsignacion);
                solicitudNumeroSolicitudOld = em.merge(solicitudNumeroSolicitudOld);
            }
            if (solicitudNumeroSolicitudNew != null && !solicitudNumeroSolicitudNew.equals(solicitudNumeroSolicitudOld)) {
                solicitudNumeroSolicitudNew.getAvAsignacionList().add(avAsignacion);
                solicitudNumeroSolicitudNew = em.merge(solicitudNumeroSolicitudNew);
            }
            for (AvAvaluo avAvaluoListNewAvAvaluo : avAvaluoListNew) {
                if (!avAvaluoListOld.contains(avAvaluoListNewAvAvaluo)) {
                    AvAsignacion oldAsignacionIdOfAvAvaluoListNewAvAvaluo = avAvaluoListNewAvAvaluo.getAsignacionId();
                    avAvaluoListNewAvAvaluo.setAsignacionId(avAsignacion);
                    avAvaluoListNewAvAvaluo = em.merge(avAvaluoListNewAvAvaluo);
                    if (oldAsignacionIdOfAvAvaluoListNewAvAvaluo != null && !oldAsignacionIdOfAvAvaluoListNewAvAvaluo.equals(avAsignacion)) {
                        oldAsignacionIdOfAvAvaluoListNewAvAvaluo.getAvAvaluoList().remove(avAvaluoListNewAvAvaluo);
                        oldAsignacionIdOfAvAvaluoListNewAvAvaluo = em.merge(oldAsignacionIdOfAvAvaluoListNewAvAvaluo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avAsignacion.getId();
                if (findAvAsignacion(id) == null) {
                    throw new NonexistentEntityException("The avAsignacion with id " + id + " no longer exists.");
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
            AvAsignacion avAsignacion;
            try {
                avAsignacion = em.getReference(AvAsignacion.class, id);
                avAsignacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avAsignacion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AvAvaluo> avAvaluoListOrphanCheck = avAsignacion.getAvAvaluoList();
            for (AvAvaluo avAvaluoListOrphanCheckAvAvaluo : avAvaluoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvAsignacion (" + avAsignacion + ") cannot be destroyed since the AvAvaluo " + avAvaluoListOrphanCheckAvAvaluo + " in its avAvaluoList field has a non-nullable asignacionId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AvSolicitud solicitudNumeroSolicitud = avAsignacion.getSolicitudNumeroSolicitud();
            if (solicitudNumeroSolicitud != null) {
                solicitudNumeroSolicitud.getAvAsignacionList().remove(avAsignacion);
                solicitudNumeroSolicitud = em.merge(solicitudNumeroSolicitud);
            }
            em.remove(avAsignacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvAsignacion> findAvAsignacionEntities() {
        return findAvAsignacionEntities(true, -1, -1);
    }

    public List<AvAsignacion> findAvAsignacionEntities(int maxResults, int firstResult) {
        return findAvAsignacionEntities(false, maxResults, firstResult);
    }

    private List<AvAsignacion> findAvAsignacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvAsignacion.class));
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

    public AvAsignacion findAvAsignacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvAsignacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvAsignacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvAsignacion> rt = cq.from(AvAsignacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
