package br.com.bancoNext.beans;

import java.util.Date;

public class Compras {

	private Date dataCompra;
	private String compra;
	private double valor;

	public Compras(Date data, double valor, String compra) {
		this.dataCompra = data;
		this.valor = valor;
		this.compra = compra;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	public String getCompra() {
		return compra;
	}

	public void setCompra(String compra) {
		this.compra = compra;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}
}
