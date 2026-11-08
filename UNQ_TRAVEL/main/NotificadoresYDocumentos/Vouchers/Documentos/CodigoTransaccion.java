package NotificadoresYDocumentos.Vouchers.Documentos;

public class CodigoTransaccion implements DocumentoRegistrable {
    private double monto;
    private int id;

    public CodigoTransaccion(int id, double monto){
        this.id = id;
        this.monto = monto;
    }


    public int getID() {
        return this.id;
    }

    public double getMonto(){
        return this.monto;
    }
}
