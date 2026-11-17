package Busqueda;
import catalogo.Catalogo;

public class PorNombre implements CriterioBusqueda {
    private String texto;
    public PorNombre(String texto) { this.texto = texto; }

    @Override
    public boolean cumple(Catalogo catalogo) {
        return catalogo.getNombre().toLowerCase().contains(texto.toLowerCase());
    }
}