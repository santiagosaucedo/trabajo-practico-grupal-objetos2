package NotificadoresYDocumentos.ObservadorReserva;

import Reservas.Reserva;

public interface ObservadorReserva {

    default void reservaFinalizada(Reserva reserva) {}
    default void reservaConfirmada(Reserva reserva) {}
    default void checkingRealizado(Reserva reserva) {}
     default void viajeComenzado(Reserva reserva) {}
    default void reservaCancelada(Reserva reserva) {} 
}