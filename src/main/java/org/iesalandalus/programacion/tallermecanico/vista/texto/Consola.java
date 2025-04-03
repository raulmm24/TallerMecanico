package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import java.time.LocalDate;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

    public class Consola {

        private static final String CADENA_FORMATO_FECHA = "dd/MM/yyyy";
        private static final Scanner scanner = new Scanner(System.in);

        private Consola() {}

        public static void mostrarCabecera(String mensaje) {
            System.out.println(mensaje);
            System.out.println("-".repeat(mensaje.length()));
        }

        public static void mostrarMenu() {
            mostrarCabecera("Menú de Opciones");

            for (Evento opcion : Evento.values()) {
                System.out.println(opcion);
            }
        }

        public static Evento elegirOpcion() {
            Evento opcion = null;
            do {
                int opcionEntero = leerEntero("Elige una opción: ");
                try {
                    opcion = Evento.get(opcionEntero);
                } catch (IllegalArgumentException e){
                    System.out.println(e.getMessage());
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

        static LocalDate leerFecha(String mensaje) {
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

    }