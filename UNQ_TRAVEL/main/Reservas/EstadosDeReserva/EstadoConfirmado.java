package Reservas.EstadosDeReserva;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.Reserva;

public class EstadoConfirmado extends EstadoDeReserva {
	
	
	@Override
	public void notificarA(ObservadorReserva observador, Reserva reserva) {
	    observador.reservaConfirmada(reserva);
	}
	
	@Override 
	public void realizarCheking(Reserva unaReserva) {
		
		unaReserva.cambiarA(new EstadoCheking());
	}
	
	@Override
	public void cancelarReserva(Reserva unaReserva) {
		
		unaReserva.liberarCupoDeServicios();
		unaReserva.reembolsarServiciosYTasas();
		unaReserva.cambiarA(new EstadoCancelado());
		
	}
}
