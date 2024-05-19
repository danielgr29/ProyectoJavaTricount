package main;

import java.util.Scanner;

public class Main {
    private static Usuario usuarioActual;
    private static Grupo grupoActual;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (usuarioActual == null) {
                mostrarMenuInicial();
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                switch (opcion) {
                    case 1:
                        registrarse(scanner);
                        break;
                    case 2:
                        iniciarSesion(scanner);
                        break;
                    case 3:
                        System.out.println("Saliendo del programa...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            } else if (grupoActual == null) {
                mostrarMenuGrupos();
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                switch (opcion) {
                    case 1:
                        grupoActual = Grupo.crearGrupo(scanner, usuarioActual);
                        break;
                    case 2:
                        Grupo.verGrupos(usuarioActual);
                        break;
                    case 3:
                        grupoActual = Grupo.seleccionarGrupo(scanner, usuarioActual);
                        break;
                    case 4:
                        System.out.println("Saliendo del programa...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opción no válida.");
                }
            } else {
                mostrarMenuGrupoActivo();
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
                switch (opcion) {
                    case 1:
                        Gasto.agregarGasto(scanner, grupoActual, usuarioActual);
                        break;
                    case 2:
                        Gasto.verGastos(grupoActual);
                        break;
                    case 3:
                        Grupo.verMiembros(grupoActual);
                        break;
                    case 4:
                        Gasto.realizarTransaccion(scanner, grupoActual);
                        break;
                    case 5:
                        grupoActual = null; // Volver al menú de grupos
                        break;
                    case 6:
                        System.out.println("Saliendo del programa...");
                        scanner.close();
                        System.exit(0);
                        break;
                    case 7:
                        if (grupoActual.getAdmin().getNombre().equals(usuarioActual.getNombre())) {
                            Grupo.eliminarMiembro(scanner, grupoActual);
                        } else {
                            System.out.println("Debe ser administrador para eliminar miembros.");
                        }
                        break;
                    case 8:
                        if (grupoActual.getAdmin().getNombre().equals(usuarioActual.getNombre())) {
                            Grupo.anadirMiembro(scanner, grupoActual);
                        } else {
                            System.out.println("Debe ser administrador para añadir miembros.");
                        }
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            }
        }
    }

    private static void mostrarMenuInicial() {
        System.out.println("\n--- Menú Inicial ---");
        System.out.println("1. Registrarse");
        System.out.println("2. Iniciar sesión");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void mostrarMenuGrupos() {
        System.out.println("\n--- Menú Grupos ---");
        System.out.println("1. Crear grupo");
        System.out.println("2. Ver grupos");
        System.out.println("3. Seleccionar grupo");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static void mostrarMenuGrupoActivo() {
        System.out.println("\n--- Menú Grupo Activo ---");
        System.out.println("1. Agregar gasto");
        System.out.println("2. Ver gastos");
        System.out.println("3. Ver miembros del grupo");
        System.out.println("4. Realizar transacción");
        System.out.println("5. Volver al menú de grupos");
        System.out.println("6. Salir");
        if (grupoActual.getAdmin().getNombre().equals(usuarioActual.getNombre())) {
            System.out.println("7. Eliminar miembro del grupo");
            System.out.println("8. Añadir miembro al grupo");
        }
        System.out.print("Seleccione una opción: ");
    }

    private static void registrarse(Scanner scanner) {
        System.out.println("Ingrese nombre de usuario:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese contraseña:");
        String contra = scanner.nextLine();
        System.out.println("Ingrese su nombre completo:");
        String nombreCompleto = scanner.nextLine();

        Usuario usuario = new Usuario(nombre, contra, nombreCompleto);
        usuario.crearUsuarioBD();
        usuarioActual = usuario;
    }

    private static void iniciarSesion(Scanner scanner) {
        System.out.println("Ingrese nombre de usuario:");
        String nombre = scanner.nextLine();
        System.out.println("Ingrese contraseña:");
        String contra = scanner.nextLine();

        if (Usuario.comprobarContra(nombre, contra)) {
            usuarioActual = new Usuario(nombre, contra, Usuario.obtenerNombreCompleto(nombre, contra));
            System.out.println("Inicio de sesión exitoso.");
        } else {
            System.out.println("Nombre de usuario o contraseña incorrectos.");
        }
    }
}
