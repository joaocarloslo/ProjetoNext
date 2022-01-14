package br.com.bancoNext.Bo;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import Util.BancoDeDados;
import br.com.bancoNext.beans.Cliente;
import br.com.bancoNext.beans.Conta;
import br.com.bancoNext.beans.Pix;
import br.com.bancoNext.beans.TipoCliente;
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

	
	
	public void atualizarConta(Conta conta) {
		if(conta.getSaldo() <=5000) {
			 conta.getCliente().setTipo(TipoCliente.Comum);
		}else if(conta.getSaldo() > 5000 && conta.getSaldo() <=14999) {
			conta.getCliente().setTipo(TipoCliente.Premium);
		}else {
			conta.getCliente().setTipo(TipoCliente.Super);
		}
		
		this.conta = conta;
		BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);
	}
	
	
	public void adicionarPix(Pix pix) {
		this.conta.setPix(pix);

		BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);
	}

	public Date adicionarMes() {

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 1);
		Date data = cal.getTime();

		return data;
	}

	public void creditoDebito() {

		if (this.conta.getData().before(new Date())) {

			if (this.conta.getTipoConta() == TipoConta.corrente) {
				double valor = this.conta.getSaldo();
				valor -= valor * 0.45;

			} else {
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
		
		System.out.println("");
		System.out.println("Seu número da conta é: " + conta.getNumConta());
		System.out.println("");
		
		return conta;

	}

	public boolean transferirDeContaParaConta(Conta contaRecebe, double valor) {
		if (this.conta.getSaldo() >= valor) {

			if (this.conta.getTipoConta() == TipoConta.corrente
					&& this.conta.getTipoConta().getId() != contaRecebe.getTipoConta().getId()) {

				double valorSaldo = this.conta.getSaldo();

				if (valorSaldo - 5.60 < valor) {
					System.out.println("");
					System.out.println("Saldo insuficiente.");
					System.out.println("");

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

			System.out.println("");
			System.out.println("Transferência realizada com sucesso");
			System.out.println("-----------------------------------");
			System.out.println("");


			return true;
		}
		System.out.println("");
		System.out.println("Saldo Insuficiente.");
		System.out.println("");
		
		return false;
	}

	public boolean transferirDeContaParaContaPix(Conta contaRecebe, double valor) {
		
		if (this.conta.getSaldo() >= valor) {

			double meuSaldo = this.conta.getSaldo();
			meuSaldo -= valor;
			this.conta.setSaldo(meuSaldo);

			double saldoOutraConta = contaRecebe.getSaldo();
			saldoOutraConta += valor;
			contaRecebe.setSaldo(saldoOutraConta);

			BancoDeDados.insereConta(contaRecebe.getNumConta(), contaRecebe);
			BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);

			System.out.println("");
			System.out.println("Transferência realizada com sucesso");
			System.out.println("-----------------------------------");
			System.out.println("");

			return true;
		}
		System.out.println("");
		System.out.println("Saldo Insuficiente.");
		System.out.println("");

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
		TipoCliente tipo = this.conta.getCliente().getTipo();

		double valor = this.conta.getSaldo();
		
		System.out.println("");
		System.out.println("CLIENTE : " + nome);
		System.out.println("CPF     : " + cpf);
		System.out.println("SALDO   : " + valor);
		System.out.println("TIPO    : " + tipo);
		System.out.println("");
		
	}

}
