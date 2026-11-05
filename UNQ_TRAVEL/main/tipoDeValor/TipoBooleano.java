package tipoDeValor;

public class TipoBooleano implements TipoDeValor {

	@Override
	public boolean esMismoTipo(Object valor) {
		return valor instanceof Boolean;
	}

}
