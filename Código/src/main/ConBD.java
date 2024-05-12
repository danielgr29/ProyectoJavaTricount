package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ConBD {
    private static String url = "jdbc:mysql://localhost:3337/prueba";

    private static Connection con;
    private static Statement sentencia;
    private static ResultSet rs;

    public static void executeUpdate(String sql) {
        try {
            con = DriverManager.getConnection(url, "root", "root");
            sentencia = con.createStatement();
            sentencia.executeUpdate(sql);
            System.out.println("hecho");

        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    public static ResultSet executeQuery(String sql) {
        try {
            con = DriverManager.getConnection(url, "root", "root");
            sentencia = con.createStatement();
            rs = sentencia.executeQuery(sql);

        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
        return rs;
    }

    public static void conClose(){
		try {
			con.close();
		} catch (SQLException e) {
			System.out.println("error" + e.getMessage());
		}
	}
}
