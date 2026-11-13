package reporte;

import java.time.LocalDate;

public class PeriodoDeFechas {

	private LocalDate fechaMinima;
	
	private LocalDate fechaMaxima;
	
	public PeriodoDeFechas(LocalDate fechaMin, LocalDate fechaMax) {
		
		this.fechaMaxima = fechaMax;
		
		this.fechaMinima = fechaMin;
		
	}
	
	public boolean laFechaEstaEnElPeriodo(LocalDate fecha) {
		return (fecha.isAfter(fechaMinima) && fecha.isBefore(fechaMaxima))
				||
			   (fecha.isEqual(fechaMinima) || fecha.isEqual(fechaMaxima));
	}
	
}
