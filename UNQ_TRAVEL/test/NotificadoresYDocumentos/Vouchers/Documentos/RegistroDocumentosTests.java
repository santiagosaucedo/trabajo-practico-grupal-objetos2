package NotificadoresYDocumentos.Vouchers.Documentos;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistroDocumentosTests {

    private RegistroDocumentos registro;
    private DocumentoRegistrable documentoUno;
    private DocumentoRegistrable documentoDos;

    @BeforeEach
    void setUp() {
        registro = new RegistroDocumentos();

        documentoUno = mock(DocumentoRegistrable.class);
        when(documentoUno.getID()).thenReturn(1);
        when(documentoUno.getMonto()).thenReturn(1000.0);

        documentoDos = mock(DocumentoRegistrable.class);
        when(documentoDos.getID()).thenReturn(2);
        when(documentoDos.getMonto()).thenReturn(2500.0);
    }

    @Test
    void unDocumentoGuardadoSePuedeRecuperarPorId() {
        registro.guardarDocumento(documentoUno);

        assertSame(documentoUno, registro.getDocumento(1));
    }

    @Test
    void recuperaElDocumentoCorrectoEntreVarios() {
        registro.guardarDocumento(documentoUno);
        registro.guardarDocumento(documentoDos);

        assertSame(documentoDos, registro.getDocumento(2));
        assertSame(documentoUno, registro.getDocumento(1));
    }

    @Test
    void buscarUnIdInexistenteLanzaExcepcion() {
        registro.guardarDocumento(documentoUno);

        assertThrows(NoSuchElementException.class, () -> registro.getDocumento(99));
    }

    @Test
    void laExcepcionIndicaElIdQueNoExiste() {
        NoSuchElementException excepcion = assertThrows(
                NoSuchElementException.class,
                () -> registro.getDocumento(99));

        assertEquals("No existe el documento 99", excepcion.getMessage());
    }

    @Test
    void buscarEnUnRegistroVacioLanzaExcepcion() {
        assertThrows(NoSuchElementException.class, () -> registro.getDocumento(1));
    }

    @Test
    void elMontoTotalSumaLosMontosDeLosDocumentosGuardados() {
        registro.guardarDocumento(documentoUno);
        registro.guardarDocumento(documentoDos);

        assertEquals(3500.0, registro.montoTotal(), 0.001);
    }

    @Test
    void elMontoTotalDeUnRegistroVacioEsCero() {
        assertEquals(0.0, registro.montoTotal(), 0.001);
    }

    @Test
    void cadaDocumentoGuardadoSeSumaAlMontoTotal() {
        registro.guardarDocumento(documentoUno);
        assertEquals(1000.0, registro.montoTotal(), 0.001);

        registro.guardarDocumento(documentoDos);
        assertEquals(3500.0, registro.montoTotal(), 0.001);
    }
}