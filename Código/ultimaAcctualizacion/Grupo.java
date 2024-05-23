package proyectoTricount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Grupo {
	private int idGrupo;
	private String nombreGrupo;
	private String administrador;
	//private List<Usuario> miembros;
	//private List<Gasto> gastosGrupo;

	public static void verGrupos(String usuario){
		try {
			String sql="SELECT IdGrupo FROM miembros WHERE nombre='"+usuario+"';";	//Sentencia de sql para sacar una tabla con todos los IdGrupo a los que pertenece un usuario
			String sql2,admin;
			ResultSet rs=ConBD.executeQuery(sql);
			ResultSet rs2;
			System.out.println("+-------+-----+------------------------------+");
			System.out.printf("|IdGrupo|Admin|%-30s|\n","Nombre del grupo");
			System.out.println("+-------+-----+------------------------------+");
			while (rs.next()) {	//Muestra los grupos a los que pertenece el usuario y si es administrador o no
				sql2="SELECT * FROM grupos WHERE IdGrupo='"+rs.getInt("IdGrupo")+"'";
				rs2=ConBD.executeQuery(sql2);
				rs2.next();
				if(rs2.getString("admin").equals(usuario)) {admin="SI";}
				else {admin="NO";}
				System.out.printf("|%7s|%-5s|%-30s|\n",rs2.getInt("IdGrupo"),admin,rs2.getString("nombreGrupo"));
			}
			System.out.println("+-------+-----+------------------------------+");
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
	}
	public static void crearGrupo(String usuario,Scanner scanner) {	//pide un nombre de grupo y tras comprobarlo lo crea
		String nombreGrupo="";
		scanner.nextLine();
		try {
			System.out.print("Nombre del grupo(maximo 30 caracteres): ");
			nombreGrupo=scanner.nextLine();
			comprobarNombreGrupo(nombreGrupo,usuario);	//comprueba que el nombre de grupo cumpla los requisitos que se piden
			
			guardarGrupoBD(nombreGrupo,usuario);	//guarda el nuevo grupo en la base de datos
			System.out.println("El grupo se ha creado con exito");
		}catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void eliminarGrupo(String usuario,Scanner scanner) {	//Pide un IdGrupo y si el usuario es su administrador lo borra
		try {
			int idGrupo=0;
			System.out.print("Introduce Id del grupo: ");
			scanner.nextLine();
			try {
				idGrupo=scanner.nextInt();
			}catch (Exception e) {scanner.nextLine();throw new ValorInvalidoException("Valor no valido.");}
			if (esAdmin(usuario,idGrupo)) {
				String sql = "DELETE FROM miembros WHERE IdGrupo='"+idGrupo+"';";
				ConBD.executeUpdate(sql);
				sql="DELETE FROM gastos WHERE IdGrupo='"+idGrupo+"';";
				ConBD.executeUpdate(sql);
				sql="DELETE FROM grupos WHERE IdGrupo='"+idGrupo+"';";
				ConBD.executeUpdate(sql);
			}
		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		}
	}
	public static void comprobarIdGrupoSeleccionada(int IdGrupo,String usuario) throws ValorInvalidoException {	//comprueba si un grupo existe y si el usuario es miembro del grupo
		try {
			String sql="SELECT * FROM grupos WHERE IdGrupo='"+IdGrupo+"';";
			ResultSet rs=ConBD.executeQuery(sql);
			
			if (!rs.next()) {
				throw new ValorInvalidoException("El grupo no existe");
			}
			if (!esMiembro(IdGrupo, usuario)) {
				throw new ValorInvalidoException("No perteneces a este grupo");
			}
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
	}
	public static void verMiembros(int idGrupo) {	//muestra una tabla con todos los miembros de un grupo
		try {
			String nombre,sql="SELECT nombre FROM miembros WHERE IdGrupo='"+idGrupo+"';";
			ResultSet rs=ConBD.executeQuery(sql);
			System.out.println("+----------+------------------------------+");
			System.out.println("|Usuario   |Nombre completo               |");
			System.out.println("+----------+------------------------------+");
			while (rs.next()) {
				nombre=rs.getString("nombre");
				System.out.printf("|%-10s|%-30s|\n",nombre,Usuario.obtenerNombreCompleto(nombre));
			}
			System.out.println("+----------+------------------------------+");
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
	}
	public static void anadirMiembro(Grupo grupo, Scanner scanner) throws ValorInvalidoException {	//pide introducir un usuario y si existe y no esta en el grupo lo agrega
		scanner.nextLine();
		System.out.print("Ingrese nombre del miembro a añadir:");
        String nombre = scanner.nextLine();
        try {
			if (!Usuario.usuarioExiste(nombre)) {
				throw new ValorInvalidoException("El usuario no existe.");
			}
			if(esMiembro(grupo.getIdGrupo(), nombre)) {
				throw new ValorInvalidoException("El usuario ya es miembro");
			}
			String sql = "INSERT INTO miembros (IdGrupo, nombre) VALUES (" + grupo.getIdGrupo() + ", '" + nombre+ "');";
			ConBD.executeUpdate(sql);
			System.out.println("Miembro añadido con éxito.");
		} catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
        
    }
	public static void eliminarMiembro(Grupo grupo, Scanner scanner) throws ValorInvalidoException {	//pide un usuario y si es miembro del grupo lo saca
        scanner.nextLine();
		System.out.print("Ingrese nombre del miembro a eliminar:");
        String nombre = scanner.nextLine();
        try {
        	if (!Usuario.usuarioExiste(nombre)) {
			throw new ValorInvalidoException("El usuario no existe.");
		}
		if(!esMiembro(grupo.getIdGrupo(), nombre)) {
			throw new ValorInvalidoException("No hay ningun miembro con ese nombre.");
		}
		String sql = "DELETE FROM gastos WHERE IdGrupo = " + grupo.getIdGrupo() + " AND nombre = '" + nombre + "';";
		ConBD.executeUpdate(sql);
		sql = "DELETE FROM miembros WHERE IdGrupo = " + grupo.getIdGrupo() + " AND nombre = '" + nombre + "';";
	        ConBD.executeUpdate(sql);
	        System.out.println("Miembro eliminado del grupo.");
		} catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
    }
	public static void calcularSaldo(int idGrupo) {	//calcula el total del gasto, la parte proporcional que tiene que pagar cada miembro y muestra por pantalla cuanto a pagado de mas o de menos cada usuario respecto a esa media
		try {
			double total=0, totalUsuario;
			int miembros=0;
			String sql2, sql="SELECT * FROM gastos WHERE IdGrupo='"+idGrupo+"';";
			ResultSet rs2,rs=ConBD.executeQuery(sql);
			while (rs.next()) {
				total=total+rs.getDouble("cantidad");
			}
			
			String usuario;
			sql="SELECT * FROM miembros WHERE IdGrupo='"+idGrupo+"';";
			rs=ConBD.executeQuery(sql);
			while (rs.next()) {
				miembros=miembros+1;
			}
			double media=total/miembros;
			rs=ConBD.executeQuery(sql);
			System.out.println("+----------+--------------------+");
			System.out.println("|Miembro   |Saldo               |");
			System.out.println("+----------+--------------------+");
			while (rs.next()) {
				usuario=rs.getString("nombre");
				sql2="SELECT * FROM gastos WHERE IdGrupo='"+idGrupo+"' AND nombre='"+usuario+"';";
				rs2=ConBD.executeQuery(sql2);
				totalUsuario=0;
				while(rs2.next()) {
					totalUsuario=totalUsuario+rs2.getDouble("cantidad");
				}
				System.out.printf("|%-10s|%+20.2f|\n",usuario,totalUsuario-media);
			}
			System.out.println("+----------+--------------------+");
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
	}
	
	private static boolean esMiembro(int IdGrupo,String usuario) throws SQLException {	//comprueba si un usuario es miembro de un grupo, ambos pasados por parametro
		try {
			String sql="SELECT * FROM miembros WHERE IdGrupo='"+IdGrupo+"';";
			ResultSet rs=ConBD.executeQuery(sql);
			
			while (rs.next()) {
				if (rs.getString("nombre").equals(usuario)) {
					return true;
				}
			}
			return false;
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
		return false;
	}
	private static boolean esAdmin(String usuario,int idGrupo) throws ValorInvalidoException {
		try {
			String sql="SELECT * FROM grupos WHERE IdGrupo='"+idGrupo+"';";	//Sentencia de sql para comprobar si el usuario es admin del grupo pasado por parametro
			ResultSet rs=ConBD.executeQuery(sql);
			if (!rs.next()) {
				throw new ValorInvalidoException("El grupo no existe.");
			}
			if (!rs.getString("admin").equals(usuario)) {
				throw new ValorInvalidoException("No puedes borrar un grupo si no eres administrador.");
			}
			return true;
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
			return false;
		}
	}
	private static void comprobarNombreGrupo(String nombreGrupo,String usuario) throws ValorInvalidoException {	//comprueba el nombre del grupo antes de crearlo
		if (nombreGrupo.length()>30) {	//Devuelve error si tiene mas de 30 caracteres
			throw new ValorInvalidoException("El nombre es demasiado largo");
		}
		try {
			String sql="SELECT nombreGrupo FROM grupos WHERE nombreGrupo='"+nombreGrupo+"' AND admin='"+usuario+"';";	//Sentencia de sql para comprobar si el usuario ya tiene creado un grupo con ese nombre
			ResultSet rs=ConBD.executeQuery(sql);
			
			if (rs.next()) {
				throw new ValorInvalidoException("Ya tienes creado un grupo con ese nombre");
			}
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
	}
	
	public Grupo(int idGrupo, String nombreGrupo, String administrador) {
		this.idGrupo = idGrupo;
		this.nombreGrupo = nombreGrupo;
		this.administrador = administrador;
	}
	public static Grupo descargarGrupo(int IdGrupo) {
		try {
			String sql="SELECT * FROM grupos WHERE IdGrupo='"+IdGrupo+"';";
			ResultSet rs=ConBD.executeQuery(sql);
			if(rs.next()) {
				return new Grupo(rs.getInt("IdGrupo"),rs.getString("nombreGrupo"),rs.getString("admin"));
			}
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
		return null;
	}
	private static void guardarGrupoBD(String nombreGrupo,String usuario){
		int idGrupo;
		
		String sql = "INSERT INTO grupos(nombreGrupo,admin) VALUES ('" + nombreGrupo + "', '" + usuario + "');";	//crea un grupo nuevo en la base de datos con el nombre y el admin pasados por parametro
		ConBD.executeUpdate(sql);
		
		try {
		sql="SELECT IdGrupo FROM grupos WHERE nombreGrupo='"+nombreGrupo+"' AND admin='" + usuario + "';";	//Sentencia para optener el id del grupo que se acaba de crear
		ResultSet rs=ConBD.executeQuery(sql);
		rs.next();
		idGrupo=rs.getInt("IdGrupo");
		sql = "INSERT INTO miembros VALUES ('" + usuario + "', '" + idGrupo + "');";	//inserta en la tabla miembros al admin
		ConBD.executeUpdate(sql);
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
	}
	public int getIdGrupo() {
		return idGrupo;
	}
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	public String getAdministrador() {
		return administrador;
	}
}
