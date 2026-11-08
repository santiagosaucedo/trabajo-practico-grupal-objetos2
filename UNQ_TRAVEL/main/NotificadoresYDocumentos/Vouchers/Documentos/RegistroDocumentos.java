package NotificadoresYDocumentos.Vouchers.Documentos;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class RegistroDocumentos {
    private ArrayList<DocumentoRegistrable> misDocumentos =  new ArrayList<>();

    public void guardarDocumento(DocumentoRegistrable documento) {
        this.misDocumentos.add(documento);
    }

    public DocumentoRegistrable getDocumento(int id){
        DocumentoRegistrable documentoBuscado;
        return documentoBuscado = misDocumentos.stream().filter(documento -> documento.getID() == id)
                                                        .findFirst()
                                                        .orElseThrow(() -> new NoSuchElementException("No existe el documento " + id));
    }
    public double montoTotal() {
        return misDocumentos.stream()
                .mapToDouble(DocumentoRegistrable::getMonto)
                .sum();
    }

}
