package NotificadoresYDocumentos.Vouchers.Documentos;
public class VoucherDeCredito implements DocumentoRegistrable {
    private double monto;
    private int id;

    public VoucherDeCredito(int id, double monto){
        this.id = id;
        this.monto = monto;
    }


    public int getID() {
        return this.id;
    }

    public double getMonto() {
        return this.monto;
    }
}
