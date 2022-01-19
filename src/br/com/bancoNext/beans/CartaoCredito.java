package br.com.bancoNext.beans;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CartaoCredito extends Cartao{
	
	private double valorFatura;
	private double limite;
	private List<Compras> compras;
	private Date dataVenc;
	
	
	
	public CartaoCredito(String numero, String bandeira, String senha, boolean ativo, double limite) {
		super(numero, bandeira, senha, ativo);
		this.limite = limite;
		this.compras = new ArrayList<Compras>();
		this.dataVenc = this.adicionarMes();
		this.valorFatura = 0;
		
	}
	
	public Date adicionarMes() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date data = cal.getTime();

		return data;
	}
	
	
	
	public double getValorFatura() {
		return valorFatura;
	}
	public void setValorFatura(double valorFatura) {
		this.valorFatura = valorFatura;
	}
	
	public List<Compras> getCompras() {
		return compras;
	}
	public void addCompras(Compras compras) {
		this.compras.add(compras);
	}
	public Date getDataVenc() {
		return dataVenc;
	}
	public void setDataVenc(Date dataVenc) {
		this.dataVenc = dataVenc;
	}
	public double getLimite() {
		return limite;
	}
	public void setLimite(double limite) {
		this.limite = limite;
	}
	
}
