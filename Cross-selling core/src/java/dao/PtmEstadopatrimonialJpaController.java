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
public class PtmEstadopatrimonialJpaController implements Serializable {

    public PtmEstadopatrimonialJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmEstadopatrimonial ptmEstadopatrimonial) throws PreexistingEntityException, Exception {
        if (ptmEstadopatrimonial.getPtmEstadopatrimonialPK() == null) {
            ptmEstadopatrimonial.setPtmEstadopatrimonialPK(new PtmEstadopatrimonialPK());
        }
        if (ptmEstadopatrimonial.getPtmPrestamoList() == null) {
            ptmEstadopatrimonial.setPtmPrestamoList(new ArrayList<PtmPrestamo>());
        }
        if (ptmEstadopatrimonial.getPtmBienesmueblesList() == null) {
            ptmEstadopatrimonial.setPtmBienesmueblesList(new ArrayList<PtmBienesmuebles>());
        }
        if (ptmEstadopatrimonial.getPtmBienesinmueblesList() == null) {
            ptmEstadopatrimonial.setPtmBienesinmueblesList(new ArrayList<PtmBienesinmuebles>());
        }
        if (ptmEstadopatrimonial.getPtmTarjetacreditoList() == null) {
            ptmEstadopatrimonial.setPtmTarjetacreditoList(new ArrayList<PtmTarjetacredito>());
        }
        ptmEstadopatrimonial.getPtmEstadopatrimonialPK().setColaboradorDpi(ptmEstadopatrimonial.getPtmColaborador().getDpi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmActivo ptmActivoIdactivo = ptmEstadopatrimonial.getPtmActivoIdactivo();
            if (ptmActivoIdactivo != null) {
                ptmActivoIdactivo = em.getReference(ptmActivoIdactivo.getClass(), ptmActivoIdactivo.getIdactivo());
                ptmEstadopatrimonial.setPtmActivoIdactivo(ptmActivoIdactivo);
            }
            PtmColaborador ptmColaborador = ptmEstadopatrimonial.getPtmColaborador();
            if (ptmColaborador != null) {
                ptmColaborador = em.getReference(ptmColaborador.getClass(), ptmColaborador.getDpi());
                ptmEstadopatrimonial.setPtmColaborador(ptmColaborador);
            }
            PtmPasivo ptmPasivoIdpasivo = ptmEstadopatrimonial.getPtmPasivoIdpasivo();
            if (ptmPasivoIdpasivo != null) {
                ptmPasivoIdpasivo = em.getReference(ptmPasivoIdpasivo.getClass(), ptmPasivoIdpasivo.getIdpasivo());
                ptmEstadopatrimonial.setPtmPasivoIdpasivo(ptmPasivoIdpasivo);
            }
            List<PtmPrestamo> attachedPtmPrestamoList = new ArrayList<PtmPrestamo>();
            for (PtmPrestamo ptmPrestamoListPtmPrestamoToAttach : ptmEstadopatrimonial.getPtmPrestamoList()) {
                ptmPrestamoListPtmPrestamoToAttach = em.getReference(ptmPrestamoListPtmPrestamoToAttach.getClass(), ptmPrestamoListPtmPrestamoToAttach.getIdprestamo());
                attachedPtmPrestamoList.add(ptmPrestamoListPtmPrestamoToAttach);
            }
            ptmEstadopatrimonial.setPtmPrestamoList(attachedPtmPrestamoList);
            List<PtmBienesmuebles> attachedPtmBienesmueblesList = new ArrayList<PtmBienesmuebles>();
            for (PtmBienesmuebles ptmBienesmueblesListPtmBienesmueblesToAttach : ptmEstadopatrimonial.getPtmBienesmueblesList()) {
                ptmBienesmueblesListPtmBienesmueblesToAttach = em.getReference(ptmBienesmueblesListPtmBienesmueblesToAttach.getClass(), ptmBienesmueblesListPtmBienesmueblesToAttach.getIdbienesmuebles());
                attachedPtmBienesmueblesList.add(ptmBienesmueblesListPtmBienesmueblesToAttach);
            }
            ptmEstadopatrimonial.setPtmBienesmueblesList(attachedPtmBienesmueblesList);
            List<PtmBienesinmuebles> attachedPtmBienesinmueblesList = new ArrayList<PtmBienesinmuebles>();
            for (PtmBienesinmuebles ptmBienesinmueblesListPtmBienesinmueblesToAttach : ptmEstadopatrimonial.getPtmBienesinmueblesList()) {
                ptmBienesinmueblesListPtmBienesinmueblesToAttach = em.getReference(ptmBienesinmueblesListPtmBienesinmueblesToAttach.getClass(), ptmBienesinmueblesListPtmBienesinmueblesToAttach.getIdbienesinmuebles());
                attachedPtmBienesinmueblesList.add(ptmBienesinmueblesListPtmBienesinmueblesToAttach);
            }
            ptmEstadopatrimonial.setPtmBienesinmueblesList(attachedPtmBienesinmueblesList);
            List<PtmTarjetacredito> attachedPtmTarjetacreditoList = new ArrayList<PtmTarjetacredito>();
            for (PtmTarjetacredito ptmTarjetacreditoListPtmTarjetacreditoToAttach : ptmEstadopatrimonial.getPtmTarjetacreditoList()) {
                ptmTarjetacreditoListPtmTarjetacreditoToAttach = em.getReference(ptmTarjetacreditoListPtmTarjetacreditoToAttach.getClass(), ptmTarjetacreditoListPtmTarjetacreditoToAttach.getIdtarjetacredito());
                attachedPtmTarjetacreditoList.add(ptmTarjetacreditoListPtmTarjetacreditoToAttach);
            }
            ptmEstadopatrimonial.setPtmTarjetacreditoList(attachedPtmTarjetacreditoList);
            em.persist(ptmEstadopatrimonial);
            if (ptmActivoIdactivo != null) {
                ptmActivoIdactivo.getPtmEstadopatrimonialList().add(ptmEstadopatrimonial);
                ptmActivoIdactivo = em.merge(ptmActivoIdactivo);
            }
            if (ptmColaborador != null) {
                ptmColaborador.getPtmEstadopatrimonialList().add(ptmEstadopatrimonial);
                ptmColaborador = em.merge(ptmColaborador);
            }
            if (ptmPasivoIdpasivo != null) {
                ptmPasivoIdpasivo.getPtmEstadopatrimonialList().add(ptmEstadopatrimonial);
                ptmPasivoIdpasivo = em.merge(ptmPasivoIdpasivo);
            }
            for (PtmPrestamo ptmPrestamoListPtmPrestamo : ptmEstadopatrimonial.getPtmPrestamoList()) {
                PtmEstadopatrimonial oldPtmEstadopatrimonialOfPtmPrestamoListPtmPrestamo = ptmPrestamoListPtmPrestamo.getPtmEstadopatrimonial();
                ptmPrestamoListPtmPrestamo.setPtmEstadopatrimonial(ptmEstadopatrimonial);
                ptmPrestamoListPtmPrestamo = em.merge(ptmPrestamoListPtmPrestamo);
                if (oldPtmEstadopatrimonialOfPtmPrestamoListPtmPrestamo != null) {
                    oldPtmEstadopatrimonialOfPtmPrestamoListPtmPrestamo.getPtmPrestamoList().remove(ptmPrestamoListPtmPrestamo);
                    oldPtmEstadopatrimonialOfPtmPrestamoListPtmPrestamo = em.merge(oldPtmEstadopatrimonialOfPtmPrestamoListPtmPrestamo);
                }
            }
            for (PtmBienesmuebles ptmBienesmueblesListPtmBienesmuebles : ptmEstadopatrimonial.getPtmBienesmueblesList()) {
                PtmEstadopatrimonial oldPtmEstadopatrimonialOfPtmBienesmueblesListPtmBienesmuebles = ptmBienesmueblesListPtmBienesmuebles.getPtmEstadopatrimonial();
                ptmBienesmueblesListPtmBienesmuebles.setPtmEstadopatrimonial(ptmEstadopatrimonial);
                ptmBienesmueblesListPtmBienesmuebles = em.merge(ptmBienesmueblesListPtmBienesmuebles);
                if (oldPtmEstadopatrimonialOfPtmBienesmueblesListPtmBienesmuebles != null) {
                    oldPtmEstadopatrimonialOfPtmBienesmueblesListPtmBienesmuebles.getPtmBienesmueblesList().remove(ptmBienesmueblesListPtmBienesmuebles);
                    oldPtmEstadopatrimonialOfPtmBienesmueblesListPtmBienesmuebles = em.merge(oldPtmEstadopatrimonialOfPtmBienesmueblesListPtmBienesmuebles);
                }
            }
            for (PtmBienesinmuebles ptmBienesinmueblesListPtmBienesinmuebles : ptmEstadopatrimonial.getPtmBienesinmueblesList()) {
                PtmEstadopatrimonial oldPtmEstadopatrimonialOfPtmBienesinmueblesListPtmBienesinmuebles = ptmBienesinmueblesListPtmBienesinmuebles.getPtmEstadopatrimonial();
                ptmBienesinmueblesListPtmBienesinmuebles.setPtmEstadopatrimonial(ptmEstadopatrimonial);
                ptmBienesinmueblesListPtmBienesinmuebles = em.merge(ptmBienesinmueblesListPtmBienesinmuebles);
                if (oldPtmEstadopatrimonialOfPtmBienesinmueblesListPtmBienesinmuebles != null) {
                    oldPtmEstadopatrimonialOfPtmBienesinmueblesListPtmBienesinmuebles.getPtmBienesinmueblesList().remove(ptmBienesinmueblesListPtmBienesinmuebles);
                    oldPtmEstadopatrimonialOfPtmBienesinmueblesListPtmBienesinmuebles = em.merge(oldPtmEstadopatrimonialOfPtmBienesinmueblesListPtmBienesinmuebles);
                }
            }
            for (PtmTarjetacredito ptmTarjetacreditoListPtmTarjetacredito : ptmEstadopatrimonial.getPtmTarjetacreditoList()) {
                PtmEstadopatrimonial oldPtmEstadopatrimonialOfPtmTarjetacreditoListPtmTarjetacredito = ptmTarjetacreditoListPtmTarjetacredito.getPtmEstadopatrimonial();
                ptmTarjetacreditoListPtmTarjetacredito.setPtmEstadopatrimonial(ptmEstadopatrimonial);
                ptmTarjetacreditoListPtmTarjetacredito = em.merge(ptmTarjetacreditoListPtmTarjetacredito);
                if (oldPtmEstadopatrimonialOfPtmTarjetacreditoListPtmTarjetacredito != null) {
                    oldPtmEstadopatrimonialOfPtmTarjetacreditoListPtmTarjetacredito.getPtmTarjetacreditoList().remove(ptmTarjetacreditoListPtmTarjetacredito);
                    oldPtmEstadopatrimonialOfPtmTarjetacreditoListPtmTarjetacredito = em.merge(oldPtmEstadopatrimonialOfPtmTarjetacreditoListPtmTarjetacredito);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPtmEstadopatrimonial(ptmEstadopatrimonial.getPtmEstadopatrimonialPK()) != null) {
                throw new PreexistingEntityException("PtmEstadopatrimonial " + ptmEstadopatrimonial + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmEstadopatrimonial ptmEstadopatrimonial) throws IllegalOrphanException, NonexistentEntityException, Exception {
        ptmEstadopatrimonial.getPtmEstadopatrimonialPK().setColaboradorDpi(ptmEstadopatrimonial.getPtmColaborador().getDpi());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmEstadopatrimonial persistentPtmEstadopatrimonial = em.find(PtmEstadopatrimonial.class, ptmEstadopatrimonial.getPtmEstadopatrimonialPK());
            PtmActivo ptmActivoIdactivoOld = persistentPtmEstadopatrimonial.getPtmActivoIdactivo();
            PtmActivo ptmActivoIdactivoNew = ptmEstadopatrimonial.getPtmActivoIdactivo();
            PtmColaborador ptmColaboradorOld = persistentPtmEstadopatrimonial.getPtmColaborador();
            PtmColaborador ptmColaboradorNew = ptmEstadopatrimonial.getPtmColaborador();
            PtmPasivo ptmPasivoIdpasivoOld = persistentPtmEstadopatrimonial.getPtmPasivoIdpasivo();
            PtmPasivo ptmPasivoIdpasivoNew = ptmEstadopatrimonial.getPtmPasivoIdpasivo();
            List<PtmPrestamo> ptmPrestamoListOld = persistentPtmEstadopatrimonial.getPtmPrestamoList();
            List<PtmPrestamo> ptmPrestamoListNew = ptmEstadopatrimonial.getPtmPrestamoList();
            List<PtmBienesmuebles> ptmBienesmueblesListOld = persistentPtmEstadopatrimonial.getPtmBienesmueblesList();
            List<PtmBienesmuebles> ptmBienesmueblesListNew = ptmEstadopatrimonial.getPtmBienesmueblesList();
            List<PtmBienesinmuebles> ptmBienesinmueblesListOld = persistentPtmEstadopatrimonial.getPtmBienesinmueblesList();
            List<PtmBienesinmuebles> ptmBienesinmueblesListNew = ptmEstadopatrimonial.getPtmBienesinmueblesList();
            List<PtmTarjetacredito> ptmTarjetacreditoListOld = persistentPtmEstadopatrimonial.getPtmTarjetacreditoList();
            List<PtmTarjetacredito> ptmTarjetacreditoListNew = ptmEstadopatrimonial.getPtmTarjetacreditoList();
            List<String> illegalOrphanMessages = null;
            for (PtmPrestamo ptmPrestamoListOldPtmPrestamo : ptmPrestamoListOld) {
                if (!ptmPrestamoListNew.contains(ptmPrestamoListOldPtmPrestamo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PtmPrestamo " + ptmPrestamoListOldPtmPrestamo + " since its ptmEstadopatrimonial field is not nullable.");
                }
            }
            for (PtmBienesmuebles ptmBienesmueblesListOldPtmBienesmuebles : ptmBienesmueblesListOld) {
                if (!ptmBienesmueblesListNew.contains(ptmBienesmueblesListOldPtmBienesmuebles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PtmBienesmuebles " + ptmBienesmueblesListOldPtmBienesmuebles + " since its ptmEstadopatrimonial field is not nullable.");
                }
            }
            for (PtmBienesinmuebles ptmBienesinmueblesListOldPtmBienesinmuebles : ptmBienesinmueblesListOld) {
                if (!ptmBienesinmueblesListNew.contains(ptmBienesinmueblesListOldPtmBienesinmuebles)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PtmBienesinmuebles " + ptmBienesinmueblesListOldPtmBienesinmuebles + " since its ptmEstadopatrimonial field is not nullable.");
                }
            }
            for (PtmTarjetacredito ptmTarjetacreditoListOldPtmTarjetacredito : ptmTarjetacreditoListOld) {
                if (!ptmTarjetacreditoListNew.contains(ptmTarjetacreditoListOldPtmTarjetacredito)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PtmTarjetacredito " + ptmTarjetacreditoListOldPtmTarjetacredito + " since its ptmEstadopatrimonial field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (ptmActivoIdactivoNew != null) {
                ptmActivoIdactivoNew = em.getReference(ptmActivoIdactivoNew.getClass(), ptmActivoIdactivoNew.getIdactivo());
                ptmEstadopatrimonial.setPtmActivoIdactivo(ptmActivoIdactivoNew);
            }
            if (ptmColaboradorNew != null) {
                ptmColaboradorNew = em.getReference(ptmColaboradorNew.getClass(), ptmColaboradorNew.getDpi());
                ptmEstadopatrimonial.setPtmColaborador(ptmColaboradorNew);
            }
            if (ptmPasivoIdpasivoNew != null) {
                ptmPasivoIdpasivoNew = em.getReference(ptmPasivoIdpasivoNew.getClass(), ptmPasivoIdpasivoNew.getIdpasivo());
                ptmEstadopatrimonial.setPtmPasivoIdpasivo(ptmPasivoIdpasivoNew);
            }
            List<PtmPrestamo> attachedPtmPrestamoListNew = new ArrayList<PtmPrestamo>();
            for (PtmPrestamo ptmPrestamoListNewPtmPrestamoToAttach : ptmPrestamoListNew) {
                ptmPrestamoListNewPtmPrestamoToAttach = em.getReference(ptmPrestamoListNewPtmPrestamoToAttach.getClass(), ptmPrestamoListNewPtmPrestamoToAttach.getIdprestamo());
                attachedPtmPrestamoListNew.add(ptmPrestamoListNewPtmPrestamoToAttach);
            }
            ptmPrestamoListNew = attachedPtmPrestamoListNew;
            ptmEstadopatrimonial.setPtmPrestamoList(ptmPrestamoListNew);
            List<PtmBienesmuebles> attachedPtmBienesmueblesListNew = new ArrayList<PtmBienesmuebles>();
            for (PtmBienesmuebles ptmBienesmueblesListNewPtmBienesmueblesToAttach : ptmBienesmueblesListNew) {
                ptmBienesmueblesListNewPtmBienesmueblesToAttach = em.getReference(ptmBienesmueblesListNewPtmBienesmueblesToAttach.getClass(), ptmBienesmueblesListNewPtmBienesmueblesToAttach.getIdbienesmuebles());
                attachedPtmBienesmueblesListNew.add(ptmBienesmueblesListNewPtmBienesmueblesToAttach);
            }
            ptmBienesmueblesListNew = attachedPtmBienesmueblesListNew;
            ptmEstadopatrimonial.setPtmBienesmueblesList(ptmBienesmueblesListNew);
            List<PtmBienesinmuebles> attachedPtmBienesinmueblesListNew = new ArrayList<PtmBienesinmuebles>();
            for (PtmBienesinmuebles ptmBienesinmueblesListNewPtmBienesinmueblesToAttach : ptmBienesinmueblesListNew) {
                ptmBienesinmueblesListNewPtmBienesinmueblesToAttach = em.getReference(ptmBienesinmueblesListNewPtmBienesinmueblesToAttach.getClass(), ptmBienesinmueblesListNewPtmBienesinmueblesToAttach.getIdbienesinmuebles());
                attachedPtmBienesinmueblesListNew.add(ptmBienesinmueblesListNewPtmBienesinmueblesToAttach);
            }
            ptmBienesinmueblesListNew = attachedPtmBienesinmueblesListNew;
            ptmEstadopatrimonial.setPtmBienesinmueblesList(ptmBienesinmueblesListNew);
            List<PtmTarjetacredito> attachedPtmTarjetacreditoListNew = new ArrayList<PtmTarjetacredito>();
            for (PtmTarjetacredito ptmTarjetacreditoListNewPtmTarjetacreditoToAttach : ptmTarjetacreditoListNew) {
                ptmTarjetacreditoListNewPtmTarjetacreditoToAttach = em.getReference(ptmTarjetacreditoListNewPtmTarjetacreditoToAttach.getClass(), ptmTarjetacreditoListNewPtmTarjetacreditoToAttach.getIdtarjetacredito());
                attachedPtmTarjetacreditoListNew.add(ptmTarjetacreditoListNewPtmTarjetacreditoToAttach);
            }
            ptmTarjetacreditoListNew = attachedPtmTarjetacreditoListNew;
            ptmEstadopatrimonial.setPtmTarjetacreditoList(ptmTarjetacreditoListNew);
            ptmEstadopatrimonial = em.merge(ptmEstadopatrimonial);
            if (ptmActivoIdactivoOld != null && !ptmActivoIdactivoOld.equals(ptmActivoIdactivoNew)) {
                ptmActivoIdactivoOld.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonial);
                ptmActivoIdactivoOld = em.merge(ptmActivoIdactivoOld);
            }
            if (ptmActivoIdactivoNew != null && !ptmActivoIdactivoNew.equals(ptmActivoIdactivoOld)) {
                ptmActivoIdactivoNew.getPtmEstadopatrimonialList().add(ptmEstadopatrimonial);
                ptmActivoIdactivoNew = em.merge(ptmActivoIdactivoNew);
            }
            if (ptmColaboradorOld != null && !ptmColaboradorOld.equals(ptmColaboradorNew)) {
                ptmColaboradorOld.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonial);
                ptmColaboradorOld = em.merge(ptmColaboradorOld);
            }
            if (ptmColaboradorNew != null && !ptmColaboradorNew.equals(ptmColaboradorOld)) {
                ptmColaboradorNew.getPtmEstadopatrimonialList().add(ptmEstadopatrimonial);
                ptmColaboradorNew = em.merge(ptmColaboradorNew);
            }
            if (ptmPasivoIdpasivoOld != null && !ptmPasivoIdpasivoOld.equals(ptmPasivoIdpasivoNew)) {
                ptmPasivoIdpasivoOld.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonial);
                ptmPasivoIdpasivoOld = em.merge(ptmPasivoIdpasivoOld);
            }
            if (ptmPasivoIdpasivoNew != null && !ptmPasivoIdpasivoNew.equals(ptmPasivoIdpasivoOld)) {
                ptmPasivoIdpasivoNew.getPtmEstadopatrimonialList().add(ptmEstadopatrimonial);
                ptmPasivoIdpasivoNew = em.merge(ptmPasivoIdpasivoNew);
            }
            for (PtmPrestamo ptmPrestamoListNewPtmPrestamo : ptmPrestamoListNew) {
                if (!ptmPrestamoListOld.contains(ptmPrestamoListNewPtmPrestamo)) {
                    PtmEstadopatrimonial oldPtmEstadopatrimonialOfPtmPrestamoListNewPtmPrestamo = ptmPrestamoListNewPtmPrestamo.getPtmEstadopatrimonial();
                    ptmPrestamoListNewPtmPrestamo.setPtmEstadopatrimonial(ptmEstadopatrimonial);
                    ptmPrestamoListNewPtmPrestamo = em.merge(ptmPrestamoListNewPtmPrestamo);
                    if (oldPtmEstadopatrimonialOfPtmPrestamoListNewPtmPrestamo != null && !oldPtmEstadopatrimonialOfPtmPrestamoListNewPtmPrestamo.equals(ptmEstadopatrimonial)) {
                        oldPtmEstadopatrimonialOfPtmPrestamoListNewPtmPrestamo.getPtmPrestamoList().remove(ptmPrestamoListNewPtmPrestamo);
                        oldPtmEstadopatrimonialOfPtmPrestamoListNewPtmPrestamo = em.merge(oldPtmEstadopatrimonialOfPtmPrestamoListNewPtmPrestamo);
                    }
                }
            }
            for (PtmBienesmuebles ptmBienesmueblesListNewPtmBienesmuebles : ptmBienesmueblesListNew) {
                if (!ptmBienesmueblesListOld.contains(ptmBienesmueblesListNewPtmBienesmuebles)) {
                    PtmEstadopatrimonial oldPtmEstadopatrimonialOfPtmBienesmueblesListNewPtmBienesmuebles = ptmBienesmueblesListNewPtmBienesmuebles.getPtmEstadopatrimonial();
                    ptmBienesmueblesListNewPtmBienesmuebles.setPtmEstadopatrimonial(ptmEstadopatrimonial);
                    ptmBienesmueblesListNewPtmBienesmuebles = em.merge(ptmBienesmueblesListNewPtmBienesmuebles);
                    if (oldPtmEstadopatrimonialOfPtmBienesmueblesListNewPtmBienesmuebles != null && !oldPtmEstadopatrimonialOfPtmBienesmueblesListNewPtmBienesmuebles.equals(ptmEstadopatrimonial)) {
                        oldPtmEstadopatrimonialOfPtmBienesmueblesListNewPtmBienesmuebles.getPtmBienesmueblesList().remove(ptmBienesmueblesListNewPtmBienesmuebles);
                        oldPtmEstadopatrimonialOfPtmBienesmueblesListNewPtmBienesmuebles = em.merge(oldPtmEstadopatrimonialOfPtmBienesmueblesListNewPtmBienesmuebles);
                    }
                }
            }
            for (PtmBienesinmuebles ptmBienesinmueblesListNewPtmBienesinmuebles : ptmBienesinmueblesListNew) {
                if (!ptmBienesinmueblesListOld.contains(ptmBienesinmueblesListNewPtmBienesinmuebles)) {
                    PtmEstadopatrimonial oldPtmEstadopatrimonialOfPtmBienesinmueblesListNewPtmBienesinmuebles = ptmBienesinmueblesListNewPtmBienesinmuebles.getPtmEstadopatrimonial();
                    ptmBienesinmueblesListNewPtmBienesinmuebles.setPtmEstadopatrimonial(ptmEstadopatrimonial);
                    ptmBienesinmueblesListNewPtmBienesinmuebles = em.merge(ptmBienesinmueblesListNewPtmBienesinmuebles);
                    if (oldPtmEstadopatrimonialOfPtmBienesinmueblesListNewPtmBienesinmuebles != null && !oldPtmEstadopatrimonialOfPtmBienesinmueblesListNewPtmBienesinmuebles.equals(ptmEstadopatrimonial)) {
                        oldPtmEstadopatrimonialOfPtmBienesinmueblesListNewPtmBienesinmuebles.getPtmBienesinmueblesList().remove(ptmBienesinmueblesListNewPtmBienesinmuebles);
                        oldPtmEstadopatrimonialOfPtmBienesinmueblesListNewPtmBienesinmuebles = em.merge(oldPtmEstadopatrimonialOfPtmBienesinmueblesListNewPtmBienesinmuebles);
                    }
                }
            }
            for (PtmTarjetacredito ptmTarjetacreditoListNewPtmTarjetacredito : ptmTarjetacreditoListNew) {
                if (!ptmTarjetacreditoListOld.contains(ptmTarjetacreditoListNewPtmTarjetacredito)) {
                    PtmEstadopatrimonial oldPtmEstadopatrimonialOfPtmTarjetacreditoListNewPtmTarjetacredito = ptmTarjetacreditoListNewPtmTarjetacredito.getPtmEstadopatrimonial();
                    ptmTarjetacreditoListNewPtmTarjetacredito.setPtmEstadopatrimonial(ptmEstadopatrimonial);
                    ptmTarjetacreditoListNewPtmTarjetacredito = em.merge(ptmTarjetacreditoListNewPtmTarjetacredito);
                    if (oldPtmEstadopatrimonialOfPtmTarjetacreditoListNewPtmTarjetacredito != null && !oldPtmEstadopatrimonialOfPtmTarjetacreditoListNewPtmTarjetacredito.equals(ptmEstadopatrimonial)) {
                        oldPtmEstadopatrimonialOfPtmTarjetacreditoListNewPtmTarjetacredito.getPtmTarjetacreditoList().remove(ptmTarjetacreditoListNewPtmTarjetacredito);
                        oldPtmEstadopatrimonialOfPtmTarjetacreditoListNewPtmTarjetacredito = em.merge(oldPtmEstadopatrimonialOfPtmTarjetacreditoListNewPtmTarjetacredito);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PtmEstadopatrimonialPK id = ptmEstadopatrimonial.getPtmEstadopatrimonialPK();
                if (findPtmEstadopatrimonial(id) == null) {
                    throw new NonexistentEntityException("The ptmEstadopatrimonial with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PtmEstadopatrimonialPK id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmEstadopatrimonial ptmEstadopatrimonial;
            try {
                ptmEstadopatrimonial = em.getReference(PtmEstadopatrimonial.class, id);
                ptmEstadopatrimonial.getPtmEstadopatrimonialPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmEstadopatrimonial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PtmPrestamo> ptmPrestamoListOrphanCheck = ptmEstadopatrimonial.getPtmPrestamoList();
            for (PtmPrestamo ptmPrestamoListOrphanCheckPtmPrestamo : ptmPrestamoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PtmEstadopatrimonial (" + ptmEstadopatrimonial + ") cannot be destroyed since the PtmPrestamo " + ptmPrestamoListOrphanCheckPtmPrestamo + " in its ptmPrestamoList field has a non-nullable ptmEstadopatrimonial field.");
            }
            List<PtmBienesmuebles> ptmBienesmueblesListOrphanCheck = ptmEstadopatrimonial.getPtmBienesmueblesList();
            for (PtmBienesmuebles ptmBienesmueblesListOrphanCheckPtmBienesmuebles : ptmBienesmueblesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PtmEstadopatrimonial (" + ptmEstadopatrimonial + ") cannot be destroyed since the PtmBienesmuebles " + ptmBienesmueblesListOrphanCheckPtmBienesmuebles + " in its ptmBienesmueblesList field has a non-nullable ptmEstadopatrimonial field.");
            }
            List<PtmBienesinmuebles> ptmBienesinmueblesListOrphanCheck = ptmEstadopatrimonial.getPtmBienesinmueblesList();
            for (PtmBienesinmuebles ptmBienesinmueblesListOrphanCheckPtmBienesinmuebles : ptmBienesinmueblesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PtmEstadopatrimonial (" + ptmEstadopatrimonial + ") cannot be destroyed since the PtmBienesinmuebles " + ptmBienesinmueblesListOrphanCheckPtmBienesinmuebles + " in its ptmBienesinmueblesList field has a non-nullable ptmEstadopatrimonial field.");
            }
            List<PtmTarjetacredito> ptmTarjetacreditoListOrphanCheck = ptmEstadopatrimonial.getPtmTarjetacreditoList();
            for (PtmTarjetacredito ptmTarjetacreditoListOrphanCheckPtmTarjetacredito : ptmTarjetacreditoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PtmEstadopatrimonial (" + ptmEstadopatrimonial + ") cannot be destroyed since the PtmTarjetacredito " + ptmTarjetacreditoListOrphanCheckPtmTarjetacredito + " in its ptmTarjetacreditoList field has a non-nullable ptmEstadopatrimonial field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            PtmActivo ptmActivoIdactivo = ptmEstadopatrimonial.getPtmActivoIdactivo();
            if (ptmActivoIdactivo != null) {
                ptmActivoIdactivo.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonial);
                ptmActivoIdactivo = em.merge(ptmActivoIdactivo);
            }
            PtmColaborador ptmColaborador = ptmEstadopatrimonial.getPtmColaborador();
            if (ptmColaborador != null) {
                ptmColaborador.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonial);
                ptmColaborador = em.merge(ptmColaborador);
            }
            PtmPasivo ptmPasivoIdpasivo = ptmEstadopatrimonial.getPtmPasivoIdpasivo();
            if (ptmPasivoIdpasivo != null) {
                ptmPasivoIdpasivo.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonial);
                ptmPasivoIdpasivo = em.merge(ptmPasivoIdpasivo);
            }
            em.remove(ptmEstadopatrimonial);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmEstadopatrimonial> findPtmEstadopatrimonialEntities() {
        return findPtmEstadopatrimonialEntities(true, -1, -1);
    }

    public List<PtmEstadopatrimonial> findPtmEstadopatrimonialEntities(int maxResults, int firstResult) {
        return findPtmEstadopatrimonialEntities(false, maxResults, firstResult);
    }

    private List<PtmEstadopatrimonial> findPtmEstadopatrimonialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmEstadopatrimonial.class));
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

    public PtmEstadopatrimonial findPtmEstadopatrimonial(PtmEstadopatrimonialPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmEstadopatrimonial.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmEstadopatrimonialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmEstadopatrimonial> rt = cq.from(PtmEstadopatrimonial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
