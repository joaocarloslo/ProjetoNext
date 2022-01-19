package br.com.bancoNext.Bo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import Util.BancoDeDados;
import br.com.bancoNext.beans.Cartao;
import br.com.bancoNext.beans.CartaoCredito;
import br.com.bancoNext.beans.CartaoDebito;
import br.com.bancoNext.beans.Cliente;
import br.com.bancoNext.beans.Compras;
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
		if (conta.getSaldo() <= 5000) {
			conta.getCliente().setTipo(TipoCliente.Comum);
		} else if (conta.getSaldo() > 5000 && conta.getSaldo() <= 14999) {
			conta.getCliente().setTipo(TipoCliente.Premium);
		} else {
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

	public void creditoDebito(Conta conta) {

			if (this.conta.getTipoConta() == TipoConta.corrente) {
				double valor = this.conta.getSaldo();
				valor = (valor * 0.45) / 100;
				this.conta.setSaldo(this.conta.getSaldo() - valor);
				System.out.println("Foi descontado o valor de: R$ "+valor);
				
			} else {
				double valor = this.conta.getSaldo();
				valor = (valor * 0.03) / 100;
				this.conta.setSaldo(this.conta.getSaldo() + valor);
				System.out.println("Foi creditado o valor de: R$ "+valor);
			}

			Date data = this.adicionarMes();
			conta.setData(data);
			BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);

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

	public void adicionarCartao(Cartao cartao) {

		this.conta.addCartao(cartao);
		System.out.println("");
		System.out.println("Cartão gerado com sucesso.");
		System.out.println("O número do seu cartão é: " + cartao.getNumero());
		System.out.println("");
		BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);

	}

	public void comprarCartaoDebito(Cartao cartao, double valor) {

		CartaoDebito cartaoDebito = (CartaoDebito) cartao;

		if (cartaoDebito.getLimitePorTransacao() >= valor) {

			double saldoConta = this.conta.getSaldo();
			if (saldoConta >= valor) {

				saldoConta -= valor;
				this.conta.setSaldo(saldoConta);

				BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);
				System.out.println("Compra realizada com sucesso.");

			} else {
				System.out.println("Valor insuficiente.");
			}

		} else {
			System.out.println("Limite por transação não pode ser maior que o valor.");
		}
	}

	public void comprarCartaoCredito(Cartao cartao, double valor, String compra) {

		CartaoCredito cartaoCredito = (CartaoCredito) cartao;

		if (cartaoCredito.getLimite() >= valor) {

			Compras compras = new Compras(new Date(), valor, compra);
			
			cartaoCredito.addCompras(compras);
			double limite = cartaoCredito.getLimite();
			limite -= valor;
			cartaoCredito.setLimite(limite);

			double valorFatura = cartaoCredito.getValorFatura();
			valorFatura += valor;
			cartaoCredito.setValorFatura(valorFatura);

			this.conta.alteraCartaoCredito(cartaoCredito);
			
			BancoDeDados.insereConta(this.conta.getNumConta(), this.conta);
			System.out.println("");
			System.out.println("Compra de "+compra+ " realizada com sucesso.");

		} else {
			System.out.println("Valor insuficiente.");
		}
	}

	public void exibirFatura(Cartao cartao) {
		
		CartaoCredito cartaoCredito = (CartaoCredito) cartao;
		
		List<Compras>lCompra = cartaoCredito.getCompras();
		
		SimpleDateFormat sdfHora = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String data = sdf.format(cartaoCredito.getDataVenc());
		String dataHora = sdfHora.format(cartaoCredito.getDataVenc());
		
		
		System.out.println("|--------------BANCO NEXT-------------|");
		System.out.println("|                                     |");
		System.out.println("|               COMPRAS               |");
		System.out.println("|                                     |");
		System.out.println("|-------------------------------------|");
		
		for(Compras compra : lCompra) {
			double valorCompra = compra.getValor();
			String nomeCompra = compra.getCompra();
			System.out.println("|                                     |");
			System.out.println("|COMPRA:  "+nomeCompra);
			System.out.println("|DATA:    "+dataHora);
			System.out.println("|VALOR:   R$ "+valorCompra);
			System.out.println("|-------------------------------------|");
			
		}
		
		System.out.println("Seu limite restante é de: "+cartaoCredito.getLimite());
		System.out.println("Seu valor da fatura é de: "+cartaoCredito.getValorFatura());
		System.out.println("Seu cartão vence dia: "+data);
	}
}
