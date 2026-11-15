package Reservas.EstadosDeReserva;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.Reserva;

public class EstadoCheking extends EstadoDeReserva {
	
	@Override
	public void notificarA(ObservadorReserva observador, Reserva reserva) {
	    observador.checkingRealizado(reserva);
	}
	
	@Override
	public void comenzarViaje(Reserva unaReserva) {

	    unaReserva.cambiarA(new EstadoEnCurso());
	}
	
	@Override
	public void cancelarReserva(Reserva unaReserva) {
		
		unaReserva.liberarCupoDeServicios();
		unaReserva.reembolsarServiciosYTasas();
		unaReserva.cambiarA(new EstadoCancelado());
		
	}
}
