package br.com.bancoNext.teste;

import java.util.List;
import java.util.UUID;

import Util.BancoDeDados;
import Util.Menu;
import Util.Utils;
import br.com.bancoNext.Bo.ClienteBo;
import br.com.bancoNext.Bo.ContaBo;
import br.com.bancoNext.beans.Cartao;
import br.com.bancoNext.beans.CartaoCredito;
import br.com.bancoNext.beans.CartaoDebito;
import br.com.bancoNext.beans.Cliente;
import br.com.bancoNext.beans.Conta;
import br.com.bancoNext.beans.Endereco;
import br.com.bancoNext.beans.Pix;
import br.com.bancoNext.beans.TipoChavePix;
import br.com.bancoNext.beans.TipoCliente;
import br.com.bancoNext.beans.TipoConta;

public class Main {

	static Utils utils = new Utils();
	static Cliente cliente = new Cliente();

	public static void main(String[] args) {

		Menu menu = new Menu();
		int opc = 0;

		// MENU COM OPÇÂO

		while (opc != 13) {

			menu.acessarMenu();
			opc = Integer.parseInt(utils.lerConsole("Digite a opção desejada:  "));

			switch (opc) {

			case 1:

				cliente = Main.cadastrarCliente();

				menu.acessarMenuConta();
				int opcaoConta = Integer.parseInt(utils.lerConsole("Digite a opção desejada:  "));

				String cpf = cliente.getCpf();

				List<Conta> lConta = BancoDeDados.buscarContaPorCliente(cpf);
				for (Conta conta : lConta) {
					if (opcaoConta == conta.getTipoConta().getId()) {
						System.out.println("Conta ja criada.");
						continue;
					}
				}

				if (opcaoConta == 1) {
					new ContaBo(cliente, TipoConta.corrente);
				} else if (opcaoConta == 2) {
					new ContaBo(cliente, TipoConta.poupanca);
				} else {
					new ContaBo(cliente, TipoConta.corrente);
					new ContaBo(cliente, TipoConta.poupanca);
				}

				break;

			// TRANSFERENCIA
			case 2:

				String numeroContaRecebe = utils.lerConsole("Digite a conta que vai receber o valor:  ");
				Conta contaRecebe = BancoDeDados.buscaContaPorNumero(numeroContaRecebe);

				if (contaRecebe == null) {
					continue;
				}

				String numeroContaPaga = utils.lerConsole("Digite a conta que vai pagar o valor:  ");
				Conta contaPaga = BancoDeDados.buscaContaPorNumero(numeroContaPaga);

				if (contaPaga == null) {
					continue;
				}

				ContaBo contaBo = new ContaBo(contaPaga);
				double valor = Double.parseDouble(utils.lerConsole("Digite a quantia a ser transferida:  R$: "));
				contaBo.transferirDeContaParaConta(contaRecebe, valor);

				contaBo.atualizarConta(contaRecebe);
				contaBo.atualizarConta(contaPaga);

				contaBo.consultarSaldo();

				break;

			// DEPOSITO
			case 3:

				String numeroConta = utils.lerConsole("Digite a conta que vai depositar:  ");
				Conta conta = BancoDeDados.buscaContaPorNumero(numeroConta);

				if (conta == null) {
					continue;
				}

				contaBo = new ContaBo(conta);
				valor = Double.parseDouble(utils.lerConsole("Digite a quantia a ser depositada:  R$: "));
				contaBo.depositar(valor);

				contaBo.atualizarConta(conta);

				contaBo.consultarSaldo();

				break;

			// SALDO
			case 4:

				numeroConta = utils.lerConsole("Digite a conta:  ");
				conta = BancoDeDados.buscaContaPorNumero(numeroConta);

				if (conta == null) {
					continue;
				}

				contaBo = new ContaBo(conta);
				contaBo.consultarSaldo();
				break;

			// COBRAR TAXAS E RENDIMENTOS
			case 5:
				
				numeroConta = utils.lerConsole("Digite a conta:  ");
				conta = BancoDeDados.buscaContaPorNumero(numeroConta);

				if (conta == null) {
					continue;
				}
				
				contaBo = new ContaBo(conta);
				contaBo.creditoDebito(conta);

				System.out.println("Taxas e rendimentos calculados com sucesso.");
				
				break;

			// CADASTRAR PIX
			case 6:

				numeroConta = utils.lerConsole("Digite a conta:  ");
				conta = BancoDeDados.buscaContaPorNumero(numeroConta);

				if (conta == null) {
					continue;
				}

				menu.acessarMenuPix();
				int opcaoPix = Integer.parseInt(utils.lerConsole("Digite a opção desejada:  "));

				String chavePix = "";
				TipoChavePix tipoChavePix = null;

				if (opcaoPix == 4) {
					chavePix = UUID.randomUUID().toString();
					tipoChavePix = TipoChavePix.aleatorio;
				} else {
					chavePix = utils.lerConsole("Digite a chave PIX:  ");
				}

				Pix pix = new Pix();
				pix.setAtivo(true);
				pix.setChave(chavePix);

				if (opcaoPix == 1) {
					tipoChavePix = TipoChavePix.cpf;
				} else if (opcaoPix == 2) {
					tipoChavePix = TipoChavePix.email;
				} else if (opcaoPix == 3) {
					tipoChavePix = TipoChavePix.telefone;
				}

				pix.setTipoChave(tipoChavePix);

				contaBo = new ContaBo(conta);
				contaBo.adicionarPix(pix);

				System.out.println("");
				System.out.println("Chave cadastrada com sucesso.");
				System.out.println("");

				break;

			// TRANSFERENCIA VIA PIX
			case 7:

				numeroConta = utils.lerConsole("Digite a conta que vai pagar o valor:  ");
				conta = BancoDeDados.buscaContaPorNumero(numeroConta);

				if (conta == null) {
					continue;
				}

				System.out.println("");
				chavePix = utils.lerConsole("Digite a chave PIX:  ");
				System.out.println("");

				Conta contaRecebePix = BancoDeDados.buscarContaPorPix(chavePix);

				if (contaRecebePix == null) {
					System.out.println("");
					System.out.println("Chave PIX não confere.");
					System.out.println("");
					continue;
				}

				valor = Double.parseDouble(utils.lerConsole("Digite a quantia a ser transferida:  R$: "));

				contaBo = new ContaBo(conta);
				contaBo.transferirDeContaParaContaPix(contaRecebePix, valor);

				System.out.println("");
				System.out.println("Transferência via PIX realizada com sucesso.");
				System.out.println("");

				break;

			case 8:

				numeroConta = utils.lerConsole("Digite a conta:  ");
				conta = BancoDeDados.buscaContaPorNumero(numeroConta);

				if (conta == null) {
					continue;
				}

				TipoCliente tipoCliente = conta.getCliente().getTipo();
				double limite = 0;

				if (tipoCliente == TipoCliente.Comum) {
					limite = 1000;

				} else if (tipoCliente == TipoCliente.Premium) {
					limite = 5000;

				} else if (tipoCliente == TipoCliente.Super) {
					limite = 10000;

				}

				System.out.println("Seu limite de cartão é: " + limite);

				System.out.println("");
				menu.acessarMenuSimNao();
				
				int opcaoCartao = Integer.parseInt(utils.lerConsole("Digite a opção desejada:  "));
				System.out.println("");
				
				if (opcaoCartao == 1) {

					String numero = UUID.randomUUID().toString();
					menu.acessarMenuBandeira();
					int opcaoBandeira = Integer.parseInt(utils.lerConsole("Digite a opção desejada:  "));

					String bandeira = "";

					if (opcaoBandeira == 1) {
						bandeira = "VISA";
					} else if (opcaoBandeira == 2) {
						bandeira = "MASTER";
					} else if (opcaoBandeira == 3) {
						bandeira = "ELO";
					}

					String senha = utils.lerConsole("Digite uma senha para o cartão:  ");

					System.out.println("");
					menu.acessarMenuSimNao();
					int opcaoCriar = Integer.parseInt(utils.lerConsole("Digite a opção desejada:  "));

					boolean ativo;

					if (opcaoCriar == 1) {
						ativo = true;
					} else {
						ativo = false;
						continue;
					}

					CartaoCredito cartaoCredito = new CartaoCredito(numero, bandeira, senha, ativo, limite);

					contaBo = new ContaBo(conta);
					contaBo.adicionarCartao(cartaoCredito);
				}

				break;

			case 9:

				numeroConta = utils.lerConsole("Digite a conta:  ");
				conta = BancoDeDados.buscaContaPorNumero(numeroConta);

				if (conta == null) {
					continue;
				}

				double limiteD = Double.parseDouble(utils.lerConsole("Digite o limite de transação:  "));
				System.out.println("");
				String numero = UUID.randomUUID().toString();

				menu.acessarMenuBandeira();
				int opcaoBandeira = Integer.parseInt(utils.lerConsole("Digite a opção desejada:  "));
				System.out.println("");

				String bandeira = "";

				if (opcaoBandeira == 1) {
					bandeira = "VISA";
				} else if (opcaoBandeira == 2) {
					bandeira = "MASTER";
				} else if (opcaoBandeira == 3) {
					bandeira = "ELO";
				}

				String senha = utils.lerConsole("Digite uma senha para o cartão:  ");

				menu.acessarMenuSimNao();

				System.out.println("");
				int opcaoCriar = Integer.parseInt(utils.lerConsole("Digite a opção desejada:  "));
				System.out.println("");

				boolean ativo;

				if (opcaoCriar == 1) {
					ativo = true;
				} else {
					ativo = false;
					continue;
				}

				CartaoDebito cartaoDebito = new CartaoDebito(numero, bandeira, senha, ativo, limiteD);

				contaBo = new ContaBo(conta);
				contaBo.adicionarCartao(cartaoDebito);

				break;

			case 10:

				String numeroCartao = utils.lerConsole("Digite o número do cartão da conta:  ");
				conta = BancoDeDados.buscaContaPorCartao(numeroCartao);

				if (conta == null) {
					System.out.println("Nenhum cartão encontrado.");
					continue;
				}

				List<Cartao> lCartao = conta.getCartoes();
				if (lCartao != null) {
					boolean encontrouCartaoDebito = false;
					for (Cartao cartao : lCartao) {
						if (cartao.getClass().getSimpleName().toLowerCase().contains("debito")) {
							if (cartao.isAtivo()) {

								encontrouCartaoDebito = true;

								double valorCompra = Double
										.parseDouble(utils.lerConsole("Digite o valor da compra:  "));

								String senhaUser = utils.lerConsole("Digite sua senha:  ");

								if (senhaUser.equals(cartao.getSenha())) {

									contaBo = new ContaBo(conta);
									contaBo.comprarCartaoDebito(cartao, valorCompra);

								} else {

									System.out.println("Senha incorreta.");
								}

							} else {
								System.out.println("Cartão desativado.");
							}
						}
					}

					if (encontrouCartaoDebito == false) {
						System.out.println("Cartão de débito não encontrado.");
					}
				} else {
					System.out.println("Nenhum cartão encontrado.");
				}

				break;

			case 11:

				String numeroCartaoC = utils.lerConsole("Digite o número do cartão da conta:  ");
				conta = BancoDeDados.buscaContaPorCartao(numeroCartaoC);

				if (conta == null) {
					System.out.println("Nenhum cartão encontrado.");
					continue;
				}

				List<Cartao> lCartaoC = conta.getCartoes();
				if (lCartaoC != null) {
					boolean encontrouCartaoCredito = false;
					for (Cartao cartao : lCartaoC) {
						if (cartao.getClass().getSimpleName().toLowerCase().contains("credito")) {
							if (cartao.isAtivo()) {

								encontrouCartaoCredito = true;

								double valorCompra = Double.parseDouble(utils.lerConsole("Digite o valor da compra: R$ "));
								String compra = utils.lerConsole                        ("Digite o nome da compra : ");
								String senhaUser = utils.lerConsole                     ("Digite sua senha        : ");

								if (senhaUser.equals(cartao.getSenha())) {

									contaBo = new ContaBo(conta);
									contaBo.comprarCartaoCredito(cartao, valorCompra, compra);

								} else {
									System.out.println("Senha incorreta.");
								}
							} else {
								System.out.println("Cartão desativado.");
							}
						}
					}

					if (encontrouCartaoCredito == false) {
						System.out.println("Cartão de crédito não encontrado.");
					}
				} else {
					System.out.println("Nenhum cartão encontrado.");
				}

				break;

			case 12:

				numeroCartaoC = utils.lerConsole("Digite o número do cartão da conta:  ");
				conta = BancoDeDados.buscaContaPorCartao(numeroCartaoC);

				if (conta == null) {
					System.out.println("Nenhum cartão encontrado.");
					continue;
				}

				lCartaoC = conta.getCartoes();
				if (lCartaoC != null) {
					boolean encontrouCartaoCredito = false;
					for (Cartao cartao : lCartaoC) {
						if (cartao.getClass().getSimpleName().toLowerCase().contains("credito")) {
							encontrouCartaoCredito = true;

							contaBo = new ContaBo(conta);
							contaBo.exibirFatura(cartao);
						}
					}

					if (encontrouCartaoCredito == false) {
						System.out.println("Cartão de crédito não encontrado.");
					}
				}

				break;

			// SAIR
			case 13:

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

		String nome = utils.lerConsole("Digite seu nome completo    : ");
		String cpf = utils.lerConsole("Digite seu CPF (11 Dígitos) : ");

		while (utils.validaCpf(cpf) == false) {
			System.out.println("CPF inválido.");
			cpf = utils.lerConsole("Digite seu CPF (11 Dígitos) : ");
		}

		String dataNasc = utils.lerConsole("Data Nascimento.            : ");
		String logradouro = utils.lerConsole("Rua                         : ");
		String numero = utils.lerConsole("Número                      : ");

		while (utils.validaNum(numero) == false) {
			System.out.println("Número inválido.");
			numero = utils.lerConsole("Número                      : ");
		}

		String cep = utils.lerConsole("CEP                         : ");

		while (utils.validaNum(cep) == false) {
			System.out.println("CEP inválido.");
			cep = utils.lerConsole("CEP                         : ");
		}

		String bairro = utils.lerConsole("Bairro                      : ");
		String cidade = utils.lerConsole("Cidade                      : ");
		String estado = utils.lerConsole("Estado                      : ");

		System.out.println("");
		System.out.println("---Usuario cadastrado com sucesso.---");
		System.out.println("");

		endereco.setBairro(bairro);
		endereco.setCep(cep);
		endereco.setCidade(cidade);
		endereco.setEstado(estado);
		endereco.setNumero(numero);
		endereco.setLogradouro(logradouro);

		return clienteBo.cadastrarCliente(nome, cpf, dataNasc, endereco);
	}
}
