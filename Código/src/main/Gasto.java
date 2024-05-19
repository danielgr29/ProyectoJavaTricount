package main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;

public class Gasto {
    public static void agregarGasto(Scanner scanner, Grupo grupo, Usuario usuario) {
        System.out.println("Ingrese descripción del gasto:");
        String descripcion = scanner.nextLine();
        System.out.println("Ingrese cantidad del gasto:");
        double cantidad = scanner.nextDouble();
        scanner.nextLine(); // Consumir el salto de línea

        String sql = "INSERT INTO gastos (IdGrupo, descripcion, cantidad, nombre, fecha) VALUES (" +
                     grupo.getIdGrupo() + ", '" + descripcion + "', " + cantidad + ", '" + usuario.getNombre() + "', '" +LocalDate.now()+"');";
        ConBD.executeUpdate(sql);
        System.out.println("Gasto agregado con éxito.");
    }

    public static void verGastos(Grupo grupo) {
        String sql = "SELECT descripcion, cantidad, nombre, fecha FROM gastos WHERE IdGrupo = " + grupo.getIdGrupo() + ";";
        ResultSet rs = ConBD.executeQuery(sql);
        System.out.println("Gastos del grupo:");
        try {
            while (rs.next()) {
                System.out.println("Descripción: " + rs.getString("descripcion") +
                                   ", Cantidad: " + rs.getDouble("cantidad") +
                                   ", Usuario: " + rs.getString("nombre") +
                                   ", Fecha: " + rs.getDate("fecha"));
            }
        } catch (SQLException ex) {
            System.out.println("Error al obtener gastos: " + ex.getMessage());
        }
    }

    public static void realizarTransaccion(Scanner scanner, Grupo grupo) {
        // Implementar la lógica para realizar la transacción
        System.out.println("Realizando transacción...");
    }
}

