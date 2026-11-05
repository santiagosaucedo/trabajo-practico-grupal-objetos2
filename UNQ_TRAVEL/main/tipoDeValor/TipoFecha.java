package tipoDeValor;

import java.time.LocalDate;

public class TipoFecha implements TipoDeValor {

	@Override
	public boolean esMismoTipo(Object valor) {
		return valor instanceof LocalDate;
	}
	
	
	
}
