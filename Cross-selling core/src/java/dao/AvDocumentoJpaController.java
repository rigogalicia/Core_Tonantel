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
public class AvDocumentoJpaController implements Serializable {

    public AvDocumentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvDocumento avDocumento) {
        if (avDocumento.getAvInmuebleList() == null) {
            avDocumento.setAvInmuebleList(new ArrayList<AvInmueble>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AvInmueble> attachedAvInmuebleList = new ArrayList<AvInmueble>();
            for (AvInmueble avInmuebleListAvInmuebleToAttach : avDocumento.getAvInmuebleList()) {
                avInmuebleListAvInmuebleToAttach = em.getReference(avInmuebleListAvInmuebleToAttach.getClass(), avInmuebleListAvInmuebleToAttach.getId());
                attachedAvInmuebleList.add(avInmuebleListAvInmuebleToAttach);
            }
            avDocumento.setAvInmuebleList(attachedAvInmuebleList);
            em.persist(avDocumento);
            for (AvInmueble avInmuebleListAvInmueble : avDocumento.getAvInmuebleList()) {
                AvDocumento oldDocumentoIdOfAvInmuebleListAvInmueble = avInmuebleListAvInmueble.getDocumentoId();
                avInmuebleListAvInmueble.setDocumentoId(avDocumento);
                avInmuebleListAvInmueble = em.merge(avInmuebleListAvInmueble);
                if (oldDocumentoIdOfAvInmuebleListAvInmueble != null) {
                    oldDocumentoIdOfAvInmuebleListAvInmueble.getAvInmuebleList().remove(avInmuebleListAvInmueble);
                    oldDocumentoIdOfAvInmuebleListAvInmueble = em.merge(oldDocumentoIdOfAvInmuebleListAvInmueble);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvDocumento avDocumento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvDocumento persistentAvDocumento = em.find(AvDocumento.class, avDocumento.getId());
            List<AvInmueble> avInmuebleListOld = persistentAvDocumento.getAvInmuebleList();
            List<AvInmueble> avInmuebleListNew = avDocumento.getAvInmuebleList();
            List<String> illegalOrphanMessages = null;
            for (AvInmueble avInmuebleListOldAvInmueble : avInmuebleListOld) {
                if (!avInmuebleListNew.contains(avInmuebleListOldAvInmueble)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvInmueble " + avInmuebleListOldAvInmueble + " since its documentoId field is not nullable.");
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
            avDocumento.setAvInmuebleList(avInmuebleListNew);
            avDocumento = em.merge(avDocumento);
            for (AvInmueble avInmuebleListNewAvInmueble : avInmuebleListNew) {
                if (!avInmuebleListOld.contains(avInmuebleListNewAvInmueble)) {
                    AvDocumento oldDocumentoIdOfAvInmuebleListNewAvInmueble = avInmuebleListNewAvInmueble.getDocumentoId();
                    avInmuebleListNewAvInmueble.setDocumentoId(avDocumento);
                    avInmuebleListNewAvInmueble = em.merge(avInmuebleListNewAvInmueble);
                    if (oldDocumentoIdOfAvInmuebleListNewAvInmueble != null && !oldDocumentoIdOfAvInmuebleListNewAvInmueble.equals(avDocumento)) {
                        oldDocumentoIdOfAvInmuebleListNewAvInmueble.getAvInmuebleList().remove(avInmuebleListNewAvInmueble);
                        oldDocumentoIdOfAvInmuebleListNewAvInmueble = em.merge(oldDocumentoIdOfAvInmuebleListNewAvInmueble);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avDocumento.getId();
                if (findAvDocumento(id) == null) {
                    throw new NonexistentEntityException("The avDocumento with id " + id + " no longer exists.");
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
            AvDocumento avDocumento;
            try {
                avDocumento = em.getReference(AvDocumento.class, id);
                avDocumento.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avDocumento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AvInmueble> avInmuebleListOrphanCheck = avDocumento.getAvInmuebleList();
            for (AvInmueble avInmuebleListOrphanCheckAvInmueble : avInmuebleListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvDocumento (" + avDocumento + ") cannot be destroyed since the AvInmueble " + avInmuebleListOrphanCheckAvInmueble + " in its avInmuebleList field has a non-nullable documentoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(avDocumento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvDocumento> findAvDocumentoEntities() {
        return findAvDocumentoEntities(true, -1, -1);
    }

    public List<AvDocumento> findAvDocumentoEntities(int maxResults, int firstResult) {
        return findAvDocumentoEntities(false, maxResults, firstResult);
    }

    private List<AvDocumento> findAvDocumentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvDocumento.class));
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

    public AvDocumento findAvDocumento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvDocumento.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvDocumentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvDocumento> rt = cq.from(AvDocumento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
