package main;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		List<Usuario> usuarios= new ArrayList<>();
		List<Grupo> grupos = new ArrayList<>();
		List<Gasto> gastos = new ArrayList<>();
	
		Usuario a=new Usuario("jose","prueba","Jose Ramirez");
		Usuario b=new Usuario("Ana","prueba","Ana Gomez");
		a.verUsuario();
		b.verUsuario();
		ConBD.conClose();
	}

}
