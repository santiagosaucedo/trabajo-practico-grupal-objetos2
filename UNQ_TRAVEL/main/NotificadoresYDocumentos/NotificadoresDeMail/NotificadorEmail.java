package NotificadoresYDocumentos.NotificadoresDeMail;

import NotificadoresYDocumentos.ObservadorReserva.ObservadorReserva;
import Reservas.Reserva;

public class NotificadorEmail implements ObservadorReserva {
	
	private MailSender unMailSender;

	public NotificadorEmail(MailSender unMailSender) {
		
		this.unMailSender = unMailSender;
	}
	
	@Override
	public void reservaConfirmada(Reserva unaReserva) {

	    unMailSender.enviarMail(unaReserva.getDireccionMailCliente(),   
	                            "Reserva confirmada",                
	                            "Tu reserva fue confirmada.",        
	                            null);                               
	}
	
	@Override
	public void viajeComenzado(Reserva unaReserva) {

	    unMailSender.enviarMail(unaReserva.getDireccionMailCliente(),   
	                            "Reserva iniciada",                
	                            "Tu viaje comenzó",        
	                            null);                               
	}
	
	@Override
	public void reservaFinalizada(Reserva unaReserva) {

	    unMailSender.enviarMail(unaReserva.getDireccionMailCliente(),   
	                            "Reserva finalizada",                
	                            "Tu reserva fue finalizada.",        
	                            null);                               
	}
	
	

}

