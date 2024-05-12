package main;

import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrearBD {
/* 
 * esta clase solo sirve para crearos vuestra base de datos
 * en base a los diagramas que hemos hecho,
 * a su vez tambien trae secciones comentadas para borrar
 * y probar la base de datos.
 * Os recomiendo instalar Docker desktop (ya que todos estamos en windows),
 * y en una terminal en windows lanzar el comando que puso María José
 * en la plataforma
 */
     public static void main(String[] args) {
          Connection con;
          Statement sentencia;
          String sql;
          ResultSet rs;
          String url = "jdbc:mysql://localhost:3337/prueba";
          // Class.forName("org.mariadb.jdbc.Driver");
          try {

               con = DriverManager.getConnection(url, "root", "root");
               System.out.println("hecho1");
               sentencia = con.createStatement();

               /* seccion para borrar la base de datos */

               // sql = "SET FOREIGN_KEY_CHECKS=0;";
               // sentencia.executeUpdate(sql);
               // sql = "DROP TABLE usuarios;";
               // sentencia.executeUpdate(sql);
               // sql = "DROP TABLE grupos;";
               // sentencia.executeUpdate(sql);
               // sql = "DROP TABLE gastos;";
               // sentencia.executeUpdate(sql);
               // sql = "DROP TABLE miembros;";
               // sentencia.executeUpdate(sql);
               // sql = "SET FOREIGN_KEY_CHECKS=1;";
               // sentencia.executeUpdate(sql);

               sql = "CREATE TABLE usuarios(nombre VARCHAR(30) NOT NULL, contra VARCHAR(30) NOT NULL, nombreCompleto VARCHAR(30), "
                         +
                         "PRIMARY KEY(nombre));";
               sentencia.executeUpdate(sql);
               sql = "CREATE TABLE grupos(IdGrupo CHAR(7) NOT NULL, nombreGrupo VARCHAR(30) NOT NULL, admin VARCHAR(30), "
                         +
                         "PRIMARY KEY(IdGrupo), " +
                         "FOREIGN KEY (admin) REFERENCES usuarios (nombre));";
               sentencia.executeUpdate(sql);
               sql = "CREATE TABLE gastos(GastosId CHAR(7) NOT NULL, cantidad DOUBLE(12,2) NOT NULL, " +
                         "descripcion VARCHAR(50), fecha DATE NOT NULL, nombre VARCHAR(30), IdGrupo CHAR(7), " +
                         "PRIMARY KEY(GastosId), " +
                         "FOREIGN KEY (nombre) REFERENCES usuarios (nombre), " +
                         "FOREIGN KEY (IdGrupo) REFERENCES grupos (IdGrupo));";
               sentencia.executeUpdate(sql);
               sql = "CREATE TABLE miembros(nombre VARCHAR(30), IdGrupo CHAR(7), " +
                         "PRIMARY KEY(nombre, IdGrupo), " +
                         "FOREIGN KEY (nombre) REFERENCES usuarios (nombre), " +
                         "FOREIGN KEY (IdGrupo) REFERENCES grupos (IdGrupo));";
               sentencia.executeUpdate(sql);

               /* seccion para ver contenido de las tablas, en este caso de la tabla usuario */

               // sql = "SELECT * FROM usuarios";
               // rs = sentencia.executeQuery(sql);
               // System.out.println("Lista de usuarios:");
               // while (rs.next()) {
               // String nombred = rs.getString("nombre");
               // String contrad = rs.getString("contra");
               // String nombreCd = rs.getString("nombreCompleto");
               // System.out.println("Usuario: " + nombred + "\tContra: " + contrad + "\tnombre "
               // +
               // "completo: " + nombreCd);
               // }

               con.close();
               System.out.println("hecho");

          } catch (SQLException ex) {
               System.out.println("error" + ex.getMessage());
          }
     }

}
