package proyectoJavaTricount;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario{
	private String usuario;
	private String contrasenia;
	private String nombreCompleto;
	
	public static void usuarioValido(String usuario) throws ValorInvalidoException  {	//Comprueba si el nombre de usuario cumple los requisitos que se le piden (entre 3 y 10 caracteres)
		if (usuario.length()<3 || usuario.length()>10) {	//Devuelve error si el usuario no tiene la longitud correcta
			throw new ValorInvalidoException("El nombre de usuario no cumple las condiciones.");
		}
	}
	public static boolean usuarioExiste(String usuario){	//Consulta la Base de Datos para comprobar si el usaurio existe
		
		try {
			String sql="SELECT nombre FROM usuarios";	//Sentencia de sql para sacar una tabla con todos los numbre de uuarios registrados
			ResultSet rs=ConBD.executeQuery(sql);
			
			while (rs.next()) {
				if (rs.getString("nombre").equals(usuario)) {	//Comprueba todos las filas de la tabla con el nombre de usaurio pasado por parametro y devuelve true si coinciden
					return true;
				}
			}
			return false;
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
		return false;
	}
	public static void contraseniaValida(String pass) throws ValorInvalidoException {	//Comprueba si la contraseña cumple los requisitos que se le piden (mas de 6 caracteres, que tenga una mayuscula, una minuscula y un numero)
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
	public static void nombreCompletoValido(String nombreCompleto) throws ValorInvalidoException  {	//Comprueba si el nombre de usuario cumple los requisitos que se le piden (entre 3 y 10 caracteres)
		if (nombreCompleto.length()>30) {	//Devuelve error si el nombre completo  no tiene la longitud correcta
			throw new ValorInvalidoException("El nombre y apellidos es demasiado largo.");
		}
	}
	public Usuario(String usuario, String contrasenia, String nombreCompleto){
		this.usuario = usuario;
		this.contrasenia = contrasenia;
		this.nombreCompleto = nombreCompleto;
	}
	public static void guardarUsuarioBD(String usuario, String contrasenia, String nombreCompleto) {
		String sql = "INSERT INTO usuarios VALUES ('" + usuario + "', '" + contrasenia + "', '" + nombreCompleto + "');";	//inserta un usuario en la base de datos
		ConBD.executeUpdate(sql);
	}
	
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}
		
}