package gc.controlador;

import admin.modelo.Colaborador;
import admin.modelo.Departamento;
import gc.modelo.Reasignar;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

@ManagedBean(name = "gc_reasignar")
@ViewScoped
public class GcReasignar {
    private Reasignar reasignar = new Reasignar();
    private String msj;
    private ArrayList<SelectItem> analistas = new ArrayList<>();

    public GcReasignar() {
    }

    public Reasignar getReasignar() {
        return reasignar;
    }

    public void setReasignar(Reasignar reasignar) {
        this.reasignar = reasignar;
    }

    public String getMsj() {
        return msj;
    }

    public void setMsj(String msj) {
        this.msj = msj;
    }

    public ArrayList<SelectItem> getAnalistas() {
        analistas.clear();
        for(Colaborador c : Colaborador.colaboradoresPorDepartamento(Departamento.idDepartamento("Fábrica de Créditos"))){
            SelectItem itemAnalista = new SelectItem(c.getUsuario(), c.getNombre());
            analistas.add(itemAnalista);
        }
        return analistas;
    }

    public void setAnalistas(ArrayList<SelectItem> analistas) {
        this.analistas = analistas;
    }
    
    /* Metodo para consultar los datos de la solicitud */
    public void consultarDatos(){
        reasignar.consultarDatos();
        msj = null;
    }
    
    // Metodo utilizado para reasignar una solicitud
    public void reasignarSolicitud(){
        if(!reasignar.getAnalista().isEmpty()){
            reasignar.cambiarAnalista();
            msj = "! El cambio de analista se ejecuto con exito";
        }
        else{
            reasignar.quitarAsignacion();
            msj = "! La asignación fue eliminada con exito";
        }
        
        reasignar = new Reasignar();
    }
    
    // Metodo utilizado para cancelar la accion
    public void cancelar(){
        reasignar = new Reasignar();
        msj = null;
    }
}
