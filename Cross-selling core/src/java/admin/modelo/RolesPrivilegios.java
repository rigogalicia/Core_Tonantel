package admin.modelo;

public class RolesPrivilegios {
    private String descripcion;
    
    public RolesPrivilegios(String Descripcion){
        this.descripcion = Descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
