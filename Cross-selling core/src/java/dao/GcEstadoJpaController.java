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
public class GcEstadoJpaController implements Serializable {

    public GcEstadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcEstado gcEstado) throws PreexistingEntityException, Exception {
        if (gcEstado.getGcGestionList() == null) {
            gcEstado.setGcGestionList(new ArrayList<GcGestion>());
        }
        if (gcEstado.getGcSolicitudList() == null) {
            gcEstado.setGcSolicitudList(new ArrayList<GcSolicitud>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<GcGestion> attachedGcGestionList = new ArrayList<GcGestion>();
            for (GcGestion gcGestionListGcGestionToAttach : gcEstado.getGcGestionList()) {
                gcGestionListGcGestionToAttach = em.getReference(gcGestionListGcGestionToAttach.getClass(), gcGestionListGcGestionToAttach.getId());
                attachedGcGestionList.add(gcGestionListGcGestionToAttach);
            }
            gcEstado.setGcGestionList(attachedGcGestionList);
            List<GcSolicitud> attachedGcSolicitudList = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListGcSolicitudToAttach : gcEstado.getGcSolicitudList()) {
                gcSolicitudListGcSolicitudToAttach = em.getReference(gcSolicitudListGcSolicitudToAttach.getClass(), gcSolicitudListGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudList.add(gcSolicitudListGcSolicitudToAttach);
            }
            gcEstado.setGcSolicitudList(attachedGcSolicitudList);
            em.persist(gcEstado);
            for (GcGestion gcGestionListGcGestion : gcEstado.getGcGestionList()) {
                GcEstado oldEstadoIdOfGcGestionListGcGestion = gcGestionListGcGestion.getEstadoId();
                gcGestionListGcGestion.setEstadoId(gcEstado);
                gcGestionListGcGestion = em.merge(gcGestionListGcGestion);
                if (oldEstadoIdOfGcGestionListGcGestion != null) {
                    oldEstadoIdOfGcGestionListGcGestion.getGcGestionList().remove(gcGestionListGcGestion);
                    oldEstadoIdOfGcGestionListGcGestion = em.merge(oldEstadoIdOfGcGestionListGcGestion);
                }
            }
            for (GcSolicitud gcSolicitudListGcSolicitud : gcEstado.getGcSolicitudList()) {
                GcEstado oldEstadoIdOfGcSolicitudListGcSolicitud = gcSolicitudListGcSolicitud.getEstadoId();
                gcSolicitudListGcSolicitud.setEstadoId(gcEstado);
                gcSolicitudListGcSolicitud = em.merge(gcSolicitudListGcSolicitud);
                if (oldEstadoIdOfGcSolicitudListGcSolicitud != null) {
                    oldEstadoIdOfGcSolicitudListGcSolicitud.getGcSolicitudList().remove(gcSolicitudListGcSolicitud);
                    oldEstadoIdOfGcSolicitudListGcSolicitud = em.merge(oldEstadoIdOfGcSolicitudListGcSolicitud);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGcEstado(gcEstado.getId()) != null) {
                throw new PreexistingEntityException("GcEstado " + gcEstado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcEstado gcEstado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcEstado persistentGcEstado = em.find(GcEstado.class, gcEstado.getId());
            List<GcGestion> gcGestionListOld = persistentGcEstado.getGcGestionList();
            List<GcGestion> gcGestionListNew = gcEstado.getGcGestionList();
            List<GcSolicitud> gcSolicitudListOld = persistentGcEstado.getGcSolicitudList();
            List<GcSolicitud> gcSolicitudListNew = gcEstado.getGcSolicitudList();
            List<String> illegalOrphanMessages = null;
            for (GcSolicitud gcSolicitudListOldGcSolicitud : gcSolicitudListOld) {
                if (!gcSolicitudListNew.contains(gcSolicitudListOldGcSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcSolicitud " + gcSolicitudListOldGcSolicitud + " since its estadoId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GcGestion> attachedGcGestionListNew = new ArrayList<GcGestion>();
            for (GcGestion gcGestionListNewGcGestionToAttach : gcGestionListNew) {
                gcGestionListNewGcGestionToAttach = em.getReference(gcGestionListNewGcGestionToAttach.getClass(), gcGestionListNewGcGestionToAttach.getId());
                attachedGcGestionListNew.add(gcGestionListNewGcGestionToAttach);
            }
            gcGestionListNew = attachedGcGestionListNew;
            gcEstado.setGcGestionList(gcGestionListNew);
            List<GcSolicitud> attachedGcSolicitudListNew = new ArrayList<GcSolicitud>();
            for (GcSolicitud gcSolicitudListNewGcSolicitudToAttach : gcSolicitudListNew) {
                gcSolicitudListNewGcSolicitudToAttach = em.getReference(gcSolicitudListNewGcSolicitudToAttach.getClass(), gcSolicitudListNewGcSolicitudToAttach.getNumeroSolicitud());
                attachedGcSolicitudListNew.add(gcSolicitudListNewGcSolicitudToAttach);
            }
            gcSolicitudListNew = attachedGcSolicitudListNew;
            gcEstado.setGcSolicitudList(gcSolicitudListNew);
            gcEstado = em.merge(gcEstado);
            for (GcGestion gcGestionListOldGcGestion : gcGestionListOld) {
                if (!gcGestionListNew.contains(gcGestionListOldGcGestion)) {
                    gcGestionListOldGcGestion.setEstadoId(null);
                    gcGestionListOldGcGestion = em.merge(gcGestionListOldGcGestion);
                }
            }
            for (GcGestion gcGestionListNewGcGestion : gcGestionListNew) {
                if (!gcGestionListOld.contains(gcGestionListNewGcGestion)) {
                    GcEstado oldEstadoIdOfGcGestionListNewGcGestion = gcGestionListNewGcGestion.getEstadoId();
                    gcGestionListNewGcGestion.setEstadoId(gcEstado);
                    gcGestionListNewGcGestion = em.merge(gcGestionListNewGcGestion);
                    if (oldEstadoIdOfGcGestionListNewGcGestion != null && !oldEstadoIdOfGcGestionListNewGcGestion.equals(gcEstado)) {
                        oldEstadoIdOfGcGestionListNewGcGestion.getGcGestionList().remove(gcGestionListNewGcGestion);
                        oldEstadoIdOfGcGestionListNewGcGestion = em.merge(oldEstadoIdOfGcGestionListNewGcGestion);
                    }
                }
            }
            for (GcSolicitud gcSolicitudListNewGcSolicitud : gcSolicitudListNew) {
                if (!gcSolicitudListOld.contains(gcSolicitudListNewGcSolicitud)) {
                    GcEstado oldEstadoIdOfGcSolicitudListNewGcSolicitud = gcSolicitudListNewGcSolicitud.getEstadoId();
                    gcSolicitudListNewGcSolicitud.setEstadoId(gcEstado);
                    gcSolicitudListNewGcSolicitud = em.merge(gcSolicitudListNewGcSolicitud);
                    if (oldEstadoIdOfGcSolicitudListNewGcSolicitud != null && !oldEstadoIdOfGcSolicitudListNewGcSolicitud.equals(gcEstado)) {
                        oldEstadoIdOfGcSolicitudListNewGcSolicitud.getGcSolicitudList().remove(gcSolicitudListNewGcSolicitud);
                        oldEstadoIdOfGcSolicitudListNewGcSolicitud = em.merge(oldEstadoIdOfGcSolicitudListNewGcSolicitud);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = gcEstado.getId();
                if (findGcEstado(id) == null) {
                    throw new NonexistentEntityException("The gcEstado with id " + id + " no longer exists.");
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
            GcEstado gcEstado;
            try {
                gcEstado = em.getReference(GcEstado.class, id);
                gcEstado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcEstado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcSolicitud> gcSolicitudListOrphanCheck = gcEstado.getGcSolicitudList();
            for (GcSolicitud gcSolicitudListOrphanCheckGcSolicitud : gcSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcEstado (" + gcEstado + ") cannot be destroyed since the GcSolicitud " + gcSolicitudListOrphanCheckGcSolicitud + " in its gcSolicitudList field has a non-nullable estadoId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<GcGestion> gcGestionList = gcEstado.getGcGestionList();
            for (GcGestion gcGestionListGcGestion : gcGestionList) {
                gcGestionListGcGestion.setEstadoId(null);
                gcGestionListGcGestion = em.merge(gcGestionListGcGestion);
            }
            em.remove(gcEstado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcEstado> findGcEstadoEntities() {
        return findGcEstadoEntities(true, -1, -1);
    }

    public List<GcEstado> findGcEstadoEntities(int maxResults, int firstResult) {
        return findGcEstadoEntities(false, maxResults, firstResult);
    }

    private List<GcEstado> findGcEstadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcEstado.class));
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

    public GcEstado findGcEstado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcEstado.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcEstadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcEstado> rt = cq.from(GcEstado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
