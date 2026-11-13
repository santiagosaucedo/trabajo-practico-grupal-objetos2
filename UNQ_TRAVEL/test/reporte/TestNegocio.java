package reporte;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import catalogo.Catalogo;

class TestNegocio {

	Negocio negocio;
	
	Catalogo servicio1;
	
	Catalogo servicio2;

	Catalogo servicio3;
	
	@BeforeEach
	void setUp() {
		
		servicio1 = mock(Catalogo.class);
		when(servicio1.getCuposReservados()).thenReturn(5);
		when(servicio1.getProveedor()).thenReturn("proveedor1");
		
		servicio2 = mock(Catalogo.class);
		when(servicio2.getCuposReservados()).thenReturn(9);
		when(servicio2.getProveedor()).thenReturn("proveedor2");
		
		servicio3 = mock(Catalogo.class);
		when(servicio3.getCuposReservados()).thenReturn(3);
		when(servicio3.getProveedor()).thenReturn("proveedor1");
		
		negocio = new Negocio(List.of(servicio1, servicio2, servicio3));
		
	}
	
	@Test
	void testGetServiciosOrdenadosPorCantidadDeReservas() {
		
		List<Catalogo> listaEsperada = List.of(servicio1, servicio2, servicio3);
		
		assertEquals(listaEsperada, negocio.getMisServicios());
		
		listaEsperada = List.of(servicio2, servicio1, servicio3);
		
		assertEquals(listaEsperada, negocio.getServiciosOrdenadosPorCantidadDeReservas());
		
	}
	
	@Test
	void testGetServiciosPorProveedor() {
		
		 Map<String, List<Catalogo>> listaEsperada = Map.of("proveedor1", List.of(servicio1, servicio3), 
				 											"proveedor2", List.of(servicio2));
		 
		 assertEquals(listaEsperada, negocio.getServiciosPorProveedor());
		
	}

}
