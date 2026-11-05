package catalogo;

import tipoDeValor.TipoDeValor;

public class AtributoDinamico {

	private String nombre;
	
	private boolean indicadorObligatoriedad;
	
	private TipoDeValor tipoValor;
	
	private Object valor;
	
	public AtributoDinamico (String nombre, boolean indicadorObligatoriedad, TipoDeValor tipoValor, Object valor){
		this.nombre = nombre;
		this.indicadorObligatoriedad = indicadorObligatoriedad;
		this.tipoValor = tipoValor;
		this.valor = valor;
	}
	
	private boolean tieneValorSiEsObligatorio() {
	    return !indicadorObligatoriedad || valor != null;
	}
	
	private boolean suValorAsignadoEsValidoConSuTipo() {
		return valor == null || tipoValor.esMismoTipo(valor);
	}
	
	public void setValor(Object valor) { 
		this.valor = valor; 
	}
	
	public boolean esValido() {
		return this.tieneValorSiEsObligatorio() && suValorAsignadoEsValidoConSuTipo();
	}
	
}
