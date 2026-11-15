package Reservas.EstadosDeReserva;


import Reservas.OperacionInvalidaException;
import Reservas.Reserva;
import catalogo.Catalogo;

public class EstadoPendiente extends EstadoDeReserva  {
	
	@Override
	public void agregarServicio(Reserva unaReserva, Catalogo servicio) {
	    
		unaReserva.incorporarServicio(servicio);
	}
	
	@Override
	
	public void quitarServicio(Reserva unaReserva, Catalogo servicio) {
	    
		unaReserva.retirarServicio(servicio);
	}
	
	@Override
	
	public void confirmarReserva(Reserva unaReserva) {
	    
		this.validarServicios(unaReserva);
		this.decrementarCupoEnServiciosConVenta(unaReserva);
		unaReserva.cambiarA( new EstadoConfirmado() );
		
	}
	
	private void validarServicios(Reserva unaReserva) {
		
		if(unaReserva.getServicios().isEmpty()) {
			 
			   throw new OperacionInvalidaException("La reserva no tiene servicios"); } 
			 
		 if(!unaReserva.getServicios().stream().allMatch(servicio -> servicio.sePuedeReservarCupo())) {
			 
			 throw new OperacionInvalidaException("No hay suficientes cupos en al menos uno de los servicios");
		 }  
		}
		
		 
	
	private void decrementarCupoEnServiciosConVenta(Reserva unaReserva) {
		
		unaReserva.getServicios().stream()
				  .forEach(servicio -> {
					 servicio.decrementarCupoConVenta(); 
				  });
		
	}
	
	@Override
	public void cancelarReserva(Reserva unaReserva) {
		
		unaReserva.cambiarA(new EstadoCancelado());
		
	}
}

