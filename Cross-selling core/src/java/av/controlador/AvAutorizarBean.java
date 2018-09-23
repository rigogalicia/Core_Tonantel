
package av.controlador;

import av.modelo.AutorizarAvaluo;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;


@ManagedBean(name = "av_autorizar")
@ViewScoped
public class AvAutorizarBean {
    private ArrayList<AutorizarAvaluo> listAutorizar = new ArrayList<>();
    private AutorizarAvaluo autorizar = new AutorizarAvaluo();

    public ArrayList<AutorizarAvaluo> getListAutorizar() {
        return listAutorizar;
    }

    public void setListAutorizar(ArrayList<AutorizarAvaluo> listAutorizar) {
        this.listAutorizar = listAutorizar;
    }

    public AutorizarAvaluo getAutorizar() {
        return autorizar;
    }

    public void setAutorizar(AutorizarAvaluo autorizar) {
        this.autorizar = autorizar;
    }
    
    
    
        public AvAutorizarBean() {
            autorizar.setEst('c');
            listAutorizar = autorizar.consultarAvaluo();
    }
        
    public void filtrarDatos(ValueChangeEvent e){
        char newEst[] = e.getNewValue().toString().toCharArray();
        autorizar.setEst(newEst[0]);
        listAutorizar = autorizar.consultarAvaluo();
    }
}
