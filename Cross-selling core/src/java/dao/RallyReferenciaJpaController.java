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
public class RallyReferenciaJpaController implements Serializable {

    public RallyReferenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RallyReferencia rallyReferencia) {
        if (rallyReferencia.getRallyAsignacionList() == null) {
            rallyReferencia.setRallyAsignacionList(new ArrayList<RallyAsignacion>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyAsociado rallyAsociadoIdasociado = rallyReferencia.getRallyAsociadoIdasociado();
            if (rallyAsociadoIdasociado != null) {
                rallyAsociadoIdasociado = em.getReference(rallyAsociadoIdasociado.getClass(), rallyAsociadoIdasociado.getIdasociado());
                rallyReferencia.setRallyAsociadoIdasociado(rallyAsociadoIdasociado);
            }
            RallyProducto rallyProductoIdproducto = rallyReferencia.getRallyProductoIdproducto();
            if (rallyProductoIdproducto != null) {
                rallyProductoIdproducto = em.getReference(rallyProductoIdproducto.getClass(), rallyProductoIdproducto.getIdproducto());
                rallyReferencia.setRallyProductoIdproducto(rallyProductoIdproducto);
            }
            List<RallyAsignacion> attachedRallyAsignacionList = new ArrayList<RallyAsignacion>();
            for (RallyAsignacion rallyAsignacionListRallyAsignacionToAttach : rallyReferencia.getRallyAsignacionList()) {
                rallyAsignacionListRallyAsignacionToAttach = em.getReference(rallyAsignacionListRallyAsignacionToAttach.getClass(), rallyAsignacionListRallyAsignacionToAttach.getIdasignacion());
                attachedRallyAsignacionList.add(rallyAsignacionListRallyAsignacionToAttach);
            }
            rallyReferencia.setRallyAsignacionList(attachedRallyAsignacionList);
            em.persist(rallyReferencia);
            if (rallyAsociadoIdasociado != null) {
                rallyAsociadoIdasociado.getRallyReferenciaList().add(rallyReferencia);
                rallyAsociadoIdasociado = em.merge(rallyAsociadoIdasociado);
            }
            if (rallyProductoIdproducto != null) {
                rallyProductoIdproducto.getRallyReferenciaList().add(rallyReferencia);
                rallyProductoIdproducto = em.merge(rallyProductoIdproducto);
            }
            for (RallyAsignacion rallyAsignacionListRallyAsignacion : rallyReferencia.getRallyAsignacionList()) {
                RallyReferencia oldRallyReferenciaIdreferenciaOfRallyAsignacionListRallyAsignacion = rallyAsignacionListRallyAsignacion.getRallyReferenciaIdreferencia();
                rallyAsignacionListRallyAsignacion.setRallyReferenciaIdreferencia(rallyReferencia);
                rallyAsignacionListRallyAsignacion = em.merge(rallyAsignacionListRallyAsignacion);
                if (oldRallyReferenciaIdreferenciaOfRallyAsignacionListRallyAsignacion != null) {
                    oldRallyReferenciaIdreferenciaOfRallyAsignacionListRallyAsignacion.getRallyAsignacionList().remove(rallyAsignacionListRallyAsignacion);
                    oldRallyReferenciaIdreferenciaOfRallyAsignacionListRallyAsignacion = em.merge(oldRallyReferenciaIdreferenciaOfRallyAsignacionListRallyAsignacion);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RallyReferencia rallyReferencia) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RallyReferencia persistentRallyReferencia = em.find(RallyReferencia.class, rallyReferencia.getIdreferencia());
            RallyAsociado rallyAsociadoIdasociadoOld = persistentRallyReferencia.getRallyAsociadoIdasociado();
            RallyAsociado rallyAsociadoIdasociadoNew = rallyReferencia.getRallyAsociadoIdasociado();
            RallyProducto rallyProductoIdproductoOld = persistentRallyReferencia.getRallyProductoIdproducto();
            RallyProducto rallyProductoIdproductoNew = rallyReferencia.getRallyProductoIdproducto();
            List<RallyAsignacion> rallyAsignacionListOld = persistentRallyReferencia.getRallyAsignacionList();
            List<RallyAsignacion> rallyAsignacionListNew = rallyReferencia.getRallyAsignacionList();
            List<String> illegalOrphanMessages = null;
            for (RallyAsignacion rallyAsignacionListOldRallyAsignacion : rallyAsignacionListOld) {
                if (!rallyAsignacionListNew.contains(rallyAsignacionListOldRallyAsignacion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain RallyAsignacion " + rallyAsignacionListOldRallyAsignacion + " since its rallyReferenciaIdreferencia field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (rallyAsociadoIdasociadoNew != null) {
                rallyAsociadoIdasociadoNew = em.getReference(rallyAsociadoIdasociadoNew.getClass(), rallyAsociadoIdasociadoNew.getIdasociado());
                rallyReferencia.setRallyAsociadoIdasociado(rallyAsociadoIdasociadoNew);
            }
            if (rallyProductoIdproductoNew != null) {
                rallyProductoIdproductoNew = em.getReference(rallyProductoIdproductoNew.getClass(), rallyProductoIdproductoNew.getIdproducto());
                rallyReferencia.setRallyProductoIdproducto(rallyProductoIdproductoNew);
            }
            List<RallyAsignacion> attachedRallyAsignacionListNew = new ArrayList<RallyAsignacion>();
            for (RallyAsignacion rallyAsignacionListNewRallyAsignacionToAttach : rallyAsignacionListNew) {
                rallyAsignacionListNewRallyAsignacionToAttach = em.getReference(rallyAsignacionListNewRallyAsignacionToAttach.getClass(), rallyAsignacionListNewRallyAsignacionToAttach.getIdasignacion());
                attachedRallyAsignacionListNew.add(rallyAsignacionListNewRallyAsignacionToAttach);
            }
            rallyAsignacionListNew = attachedRallyAsignacionListNew;
            rallyReferencia.setRallyAsignacionList(rallyAsignacionListNew);
            rallyReferencia = em.merge(rallyReferencia);
            if (rallyAsociadoIdasociadoOld != null && !rallyAsociadoIdasociadoOld.equals(rallyAsociadoIdasociadoNew)) {
                rallyAsociadoIdasociadoOld.getRallyReferenciaList().remove(rallyReferencia);
                rallyAsociadoIdasociadoOld = em.merge(rallyAsociadoIdasociadoOld);
            }
            if (rallyAsociadoIdasociadoNew != null && !rallyAsociadoIdasociadoNew.equals(rallyAsociadoIdasociadoOld)) {
                rallyAsociadoIdasociadoNew.getRallyReferenciaList().add(rallyReferencia);
                rallyAsociadoIdasociadoNew = em.merge(rallyAsociadoIdasociadoNew);
            }
            if (rallyProductoIdproductoOld != null && !rallyProductoIdproductoOld.equals(rallyProductoIdproductoNew)) {
                rallyProductoIdproductoOld.getRallyReferenciaList().remove(rallyReferencia);
                rallyProductoIdproductoOld = em.merge(rallyProductoIdproductoOld);
            }
            if (rallyProductoIdproductoNew != null && !rallyProductoIdproductoNew.equals(rallyProductoIdproductoOld)) {
                rallyProductoIdproductoNew.getRallyReferenciaList().add(rallyReferencia);
                rallyProductoIdproductoNew = em.merge(rallyProductoIdproductoNew);
            }
            for (RallyAsignacion rallyAsignacionListNewRallyAsignacion : rallyAsignacionListNew) {
                if (!rallyAsignacionListOld.contains(rallyAsignacionListNewRallyAsignacion)) {
                    RallyReferencia oldRallyReferenciaIdreferenciaOfRallyAsignacionListNewRallyAsignacion = rallyAsignacionListNewRallyAsignacion.getRallyReferenciaIdreferencia();
                    rallyAsignacionListNewRallyAsignacion.setRallyReferenciaIdreferencia(rallyReferencia);
                    rallyAsignacionListNewRallyAsignacion = em.merge(rallyAsignacionListNewRallyAsignacion);
                    if (oldRallyReferenciaIdreferenciaOfRallyAsignacionListNewRallyAsignacion != null && !oldRallyReferenciaIdreferenciaOfRallyAsignacionListNewRallyAsignacion.equals(rallyReferencia)) {
                        oldRallyReferenciaIdreferenciaOfRallyAsignacionListNewRallyAsignacion.getRallyAsignacionList().remove(rallyAsignacionListNewRallyAsignacion);
                        oldRallyReferenciaIdreferenciaOfRallyAsignacionListNewRallyAsignacion = em.merge(oldRallyReferenciaIdreferenciaOfRallyAsignacionListNewRallyAsignacion);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = rallyReferencia.getIdreferencia();
                if (findRallyReferencia(id) == null) {
                    throw new NonexistentEntityException("The rallyReferencia with id " + id + " no longer exists.");
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
            RallyReferencia rallyReferencia;
            try {
                rallyReferencia = em.getReference(RallyReferencia.class, id);
                rallyReferencia.getIdreferencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rallyReferencia with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<RallyAsignacion> rallyAsignacionListOrphanCheck = rallyReferencia.getRallyAsignacionList();
            for (RallyAsignacion rallyAsignacionListOrphanCheckRallyAsignacion : rallyAsignacionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This RallyReferencia (" + rallyReferencia + ") cannot be destroyed since the RallyAsignacion " + rallyAsignacionListOrphanCheckRallyAsignacion + " in its rallyAsignacionList field has a non-nullable rallyReferenciaIdreferencia field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            RallyAsociado rallyAsociadoIdasociado = rallyReferencia.getRallyAsociadoIdasociado();
            if (rallyAsociadoIdasociado != null) {
                rallyAsociadoIdasociado.getRallyReferenciaList().remove(rallyReferencia);
                rallyAsociadoIdasociado = em.merge(rallyAsociadoIdasociado);
            }
            RallyProducto rallyProductoIdproducto = rallyReferencia.getRallyProductoIdproducto();
            if (rallyProductoIdproducto != null) {
                rallyProductoIdproducto.getRallyReferenciaList().remove(rallyReferencia);
                rallyProductoIdproducto = em.merge(rallyProductoIdproducto);
            }
            em.remove(rallyReferencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RallyReferencia> findRallyReferenciaEntities() {
        return findRallyReferenciaEntities(true, -1, -1);
    }

    public List<RallyReferencia> findRallyReferenciaEntities(int maxResults, int firstResult) {
        return findRallyReferenciaEntities(false, maxResults, firstResult);
    }

    private List<RallyReferencia> findRallyReferenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RallyReferencia.class));
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

    public RallyReferencia findRallyReferencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RallyReferencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getRallyReferenciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RallyReferencia> rt = cq.from(RallyReferencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
