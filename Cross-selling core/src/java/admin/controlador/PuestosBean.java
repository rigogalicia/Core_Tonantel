package admin.controlador;

import admin.modelo.Departamento;
import admin.modelo.Puesto;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "puestos")
@ViewScoped
public class PuestosBean {
    private Puesto puesto = new Puesto();
    private ArrayList<Puesto> puestos = new ArrayList<>();
    private ArrayList<SelectItem> departamentosItem = new ArrayList<>();
    
    public PuestosBean() {
        
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public ArrayList<Puesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(ArrayList<Puesto> puestos) {
        this.puestos = puestos;
    }

    public ArrayList<SelectItem> getDepartamentosItem() {
        return departamentosItem;
    }

    public void setDepartamentosItem(ArrayList<SelectItem> departamentosItem) {
        this.departamentosItem = departamentosItem;
    }
    
    /* Metodo utilizado para guardar un registro en la coleccion de puesto */
    public void insertar(ActionEvent event){
        puesto.insert();
        puesto = new Puesto();
    }
}
