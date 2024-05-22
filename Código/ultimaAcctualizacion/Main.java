package proyectoMezcla;


import java.util.Scanner;

public class Main {

	public static void main(String[] args){
		
		boolean cerrarSesion=false,salir=false;
		String usuario="";
		Scanner scanner=new Scanner(System.in);
		int op=0;
		
		while (!salir) {
			
			//este while se repite hasta que se inicie sesiion o se seleccione salir, lo que termina el programa
			while(!salir&&usuario=="") {
				do {
					menuInicioUsuario();
					try {
						op=scanner.nextInt();
					}catch (Exception e) {op=-1;}
					if (op<0||op>2) {System.out.println("\nOpción no valida\n");}
				}while (op<0||op>2);
				switch (op) {
				case 1:
					usuario=Usuario.iniciarSesion(scanner);	//pide usuario y contraseña y los comprueba, si son correctos devuelve un string con el usuario para saber quien esta logueado
					cerrarSesion=false;
					break;
				case 2:
					Usuario.crearUsuario(scanner);	//Pide los datos del usuario nuevo y tras comprobar que son validos y el usuario no existe ya los guarda en la base de datos
					break;
				case 0:
					salir=true;
					break;
				}
			}
			
			while(!salir&&!cerrarSesion) {
				do {
					menuUsuario(usuario);
					try {
						op=scanner.nextInt();
					}catch (Exception e) {op=-1;}
					if (op<0||op>5) {System.out.println("\nOpción no valida\n");}
				}while (op<0||op>5);
				switch (op) {
				case 1:
					Grupo.verGrupos(usuario);	//muestra por pantalla todos los grupos donde el usuario es miembro
					break;
				case 2:
					salir=elegirGrupo(usuario,scanner);	//Pide un id de grupo donde el usuario sea mienbro y lo deriba a un menu con las opciones que tiene en ese grupo
					break;
				case 3:
					Grupo.crearGrupo(usuario, scanner);	//pide un nombre de grupo y lo guarda en la base de datos con el usuario como admin
					break;
				case 4:
					Grupo.eliminarGrupo(usuario, scanner);	//pide un id de grupo y tras comprobar que es administrador lo borra
					break;
				case 5:
					usuario="";
					cerrarSesion=true;
					break;
				case 0:
					salir=true;
					break;
				}
			}
		}
		System.out.println("Programa terminado");
		scanner.close();
		ConBD.conClose();
	}
	
	public static boolean elegirGrupo(String usuario,Scanner scanner){	
		try {
			int IdGrupo=0;
			System.out.print("Introduce Id del grupo: ");
			try {
				IdGrupo=scanner.nextInt();
			}catch (Exception e) {throw new ValorInvalidoException("Valor no valido.");}
			Grupo.comprobarIdGrupoSeleccionada(IdGrupo,usuario);	//comprueba que el grupo exista y el usuario sea mienbro
			Grupo grupo=Grupo.descargarGrupo(IdGrupo);	//guarda los datos del grupo seleccionado en una variable
			return opcionesGrupo(grupo,usuario,scanner);	//llama a la funcion donde estan las opciones de grupo
		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	public static boolean opcionesGrupo(Grupo grupo,String usuario,Scanner scanner) throws ValorInvalidoException {
		boolean salir=false, opValida=false;
		int op;
		while (!salir) {
			do {
				menuGrupo(usuario,grupo);
				opValida=false;
				try {
					op=scanner.nextInt();
				}catch (Exception e) {op=-1;}
				if ((op<0||op>5)&&!grupo.getAdministrador().equals(usuario)) {System.out.println("\nOpción no valida\n");}
				else if (op<0||op>8) {System.out.println("\nOpción no valida\n");}
				else {opValida=true;}
			}while (!opValida);
			switch (op) {
			case 1:
				Gasto.verGastos(grupo);	//muestra los gastos de ese grupo ordenados por fecha
				break;
			case 2:
				Gasto.agregarGasto(grupo,usuario,scanner);	//agrega un gasto al grupo en nombre del usuario
				break;
			case 3:
				Grupo.verMiembros(grupo.getIdGrupo());	//muestra una lista de los usuarios del grupo
				break;
			case 4:
				Grupo.calcularSaldo(grupo.getIdGrupo());	//calcula cuanto a pagado de mas o de menos cada usuario respecto a la media de gastos
				break;
			case 5:
				if(!grupo.getAdministrador().equals(usuario)) {salir=true;}	//comprueba si el usaurio es el admin del grupo para mostrarle mas o menos opciones
				else {
					Grupo.anadirMiembro(grupo, scanner);	//añade un miembro al grupo que ya exista y no este
				}
				break;
			case 6:
				Grupo.eliminarMiembro(grupo, scanner);	//pide un usuario y si esta en el grupo lo saca
				break;
			case 7:
				Gasto.eliminarGasto(grupo, scanner);	//pide un id de gasto y si pertenece al grupo lo borra
				break;
			case 8:
				salir=true;
				break;
			case 0:
				return true;
			}
		}
		return false;
	}
	
	//aqui estan los diferentes menus que iran apareciendo
	public static void menuInicioUsuario() {
		System.out.println();
		System.out.println("+----------------------+");
		System.out.println("| Aplicación de gastos |");
		System.out.println("+----------------------+");
		System.out.println("| 1) Iniciar sesión.   |");
		System.out.println("| 2) Usuario nuevo.    |");
		System.out.println("| 0) Salir.            |");
		System.out.println("+----------------------+");
		System.out.print("Eligue una opcion: ");
	}
	public static void menuUsuario(String usuario) {
		System.out.println();
		System.out.println("+----------------------+");
		System.out.printf("| Bienvenido %-10s|\n",usuario);
		System.out.println("+----------------------+");
		System.out.println("| 1) Ver grupos.       |");
		System.out.println("| 2) Seleccionar grupo.|");
		System.out.println("| 3) Crear grupo.      |");
		System.out.println("| 4) Eliminar grupo.   |");
		System.out.println("| 5) Cerrar sesión.    |");
		System.out.println("| 0) Salir.            |");
		System.out.println("+----------------------+");
		System.out.print("Eligue una opcion: ");
	}
	public static void menuGrupo(String usuario,Grupo grupo) {
		int num=Integer.toString(grupo.getIdGrupo()).length()+grupo.getNombreGrupo().length()+2;
		if (num>13) {num=num-13;}
		else {num=1;}
		System.out.println();
		System.out.print("+--------------");
		for (int i=0;i<num;i++) {
			System.out.print("-");
		}
		System.out.println("-------+");
		System.out.printf("| Grupo: %-"+(13+num)+"s|\n",grupo.getNombreGrupo()+"("+grupo.getIdGrupo()+")");
		System.out.print("+--------------");
		for (int i=0;i<num;i++) {
			System.out.print("-");
		}
		System.out.println("-------+");
		System.out.printf("| 1) Ver gastos       %"+num+"s|\n","");
		System.out.printf("| 2) Agregar gasto.   %"+num+"s|\n","");
		System.out.printf("| 3) Ver miembros.    %"+num+"s|\n","");
		System.out.printf("| 4) Calcular saldo.  %"+num+"s|\n","");
		if (grupo.getAdministrador().equals(usuario)) {
			System.out.printf("| 5) Añadir miembro.  %"+num+"s|\n","");
			System.out.printf("| 6) Eliminar miembro.%"+num+"s|\n","");
			System.out.printf("| 7) Eliminar gasto.  %"+num+"s|\n","");
			System.out.printf("| 8) Volver.          %"+num+"s|\n","");
		}else {
			System.out.printf("| 5) volver.          %"+num+"s|\n","");
		}
		System.out.printf("| 0) Salir.           %"+num+"s|\n","");
		System.out.print("+--------------");
		for (int i=0;i<num;i++) {
			System.out.print("-");
		}
		System.out.println("-------+");
		System.out.print("Eligue una opcion: ");
	}
}
