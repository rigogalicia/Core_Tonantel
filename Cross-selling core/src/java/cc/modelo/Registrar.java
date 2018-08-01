package cc.modelo;

import admin.modelo.Colaborador;
import dao.CcBanco;
import dao.CcCheque;
import gc.controlador.Correo;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

public class Registrar {
    private CcCheque cheque = new CcCheque();
    private CcBanco banco = new CcBanco();
    private String userConect = null;
    
    public Registrar(){
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
        }
    }

    public CcCheque getCheque() {
        return cheque;
    }

    public void setCheque(CcCheque cheque) {
        this.cheque = cheque;
    }

    public CcBanco getBanco() {
        return banco;
    }

    public void setBanco(CcBanco banco) {
        this.banco = banco;
    }
    
    /* Metodo para validar fecha */
    private Date fechaExacta(Date f){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(f);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        
        return calendar.getTime();
    }

    /* Metodo utilizado para insertar un nuevo registro */
    public void insertar(){
        banco.setDescripcion(descripcionBanco(banco.getId()));
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try{
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            cheque.setFecha(fechaExacta(cheque.getFecha()));
            cheque.setUsuario(userConect);
            cheque.setAgencia(Colaborador.datosColaborador(userConect).getAgencia());
            cheque.setBancoId(banco);
            em.persist(cheque);
            em.getTransaction().commit();
            enviarCorreos();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/cc/cc_registrados.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
        finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        }
    }
    
    /* Metodo utilizado para consultar los registros activos de bancos */
    public List<CcBanco> bancos(){
        List<CcBanco> result = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT c "
                + "FROM CcBanco c "
                + "WHERE c.estado = :est";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("est", 'a');
        result = consulta.getResultList();
        
        em.close();
        emf.close();
        return result;
    }
    
    /* Metodo utilizado para consultar los datos del banco por id */
    private String descripcionBanco(int idB){
        String result = "";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT c "
                + "FROM CcBanco c "
                + "WHERE c.id = :idBanco";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("idBanco", idB);
        
        List<CcBanco> resultado = consulta.getResultList();
        for(CcBanco b : resultado){
            result = b.getDescripcion();
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
    /* Metodo para enviar los correos de notificacion */
    private void enviarCorreos(){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        DecimalFormat formatoDecimal = new DecimalFormat("0,000.00");
        
        String mensaje = "Se ha girado un cheque en "+Colaborador.agenciaColaborador(userConect)+" \n\n"
                + "Número: "+cheque.getNumero()+" \n"
                + "Cuenta Bancaria: "+cheque.getCuentaBancaria()+" \n"
                + "Monto: "+formatoDecimal.format(cheque.getMonto())+" \n"
                + "Fecha de emisión: "+formatoFecha.format(cheque.getFecha())+" \n"
                + "Beneficiario: "+cheque.getBeneficiario()+" \n"
                + "Motivo: "+cheque.getMotivo()+" \n"
                + "Banco: "+banco.getDescripcion()+"\n\n"
                + "Transacción realizada por: "+Colaborador.datosColaborador(userConect).getNombre()+"";
        
        Autorizadores autorizadores = new Autorizadores();
        for(Autorizadores a : autorizadores.mostrarAutorizadores(Colaborador.datosColaborador(userConect).getAgencia())){
            Correo correo = new Correo(a.getUsuario(), "Emisión de cheques", mensaje);
            correo.enviar();
        }
    }
}