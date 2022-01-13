package Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.bancoNext.beans.Conta;
import br.com.bancoNext.beans.Pix;

public class BancoDeDados {

	private static Map<String, Conta> BANCO_DE_DADOS = new HashMap<String, Conta>();

	public static Conta buscaContaPorNumero(String numConta) {

		Conta conta = BancoDeDados.BANCO_DE_DADOS.get(numConta);
		if (conta == null) {
			System.out.println("Conta não encontrada");
		}
		return conta;
	}

	public static void insereConta(String numConta, Conta conta) {
		BancoDeDados.BANCO_DE_DADOS.put(numConta, conta);
	}

	public static List<Conta> buscarContaPorCliente(String cpf) {

		List<Conta> lConta = new ArrayList<Conta>();
		for (Map.Entry<String, Conta> mapaConta : BancoDeDados.BANCO_DE_DADOS.entrySet()) {

			Conta conta = mapaConta.getValue();

			if (conta.getCliente().getCpf().equals(cpf)) {
				lConta.add(conta);
			}
		}
		return lConta;

	}

	public static List<Conta> buscarTodasContas() {

		List<Conta> lConta = new ArrayList<Conta>();
		for (Map.Entry<String, Conta> mapaConta : BancoDeDados.BANCO_DE_DADOS.entrySet()) {
			Conta conta = mapaConta.getValue();
			lConta.add(conta);
		}
		return lConta;
	}
	
	public static Conta buscarContaPorPix(String chave) {
		
		for (Map.Entry<String, Conta> mapaConta : BancoDeDados.BANCO_DE_DADOS.entrySet()) {
			Conta conta = mapaConta.getValue();
			
			Pix pix = conta.getPix();
			if(pix != null) {
				if(pix.getChave().equals(chave)) {
					return conta;
				}
			}
		}
		
		return null;
	}

}
