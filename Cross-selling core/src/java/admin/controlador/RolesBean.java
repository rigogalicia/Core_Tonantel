package admin.controlador;

import admin.modelo.Rol;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

@ManagedBean(name = "roles")
@ViewScoped
public class RolesBean {
    private Rol rol = new Rol();
    private ArrayList<Rol> roles = new ArrayList<>();
    private boolean select = false;
    private String nombreBuscar;
    
    public RolesBean() {
        consultarDatos();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public ArrayList<Rol> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<Rol> roles) {
        this.roles = roles;
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
    
    
    /* Metodo utilizado para consultar los registros de rol */
    private void consultarDatos(){
        roles.clear();
        roles = rol.mostrarRoles();
    }
    
    /* Metodo utilizado para insert un registro en el documento de roles */
    public void insertar(ActionEvent event){
        rol.insert();
        consultarDatos();
        rol = new Rol();
    }
    
    /* Metodo utilizado para realizar la accion de modificar un registro de rol */
    public void actualizar(ActionEvent event){
        rol.update();
        consultarDatos();
        rol= new Rol();
        select = false;
    }
    
    /* Metodo utilizado para seleccionar un registro de rol */
    public void seleccionar(Rol r){
        rol = r;
        select = true;
    }
    
    /* Metodo para realizar la opcion de cancelar */
    public void cancelar(ActionEvent event){
        rol = new Rol();
        select = false;
        consultarDatos();
    }
    
    /* Metodo utilizado para filtrar por nombre*/
    public void buscarRoles(){
        if(!nombreBuscar.isEmpty()){
            roles.clear();
            roles = Rol.buscarRoles(nombreBuscar);
        }
        else{
            nombreBuscar = new String();
            consultarDatos();
        }
    }
}
