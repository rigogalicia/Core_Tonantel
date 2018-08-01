package cc.modelo;

import dao.CcBanco;
import dao.CcCheque;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Registrados {
    private String numero;
    private String cuenta;
    private String monto;
    private String fecha;
    private String beneficiario;
    private String motivo;
    private String banco;
    private String usuario;
    
    public Registrados(){}
    
    public Registrados(String user){
        this.usuario = user;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
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

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
    
    /* Metodo que devuelve los cheques registrados por usuario */
    public ArrayList<Registrados> chequesRegistrados(){
        ArrayList<Registrados> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT c, b "
                + "FROM CcCheque c "
                + "JOIN c.bancoId b "
                + "WHERE c.usuario = :userConect "
                + "ORDER BY c.fecha DESC";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("userConect", usuario);
        List<Object[]> resultado = consulta.getResultList();
        
        DecimalFormat formatoDecimal = new DecimalFormat("0,000.00");
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        
        for(Object[] obj : resultado){
            CcCheque c = (CcCheque) obj[0];
            CcBanco b = (CcBanco) obj[1];
            
            Registrados r = new Registrados();
            r.setNumero(c.getNumero());
            r.setCuenta(c.getCuentaBancaria());
            r.setMonto(formatoDecimal.format(c.getMonto()));
            r.setFecha(formatoFecha.format(c.getFecha()));
            r.setBeneficiario(c.getBeneficiario());
            r.setMotivo(c.getMotivo());
            r.setBanco(b.getDescripcion());
            
            result.add(r);
        }
        
        em.close();
        emf.close();
        
        return result;
    }
}
