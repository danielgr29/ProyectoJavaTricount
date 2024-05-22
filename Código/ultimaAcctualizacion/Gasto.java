package proyectoTricount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Gasto {
	//private int igGasto;
	//private int idGrupo;
	//private String usuario;
	//private double cantidad;
	//private String descripcion;
	//private LocalDate fecha;
	
	public static void verGastos(Grupo grupo) {	//muestra una tabla con todos los gastos del grupo ordenados por fecha
		try {
			String sql="SELECT * FROM gastos WHERE IdGrupo='"+grupo.getIdGrupo()+"' ORDER BY fecha DESC";
			ResultSet rs=ConBD.executeQuery(sql);
			System.out.println("+-------+----------+----------+----------+--------------------------------------------------+");
			System.out.println("|IdGasto|Usuario   |Importe   |Fecha     |Descripción                                       |");
			System.out.println("+-------+----------+----------+----------+--------------------------------------------------+");
			while (rs.next()) {
				System.out.printf("|%-7d|%-10s|%-10.2f|%-10s|%-50s|\n",rs.getInt("GastosId"),rs.getString("nombre"),rs.getDouble("Cantidad"),rs.getString("fecha"),rs.getString("descripcion"));
			}
			System.out.println("+-------+----------+----------+----------+--------------------------------------------------+");
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
	}
	public static void agregarGasto(Grupo grupo,String usuario,Scanner scanner) {	//pide introducir valores de un gasto y si son validos lo crea
		String desc;
		String fecha;
		double importe;
		scanner.nextLine();
		try {
			System.out.print("Introduce una descripción: ");
			desc=scanner.nextLine();
			comprobarDescripcion(desc);	
			
			System.out.print("Introduce la fecha(aaaa-mm-dd): ");
			fecha=scanner.nextLine();
			comprobarFecha(fecha);
			
			System.out.print("Introduce el importe: ");
			try {
				importe=scanner.nextDouble();
			}catch (Exception e) {
				scanner.nextLine();
				throw new ValorInvalidoException("Importe no valido.");
			}
			
			agregarGastoBD(importe,desc,fecha,usuario,grupo.getIdGrupo());
			System.out.println("Se ha añadido el gasto en el grupo (ID:"+grupo.getIdGrupo()+")"+grupo.getNombreGrupo());
		} catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		}
	}
	private static void agregarGastoBD(double importe,String desc,String fecha,String usuario, int idgrupo) {	//agrega un gasto a la base de datos
		String sql = "INSERT INTO gastos(cantidad,descripcion,fecha,nombre,IdGrupo) VALUES "
				+ "('" + importe + "', '" + desc + "', '" + fecha + "', '"+usuario+"', '"+idgrupo+"');";	
		ConBD.executeUpdate(sql);
	}
	public static void eliminarGasto(Grupo grupo,Scanner scanner){	//pide un IdGasto y si pertenece al grupo lo borra
		int idGasto=0;
		scanner.nextLine();
		try {
			System.out.print("Introduce el IdGasto: ");
			try {
				idGasto=scanner.nextInt();
			}catch (Exception e) {
				scanner.nextLine();
				throw new ValorInvalidoException("Id no valida.");
			}
			comprobarIdGasto(idGasto,grupo);
			String sql = "DELETE FROM gastos WHERE IdGrupo = " + grupo.getIdGrupo() + " AND GastosId = '" + idGasto + "';";
	        ConBD.executeUpdate(sql);
	        System.out.println("Gasto eliminado del grupo.");
		}catch (ValorInvalidoException e) {
			System.out.println(e.getMessage());
		}
	}
	private static void comprobarIdGasto(int idGasto,Grupo grupo) throws ValorInvalidoException {	//Comprueba que un IdGasto exista y que pertenezca al grupo pasado por parametro
		String sql="SELECT * FROM gastos WHERE GastosId='"+idGasto+"';";
		ResultSet rs=ConBD.executeQuery(sql);
		try {
			if (!rs.next()) {
				throw new ValorInvalidoException("El ID introducido no existe.");
			}else {
				if(rs.getInt("IdGrupo")!=grupo.getIdGrupo()) {
					throw new ValorInvalidoException("El ID introducido no coincide con ningun gasto del grupo seleccionado.");
				}
			}
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
	}
	private static void comprobarDescripcion(String desc) throws ValorInvalidoException {	//Comprueba que la descripcion no exceda cierta longitud
		if (desc.length()>50) {
			throw new ValorInvalidoException("La descripción es demasiado larga.");
		}
	}
	private static void comprobarFecha(String fecha) throws ValorInvalidoException {	//comprueba que la fecha pasada por parametro sea valida
		try {
			LocalDate.parse(fecha);
		}catch(Exception e) {
			throw new ValorInvalidoException("La fecha no es válida o esta mal escrita (ej. 2023-12-25)");
		}
	}
}
