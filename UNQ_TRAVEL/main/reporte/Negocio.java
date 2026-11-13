package reporte;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import catalogo.Catalogo;

public class Negocio {
	
	private List<Catalogo> misServicios;
	
	public Negocio (List<Catalogo> servicios) {
		misServicios = servicios;
	}
	
	public List<Catalogo> getMisServicios(){
		return misServicios;
	}

	public List<Catalogo> getServiciosOrdenadosPorCantidadDeReservas() {
		Stream<Catalogo> servicios = misServicios.stream();
		return servicios.sorted((s1,s2) -> Integer.compare(s2.getCuposReservados(), s1.getCuposReservados())).toList();
	}
	
	public Map<String, List<Catalogo>> getServiciosPorProveedor(){
		Stream<Catalogo> servicios = misServicios.stream();
		return servicios.collect(Collectors.groupingBy(servicio -> servicio.getProveedor()));
	}

}
