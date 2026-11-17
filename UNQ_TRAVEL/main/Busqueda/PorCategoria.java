package Busqueda;
import catalogo.Catalogo;
import catalogo.Categoria;

public class PorCategoria implements CriterioBusqueda {
    private Categoria categoria;
    public PorCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public boolean cumple(Catalogo catalogo) {
        return catalogo.esDeCategoria(categoria);
    }
}