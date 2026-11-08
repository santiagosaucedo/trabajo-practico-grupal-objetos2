package Direccion;

public class Direccion {
    private String calle;
    private int altura;
    private String ciudad;

    public Direccion(String calle, int altura, String ciudad) {
        this.calle = calle;
        this.altura = altura;
        this.ciudad = ciudad;
    }

    public String getCalle() { return calle; }
    public int getAltura() { return altura; }
    public String getCiudad() { return ciudad; }
}
