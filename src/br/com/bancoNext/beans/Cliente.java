package br.com.bancoNext.beans;

public class Cliente {

	private String nome;
	private String cpf;
	private TipoCliente tipo;
	private String dataNasc;
	private Endereco endereco;
	
	
	public Cliente() {
		
	}
	
	public Cliente(String nome, String cpf, String dataNasc) {
		this.nome = nome;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
	}
	
	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public TipoCliente getTipo() {
		return tipo;
	}
	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo;
	}
	
}
