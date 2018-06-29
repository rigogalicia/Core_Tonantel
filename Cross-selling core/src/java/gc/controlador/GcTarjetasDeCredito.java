package gc.controlador;

import gc.modelo.TarjetasDeCredito;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "gc_tarjetas")
@ViewScoped
public class GcTarjetasDeCredito {
    private int opcion = 1;
    private ArrayList<TarjetasDeCredito> tarjetas = new ArrayList<>();
    
    public GcTarjetasDeCredito() {
        tarjetas = TarjetasDeCredito.mostrar(opcion);
    }

    public int getOpcion() {
        return opcion;
    }

    public void setOpcion(int opcion) {
        this.opcion = opcion;
    }

    public ArrayList<TarjetasDeCredito> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(ArrayList<TarjetasDeCredito> tarjetas) {
        this.tarjetas = tarjetas;
    }
    
    // Metodo utilizado para consultar los registros
    public void consultar(){
        tarjetas.clear();
        tarjetas = TarjetasDeCredito.mostrar(opcion);
    }
    
    // Metodo creado para troquelar la tarjeta
    public void troquelar(TarjetasDeCredito t){
        t.troquelar();
        consultar();
    }
}
