package br.com.bancoNext.Bo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import Util.BancoDeDados;
import br.com.bancoNext.beans.Cliente;
import br.com.bancoNext.beans.Conta;
import br.com.bancoNext.beans.Pix;
import br.com.bancoNext.beans.TipoConta;

public class ContaBo {

	Conta contaBo = new Conta();

	private Conta conta;

	public ContaBo(Conta conta) {
		this.conta = conta;
	}

	public ContaBo(Cliente cliente, TipoConta tipoConta) {
		this.conta = this.gerarConta(cliente, tipoConta);
	}

	
	public void adicionarPix(Pix pix) {
		this.conta.setPix(pix);
		
		BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);
	}
	
	
	public Date adicionarMes() {
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date data = cal.getTime();
		
		conta.setData(data);
		return data;
	}
	
	public void creditoDebito() {
		
		if(this.conta.getData().before(new Date())) {
			
			if(this.conta.getTipoConta() == TipoConta.corrente) {
				double valor = this.conta.getSaldo();
				valor -= valor * 0.45;
			
			}else {
				double valor = this.conta.getSaldo();
				valor += valor * 0.03;
				this.depositar(valor);
			}
			
			Date data = this.adicionarMes();	
			conta.setData(data);
			BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);
		
		}
	}
	
	
	private Conta gerarConta(Cliente cliente, TipoConta tipoConta) {
		Conta conta = new Conta();
		conta.setCliente(cliente);
		conta.setNumConta(UUID.randomUUID().toString());
		conta.setSaldo(0);
		conta.setTipoConta(tipoConta);
		
		
		Date data = this.adicionarMes();	
		conta.setData(data);

		BancoDeDados.insereConta(conta.getNumConta(), conta);
		System.out.println("Seu número da conta é: "+conta.getNumConta());
		
		return conta;

	}

	public boolean transferirDeContaParaConta(Conta contaRecebe, double valor) {
		if (this.conta.getSaldo() >= valor) {
			
			if(this.conta.getTipoConta() == TipoConta.corrente && this.conta.getTipoConta().getId() != contaRecebe.getTipoConta().getId()) {
				
				double valorSaldo = this.conta.getSaldo();
				
				if(valorSaldo - 5.60 < valor) {
					System.out.println("Saldo insuficiente.");
					return false;
				}
				
				valorSaldo -= 5.60;
				this.conta.setSaldo(valorSaldo);
				
			}
			
			double meuSaldo = this.conta.getSaldo();
			meuSaldo -= valor;
			this.conta.setSaldo(meuSaldo);

			double saldoOutraConta = contaRecebe.getSaldo();
			saldoOutraConta += valor;
			contaRecebe.setSaldo(saldoOutraConta);

			BancoDeDados.insereConta(contaRecebe.getNumConta(), contaRecebe);
			BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);

			System.out.println("Transferência realizada com sucesso");
			System.out.println("-----------------------------------");

			return true;
		}
		System.out.println("Saldo Insuficiente.");
		return false;
	}

	public void depositar(double valor) {
		double meuSaldo = this.conta.getSaldo();
		meuSaldo += valor;
		this.conta.setSaldo(meuSaldo);

		BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);
	}

	public void consultarSaldo() {
		String nome = this.conta.getCliente().getNome();
		String cpf = this.conta.getCliente().getCpf();

		double valor = this.conta.getSaldo();

		System.out.println("O cliente " + nome + " com o CPF " + cpf + " possui o saldo de R$ " + valor);
	}

}
