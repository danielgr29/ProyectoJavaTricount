package main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
	private String nombre, contra, nombreCompleto;

	public Usuario(String nombre, String contra, String nombreCompleto) {
		this.nombre = nombre;
		this.contra = contra;
		this.nombreCompleto = nombreCompleto;

	}

	public void crearUsuarioBD() {
		if (contraValida(this.contra) | usuarioValido(this.nombre) | !existeUsuario()) {

			String sql = "INSERT INTO usuarios VALUES ('" + this.nombre + "', '" + this.nombre + "', '"
					+ this.nombreCompleto + "');";
			ConBD.executeUpdate(sql);
		}
		System.out.println("hecho");
	}

	public static boolean existeUsuario(String nombre) {
		try {
			String sql = "SELECT nombre FROM usuarios WHERE nombre = '" + nombre + "';";
			ResultSet rs = ConBD.executeQuery(sql);
			if (rs.next()) {
				return true;
			} else {
				return false;
			}

		} catch (SQLException e) {
			System.out.println("error " + e.getMessage());
		}
		return true;
	}

	public boolean existeUsuario() throws ValorInvalidoException {
		try {
			String sql = "SELECT nombre FROM usuarios WHERE nombre = '" + this.nombre + "';";
			ResultSet rs = ConBD.executeQuery(sql);
			if (rs.next()) {
				throw new ValorInvalidoException("Ya existe este usuario");
			} else {
				return false;
			}

		} catch (SQLException e) {
			System.out.println("error " + e.getMessage());
		}
		return true;
	}

	public static boolean comprobarContra(String nombre, String contra) {
		try {

			String sql = "SELECT * FROM usuarios WHERE nombre = '" + nombre + "';";
			ResultSet rs = ConBD.executeQuery(sql);
			rs.next();
			if (rs.getString("contra").equals(contra)) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			System.out.println("error" + e.getMessage());
		}
		return false;

	}

	public static String obtenerNombreCompleto(String nombre, String contra) {
		try {

			String sql = "SELECT * FROM usuarios WHERE nombre = '" + nombre + "' AND contra = '" + contra
					+ "';";
			ResultSet rs = ConBD.executeQuery(sql);
			rs.next();
			return rs.getString("nombreCompleto");

		} catch (SQLException e) {
			System.out.println("error" + e.getMessage());
		}
		return "";

	}

	// Comprueba si la contraseña cumple los requisitos que se le piden (mas de 6
	// caracteres, que tenga una mayuscula, una minuscula y un numero). Devuelve
	// error si tiene menos de 6 caracteres
	private static boolean contraValida(String contra) throws ValorInvalidoException {

		if (contra.length() < 6) {
			throw new ValorInvalidoException("Contraseña demasiado corta");
		}
		boolean minuscula = false;
		boolean mayuscula = false;
		boolean numero = false;
		for (int i = 0; i < contra.length(); i++) {
			if (contra.charAt(i) >= 'a' && contra.charAt(i) <= 'z') {
				minuscula = true;
			} // guarda true si encuentra almenos una minuscula
			if (contra.charAt(i) >= 'A' && contra.charAt(i) <= 'Z') {
				mayuscula = true;
			} // guarda true si encuentra almenos una mayuscula
			if (contra.charAt(i) >= '0' && contra.charAt(i) <= '9') {
				numero = true;
			} // guarda true si encuentra almenos un numero
		}
		if (!mayuscula) {
			throw new ValorInvalidoException("No contiene mayusculas."); // Devuelve error si no tiene mayusculas
		}
		if (!minuscula) {
			throw new ValorInvalidoException("No contiene minusculas."); // Devuelve error si no tiene minusculas
		}
		if (!numero) {
			throw new ValorInvalidoException("No contiene digitos."); // Devuelve error si no tiene numeros
		}
		if (numero | minuscula | mayuscula) {
			return true;
		} else {
			return false;
		}
	}

	// Comprueba si el nombre de usuario cumple los requisitos que se le piden
	// (entre 3 y 10 caracteres).
	// Devuelve error si el usuario no tiene la longitud correcta
	private static boolean usuarioValido(String nombre) throws ValorInvalidoException {
		if (nombre.length() < 3 || nombre.length() > 10) {
			throw new ValorInvalidoException("Usuario no valido");
		} else {
			return true;
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		String sql = "UPDATE usuarios SET nombre = '" + nombre + "' WHERE nombre = '" + this.nombre + "';";
		ConBD.executeUpdate(sql);
		this.nombre = nombre;
	}

	public String getContra() {
		return contra;
	}

	public void setContra(String contra) {
		String sql = "UPDATE usuarios SET contra = '" + contra + "' WHERE nombre = '" + this.nombre + "';";
		ConBD.executeUpdate(sql);
		this.contra = contra;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		String sql = "UPDATE usuarios SET nombreCompleto = '" + nombreCompleto + "' WHERE nombre = '" + this.nombre
				+ "';";
		ConBD.executeUpdate(sql);
		this.nombreCompleto = nombreCompleto;
	}

	public void verUsuario() {
		try {

			String sql = "SELECT * FROM usuarios WHERE nombre = '" + this.nombre + "' AND contra = '" + this.contra
					+ "';";
			ResultSet rs = ConBD.executeQuery(sql);
			while (rs.next()) {
				String nombred = rs.getString("nombre");
				String contrad = rs.getString("contra");
				String nombreCd = rs.getString("nombreCompleto");
				System.out.println("Nombre: " + nombred + "\tContr: " + contrad + "\tNombre Completo: " + nombreCd);
			}
		} catch (SQLException e) {
			System.out.println("error" + e.getMessage());
		}

	}

}