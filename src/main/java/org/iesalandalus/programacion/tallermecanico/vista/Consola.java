package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


    public class Consola {

        private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";
        private static final Scanner scanner = new Scanner(System.in);

        private Consola() {}

        public static String mostrarCabecera(String mensaje) {
            System.out.println(mensaje);
            System.out.println("-".repeat(mensaje.length()));
            return mensaje;
        }

        public static void mostrarMenu() {
            mostrarCabecera("Menú de Opciones");
            // Se recorre el enum Opcion para mostrar todas las opciones.
            for (Opcion opcion : Opcion.values()) {
                System.out.println(opcion);
            }
        }

        public static Opcion elegirOpcion() {
            Opcion opcion = null;
            do {
                int opcionEntero = leerEntero("Elige una opción: ");
                if (opcionEntero >= 1 && opcionEntero <= Opcion.values().length) {
                    opcion = Opcion.values()[opcionEntero - 1];
                } else {
                    System.out.println("Opcion invalida. Intentalo de nuevo.");
                }
            } while (opcion == null);
            return opcion;
        }

        private static int leerEntero(String mensaje) {
            int numero = 0;
            boolean valido = false;
            do {
                System.out.print(mensaje);
                try {
                    numero = Integer.parseInt(scanner.nextLine());
                    valido = true;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, introduce un número entero.");
                }
            } while (!valido);
            return numero;
        }

        private static float leerReal(String mensaje) {
            float numero = 0;
            boolean valido = false;
            do {
                System.out.print(mensaje);
                try {
                    numero = Float.parseFloat(scanner.nextLine());
                    valido = true;
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, introduce un número real.");
                }
            } while (!valido);
            return numero;
        }

        private static String leerCadena(String mensaje) {
            System.out.print(mensaje);
            return scanner.nextLine();
        }

        private static LocalDate leerFecha(String mensaje) {
            LocalDate fecha = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CADENA_FORMATO_FECHA);
            boolean valido = false;
            do {
                System.out.print(mensaje);
                String entrada = scanner.nextLine();
                try {
                    fecha = LocalDate.parse(entrada, formatter);
                    valido = true;
                } catch (DateTimeParseException e) {
                    System.out.println("Fecha inválida. Por favor, introduce la fecha en el formato " + CADENA_FORMATO_FECHA);
                }
            } while (!valido);
            return fecha;
        }

        public static Cliente leerCliente() {
            String dni = leerCadena("Introduce el DNI del cliente: ");
            String nombre = leerCadena("Introduce el nombre del cliente: ");
            String telefono = leerCadena("Introduce el teléfono del cliente: ");
            return new Cliente(nombre, dni, telefono);
        }

        public static Cliente leerClienteDni() {
            String dni = leerCadena("Introduce el DNI del cliente: ");
            return new Cliente("",dni,"");
        }

        public static String leerNuevoNombre() {
            return leerCadena("Introduce el nuevo nombre: ");
        }

        public static String leerNuevoTelefono() {
            return leerCadena("Introduce el nuevo teléfono: ");
        }

        public static Vehiculo leerVehiculo() {
            String matricula = leerCadena("Introduce la matrícula del vehículo: ");
            String marca = leerCadena("Introduce la marca del vehículo: ");
            String modelo = leerCadena("Introduce el modelo del vehículo: ");
            return new Vehiculo(matricula, marca, modelo);
        }

        public static Vehiculo leerVehiculoMatricula() {
            String matricula = leerCadena("Introduce la matrícula del vehículo: ");
            return new Vehiculo(matricula,"","");
        }

        public static Revision leerRevision() {
            Cliente cliente = leerCliente();
            Vehiculo vehiculo = leerVehiculo();
            LocalDate fechaInicio = leerFecha("Introduce la fecha de inicio de la revisión (dd/MM/yyyy): ");
            return new Revision(cliente,vehiculo,fechaInicio);
        }

        public static int leerHoras() {
            return leerEntero("Introduce las horas de trabajo: ");
        }

        public static float leerPrecioMaterial() {
            return leerReal("Introduce el precio del material: ");
        }

        public static LocalDate leerFechaCierre() {
            return leerFecha("Introduce la fecha de cierre (dd/MM/yyyy): ");
        }
    }

