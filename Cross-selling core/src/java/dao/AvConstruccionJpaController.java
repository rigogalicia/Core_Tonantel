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
public class AvConstruccionJpaController implements Serializable {

    public AvConstruccionJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvConstruccion avConstruccion) {
        if (avConstruccion.getAvConstruccionList() == null) {
            avConstruccion.setAvConstruccionList(new ArrayList<AvConstruccion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvConstruccion inmueble = avConstruccion.getInmueble();
            if (inmueble != null) {
                inmueble = em.getReference(inmueble.getClass(), inmueble.getId());
                avConstruccion.setInmueble(inmueble);
            }
            List<AvConstruccion> attachedAvConstruccionList = new ArrayList<AvConstruccion>();
            for (AvConstruccion avConstruccionListAvConstruccionToAttach : avConstruccion.getAvConstruccionList()) {
                avConstruccionListAvConstruccionToAttach = em.getReference(avConstruccionListAvConstruccionToAttach.getClass(), avConstruccionListAvConstruccionToAttach.getId());
                attachedAvConstruccionList.add(avConstruccionListAvConstruccionToAttach);
            }
            avConstruccion.setAvConstruccionList(attachedAvConstruccionList);
            em.persist(avConstruccion);
            if (inmueble != null) {
                inmueble.getAvConstruccionList().add(avConstruccion);
                inmueble = em.merge(inmueble);
            }
            for (AvConstruccion avConstruccionListAvConstruccion : avConstruccion.getAvConstruccionList()) {
                AvConstruccion oldInmuebleOfAvConstruccionListAvConstruccion = avConstruccionListAvConstruccion.getInmueble();
                avConstruccionListAvConstruccion.setInmueble(avConstruccion);
                avConstruccionListAvConstruccion = em.merge(avConstruccionListAvConstruccion);
                if (oldInmuebleOfAvConstruccionListAvConstruccion != null) {
                    oldInmuebleOfAvConstruccionListAvConstruccion.getAvConstruccionList().remove(avConstruccionListAvConstruccion);
                    oldInmuebleOfAvConstruccionListAvConstruccion = em.merge(oldInmuebleOfAvConstruccionListAvConstruccion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvConstruccion avConstruccion) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvConstruccion persistentAvConstruccion = em.find(AvConstruccion.class, avConstruccion.getId());
            AvConstruccion inmuebleOld = persistentAvConstruccion.getInmueble();
            AvConstruccion inmuebleNew = avConstruccion.getInmueble();
            List<AvConstruccion> avConstruccionListOld = persistentAvConstruccion.getAvConstruccionList();
            List<AvConstruccion> avConstruccionListNew = avConstruccion.getAvConstruccionList();
            List<String> illegalOrphanMessages = null;
            for (AvConstruccion avConstruccionListOldAvConstruccion : avConstruccionListOld) {
                if (!avConstruccionListNew.contains(avConstruccionListOldAvConstruccion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvConstruccion " + avConstruccionListOldAvConstruccion + " since its inmueble field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (inmuebleNew != null) {
                inmuebleNew = em.getReference(inmuebleNew.getClass(), inmuebleNew.getId());
                avConstruccion.setInmueble(inmuebleNew);
            }
            List<AvConstruccion> attachedAvConstruccionListNew = new ArrayList<AvConstruccion>();
            for (AvConstruccion avConstruccionListNewAvConstruccionToAttach : avConstruccionListNew) {
                avConstruccionListNewAvConstruccionToAttach = em.getReference(avConstruccionListNewAvConstruccionToAttach.getClass(), avConstruccionListNewAvConstruccionToAttach.getId());
                attachedAvConstruccionListNew.add(avConstruccionListNewAvConstruccionToAttach);
            }
            avConstruccionListNew = attachedAvConstruccionListNew;
            avConstruccion.setAvConstruccionList(avConstruccionListNew);
            avConstruccion = em.merge(avConstruccion);
            if (inmuebleOld != null && !inmuebleOld.equals(inmuebleNew)) {
                inmuebleOld.getAvConstruccionList().remove(avConstruccion);
                inmuebleOld = em.merge(inmuebleOld);
            }
            if (inmuebleNew != null && !inmuebleNew.equals(inmuebleOld)) {
                inmuebleNew.getAvConstruccionList().add(avConstruccion);
                inmuebleNew = em.merge(inmuebleNew);
            }
            for (AvConstruccion avConstruccionListNewAvConstruccion : avConstruccionListNew) {
                if (!avConstruccionListOld.contains(avConstruccionListNewAvConstruccion)) {
                    AvConstruccion oldInmuebleOfAvConstruccionListNewAvConstruccion = avConstruccionListNewAvConstruccion.getInmueble();
                    avConstruccionListNewAvConstruccion.setInmueble(avConstruccion);
                    avConstruccionListNewAvConstruccion = em.merge(avConstruccionListNewAvConstruccion);
                    if (oldInmuebleOfAvConstruccionListNewAvConstruccion != null && !oldInmuebleOfAvConstruccionListNewAvConstruccion.equals(avConstruccion)) {
                        oldInmuebleOfAvConstruccionListNewAvConstruccion.getAvConstruccionList().remove(avConstruccionListNewAvConstruccion);
                        oldInmuebleOfAvConstruccionListNewAvConstruccion = em.merge(oldInmuebleOfAvConstruccionListNewAvConstruccion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avConstruccion.getId();
                if (findAvConstruccion(id) == null) {
                    throw new NonexistentEntityException("The avConstruccion with id " + id + " no longer exists.");
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
            AvConstruccion avConstruccion;
            try {
                avConstruccion = em.getReference(AvConstruccion.class, id);
                avConstruccion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avConstruccion with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AvConstruccion> avConstruccionListOrphanCheck = avConstruccion.getAvConstruccionList();
            for (AvConstruccion avConstruccionListOrphanCheckAvConstruccion : avConstruccionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvConstruccion (" + avConstruccion + ") cannot be destroyed since the AvConstruccion " + avConstruccionListOrphanCheckAvConstruccion + " in its avConstruccionList field has a non-nullable inmueble field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AvConstruccion inmueble = avConstruccion.getInmueble();
            if (inmueble != null) {
                inmueble.getAvConstruccionList().remove(avConstruccion);
                inmueble = em.merge(inmueble);
            }
            em.remove(avConstruccion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvConstruccion> findAvConstruccionEntities() {
        return findAvConstruccionEntities(true, -1, -1);
    }

    public List<AvConstruccion> findAvConstruccionEntities(int maxResults, int firstResult) {
        return findAvConstruccionEntities(false, maxResults, firstResult);
    }

    private List<AvConstruccion> findAvConstruccionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvConstruccion.class));
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

    public AvConstruccion findAvConstruccion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvConstruccion.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvConstruccionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvConstruccion> rt = cq.from(AvConstruccion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
