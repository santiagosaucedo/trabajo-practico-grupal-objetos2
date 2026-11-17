package Busqueda;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import catalogo.Catalogo;
import catalogo.Categoria;

class PorCategoriaTests {
    @Test void cumpleSiElItemEsDeLaCategoria() {
        Catalogo c = mock(Catalogo.class);
        when(c.esDeCategoria(Categoria.ALOJAMIENTO)).thenReturn(true);

        assertTrue(new PorCategoria(Categoria.ALOJAMIENTO).cumple(c));
    }
    @Test void noCumpleSiElItemNoEsDeLaCategoria() {
        Catalogo c = mock(Catalogo.class);
        when(c.esDeCategoria(Categoria.ALOJAMIENTO)).thenReturn(false);

        assertFalse(new PorCategoria(Categoria.ALOJAMIENTO).cumple(c));
    }
    @Test void consultaALaCategoriaRecibida() {
        Catalogo c = mock(Catalogo.class);

        new PorCategoria(Categoria.VUELO).cumple(c);

        verify(c).esDeCategoria(Categoria.VUELO);
    }
}