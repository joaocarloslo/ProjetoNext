package br.com.bancoNext.beans;

public enum TipoConta {

	corrente(1), poupanca(2);

	private int id;

	TipoConta(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

}