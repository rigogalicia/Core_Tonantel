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
public class AvAvaluoJpaController implements Serializable {

    public AvAvaluoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvAvaluo avAvaluo) {
        if (avAvaluo.getAvAnexosList() == null) {
            avAvaluo.setAvAnexosList(new ArrayList<AvAnexos>());
        }
        if (avAvaluo.getAvDetalleList() == null) {
            avAvaluo.setAvDetalleList(new ArrayList<AvDetalle>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvAsignacion asignacionId = avAvaluo.getAsignacionId();
            if (asignacionId != null) {
                asignacionId = em.getReference(asignacionId.getClass(), asignacionId.getId());
                avAvaluo.setAsignacionId(asignacionId);
            }
            AvInmueble inmuebleId = avAvaluo.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId = em.getReference(inmuebleId.getClass(), inmuebleId.getId());
                avAvaluo.setInmuebleId(inmuebleId);
            }
            List<AvAnexos> attachedAvAnexosList = new ArrayList<AvAnexos>();
            for (AvAnexos avAnexosListAvAnexosToAttach : avAvaluo.getAvAnexosList()) {
                avAnexosListAvAnexosToAttach = em.getReference(avAnexosListAvAnexosToAttach.getClass(), avAnexosListAvAnexosToAttach.getId());
                attachedAvAnexosList.add(avAnexosListAvAnexosToAttach);
            }
            avAvaluo.setAvAnexosList(attachedAvAnexosList);
            List<AvDetalle> attachedAvDetalleList = new ArrayList<AvDetalle>();
            for (AvDetalle avDetalleListAvDetalleToAttach : avAvaluo.getAvDetalleList()) {
                avDetalleListAvDetalleToAttach = em.getReference(avDetalleListAvDetalleToAttach.getClass(), avDetalleListAvDetalleToAttach.getId());
                attachedAvDetalleList.add(avDetalleListAvDetalleToAttach);
            }
            avAvaluo.setAvDetalleList(attachedAvDetalleList);
            em.persist(avAvaluo);
            if (asignacionId != null) {
                asignacionId.getAvAvaluoList().add(avAvaluo);
                asignacionId = em.merge(asignacionId);
            }
            if (inmuebleId != null) {
                inmuebleId.getAvAvaluoList().add(avAvaluo);
                inmuebleId = em.merge(inmuebleId);
            }
            for (AvAnexos avAnexosListAvAnexos : avAvaluo.getAvAnexosList()) {
                AvAvaluo oldAvaluoIdOfAvAnexosListAvAnexos = avAnexosListAvAnexos.getAvaluoId();
                avAnexosListAvAnexos.setAvaluoId(avAvaluo);
                avAnexosListAvAnexos = em.merge(avAnexosListAvAnexos);
                if (oldAvaluoIdOfAvAnexosListAvAnexos != null) {
                    oldAvaluoIdOfAvAnexosListAvAnexos.getAvAnexosList().remove(avAnexosListAvAnexos);
                    oldAvaluoIdOfAvAnexosListAvAnexos = em.merge(oldAvaluoIdOfAvAnexosListAvAnexos);
                }
            }
            for (AvDetalle avDetalleListAvDetalle : avAvaluo.getAvDetalleList()) {
                AvAvaluo oldAvaluoIdOfAvDetalleListAvDetalle = avDetalleListAvDetalle.getAvaluoId();
                avDetalleListAvDetalle.setAvaluoId(avAvaluo);
                avDetalleListAvDetalle = em.merge(avDetalleListAvDetalle);
                if (oldAvaluoIdOfAvDetalleListAvDetalle != null) {
                    oldAvaluoIdOfAvDetalleListAvDetalle.getAvDetalleList().remove(avDetalleListAvDetalle);
                    oldAvaluoIdOfAvDetalleListAvDetalle = em.merge(oldAvaluoIdOfAvDetalleListAvDetalle);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvAvaluo avAvaluo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvAvaluo persistentAvAvaluo = em.find(AvAvaluo.class, avAvaluo.getId());
            AvAsignacion asignacionIdOld = persistentAvAvaluo.getAsignacionId();
            AvAsignacion asignacionIdNew = avAvaluo.getAsignacionId();
            AvInmueble inmuebleIdOld = persistentAvAvaluo.getInmuebleId();
            AvInmueble inmuebleIdNew = avAvaluo.getInmuebleId();
            List<AvAnexos> avAnexosListOld = persistentAvAvaluo.getAvAnexosList();
            List<AvAnexos> avAnexosListNew = avAvaluo.getAvAnexosList();
            List<AvDetalle> avDetalleListOld = persistentAvAvaluo.getAvDetalleList();
            List<AvDetalle> avDetalleListNew = avAvaluo.getAvDetalleList();
            List<String> illegalOrphanMessages = null;
            for (AvAnexos avAnexosListOldAvAnexos : avAnexosListOld) {
                if (!avAnexosListNew.contains(avAnexosListOldAvAnexos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvAnexos " + avAnexosListOldAvAnexos + " since its avaluoId field is not nullable.");
                }
            }
            for (AvDetalle avDetalleListOldAvDetalle : avDetalleListOld) {
                if (!avDetalleListNew.contains(avDetalleListOldAvDetalle)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvDetalle " + avDetalleListOldAvDetalle + " since its avaluoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (asignacionIdNew != null) {
                asignacionIdNew = em.getReference(asignacionIdNew.getClass(), asignacionIdNew.getId());
                avAvaluo.setAsignacionId(asignacionIdNew);
            }
            if (inmuebleIdNew != null) {
                inmuebleIdNew = em.getReference(inmuebleIdNew.getClass(), inmuebleIdNew.getId());
                avAvaluo.setInmuebleId(inmuebleIdNew);
            }
            List<AvAnexos> attachedAvAnexosListNew = new ArrayList<AvAnexos>();
            for (AvAnexos avAnexosListNewAvAnexosToAttach : avAnexosListNew) {
                avAnexosListNewAvAnexosToAttach = em.getReference(avAnexosListNewAvAnexosToAttach.getClass(), avAnexosListNewAvAnexosToAttach.getId());
                attachedAvAnexosListNew.add(avAnexosListNewAvAnexosToAttach);
            }
            avAnexosListNew = attachedAvAnexosListNew;
            avAvaluo.setAvAnexosList(avAnexosListNew);
            List<AvDetalle> attachedAvDetalleListNew = new ArrayList<AvDetalle>();
            for (AvDetalle avDetalleListNewAvDetalleToAttach : avDetalleListNew) {
                avDetalleListNewAvDetalleToAttach = em.getReference(avDetalleListNewAvDetalleToAttach.getClass(), avDetalleListNewAvDetalleToAttach.getId());
                attachedAvDetalleListNew.add(avDetalleListNewAvDetalleToAttach);
            }
            avDetalleListNew = attachedAvDetalleListNew;
            avAvaluo.setAvDetalleList(avDetalleListNew);
            avAvaluo = em.merge(avAvaluo);
            if (asignacionIdOld != null && !asignacionIdOld.equals(asignacionIdNew)) {
                asignacionIdOld.getAvAvaluoList().remove(avAvaluo);
                asignacionIdOld = em.merge(asignacionIdOld);
            }
            if (asignacionIdNew != null && !asignacionIdNew.equals(asignacionIdOld)) {
                asignacionIdNew.getAvAvaluoList().add(avAvaluo);
                asignacionIdNew = em.merge(asignacionIdNew);
            }
            if (inmuebleIdOld != null && !inmuebleIdOld.equals(inmuebleIdNew)) {
                inmuebleIdOld.getAvAvaluoList().remove(avAvaluo);
                inmuebleIdOld = em.merge(inmuebleIdOld);
            }
            if (inmuebleIdNew != null && !inmuebleIdNew.equals(inmuebleIdOld)) {
                inmuebleIdNew.getAvAvaluoList().add(avAvaluo);
                inmuebleIdNew = em.merge(inmuebleIdNew);
            }
            for (AvAnexos avAnexosListNewAvAnexos : avAnexosListNew) {
                if (!avAnexosListOld.contains(avAnexosListNewAvAnexos)) {
                    AvAvaluo oldAvaluoIdOfAvAnexosListNewAvAnexos = avAnexosListNewAvAnexos.getAvaluoId();
                    avAnexosListNewAvAnexos.setAvaluoId(avAvaluo);
                    avAnexosListNewAvAnexos = em.merge(avAnexosListNewAvAnexos);
                    if (oldAvaluoIdOfAvAnexosListNewAvAnexos != null && !oldAvaluoIdOfAvAnexosListNewAvAnexos.equals(avAvaluo)) {
                        oldAvaluoIdOfAvAnexosListNewAvAnexos.getAvAnexosList().remove(avAnexosListNewAvAnexos);
                        oldAvaluoIdOfAvAnexosListNewAvAnexos = em.merge(oldAvaluoIdOfAvAnexosListNewAvAnexos);
                    }
                }
            }
            for (AvDetalle avDetalleListNewAvDetalle : avDetalleListNew) {
                if (!avDetalleListOld.contains(avDetalleListNewAvDetalle)) {
                    AvAvaluo oldAvaluoIdOfAvDetalleListNewAvDetalle = avDetalleListNewAvDetalle.getAvaluoId();
                    avDetalleListNewAvDetalle.setAvaluoId(avAvaluo);
                    avDetalleListNewAvDetalle = em.merge(avDetalleListNewAvDetalle);
                    if (oldAvaluoIdOfAvDetalleListNewAvDetalle != null && !oldAvaluoIdOfAvDetalleListNewAvDetalle.equals(avAvaluo)) {
                        oldAvaluoIdOfAvDetalleListNewAvDetalle.getAvDetalleList().remove(avDetalleListNewAvDetalle);
                        oldAvaluoIdOfAvDetalleListNewAvDetalle = em.merge(oldAvaluoIdOfAvDetalleListNewAvDetalle);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avAvaluo.getId();
                if (findAvAvaluo(id) == null) {
                    throw new NonexistentEntityException("The avAvaluo with id " + id + " no longer exists.");
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
            AvAvaluo avAvaluo;
            try {
                avAvaluo = em.getReference(AvAvaluo.class, id);
                avAvaluo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avAvaluo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AvAnexos> avAnexosListOrphanCheck = avAvaluo.getAvAnexosList();
            for (AvAnexos avAnexosListOrphanCheckAvAnexos : avAnexosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvAvaluo (" + avAvaluo + ") cannot be destroyed since the AvAnexos " + avAnexosListOrphanCheckAvAnexos + " in its avAnexosList field has a non-nullable avaluoId field.");
            }
            List<AvDetalle> avDetalleListOrphanCheck = avAvaluo.getAvDetalleList();
            for (AvDetalle avDetalleListOrphanCheckAvDetalle : avDetalleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvAvaluo (" + avAvaluo + ") cannot be destroyed since the AvDetalle " + avDetalleListOrphanCheckAvDetalle + " in its avDetalleList field has a non-nullable avaluoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AvAsignacion asignacionId = avAvaluo.getAsignacionId();
            if (asignacionId != null) {
                asignacionId.getAvAvaluoList().remove(avAvaluo);
                asignacionId = em.merge(asignacionId);
            }
            AvInmueble inmuebleId = avAvaluo.getInmuebleId();
            if (inmuebleId != null) {
                inmuebleId.getAvAvaluoList().remove(avAvaluo);
                inmuebleId = em.merge(inmuebleId);
            }
            em.remove(avAvaluo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvAvaluo> findAvAvaluoEntities() {
        return findAvAvaluoEntities(true, -1, -1);
    }

    public List<AvAvaluo> findAvAvaluoEntities(int maxResults, int firstResult) {
        return findAvAvaluoEntities(false, maxResults, firstResult);
    }

    private List<AvAvaluo> findAvAvaluoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvAvaluo.class));
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

    public AvAvaluo findAvAvaluo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvAvaluo.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvAvaluoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvAvaluo> rt = cq.from(AvAvaluo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
