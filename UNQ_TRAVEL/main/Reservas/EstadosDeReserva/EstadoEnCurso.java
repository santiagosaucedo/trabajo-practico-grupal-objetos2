package Reservas.EstadosDeReserva;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.OperacionInvalidaException;
import Reservas.Reserva;

public class EstadoEnCurso extends EstadoDeReserva {
	
	@Override
	public void notificarA(ObservadorReserva observador, Reserva reserva) {
	    observador.viajeComenzado(reserva);
	}
	
	@Override
	public void finalizarViaje(Reserva unaReserva) {
		
		unaReserva.cambiarA(new EstadoFinalizado());
	}
	
	@Override
	public void pasarAlSiguienteServicio(Reserva unaReserva) {
		
		this.validarPasarAlSiguienteServicio(unaReserva);
		unaReserva.registrarServicioDisfrutado();
		
	}
	
	private void validarPasarAlSiguienteServicio(Reserva unaReserva) {
		
		if(unaReserva.getServiciosDisfrutados() >= unaReserva.cantidadDeServicios()) {
			
			throw new OperacionInvalidaException("La reserva no tiene servicios para disfrutar");
		}
		
	}
}
