package br.com.bancoNext.beans;

public enum TipoChavePix {

	cpf(1), email(2), telefone(3), aleatorio(4);

	private int id;

	TipoChavePix(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
