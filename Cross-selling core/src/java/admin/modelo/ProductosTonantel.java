package admin.modelo;

public class ProductosTonantel {
    private int idproducto;
    private String  nombre_producto;
    
    public ProductosTonantel(){}
    
    public ProductosTonantel(int idProducto, String nombreProducto){
        this.idproducto = idProducto;
        this.nombre_producto = nombreProducto;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }    
}