package br.com.bancoNext.beans;

public abstract class Cartao {
	
	private String idCartao;
	private String numero;
	private String bandeira;
	private String senha;
	private boolean ativo;
	
	public Cartao(String numero, String bandeira, String senha, boolean ativo) {
		this.numero = numero;
		this.bandeira = bandeira;
		this.senha = senha;
		this.ativo = ativo;
	}
	
	
	public String getIdCartao() {
		return idCartao;
	}
	public void setIdCartao(String idCartao) {
		this.idCartao = idCartao;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBandeira() {
		return bandeira;
	}
	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
}
