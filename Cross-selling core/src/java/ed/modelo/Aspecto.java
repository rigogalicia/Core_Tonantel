package ed.modelo;

import java.util.ArrayList;
import org.bson.Document;

public class Aspecto extends Conducta{
    private ArrayList<Conducta> conductas = new ArrayList<>();
    private ArrayList<Document> documentConductas = new ArrayList<>();

    public ArrayList<Conducta> getConductas() {
        return conductas;
    }

    public void setConductas(ArrayList<Conducta> conductas) {
        this.conductas = conductas;
    }

    public ArrayList<Document> getDocumentConductas() {
        for(Conducta c : conductas){
            documentConductas.add(new Document("descripcion", c.getDescripcion()).append("peso", c.getPeso()));
        }
        return documentConductas;
    }

    public void setDocumentConductas(ArrayList<Document> documentConductas) {
        this.documentConductas = documentConductas;
    }

}
