package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Consola {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Consola() {}

    public static void mostrarCabecera(String mensaje) {
        System.out.printf("%n%s%n", mensaje);
        System.out.println("-".repeat(mensaje.length()));
    }

    public static void mostrarMenu() {
        mostrarCabecera("Gestión de Taller Mecánico");
        for (Evento opcion : Evento.values()) {
            System.out.println(opcion);
        }
    }

    public static Evento elegirOpcion() {
        Evento opcion = null;
        do {
            try {
                int numero = leerEntero("Elige una opción: ");
                opcion = Evento.get(numero);
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (opcion == null);
        return opcion;
    }

    public static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Entrada.entero();
    }

    public static float leerReal(String mensaje) {
        System.out.print(mensaje);
        return Entrada.real();
    }

    public static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    public static LocalDate leerFecha(String mensaje) {
        LocalDate fecha = null;
        do {
            try {
                String fechaIntroducida = leerCadena(mensaje + " (dd/MM/yyyy): ");
                fecha = LocalDate.parse(fechaIntroducida, FORMATO_FECHA);
            } catch (DateTimeParseException e) {
                System.out.println("ERROR: El formato de la fecha no es correcto o la fecha no es válida.");
            }
        } while (fecha == null);
        return fecha;
    }

}
