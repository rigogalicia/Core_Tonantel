package ed.modelo;

import java.util.ArrayList;
import org.bson.Document;

public class Cualitativo extends Conducta{
    private ArrayList<Aspecto> aspectos = new ArrayList<>();
    private ArrayList<Document> documentAspectos = new ArrayList<>();

    public ArrayList<Aspecto> getAspectos() {
        return aspectos;
    }

    public void setAspectos(ArrayList<Aspecto> aspectos) {
        this.aspectos = aspectos;
    }

    public ArrayList<Document> getDocumentAspectos() {
        for(Aspecto a : aspectos) {
            documentAspectos.add(new Document("descripcion", a.getDescripcion())
                    .append("peso", a.getPeso())
                    .append("conductas", a.getDocumentConductas()));
        }
        return documentAspectos;
    }

    public void setDocumentAspectos(ArrayList<Document> documentAspectos) {
        this.documentAspectos = documentAspectos;
    }

}
