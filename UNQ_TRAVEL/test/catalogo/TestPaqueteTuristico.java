package catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPaqueteTuristico {

	PaqueteTuristico paqueteViajeAJujuy;
	
	PaqueteTuristico paqueteViajeASalta;
	
	PaqueteTuristico paqueteMockito;
	
	PaqueteTuristico paqueteMockitoVacio;
	
	ServicioIndividual servicioMockito;
	
	ServicioIndividual servicioMockitoSinCapacidadDeLiberar;
	
	ServicioIndividual servicioMockitoSinCapacidadDeReservar;
	
	@BeforeEach
	void setUp() {
		servicioMockitoSinCapacidadDeLiberar = mock(ServicioIndividual.class);
		servicioMockitoSinCapacidadDeReservar = mock(ServicioIndividual.class);
		paqueteMockito = mock(PaqueteTuristico.class);
		servicioMockito = mock(ServicioIndividual.class);
		
		paqueteViajeAJujuy = new PaqueteTuristico(50, "ViajeAJujuy", "Un super viaje", "Aerolineas el carpincho");
		paqueteViajeAJujuy.agregarCatalogo(paqueteMockito);
		
		paqueteViajeASalta = new PaqueteTuristico(50, "ViajeASalta", "Un super viaje", "Aerolineas el carpincho");
		
		
		
		when(servicioMockitoSinCapacidadDeReservar.sePuedeReservarCupo()).thenReturn(false);
		when(servicioMockitoSinCapacidadDeReservar.sePuedeQuitarCupo()).thenReturn(true);
		
		when(servicioMockitoSinCapacidadDeLiberar.sePuedeReservarCupo()).thenReturn(true);
		when(servicioMockitoSinCapacidadDeLiberar.sePuedeQuitarCupo()).thenReturn(false);
		

		
		when(paqueteMockito.precioBaseCalculado()).thenReturn(1500.0);
		when(paqueteMockito.sePuedeQuitarCupo()).thenReturn(true);
		when(paqueteMockito.sePuedeReservarCupo()).thenReturn(true);
		when(paqueteMockito.getCuposTotales()).thenReturn(30);
		when(paqueteMockito.getCuposReservados()).thenReturn(27);
		when(paqueteMockito.getCuposLibres()).thenReturn(3);
		when(paqueteMockito.esDeCategoria(Categoria.ALOJAMIENTO)).thenReturn(false);
		when(paqueteMockito.getFecha()).thenReturn(LocalDate.of(2004, 6, 23));

		
		when(servicioMockito.precioBaseCalculado()).thenReturn(500.0);
		when(servicioMockito.sePuedeQuitarCupo()).thenReturn(true);
		when(servicioMockito.sePuedeReservarCupo()).thenReturn(true);
		when(servicioMockito.getCuposTotales()).thenReturn(10);
		when(servicioMockito.getCuposReservados()).thenReturn(0);
		when(servicioMockito.getCuposLibres()).thenReturn(10);
		when(servicioMockito.esDeCategoria(Categoria.ALOJAMIENTO)).thenReturn(true);
		when(servicioMockito.getFecha()).thenReturn(LocalDate.of(2004, 6, 21));
	}
	
	@Test
	void testAdicionDeCatalogoAlPaquete() {
		
		assertThrows(RuntimeException.class, () -> paqueteViajeAJujuy.agregarCatalogo(paqueteViajeAJujuy));
		
	}
	
	@Test
	void testInstanciamientoInvalidoDePaquete() {
		assertThrows(RuntimeException.class, () -> new PaqueteTuristico (101, "ViajeAJujuy", "", ""));
		
		assertThrows(RuntimeException.class, () -> new PaqueteTuristico (-1, "ViajeAJujuy", "", ""));
		
	}
	
	@Test
	void testSubidaYBajadaDeCupos() {
		
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockito);
		
		verify(paqueteMockito, times(0)).decrementarCupoConVenta();
		verify(servicioMockito, times(0)).decrementarCupoConVenta();
		verify(paqueteMockito, times(0)).liberarCupoConVenta();
		verify(servicioMockito, times(0)).liberarCupoConVenta();
		
		paqueteViajeAJujuy.decrementarCupoConVenta();
		
		verify(paqueteMockito, times(1)).decrementarCupoSinVenta();
		verify(servicioMockito, times(1)).decrementarCupoSinVenta();
		verify(paqueteMockito, times(0)).decrementarCupoConVenta();
		verify(servicioMockito, times(0)).decrementarCupoConVenta();
		
		paqueteViajeAJujuy.liberarCupoConVenta();
		
		verify(paqueteMockito, times(1)).liberarCupoSinVenta();
		verify(servicioMockito, times(1)).liberarCupoSinVenta();
		verify(paqueteMockito, times(0)).liberarCupoConVenta();
		verify(servicioMockito, times(0)).liberarCupoConVenta();
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockitoSinCapacidadDeReservar);
		
		assertThrows(RuntimeException.class, () -> paqueteViajeAJujuy.decrementarCupoConVenta());
		
		verify(paqueteMockito, times(1)).decrementarCupoSinVenta();
		verify(servicioMockito, times(1)).decrementarCupoSinVenta();
		verify(servicioMockitoSinCapacidadDeReservar, times(0)).decrementarCupoSinVenta();
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockitoSinCapacidadDeLiberar);
		
		assertThrows(RuntimeException.class, () -> paqueteViajeAJujuy.liberarCupoConVenta());
		
		verify(paqueteMockito, times(1)).liberarCupoSinVenta();
		verify(servicioMockito, times(1)).liberarCupoSinVenta();
		verify(servicioMockitoSinCapacidadDeReservar, times(0)).liberarCupoSinVenta();
		verify(servicioMockitoSinCapacidadDeLiberar, times(0)).liberarCupoSinVenta();
		
	}	
	
	
	@Test
	void testCalcularPrecios() {
		
		assertEquals(1500.0, paqueteViajeAJujuy.precioBaseCalculado());
		
		assertEquals(750.0, paqueteViajeAJujuy.getPrecioFinal());
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockito);
		
		assertEquals(2000.0, paqueteViajeAJujuy.precioBaseCalculado());
		
		assertEquals(1000.0, paqueteViajeAJujuy.getPrecioFinal());
		
	}

	@Test
	void testCantidadesDeCupos() {
		
		assertThrows(RuntimeException.class, () -> paqueteViajeASalta.getCuposLibres());
		assertThrows(RuntimeException.class, () -> paqueteViajeASalta.getCuposTotales());
		assertThrows(RuntimeException.class, () -> paqueteViajeASalta.getCuposReservados());

		assertEquals(30, paqueteViajeAJujuy.getCuposTotales());
		assertEquals(27, paqueteViajeAJujuy.getCuposReservados());
		assertEquals(3, paqueteViajeAJujuy.getCuposLibres());
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockito);
		
		assertEquals(10, paqueteViajeAJujuy.getCuposTotales());
		assertEquals(7, paqueteViajeAJujuy.getCuposReservados());
		assertEquals(3, paqueteViajeAJujuy.getCuposLibres());
		
	}
	
	@Test
	void testGets() {
		
		assertEquals("ViajeAJujuy",paqueteViajeAJujuy.getNombre());
		
		assertEquals("Aerolineas el carpincho", paqueteViajeAJujuy.getProveedor());
		
		assertEquals("Un super viaje", paqueteViajeAJujuy.getDescripcion());
		
		assertEquals(List.of(paqueteMockito), paqueteViajeAJujuy.getContenido());
		
	}
	
	@Test
	void testGetVentas() {
		assertEquals(0, paqueteViajeAJujuy.getVentas());
		
		paqueteViajeAJujuy.decrementarCupoConVenta();
		
		assertEquals(1, paqueteViajeAJujuy.getVentas());
		
		paqueteViajeAJujuy.decrementarCupoSinVenta();
		
		assertEquals(1, paqueteViajeAJujuy.getVentas());
		
	}
	
	@Test
	void testEsDeCategoria() {
		
		assertFalse(paqueteViajeAJujuy.esDeCategoria(Categoria.ALOJAMIENTO));
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockito);
		
		assertTrue(paqueteViajeAJujuy.esDeCategoria(Categoria.ALOJAMIENTO));

		
	}
	
	@Test
	void testGetFecha() {
		
		assertThrows(RuntimeException.class, () -> paqueteViajeASalta.getFecha());
		
		assertEquals(LocalDate.of(2004, 6, 23), paqueteViajeAJujuy.getFecha());
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockito);

		assertEquals(LocalDate.of(2004, 6, 21), paqueteViajeAJujuy.getFecha());
		
	}
	
}
