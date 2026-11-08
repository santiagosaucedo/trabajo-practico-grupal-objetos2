package Traslado.Plataforma;

import Direccion.Direccion;

public interface PlataformaDeTransporte {
    double estimarTraslado(int cantidadPasajeros, Direccion direccionDestino);
}
