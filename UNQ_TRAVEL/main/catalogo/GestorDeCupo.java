package catalogo;

import java.time.LocalDate;

public class GestorDeCupo {

	private int cuposTotales;
	
	private int cuposReservados;
	 
	private LocalDate fechaCupo;
	
	public GestorDeCupo(int cuposTotales, LocalDate fechaCupo) {
	    if (cuposTotales < 0) {
	        throw new RuntimeException("Los cupos totales no pueden ser negativos.");
	    }
	    this.cuposTotales = cuposTotales;
	    this.fechaCupo = fechaCupo;
	}
	 
	public void reservarCupo(){
		if(!this.sePuedeReservarCupo()) {
			throw new RuntimeException("No hay espacio para reservar cupos.");
		}
		cuposReservados = cuposReservados + 1;
	}

	public boolean sePuedeReservarCupo() {
		if (cuposReservados < cuposTotales) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean sePuedeQuitarCupo() {
		if (cuposReservados > 0) {
			return true;
		} else {
			return false;
		}
	}

	public void quitarCupoReservado(){
		if(!this.sePuedeQuitarCupo()) {
	        throw new RuntimeException("No hay cupos para quitar");
		}
		cuposReservados = cuposReservados - 1;
	}
	
	public int getCuposLibres() {
		return cuposTotales - cuposReservados;
	}

	public boolean hayCuposDisponibles() {
		return this.sePuedeReservarCupo();
	}
	
	public LocalDate getFecha() {
		return fechaCupo;
	}

	public int getCuposTotales() {
		return cuposTotales;
	}

	public int getCuposReservados() {
		return cuposReservados;
	}

}
