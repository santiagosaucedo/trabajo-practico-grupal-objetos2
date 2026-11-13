package reporte;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import catalogo.Catalogo;
import catalogo.Categoria;
import visitanteReporte.VisitanteReporte;

class TestReportePorOcupacionYCupoDisponible {

	ReporteOcupacionYCupoDisponible reportePorOcupacionYCupo;
	
	Negocio negocio;
	
	PeriodoDeFechas periodo;
	
	VisitanteReporte visitante;
	
	Catalogo vueloEnPeriodo;
	
	Catalogo vueloEnPeriodo2;
	
	Catalogo vueloFueraDePeriodo;
	
	Catalogo excursionEnPeriodo;
	
	@BeforeEach
	void setUp() {
		
		visitante = mock (VisitanteReporte.class);
		
		vueloEnPeriodo = mock (Catalogo.class);
		when (vueloEnPeriodo.esDeCategoria(Categoria.VUELO)).thenReturn(true);
		when (vueloEnPeriodo.getFecha()).thenReturn(LocalDate.of(2026, 12, 31));
		when (vueloEnPeriodo.getCuposTotales()).thenReturn(10);
		when (vueloEnPeriodo.getCuposReservados()).thenReturn(5);
		when (vueloEnPeriodo.getCuposLibres()).thenReturn(5);
		
		vueloEnPeriodo2 = mock (Catalogo.class);
		when (vueloEnPeriodo2.esDeCategoria(Categoria.VUELO)).thenReturn(true);
		when (vueloEnPeriodo2.getFecha()).thenReturn(LocalDate.of(2026, 12, 21));
		when (vueloEnPeriodo2.getCuposTotales()).thenReturn(12);
		when (vueloEnPeriodo2.getCuposReservados()).thenReturn(2);
		when (vueloEnPeriodo2.getCuposLibres()).thenReturn(10);
		
		vueloFueraDePeriodo = mock (Catalogo.class);
		when (vueloFueraDePeriodo.esDeCategoria(Categoria.VUELO)).thenReturn(true);
		when (vueloFueraDePeriodo.getFecha()).thenReturn(LocalDate.of(2026,11,1));
		
		excursionEnPeriodo = mock (Catalogo.class);
		when (excursionEnPeriodo.esDeCategoria(Categoria.VUELO)).thenReturn(false);
		when (excursionEnPeriodo.getFecha()).thenReturn(LocalDate.of(2026,12,1));
		
		periodo = new PeriodoDeFechas(LocalDate.of(2026, 12, 1), LocalDate.of(2026, 12, 31));
		
		negocio = mock(Negocio.class);
		when (negocio.getMisServicios()).thenReturn(List.of(vueloEnPeriodo, vueloEnPeriodo2, vueloFueraDePeriodo
				, excursionEnPeriodo));
		
		reportePorOcupacionYCupo = new ReporteOcupacionYCupoDisponible(negocio, periodo, Categoria.VUELO);
		 
	}
	
	@Test
	void testReporte() {
		
		List<Catalogo> listaEsperada = List.of(vueloEnPeriodo, vueloEnPeriodo2);
		
		assertEquals(listaEsperada, reportePorOcupacionYCupo.serviciosDeNegocioPorFechaYCategoria());
		
		assertEquals(22, reportePorOcupacionYCupo.getCuposTotales());
		
		assertEquals(7, reportePorOcupacionYCupo.getCuposReservados());
		
		assertEquals(15, reportePorOcupacionYCupo.getCuposLibres());
		
		reportePorOcupacionYCupo.aceptar(visitante);
		
		verify(visitante , times(1)).visitarOcupacionYCupoDisponible(reportePorOcupacionYCupo);
		
	}

}
