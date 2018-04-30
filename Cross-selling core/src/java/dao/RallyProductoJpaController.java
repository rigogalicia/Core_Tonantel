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
public class RallyProductoJpaController implements Serializable {

    public RallyProductoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallyProducto rallyProducto) {
        if (rallyProducto.getRallyPreciocomisionList() == null) {
            rallyProducto.setRallyPreciocomisionList(new ArrayList<RallyPreciocomision>());
        }
        if (rallyProducto.getRallyReferenciaList() == null) {
            rallyProducto.setRallyReferenciaList(new ArrayList<RallyReferencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<RallyPreciocomision> attachedRallyPreciocomisionList = new ArrayList<RallyPreciocomision>();
            for (RallyPreciocomision rallyPreciocomisionListRallyPreciocomisionToAttach : rallyProducto.getRallyPreciocomisionList()) {
                rallyPreciocomisionListRallyPreciocomisionToAttach = em.getReference(rallyPreciocomisionListRallyPreciocomisionToAttach.getClass(), rallyPreciocomisionListRallyPreciocomisionToAttach.getIdpreciocomision());
                attachedRallyPreciocomisionList.add(rallyPreciocomisionListRallyPreciocomisionToAttach);
            }
            rallyProducto.setRallyPreciocomisionList(attachedRallyPreciocomisionList);
            List<RallyReferencia> attachedRallyReferenciaList = new ArrayList<RallyReferencia>();
            for (RallyReferencia rallyReferenciaListRallyReferenciaToAttach : rallyProducto.getRallyReferenciaList()) {
                rallyReferenciaListRallyReferenciaToAttach = em.getReference(rallyReferenciaListRallyReferenciaToAttach.getClass(), rallyReferenciaListRallyReferenciaToAttach.getIdreferencia());
                attachedRallyReferenciaList.add(rallyReferenciaListRallyReferenciaToAttach);
            }
            rallyProducto.setRallyReferenciaList(attachedRallyReferenciaList);
            em.persist(rallyProducto);
            for (RallyPreciocomision rallyPreciocomisionListRallyPreciocomision : rallyProducto.getRallyPreciocomisionList()) {
                RallyProducto oldRallyProductoIdproductoOfRallyPreciocomisionListRallyPreciocomision = rallyPreciocomisionListRallyPreciocomision.getRallyProductoIdproducto();
                rallyPreciocomisionListRallyPreciocomision.setRallyProductoIdproducto(rallyProducto);
                rallyPreciocomisionListRallyPreciocomision = em.merge(rallyPreciocomisionListRallyPreciocomision);
                if (oldRallyProductoIdproductoOfRallyPreciocomisionListRallyPreciocomision != null) {
                    oldRallyProductoIdproductoOfRallyPreciocomisionListRallyPreciocomision.getRallyPreciocomisionList().remove(rallyPreciocomisionListRallyPreciocomision);
                    oldRallyProductoIdproductoOfRallyPreciocomisionListRallyPreciocomision = em.merge(oldRallyProductoIdproductoOfRallyPreciocomisionListRallyPreciocomision);
                }
            }
            for (RallyReferencia rallyReferenciaListRallyReferencia : rallyProducto.getRallyReferenciaList()) {
                RallyProducto oldRallyProductoIdproductoOfRallyReferenciaListRallyReferencia = rallyReferenciaListRallyReferencia.getRallyProductoIdproducto();
                rallyReferenciaListRallyReferencia.setRallyProductoIdproducto(rallyProducto);
                rallyReferenciaListRallyReferencia = em.merge(rallyReferenciaListRallyReferencia);
                if (oldRallyProductoIdproductoOfRallyReferenciaListRallyReferencia != null) {
                    oldRallyProductoIdproductoOfRallyReferenciaListRallyReferencia.getRallyReferenciaList().remove(rallyReferenciaListRallyReferencia);
                    oldRallyProductoIdproductoOfRallyReferenciaListRallyReferencia = em.merge(oldRallyProductoIdproductoOfRallyReferenciaListRallyReferencia);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallyProducto rallyProducto) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyProducto persistentRallyProducto = em.find(RallyProducto.class, rallyProducto.getIdproducto());
            List<RallyPreciocomision> rallyPreciocomisionListOld = persistentRallyProducto.getRallyPreciocomisionList();
            List<RallyPreciocomision> rallyPreciocomisionListNew = rallyProducto.getRallyPreciocomisionList();
            List<RallyReferencia> rallyReferenciaListOld = persistentRallyProducto.getRallyReferenciaList();
            List<RallyReferencia> rallyReferenciaListNew = rallyProducto.getRallyReferenciaList();
            List<String> illegalOrphanMessages = null;
            for (RallyPreciocomision rallyPreciocomisionListOldRallyPreciocomision : rallyPreciocomisionListOld) {
                if (!rallyPreciocomisionListNew.contains(rallyPreciocomisionListOldRallyPreciocomision)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RallyPreciocomision " + rallyPreciocomisionListOldRallyPreciocomision + " since its rallyProductoIdproducto field is not nullable.");
                }
            }
            for (RallyReferencia rallyReferenciaListOldRallyReferencia : rallyReferenciaListOld) {
                if (!rallyReferenciaListNew.contains(rallyReferenciaListOldRallyReferencia)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RallyReferencia " + rallyReferenciaListOldRallyReferencia + " since its rallyProductoIdproducto field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<RallyPreciocomision> attachedRallyPreciocomisionListNew = new ArrayList<RallyPreciocomision>();
            for (RallyPreciocomision rallyPreciocomisionListNewRallyPreciocomisionToAttach : rallyPreciocomisionListNew) {
                rallyPreciocomisionListNewRallyPreciocomisionToAttach = em.getReference(rallyPreciocomisionListNewRallyPreciocomisionToAttach.getClass(), rallyPreciocomisionListNewRallyPreciocomisionToAttach.getIdpreciocomision());
                attachedRallyPreciocomisionListNew.add(rallyPreciocomisionListNewRallyPreciocomisionToAttach);
            }
            rallyPreciocomisionListNew = attachedRallyPreciocomisionListNew;
            rallyProducto.setRallyPreciocomisionList(rallyPreciocomisionListNew);
            List<RallyReferencia> attachedRallyReferenciaListNew = new ArrayList<RallyReferencia>();
            for (RallyReferencia rallyReferenciaListNewRallyReferenciaToAttach : rallyReferenciaListNew) {
                rallyReferenciaListNewRallyReferenciaToAttach = em.getReference(rallyReferenciaListNewRallyReferenciaToAttach.getClass(), rallyReferenciaListNewRallyReferenciaToAttach.getIdreferencia());
                attachedRallyReferenciaListNew.add(rallyReferenciaListNewRallyReferenciaToAttach);
            }
            rallyReferenciaListNew = attachedRallyReferenciaListNew;
            rallyProducto.setRallyReferenciaList(rallyReferenciaListNew);
            rallyProducto = em.merge(rallyProducto);
            for (RallyPreciocomision rallyPreciocomisionListNewRallyPreciocomision : rallyPreciocomisionListNew) {
                if (!rallyPreciocomisionListOld.contains(rallyPreciocomisionListNewRallyPreciocomision)) {
                    RallyProducto oldRallyProductoIdproductoOfRallyPreciocomisionListNewRallyPreciocomision = rallyPreciocomisionListNewRallyPreciocomision.getRallyProductoIdproducto();
                    rallyPreciocomisionListNewRallyPreciocomision.setRallyProductoIdproducto(rallyProducto);
                    rallyPreciocomisionListNewRallyPreciocomision = em.merge(rallyPreciocomisionListNewRallyPreciocomision);
                    if (oldRallyProductoIdproductoOfRallyPreciocomisionListNewRallyPreciocomision != null && !oldRallyProductoIdproductoOfRallyPreciocomisionListNewRallyPreciocomision.equals(rallyProducto)) {
                        oldRallyProductoIdproductoOfRallyPreciocomisionListNewRallyPreciocomision.getRallyPreciocomisionList().remove(rallyPreciocomisionListNewRallyPreciocomision);
                        oldRallyProductoIdproductoOfRallyPreciocomisionListNewRallyPreciocomision = em.merge(oldRallyProductoIdproductoOfRallyPreciocomisionListNewRallyPreciocomision);
                    }
                }
            }
            for (RallyReferencia rallyReferenciaListNewRallyReferencia : rallyReferenciaListNew) {
                if (!rallyReferenciaListOld.contains(rallyReferenciaListNewRallyReferencia)) {
                    RallyProducto oldRallyProductoIdproductoOfRallyReferenciaListNewRallyReferencia = rallyReferenciaListNewRallyReferencia.getRallyProductoIdproducto();
                    rallyReferenciaListNewRallyReferencia.setRallyProductoIdproducto(rallyProducto);
                    rallyReferenciaListNewRallyReferencia = em.merge(rallyReferenciaListNewRallyReferencia);
                    if (oldRallyProductoIdproductoOfRallyReferenciaListNewRallyReferencia != null && !oldRallyProductoIdproductoOfRallyReferenciaListNewRallyReferencia.equals(rallyProducto)) {
                        oldRallyProductoIdproductoOfRallyReferenciaListNewRallyReferencia.getRallyReferenciaList().remove(rallyReferenciaListNewRallyReferencia);
                        oldRallyProductoIdproductoOfRallyReferenciaListNewRallyReferencia = em.merge(oldRallyProductoIdproductoOfRallyReferenciaListNewRallyReferencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallyProducto.getIdproducto();
                if (findRallyProducto(id) == null) {
                    throw new NonexistentEntityException("The rallyProducto with id " + id + " no longer exists.");
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
            RallyProducto rallyProducto;
            try {
                rallyProducto = em.getReference(RallyProducto.class, id);
                rallyProducto.getIdproducto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallyProducto with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RallyPreciocomision> rallyPreciocomisionListOrphanCheck = rallyProducto.getRallyPreciocomisionList();
            for (RallyPreciocomision rallyPreciocomisionListOrphanCheckRallyPreciocomision : rallyPreciocomisionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RallyProducto (" + rallyProducto + ") cannot be destroyed since the RallyPreciocomision " + rallyPreciocomisionListOrphanCheckRallyPreciocomision + " in its rallyPreciocomisionList field has a non-nullable rallyProductoIdproducto field.");
            }
            List<RallyReferencia> rallyReferenciaListOrphanCheck = rallyProducto.getRallyReferenciaList();
            for (RallyReferencia rallyReferenciaListOrphanCheckRallyReferencia : rallyReferenciaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RallyProducto (" + rallyProducto + ") cannot be destroyed since the RallyReferencia " + rallyReferenciaListOrphanCheckRallyReferencia + " in its rallyReferenciaList field has a non-nullable rallyProductoIdproducto field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rallyProducto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallyProducto> findRallyProductoEntities() {
        return findRallyProductoEntities(true, -1, -1);
    }

    public List<RallyProducto> findRallyProductoEntities(int maxResults, int firstResult) {
        return findRallyProductoEntities(false, maxResults, firstResult);
    }

    private List<RallyProducto> findRallyProductoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallyProducto.class));
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

    public RallyProducto findRallyProducto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallyProducto.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallyProductoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallyProducto> rt = cq.from(RallyProducto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
