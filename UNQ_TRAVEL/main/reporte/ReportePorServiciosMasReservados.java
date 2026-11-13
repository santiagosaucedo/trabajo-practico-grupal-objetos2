package reporte;

import java.util.List;
import catalogo.Catalogo;
import visitanteReporte.VisitanteReporte;

public class ReportePorServiciosMasReservados implements Reporte {

	private Negocio negocio;
	
	private PeriodoDeFechas periodo;
	
	public ReportePorServiciosMasReservados(Negocio negocio, PeriodoDeFechas periodo) {
		this.negocio = negocio;
		
		this.periodo = periodo;
	}
	
	@Override
	public void aceptar(VisitanteReporte visitante) {
		visitante.visitarReporteServiciosMasReservados(this);
	}

	public List<Catalogo> serviciosDeNegocioPorPeriodoYCantidadDeReservas(){
		return negocio.getServiciosOrdenadosPorCantidadDeReservas().stream().
				filter(servicio -> periodo.laFechaEstaEnElPeriodo(servicio.getFecha())).toList();
	}
	
	//Asumo que los precios y descuentos no cambian entre ventas y cada elemento es un servicio en una fecha determinada
	public double costoPromedio() {
		List<Catalogo> servicios = this.serviciosDeNegocioPorPeriodoYCantidadDeReservas();
		int ventas = this.cantidadDeVentas(servicios);
		if (ventas == 0) {
			throw new RuntimeException("No hay promedio, no se han realizado ventas");
		}
		return servicios.stream().mapToDouble(servicio -> servicio.getPrecioFinal() * servicio.getVentas()).sum() / ventas;
	}
	
	public int cantidadDeVentas(List<Catalogo> servicios) {
		return servicios.stream().mapToInt(servicio -> servicio.getVentas()).sum();
	}
	
}
