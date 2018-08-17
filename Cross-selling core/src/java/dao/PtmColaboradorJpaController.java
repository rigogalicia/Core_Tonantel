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
public class PtmColaboradorJpaController implements Serializable {

    public PtmColaboradorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PtmColaborador ptmColaborador) throws PreexistingEntityException, Exception {
        if (ptmColaborador.getPtmCursoList() == null) {
            ptmColaborador.setPtmCursoList(new ArrayList<PtmCurso>());
        }
        if (ptmColaborador.getPtmHijoList() == null) {
            ptmColaborador.setPtmHijoList(new ArrayList<PtmHijo>());
        }
        if (ptmColaborador.getPtmEstadopatrimonialList() == null) {
            ptmColaborador.setPtmEstadopatrimonialList(new ArrayList<PtmEstadopatrimonial>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<PtmCurso> attachedPtmCursoList = new ArrayList<PtmCurso>();
            for (PtmCurso ptmCursoListPtmCursoToAttach : ptmColaborador.getPtmCursoList()) {
                ptmCursoListPtmCursoToAttach = em.getReference(ptmCursoListPtmCursoToAttach.getClass(), ptmCursoListPtmCursoToAttach.getIdcurso());
                attachedPtmCursoList.add(ptmCursoListPtmCursoToAttach);
            }
            ptmColaborador.setPtmCursoList(attachedPtmCursoList);
            List<PtmHijo> attachedPtmHijoList = new ArrayList<PtmHijo>();
            for (PtmHijo ptmHijoListPtmHijoToAttach : ptmColaborador.getPtmHijoList()) {
                ptmHijoListPtmHijoToAttach = em.getReference(ptmHijoListPtmHijoToAttach.getClass(), ptmHijoListPtmHijoToAttach.getIdhijo());
                attachedPtmHijoList.add(ptmHijoListPtmHijoToAttach);
            }
            ptmColaborador.setPtmHijoList(attachedPtmHijoList);
            List<PtmEstadopatrimonial> attachedPtmEstadopatrimonialList = new ArrayList<PtmEstadopatrimonial>();
            for (PtmEstadopatrimonial ptmEstadopatrimonialListPtmEstadopatrimonialToAttach : ptmColaborador.getPtmEstadopatrimonialList()) {
                ptmEstadopatrimonialListPtmEstadopatrimonialToAttach = em.getReference(ptmEstadopatrimonialListPtmEstadopatrimonialToAttach.getClass(), ptmEstadopatrimonialListPtmEstadopatrimonialToAttach.getPtmEstadopatrimonialPK());
                attachedPtmEstadopatrimonialList.add(ptmEstadopatrimonialListPtmEstadopatrimonialToAttach);
            }
            ptmColaborador.setPtmEstadopatrimonialList(attachedPtmEstadopatrimonialList);
            em.persist(ptmColaborador);
            for (PtmCurso ptmCursoListPtmCurso : ptmColaborador.getPtmCursoList()) {
                PtmColaborador oldPtmColaboradorDpiOfPtmCursoListPtmCurso = ptmCursoListPtmCurso.getPtmColaboradorDpi();
                ptmCursoListPtmCurso.setPtmColaboradorDpi(ptmColaborador);
                ptmCursoListPtmCurso = em.merge(ptmCursoListPtmCurso);
                if (oldPtmColaboradorDpiOfPtmCursoListPtmCurso != null) {
                    oldPtmColaboradorDpiOfPtmCursoListPtmCurso.getPtmCursoList().remove(ptmCursoListPtmCurso);
                    oldPtmColaboradorDpiOfPtmCursoListPtmCurso = em.merge(oldPtmColaboradorDpiOfPtmCursoListPtmCurso);
                }
            }
            for (PtmHijo ptmHijoListPtmHijo : ptmColaborador.getPtmHijoList()) {
                PtmColaborador oldPtmColaboradorDpiOfPtmHijoListPtmHijo = ptmHijoListPtmHijo.getPtmColaboradorDpi();
                ptmHijoListPtmHijo.setPtmColaboradorDpi(ptmColaborador);
                ptmHijoListPtmHijo = em.merge(ptmHijoListPtmHijo);
                if (oldPtmColaboradorDpiOfPtmHijoListPtmHijo != null) {
                    oldPtmColaboradorDpiOfPtmHijoListPtmHijo.getPtmHijoList().remove(ptmHijoListPtmHijo);
                    oldPtmColaboradorDpiOfPtmHijoListPtmHijo = em.merge(oldPtmColaboradorDpiOfPtmHijoListPtmHijo);
                }
            }
            for (PtmEstadopatrimonial ptmEstadopatrimonialListPtmEstadopatrimonial : ptmColaborador.getPtmEstadopatrimonialList()) {
                PtmColaborador oldPtmColaboradorOfPtmEstadopatrimonialListPtmEstadopatrimonial = ptmEstadopatrimonialListPtmEstadopatrimonial.getPtmColaborador();
                ptmEstadopatrimonialListPtmEstadopatrimonial.setPtmColaborador(ptmColaborador);
                ptmEstadopatrimonialListPtmEstadopatrimonial = em.merge(ptmEstadopatrimonialListPtmEstadopatrimonial);
                if (oldPtmColaboradorOfPtmEstadopatrimonialListPtmEstadopatrimonial != null) {
                    oldPtmColaboradorOfPtmEstadopatrimonialListPtmEstadopatrimonial.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonialListPtmEstadopatrimonial);
                    oldPtmColaboradorOfPtmEstadopatrimonialListPtmEstadopatrimonial = em.merge(oldPtmColaboradorOfPtmEstadopatrimonialListPtmEstadopatrimonial);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPtmColaborador(ptmColaborador.getDpi()) != null) {
                throw new PreexistingEntityException("PtmColaborador " + ptmColaborador + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PtmColaborador ptmColaborador) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            PtmColaborador persistentPtmColaborador = em.find(PtmColaborador.class, ptmColaborador.getDpi());
            List<PtmCurso> ptmCursoListOld = persistentPtmColaborador.getPtmCursoList();
            List<PtmCurso> ptmCursoListNew = ptmColaborador.getPtmCursoList();
            List<PtmHijo> ptmHijoListOld = persistentPtmColaborador.getPtmHijoList();
            List<PtmHijo> ptmHijoListNew = ptmColaborador.getPtmHijoList();
            List<PtmEstadopatrimonial> ptmEstadopatrimonialListOld = persistentPtmColaborador.getPtmEstadopatrimonialList();
            List<PtmEstadopatrimonial> ptmEstadopatrimonialListNew = ptmColaborador.getPtmEstadopatrimonialList();
            List<String> illegalOrphanMessages = null;
            for (PtmCurso ptmCursoListOldPtmCurso : ptmCursoListOld) {
                if (!ptmCursoListNew.contains(ptmCursoListOldPtmCurso)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PtmCurso " + ptmCursoListOldPtmCurso + " since its ptmColaboradorDpi field is not nullable.");
                }
            }
            for (PtmHijo ptmHijoListOldPtmHijo : ptmHijoListOld) {
                if (!ptmHijoListNew.contains(ptmHijoListOldPtmHijo)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PtmHijo " + ptmHijoListOldPtmHijo + " since its ptmColaboradorDpi field is not nullable.");
                }
            }
            for (PtmEstadopatrimonial ptmEstadopatrimonialListOldPtmEstadopatrimonial : ptmEstadopatrimonialListOld) {
                if (!ptmEstadopatrimonialListNew.contains(ptmEstadopatrimonialListOldPtmEstadopatrimonial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain PtmEstadopatrimonial " + ptmEstadopatrimonialListOldPtmEstadopatrimonial + " since its ptmColaborador field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<PtmCurso> attachedPtmCursoListNew = new ArrayList<PtmCurso>();
            for (PtmCurso ptmCursoListNewPtmCursoToAttach : ptmCursoListNew) {
                ptmCursoListNewPtmCursoToAttach = em.getReference(ptmCursoListNewPtmCursoToAttach.getClass(), ptmCursoListNewPtmCursoToAttach.getIdcurso());
                attachedPtmCursoListNew.add(ptmCursoListNewPtmCursoToAttach);
            }
            ptmCursoListNew = attachedPtmCursoListNew;
            ptmColaborador.setPtmCursoList(ptmCursoListNew);
            List<PtmHijo> attachedPtmHijoListNew = new ArrayList<PtmHijo>();
            for (PtmHijo ptmHijoListNewPtmHijoToAttach : ptmHijoListNew) {
                ptmHijoListNewPtmHijoToAttach = em.getReference(ptmHijoListNewPtmHijoToAttach.getClass(), ptmHijoListNewPtmHijoToAttach.getIdhijo());
                attachedPtmHijoListNew.add(ptmHijoListNewPtmHijoToAttach);
            }
            ptmHijoListNew = attachedPtmHijoListNew;
            ptmColaborador.setPtmHijoList(ptmHijoListNew);
            List<PtmEstadopatrimonial> attachedPtmEstadopatrimonialListNew = new ArrayList<PtmEstadopatrimonial>();
            for (PtmEstadopatrimonial ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach : ptmEstadopatrimonialListNew) {
                ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach = em.getReference(ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach.getClass(), ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach.getPtmEstadopatrimonialPK());
                attachedPtmEstadopatrimonialListNew.add(ptmEstadopatrimonialListNewPtmEstadopatrimonialToAttach);
            }
            ptmEstadopatrimonialListNew = attachedPtmEstadopatrimonialListNew;
            ptmColaborador.setPtmEstadopatrimonialList(ptmEstadopatrimonialListNew);
            ptmColaborador = em.merge(ptmColaborador);
            for (PtmCurso ptmCursoListNewPtmCurso : ptmCursoListNew) {
                if (!ptmCursoListOld.contains(ptmCursoListNewPtmCurso)) {
                    PtmColaborador oldPtmColaboradorDpiOfPtmCursoListNewPtmCurso = ptmCursoListNewPtmCurso.getPtmColaboradorDpi();
                    ptmCursoListNewPtmCurso.setPtmColaboradorDpi(ptmColaborador);
                    ptmCursoListNewPtmCurso = em.merge(ptmCursoListNewPtmCurso);
                    if (oldPtmColaboradorDpiOfPtmCursoListNewPtmCurso != null && !oldPtmColaboradorDpiOfPtmCursoListNewPtmCurso.equals(ptmColaborador)) {
                        oldPtmColaboradorDpiOfPtmCursoListNewPtmCurso.getPtmCursoList().remove(ptmCursoListNewPtmCurso);
                        oldPtmColaboradorDpiOfPtmCursoListNewPtmCurso = em.merge(oldPtmColaboradorDpiOfPtmCursoListNewPtmCurso);
                    }
                }
            }
            for (PtmHijo ptmHijoListNewPtmHijo : ptmHijoListNew) {
                if (!ptmHijoListOld.contains(ptmHijoListNewPtmHijo)) {
                    PtmColaborador oldPtmColaboradorDpiOfPtmHijoListNewPtmHijo = ptmHijoListNewPtmHijo.getPtmColaboradorDpi();
                    ptmHijoListNewPtmHijo.setPtmColaboradorDpi(ptmColaborador);
                    ptmHijoListNewPtmHijo = em.merge(ptmHijoListNewPtmHijo);
                    if (oldPtmColaboradorDpiOfPtmHijoListNewPtmHijo != null && !oldPtmColaboradorDpiOfPtmHijoListNewPtmHijo.equals(ptmColaborador)) {
                        oldPtmColaboradorDpiOfPtmHijoListNewPtmHijo.getPtmHijoList().remove(ptmHijoListNewPtmHijo);
                        oldPtmColaboradorDpiOfPtmHijoListNewPtmHijo = em.merge(oldPtmColaboradorDpiOfPtmHijoListNewPtmHijo);
                    }
                }
            }
            for (PtmEstadopatrimonial ptmEstadopatrimonialListNewPtmEstadopatrimonial : ptmEstadopatrimonialListNew) {
                if (!ptmEstadopatrimonialListOld.contains(ptmEstadopatrimonialListNewPtmEstadopatrimonial)) {
                    PtmColaborador oldPtmColaboradorOfPtmEstadopatrimonialListNewPtmEstadopatrimonial = ptmEstadopatrimonialListNewPtmEstadopatrimonial.getPtmColaborador();
                    ptmEstadopatrimonialListNewPtmEstadopatrimonial.setPtmColaborador(ptmColaborador);
                    ptmEstadopatrimonialListNewPtmEstadopatrimonial = em.merge(ptmEstadopatrimonialListNewPtmEstadopatrimonial);
                    if (oldPtmColaboradorOfPtmEstadopatrimonialListNewPtmEstadopatrimonial != null && !oldPtmColaboradorOfPtmEstadopatrimonialListNewPtmEstadopatrimonial.equals(ptmColaborador)) {
                        oldPtmColaboradorOfPtmEstadopatrimonialListNewPtmEstadopatrimonial.getPtmEstadopatrimonialList().remove(ptmEstadopatrimonialListNewPtmEstadopatrimonial);
                        oldPtmColaboradorOfPtmEstadopatrimonialListNewPtmEstadopatrimonial = em.merge(oldPtmColaboradorOfPtmEstadopatrimonialListNewPtmEstadopatrimonial);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = ptmColaborador.getDpi();
                if (findPtmColaborador(id) == null) {
                    throw new NonexistentEntityException("The ptmColaborador with id " + id + " no longer exists.");
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
            PtmColaborador ptmColaborador;
            try {
                ptmColaborador = em.getReference(PtmColaborador.class, id);
                ptmColaborador.getDpi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ptmColaborador with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<PtmCurso> ptmCursoListOrphanCheck = ptmColaborador.getPtmCursoList();
            for (PtmCurso ptmCursoListOrphanCheckPtmCurso : ptmCursoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PtmColaborador (" + ptmColaborador + ") cannot be destroyed since the PtmCurso " + ptmCursoListOrphanCheckPtmCurso + " in its ptmCursoList field has a non-nullable ptmColaboradorDpi field.");
            }
            List<PtmHijo> ptmHijoListOrphanCheck = ptmColaborador.getPtmHijoList();
            for (PtmHijo ptmHijoListOrphanCheckPtmHijo : ptmHijoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PtmColaborador (" + ptmColaborador + ") cannot be destroyed since the PtmHijo " + ptmHijoListOrphanCheckPtmHijo + " in its ptmHijoList field has a non-nullable ptmColaboradorDpi field.");
            }
            List<PtmEstadopatrimonial> ptmEstadopatrimonialListOrphanCheck = ptmColaborador.getPtmEstadopatrimonialList();
            for (PtmEstadopatrimonial ptmEstadopatrimonialListOrphanCheckPtmEstadopatrimonial : ptmEstadopatrimonialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This PtmColaborador (" + ptmColaborador + ") cannot be destroyed since the PtmEstadopatrimonial " + ptmEstadopatrimonialListOrphanCheckPtmEstadopatrimonial + " in its ptmEstadopatrimonialList field has a non-nullable ptmColaborador field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(ptmColaborador);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PtmColaborador> findPtmColaboradorEntities() {
        return findPtmColaboradorEntities(true, -1, -1);
    }

    public List<PtmColaborador> findPtmColaboradorEntities(int maxResults, int firstResult) {
        return findPtmColaboradorEntities(false, maxResults, firstResult);
    }

    private List<PtmColaborador> findPtmColaboradorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PtmColaborador.class));
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

    public PtmColaborador findPtmColaborador(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PtmColaborador.class, id);
        } finally {
            em.close();
        }
    }

    public int getPtmColaboradorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PtmColaborador> rt = cq.from(PtmColaborador.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
