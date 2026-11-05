package tipoDeValor;

public class TipoTexto implements TipoDeValor{

	@Override
	public boolean esMismoTipo(Object valor) {
		return valor instanceof String;
	}

}
