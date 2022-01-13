package br.com.bancoNext.beans;

public enum TipoCliente {
	
	Comum(1),Super(2),Premium(3);
	
	private int id;
	
	TipoCliente(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
}
