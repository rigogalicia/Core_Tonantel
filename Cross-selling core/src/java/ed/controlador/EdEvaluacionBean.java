package ed.controlador;

import ed.modelo.Aspecto;
import ed.modelo.Conducta;
import ed.modelo.Evaluacion;
import ed.modelo.Ponderacion;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "ed_evaluacion")
@ViewScoped
public class EdEvaluacionBean {
    private Evaluacion evaluacion = new Evaluacion();
    private Aspecto aspecto = new Aspecto();
    private Conducta conducta = new Conducta();
    private Ponderacion ponderacion = new Ponderacion();
    
    public EdEvaluacionBean() {
        
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
        evaluacion.setEstado('a');
        evaluacion.insert();
    }
}
