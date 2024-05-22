package proyectoMezcla;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Usuario{
	public static String iniciarSesion(Scanner scanner) {
		String usuario,contrasenia;
		scanner.nextLine();
		System.out.print("Usuario: ");
		usuario=scanner.nextLine();
		System.out.print("Contraseña: ");
		contrasenia=scanner.nextLine();
		try {
			if (!usuarioExiste(usuario)) {	//Comprueba si el usuario esta en la base de datos
				throw new ValorInvalidoException("El usuario no existe.");
			}
			if (!comprobarContraseniaCorrecta(usuario,contrasenia)) {	//comprueba que la contraseña introducida sea la misma que esta guardada en la base de datos
				throw new ValorInvalidoException("Contraseña incorrecta");
			}
		}catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
			usuario="";
		}
		return usuario;
	}
	public static void crearUsuario(Scanner scanner) throws ValorInvalidoException{	//Crea un nuevo usuario pidiendo los datos
		String usuario="", contrasenia="", nombreCompleto="";
		scanner.nextLine();
		System.out.print("Nombre de usuario(entre 3 y 10 caracteres): ");
		try {
			usuario=scanner.nextLine();
			if(usuarioExiste(usuario)) {	//Comprueba que no exista un usuario con el mismo nombre
				throw new ValorInvalidoException("El usuario ya existe.");
			}
			usuarioValido(usuario);	//comprueba que el nombre de usuario cumpla los requisitos que se piden
			
			System.out.print("Nombre y apellídos: ");
			nombreCompleto=scanner.nextLine();
			nombreCompletoValido(nombreCompleto);	//comprueba que el nombre completo cumpla los requisitos que se piden
			
			System.out.print("Contraseña(más de 6 caracteres, mayusculas, minusculas y numeros): ");
			contrasenia=scanner.nextLine();
			contraseniaValida(contrasenia);	//comprueba que la contraseña cumpla los requisitos que se piden
			
			guardarUsuarioBD(usuario, contrasenia, nombreCompleto);	//guarda el nuevo usaurio en la base de datos
			System.out.println("Usuario creado con exito.");
		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		}
	}
	
	private static void usuarioValido(String usuario) throws ValorInvalidoException  {	//Comprueba si el nombre de usuario cumple los requisitos que se le piden (entre 3 y 10 caracteres)
		if (usuario.length()<3 || usuario.length()>10) {	//Devuelve error si el usuario no tiene la longitud correcta
			throw new ValorInvalidoException("El nombre de usuario no cumple las condiciones.");
		}
	}
	public static boolean usuarioExiste(String usuario) throws ValorInvalidoException{	//Consulta la Base de Datos para comprobar si el usaurio existe
		try {
			String sql="SELECT nombre FROM usuarios WHERE nombre='"+usuario+"'";	//Sentencia de sql para comprobar si el usuario esta en la base de datos
			ResultSet rs=ConBD.executeQuery(sql);
			if(rs.next()) {	
				return true;
			}
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
		return false;
	}
	private static void contraseniaValida(String pass) throws ValorInvalidoException {	//Comprueba si la contraseña cumple los requisitos que se le piden (mas de 6 caracteres, que tenga una mayuscula, una minuscula y un numero)
		if (pass.length()<6) {	//Devuelve error si tiene menos de 6 caracteres
			throw new ValorInvalidoException("Contraseña demasiado corta");
		}
		boolean minuscula = false;
	    boolean mayuscula = false;
	    boolean numero = false; 
	    for (int i=0;i<pass.length();i++) {
			if (pass.charAt(i)>='a'&&pass.charAt(i)<='z') {minuscula=true;}	//guarda true si encuentra almenos una minuscula
			if (pass.charAt(i)>='A'&&pass.charAt(i)<='Z') {mayuscula=true;}	//guarda true si encuentra almenos una mayuscula
			if (pass.charAt(i)>='0'&&pass.charAt(i)<='9') {numero=true;}	//guarda true si encuentra almenos un numero
		}
	    if (!mayuscula) {
	    	throw new ValorInvalidoException("No contiene mayusculas.");	//Devuelve error si no tiene mayusculas
	    }
	    if (!minuscula) {
	    	throw new ValorInvalidoException("No contiene minusculas.");	//Devuelve error si no tiene minusculas
	    }
	    if (!numero) {
	    	throw new ValorInvalidoException("No contiene digitos.");		//Devuelve error si no tiene numeros
	    }
	}
	public static boolean comprobarContraseniaCorrecta(String usuario, String pass) throws ValorInvalidoException {	//Compara una contraseña pasada por parametro con la del usuario y debuelve verdadero si son iguales
		try {
			String sql="SELECT * FROM usuarios WHERE nombre='"+usuario+"' ";	//Sentencia para seleccionar de la tabla de usaurios uno concreto pasado por parametro
			ResultSet rs=ConBD.executeQuery(sql);
			
			while (rs.next()) {
				if (rs.getString("contra").equals(pass)) {	//Compara la contraseña pasada por parametro con la guardada en la base de datos y devuelve true si coinciden
					return true;
				}
			}
			return false;
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
		return false;
	}
	private static void nombreCompletoValido(String nombreCompleto) throws ValorInvalidoException  {	//Comprueba si el nombre completo de usuario cumple los requisitos que se le piden
		if (nombreCompleto.length()>30) {	//Devuelve error si el nombre completo  no tiene la longitud correcta
			throw new ValorInvalidoException("El nombre y apellidos es demasiado largo.");
		}
	}
	public static String obtenerNombreCompleto(String usuario) {
		try {
			String sql="SELECT nombreCompleto FROM usuarios WHERE nombre='"+usuario+"'";	//Sentencia de sql para obtener el nombre completo de un usuario
			ResultSet rs=ConBD.executeQuery(sql);
			rs.next();
			return rs.getString("nombreCompleto");
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
		return "";
	}
	private static void guardarUsuarioBD(String usuario, String contrasenia, String nombreCompleto) {
		String sql = "INSERT INTO usuarios VALUES ('" + usuario + "', '" + contrasenia + "', '" + nombreCompleto + "');";	//inserta un usuario en la base de datos
		ConBD.executeUpdate(sql);
	}	
}