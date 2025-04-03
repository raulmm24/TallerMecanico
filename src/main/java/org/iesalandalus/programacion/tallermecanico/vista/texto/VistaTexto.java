package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class VistaTexto implements Vista {

    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());
    Scanner scanner;
    Evento evento;

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
    public void comenzar() {
        Evento evento; {
            do {
                Consola.mostrarMenu();
                evento = Consola.elegirOpcion();
                ejecutar(evento);
            } while (evento != Evento.SALIR);
        }
    }

    @Override
    public void ejecutar(Evento opcion) {
        Consola.mostrarCabecera(evento.toString());
        gestorEventos.notificar(evento);
    }

    @Override
    public void terminar() {
        Consola.mostrarCabecera("¡Hasta pronto!");
    }

    @Override
    public Cliente leerCliente() {
        System.out.print("Ingrese el nombre del cliente");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese DNI");
        String dni = scanner.nextLine();
        System.out.print("Ingrese el telefono");
        String telefono = scanner.nextLine();
        return new Cliente(nombre,dni,telefono);
    }

    @Override
    public Cliente leerClienteDni() {
        System.out.print("Ingrese el DNI del cliente");
        String dni = scanner.nextLine();
        return new Cliente(null,dni,null);
    }

    @Override
    public String leerNuevoNombre() {
        System.out.print("Ingrese nuevo nombre");
        return scanner.nextLine();
    }

    @Override
    public String leerNuevoTelefono() {
        System.out.print("Ingrese nuevo telefono");
        return scanner.nextLine();
    }

    @Override
    public Vehiculo leerVehiculo() {
        System.out.print("Ingrese la matricula");
        String matricula = scanner.nextLine();
        System.out.print("Ingrese marca");
        String marca = scanner.nextLine();
        System.out.print("Ingrese modelo");
        String modelo = scanner.nextLine();
        return new Vehiculo(matricula,marca,modelo);
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        System.out.print("Ingrese la matricula del vehiculo");
        String matricula = scanner.nextLine();
        return new Vehiculo(null, null, matricula);
    }

    @Override
    public Trabajo leerRevision() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = Consola.leerFecha("Introduce la fecha de inicio");
        return new Revision(cliente, vehiculo, fechaInicio);
    }

    @Override
    public Trabajo leerMecanico() {
        Cliente cliente = leerClienteDni();
        Vehiculo vehiculo = leerVehiculoMatricula();
        LocalDate fechaInicio = Consola.leerFecha("Introduce la fecha de inicio");
        return new Revision(cliente, vehiculo, fechaInicio);
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        return Trabajo.get(leerVehiculoMatricula());
    }

    @Override
    public int leerHoras() {
        System.out.print("Ingrese el número de horas trabajadas: ");
        return scanner.nextInt();
    }

    @Override
    public float leerPrecioMaterial() {
        System.out.print("Ingrese el costo del material utilizado: ");
        return scanner.nextFloat();
    }

    @Override
    public LocalDate leerFechaCierre() {
        System.out.print("Ingrese la fecha de cierre (YYYY-MM-DD): ");
        return LocalDate.parse(scanner.next());
    }

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {

    }

    @Override
    public void notificarResultado(boolean exito, String texto, Evento evento) {
        if (exito) {
            System.out.println("ÉXITO: " + texto);
        } else {
            System.out.println("ERROR: " + texto);
        }
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        System.out.println("Cliente: " + cliente);
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {
        System.out.println("Vehículo: " + vehiculo);
    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {
        System.out.println("Trabajo: " + trabajo);
    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        if (!clientes.isEmpty()) {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        } else {
            System.out.println("No hay clientes que mostrar: ");
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        for (Vehiculo vehiculo : vehiculos) {
            System.out.println(vehiculo);
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        for (Trabajo trabajo : trabajos) {
            System.out.println(trabajo);
        }
    }
}