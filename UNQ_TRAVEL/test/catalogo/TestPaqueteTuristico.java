package catalogo;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestPaqueteTuristico {

	PaqueteTuristico paqueteViajeAJujuy;
	
	PaqueteTuristico paqueteMockito;
	
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
		
		
		
		when(servicioMockitoSinCapacidadDeReservar.sePuedeReservarCupo()).thenReturn(false);
		when(servicioMockitoSinCapacidadDeReservar.sePuedeQuitarCupo()).thenReturn(true);
		
		when(servicioMockitoSinCapacidadDeLiberar.sePuedeReservarCupo()).thenReturn(true);
		when(servicioMockitoSinCapacidadDeLiberar.sePuedeQuitarCupo()).thenReturn(false);
		

		
		when(paqueteMockito.precioBaseCalculado()).thenReturn(1500.0);
		when(paqueteMockito.sePuedeQuitarCupo()).thenReturn(true);
		when(paqueteMockito.sePuedeReservarCupo()).thenReturn(true);
		when(paqueteMockito.getCuposTotales()).thenReturn(30);
		when(paqueteMockito.getCuposReservados()).thenReturn(20);
		
		when(servicioMockito.precioBaseCalculado()).thenReturn(500.0);
		when(servicioMockito.sePuedeQuitarCupo()).thenReturn(true);
		when(servicioMockito.sePuedeReservarCupo()).thenReturn(true);
		when(servicioMockito.getCuposTotales()).thenReturn(10);
		when(servicioMockito.getCuposReservados()).thenReturn(0);
	}
	
	
	@Test
	void testInstancionInvalidaDePaquete() {
		assertThrows(RuntimeException.class, () -> new PaqueteTuristico (101, "ViajeAJujuy", "", ""));
		
		assertThrows(RuntimeException.class, () -> new PaqueteTuristico (-1, "ViajeAJujuy", "", ""));
		
	}
	
	@Test
	void testSubidaYBajadaDeCupos() {
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockito);
		
		verify(paqueteMockito, times(0)).decrementarCupo();
		verify(servicioMockito, times(0)).decrementarCupo();
		verify(paqueteMockito, times(0)).liberarCupo();
		verify(servicioMockito, times(0)).liberarCupo();
		
		paqueteViajeAJujuy.decrementarCupo();
		
		verify(paqueteMockito, times(1)).decrementarCupo();
		verify(servicioMockito, times(1)).decrementarCupo();
		
		paqueteViajeAJujuy.liberarCupo();
		
		verify(paqueteMockito, times(1)).liberarCupo();
		verify(servicioMockito, times(1)).liberarCupo();
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockitoSinCapacidadDeReservar);
		
		assertThrows(RuntimeException.class, () -> paqueteViajeAJujuy.decrementarCupo());
		
		verify(paqueteMockito, times(1)).decrementarCupo();
		verify(servicioMockito, times(1)).decrementarCupo();
		verify(servicioMockitoSinCapacidadDeReservar, times(0)).decrementarCupo();
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockitoSinCapacidadDeLiberar);
		
		assertThrows(RuntimeException.class, () -> paqueteViajeAJujuy.liberarCupo());
		
		verify(paqueteMockito, times(1)).liberarCupo();
		verify(servicioMockito, times(1)).liberarCupo();
		verify(servicioMockitoSinCapacidadDeReservar, times(0)).liberarCupo();
		verify(servicioMockitoSinCapacidadDeLiberar, times(0)).liberarCupo();
		
	}
	
	@Test
	void testCalcularPrecios() {
		
		assertEquals(paqueteViajeAJujuy.precioBaseCalculado(), 1500.0);
		
		assertEquals(paqueteViajeAJujuy.getPrecioFinal(),  750.0);
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockito);
		
		assertEquals(paqueteViajeAJujuy.precioBaseCalculado(), 2000.0);
		
		assertEquals(paqueteViajeAJujuy.getPrecioFinal(),  1000.0);
		
	}

	@Test
	void testCantidadesDeCupos() {
		
		assertEquals(paqueteViajeAJujuy.getCuposTotales(), 30);
		assertEquals(paqueteViajeAJujuy.getCuposReservados(), 20);
		
		paqueteViajeAJujuy.agregarCatalogo(servicioMockito);
		
		assertEquals(paqueteViajeAJujuy.getCuposTotales(), 40);
		assertEquals(paqueteViajeAJujuy.getCuposReservados(), 20);
		
	}
	
	@Test
	void testGets() {
		
		assertEquals(paqueteViajeAJujuy.getNombre(), "ViajeAJujuy");
		
		assertEquals(paqueteViajeAJujuy.getProveedor(), "Aerolineas el carpincho");
		
		assertEquals(paqueteViajeAJujuy.getDescripcion(), "Un super viaje");
		
	}
	
}
