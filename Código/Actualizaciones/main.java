package proyectoJavaTricount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		List<Usuario> usuarios= new ArrayList<>();
		/*List<Grupo> grupos = new ArrayList<>();
		List<Gasto> gastos = new ArrayList<>();
		descargarUsuarios(usuarios);
		descargarGastos(gastos);
		
		for(Gasto g:gastos) {
			System.out.println(g.toString());
		}*/
		
		boolean cerrarSesion=false,salir=false;
		while (!salir) {
			
			while(!cerrarSesion) {
				
			}
		}
		ConBD.conClose();
	}
	public static String iniciarSesion() throws ValorInvalidoException {
		boolean salir=false;
		String usuario="",contrasenia;
		do {
			try {
				System.out.print("Usuario: ");
				usuario=leerString();
				System.out.print("Contraseña: ");
				contrasenia=leerString();
				if (Usuario.usuarioExiste(usuario)) {
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
	public static void crearUsuario(List<Usuario> usuarios) throws ValorInvalidoException{
		String usuario="", contrasenia="", nombreCompleto="";
		boolean salir=true;
		do {
			try {
				salir=true;
				System.out.print("Nombre de usuario(entre 3 y 10 caracteres): ");
				usuario=leerString();
					if(Usuario.usuarioExiste(usuario)) {
						throw new ValorInvalidoException("El usuario ya existe.");
					}
				Usuario.usuarioValido(usuario);
			} catch (ValorInvalidoException e) {
				System.out.println(e.getMessage());
				salir=false;
			}
		} while(!salir);
		System.out.print("Nombre y apellídos: ");
		nombreCompleto=leerString();
		do {
			try {
				salir=true;
				System.out.print("Contraseña(más de 6 caracteres, mayusculas, minusculas y numeros): ");
				contrasenia=leerString();
				Usuario.contraseniaValida(contrasenia);
			} catch (ValorInvalidoException e) {
				System.out.println(e.getMessage());
				salir=false;
			}
		} while(!salir);
		usuarios.add(new Usuario(usuario, contrasenia, nombreCompleto));
	}
	public static void crearGrupo() {
		
	}
	public static String leerString() {
		String cadena;
		Scanner sc=new Scanner(System.in);
		cadena=sc.nextLine();
		//sc.close();
		return cadena;
	}
	
	/*public static void descargarUsuarios(List<Usuario> usuarios) {
		String sql;
		String url = "jdbc:mysql://localhost:3337/prueba";
		Connection con;
		Statement sentencia;
		ResultSet rs;
		try {
			con = DriverManager.getConnection(url, "root", "root");
			sentencia = con.createStatement();
			sql = "SELECT * FROM usuarios";
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				usuarios.add(new Usuario(rs.getString("nombre"),rs.getString("contra"),rs.getString("nombreCompleto")));
			}
			System.out.println("Base de datos de usuario descargada.");

		} catch (SQLException ex) {
			System.out.println("Error al descargar la base de datos de usuario." + ex.getMessage());
		}
	}
	public static void descargarGrupos(List<Grupo> grupos) {
		String sql;
		String url = "jdbc:mysql://localhost:3337/prueba";
		Connection con;
		Statement sentencia;
		ResultSet rs;
		try {
			con = DriverManager.getConnection(url, "root", "root");
			sentencia = con.createStatement();
			sql = "SELECT * FROM grupos";
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				grupos.add(new Grupo(rs.getString("IdGrupo"),rs.getString("contra"),rs.getString("nombreCompleto")));
			}
			System.out.println("Base de datos de usuario descargada.");

		} catch (SQLException ex) {
			System.out.println("Error al descargar la base de datos de usuario." + ex.getMessage());
		}
	}
	public static void descargarGastos(List<Gasto> gastos) {
		String nombre, contra, nombreCompleto, sql;
		String url = "jdbc:mysql://localhost:3337/prueba";
		Connection con;
		Statement sentencia;
		ResultSet rs;
		try {
			con = DriverManager.getConnection(url, "root", "root");
			sentencia = con.createStatement();
			sql = "SELECT * FROM gastos";
			rs = sentencia.executeQuery(sql);
			while (rs.next()) {
				//(String igGasto, String idGrupo, String usuario, double cantidad, String descripcion, LocalDate fecha)
				gastos.add(new Gasto(rs.getString("GastosId"),rs.getString("idGrupo"),rs.getString("nombre"),Double.valueOf(rs.getString("cantidad")),rs.getString("descripcion"),LocalDate.parse(rs.getString("fecha"))));
			}
			System.out.println("Base de datos de usuario descargada.");

		} catch (SQLException ex) {
			System.out.println("Error al descargar la base de datos de usuario." + ex.getMessage());
		}
	}*/
}
