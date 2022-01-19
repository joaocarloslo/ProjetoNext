package br.com.bancoNext.beans;

public class CartaoDebito extends Cartao{

	public CartaoDebito(String numero, String bandeira, String senha, boolean ativo, double limite) {
		super(numero, bandeira, senha, ativo);
		this.limitePorTransacao = limite;
	}

	private double limitePorTransacao;

	public double getLimitePorTransacao() {
		return limitePorTransacao;
	}

	public void setLimitePorTransacao(double limitePorTransacao) {
		this.limitePorTransacao = limitePorTransacao;
	}
	
}
