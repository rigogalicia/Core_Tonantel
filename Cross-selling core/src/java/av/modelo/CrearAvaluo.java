
package av.modelo;

import admin.modelo.Colaborador;
import admin.modelo.Puesto;
import admin.modelo.ReportConfig;
import dao.AvAnexos;
import dao.AvArea;
import dao.AvAsignacion;
import dao.AvAvaluo;
import dao.AvColindante;
import dao.AvConstruccion;
import dao.AvDetalle;
import dao.AvInmueble;
import dao.AvSolicitud;
import gc.controlador.Correo;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperRunManager;
import patrimonio.modelo.ConexionMySql;

public class CrearAvaluo {
    private AvSolicitud solicitud = new AvSolicitud();
    private AvInmueble inmueble = new AvInmueble();
    private AvColindante colindante = new AvColindante();
    private ArrayList<AvColindante> colindantes = new ArrayList<>();
    private AvArea area = new AvArea();
    private AvConstruccion construccion = new AvConstruccion();
    private AvAsignacion asignacion = new AvAsignacion();
    private AvAvaluo avaluo = new AvAvaluo();
    private DetalleAvaluo detalle = new DetalleAvaluo();
    private ArrayList<DetalleAvaluo> detalleAvaluo = new ArrayList<>();
    private AnexosAvaluo anexos = new AnexosAvaluo();
    private ArrayList<AnexosAvaluo> listAnexos = new ArrayList<>();
    private double sumaTotalDetalle;
    private double total;
    
    private boolean btnGenerar = true;
    private boolean btnUpdate = false;
    private String msjInsertar;
    
    public CrearAvaluo() {
        
    }

    public AvSolicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(AvSolicitud solicitud) {
        this.solicitud = solicitud;
    }

    public AvInmueble getInmueble() {
        return inmueble;
    }

    public void setInmueble(AvInmueble inmueble) {
        this.inmueble = inmueble;
    }

    public AvColindante getColindante() {
        return colindante;
    }

    public void setColindante(AvColindante colindante) {
        this.colindante = colindante;
    }

    public ArrayList<AvColindante> getColindantes() {
        return colindantes;
    }

    public void setColindantes(ArrayList<AvColindante> colindantes) {
        this.colindantes = colindantes;
    }

    public AvArea getArea() {
        return area;
    }

    public void setArea(AvArea area) {
        this.area = area;
    }

    public AvConstruccion getConstruccion() {
        return construccion;
    }

    public void setConstruccion(AvConstruccion construccion) {
        this.construccion = construccion;
    }

    public AvAsignacion getAsignacion() {
        return asignacion;
    }

    public void setAsignacion(AvAsignacion asignacion) {
        this.asignacion = asignacion;
    }

    public AvAvaluo getAvaluo() {
        return avaluo;
    }

    public void setAvaluo(AvAvaluo avaluo) {
        this.avaluo = avaluo;
    }

    public DetalleAvaluo getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleAvaluo detalle) {
        this.detalle = detalle;
    }

    public ArrayList<DetalleAvaluo> getDetalleAvaluo() {
        return detalleAvaluo;
    }

    public void setDetalleAvaluo(ArrayList<DetalleAvaluo> detalleAvaluo) {
        this.detalleAvaluo = detalleAvaluo;
    }

    public AnexosAvaluo getAnexos() {
        return anexos;
    }

    public void setAnexos(AnexosAvaluo anexos) {
        this.anexos = anexos;
    }

    public ArrayList<AnexosAvaluo> getListAnexos() {
        return listAnexos;
    }

    public void setListAnexos(ArrayList<AnexosAvaluo> listAnexos) {
        this.listAnexos = listAnexos;
    }
        
    public double getSumaTotalDetalle() {
        return sumaTotalDetalle;
    }

    public void setSumaTotalDetalle(double sumaTotalDetalle) {
        this.sumaTotalDetalle = sumaTotalDetalle;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isBtnGenerar() {
        return btnGenerar;
    }

    public void setBtnGenerar(boolean btnGenerar) {
        this.btnGenerar = btnGenerar;
    }

    public boolean isBtnUpdate() {
        return btnUpdate;
    }

    public void setBtnUpdate(boolean btnUpdate) {
        this.btnUpdate = btnUpdate;
    }
    
    public String getMsjInsertar() {
        return msjInsertar;
    }

    public void setMsjInsertar(String msjInsertar) {
        this.msjInsertar = msjInsertar;
    }
    
    //Metodo para tomar el id que trae de asigancion, solicicitud e inmueble la solicitud
    public void consultar(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();

          String instruccion ="SELECT a, s, i "
                  + "FROM AvAsignacion a "
                  + "JOIN a.solicitudNumeroSolicitud s "
                  + "JOIN s.inmuebleId i "
                  + "WHERE s.numeroSolicitud = :numSolicitud ";

     
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSolicitud", solicitud.getNumeroSolicitud());
        List<Object[]> resultado = consulta.getResultList();
        for(Object[] objeto: resultado){    
            asignacion = (AvAsignacion)objeto[0];
            solicitud = (AvSolicitud) objeto[1];              
            inmueble= (AvInmueble) objeto[2];

        }
        
        em.close();
        emf.close();
    }
    
    // Este metodo realiza la consulta de la clase avaluo e inmueble
    public void consultarSolicitud(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT S.numero_solicitud numeroSolicitud, "
                + "I.id inmuebleId, "
                + "I.direccion_fisica direccionFisica, "
                + "I.coordenadas Coordenadas, "
                + "I.observaciones observaciones, "
                + "R.avaluar areaAvaluar, "
                + "R.construida areaConstruida, "
                + "R.exceso areaExceso, "
                + "R.fisica areaFisica, "
                + "R.frenteyfondo medidasFrenteFondo, "
                + "R.id AreaId, "
                + "R.registrada areaRegistrada, "
                + "C.agua agua, "
                + "C.ambiente ambientes, "
                + "C.cielo cielos, "
                + "C.destino destino, "
                + "C.electricidad servicoElectrico, "
                + "C.factores_positivos factoresPositivos, "
                + "C.id construccionId, "
                + "C.muro muros, "
                + "C.niveles niveles, "
                + "C.piso pisos, "
                + "C.riesgo riesgoInmueble, "
                + "C.sanitario servicioSanitario, "
                + "C.techo techos, "
                + "V.fechahora fehcainspeccion, "
                + "V.valor_bancario valorbancario, "
                + "V.valor_redonDeado valorRedondeado, "
                + "V.id avaluoId,"
                + "A.id avaluoId "
                + "FROM av_solicitud S "
                + "JOIN av_inmueble I "
                + "ON S.inmueble_id = I.id "
                + "JOIN av_area R "
                + "ON R.inmueble_id = I.id "
                + "JOIN av_construccion C "
                + "ON C.inmueble_id = I.id "
                + "JOIN av_avaluo as V "
                + "ON V.inmueble_id = I.id "
                + "JOIN av_asignacion A "
                + "ON V.asignacion_id = A.id "
                + "WHERE S.numero_solicitud = '"+solicitud.getNumeroSolicitud()+"' ";
           

        Query consulta = em.createNativeQuery(instruccion);
        List<Object[]> resultado = consulta.getResultList();
//        
        if(!resultado.isEmpty()){
            for(Object[] obj: resultado){
                solicitud.setNumeroSolicitud((String) obj[0]);
                inmueble.setId((int) obj[1]);
                inmueble.setDireccionFisica((String) obj[2]);
                inmueble.setCoordenadas((String) obj[3]);
                inmueble.setObservaciones((String) obj[4]);
                area.setAvaluar((String) obj[5]);
                area.setConstruida((String) obj[6]);
                area.setExceso((String) obj[7]);
                area.setFisica((String) obj[8]);
                area.setFrenteyfondo((String) obj[9]);
                area.setId((int) obj[10]);
                area.setRegistrada((String) obj[11]);
                construccion.setAgua((String) obj[12]);
                construccion.setAmbiente((String) obj[13]);
                construccion.setCielo((String) obj[14]);
                construccion.setDestino((String) obj[15]);
                construccion.setElectricidad((String) obj[16]);
                construccion.setFactoresPositivos((String) obj[17]);
                construccion.setId((int) obj[18]);
                construccion.setMuro((String) obj[19]);
                construccion.setNiveles((String) obj[20]);
                construccion.setPiso((String) obj[21]);
                construccion.setRiesgo((String) obj[22]);
                construccion.setSanitario((String) obj[23]);
                construccion.setTecho((String) obj[24]);
                avaluo.setFechahora((Date) obj[25]);
                avaluo.setValorBancario((int) obj[26]);
                avaluo.setValorRedondeado((BigDecimal) obj[27]);
                avaluo.setId((int) obj[28]);
                asignacion.setId((int) obj[29]);

            }
           
            listaColindantes(inmueble);
            listaDetalleConstruccion(inmueble);
            listaAnexos(avaluo); 
            
            //Validamos el resultado para validar botones
            if(!resultado.isEmpty()){
                btnGenerar = false;
                btnUpdate = true;
            }
        }


        em.close();
        emf.close();
    }
    
    //Metodo utilizado para agrengar colindates de avaluo
    public void agregarColindante(){
        colindantes.add(colindante);
        colindante = new AvColindante();
    }
    
    //Metodo utilizado para borrar colindantes agregados en la tabla
    public void quitarColindante(AvColindante c){
        colindantes.remove(c);
    }
    
    // Metodo para agregar detalle de solicitud
    public void agregarDetalle(){
        detalle.setDescripcionTipo(detalle.getTipo() == 'a' ? "Solar" : "Construccion");
        detalle.setTotal(detalle.getMedidas() * detalle.getValor());
        detalleAvaluo.add(detalle);
        detalle = new DetalleAvaluo();
        
        calcularTotal();
    }
    
    // Metodo para quitar el detalle de solicitud
    public void quitarDetalle(DetalleAvaluo d){
        detalleAvaluo.remove(d);
        calcularTotal();
    }
    
    //Metodo para llenar anexos del avaluo
    public void agregarAnexos(String path, String nombreImagen){
        //nombreImagen = nombreImagen(anexos.getUrl()+ num ++) ;
        anexos.setDescripcion(anexos.getDescripcion());
        anexos.setUrl(path);
        anexos.setNombreImagen(nombreImagen);
        listAnexos.add(anexos);
       
        anexos = new AnexosAvaluo();
        

        
    }
    
    //Metodo para quitar anello
    public void quitarAnexo(AnexosAvaluo a){
        listAnexos.remove(a);
    }
    
    // Metodo que es utilizado para calcular la suma total de detalle
    private void calcularTotal(){
        sumaTotalDetalle = 0;
        detalleAvaluo.forEach((d) -> {
            
            sumaTotalDetalle += d.getTotal();
        });
    }
    
    // Este metodo calcula el total del avaluo tamando en cuenta el valor vancario
    public void total(){
        total = (avaluo.getValorRedondeado().doubleValue() * avaluo.getValorBancario()) / 100;
    }
    
        //Metodo para consultar los registros de punto cardinal
    private void listaColindantes(AvInmueble inmueble){
        char est = 'b';
        colindantes.clear();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT c "
                + "FROM AvColindante c "
                + "JOIN c.inmuebleId i "
                + "WHERE i.id = :idInmueble "
                + "AND c.tipo = :est ";
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("idInmueble", inmueble.getId());
        consulta.setParameter("est", est);
        List<AvColindante> resultado = consulta.getResultList();
        for(AvColindante  c: resultado){
            colindantes.add(c);
        }
        
        em.close();
        emf.close();
    }
    
    //Metodo para consultar el detalle de la construccion
    private void listaDetalleConstruccion(AvInmueble inmiuble){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT d "
                + "FROM AvDetalle d "
                + "JOIN d.avaluoId v "
                + "JOIN v.inmuebleId i "
                + "WHERE i.id = :idInmueble";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("idInmueble", inmiuble.getId());
        List<AvDetalle> resultado = consulta.getResultList();
        
        for(AvDetalle d: resultado){
           
           DetalleAvaluo dd = new DetalleAvaluo();
           dd.setDescripcion(d.getDescripcion());
           dd.setDescripcionTipo(d.getTipo()== 'a' ? "Solar" : "Construccion");
           dd.setMedidas(Double.parseDouble(d.getMedidas().toString()));
           dd.setValor(Double.parseDouble(d.getValor().toString()));
           dd.setTotal(dd.getMedidas() * dd.getValor());
           detalleAvaluo.add(dd);
        }
        calcularTotal();
        total();
        em.close();
        emf.close();
    }
    
    //Metodo para listar de nuevo los anexos
    public void listaAnexos(AvAvaluo avaluo){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
       
        String instruccion = "SELECT a "
                + "FROM AvAnexos a "
                + "JOIN a.avaluoId v "
                + "WHERE v.id = :idAvaluo ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("idAvaluo", avaluo.getId());
        List<AvAnexos> resultado = consulta.getResultList();
        
        for(AvAnexos a: resultado){
            AnexosAvaluo an = new AnexosAvaluo();

            an.setNombreImagen(nombreImagen(a.getUrl()));
            an.setDescripcion(a.getDescripcion()); 
            an.setUrl(a.getUrl());
            listAnexos.add(an);
        }

        em.close();
        emf.close();
    }
    
    /* Metodo para obtener el nombre de la imagen tomando como base el path de la base de datos */
    private String nombreImagen(String pathDB) {
        String resultado = "";
        
        StringTokenizer token = new StringTokenizer(pathDB, "/");
        while(token.hasMoreTokens()) {
            resultado = token.nextToken();
        }
        
        return resultado;
    }
    
    //  Metodo para eliminar las colindantes
    private void eliminarColindantes(int inmuebleid){
        try{
            char tipo = 'b';
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);

            Statement st = conexion.createStatement();
            st.execute("DELETE FROM av_colindante WHERE inmueble_id = '"+inmuebleid+"' AND tipo = '"+tipo+"' ");
            
            st.close();
            conexion.close();
           
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    //  Metodo para eliminar las colindantes
    private void eliminarDetalle(int avaluoid){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);

            Statement st = conexion.createStatement();
            st.execute("DELETE FROM av_detalle WHERE avaluo_id = '"+avaluoid+"'");
            
            st.close();
            conexion.close();
           
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
        
    //Metodo para eliminar los anexos de 
    public void eliminarListAnexos(int avaluoid){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);

            Statement st = conexion.createStatement();
            st.execute("DELETE FROM av_anexos WHERE avaluo_id = '"+avaluoid+"'");
            
            st.close();
            conexion.close();
           
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    //Metodo par crear el avaluo
    public void insert(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();

            em.getTransaction().begin();
            AvSolicitud solicitudModif = em.find(AvSolicitud.class, solicitud.getNumeroSolicitud());
            solicitudModif.setEstado('c');
            AvInmueble inmuebleModif = em.find(AvInmueble.class, inmueble.getId());
            inmuebleModif.setDireccionFisica(inmueble.getDireccionFisica());
            inmuebleModif.setCoordenadas(inmueble.getCoordenadas());
            inmuebleModif.setObservaciones(inmueble.getObservaciones());
            area.setInmuebleId(inmueble);
            em.persist(area);
            //eliminarColindantes(inmueble.getId());
            for(AvColindante colin : colindantes){
                    colin.setTipo('b');
                    colin.setInmuebleId(inmueble);
                    em.persist(colin);
                    
            }
            construccion.setInmuebleId(inmueble);
            em.persist(construccion);
            avaluo.setInmuebleId(inmueble);
            avaluo.setAsignacionId(asignacion);
            em.persist(avaluo);
            //eliminarDetalle(avaluo.getId());
            for(DetalleAvaluo da : detalleAvaluo){
                AvDetalle detalleInsert = new AvDetalle();
                detalleInsert.setDescripcion(da.getDescripcion());
                detalleInsert.setMedidas(new BigDecimal(da.getMedidas()));
                detalleInsert.setValor(new BigDecimal(da.getValor()));
                detalleInsert.setTipo(da.getTipo());
                detalleInsert.setAvaluoId(avaluo);
                em.persist(detalleInsert);
            }
            
            
            em.getTransaction().commit();
            
            msjInsertar = "Datos enviados Exitosamente! ahora puedes adjuntar los anexos al avaluo";
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        }
    }
    
    //Metodo par actualziar el formulario
    public void updateAvaluo(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();

            em.getTransaction().begin();
            AvSolicitud solicitudModif = em.find(AvSolicitud.class, solicitud.getNumeroSolicitud());
            AvInmueble inmuebleModif = em.find(AvInmueble.class, inmueble.getId());
            AvAvaluo avaluoModi = em.find(AvAvaluo.class, avaluo.getId());
            
            inmuebleModif.setDireccionFisica(inmueble.getDireccionFisica());
            inmuebleModif.setCoordenadas(inmueble.getCoordenadas());
            inmuebleModif.setObservaciones(inmueble.getObservaciones());

            area.setInmuebleId(inmueble);
            em.merge(area);
            eliminarColindantes(inmueble.getId());
            for(AvColindante colin : colindantes){
                    colin.setTipo('b');
                    colin.setInmuebleId(inmueble);
                    em.merge(colin);
                    
            }
            construccion.setInmuebleId(inmueble);
            em.merge(construccion);
            avaluo.setInmuebleId(inmueble);
            avaluo.setAsignacionId(asignacion);
            em.merge(avaluo);
            eliminarDetalle(avaluo.getId());
            for(DetalleAvaluo da : detalleAvaluo){
                AvDetalle detalleInsert = new AvDetalle();
                detalleInsert.setDescripcion(da.getDescripcion());
                detalleInsert.setMedidas(new BigDecimal(da.getMedidas()));
                detalleInsert.setValor(new BigDecimal(da.getValor()));
                detalleInsert.setTipo(da.getTipo());
                detalleInsert.setAvaluoId(avaluo);
                em.merge(detalleInsert);
            }
            
            
            em.getTransaction().commit();
            
            msjInsertar = "Datos Actualizados exitosamente!";
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        } 
    }
    
    
    //Metodo para insertar Anexos 
    public void insertAnexos(){
        EntityManagerFactory emf = null;
        EntityManager em = null;
        
        try {
            emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
            em = emf.createEntityManager();
            
            em.getTransaction().begin();
            eliminarListAnexos(avaluo.getId());
            for(AnexosAvaluo anex: listAnexos){
                AvAnexos anexosInsert = new AvAnexos();
                anexosInsert.setDescripcion(anex.getDescripcion());
                anexosInsert.setUrl(anex.getUrl());
                anexosInsert.setAvaluoId(avaluo);
                em.merge(anexosInsert);
            }
            
            em.getTransaction().commit();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/av/av_enproceso.xhtml");
            enviarCorreo();
            
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }finally{
            if(emf != null && em != null){
                em.close();
                emf.close();
            }
        }
    }
    
   // Metodo utilizado para enviar correo que notificara al enncargado de autorizar avaluos
    public void enviarCorreo(){
        for(Colaborador c : Colaborador.ColaboradoresPorPuesto(Puesto.idPuesto("Jefe de Créditos y Cobros"))){
            String msj = "Tienes un nuevo avaluó por revisar, realizado por  " + Colaborador.datosColaborador(asignacion.getUsuario()).getNombre() + " \n"
                + "para un mejor seguimiento ingresa a Crosselling Core.\n\n"
                + "Copyright © Investigación y Desarrollo de Tecnología Cooperativa Tonantel R.L";
        
        Correo correo = new Correo(Colaborador.correoColaborador(c.getUsuario()), "Numero Solicitud " + solicitud.getNumeroSolicitud() , msj);
        correo.enviar();
        }  
    }
    
    /* Este metodo es utilizado para mostrar el detalle de avaluo */
    public static void detalle(String numeroSolicitud, String nombreValuador, String autorizadorResponsable){ 
  
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conexion = DriverManager.getConnection(ConexionMySql.URL, ConexionMySql.USERNAME, ConexionMySql.PASSWORD);
            
            String nombreReporte = "av_detalle.jasper";
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("numeroSolicitud", numeroSolicitud);
            parametros.put("valuador", nombreValuador);
            parametros.put("autorizador", autorizadorResponsable);

            byte[] bytes = JasperRunManager.runReportToPdf(ReportConfig.path_avaluos + nombreReporte, parametros, conexion);
            HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            response.setContentType("application/pdf");
            response.setContentLength(bytes.length);
            ServletOutputStream outStream = response.getOutputStream();
            outStream.write(bytes, 0, bytes.length);

            outStream.flush();
            outStream.close();
            conexion.close();
            
            FacesContext.getCurrentInstance().responseComplete();
        }
        catch(Exception e){
            e.printStackTrace(System.out);
        }
    }
    
    // Metodo para consultar el nombre del valuador responsable
    public static String valuadorResponsable(String numeroSolicitud){
        String result = "";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a "
                + "FROM AvAsignacion a "
                + "JOIN a.solicitudNumeroSolicitud s "
                + "WHERE s.numeroSolicitud = :numSol";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSol", numeroSolicitud);
        List<AvAsignacion> resultado = consulta.getResultList();
        
        for(AvAsignacion a : resultado){
            result = Colaborador.datosColaborador(a.getUsuario()).getNombre();
        }
        
        em.close();
        emf.close();
        
        return result;
    }
    
    // Metodo para consultar el nombre del valuador responsable
    public static String autorizadorResponsable(String numeroSolicitud){
        String result = "";
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Cross-selling_corePU");
        EntityManager em = emf.createEntityManager();
        
        String instruccion = "SELECT a "
                + "FROM AvAvaluo a "
                + "JOIN a.asignacionId g "
                + "JOIN g.solicitudNumeroSolicitud s "
                + "WHERE s.numeroSolicitud = :numSol ";
        
        Query consulta = em.createQuery(instruccion);
        consulta.setParameter("numSol", numeroSolicitud);
        List<AvAvaluo> resultado = consulta.getResultList();
        for(AvAvaluo a : resultado){
            result = Colaborador.datosColaborador(a.getAutorizador()).getNombre();
        }
        
        em.close();
        emf.close();
        
        return result;
    }
}
