package tipoDeValor;

public class TipoNumerico implements TipoDeValor {

	@Override
	public boolean esMismoTipo(Object valor) {
		return valor instanceof Number;
	}

}
