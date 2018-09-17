package gc.modelo;

import admin.modelo.Colaborador;
import dao.GcAsociado;
import dao.GcDestino;
import dao.GcSolicitud;
import dao.GcTipo;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Negociacion {
    private String numeroSolicitud;
    private String cif;
    private String nombre;
    private String tipo;
    private String destino;
    private String monto;
    private String fecha;
    private String asesorFinanciero;
    private char estado;
    private String idAgencia;

    public String getNumeroSolicitud() {
        return numeroSolicitud;
    }

    public void setNumeroSolicitud(String numeroSolicitud) {
        this.numeroSolicitud = numeroSolicitud;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getAsesorFinanciero() {
        return asesorFinanciero;
    }

    public void setAsesorFinanciero(String asesorFinanciero) {
        this.asesorFinanciero = asesorFinanciero;
    }

    public char getEstado() {
        return estado;
    }

    public void setEstado(char estado) {
        this.estado = estado;
    }

    public String getIdAgencia() {
        return idAgencia;
    }

    public void setIdAgencia(String idAgencia) {
        this.idAgencia = idAgencia;
    }
    
    /* 
        Metodo para consultar los datos del credito en negociacion
        dependiendo el tipo de credito
    */
    public ArrayList<Negociacion> consultarDatos(){
        ArrayList<Negociacion> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT s, a, t, d "
                + "FROM GcSolicitud s "
                + "JOIN s.asociadoCif a "
                + "JOIN s.tipoId t "
                + "JOIN s.destinoId d "
                + "WHERE s.est = :est ";
        
        if(idAgencia != null){
            instruccion += "AND s.idAgencia = '"+idAgencia+"'";
        }
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("est", estado);
        
        List<Object[]> resultado = consulta.getResultList();
        
        DecimalFormat formatoDecimal = new DecimalFormat("0,000.00");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        resultado.stream().map((obj) -> {
            GcSolicitud s = (GcSolicitud) obj[0];
            GcAsociado a = (GcAsociado) obj[1];
            GcTipo t = (GcTipo) obj[2];
            GcDestino d = (GcDestino) obj[3];
            Negociacion n = new Negociacion();
            n.setNumeroSolicitud(s.getNumeroSolicitud());
            n.setCif(a.getCif());
            n.setNombre(a.getNombre());
            n.setTipo(t.getDescripcion());
            n.setDestino(d.getDescripcion());
            n.setMonto(formatoDecimal.format(s.getMonto()));
            n.setFecha(formatoFecha.format(s.getFecha()));
            n.setAsesorFinanciero(Colaborador.datosColaborador(s.getAsesorFinanciero()).getNombre());
            return n;            
        }).forEachOrdered((n) -> {
            result.add(n);
        });
        
        em.close();
        emf.close();
        
        return result;
    }
}
