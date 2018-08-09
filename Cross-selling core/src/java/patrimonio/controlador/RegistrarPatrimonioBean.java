package patrimonio.controlador;

import admin.modelo.Departamento;
import dao.PtmActivo;
import dao.PtmBienesinmuebles;
import dao.PtmBienesmuebles;
import dao.PtmColaborador;
import dao.PtmEstadopatrimonial;
import dao.PtmEstadopatrimonialPK;
import dao.PtmPasivo;
import dao.PtmPrestamo;
import dao.PtmTarjetacredito;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import patrimonio.modelo.ConexionMySql;
import patrimonio.modelo.EstadoPatrimonial;
import patrimonio.modelo.Registrar;

@ManagedBean(name = "registrar")
@ViewScoped
public class RegistrarPatrimonioBean {
    private String userConect;
    private List<SelectItem> listaAnio = new ArrayList<>();
    private List<SelectItem> listaDepartamentos = new ArrayList<>();
    private PtmColaborador colaborador = new PtmColaborador();
    private PtmActivo activo = new PtmActivo();
    private PtmPasivo pasivo = new PtmPasivo();
    private PtmEstadopatrimonialPK estadoPatrimonialPk = new PtmEstadopatrimonialPK();
    private PtmEstadopatrimonial estadoPatrimonial = new PtmEstadopatrimonial();
    private PtmBienesmuebles bienesMuebles = new PtmBienesmuebles();
    private ArrayList<SelectItem> listaTipoBienMueble = new ArrayList<>();
    private ArrayList<PtmBienesmuebles> listaBienesmuebles = new ArrayList<>();
    private PtmBienesinmuebles bienesInmuebles = new PtmBienesinmuebles();
    private ArrayList<PtmBienesinmuebles> listaBienesInmuebles = new ArrayList<>();
    private PtmPrestamo prestamo = new PtmPrestamo();
    private ArrayList<PtmPrestamo> listaPrestamo = new ArrayList<>();
    private PtmTarjetacredito tarjeta = new PtmTarjetacredito();
    private ArrayList<PtmTarjetacredito> listaTarjeta = new ArrayList<>();
    
    /* Metodo constructor de la clase y valida que la sesion este activa */
    public RegistrarPatrimonioBean() {
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
        }
        else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml");
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    /* Metodo para mostrar el listado de anios en el formulario */
    public List<SelectItem> getListaAnio() {
        listaAnio.clear();
        for(int i = 2010; i <= 2030; i++){
            SelectItem anioItem = new SelectItem(i, Integer.toString(i));
            listaAnio.add(anioItem);
        }
        return listaAnio;
    }

    public void setListaAnio(List<SelectItem> listaAnio) {
        this.listaAnio = listaAnio;
    }

    public List<SelectItem> getListaDepartamentos() {
        listaDepartamentos.clear();
        Departamento dep = new Departamento();
        dep.mostrarDepartamentos().stream().map((d) -> new SelectItem(d.getNombre(), d.getNombre())).forEach((itemDepartamento) -> {
            listaDepartamentos.add(itemDepartamento);
        });
        return listaDepartamentos;
    }

    public void setListaDepartamentos(List<SelectItem> listaDepartamentos) {
        this.listaDepartamentos = listaDepartamentos;
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

    public PtmEstadopatrimonialPK getEstadoPatrimonialPk() {
        return estadoPatrimonialPk;
    }

    public void setEstadoPatrimonialPk(PtmEstadopatrimonialPK estadoPatrimonialPk) {
        this.estadoPatrimonialPk = estadoPatrimonialPk;
    }

    public PtmEstadopatrimonial getEstadoPatrimonial() {
        return estadoPatrimonial;
    }

    public void setEstadoPatrimonial(PtmEstadopatrimonial estadoPatrimonial) {
        this.estadoPatrimonial = estadoPatrimonial;
    }

    public PtmBienesmuebles getBienesMuebles() {
        return bienesMuebles;
    }

    public void setBienesMuebles(PtmBienesmuebles bienesMuebles) {
        this.bienesMuebles = bienesMuebles;
    }

    public ArrayList<SelectItem> getListaTipoBienMueble() {
        listaTipoBienMueble.clear();
        listaTipoBienMueble.add(new SelectItem("Automóvil", "Automóvil"));
        listaTipoBienMueble.add(new SelectItem("Autobús", "Autobús"));
        listaTipoBienMueble.add(new SelectItem("Cabezal", "Cabezal"));
        listaTipoBienMueble.add(new SelectItem("Cuadrimoto", "Cuadrimoto"));
        listaTipoBienMueble.add(new SelectItem("Tricimoto", "Tricimoto"));
        listaTipoBienMueble.add(new SelectItem("Microbús", "Microbús"));
        listaTipoBienMueble.add(new SelectItem("Motocicleta", "Motocicleta"));
        listaTipoBienMueble.add(new SelectItem("Panel", "Panel"));
        listaTipoBienMueble.add(new SelectItem("Pick Up", "Pick Up"));
        listaTipoBienMueble.add(new SelectItem("Remolque", "Remolque"));
        listaTipoBienMueble.add(new SelectItem("Camión", "Camión"));
        return listaTipoBienMueble;
    }

    public void setListaTipoBienMueble(ArrayList<SelectItem> listaTipoBienMueble) {
        this.listaTipoBienMueble = listaTipoBienMueble;
    }

    public ArrayList<PtmBienesmuebles> getListaBienesmuebles() {
        return listaBienesmuebles;
    }

    public void setListaBienesmuebles(ArrayList<PtmBienesmuebles> listaBienesmuebles) {
        this.listaBienesmuebles = listaBienesmuebles;
    }

    public PtmBienesinmuebles getBienesInmuebles() {
        return bienesInmuebles;
    }

    public void setBienesInmuebles(PtmBienesinmuebles bienesInmuebles) {
        this.bienesInmuebles = bienesInmuebles;
    }

    public ArrayList<PtmBienesinmuebles> getListaBienesInmuebles() {
        return listaBienesInmuebles;
    }

    public void setListaBienesInmuebles(ArrayList<PtmBienesinmuebles> listaBienesInmuebles) {
        this.listaBienesInmuebles = listaBienesInmuebles;
    }

    public PtmPrestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(PtmPrestamo prestamo) {
        this.prestamo = prestamo;
    }

    public ArrayList<PtmPrestamo> getListaPrestamo() {
        return listaPrestamo;
    }

    public void setListaPrestamo(ArrayList<PtmPrestamo> listaPrestamo) {
        this.listaPrestamo = listaPrestamo;
    }

    public PtmTarjetacredito getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(PtmTarjetacredito tarjeta) {
        this.tarjeta = tarjeta;
    }

    public ArrayList<PtmTarjetacredito> getListaTarjeta() {
        return listaTarjeta;
    }

    public void setListaTarjeta(ArrayList<PtmTarjetacredito> listaTarjeta) {
        this.listaTarjeta = listaTarjeta;
    }

    /* Metodo para consultar los datos del colaborador por DPI */
    public void consultarPatrimonio(){
        if(estadoPatrimonialPk.getAnio() != 0 && colaborador.getDpi() != null){
            EstadoPatrimonial estado = new EstadoPatrimonial();
            estado.setAnio(estadoPatrimonialPk.getAnio());
            estado.setDpi(colaborador.getDpi());
            estado.setUsuario(userConect);
            
            String myDpi = colaborador.getDpi();

            if(estado.consultarDatos()){
                
                colaborador = estado.getColaborador();
                activo = estado.getActivo();
                pasivo = estado.getPasivo();

                if(estado.listaBienesMuebles().size() > 0){
                    listaBienesmuebles = estado.listaBienesMuebles();
                }
                
                if(estado.listaBienesInmuebles().size() > 0){
                    listaBienesInmuebles = estado.listaBienesInmuebles();
                }
                
                if(estado.listaPrestamos().size() > 0){
                    listaPrestamo = estado.listaPrestamos();
                }
                
                if(estado.listaTarjetas().size() > 0){
                    listaTarjeta = estado.listaTarjetas();
                }
            }
            else{
                if(estado.consultarColaborador()){
                    colaborador = estado.getColaborador();
                    activo = new PtmActivo();
                    pasivo = new PtmPasivo();
                    listaBienesmuebles.clear();
                    listaBienesInmuebles.clear();
                    listaPrestamo.clear();
                    listaTarjeta.clear();
                }
                else{
                    colaborador = new PtmColaborador();
                    colaborador.setDpi(myDpi);
                    activo = new PtmActivo();
                    pasivo = new PtmPasivo();
                    listaBienesmuebles.clear();
                    listaBienesInmuebles.clear();
                    listaPrestamo.clear();
                    listaTarjeta.clear();
                }
            }
        }
    }
    
    /* Agregar un registro en la lista de bienes muebles */
    public void agregarBienesMuebles(){
        listaBienesmuebles.add(bienesMuebles);
        bienesMuebles = new PtmBienesmuebles();
    }
    
    /* Quita un registro de la lista de bienes muebles */
    public void quitarBienesMuebles(PtmBienesmuebles b){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        
        if(b.getIdbienesmuebles() != null){
            PtmBienesmuebles bienMueble = em.find(PtmBienesmuebles.class, b.getIdbienesmuebles());
            em.getTransaction().begin();
            em.remove(bienMueble);
            em.getTransaction().commit();
        }
        
        em.close();
        emf.close();
        
        listaBienesmuebles.remove(b);
    }
    
    /* Metodo utilizado para almacenar la lista de bienes muebles */
    private void almacenarBienesMuebles(){
        for(PtmBienesmuebles b : listaBienesmuebles){
            b.setPtmEstadopatrimonial(estadoPatrimonial);
            Registrar<PtmBienesmuebles> registrar = new Registrar(b);
            registrar.guardar();
        }
    }
    
    /* Metodo utilizado para agregar un registro en la lista de Bienes Inmuebles */
    public void agregarBienesInmuebles(){
        listaBienesInmuebles.add(bienesInmuebles);
        bienesInmuebles = new PtmBienesinmuebles();
    }
    
    /* Metodo utilizado para quitar un registro de bienes inmuebles */
    public void quitarBienesInmuebles(PtmBienesinmuebles bi){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        
        if(bi.getIdbienesinmuebles() != null){
            PtmBienesinmuebles bienInmueble = em.find(PtmBienesinmuebles.class, bi.getIdbienesinmuebles());
            em.getTransaction().begin();
            em.remove(bienInmueble);
            em.getTransaction().commit();
        }
        
        em.close();
        emf.close();
        
        listaBienesInmuebles.remove(bi);
    }
    
    /* Metodo utilizado para almacenar la lista de bienes muebles */
    private void almacenarBienesInmuebles(){
        for(PtmBienesinmuebles bi : listaBienesInmuebles){
            bi.setPtmEstadopatrimonial(estadoPatrimonial);
            Registrar<PtmBienesmuebles> registrar = new Registrar(bi);
            registrar.guardar();
        }
    }
    
    /* Metodo utilizado para agregar un registro en la lista de prestamos */
    public void agregarPrestamos(){
        listaPrestamo.add(prestamo);
        prestamo = new PtmPrestamo();
    }
    
    /* Metodo utilizado para quitar un registro de prestamos */
    public void quitarPrestamos(PtmPrestamo p){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        
        if(p.getIdprestamo() != null){
            PtmPrestamo prest = em.find(PtmPrestamo.class, p.getIdprestamo());
            em.getTransaction().begin();
            em.remove(prest);
            em.getTransaction().commit();
        }
        
        em.close();
        emf.close();
        
        listaPrestamo.remove(p);
    }
    
    /* Metodo utilizado para almacenar la lista de prestamos */
    private void almacenarPrestamos(){
        for(PtmPrestamo p : listaPrestamo){
            p.setPtmEstadopatrimonial(estadoPatrimonial);
            Registrar<PtmBienesmuebles> registrar = new Registrar(p);
            registrar.guardar();
        }
    }
    
    /* Metodo utilizado para agregar un registro en la lista de tarjetas */
    public void agregarTarjeta(){
        listaTarjeta.add(tarjeta);
        tarjeta = new PtmTarjetacredito();
    }
    
    /* Metodo utilizado para quitar un registro de tarjetas */
    public void quitarTarjetas(PtmTarjetacredito t){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        
        if(t.getIdtarjetacredito() != null){
            PtmTarjetacredito tar = em.find(PtmTarjetacredito.class, t.getIdtarjetacredito());
            em.getTransaction().begin();
            em.remove(tar);
            em.getTransaction().commit();
        }
        
        em.close();
        emf.close();
        
        listaTarjeta.remove(t);
    }
    
    /* Metodo utilizado para almacenar la lista de tarjetas */
    private void almacenarTarjetas(){
        for(PtmTarjetacredito t : listaTarjeta){
            t.setPtmEstadopatrimonial(estadoPatrimonial);
            Registrar<PtmBienesmuebles> registrar = new Registrar(t);
            registrar.guardar();
        }
    }
    
    /* Metodo para registrar los datos del colaborador ingresado */
    public void guardarPatrimonio(){
        // Datos necesarios para el estado patrimonial
        estadoPatrimonialPk.setColaboradorDpi(colaborador.getDpi());
        estadoPatrimonial.setPtmEstadopatrimonialPK(estadoPatrimonialPk);
        estadoPatrimonial.setEstado('a');
        estadoPatrimonial.setFecha(new Date());
        estadoPatrimonial.setUsuario(userConect);
        estadoPatrimonial.setPtmActivoIdactivo(activo);
        estadoPatrimonial.setPtmPasivoIdpasivo(pasivo);
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        // Almacena la informacion en forma de transaccion
        try{
            em.getTransaction().begin();
            
            em.merge(colaborador);
            em.merge(activo);
            em.merge(pasivo);
            em.merge(estadoPatrimonial);
            
            em.getTransaction().commit();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }finally{
            em.close();
            emf.close();
        }
    }
    
    /* Metodo utilizado para imprimir estado patrimonial a pdf */
    public void imprimirDatos() throws Exception{
        estadoPatrimonialPk.setColaboradorDpi(colaborador.getDpi());
        estadoPatrimonial.setPtmEstadopatrimonialPK(estadoPatrimonialPk);
        
        // Almacena los bienes agregados
        almacenarBienesMuebles();
        almacenarBienesInmuebles();
        almacenarPrestamos();
        almacenarTarjetas();
            
        // Accion para exportar a PDF
        exportarPDF();
    }
    
    /* Metodo utilizado para exportar documento en PDF */
    public void exportarPDF() throws Exception{
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("anio", estadoPatrimonialPk.getAnio());
        parametros.put("dpi", colaborador.getDpi());
        parametros.put("usuario", userConect);
        
        Class.forName("com.mysql.jdbc.Driver");
        Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);
        JasperPrint jasperPrint = JasperFillManager.fillReport("/var/lib/tomcat7/webapps/Report/Patrimonio_Personal.jasper", parametros, conexion);
        
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.addHeader("Content-Disposition","attachment; filename=Estado_Patrimonial.pdf");
        ServletOutputStream stream = response.getOutputStream();
        
        JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
        
        stream.flush();
        stream.close();
        conexion.close();
        
        FacesContext.getCurrentInstance().responseComplete();
    }
    
}