package reporte;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import catalogo.Catalogo;
import visitanteReporte.VisitanteReporte;

class TestReportePorServiciosMasReservados {

	Negocio negocio;
	
	VisitanteReporte visitante;

	ReportePorServiciosMasReservados reporte;
	
	PeriodoDeFechas periodo;
	
	Catalogo servicio1;
	
	Catalogo servicio2;
	
	Catalogo servicio3;
	
	List<Catalogo> listaEsperada;
	
	@BeforeEach
	void setUp() {
	
		servicio1 = mock(Catalogo.class);
		when(servicio1.getFecha()).thenReturn(LocalDate.of(2000, 2, 13));
		when(servicio1.getVentas()).thenReturn(10);
		when(servicio1.getPrecioFinal()).thenReturn(130.0);
		
		
		servicio2 = mock(Catalogo.class);
		when(servicio2.getFecha()).thenReturn(LocalDate.of(2000, 2, 9));
		when(servicio2.getVentas()).thenReturn(0);

		servicio3 = mock(Catalogo.class);
		when(servicio3.getFecha()).thenReturn(LocalDate.of(2000, 2, 18));
		when(servicio3.getVentas()).thenReturn(10);
		when(servicio3.getPrecioFinal()).thenReturn(100.0);

		
		periodo = new PeriodoDeFechas (LocalDate.of(2000, 2, 12), LocalDate.of(2000, 2, 18));
		
		negocio = mock(Negocio.class);
		when(negocio.getServiciosOrdenadosPorCantidadDeReservas()).thenReturn(List.of(servicio1, servicio2, servicio3));
		
		visitante = mock(VisitanteReporte.class);
		
		listaEsperada = List.of(servicio1,servicio3);

		
		reporte = new ReportePorServiciosMasReservados(negocio, periodo);
		
		
	}
	
	@Test
	void testAceptarVisitante() {
		
		reporte.aceptar(visitante);
		
		verify(visitante, times(1)).visitarReporteServiciosMasReservados(reporte);
		
	}
	
	@Test
	void testServiciosDeNegocioPorPeriodoYCantidadDeReservas() {
				
		assertEquals(listaEsperada, reporte.serviciosDeNegocioPorPeriodoYCantidadDeReservas());
		
	}
	
	@Test
	void testCostoPromedio() {
		
		assertEquals(115.0, reporte.costoPromedio());
		
	}
	
	@Test
	void testCostoPromedioConNegocioSinVentas() {
		
		periodo = new PeriodoDeFechas (LocalDate.of(2000, 2, 9), LocalDate.of(2000, 2, 10));
		
		reporte = new ReportePorServiciosMasReservados(negocio, periodo);
		
		assertThrows(RuntimeException.class, () -> reporte.costoPromedio());
		
	}
	

}
