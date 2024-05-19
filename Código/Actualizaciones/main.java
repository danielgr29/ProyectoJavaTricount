package proyectoJavaTricount;


import java.util.Scanner;

public class main {

	public static void main(String[] args) throws ValorInvalidoException {
		
		boolean cerrarSesion=false,salir=false;
		String usuario="";
		int op=0;
		
		while (!salir) {
			
			//este while se repite hasta que se inicie sesiion o se seleccione salir, lo que termina el programa
			while(!salir&&usuario=="") {
				do {
					menuInicioUsuario();
					op=leerInt();
					if (op<0||op>2) {System.out.println("\nOpción no valida\n");}
				}while (op<0||op>2);
				switch (op) {
				case 1:
					usuario=iniciarSesion();	//pide usuario y contraseña y los comprueba, si son correctos devuelve un string con el usuario para saber quien esta logueado
					break;
				case 2:
					crearUsuario();	//Pide los datos del usuario nuevo y tras comprobar que son validos y el usuario no existe ya los guarda en la base de datos
					break;
				case 0:
					salir=true;
					break;
				}
			}
			
			while(!salir&&!cerrarSesion) {
				do {
					menuUsuario(usuario);
					op=leerInt();
					if (op<0||op>4) {System.out.println("\nOpción no valida\n");}
				}while (op<0||op>4);
				switch (op) {
				case 1:
					Grupo.verGrupos(usuario);	//muestra por pantalla todos los grupos donde el usuario es miembro
					break;
				case 2:
					
					break;
				case 3:
					crearGrupo(usuario);	//pide un nombre de grupo y lo guarda en la base de datos con el usuario como admin
					break;
				case 4:
					
					break;
				case 0:
					salir=true;
					break;
				}
			}
		}
		System.out.println("Xaete");
		ConBD.conClose();
	}
	public static String iniciarSesion() throws ValorInvalidoException {	//Pide usuario y contraseña y comprueba que sean correctos
		boolean salir=false;
		String usuario="",contrasenia;
		do {
			try {
				System.out.print("Usuario: ");
				usuario=leerString();
				System.out.print("Contraseña: ");
				contrasenia=leerString();
				if (!Usuario.usuarioExiste(usuario)) {	//
					throw new ValorInvalidoException("El usuario no existe.");
				}
				if (!Usuario.comprobarContraseniaCorrecta(usuario,contrasenia)) {
					throw new ValorInvalidoException("Contraseña incorrecta");
				}
				salir=true;
			}catch (ValorInvalidoException e) {
				System.out.println(e.getMessage());
			}
			
		}while(!salir);
		return usuario;
	}
	public static void crearUsuario() throws ValorInvalidoException{
		String usuario="", contrasenia="", nombreCompleto="";
		boolean salir=true;
		do {
			try {
				salir=true;
				System.out.print("Nombre de usuario(entre 3 y 10 caracteres): ");
				usuario=leerString();
					if(Usuario.usuarioExiste(usuario)) {	//comprueba que el usuario no este ya en la base de datos
						throw new ValorInvalidoException("El usuario ya existe.");
					}
				Usuario.usuarioValido(usuario);	//comprueba que el nombre de usuario cumpla los requisitos que se piden
			} catch (ValorInvalidoException e) {
				System.out.println(e.getMessage());
				salir=false;
			}
		} while(!salir);
		do {
			try {
				salir=true;
				System.out.print("Nombre y apellídos: ");
				nombreCompleto=leerString();
				Usuario.nombreCompletoValido(nombreCompleto);	//comprueba que el nombre completo cumpla los requisitos que se piden
			} catch (ValorInvalidoException e) {
				System.out.println(e.getMessage());
				salir=false;
			}
		} while(!salir);
		do {
			try {
				salir=true;
				System.out.print("Contraseña(más de 6 caracteres, mayusculas, minusculas y numeros): ");
				contrasenia=leerString();
				Usuario.contraseniaValida(contrasenia);	//comprueba que la contraseña cumpla los requisitos que se piden
			} catch (ValorInvalidoException e) {
				System.out.println(e.getMessage());
				salir=false;
			}
		} while(!salir);
		Usuario.guardarUsuarioBD(usuario, contrasenia, nombreCompleto);	//guarda el nuevo usaurio en la base de datos
	}
	
	public static void crearGrupo(String usuario) {
		String nombreGrupo="";
		boolean salir=true;
		do {
			try {
				salir=true;
				System.out.print("Nombre del grupo(maximo 30 caracteres): ");
				nombreGrupo=leerString();
				Grupo.comprobarNombreGrupo(nombreGrupo,usuario);	//comprueba que el nombre de grupo cumpla los requisitos que se piden
			} catch (ValorInvalidoException e) {
				System.out.println(e.getMessage());
				salir=false;
			}
		} while(!salir);
		
		Grupo.guardarGrupoBD(nombreGrupo,usuario);	//guarda el nuevo grupo en la base de datos
	}
	
	public static String leerString() {
		String cadena;
		Scanner sc=new Scanner(System.in);
		cadena=sc.nextLine();
		return cadena;
	}
	public static int leerInt() {
		int num=-1;
		Scanner sc=new Scanner(System.in);
		try {
			num=sc.nextInt();
		}catch (Exception e) {
			
		}
			return num;
	}
	
	//aqui estan los diferentes menus que iran apareciendo
	public static void menuInicioUsuario() {
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
		System.out.println("+----------------------+");
		System.out.printf("| Bienvenido %-10s|\n",usuario);
		System.out.println("+----------------------+");
		System.out.println("| 1) Ver grupos.       |");
		System.out.println("| 2) Seleccionar grupo.|");
		System.out.println("| 3) Crear grupo.      |");
		System.out.println("| 4) Eliminar grupo.   |");
		System.out.println("| 0) Salir.            |");
		System.out.println("+----------------------+");
		System.out.print("Eligue una opcion: ");
	}
}
