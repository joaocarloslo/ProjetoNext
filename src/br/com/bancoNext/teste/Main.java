package br.com.bancoNext.teste;

import java.util.List;
import java.util.UUID;

import Util.BancoDeDados;
import Util.Menu;
import Util.Utils;
import br.com.bancoNext.Bo.ClienteBo;
import br.com.bancoNext.Bo.ContaBo;
import br.com.bancoNext.beans.Cliente;
import br.com.bancoNext.beans.Conta;
import br.com.bancoNext.beans.Endereco;
import br.com.bancoNext.beans.Pix;
import br.com.bancoNext.beans.TipoChavePix;
import br.com.bancoNext.beans.TipoConta;

public class Main {

	static Utils utils = new Utils();

	public static void main(String[] args) {

		Menu menu = new Menu();
		int opc = 0;

		// MENU COM OPÇÂO

		while (opc != 8) {

			menu.acessarMenu();
			opc = Integer.parseInt(utils.lerConsole("Digite a opção desejada."));

			switch (opc) {

			case 1:
				
				Cliente cliente = Main.cadastrarCliente();
				
				menu.acessarMenuConta();
				int opcaoConta = Integer.parseInt(utils.lerConsole("Digite a opção desejada."));
				
				String cpf = cliente.getCpf();
				
				List<Conta>lConta = BancoDeDados.buscarContaPorCliente(cpf);
				for(Conta conta : lConta) {
					if(opcaoConta == conta.getTipoConta().getId()) {
						System.out.println("Conta ja criada.");
						continue;
					}
				}
				
				if(opcaoConta == 1) {
					new ContaBo(cliente,TipoConta.corrente);
				}else {
					new ContaBo(cliente,TipoConta.poupanca);
				}
				
				break;

			// TRANSFERENCIA
			case 2:	
				
				String numeroContaRecebe = utils.lerConsole("Digite a conta que vai receber o valor. ");
				Conta contaRecebe = BancoDeDados.buscaContaPorNumero(numeroContaRecebe);
				
				if(contaRecebe == null) {
					continue;
				}
				
				String numeroContaPaga = utils.lerConsole  ("Digite a conta que vai pagar o valor. ");
				Conta contaPaga = BancoDeDados.buscaContaPorNumero(numeroContaPaga);
				
				if(contaPaga == null) {
					continue;
				}
				
				ContaBo contaBo = new ContaBo(contaPaga);
				double valor = Double.parseDouble(utils.lerConsole("Digite a quantia a ser transferida. R$: "));
				contaBo.transferirDeContaParaConta(contaRecebe, valor);
				contaBo.consultarSaldo();
				
				break;

			// DEPOSITO
			case 3:
				
				String numeroConta = utils.lerConsole("Digite a conta que vai depositar. ");
				Conta conta = BancoDeDados.buscaContaPorNumero(numeroConta);
				
				if(conta == null) {
					continue;
				}
				
				contaBo = new ContaBo(conta);
				valor = Double.parseDouble(utils.lerConsole("Digite a quantia a ser depositada. R$: "));
				contaBo.depositar(valor);
				
				System.out.println("Sua conta é do tipo: " );
				break;

			// SALDO
			case 4:
				
				numeroConta = utils.lerConsole("Digite a conta. ");
				conta = BancoDeDados.buscaContaPorNumero(numeroConta);
				
				if(conta == null) {
					continue;
				}
				
				contaBo = new ContaBo(conta);
				contaBo.consultarSaldo();
				break;
				
			case 5:
				
				List<Conta> lConta1 = BancoDeDados.buscarTodasContas();
				
				for(Conta conta1 : lConta1) {
					contaBo = new ContaBo(conta1);
					contaBo.creditoDebito();
				}
				
				break;
				
			case 6:
				
				numeroConta = utils.lerConsole("Digite a conta. ");
				conta = BancoDeDados.buscaContaPorNumero(numeroConta);
				
				if(conta == null) {
					continue;
				}
				
				menu.acessarMenuPix();
				int opcaoPix = Integer.parseInt(utils.lerConsole("Digite a opção desejada."));
				
				String chavePix = "";
				TipoChavePix tipoChavePix = null;
				
				
				if(opcaoPix == 4) {
					chavePix = UUID.randomUUID().toString();
					tipoChavePix = TipoChavePix.aleatorio;
				}else {
					chavePix = utils.lerConsole("Digite a chave PIX.");
				}
				
				Pix pix = new Pix();
				pix.setAtivo(true);
				pix.setChave(chavePix);
				
				
				if(opcaoPix == 1) {
					tipoChavePix = TipoChavePix.cpf;
				}else if(opcaoPix == 2) {
					tipoChavePix = TipoChavePix.email;
				}else if(opcaoPix == 3) {
					tipoChavePix = TipoChavePix.telefone;
				}
				
				pix.setTipoChave(tipoChavePix);
				
				contaBo = new ContaBo(conta);
				contaBo.adicionarPix(pix);
				
				break;
				
			case 7:
				
				numeroConta = utils.lerConsole("Digite a conta que vai pagar o valor. ");
				conta = BancoDeDados.buscaContaPorNumero(numeroConta);
				
				if(conta == null) {
					continue;
				}
				
				chavePix = utils.lerConsole("Digite a chave PIX ");
				
				Conta contaRecebePix = BancoDeDados.buscarContaPorPix(chavePix);
				
				if(contaRecebePix == null) {
					System.out.println("Chave PIX não confere.");
					continue;
				}
				
				
				valor = Double.parseDouble(utils.lerConsole("Digite a quantia a ser transferida. R$: "));
				
				contaBo = new ContaBo(conta);
				contaBo.transferirDeContaParaConta(contaRecebePix, valor);
				
				break;
				
			// SAIR
			case 8:
				System.out.println("Obrigado por usar nossso console.");
				utils.fechaConsole();
				System.exit(0);
				break;
			default:
				System.out.println("Opção inválida.");

			}
		}
	}

	public static Cliente cadastrarCliente() {
		
		ClienteBo clienteBo = new ClienteBo();
		Endereco endereco = new Endereco();
		
		String nome = utils.lerConsole("Digite seu nome completo.  >>  ");
		String cpf = utils.lerConsole("Digite seu CPF (11 Dígitos).>>  ");

		while (clienteBo.validaCpf(cpf) == false) {
			cpf = utils.lerConsole("Digite seu CPF (11 Dígitos).>>  ");
		}

		String dataNasc = utils.lerConsole("Digite sua data de nascimento.  >>  ");
		String logradouro = utils.lerConsole("Digite seu logradouro.  >>  ");
		int numero = Integer.parseInt(utils.lerConsole("Digite seu número.  >>  "));
		String cep = utils.lerConsole("Digite seu cep.  >>  ");
		String bairro = utils.lerConsole("Digite seu bairro.  >>  ");
		String cidade = utils.lerConsole("Digite sua cidade.  >>  ");
		String estado = utils.lerConsole("Digite seu estado.  >>  ");

		System.out.println("---Usuario cadastrado com sucesso.---");
		
		endereco.setBairro(bairro);
		endereco.setCep(cep);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setNumero(numero);
		endereco.setLogradouro(logradouro);
		
		return clienteBo.cadastrarCliente(nome, cpf, dataNasc, endereco);
	}
}
