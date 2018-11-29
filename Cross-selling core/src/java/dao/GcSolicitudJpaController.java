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
 * @author Desarrollo
 */
public class GcSolicitudJpaController implements Serializable {

    public GcSolicitudJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(GcSolicitud gcSolicitud) throws PreexistingEntityException, Exception {
        if (gcSolicitud.getGcFichanegocioList() == null) {
            gcSolicitud.setGcFichanegocioList(new ArrayList<GcFichanegocio>());
        }
        if (gcSolicitud.getGcGestionList() == null) {
            gcSolicitud.setGcGestionList(new ArrayList<GcGestion>());
        }
        if (gcSolicitud.getGcSeguimientoList() == null) {
            gcSolicitud.setGcSeguimientoList(new ArrayList<GcSeguimiento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcAsociado asociadoCif = gcSolicitud.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif = em.getReference(asociadoCif.getClass(), asociadoCif.getCif());
                gcSolicitud.setAsociadoCif(asociadoCif);
            }
            GcDestino destinoId = gcSolicitud.getDestinoId();
            if (destinoId != null) {
                destinoId = em.getReference(destinoId.getClass(), destinoId.getId());
                gcSolicitud.setDestinoId(destinoId);
            }
            GcEstado estadoId = gcSolicitud.getEstadoId();
            if (estadoId != null) {
                estadoId = em.getReference(estadoId.getClass(), estadoId.getId());
                gcSolicitud.setEstadoId(estadoId);
            }
            GcRiesgo riesgoId = gcSolicitud.getRiesgoId();
            if (riesgoId != null) {
                riesgoId = em.getReference(riesgoId.getClass(), riesgoId.getId());
                gcSolicitud.setRiesgoId(riesgoId);
            }
            GcTipo tipoId = gcSolicitud.getTipoId();
            if (tipoId != null) {
                tipoId = em.getReference(tipoId.getClass(), tipoId.getId());
                gcSolicitud.setTipoId(tipoId);
            }
            GcTipocliente tipoclienteId = gcSolicitud.getTipoclienteId();
            if (tipoclienteId != null) {
                tipoclienteId = em.getReference(tipoclienteId.getClass(), tipoclienteId.getId());
                gcSolicitud.setTipoclienteId(tipoclienteId);
            }
            GcTramite tramiteId = gcSolicitud.getTramiteId();
            if (tramiteId != null) {
                tramiteId = em.getReference(tramiteId.getClass(), tramiteId.getId());
                gcSolicitud.setTramiteId(tramiteId);
            }
            List<GcFichanegocio> attachedGcFichanegocioList = new ArrayList<GcFichanegocio>();
            for (GcFichanegocio gcFichanegocioListGcFichanegocioToAttach : gcSolicitud.getGcFichanegocioList()) {
                gcFichanegocioListGcFichanegocioToAttach = em.getReference(gcFichanegocioListGcFichanegocioToAttach.getClass(), gcFichanegocioListGcFichanegocioToAttach.getId());
                attachedGcFichanegocioList.add(gcFichanegocioListGcFichanegocioToAttach);
            }
            gcSolicitud.setGcFichanegocioList(attachedGcFichanegocioList);
            List<GcGestion> attachedGcGestionList = new ArrayList<GcGestion>();
            for (GcGestion gcGestionListGcGestionToAttach : gcSolicitud.getGcGestionList()) {
                gcGestionListGcGestionToAttach = em.getReference(gcGestionListGcGestionToAttach.getClass(), gcGestionListGcGestionToAttach.getId());
                attachedGcGestionList.add(gcGestionListGcGestionToAttach);
            }
            gcSolicitud.setGcGestionList(attachedGcGestionList);
            List<GcSeguimiento> attachedGcSeguimientoList = new ArrayList<GcSeguimiento>();
            for (GcSeguimiento gcSeguimientoListGcSeguimientoToAttach : gcSolicitud.getGcSeguimientoList()) {
                gcSeguimientoListGcSeguimientoToAttach = em.getReference(gcSeguimientoListGcSeguimientoToAttach.getClass(), gcSeguimientoListGcSeguimientoToAttach.getId());
                attachedGcSeguimientoList.add(gcSeguimientoListGcSeguimientoToAttach);
            }
            gcSolicitud.setGcSeguimientoList(attachedGcSeguimientoList);
            em.persist(gcSolicitud);
            if (asociadoCif != null) {
                asociadoCif.getGcSolicitudList().add(gcSolicitud);
                asociadoCif = em.merge(asociadoCif);
            }
            if (destinoId != null) {
                destinoId.getGcSolicitudList().add(gcSolicitud);
                destinoId = em.merge(destinoId);
            }
            if (estadoId != null) {
                estadoId.getGcSolicitudList().add(gcSolicitud);
                estadoId = em.merge(estadoId);
            }
            if (riesgoId != null) {
                riesgoId.getGcSolicitudList().add(gcSolicitud);
                riesgoId = em.merge(riesgoId);
            }
            if (tipoId != null) {
                tipoId.getGcSolicitudList().add(gcSolicitud);
                tipoId = em.merge(tipoId);
            }
            if (tipoclienteId != null) {
                tipoclienteId.getGcSolicitudList().add(gcSolicitud);
                tipoclienteId = em.merge(tipoclienteId);
            }
            if (tramiteId != null) {
                tramiteId.getGcSolicitudList().add(gcSolicitud);
                tramiteId = em.merge(tramiteId);
            }
            for (GcFichanegocio gcFichanegocioListGcFichanegocio : gcSolicitud.getGcFichanegocioList()) {
                GcSolicitud oldSolicitudNumeroSolicitudOfGcFichanegocioListGcFichanegocio = gcFichanegocioListGcFichanegocio.getSolicitudNumeroSolicitud();
                gcFichanegocioListGcFichanegocio.setSolicitudNumeroSolicitud(gcSolicitud);
                gcFichanegocioListGcFichanegocio = em.merge(gcFichanegocioListGcFichanegocio);
                if (oldSolicitudNumeroSolicitudOfGcFichanegocioListGcFichanegocio != null) {
                    oldSolicitudNumeroSolicitudOfGcFichanegocioListGcFichanegocio.getGcFichanegocioList().remove(gcFichanegocioListGcFichanegocio);
                    oldSolicitudNumeroSolicitudOfGcFichanegocioListGcFichanegocio = em.merge(oldSolicitudNumeroSolicitudOfGcFichanegocioListGcFichanegocio);
                }
            }
            for (GcGestion gcGestionListGcGestion : gcSolicitud.getGcGestionList()) {
                GcSolicitud oldSolicitudNumeroSolicitudOfGcGestionListGcGestion = gcGestionListGcGestion.getSolicitudNumeroSolicitud();
                gcGestionListGcGestion.setSolicitudNumeroSolicitud(gcSolicitud);
                gcGestionListGcGestion = em.merge(gcGestionListGcGestion);
                if (oldSolicitudNumeroSolicitudOfGcGestionListGcGestion != null) {
                    oldSolicitudNumeroSolicitudOfGcGestionListGcGestion.getGcGestionList().remove(gcGestionListGcGestion);
                    oldSolicitudNumeroSolicitudOfGcGestionListGcGestion = em.merge(oldSolicitudNumeroSolicitudOfGcGestionListGcGestion);
                }
            }
            for (GcSeguimiento gcSeguimientoListGcSeguimiento : gcSolicitud.getGcSeguimientoList()) {
                GcSolicitud oldSolicitudNumeroSolicitudOfGcSeguimientoListGcSeguimiento = gcSeguimientoListGcSeguimiento.getSolicitudNumeroSolicitud();
                gcSeguimientoListGcSeguimiento.setSolicitudNumeroSolicitud(gcSolicitud);
                gcSeguimientoListGcSeguimiento = em.merge(gcSeguimientoListGcSeguimiento);
                if (oldSolicitudNumeroSolicitudOfGcSeguimientoListGcSeguimiento != null) {
                    oldSolicitudNumeroSolicitudOfGcSeguimientoListGcSeguimiento.getGcSeguimientoList().remove(gcSeguimientoListGcSeguimiento);
                    oldSolicitudNumeroSolicitudOfGcSeguimientoListGcSeguimiento = em.merge(oldSolicitudNumeroSolicitudOfGcSeguimientoListGcSeguimiento);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGcSolicitud(gcSolicitud.getNumeroSolicitud()) != null) {
                throw new PreexistingEntityException("GcSolicitud " + gcSolicitud + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(GcSolicitud gcSolicitud) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            GcSolicitud persistentGcSolicitud = em.find(GcSolicitud.class, gcSolicitud.getNumeroSolicitud());
            GcAsociado asociadoCifOld = persistentGcSolicitud.getAsociadoCif();
            GcAsociado asociadoCifNew = gcSolicitud.getAsociadoCif();
            GcDestino destinoIdOld = persistentGcSolicitud.getDestinoId();
            GcDestino destinoIdNew = gcSolicitud.getDestinoId();
            GcEstado estadoIdOld = persistentGcSolicitud.getEstadoId();
            GcEstado estadoIdNew = gcSolicitud.getEstadoId();
            GcRiesgo riesgoIdOld = persistentGcSolicitud.getRiesgoId();
            GcRiesgo riesgoIdNew = gcSolicitud.getRiesgoId();
            GcTipo tipoIdOld = persistentGcSolicitud.getTipoId();
            GcTipo tipoIdNew = gcSolicitud.getTipoId();
            GcTipocliente tipoclienteIdOld = persistentGcSolicitud.getTipoclienteId();
            GcTipocliente tipoclienteIdNew = gcSolicitud.getTipoclienteId();
            GcTramite tramiteIdOld = persistentGcSolicitud.getTramiteId();
            GcTramite tramiteIdNew = gcSolicitud.getTramiteId();
            List<GcFichanegocio> gcFichanegocioListOld = persistentGcSolicitud.getGcFichanegocioList();
            List<GcFichanegocio> gcFichanegocioListNew = gcSolicitud.getGcFichanegocioList();
            List<GcGestion> gcGestionListOld = persistentGcSolicitud.getGcGestionList();
            List<GcGestion> gcGestionListNew = gcSolicitud.getGcGestionList();
            List<GcSeguimiento> gcSeguimientoListOld = persistentGcSolicitud.getGcSeguimientoList();
            List<GcSeguimiento> gcSeguimientoListNew = gcSolicitud.getGcSeguimientoList();
            List<String> illegalOrphanMessages = null;
            for (GcFichanegocio gcFichanegocioListOldGcFichanegocio : gcFichanegocioListOld) {
                if (!gcFichanegocioListNew.contains(gcFichanegocioListOldGcFichanegocio)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcFichanegocio " + gcFichanegocioListOldGcFichanegocio + " since its solicitudNumeroSolicitud field is not nullable.");
                }
            }
            for (GcGestion gcGestionListOldGcGestion : gcGestionListOld) {
                if (!gcGestionListNew.contains(gcGestionListOldGcGestion)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcGestion " + gcGestionListOldGcGestion + " since its solicitudNumeroSolicitud field is not nullable.");
                }
            }
            for (GcSeguimiento gcSeguimientoListOldGcSeguimiento : gcSeguimientoListOld) {
                if (!gcSeguimientoListNew.contains(gcSeguimientoListOldGcSeguimiento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain GcSeguimiento " + gcSeguimientoListOldGcSeguimiento + " since its solicitudNumeroSolicitud field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (asociadoCifNew != null) {
                asociadoCifNew = em.getReference(asociadoCifNew.getClass(), asociadoCifNew.getCif());
                gcSolicitud.setAsociadoCif(asociadoCifNew);
            }
            if (destinoIdNew != null) {
                destinoIdNew = em.getReference(destinoIdNew.getClass(), destinoIdNew.getId());
                gcSolicitud.setDestinoId(destinoIdNew);
            }
            if (estadoIdNew != null) {
                estadoIdNew = em.getReference(estadoIdNew.getClass(), estadoIdNew.getId());
                gcSolicitud.setEstadoId(estadoIdNew);
            }
            if (riesgoIdNew != null) {
                riesgoIdNew = em.getReference(riesgoIdNew.getClass(), riesgoIdNew.getId());
                gcSolicitud.setRiesgoId(riesgoIdNew);
            }
            if (tipoIdNew != null) {
                tipoIdNew = em.getReference(tipoIdNew.getClass(), tipoIdNew.getId());
                gcSolicitud.setTipoId(tipoIdNew);
            }
            if (tipoclienteIdNew != null) {
                tipoclienteIdNew = em.getReference(tipoclienteIdNew.getClass(), tipoclienteIdNew.getId());
                gcSolicitud.setTipoclienteId(tipoclienteIdNew);
            }
            if (tramiteIdNew != null) {
                tramiteIdNew = em.getReference(tramiteIdNew.getClass(), tramiteIdNew.getId());
                gcSolicitud.setTramiteId(tramiteIdNew);
            }
            List<GcFichanegocio> attachedGcFichanegocioListNew = new ArrayList<GcFichanegocio>();
            for (GcFichanegocio gcFichanegocioListNewGcFichanegocioToAttach : gcFichanegocioListNew) {
                gcFichanegocioListNewGcFichanegocioToAttach = em.getReference(gcFichanegocioListNewGcFichanegocioToAttach.getClass(), gcFichanegocioListNewGcFichanegocioToAttach.getId());
                attachedGcFichanegocioListNew.add(gcFichanegocioListNewGcFichanegocioToAttach);
            }
            gcFichanegocioListNew = attachedGcFichanegocioListNew;
            gcSolicitud.setGcFichanegocioList(gcFichanegocioListNew);
            List<GcGestion> attachedGcGestionListNew = new ArrayList<GcGestion>();
            for (GcGestion gcGestionListNewGcGestionToAttach : gcGestionListNew) {
                gcGestionListNewGcGestionToAttach = em.getReference(gcGestionListNewGcGestionToAttach.getClass(), gcGestionListNewGcGestionToAttach.getId());
                attachedGcGestionListNew.add(gcGestionListNewGcGestionToAttach);
            }
            gcGestionListNew = attachedGcGestionListNew;
            gcSolicitud.setGcGestionList(gcGestionListNew);
            List<GcSeguimiento> attachedGcSeguimientoListNew = new ArrayList<GcSeguimiento>();
            for (GcSeguimiento gcSeguimientoListNewGcSeguimientoToAttach : gcSeguimientoListNew) {
                gcSeguimientoListNewGcSeguimientoToAttach = em.getReference(gcSeguimientoListNewGcSeguimientoToAttach.getClass(), gcSeguimientoListNewGcSeguimientoToAttach.getId());
                attachedGcSeguimientoListNew.add(gcSeguimientoListNewGcSeguimientoToAttach);
            }
            gcSeguimientoListNew = attachedGcSeguimientoListNew;
            gcSolicitud.setGcSeguimientoList(gcSeguimientoListNew);
            gcSolicitud = em.merge(gcSolicitud);
            if (asociadoCifOld != null && !asociadoCifOld.equals(asociadoCifNew)) {
                asociadoCifOld.getGcSolicitudList().remove(gcSolicitud);
                asociadoCifOld = em.merge(asociadoCifOld);
            }
            if (asociadoCifNew != null && !asociadoCifNew.equals(asociadoCifOld)) {
                asociadoCifNew.getGcSolicitudList().add(gcSolicitud);
                asociadoCifNew = em.merge(asociadoCifNew);
            }
            if (destinoIdOld != null && !destinoIdOld.equals(destinoIdNew)) {
                destinoIdOld.getGcSolicitudList().remove(gcSolicitud);
                destinoIdOld = em.merge(destinoIdOld);
            }
            if (destinoIdNew != null && !destinoIdNew.equals(destinoIdOld)) {
                destinoIdNew.getGcSolicitudList().add(gcSolicitud);
                destinoIdNew = em.merge(destinoIdNew);
            }
            if (estadoIdOld != null && !estadoIdOld.equals(estadoIdNew)) {
                estadoIdOld.getGcSolicitudList().remove(gcSolicitud);
                estadoIdOld = em.merge(estadoIdOld);
            }
            if (estadoIdNew != null && !estadoIdNew.equals(estadoIdOld)) {
                estadoIdNew.getGcSolicitudList().add(gcSolicitud);
                estadoIdNew = em.merge(estadoIdNew);
            }
            if (riesgoIdOld != null && !riesgoIdOld.equals(riesgoIdNew)) {
                riesgoIdOld.getGcSolicitudList().remove(gcSolicitud);
                riesgoIdOld = em.merge(riesgoIdOld);
            }
            if (riesgoIdNew != null && !riesgoIdNew.equals(riesgoIdOld)) {
                riesgoIdNew.getGcSolicitudList().add(gcSolicitud);
                riesgoIdNew = em.merge(riesgoIdNew);
            }
            if (tipoIdOld != null && !tipoIdOld.equals(tipoIdNew)) {
                tipoIdOld.getGcSolicitudList().remove(gcSolicitud);
                tipoIdOld = em.merge(tipoIdOld);
            }
            if (tipoIdNew != null && !tipoIdNew.equals(tipoIdOld)) {
                tipoIdNew.getGcSolicitudList().add(gcSolicitud);
                tipoIdNew = em.merge(tipoIdNew);
            }
            if (tipoclienteIdOld != null && !tipoclienteIdOld.equals(tipoclienteIdNew)) {
                tipoclienteIdOld.getGcSolicitudList().remove(gcSolicitud);
                tipoclienteIdOld = em.merge(tipoclienteIdOld);
            }
            if (tipoclienteIdNew != null && !tipoclienteIdNew.equals(tipoclienteIdOld)) {
                tipoclienteIdNew.getGcSolicitudList().add(gcSolicitud);
                tipoclienteIdNew = em.merge(tipoclienteIdNew);
            }
            if (tramiteIdOld != null && !tramiteIdOld.equals(tramiteIdNew)) {
                tramiteIdOld.getGcSolicitudList().remove(gcSolicitud);
                tramiteIdOld = em.merge(tramiteIdOld);
            }
            if (tramiteIdNew != null && !tramiteIdNew.equals(tramiteIdOld)) {
                tramiteIdNew.getGcSolicitudList().add(gcSolicitud);
                tramiteIdNew = em.merge(tramiteIdNew);
            }
            for (GcFichanegocio gcFichanegocioListNewGcFichanegocio : gcFichanegocioListNew) {
                if (!gcFichanegocioListOld.contains(gcFichanegocioListNewGcFichanegocio)) {
                    GcSolicitud oldSolicitudNumeroSolicitudOfGcFichanegocioListNewGcFichanegocio = gcFichanegocioListNewGcFichanegocio.getSolicitudNumeroSolicitud();
                    gcFichanegocioListNewGcFichanegocio.setSolicitudNumeroSolicitud(gcSolicitud);
                    gcFichanegocioListNewGcFichanegocio = em.merge(gcFichanegocioListNewGcFichanegocio);
                    if (oldSolicitudNumeroSolicitudOfGcFichanegocioListNewGcFichanegocio != null && !oldSolicitudNumeroSolicitudOfGcFichanegocioListNewGcFichanegocio.equals(gcSolicitud)) {
                        oldSolicitudNumeroSolicitudOfGcFichanegocioListNewGcFichanegocio.getGcFichanegocioList().remove(gcFichanegocioListNewGcFichanegocio);
                        oldSolicitudNumeroSolicitudOfGcFichanegocioListNewGcFichanegocio = em.merge(oldSolicitudNumeroSolicitudOfGcFichanegocioListNewGcFichanegocio);
                    }
                }
            }
            for (GcGestion gcGestionListNewGcGestion : gcGestionListNew) {
                if (!gcGestionListOld.contains(gcGestionListNewGcGestion)) {
                    GcSolicitud oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion = gcGestionListNewGcGestion.getSolicitudNumeroSolicitud();
                    gcGestionListNewGcGestion.setSolicitudNumeroSolicitud(gcSolicitud);
                    gcGestionListNewGcGestion = em.merge(gcGestionListNewGcGestion);
                    if (oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion != null && !oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion.equals(gcSolicitud)) {
                        oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion.getGcGestionList().remove(gcGestionListNewGcGestion);
                        oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion = em.merge(oldSolicitudNumeroSolicitudOfGcGestionListNewGcGestion);
                    }
                }
            }
            for (GcSeguimiento gcSeguimientoListNewGcSeguimiento : gcSeguimientoListNew) {
                if (!gcSeguimientoListOld.contains(gcSeguimientoListNewGcSeguimiento)) {
                    GcSolicitud oldSolicitudNumeroSolicitudOfGcSeguimientoListNewGcSeguimiento = gcSeguimientoListNewGcSeguimiento.getSolicitudNumeroSolicitud();
                    gcSeguimientoListNewGcSeguimiento.setSolicitudNumeroSolicitud(gcSolicitud);
                    gcSeguimientoListNewGcSeguimiento = em.merge(gcSeguimientoListNewGcSeguimiento);
                    if (oldSolicitudNumeroSolicitudOfGcSeguimientoListNewGcSeguimiento != null && !oldSolicitudNumeroSolicitudOfGcSeguimientoListNewGcSeguimiento.equals(gcSolicitud)) {
                        oldSolicitudNumeroSolicitudOfGcSeguimientoListNewGcSeguimiento.getGcSeguimientoList().remove(gcSeguimientoListNewGcSeguimiento);
                        oldSolicitudNumeroSolicitudOfGcSeguimientoListNewGcSeguimiento = em.merge(oldSolicitudNumeroSolicitudOfGcSeguimientoListNewGcSeguimiento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = gcSolicitud.getNumeroSolicitud();
                if (findGcSolicitud(id) == null) {
                    throw new NonexistentEntityException("The gcSolicitud with id " + id + " no longer exists.");
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
            GcSolicitud gcSolicitud;
            try {
                gcSolicitud = em.getReference(GcSolicitud.class, id);
                gcSolicitud.getNumeroSolicitud();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The gcSolicitud with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<GcFichanegocio> gcFichanegocioListOrphanCheck = gcSolicitud.getGcFichanegocioList();
            for (GcFichanegocio gcFichanegocioListOrphanCheckGcFichanegocio : gcFichanegocioListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcSolicitud (" + gcSolicitud + ") cannot be destroyed since the GcFichanegocio " + gcFichanegocioListOrphanCheckGcFichanegocio + " in its gcFichanegocioList field has a non-nullable solicitudNumeroSolicitud field.");
            }
            List<GcGestion> gcGestionListOrphanCheck = gcSolicitud.getGcGestionList();
            for (GcGestion gcGestionListOrphanCheckGcGestion : gcGestionListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcSolicitud (" + gcSolicitud + ") cannot be destroyed since the GcGestion " + gcGestionListOrphanCheckGcGestion + " in its gcGestionList field has a non-nullable solicitudNumeroSolicitud field.");
            }
            List<GcSeguimiento> gcSeguimientoListOrphanCheck = gcSolicitud.getGcSeguimientoList();
            for (GcSeguimiento gcSeguimientoListOrphanCheckGcSeguimiento : gcSeguimientoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This GcSolicitud (" + gcSolicitud + ") cannot be destroyed since the GcSeguimiento " + gcSeguimientoListOrphanCheckGcSeguimiento + " in its gcSeguimientoList field has a non-nullable solicitudNumeroSolicitud field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            GcAsociado asociadoCif = gcSolicitud.getAsociadoCif();
            if (asociadoCif != null) {
                asociadoCif.getGcSolicitudList().remove(gcSolicitud);
                asociadoCif = em.merge(asociadoCif);
            }
            GcDestino destinoId = gcSolicitud.getDestinoId();
            if (destinoId != null) {
                destinoId.getGcSolicitudList().remove(gcSolicitud);
                destinoId = em.merge(destinoId);
            }
            GcEstado estadoId = gcSolicitud.getEstadoId();
            if (estadoId != null) {
                estadoId.getGcSolicitudList().remove(gcSolicitud);
                estadoId = em.merge(estadoId);
            }
            GcRiesgo riesgoId = gcSolicitud.getRiesgoId();
            if (riesgoId != null) {
                riesgoId.getGcSolicitudList().remove(gcSolicitud);
                riesgoId = em.merge(riesgoId);
            }
            GcTipo tipoId = gcSolicitud.getTipoId();
            if (tipoId != null) {
                tipoId.getGcSolicitudList().remove(gcSolicitud);
                tipoId = em.merge(tipoId);
            }
            GcTipocliente tipoclienteId = gcSolicitud.getTipoclienteId();
            if (tipoclienteId != null) {
                tipoclienteId.getGcSolicitudList().remove(gcSolicitud);
                tipoclienteId = em.merge(tipoclienteId);
            }
            GcTramite tramiteId = gcSolicitud.getTramiteId();
            if (tramiteId != null) {
                tramiteId.getGcSolicitudList().remove(gcSolicitud);
                tramiteId = em.merge(tramiteId);
            }
            em.remove(gcSolicitud);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<GcSolicitud> findGcSolicitudEntities() {
        return findGcSolicitudEntities(true, -1, -1);
    }

    public List<GcSolicitud> findGcSolicitudEntities(int maxResults, int firstResult) {
        return findGcSolicitudEntities(false, maxResults, firstResult);
    }

    private List<GcSolicitud> findGcSolicitudEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GcSolicitud.class));
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

    public GcSolicitud findGcSolicitud(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(GcSolicitud.class, id);
        } finally {
            em.close();
        }
    }

    public int getGcSolicitudCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<GcSolicitud> rt = cq.from(GcSolicitud.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
