/*
 * Clase utilizada para gestionar la informacion del estado patrimonial
 */
package patrimonio.modelo;

import dao.PtmActivo;
import dao.PtmBienesinmuebles;
import dao.PtmBienesmuebles;
import dao.PtmColaborador;
import dao.PtmEstadopatrimonial;
import dao.PtmEstadopatrimonialPK;
import dao.PtmPasivo;
import dao.PtmPrestamo;
import dao.PtmTarjetacredito;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EstadoPatrimonial {
    private int anio;
    private String dpi;
    private String usuario;
    private PtmColaborador colaborador;
    private PtmActivo activo;
    private PtmPasivo pasivo;
    private PtmEstadopatrimonial estadoP;

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public PtmColaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(PtmColaborador colaborador) {
        this.colaborador = colaborador;
    }

    public PtmActivo getActivo() {
        return activo;
    }

    public void setActivo(PtmActivo activo) {
        this.activo = activo;
    }

    public PtmPasivo getPasivo() {
        return pasivo;
    }

    public void setPasivo(PtmPasivo pasivo) {
        this.pasivo = pasivo;
    }

    public PtmEstadopatrimonial getEstadoP() {
        return estadoP;
    }

    public void setEstadoP(PtmEstadopatrimonial estadoP) {
        this.estadoP = estadoP;
    }
    
    public boolean consultarDatos(){
        boolean result = false;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();

        PtmEstadopatrimonialPK estadopk = new PtmEstadopatrimonialPK(anio, dpi);        
        String instruccion = "SELECT c, a, p, e "
                + "FROM PtmEstadopatrimonial e "
                + "JOIN e.ptmColaborador c "
                + "JOIN e.ptmActivoIdactivo a "
                + "JOIN e.ptmPasivoIdpasivo p "
                + "WHERE e.usuario = :userConect "
                + "AND e.ptmEstadopatrimonialPK = :estadoActual";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estadoActual", estadopk);
        consulta.setParameter("userConect", usuario);
        
        List<Object[]> resultado = consulta.getResultList();
        for(Object[] objeto : resultado){
            colaborador = (PtmColaborador) objeto[0];
            activo = (PtmActivo) objeto[1];
            pasivo = (PtmPasivo) objeto[2];
            estadoP = (PtmEstadopatrimonial) objeto[3];
            result = true;
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
    /* Metodo utilizado para consultar los datos del colaborador */
    public boolean consultarColaborador(){
        boolean result = false;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT c, e "
                + "FROM PtmEstadopatrimonial e "
                + "JOIN e.ptmColaborador c "
                + "WHERE e.usuario = :userConect "
                + "AND c.dpi = :dpiBuscar";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("userConect", usuario);
        consulta.setParameter("dpiBuscar", dpi);
        
        List<Object[]> resultado = consulta.getResultList();
        for(Object[] objeto : resultado){
            colaborador = (PtmColaborador) objeto[0];
            estadoP = (PtmEstadopatrimonial) objeto[1];
            result = true;
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
    /* Metodo utilizado para obtener el listado de bienes muebles */
    public ArrayList<PtmBienesmuebles> listaBienesMuebles(){
        ArrayList<PtmBienesmuebles> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT b "
                + "FROM PtmBienesmuebles b "
                + "WHERE b.ptmEstadopatrimonial = :estadoP "
                + "ORDER BY b.idbienesmuebles desc";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estadoP", new PtmEstadopatrimonial(new PtmEstadopatrimonialPK(anio, dpi)));
        List<PtmBienesmuebles> resultado = consulta.getResultList();
        for(PtmBienesmuebles b : resultado){
            result.add(b);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
    /* Metodo utilizado para obtener el listado de bienes inmuebles */
    public ArrayList<PtmBienesinmuebles> listaBienesInmuebles(){
        ArrayList<PtmBienesinmuebles> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT b "
                + "FROM PtmBienesinmuebles b "
                + "WHERE b.ptmEstadopatrimonial = :estadoP "
                + "ORDER BY b.idbienesinmuebles desc";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estadoP", new PtmEstadopatrimonial(new PtmEstadopatrimonialPK(anio, dpi)));
        List<PtmBienesinmuebles> resultado = consulta.getResultList();
        for(PtmBienesinmuebles b : resultado){
            result.add(b);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
    /* Metodo utilizado para obtener el listado de prestamos */
    public ArrayList<PtmPrestamo> listaPrestamos(){
        ArrayList<PtmPrestamo> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT p "
                + "FROM PtmPrestamo p "
                + "WHERE p.ptmEstadopatrimonial = :estadoP "
                + "ORDER BY p.idprestamo desc";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estadoP", new PtmEstadopatrimonial(new PtmEstadopatrimonialPK(anio, dpi)));
        List<PtmPrestamo> resultado = consulta.getResultList();
        for(PtmPrestamo p : resultado){
            result.add(p);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
    /* Metodo utilizado para obtener el listado de tarjetas */
    public ArrayList<PtmTarjetacredito> listaTarjetas(){
        ArrayList<PtmTarjetacredito> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT t "
                + "FROM PtmTarjetacredito t "
                + "WHERE t.ptmEstadopatrimonial = :estadoP "
                + "ORDER BY t.idtarjetacredito desc";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("estadoP", new PtmEstadopatrimonial(new PtmEstadopatrimonialPK(anio, dpi)));
        List<PtmTarjetacredito> resultado = consulta.getResultList();
        for(PtmTarjetacredito t : resultado){
            result.add(t);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
}
