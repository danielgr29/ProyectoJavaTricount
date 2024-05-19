package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Grupo {
    private int idGrupo;
    private String nombreGrupo;
    private Usuario admin;

    public Grupo(int idGrupo, String nombreGrupo, Usuario admin) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.admin = admin;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public Usuario getAdmin() {
        return admin;
    }

    public static Grupo crearGrupo(Scanner scanner, Usuario usuario) {
        System.out.println("Ingrese nombre del grupo:");
        String nombreGrupo = scanner.nextLine();
        String sql = "INSERT INTO grupos (nombreGrupo, admin) VALUES ('" + nombreGrupo + "', '" + usuario.getNombre()
                + "');";
        ConBD.executeUpdate(sql);

        // Obtener el ID autoincremental del grupo recién creado
        sql = "SELECT IdGrupo from grupos WHERE nombreGrupo = '" + nombreGrupo + "';";
        ResultSet rs = ConBD.executeQuery(sql);

        try {
            if (rs.next()) {
                int idGrupo = rs.getInt("IdGrupo");
                System.out.println("Grupo nº " + idGrupo + " creado con éxito.");
                System.out.println(idGrupo);
                sql = "INSERT INTO miembros (nombre, IdGrupo) VALUES ('" + usuario.getNombre() + "', '" + idGrupo
                        + "');";
                ConBD.executeUpdate(sql);
                return new Grupo(idGrupo, nombreGrupo, usuario);
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener ID del grupo: " + ex.getMessage());
        }
        return null;
    }

    public static void verGrupos(Usuario usuario) {
        String sql = "SELECT IdGrupo, nombreGrupo FROM grupos WHERE admin = '" + usuario.getNombre() + "' " +
                "OR IdGrupo IN (SELECT IdGrupo FROM miembros WHERE nombre = '" + usuario.getNombre() + "');";
        ResultSet rs = ConBD.executeQuery(sql);
        System.out.println("Grupos a los que pertenece:");
        try {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("IdGrupo") + ", Nombre: " + rs.getString("nombreGrupo"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener grupos: " + ex.getMessage());
        }
    }

    public static Grupo seleccionarGrupo(Scanner scanner, Usuario usuario) {
        System.out.println("Ingrese ID del grupo:");
        int idGrupo = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        String sql = "SELECT nombreGrupo, admin FROM grupos WHERE IdGrupo = " + idGrupo + ";";
        ResultSet rs = ConBD.executeQuery(sql);
        try {
            if (rs.next()) {
                String nombreGrupo = rs.getString("nombreGrupo");
                String adminNombre = rs.getString("admin");
                Usuario admin = new Usuario(adminNombre, "", ""); // Crear un objeto Usuario con el nombre del
                                                                  // administrador
                if (admin.getNombre().equals(usuario.getNombre()) || esMiembro(idGrupo, usuario.getNombre())) {
                    System.out.println("Grupo seleccionado: " + nombreGrupo);
                    return new Grupo(idGrupo, nombreGrupo, admin);
                } else {
                    System.out.println("No pertenece a este grupo.");
                }
            } else {
                System.out.println("Grupo no encontrado.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al seleccionar grupo: " + ex.getMessage());
        }
        return null;
    }

    private static boolean esMiembro(int idGrupo, String nombreUsuario) {
        String sql = "SELECT * FROM miembros WHERE IdGrupo = " + idGrupo + " AND nombre = '" + nombreUsuario + "';";
        ResultSet rs = ConBD.executeQuery(sql);
        try {
            return rs.next();
        } catch (SQLException ex) {
            System.out.println("Error al comprobar membresía: " + ex.getMessage());
        }
        return false;
    }

    public static void verMiembros(Grupo grupo) {
        String sql = "SELECT nombre FROM miembros WHERE IdGrupo = " + grupo.getIdGrupo() + ";";
        ResultSet rs = ConBD.executeQuery(sql);
        System.out.println("Miembros del grupo:");
        try {
            while (rs.next()) {
                System.out.println(rs.getString("nombre"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener miembros: " + ex.getMessage());
        }
    }

    public static void eliminarMiembro(Scanner scanner, Grupo grupo) {
        System.out.println("Ingrese nombre del miembro a eliminar:");
        String nombreMiembro = scanner.nextLine();
        String sql = "DELETE FROM miembros WHERE IdGrupo = " + grupo.getIdGrupo() + " AND nombre = '" + nombreMiembro
                + "';";
        ConBD.executeUpdate(sql);
        System.out.println("Miembro eliminado del grupo.");
    }

    public static void anadirMiembro(Scanner scanner, Grupo grupo) {
        System.out.println("Ingrese nombre del miembro a añadir:");
        String nombreMiembro = scanner.nextLine();
        String sql = "INSERT INTO miembros (IdGrupo, nombre) VALUES (" + grupo.getIdGrupo() + ", '" + nombreMiembro
                + "');";
        ConBD.executeUpdate(sql);
        System.out.println("Miembro añadido con éxito.");
    }
}
