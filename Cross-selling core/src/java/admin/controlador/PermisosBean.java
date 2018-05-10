package admin.controlador;

import admin.modelo.Colaborador;
import admin.modelo.Permiso;
import admin.modelo.Privilegio;
import admin.modelo.Rol;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "permisos")
@ViewScoped
public class PermisosBean {
    private Permiso permiso = new Permiso();
    private Colaborador colaborador = new Colaborador();
    private ArrayList<Rol> roles = new ArrayList<>();
    private ArrayList<Permiso> rolesAsignados = new ArrayList<>();
    private ArrayList<Privilegio> privilegios = new ArrayList<>();
    private ArrayList<Privilegio> privilegiosAsignados = new ArrayList<>();
    
    public PermisosBean() {
        
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public ArrayList<Rol> getRoles() {
        roles.clear();
        Rol rol = new Rol();
        roles = rol.mostrarRoles();
        return roles;
    }

    public void setRoles(ArrayList<Rol> roles) {
        this.roles = roles;
    }

    public ArrayList<Permiso> getRolesAsignados() {
        return rolesAsignados;
    }

    public void setRolesAsignados(ArrayList<Permiso> rolesAsignados) {
        this.rolesAsignados = rolesAsignados;
    }

    public ArrayList<Privilegio> getPrivilegios() {
        return privilegios;
    }

    public void setPrivilegios(ArrayList<Privilegio> privilegios) {
        this.privilegios = privilegios;
    }

    public ArrayList<Privilegio> getPrivilegiosAsignados() {
        return privilegiosAsignados;
    }

    public void setPrivilegiosAsignados(ArrayList<Privilegio> privilegiosAsignados) {
        this.privilegiosAsignados = privilegiosAsignados;
    }
    
    /* Metodo utilizado para consultar los datos de colaborador */
    public void consultarColaborador(){
        this.colaborador = colaborador.datosColaborador();
        Permiso per = new Permiso();
        per.setIdUsuario(colaborador.getUsuario());
        rolesAsignados = per.mostrarPermisos();
    }
    
    /* Metodo utilizado para asignar roles */
    public void asignarRol(Rol r){
        Permiso per = new Permiso();
        per.setIdUsuario(colaborador.getUsuario());
        per.setId(r.getId());
        per.setDescripcion(r.getDescripcion());
        per.setForma(r.getForma());
        per.asignarRol();
        rolesAsignados = per.mostrarPermisos();
    }
    
    /* Metodo utilizado para eliminar un rol asignado */
    public void eliminarRol(Permiso p){
        p.eliminarRol();
        privilegios.clear();
        privilegiosAsignados.clear();
        rolesAsignados.clear();
        rolesAsignados = p.mostrarPermisos();
    }
    
    /* Metodo utilizado para consultar los privilegios */
    public void consultarPrivilegios(Permiso p){
        permiso = p;
        privilegios.clear();
        Privilegio priv = new Privilegio();
        priv.setIdRol(p.getId().toString());
        privilegios = priv.privilegiosPorRol();
        privilegiosAsignados.clear();
        privilegiosAsignados = permiso.consultarPrivilegios();
    }
    
    /* Metodo para asignar los privilegios */
    public void asignarPrivilegios(Privilegio p){
        privilegiosAsignados.add(p);
        permiso.setPrivilegios(privilegiosAsignados);
        permiso.asignarPrivilegio();
        privilegiosAsignados.clear();
        privilegiosAsignados = permiso.consultarPrivilegios();
    }
    
    /* Metodo para eliminar los privilegios */
    public void eliminarPrivilegio(Privilegio p){
        privilegiosAsignados.remove(p);
        permiso.setPrivilegios(privilegiosAsignados);
        permiso.asignarPrivilegio();
        privilegiosAsignados.clear();
        privilegiosAsignados = permiso.consultarPrivilegios();
    }
    
}
