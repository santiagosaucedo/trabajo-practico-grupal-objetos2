package NotificadoresYDocumentos.Vouchers.Documentos;

public class ComprobanteFiscal implements DocumentoRegistrable {
    private int ID;
    private double monto;
    private String descripcion;

    public ComprobanteFiscal(int ID, double monto, String descripcion) {
        this.ID = ID;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public int getID() {
        return this.ID;
    }
    public double getMonto() {
        return this.monto;
    }
}
