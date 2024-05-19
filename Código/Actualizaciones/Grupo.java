package proyectoJavaTricount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Grupo {
	private int idGrupo;
	private String nombreGrupo;
	private String administrador;
	private List<Usuario> miembros;
	private List<Gasto> gastosGrupo;

	public static void verGrupos(String usuario){
		try {
			String sql="SELECT IdGrupo FROM miembros WHERE nombre='"+usuario+"'";	//Sentencia de sql para sacar una tabla con todos los numbre de uuarios registrados
			String sql2,admin;
			ResultSet rs=ConBD.executeQuery(sql);
			ResultSet rs2;
			System.out.println("+-------+-----+------------------------------+");
			System.out.printf("|IdGrupo|Admin|%-30s|\n","Nombre del grupo");
			System.out.println("+-------+-----+------------------------------+");
			while (rs.next()) {
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
	public static void guardarGrupoBD(String nombreGrupo,String usuario){
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
	public static void comprobarNombreGrupo(String nombreGrupo,String usuario) throws ValorInvalidoException {
		if (nombreGrupo.length()>30) {	//Devuelve error si tiene mas de 30 caracteres
			throw new ValorInvalidoException("El nombre es demasiado largo");
		}
		try {
			String sql="SELECT nombreGrupo FROM grupos WHERE nombreGrupo='"+nombreGrupo+"' AND admin='"+usuario+"'";	//Sentencia de sql para comprobar si el usuario ya tiene creado un grupo con ese nombre
			ResultSet rs=ConBD.executeQuery(sql);
			
			if (rs.next()) {
				throw new ValorInvalidoException("Ya tienes creado un grupo con ese nombre");
			}
		}catch (SQLException e) {
			System.out.println("Error "+e.getMessage());
		}
	}

	public void agregarGasto(Gasto gasto) {// metodo para añadir gasto
		gastosGrupo.add(gasto);
	}

	public void eliminarGasto(Gasto gasto) {// metodo para quitar gasto
		gastosGrupo.remove(gasto);
	}

	public double calcularSaldo() {
        double totalGastos = 0.0;

        // Calcular el total de gastos
        for (Gasto gasto : gastosGrupo) {
            totalGastos += gasto.getCantidad();//metodo que esta en gastos y devuelve la cantidad// public double getCantidad(){ return cantidad};
        }

        // Calcular la media de gastos entre los miembros del grupo
        int numUsuario=miembros.size();

        double mediaGastos = numUsuario> 0 ? totalGastos/ numUsuario :0.0     ;

        return mediaGastos;
    }
	
	public Grupo(int idGrupo, String nombreGrupo, String administrador) {
		this.idGrupo = idGrupo;
		this.nombreGrupo = nombreGrupo;
		this.administrador = administrador;
	}
	public int getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(int idGrupo) {
		this.idGrupo = idGrupo;
	}
	public String getNombreGrupo() {
		return nombreGrupo;
	}
	public void setNombreGrupo(String nombreGrupo) {
		this.nombreGrupo = nombreGrupo;
	}
	public String getAdministrador() {
		return administrador;
	}
	public void setAdministrador(String administrador) {
		this.administrador = administrador;
	}
	public List<Usuario> getMiembros() {
		return miembros;
	}
	public void setMiembros(List<Usuario> miembros) {
		this.miembros = miembros;
	}
	public List<Gasto> getGastosGrupo() {
		return gastosGrupo;
	}
	public void setGastosGrupo(List<Gasto> gastosGrupo) {
		this.gastosGrupo = gastosGrupo;
	}
}
