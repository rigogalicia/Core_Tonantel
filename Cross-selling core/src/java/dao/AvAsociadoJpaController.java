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
public class AvAsociadoJpaController implements Serializable {

    public AvAsociadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvAsociado avAsociado) throws PreexistingEntityException, Exception {
        if (avAsociado.getAvSolicitudList() == null) {
            avAsociado.setAvSolicitudList(new ArrayList<AvSolicitud>());
        }
        if (avAsociado.getAvTelefonoList() == null) {
            avAsociado.setAvTelefonoList(new ArrayList<AvTelefono>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<AvSolicitud> attachedAvSolicitudList = new ArrayList<AvSolicitud>();
            for (AvSolicitud avSolicitudListAvSolicitudToAttach : avAsociado.getAvSolicitudList()) {
                avSolicitudListAvSolicitudToAttach = em.getReference(avSolicitudListAvSolicitudToAttach.getClass(), avSolicitudListAvSolicitudToAttach.getNumeroSolicitud());
                attachedAvSolicitudList.add(avSolicitudListAvSolicitudToAttach);
            }
            avAsociado.setAvSolicitudList(attachedAvSolicitudList);
            List<AvTelefono> attachedAvTelefonoList = new ArrayList<AvTelefono>();
            for (AvTelefono avTelefonoListAvTelefonoToAttach : avAsociado.getAvTelefonoList()) {
                avTelefonoListAvTelefonoToAttach = em.getReference(avTelefonoListAvTelefonoToAttach.getClass(), avTelefonoListAvTelefonoToAttach.getId());
                attachedAvTelefonoList.add(avTelefonoListAvTelefonoToAttach);
            }
            avAsociado.setAvTelefonoList(attachedAvTelefonoList);
            em.persist(avAsociado);
            for (AvSolicitud avSolicitudListAvSolicitud : avAsociado.getAvSolicitudList()) {
                AvAsociado oldAsociadoCifOfAvSolicitudListAvSolicitud = avSolicitudListAvSolicitud.getAsociadoCif();
                avSolicitudListAvSolicitud.setAsociadoCif(avAsociado);
                avSolicitudListAvSolicitud = em.merge(avSolicitudListAvSolicitud);
                if (oldAsociadoCifOfAvSolicitudListAvSolicitud != null) {
                    oldAsociadoCifOfAvSolicitudListAvSolicitud.getAvSolicitudList().remove(avSolicitudListAvSolicitud);
                    oldAsociadoCifOfAvSolicitudListAvSolicitud = em.merge(oldAsociadoCifOfAvSolicitudListAvSolicitud);
                }
            }
            for (AvTelefono avTelefonoListAvTelefono : avAsociado.getAvTelefonoList()) {
                AvAsociado oldAsociadoCifOfAvTelefonoListAvTelefono = avTelefonoListAvTelefono.getAsociadoCif();
                avTelefonoListAvTelefono.setAsociadoCif(avAsociado);
                avTelefonoListAvTelefono = em.merge(avTelefonoListAvTelefono);
                if (oldAsociadoCifOfAvTelefonoListAvTelefono != null) {
                    oldAsociadoCifOfAvTelefonoListAvTelefono.getAvTelefonoList().remove(avTelefonoListAvTelefono);
                    oldAsociadoCifOfAvTelefonoListAvTelefono = em.merge(oldAsociadoCifOfAvTelefonoListAvTelefono);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAvAsociado(avAsociado.getCif()) != null) {
                throw new PreexistingEntityException("AvAsociado " + avAsociado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvAsociado avAsociado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvAsociado persistentAvAsociado = em.find(AvAsociado.class, avAsociado.getCif());
            List<AvSolicitud> avSolicitudListOld = persistentAvAsociado.getAvSolicitudList();
            List<AvSolicitud> avSolicitudListNew = avAsociado.getAvSolicitudList();
            List<AvTelefono> avTelefonoListOld = persistentAvAsociado.getAvTelefonoList();
            List<AvTelefono> avTelefonoListNew = avAsociado.getAvTelefonoList();
            List<String> illegalOrphanMessages = null;
            for (AvSolicitud avSolicitudListOldAvSolicitud : avSolicitudListOld) {
                if (!avSolicitudListNew.contains(avSolicitudListOldAvSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvSolicitud " + avSolicitudListOldAvSolicitud + " since its asociadoCif field is not nullable.");
                }
            }
            for (AvTelefono avTelefonoListOldAvTelefono : avTelefonoListOld) {
                if (!avTelefonoListNew.contains(avTelefonoListOldAvTelefono)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvTelefono " + avTelefonoListOldAvTelefono + " since its asociadoCif field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<AvSolicitud> attachedAvSolicitudListNew = new ArrayList<AvSolicitud>();
            for (AvSolicitud avSolicitudListNewAvSolicitudToAttach : avSolicitudListNew) {
                avSolicitudListNewAvSolicitudToAttach = em.getReference(avSolicitudListNewAvSolicitudToAttach.getClass(), avSolicitudListNewAvSolicitudToAttach.getNumeroSolicitud());
                attachedAvSolicitudListNew.add(avSolicitudListNewAvSolicitudToAttach);
            }
            avSolicitudListNew = attachedAvSolicitudListNew;
            avAsociado.setAvSolicitudList(avSolicitudListNew);
            List<AvTelefono> attachedAvTelefonoListNew = new ArrayList<AvTelefono>();
            for (AvTelefono avTelefonoListNewAvTelefonoToAttach : avTelefonoListNew) {
                avTelefonoListNewAvTelefonoToAttach = em.getReference(avTelefonoListNewAvTelefonoToAttach.getClass(), avTelefonoListNewAvTelefonoToAttach.getId());
                attachedAvTelefonoListNew.add(avTelefonoListNewAvTelefonoToAttach);
            }
            avTelefonoListNew = attachedAvTelefonoListNew;
            avAsociado.setAvTelefonoList(avTelefonoListNew);
            avAsociado = em.merge(avAsociado);
            for (AvSolicitud avSolicitudListNewAvSolicitud : avSolicitudListNew) {
                if (!avSolicitudListOld.contains(avSolicitudListNewAvSolicitud)) {
                    AvAsociado oldAsociadoCifOfAvSolicitudListNewAvSolicitud = avSolicitudListNewAvSolicitud.getAsociadoCif();
                    avSolicitudListNewAvSolicitud.setAsociadoCif(avAsociado);
                    avSolicitudListNewAvSolicitud = em.merge(avSolicitudListNewAvSolicitud);
                    if (oldAsociadoCifOfAvSolicitudListNewAvSolicitud != null && !oldAsociadoCifOfAvSolicitudListNewAvSolicitud.equals(avAsociado)) {
                        oldAsociadoCifOfAvSolicitudListNewAvSolicitud.getAvSolicitudList().remove(avSolicitudListNewAvSolicitud);
                        oldAsociadoCifOfAvSolicitudListNewAvSolicitud = em.merge(oldAsociadoCifOfAvSolicitudListNewAvSolicitud);
                    }
                }
            }
            for (AvTelefono avTelefonoListNewAvTelefono : avTelefonoListNew) {
                if (!avTelefonoListOld.contains(avTelefonoListNewAvTelefono)) {
                    AvAsociado oldAsociadoCifOfAvTelefonoListNewAvTelefono = avTelefonoListNewAvTelefono.getAsociadoCif();
                    avTelefonoListNewAvTelefono.setAsociadoCif(avAsociado);
                    avTelefonoListNewAvTelefono = em.merge(avTelefonoListNewAvTelefono);
                    if (oldAsociadoCifOfAvTelefonoListNewAvTelefono != null && !oldAsociadoCifOfAvTelefonoListNewAvTelefono.equals(avAsociado)) {
                        oldAsociadoCifOfAvTelefonoListNewAvTelefono.getAvTelefonoList().remove(avTelefonoListNewAvTelefono);
                        oldAsociadoCifOfAvTelefonoListNewAvTelefono = em.merge(oldAsociadoCifOfAvTelefonoListNewAvTelefono);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = avAsociado.getCif();
                if (findAvAsociado(id) == null) {
                    throw new NonexistentEntityException("The avAsociado with id " + id + " no longer exists.");
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
            AvAsociado avAsociado;
            try {
                avAsociado = em.getReference(AvAsociado.class, id);
                avAsociado.getCif();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avAsociado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AvSolicitud> avSolicitudListOrphanCheck = avAsociado.getAvSolicitudList();
            for (AvSolicitud avSolicitudListOrphanCheckAvSolicitud : avSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvAsociado (" + avAsociado + ") cannot be destroyed since the AvSolicitud " + avSolicitudListOrphanCheckAvSolicitud + " in its avSolicitudList field has a non-nullable asociadoCif field.");
            }
            List<AvTelefono> avTelefonoListOrphanCheck = avAsociado.getAvTelefonoList();
            for (AvTelefono avTelefonoListOrphanCheckAvTelefono : avTelefonoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvAsociado (" + avAsociado + ") cannot be destroyed since the AvTelefono " + avTelefonoListOrphanCheckAvTelefono + " in its avTelefonoList field has a non-nullable asociadoCif field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(avAsociado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvAsociado> findAvAsociadoEntities() {
        return findAvAsociadoEntities(true, -1, -1);
    }

    public List<AvAsociado> findAvAsociadoEntities(int maxResults, int firstResult) {
        return findAvAsociadoEntities(false, maxResults, firstResult);
    }

    private List<AvAsociado> findAvAsociadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvAsociado.class));
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

    public AvAsociado findAvAsociado(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvAsociado.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvAsociadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvAsociado> rt = cq.from(AvAsociado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
