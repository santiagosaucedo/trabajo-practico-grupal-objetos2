package NotificadoresYDocumentos.Vouchers.Documentos;

public class ComprobanteCBU implements DocumentoRegistrable {
    private int ID;
    private double monto;
    private int cbu;

    public ComprobanteCBU(int ID, double monto, int cbu) {
        this.ID = ID;
        this.monto = monto;
        this.cbu = cbu;
    }

    public int getCbu() {
        return this.cbu;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public double getMonto() {
        return this.monto;
    }
}
