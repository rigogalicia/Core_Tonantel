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
 * @author r29galicia
 */
public class AvPuntocardinalJpaController implements Serializable {

    public AvPuntocardinalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvPuntocardinal avPuntocardinal) throws PreexistingEntityException, Exception {
        if (avPuntocardinal.getAvColindanteList() == null) {
            avPuntocardinal.setAvColindanteList(new ArrayList<AvColindante>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AvColindante> attachedAvColindanteList = new ArrayList<AvColindante>();
            for (AvColindante avColindanteListAvColindanteToAttach : avPuntocardinal.getAvColindanteList()) {
                avColindanteListAvColindanteToAttach = em.getReference(avColindanteListAvColindanteToAttach.getClass(), avColindanteListAvColindanteToAttach.getId());
                attachedAvColindanteList.add(avColindanteListAvColindanteToAttach);
            }
            avPuntocardinal.setAvColindanteList(attachedAvColindanteList);
            em.persist(avPuntocardinal);
            for (AvColindante avColindanteListAvColindante : avPuntocardinal.getAvColindanteList()) {
                AvPuntocardinal oldPuntocardinalIdOfAvColindanteListAvColindante = avColindanteListAvColindante.getPuntocardinalId();
                avColindanteListAvColindante.setPuntocardinalId(avPuntocardinal);
                avColindanteListAvColindante = em.merge(avColindanteListAvColindante);
                if (oldPuntocardinalIdOfAvColindanteListAvColindante != null) {
                    oldPuntocardinalIdOfAvColindanteListAvColindante.getAvColindanteList().remove(avColindanteListAvColindante);
                    oldPuntocardinalIdOfAvColindanteListAvColindante = em.merge(oldPuntocardinalIdOfAvColindanteListAvColindante);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAvPuntocardinal(avPuntocardinal.getId()) != null) {
                throw new PreexistingEntityException("AvPuntocardinal " + avPuntocardinal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvPuntocardinal avPuntocardinal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvPuntocardinal persistentAvPuntocardinal = em.find(AvPuntocardinal.class, avPuntocardinal.getId());
            List<AvColindante> avColindanteListOld = persistentAvPuntocardinal.getAvColindanteList();
            List<AvColindante> avColindanteListNew = avPuntocardinal.getAvColindanteList();
            List<String> illegalOrphanMessages = null;
            for (AvColindante avColindanteListOldAvColindante : avColindanteListOld) {
                if (!avColindanteListNew.contains(avColindanteListOldAvColindante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvColindante " + avColindanteListOldAvColindante + " since its puntocardinalId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<AvColindante> attachedAvColindanteListNew = new ArrayList<AvColindante>();
            for (AvColindante avColindanteListNewAvColindanteToAttach : avColindanteListNew) {
                avColindanteListNewAvColindanteToAttach = em.getReference(avColindanteListNewAvColindanteToAttach.getClass(), avColindanteListNewAvColindanteToAttach.getId());
                attachedAvColindanteListNew.add(avColindanteListNewAvColindanteToAttach);
            }
            avColindanteListNew = attachedAvColindanteListNew;
            avPuntocardinal.setAvColindanteList(avColindanteListNew);
            avPuntocardinal = em.merge(avPuntocardinal);
            for (AvColindante avColindanteListNewAvColindante : avColindanteListNew) {
                if (!avColindanteListOld.contains(avColindanteListNewAvColindante)) {
                    AvPuntocardinal oldPuntocardinalIdOfAvColindanteListNewAvColindante = avColindanteListNewAvColindante.getPuntocardinalId();
                    avColindanteListNewAvColindante.setPuntocardinalId(avPuntocardinal);
                    avColindanteListNewAvColindante = em.merge(avColindanteListNewAvColindante);
                    if (oldPuntocardinalIdOfAvColindanteListNewAvColindante != null && !oldPuntocardinalIdOfAvColindanteListNewAvColindante.equals(avPuntocardinal)) {
                        oldPuntocardinalIdOfAvColindanteListNewAvColindante.getAvColindanteList().remove(avColindanteListNewAvColindante);
                        oldPuntocardinalIdOfAvColindanteListNewAvColindante = em.merge(oldPuntocardinalIdOfAvColindanteListNewAvColindante);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = avPuntocardinal.getId();
                if (findAvPuntocardinal(id) == null) {
                    throw new NonexistentEntityException("The avPuntocardinal with id " + id + " no longer exists.");
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
            AvPuntocardinal avPuntocardinal;
            try {
                avPuntocardinal = em.getReference(AvPuntocardinal.class, id);
                avPuntocardinal.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avPuntocardinal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AvColindante> avColindanteListOrphanCheck = avPuntocardinal.getAvColindanteList();
            for (AvColindante avColindanteListOrphanCheckAvColindante : avColindanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvPuntocardinal (" + avPuntocardinal + ") cannot be destroyed since the AvColindante " + avColindanteListOrphanCheckAvColindante + " in its avColindanteList field has a non-nullable puntocardinalId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(avPuntocardinal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvPuntocardinal> findAvPuntocardinalEntities() {
        return findAvPuntocardinalEntities(true, -1, -1);
    }

    public List<AvPuntocardinal> findAvPuntocardinalEntities(int maxResults, int firstResult) {
        return findAvPuntocardinalEntities(false, maxResults, firstResult);
    }

    private List<AvPuntocardinal> findAvPuntocardinalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvPuntocardinal.class));
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

    public AvPuntocardinal findAvPuntocardinal(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvPuntocardinal.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvPuntocardinalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvPuntocardinal> rt = cq.from(AvPuntocardinal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
