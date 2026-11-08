package NotificadoresYDocumentos.ObservadorReserva;

import Reservas.Reserva;

public interface ObservadorReserva {
    void reservaFinalizada(Reserva reserva);
    void reservaConfirmada(Reserva reserva);
    void checkingRealizado(Reserva reserva);
    void viajeComenzado(Reserva reserva);
    void reservaCancelada(Reserva reserva);
}
