package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuario {
	private String nombre, contra, nombreCompleto, sql;
	private String url = "jdbc:mysql://localhost:3337/prueba";

	private Connection con;
	private Statement sentencia;
	private ResultSet rs;

	public Usuario(String nombre, String contra, String nombreCompleto) {
		this.nombre = nombre;
		this.contra = contra;
		this.nombreCompleto = nombreCompleto;

		try {
			con = DriverManager.getConnection(url, "root", "root");
			sentencia = con.createStatement();
			sql = "SELECT nombre FROM usuarios WHERE nombre = '" + this.nombre + "'";
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				if (rs.getString("nombre") == this.nombre) {
					System.out.println("Ya existe este usuario");
				} else {
					sql = "INSERT INTO usuarios VALUES ('" + nombre + "', '" + contra + "', '" + nombreCompleto + "');";
					sentencia.executeUpdate(sql);
				}
			}

			System.out.println("hecho");

		} catch (SQLException ex) {
			System.out.println("error" + ex.getMessage());
		}

	}

	public void verUsuario() {
		try {
			con = DriverManager.getConnection(url, "root", "root");
			sentencia = con.createStatement();
			sql = "SELECT * FROM usuarios WHERE nombre = '" + this.nombre + "' AND contr = '" + this.contra + "';";
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				String nombred = rs.getString("nombre");
				String contrd = rs.getString("contr");
				String nombreCd = rs.getString("nombreCompleto");
				System.out.println("Alumno: " + nombred + "\tContr: " + contrd + "\tGastos: " + nombreCd);
			}
		} catch (SQLException e) {
			System.out.println("error" + e.getMessage());
		}

	}
	public void conClose(){
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("error" + e.getMessage());
		}
	}

}