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
public class RallyAsociadoJpaController implements Serializable {

    public RallyAsociadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallyAsociado rallyAsociado) {
        if (rallyAsociado.getRallyReferenciaList() == null) {
            rallyAsociado.setRallyReferenciaList(new ArrayList<RallyReferencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RallyReferencia> attachedRallyReferenciaList = new ArrayList<RallyReferencia>();
            for (RallyReferencia rallyReferenciaListRallyReferenciaToAttach : rallyAsociado.getRallyReferenciaList()) {
                rallyReferenciaListRallyReferenciaToAttach = em.getReference(rallyReferenciaListRallyReferenciaToAttach.getClass(), rallyReferenciaListRallyReferenciaToAttach.getIdreferencia());
                attachedRallyReferenciaList.add(rallyReferenciaListRallyReferenciaToAttach);
            }
            rallyAsociado.setRallyReferenciaList(attachedRallyReferenciaList);
            em.persist(rallyAsociado);
            for (RallyReferencia rallyReferenciaListRallyReferencia : rallyAsociado.getRallyReferenciaList()) {
                RallyAsociado oldRallyAsociadoIdasociadoOfRallyReferenciaListRallyReferencia = rallyReferenciaListRallyReferencia.getRallyAsociadoIdasociado();
                rallyReferenciaListRallyReferencia.setRallyAsociadoIdasociado(rallyAsociado);
                rallyReferenciaListRallyReferencia = em.merge(rallyReferenciaListRallyReferencia);
                if (oldRallyAsociadoIdasociadoOfRallyReferenciaListRallyReferencia != null) {
                    oldRallyAsociadoIdasociadoOfRallyReferenciaListRallyReferencia.getRallyReferenciaList().remove(rallyReferenciaListRallyReferencia);
                    oldRallyAsociadoIdasociadoOfRallyReferenciaListRallyReferencia = em.merge(oldRallyAsociadoIdasociadoOfRallyReferenciaListRallyReferencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallyAsociado rallyAsociado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyAsociado persistentRallyAsociado = em.find(RallyAsociado.class, rallyAsociado.getIdasociado());
            List<RallyReferencia> rallyReferenciaListOld = persistentRallyAsociado.getRallyReferenciaList();
            List<RallyReferencia> rallyReferenciaListNew = rallyAsociado.getRallyReferenciaList();
            List<String> illegalOrphanMessages = null;
            for (RallyReferencia rallyReferenciaListOldRallyReferencia : rallyReferenciaListOld) {
                if (!rallyReferenciaListNew.contains(rallyReferenciaListOldRallyReferencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RallyReferencia " + rallyReferenciaListOldRallyReferencia + " since its rallyAsociadoIdasociado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RallyReferencia> attachedRallyReferenciaListNew = new ArrayList<RallyReferencia>();
            for (RallyReferencia rallyReferenciaListNewRallyReferenciaToAttach : rallyReferenciaListNew) {
                rallyReferenciaListNewRallyReferenciaToAttach = em.getReference(rallyReferenciaListNewRallyReferenciaToAttach.getClass(), rallyReferenciaListNewRallyReferenciaToAttach.getIdreferencia());
                attachedRallyReferenciaListNew.add(rallyReferenciaListNewRallyReferenciaToAttach);
            }
            rallyReferenciaListNew = attachedRallyReferenciaListNew;
            rallyAsociado.setRallyReferenciaList(rallyReferenciaListNew);
            rallyAsociado = em.merge(rallyAsociado);
            for (RallyReferencia rallyReferenciaListNewRallyReferencia : rallyReferenciaListNew) {
                if (!rallyReferenciaListOld.contains(rallyReferenciaListNewRallyReferencia)) {
                    RallyAsociado oldRallyAsociadoIdasociadoOfRallyReferenciaListNewRallyReferencia = rallyReferenciaListNewRallyReferencia.getRallyAsociadoIdasociado();
                    rallyReferenciaListNewRallyReferencia.setRallyAsociadoIdasociado(rallyAsociado);
                    rallyReferenciaListNewRallyReferencia = em.merge(rallyReferenciaListNewRallyReferencia);
                    if (oldRallyAsociadoIdasociadoOfRallyReferenciaListNewRallyReferencia != null && !oldRallyAsociadoIdasociadoOfRallyReferenciaListNewRallyReferencia.equals(rallyAsociado)) {
                        oldRallyAsociadoIdasociadoOfRallyReferenciaListNewRallyReferencia.getRallyReferenciaList().remove(rallyReferenciaListNewRallyReferencia);
                        oldRallyAsociadoIdasociadoOfRallyReferenciaListNewRallyReferencia = em.merge(oldRallyAsociadoIdasociadoOfRallyReferenciaListNewRallyReferencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallyAsociado.getIdasociado();
                if (findRallyAsociado(id) == null) {
                    throw new NonexistentEntityException("The rallyAsociado with id " + id + " no longer exists.");
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
            RallyAsociado rallyAsociado;
            try {
                rallyAsociado = em.getReference(RallyAsociado.class, id);
                rallyAsociado.getIdasociado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallyAsociado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RallyReferencia> rallyReferenciaListOrphanCheck = rallyAsociado.getRallyReferenciaList();
            for (RallyReferencia rallyReferenciaListOrphanCheckRallyReferencia : rallyReferenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RallyAsociado (" + rallyAsociado + ") cannot be destroyed since the RallyReferencia " + rallyReferenciaListOrphanCheckRallyReferencia + " in its rallyReferenciaList field has a non-nullable rallyAsociadoIdasociado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rallyAsociado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallyAsociado> findRallyAsociadoEntities() {
        return findRallyAsociadoEntities(true, -1, -1);
    }

    public List<RallyAsociado> findRallyAsociadoEntities(int maxResults, int firstResult) {
        return findRallyAsociadoEntities(false, maxResults, firstResult);
    }

    private List<RallyAsociado> findRallyAsociadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallyAsociado.class));
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

    public RallyAsociado findRallyAsociado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallyAsociado.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallyAsociadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallyAsociado> rt = cq.from(RallyAsociado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
