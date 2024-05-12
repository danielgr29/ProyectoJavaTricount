package main;

import java.util.ArrayList;
import java.util.List;

public class main {

	public static void main(String[] args) {
		List<Usuario> usuarios= new ArrayList<>();
		List<Grupo> grupos = new ArrayList<>();
		List<Gasto> gastos = new ArrayList<>();
		/* Al instanciar un elemento usuario, este tiene que cerrar la base de datos con el
		 * método conClose que está en la clase Usuario, no creo que pueda ir en un lugar mejor
		 * y creo que en Grupo y Gasto tendre que hacer lo mismo.
		 */
	}

}
