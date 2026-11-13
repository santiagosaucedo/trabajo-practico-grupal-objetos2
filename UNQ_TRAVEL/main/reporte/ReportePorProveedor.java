package reporte;

import java.util.Map;
import java.util.stream.Collectors;

import visitanteReporte.VisitanteReporte;

public class ReportePorProveedor {

	private Negocio negocio;
	
	
	public void aceptar(VisitanteReporte visitante) {
		visitante.visitarReportePorProveedor(this);
	}
	
	public ReportePorProveedor(Negocio negocio) {
		this.negocio = negocio;
	}
	
	//entrySet convierte un mapa en una lista de pares. Cada para sabe dar su clave (getKey) y su valor (getValue).
	public Map<String, Double> proveedoresYSuPorcentajeEnElNegocio(){
		int cantidadDeServicios = negocio.getMisServicios().size();
		return negocio.getServiciosPorProveedor().entrySet().
				stream().collect(Collectors.toMap
						//collect cierra un stream y lo convierte en lo que se ponga en "Collectors.To..."
						(entry -> entry.getKey(), //entry es un elemento del map, aca tomamos la clave y la dejamos igual
						entry -> entry.getValue().size() * 100.0 / cantidadDeServicios )); //Aca tomamos su valor, y le hacemos cambios
	}
	
}
