package br.com.bancoNext.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Conta {

	private String numConta;
	private double saldo;
	private Cliente cliente;
	private Pix pix;
	private TipoConta tipoConta;
	private Date data;
	private List<Cartao> cartoes;

	public void validaTipo(double saldo) {
		if (this.saldo <= 5000) {
			cliente.setTipo(TipoCliente.Comum);
		} else if (this.saldo > 5000 && saldo <= 14999) {
			cliente.setTipo(TipoCliente.Premium);
		} else {
			cliente.setTipo(TipoCliente.Super);
		}
	}

	public void alteraCartaoCredito(CartaoCredito cartaoCredito) {

		List<Cartao> lCartao = new ArrayList<Cartao>();
		for (Cartao cartao : this.cartoes) {
			if (cartao.getClass().getSimpleName().contains("credito")) {
				lCartao.add(cartaoCredito);
			} else {
				lCartao.add(cartao);
			}

			this.cartoes = lCartao;
		}

	}

	public List<Cartao> getCartoes() {
		return cartoes;
	}

	public void addCartao(Cartao cartao) {
		if (this.cartoes == null) {
			this.cartoes = new ArrayList<Cartao>();
		}
		
		this.cartoes.add(cartao);
	}

	public Pix getPix() {
		return pix;
	}

	public void setPix(Pix pix) {
		this.pix = pix;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getNumConta() {
		return numConta;
	}

	public void setNumConta(String numero) {
		this.numConta = numero;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}
