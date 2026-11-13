package reporte;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import catalogo.Catalogo;
import visitanteReporte.VisitanteReporte;

class TestReportePorProveedor {

	Negocio negocio;
	
	Catalogo servicio1;
	
	Catalogo servicio2;
	
	Catalogo servicio3;
	
	Catalogo servicio4;
	
	ReportePorProveedor reporte;
	
	VisitanteReporte visitante;

	
	@BeforeEach
	void setUp() {
		
		servicio1 = mock (Catalogo.class);
		
		servicio2 = mock (Catalogo.class);
		
		servicio3 = mock (Catalogo.class);
		
		servicio4 = mock (Catalogo.class);
		
		visitante = mock (VisitanteReporte.class);
	
		//Un "Map.of" devuelve siempre los elementos en un orden de : clave1, valor1, clave2, valor2
		negocio = mock(Negocio.class);
		when(negocio.getMisServicios()).thenReturn(List.of(servicio1, servicio2, servicio3, servicio4));
		when(negocio.getServiciosPorProveedor()).thenReturn(Map.of("proveedor1", List.of(servicio1,servicio2),
															"proveedor2", List.of(servicio3, servicio4)));
		
		 reporte = new ReportePorProveedor (negocio);
		
	}
	
	@Test
	void testAceptarVisitante() {
		reporte.aceptar(visitante);
		
		verify(visitante, times(1)).visitarReportePorProveedor(reporte);
	}
	
	@Test
	void testProveedoresYSuPorcentaje() {
		Map<String, Double> mapEsperado = Map.of("proveedor1", 50.0, "proveedor2", 50.0);
		
		assertEquals(mapEsperado, reporte.proveedoresYSuPorcentajeEnElNegocio());
	}

}
