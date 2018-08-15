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
public class AvPropietarioJpaController implements Serializable {

    public AvPropietarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvPropietario avPropietario) throws PreexistingEntityException, Exception {
        if (avPropietario.getAvInmuebleList() == null) {
            avPropietario.setAvInmuebleList(new ArrayList<AvInmueble>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AvInmueble> attachedAvInmuebleList = new ArrayList<AvInmueble>();
            for (AvInmueble avInmuebleListAvInmuebleToAttach : avPropietario.getAvInmuebleList()) {
                avInmuebleListAvInmuebleToAttach = em.getReference(avInmuebleListAvInmuebleToAttach.getClass(), avInmuebleListAvInmuebleToAttach.getId());
                attachedAvInmuebleList.add(avInmuebleListAvInmuebleToAttach);
            }
            avPropietario.setAvInmuebleList(attachedAvInmuebleList);
            em.persist(avPropietario);
            for (AvInmueble avInmuebleListAvInmueble : avPropietario.getAvInmuebleList()) {
                AvPropietario oldPropietarioDpiOfAvInmuebleListAvInmueble = avInmuebleListAvInmueble.getPropietarioDpi();
                avInmuebleListAvInmueble.setPropietarioDpi(avPropietario);
                avInmuebleListAvInmueble = em.merge(avInmuebleListAvInmueble);
                if (oldPropietarioDpiOfAvInmuebleListAvInmueble != null) {
                    oldPropietarioDpiOfAvInmuebleListAvInmueble.getAvInmuebleList().remove(avInmuebleListAvInmueble);
                    oldPropietarioDpiOfAvInmuebleListAvInmueble = em.merge(oldPropietarioDpiOfAvInmuebleListAvInmueble);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAvPropietario(avPropietario.getDpi()) != null) {
                throw new PreexistingEntityException("AvPropietario " + avPropietario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvPropietario avPropietario) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvPropietario persistentAvPropietario = em.find(AvPropietario.class, avPropietario.getDpi());
            List<AvInmueble> avInmuebleListOld = persistentAvPropietario.getAvInmuebleList();
            List<AvInmueble> avInmuebleListNew = avPropietario.getAvInmuebleList();
            List<String> illegalOrphanMessages = null;
            for (AvInmueble avInmuebleListOldAvInmueble : avInmuebleListOld) {
                if (!avInmuebleListNew.contains(avInmuebleListOldAvInmueble)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvInmueble " + avInmuebleListOldAvInmueble + " since its propietarioDpi field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<AvInmueble> attachedAvInmuebleListNew = new ArrayList<AvInmueble>();
            for (AvInmueble avInmuebleListNewAvInmuebleToAttach : avInmuebleListNew) {
                avInmuebleListNewAvInmuebleToAttach = em.getReference(avInmuebleListNewAvInmuebleToAttach.getClass(), avInmuebleListNewAvInmuebleToAttach.getId());
                attachedAvInmuebleListNew.add(avInmuebleListNewAvInmuebleToAttach);
            }
            avInmuebleListNew = attachedAvInmuebleListNew;
            avPropietario.setAvInmuebleList(avInmuebleListNew);
            avPropietario = em.merge(avPropietario);
            for (AvInmueble avInmuebleListNewAvInmueble : avInmuebleListNew) {
                if (!avInmuebleListOld.contains(avInmuebleListNewAvInmueble)) {
                    AvPropietario oldPropietarioDpiOfAvInmuebleListNewAvInmueble = avInmuebleListNewAvInmueble.getPropietarioDpi();
                    avInmuebleListNewAvInmueble.setPropietarioDpi(avPropietario);
                    avInmuebleListNewAvInmueble = em.merge(avInmuebleListNewAvInmueble);
                    if (oldPropietarioDpiOfAvInmuebleListNewAvInmueble != null && !oldPropietarioDpiOfAvInmuebleListNewAvInmueble.equals(avPropietario)) {
                        oldPropietarioDpiOfAvInmuebleListNewAvInmueble.getAvInmuebleList().remove(avInmuebleListNewAvInmueble);
                        oldPropietarioDpiOfAvInmuebleListNewAvInmueble = em.merge(oldPropietarioDpiOfAvInmuebleListNewAvInmueble);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = avPropietario.getDpi();
                if (findAvPropietario(id) == null) {
                    throw new NonexistentEntityException("The avPropietario with id " + id + " no longer exists.");
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
            AvPropietario avPropietario;
            try {
                avPropietario = em.getReference(AvPropietario.class, id);
                avPropietario.getDpi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avPropietario with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AvInmueble> avInmuebleListOrphanCheck = avPropietario.getAvInmuebleList();
            for (AvInmueble avInmuebleListOrphanCheckAvInmueble : avInmuebleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvPropietario (" + avPropietario + ") cannot be destroyed since the AvInmueble " + avInmuebleListOrphanCheckAvInmueble + " in its avInmuebleList field has a non-nullable propietarioDpi field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(avPropietario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvPropietario> findAvPropietarioEntities() {
        return findAvPropietarioEntities(true, -1, -1);
    }

    public List<AvPropietario> findAvPropietarioEntities(int maxResults, int firstResult) {
        return findAvPropietarioEntities(false, maxResults, firstResult);
    }

    private List<AvPropietario> findAvPropietarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvPropietario.class));
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

    public AvPropietario findAvPropietario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvPropietario.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvPropietarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvPropietario> rt = cq.from(AvPropietario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
