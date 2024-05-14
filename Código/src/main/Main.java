package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		List<Usuario> usuarios= new ArrayList<>();
		List<Grupo> grupos = new ArrayList<>();
		List<Gasto> gastos = new ArrayList<>();
		Scanner sc=new Scanner(System.in);

		System.out.println("Bienvenido a Tricount");
		System.out.println("1. Iniciar sesión");
		System.out.println("2. Registrarse");
		int elec=sc.nextInt();
		switch (elec) {
			case 1:
				login(sc);
				break;
		
			default:
				break;
		}
		ConBD.conClose();
	}

	private static void login(Scanner sc) {
		System.out.println("Introduce nombre de usuario: ");
		String nombre=sc.next();
		if (Usuario.existeUsuario(nombre)) {
			System.out.println("Introduzca contraseña: ");
			String contra=sc.next();
			if (Usuario.comprobarContra(nombre, contra)) {
				System.out.println("Bienvenido");
				Usuario a=new Usuario(nombre, contra, Usuario.obtenerNombreCompleto(nombre, contra));
			}else{
				throw new ValorInvalidoException("Contraseña Incorrecta");
			}
		}else{
			throw new ValorInvalidoException("No existe este usuario");
		}
	}

}
