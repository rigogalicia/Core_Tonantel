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
public class AvInmuebleJpaController implements Serializable {

    public AvInmuebleJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AvInmueble avInmueble) {
        if (avInmueble.getAvColindanteList() == null) {
            avInmueble.setAvColindanteList(new ArrayList<AvColindante>());
        }
        if (avInmueble.getAvAreaList() == null) {
            avInmueble.setAvAreaList(new ArrayList<AvArea>());
        }
        if (avInmueble.getAvSolicitudList() == null) {
            avInmueble.setAvSolicitudList(new ArrayList<AvSolicitud>());
        }
        if (avInmueble.getAvAvaluoList() == null) {
            avInmueble.setAvAvaluoList(new ArrayList<AvAvaluo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvDocumento documentoId = avInmueble.getDocumentoId();
            if (documentoId != null) {
                documentoId = em.getReference(documentoId.getClass(), documentoId.getId());
                avInmueble.setDocumentoId(documentoId);
            }
            AvPropietario propietarioDpi = avInmueble.getPropietarioDpi();
            if (propietarioDpi != null) {
                propietarioDpi = em.getReference(propietarioDpi.getClass(), propietarioDpi.getDpi());
                avInmueble.setPropietarioDpi(propietarioDpi);
            }
            List<AvColindante> attachedAvColindanteList = new ArrayList<AvColindante>();
            for (AvColindante avColindanteListAvColindanteToAttach : avInmueble.getAvColindanteList()) {
                avColindanteListAvColindanteToAttach = em.getReference(avColindanteListAvColindanteToAttach.getClass(), avColindanteListAvColindanteToAttach.getId());
                attachedAvColindanteList.add(avColindanteListAvColindanteToAttach);
            }
            avInmueble.setAvColindanteList(attachedAvColindanteList);
            List<AvArea> attachedAvAreaList = new ArrayList<AvArea>();
            for (AvArea avAreaListAvAreaToAttach : avInmueble.getAvAreaList()) {
                avAreaListAvAreaToAttach = em.getReference(avAreaListAvAreaToAttach.getClass(), avAreaListAvAreaToAttach.getId());
                attachedAvAreaList.add(avAreaListAvAreaToAttach);
            }
            avInmueble.setAvAreaList(attachedAvAreaList);
            List<AvSolicitud> attachedAvSolicitudList = new ArrayList<AvSolicitud>();
            for (AvSolicitud avSolicitudListAvSolicitudToAttach : avInmueble.getAvSolicitudList()) {
                avSolicitudListAvSolicitudToAttach = em.getReference(avSolicitudListAvSolicitudToAttach.getClass(), avSolicitudListAvSolicitudToAttach.getNumeroSolicitud());
                attachedAvSolicitudList.add(avSolicitudListAvSolicitudToAttach);
            }
            avInmueble.setAvSolicitudList(attachedAvSolicitudList);
            List<AvAvaluo> attachedAvAvaluoList = new ArrayList<AvAvaluo>();
            for (AvAvaluo avAvaluoListAvAvaluoToAttach : avInmueble.getAvAvaluoList()) {
                avAvaluoListAvAvaluoToAttach = em.getReference(avAvaluoListAvAvaluoToAttach.getClass(), avAvaluoListAvAvaluoToAttach.getId());
                attachedAvAvaluoList.add(avAvaluoListAvAvaluoToAttach);
            }
            avInmueble.setAvAvaluoList(attachedAvAvaluoList);
            em.persist(avInmueble);
            if (documentoId != null) {
                documentoId.getAvInmuebleList().add(avInmueble);
                documentoId = em.merge(documentoId);
            }
            if (propietarioDpi != null) {
                propietarioDpi.getAvInmuebleList().add(avInmueble);
                propietarioDpi = em.merge(propietarioDpi);
            }
            for (AvColindante avColindanteListAvColindante : avInmueble.getAvColindanteList()) {
                AvInmueble oldInmuebleIdOfAvColindanteListAvColindante = avColindanteListAvColindante.getInmuebleId();
                avColindanteListAvColindante.setInmuebleId(avInmueble);
                avColindanteListAvColindante = em.merge(avColindanteListAvColindante);
                if (oldInmuebleIdOfAvColindanteListAvColindante != null) {
                    oldInmuebleIdOfAvColindanteListAvColindante.getAvColindanteList().remove(avColindanteListAvColindante);
                    oldInmuebleIdOfAvColindanteListAvColindante = em.merge(oldInmuebleIdOfAvColindanteListAvColindante);
                }
            }
            for (AvArea avAreaListAvArea : avInmueble.getAvAreaList()) {
                AvInmueble oldInmuebleIdOfAvAreaListAvArea = avAreaListAvArea.getInmuebleId();
                avAreaListAvArea.setInmuebleId(avInmueble);
                avAreaListAvArea = em.merge(avAreaListAvArea);
                if (oldInmuebleIdOfAvAreaListAvArea != null) {
                    oldInmuebleIdOfAvAreaListAvArea.getAvAreaList().remove(avAreaListAvArea);
                    oldInmuebleIdOfAvAreaListAvArea = em.merge(oldInmuebleIdOfAvAreaListAvArea);
                }
            }
            for (AvSolicitud avSolicitudListAvSolicitud : avInmueble.getAvSolicitudList()) {
                AvInmueble oldInmuebleIdOfAvSolicitudListAvSolicitud = avSolicitudListAvSolicitud.getInmuebleId();
                avSolicitudListAvSolicitud.setInmuebleId(avInmueble);
                avSolicitudListAvSolicitud = em.merge(avSolicitudListAvSolicitud);
                if (oldInmuebleIdOfAvSolicitudListAvSolicitud != null) {
                    oldInmuebleIdOfAvSolicitudListAvSolicitud.getAvSolicitudList().remove(avSolicitudListAvSolicitud);
                    oldInmuebleIdOfAvSolicitudListAvSolicitud = em.merge(oldInmuebleIdOfAvSolicitudListAvSolicitud);
                }
            }
            for (AvAvaluo avAvaluoListAvAvaluo : avInmueble.getAvAvaluoList()) {
                AvInmueble oldInmuebleIdOfAvAvaluoListAvAvaluo = avAvaluoListAvAvaluo.getInmuebleId();
                avAvaluoListAvAvaluo.setInmuebleId(avInmueble);
                avAvaluoListAvAvaluo = em.merge(avAvaluoListAvAvaluo);
                if (oldInmuebleIdOfAvAvaluoListAvAvaluo != null) {
                    oldInmuebleIdOfAvAvaluoListAvAvaluo.getAvAvaluoList().remove(avAvaluoListAvAvaluo);
                    oldInmuebleIdOfAvAvaluoListAvAvaluo = em.merge(oldInmuebleIdOfAvAvaluoListAvAvaluo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AvInmueble avInmueble) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AvInmueble persistentAvInmueble = em.find(AvInmueble.class, avInmueble.getId());
            AvDocumento documentoIdOld = persistentAvInmueble.getDocumentoId();
            AvDocumento documentoIdNew = avInmueble.getDocumentoId();
            AvPropietario propietarioDpiOld = persistentAvInmueble.getPropietarioDpi();
            AvPropietario propietarioDpiNew = avInmueble.getPropietarioDpi();
            List<AvColindante> avColindanteListOld = persistentAvInmueble.getAvColindanteList();
            List<AvColindante> avColindanteListNew = avInmueble.getAvColindanteList();
            List<AvArea> avAreaListOld = persistentAvInmueble.getAvAreaList();
            List<AvArea> avAreaListNew = avInmueble.getAvAreaList();
            List<AvSolicitud> avSolicitudListOld = persistentAvInmueble.getAvSolicitudList();
            List<AvSolicitud> avSolicitudListNew = avInmueble.getAvSolicitudList();
            List<AvAvaluo> avAvaluoListOld = persistentAvInmueble.getAvAvaluoList();
            List<AvAvaluo> avAvaluoListNew = avInmueble.getAvAvaluoList();
            List<String> illegalOrphanMessages = null;
            for (AvColindante avColindanteListOldAvColindante : avColindanteListOld) {
                if (!avColindanteListNew.contains(avColindanteListOldAvColindante)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvColindante " + avColindanteListOldAvColindante + " since its inmuebleId field is not nullable.");
                }
            }
            for (AvArea avAreaListOldAvArea : avAreaListOld) {
                if (!avAreaListNew.contains(avAreaListOldAvArea)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvArea " + avAreaListOldAvArea + " since its inmuebleId field is not nullable.");
                }
            }
            for (AvSolicitud avSolicitudListOldAvSolicitud : avSolicitudListOld) {
                if (!avSolicitudListNew.contains(avSolicitudListOldAvSolicitud)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvSolicitud " + avSolicitudListOldAvSolicitud + " since its inmuebleId field is not nullable.");
                }
            }
            for (AvAvaluo avAvaluoListOldAvAvaluo : avAvaluoListOld) {
                if (!avAvaluoListNew.contains(avAvaluoListOldAvAvaluo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain AvAvaluo " + avAvaluoListOldAvAvaluo + " since its inmuebleId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (documentoIdNew != null) {
                documentoIdNew = em.getReference(documentoIdNew.getClass(), documentoIdNew.getId());
                avInmueble.setDocumentoId(documentoIdNew);
            }
            if (propietarioDpiNew != null) {
                propietarioDpiNew = em.getReference(propietarioDpiNew.getClass(), propietarioDpiNew.getDpi());
                avInmueble.setPropietarioDpi(propietarioDpiNew);
            }
            List<AvColindante> attachedAvColindanteListNew = new ArrayList<AvColindante>();
            for (AvColindante avColindanteListNewAvColindanteToAttach : avColindanteListNew) {
                avColindanteListNewAvColindanteToAttach = em.getReference(avColindanteListNewAvColindanteToAttach.getClass(), avColindanteListNewAvColindanteToAttach.getId());
                attachedAvColindanteListNew.add(avColindanteListNewAvColindanteToAttach);
            }
            avColindanteListNew = attachedAvColindanteListNew;
            avInmueble.setAvColindanteList(avColindanteListNew);
            List<AvArea> attachedAvAreaListNew = new ArrayList<AvArea>();
            for (AvArea avAreaListNewAvAreaToAttach : avAreaListNew) {
                avAreaListNewAvAreaToAttach = em.getReference(avAreaListNewAvAreaToAttach.getClass(), avAreaListNewAvAreaToAttach.getId());
                attachedAvAreaListNew.add(avAreaListNewAvAreaToAttach);
            }
            avAreaListNew = attachedAvAreaListNew;
            avInmueble.setAvAreaList(avAreaListNew);
            List<AvSolicitud> attachedAvSolicitudListNew = new ArrayList<AvSolicitud>();
            for (AvSolicitud avSolicitudListNewAvSolicitudToAttach : avSolicitudListNew) {
                avSolicitudListNewAvSolicitudToAttach = em.getReference(avSolicitudListNewAvSolicitudToAttach.getClass(), avSolicitudListNewAvSolicitudToAttach.getNumeroSolicitud());
                attachedAvSolicitudListNew.add(avSolicitudListNewAvSolicitudToAttach);
            }
            avSolicitudListNew = attachedAvSolicitudListNew;
            avInmueble.setAvSolicitudList(avSolicitudListNew);
            List<AvAvaluo> attachedAvAvaluoListNew = new ArrayList<AvAvaluo>();
            for (AvAvaluo avAvaluoListNewAvAvaluoToAttach : avAvaluoListNew) {
                avAvaluoListNewAvAvaluoToAttach = em.getReference(avAvaluoListNewAvAvaluoToAttach.getClass(), avAvaluoListNewAvAvaluoToAttach.getId());
                attachedAvAvaluoListNew.add(avAvaluoListNewAvAvaluoToAttach);
            }
            avAvaluoListNew = attachedAvAvaluoListNew;
            avInmueble.setAvAvaluoList(avAvaluoListNew);
            avInmueble = em.merge(avInmueble);
            if (documentoIdOld != null && !documentoIdOld.equals(documentoIdNew)) {
                documentoIdOld.getAvInmuebleList().remove(avInmueble);
                documentoIdOld = em.merge(documentoIdOld);
            }
            if (documentoIdNew != null && !documentoIdNew.equals(documentoIdOld)) {
                documentoIdNew.getAvInmuebleList().add(avInmueble);
                documentoIdNew = em.merge(documentoIdNew);
            }
            if (propietarioDpiOld != null && !propietarioDpiOld.equals(propietarioDpiNew)) {
                propietarioDpiOld.getAvInmuebleList().remove(avInmueble);
                propietarioDpiOld = em.merge(propietarioDpiOld);
            }
            if (propietarioDpiNew != null && !propietarioDpiNew.equals(propietarioDpiOld)) {
                propietarioDpiNew.getAvInmuebleList().add(avInmueble);
                propietarioDpiNew = em.merge(propietarioDpiNew);
            }
            for (AvColindante avColindanteListNewAvColindante : avColindanteListNew) {
                if (!avColindanteListOld.contains(avColindanteListNewAvColindante)) {
                    AvInmueble oldInmuebleIdOfAvColindanteListNewAvColindante = avColindanteListNewAvColindante.getInmuebleId();
                    avColindanteListNewAvColindante.setInmuebleId(avInmueble);
                    avColindanteListNewAvColindante = em.merge(avColindanteListNewAvColindante);
                    if (oldInmuebleIdOfAvColindanteListNewAvColindante != null && !oldInmuebleIdOfAvColindanteListNewAvColindante.equals(avInmueble)) {
                        oldInmuebleIdOfAvColindanteListNewAvColindante.getAvColindanteList().remove(avColindanteListNewAvColindante);
                        oldInmuebleIdOfAvColindanteListNewAvColindante = em.merge(oldInmuebleIdOfAvColindanteListNewAvColindante);
                    }
                }
            }
            for (AvArea avAreaListNewAvArea : avAreaListNew) {
                if (!avAreaListOld.contains(avAreaListNewAvArea)) {
                    AvInmueble oldInmuebleIdOfAvAreaListNewAvArea = avAreaListNewAvArea.getInmuebleId();
                    avAreaListNewAvArea.setInmuebleId(avInmueble);
                    avAreaListNewAvArea = em.merge(avAreaListNewAvArea);
                    if (oldInmuebleIdOfAvAreaListNewAvArea != null && !oldInmuebleIdOfAvAreaListNewAvArea.equals(avInmueble)) {
                        oldInmuebleIdOfAvAreaListNewAvArea.getAvAreaList().remove(avAreaListNewAvArea);
                        oldInmuebleIdOfAvAreaListNewAvArea = em.merge(oldInmuebleIdOfAvAreaListNewAvArea);
                    }
                }
            }
            for (AvSolicitud avSolicitudListNewAvSolicitud : avSolicitudListNew) {
                if (!avSolicitudListOld.contains(avSolicitudListNewAvSolicitud)) {
                    AvInmueble oldInmuebleIdOfAvSolicitudListNewAvSolicitud = avSolicitudListNewAvSolicitud.getInmuebleId();
                    avSolicitudListNewAvSolicitud.setInmuebleId(avInmueble);
                    avSolicitudListNewAvSolicitud = em.merge(avSolicitudListNewAvSolicitud);
                    if (oldInmuebleIdOfAvSolicitudListNewAvSolicitud != null && !oldInmuebleIdOfAvSolicitudListNewAvSolicitud.equals(avInmueble)) {
                        oldInmuebleIdOfAvSolicitudListNewAvSolicitud.getAvSolicitudList().remove(avSolicitudListNewAvSolicitud);
                        oldInmuebleIdOfAvSolicitudListNewAvSolicitud = em.merge(oldInmuebleIdOfAvSolicitudListNewAvSolicitud);
                    }
                }
            }
            for (AvAvaluo avAvaluoListNewAvAvaluo : avAvaluoListNew) {
                if (!avAvaluoListOld.contains(avAvaluoListNewAvAvaluo)) {
                    AvInmueble oldInmuebleIdOfAvAvaluoListNewAvAvaluo = avAvaluoListNewAvAvaluo.getInmuebleId();
                    avAvaluoListNewAvAvaluo.setInmuebleId(avInmueble);
                    avAvaluoListNewAvAvaluo = em.merge(avAvaluoListNewAvAvaluo);
                    if (oldInmuebleIdOfAvAvaluoListNewAvAvaluo != null && !oldInmuebleIdOfAvAvaluoListNewAvAvaluo.equals(avInmueble)) {
                        oldInmuebleIdOfAvAvaluoListNewAvAvaluo.getAvAvaluoList().remove(avAvaluoListNewAvAvaluo);
                        oldInmuebleIdOfAvAvaluoListNewAvAvaluo = em.merge(oldInmuebleIdOfAvAvaluoListNewAvAvaluo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = avInmueble.getId();
                if (findAvInmueble(id) == null) {
                    throw new NonexistentEntityException("The avInmueble with id " + id + " no longer exists.");
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
            AvInmueble avInmueble;
            try {
                avInmueble = em.getReference(AvInmueble.class, id);
                avInmueble.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The avInmueble with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<AvColindante> avColindanteListOrphanCheck = avInmueble.getAvColindanteList();
            for (AvColindante avColindanteListOrphanCheckAvColindante : avColindanteListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvInmueble (" + avInmueble + ") cannot be destroyed since the AvColindante " + avColindanteListOrphanCheckAvColindante + " in its avColindanteList field has a non-nullable inmuebleId field.");
            }
            List<AvArea> avAreaListOrphanCheck = avInmueble.getAvAreaList();
            for (AvArea avAreaListOrphanCheckAvArea : avAreaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvInmueble (" + avInmueble + ") cannot be destroyed since the AvArea " + avAreaListOrphanCheckAvArea + " in its avAreaList field has a non-nullable inmuebleId field.");
            }
            List<AvSolicitud> avSolicitudListOrphanCheck = avInmueble.getAvSolicitudList();
            for (AvSolicitud avSolicitudListOrphanCheckAvSolicitud : avSolicitudListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvInmueble (" + avInmueble + ") cannot be destroyed since the AvSolicitud " + avSolicitudListOrphanCheckAvSolicitud + " in its avSolicitudList field has a non-nullable inmuebleId field.");
            }
            List<AvAvaluo> avAvaluoListOrphanCheck = avInmueble.getAvAvaluoList();
            for (AvAvaluo avAvaluoListOrphanCheckAvAvaluo : avAvaluoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This AvInmueble (" + avInmueble + ") cannot be destroyed since the AvAvaluo " + avAvaluoListOrphanCheckAvAvaluo + " in its avAvaluoList field has a non-nullable inmuebleId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            AvDocumento documentoId = avInmueble.getDocumentoId();
            if (documentoId != null) {
                documentoId.getAvInmuebleList().remove(avInmueble);
                documentoId = em.merge(documentoId);
            }
            AvPropietario propietarioDpi = avInmueble.getPropietarioDpi();
            if (propietarioDpi != null) {
                propietarioDpi.getAvInmuebleList().remove(avInmueble);
                propietarioDpi = em.merge(propietarioDpi);
            }
            em.remove(avInmueble);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AvInmueble> findAvInmuebleEntities() {
        return findAvInmuebleEntities(true, -1, -1);
    }

    public List<AvInmueble> findAvInmuebleEntities(int maxResults, int firstResult) {
        return findAvInmuebleEntities(false, maxResults, firstResult);
    }

    private List<AvInmueble> findAvInmuebleEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AvInmueble.class));
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

    public AvInmueble findAvInmueble(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AvInmueble.class, id);
        } finally {
            em.close();
        }
    }

    public int getAvInmuebleCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AvInmueble> rt = cq.from(AvInmueble.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
