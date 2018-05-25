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
    private ArrayList<SelectItem> itemDepartamentos = new ArrayList<>();
    private ArrayList<Puesto> puestos = new ArrayList<>();
    public boolean select = false;
    private String nombreBuscar;
    
    public PuestosBean(){
        consultarPuestos();
    }

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public ArrayList<SelectItem> getItemDepartamentos() {
        itemDepartamentos.clear();
        for(Departamento d : new Departamento().mostrarDepartamentos()){
            SelectItem itemDep = new SelectItem(d.getId(), d.getNombre());
            itemDepartamentos.add(itemDep);
        }
        return itemDepartamentos;
    }

    public void setItemDepartamentos(ArrayList<SelectItem> itemDepartamentos) {
        this.itemDepartamentos = itemDepartamentos;
    }

    public ArrayList<Puesto> getPuestos() {
        return puestos;
    }

    public void setPuestos(ArrayList<Puesto> puestos) {
        this.puestos = puestos;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public String getNombreBuscar() {
        return nombreBuscar;
    }

    public void setNombreBuscar(String nombreBuscar) {
        this.nombreBuscar = nombreBuscar;
    }
    /* Metodo utilizado para consultar los registro de puesto */
    private void consultarPuestos(){
        puestos = puesto.mostrarPuesto();
    }
    
    /* Metodo utilizado para insertar un registro de puesto */
    public void insertar(ActionEvent event){
        puesto.insertar();
        consultarPuestos();
        puesto = new Puesto();
    }
    
    /* Metodo para seleccionar un objeto de puesto */
    public void selectPuesto(Puesto p){
        puesto = p;
        select = true;
    }
    
    /* Metodo utilizado para actualizar un registro de puesto */
    public void actualizar(ActionEvent event){
        puesto.update();
        consultarPuestos();
        puesto = new Puesto();
        select = false;
    }
    
    /* Metodo utilizado para cancelar la accion */
    public void canclear(ActionEvent event){
        puesto = new Puesto();
        select = false;
        consultarPuestos();
    }
    
    /*Metodo utilizado para filtrar por nombre*/
    public void buscarPuestos(){
        if(!nombreBuscar.isEmpty()){
            puestos.clear();
            puestos = Puesto.buscarPuestos(nombreBuscar);
        }
        else{
            nombreBuscar = new String();
            consultarPuestos();
        }
    }
}
