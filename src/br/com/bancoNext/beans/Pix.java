package br.com.bancoNext.beans;

public class Pix {
	
	private String chave;
	private TipoChavePix tipoChave;
	private boolean Ativo;
	
	public String getChave() {
		return chave;
	}
	public void setChave(String chave) {
		this.chave = chave;
	}
	public TipoChavePix getTipoChave() {
		return tipoChave;
	}
	public void setTipoChave(TipoChavePix tipoChave) {
		this.tipoChave = tipoChave;
	}
	public boolean isAtivo() {
		return Ativo;
	}
	public void setAtivo(boolean isAtivo) {
		this.Ativo = isAtivo;
	}
	
}
