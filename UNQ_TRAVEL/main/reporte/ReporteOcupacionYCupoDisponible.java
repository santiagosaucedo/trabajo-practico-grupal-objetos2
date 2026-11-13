package reporte;

import java.util.List;
import catalogo.Catalogo;
import catalogo.Categoria;
import visitanteReporte.VisitanteReporte;

public class ReporteOcupacionYCupoDisponible implements Reporte {
	
	private Negocio negocio;
	 
	private PeriodoDeFechas periodo;
	
	private Categoria categoria;
	
	public ReporteOcupacionYCupoDisponible (Negocio negocio, PeriodoDeFechas periodoFechas, Categoria categoria) {
		
		this.negocio = negocio;
		
		this.periodo = periodoFechas;
		
		this.categoria = categoria;
		
	}

	public List<Catalogo> serviciosDeNegocioPorFechaYCategoria(){
		return negocio.getMisServicios().stream().filter
				(servicio -> servicio.esDeCategoria(categoria) && periodo.laFechaEstaEnElPeriodo(servicio.getFecha())).toList();
	}
		
	@Override
	public void aceptar(VisitanteReporte visitante) {
		visitante.visitarOcupacionYCupoDisponible(this);
	}
	
	public int getCuposTotales() {
		return this.serviciosDeNegocioPorFechaYCategoria().stream().mapToInt(servicio -> servicio.getCuposTotales()).sum();
	}
	
	public int getCuposReservados() {
		return this.serviciosDeNegocioPorFechaYCategoria().stream().mapToInt(servicio -> servicio.getCuposReservados()).sum();
	}

	public int getCuposLibres() {
		return this.serviciosDeNegocioPorFechaYCategoria().stream().mapToInt(servicio -> servicio.getCuposLibres()).sum();
	}
}
