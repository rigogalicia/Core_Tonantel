
package admin.controlador;

import admin.modelo.Departamento;
import admin.modelo.Puesto;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


@ManagedBean(name = "puestos")
@ViewScoped
public class PuestosBean {
    Puesto puesto = new Puesto();
    ArrayList<Puesto> listapuestos = new ArrayList<>();
    
    private Departamento departamento;

    public PuestosBean() {

        consultarPuesto();
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public ArrayList<Puesto> getListapuestos() {
        return listapuestos;
    }

    public void setListapuestos(ArrayList<Puesto> listapuestos) {
        this.listapuestos = listapuestos;
    }
    
    
    private void consultarPuesto(){
        listapuestos.clear();
        listapuestos = puesto.mostrarPuestos();
    }
    public void insertarPuesto(){
        Departamento departamento = new Departamento();
        puesto.setIdDepartamento(departamento);
        puesto.insert();
        consultarPuesto();
        puesto = new Puesto();

        
    }        
    public void actualizarPuesto(){
        
        puesto.update();
        consultarPuesto();
        puesto = new Puesto();
    }
    public void seleccionarPuesto(Puesto a){
        puesto = a;
    }
    
}
