package br.com.bancoNext.Bo;

import br.com.bancoNext.beans.Cliente;
import br.com.bancoNext.beans.Endereco;
import br.com.bancoNext.beans.TipoCliente;

public class ClienteBo {

	public ClienteBo() {

	}

	public Cliente cadastrarCliente(String nome, String cpf, String dataNasc, Endereco endereco) {
		
		Cliente cli1 = new Cliente();
		cli1.setNome(nome);
		cli1.setCpf(cpf);
		cli1.setDataNasc(dataNasc);
		cli1.setEndereco(endereco);
		
		cli1.setTipo(TipoCliente.Comum);
		return cli1;
	}
}
