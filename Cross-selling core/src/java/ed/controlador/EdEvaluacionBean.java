package ed.controlador;

import ed.modelo.Aspecto;
import ed.modelo.Conducta;
import ed.modelo.Evaluacion;
import ed.modelo.Ponderacion;
import java.io.IOException;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;

@ManagedBean(name = "ed_evaluacion")
@ViewScoped
public class EdEvaluacionBean {
    private String userConect;
    private ArrayList<SelectItem> aspectosSelect = new ArrayList<>();
    private ArrayList<SelectItem> conductasSelect = new ArrayList<>();
    private Evaluacion evaluacion = new Evaluacion();
    private Aspecto aspecto = new Aspecto();
    private Conducta conducta = new Conducta();
    private Ponderacion ponderacion = new Ponderacion();
    
    public EdEvaluacionBean() {
        // Metodo constructor de la clase
        HttpSession sesion = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if(sesion.getAttribute("userConect") != null){
            userConect = sesion.getAttribute("userConect").toString();
        }
        else
        {
            try {
               FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/index.xhtml"); 
            } catch (IOException ex) {
                ex.printStackTrace(System.out);
            }
        }
    }

    public ArrayList<SelectItem> getAspectosSelect() {
        aspectosSelect.clear();
        aspectosSelect.add(new SelectItem("Autoevaluación", "Autoevaluación"));
        aspectosSelect.add(new SelectItem("Especificas", "Jefe Inmediato"));
        aspectosSelect.add(new SelectItem("Complementarias", "Colaboradores"));
        return aspectosSelect;
    }

    public void setAspectosSelect(ArrayList<SelectItem> aspectosSelect) {
        this.aspectosSelect = aspectosSelect;
    }

    public ArrayList<SelectItem> getConductasSelect() {
        conductasSelect.clear();
        conductasSelect.add(new SelectItem("Conductas Genéricas", "Conductas Genéricas"));
        conductasSelect.add(new SelectItem("Conductas Específicas", "Conductas Específicas"));
        conductasSelect.add(new SelectItem("Conductas Complementarias", "Conductas Complementarias"));
        return conductasSelect;
    }

    public void setConductasSelect(ArrayList<SelectItem> conductasSelect) {
        this.conductasSelect = conductasSelect;
    }

    public Evaluacion getEvaluacion() {
        return evaluacion;
    }

    public void setEvaluacion(Evaluacion evaluacion) {
        this.evaluacion = evaluacion;
    }

    public Aspecto getAspecto() {
        return aspecto;
    }

    public void setAspecto(Aspecto aspecto) {
        this.aspecto = aspecto;
    }

    public Conducta getConducta() {
        return conducta;
    }

    public void setConducta(Conducta conducta) {
        this.conducta = conducta;
    }

    public Ponderacion getPonderacion() {
        return ponderacion;
    }

    public void setPonderacion(Ponderacion ponderacion) {
        this.ponderacion = ponderacion;
    }
    
    /* Metodo utilizado para agregar conductas */
    public void addConducta() {
        aspecto.getConductas().add(conducta);
        conducta = new Conducta();
    }
    
    /* Metodo utilizado para eliminar una conducta */
    public void deleteConducta(Conducta c) {
        aspecto.getConductas().remove(c);
    }
    
    /* Metodo para agregar aspectos */
    public void addAspectos() {
        evaluacion.getCualitativo().getAspectos().add(aspecto);
        aspecto = new Aspecto();
    }
    
    /* Metodo para eliminar aspectos */
    public void deleteAspecto(Aspecto a) {
        evaluacion.getCualitativo().getAspectos().remove(a);
    }
    
    /* Este metodo es utilizado para agregar una ponderacion */
    public void addPonderacion() {
        evaluacion.getPonderaciones().add(ponderacion);
        ponderacion = new Ponderacion();
    }
    
    /* Este metodo es utilizado para eliminar una ponderacion */
    public void deletePonderacion(Ponderacion p) {
        evaluacion.getPonderaciones().remove(p);
    }
    
    /* Metodo utilizado para insertar un registro de evaluacion */
    public void agregarEvaluacion() {
        try {
            evaluacion.setEstado("Activa");
            evaluacion.insert();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/Cross-selling_core/faces/vista/ed/ed_evaluaciones.xhtml");
        } catch(IOException e) {
            e.printStackTrace(System.out);
        }
    }
}
