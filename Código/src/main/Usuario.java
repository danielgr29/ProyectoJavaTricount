package main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
	private String nombre, contra, nombreCompleto, sql;
	private ResultSet rs;

	public Usuario(String nombre, String contra, String nombreCompleto) {
		this.nombre = nombre;
		this.contra = contra;
		this.nombreCompleto = nombreCompleto;

		try {
			sql = "SELECT nombre FROM usuarios WHERE nombre = '" + this.nombre + "'";
			rs = ConBD.executeQuery(sql);
			do {
				if (rs.next()) {
					
					System.out.println("Ya existe este usuario");
				} else {
					
					sql = "INSERT INTO usuarios VALUES ('" + nombre + "', '" + contra + "', '" + nombreCompleto + "');";
					ConBD.executeUpdate(sql);
				}
			} while (rs.next());

			System.out.println("hecho");

		} catch (SQLException ex) {
			System.out.println("error " + ex.getMessage());
		}

	}

	public void verUsuario() {
		try {

			sql = "SELECT * FROM usuarios WHERE nombre = '" + this.nombre + "' AND contra = '" + this.contra + "';";
			rs = ConBD.executeQuery(sql);
			while (rs.next()) {
				String nombred = rs.getString("nombre");
				String contrad = rs.getString("contra");
				String nombreCd = rs.getString("nombreCompleto");
				System.out.println("Alumno: " + nombred + "\tContr: " + contrad + "\tGastos: " + nombreCd);
			}
		} catch (SQLException e) {
			System.out.println("error" + e.getMessage());
		}

	}

}