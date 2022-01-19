package Util;

import java.util.Scanner;

public class Utils {
	Scanner ler = new Scanner(System.in);

// METODO QUE LÊ O CONSOLE E RETORNA UMA STRING(TEXTO)
	public String lerConsole(String texto) {
		System.out.print(texto);
		String textoDigitado = ler.nextLine();
		return textoDigitado;
	}

	public void fechaConsole() {
		ler.close();
	}

	public boolean validaNum(String num) {
		try {
	        Integer.parseInt(num);
	        return true;
	    } catch (Exception e) {
	    	return false;
	    }
	}
	
	public boolean validaCpf(String cpf) {
		try {
			Long.parseLong(cpf);
			if (cpf.length() == 11) {
				return true;
			}
			return false;
		} catch (Exception e) {
			System.out.println("CPF inválido");
			return false;
		}
	}
}