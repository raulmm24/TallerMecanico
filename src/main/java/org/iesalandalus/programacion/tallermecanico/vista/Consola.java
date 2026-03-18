package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.utilidades.Entrada;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Consola {

    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Consola() {}

    public static void mostrarCabecera(String mensaje) {
        System.out.printf("%n%s%n", mensaje);
        System.out.println("-".repeat(mensaje.length()));
    }

    public static void mostrarMenu() {
        mostrarCabecera("Gestión de Taller Mecánico");
        for (Opcion opcion : Opcion.values()) {
            System.out.println(opcion);
        }
    }

    public static Opcion elegirOpcion() {
        Opcion opcion = null;
        do {
            try {
                int numero = leerEntero("Elige una opción: ");
                opcion = Opcion.get(numero);
            } catch (IllegalArgumentException e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        } while (opcion == null);
        return opcion;
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        return Entrada.entero();
    }

    private static float leerReal(String mensaje) {
        System.out.print(mensaje);
        return Entrada.real();
    }

    private static String leerCadena(String mensaje) {
        System.out.print(mensaje);
        return Entrada.cadena();
    }

    private static LocalDate leerFecha(String mensaje) {
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

    public static Cliente leerCliente() {
        String nombre = leerCadena("Introduce tu nombre: ");
        String dni = leerCadena("Introduce tu dni: ");
        String telefono = leerCadena("Introduce tu telefono: ");
        return new Cliente(nombre,dni,telefono);
    }

    public static Cliente leerClienteDni() {
        return Cliente.get(leerCadena("Introduce el dni del cliente: "));
    }

    public static String leerNuevoNombre() {
        return leerCadena("Introduce un nuevo nombre: ");
    }

    public static String leerNuevoTelefono() {
        return leerCadena("Introduce un nuevo telefono: ");
    }

    public static Vehiculo leerVehiculo() {
        String marca = leerCadena("Introduce la marca: ");
        String modelo = leerCadena("Introduce el modelo: ");
        String matricula = leerCadena("Introduce la matricula: ");
        return new Vehiculo(marca,modelo,matricula);
    }

    public static Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get(leerCadena("Introduce la matricula del vehículo del cliente: "));
    }

    public static Revision leerRevision() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = leerFecha("Introduce la fecha de inicio: ");
        return new Revision(cliente,vehiculo,fechaInicio);
    }

    public static int leerHoras() {
        return leerEntero("Introduce el número de horas: ");
    }

    public static float leerPrecioMaterial() {
        return leerReal("Introduce el precio del material: ");
    }

    public static LocalDate leerFechaCierre() {
        return leerFecha("Introduce la fecha de cierre: ");
    }
}
