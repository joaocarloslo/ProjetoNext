package Util;

public class Menu {
	
	public void acessarMenu() {
				
		System.out.println("--------BANCO NEXT--------");
		System.out.println("1- Cadastrar novo cliente.");
		System.out.println("2- Transferir dinheiro.");
		System.out.println("3- Depositar dinheiro.");
		System.out.println("4- Consultar saldo.");
		System.out.println("5- Cr�dito/D�bito.");
		System.out.println("6- Cadastro chave PIX.");
		System.out.println("7- Transfer�ncia via PIX");
		System.out.println("8- Sair.");
		System.out.println("---------------------------");
		
	}
	
	public void acessarMenuConta() {
		
		System.out.println("Qual tipo de conta voc� deseja? ");
		System.out.println("1- Conta corrente.");
		System.out.println("2- Conta poupan�a.");
		
	}
	
	public void acessarMenuPix() {
		System.out.println("Qual tipo de chave PIX voc� deseja? ");
		System.out.println("1- CPF.");
		System.out.println("2- Email.");
		System.out.println("3- Telefone.");
		System.out.println("4- Aleat�rio.");
	}
	
}
