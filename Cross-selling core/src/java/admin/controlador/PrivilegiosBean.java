package admin.controlador;

import admin.modelo.Privilegio;
import admin.modelo.Rol;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

@ManagedBean(name = "privilegios")
@ViewScoped
public class PrivilegiosBean {
    private Privilegio privilegio = new Privilegio();
    private ArrayList<Privilegio> privilegios = new ArrayList<>();
    private ArrayList<SelectItem> itemRoles = new ArrayList<>();
    private boolean select = false;
    private String nombreBuscar;
    
    public PrivilegiosBean() {
        consultarDatos();
    }

    public Privilegio getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Privilegio privilegio) {
        this.privilegio = privilegio;
    }

    public ArrayList<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(ArrayList<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }

    public String getNombreBuscar() {
        return nombreBuscar;
    }

    public void setNombreBuscar(String nombreBuscar) {
        this.nombreBuscar = nombreBuscar;
    }
    

    public ArrayList<SelectItem> getItemRoles() {
        itemRoles.clear();
        for(Rol r : new Rol().mostrarRoles()){
            SelectItem itemRol = new SelectItem(r.getId(), r.getDescripcion());
            itemRoles.add(itemRol);
        }
        return itemRoles;
    }

    public void setItemRoles(ArrayList<SelectItem> itemRoles) {
        this.itemRoles = itemRoles;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
    
    /* Metodo utilizado para consultar registro de privilegios */
    private void consultarDatos(){
        privilegios.clear();
        privilegios = privilegio.mostrarPrivilegios();
    }
    
    /* Metodo utilizado para insertar un registro en privilegios */
    public void insertar(ActionEvent event){
        privilegio.insert();
        privilegio = new Privilegio();
        consultarDatos();
    }
    
    /* Metodo utilizado para actualizar un registro de privilegios */
    public void actualizar(ActionEvent event){
        privilegio.update();
        consultarDatos();
        privilegio = new Privilegio();
        select = false;
    }
    
    /* Metodo utilizado para seleccionar un registro de privilegio */
    public void seleccionar(Privilegio p){
        privilegio = p;
        select = true;
    }
    
    /* Metodo para cancelar la accion de actualizar */
    public void cancelar(ActionEvent event){
        privilegio = new Privilegio();
        select = false;
        consultarDatos();
    }
    public void buscarRoles(){
        if(!nombreBuscar.isEmpty()){
            privilegios.clear();
            privilegios = Privilegio.buscarPrivilegios(nombreBuscar);
        }
        else{
            nombreBuscar = new String();
            consultarDatos();
        }
    }
}

