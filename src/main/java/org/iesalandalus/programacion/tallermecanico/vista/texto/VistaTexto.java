package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;

public class VistaTexto {

    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());

    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    public void comenzar() {
        Evento opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while (opcion != Evento.SALIR);
    }

    private void ejecutar(Evento opcion) {
        if (opcion != null) {
            if (opcion == Evento.SALIR) {
                terminar();
            } else {
                gestorEventos.notificar(opcion);
            }
        }
    }

    public void terminar() {
        System.out.println("Aplicación finalizada correctamente.");
    }

    // --- MÉTODOS DE LECTURA ---

    public Cliente leerCliente() {
        return new Cliente(Consola.leerCadena("Nombre: "), Consola.leerCadena("DNI: "), Consola.leerCadena("Teléfono: "));
    }

    public Cliente leerClienteDni() {
        return Cliente.get(Consola.leerCadena("Introduce el DNI del cliente: "));
    }

    public String leerNuevoNombre() {
        return Consola.leerCadena("Introduce el nuevo nombre: ");
    }

    public String leerNuevoTelefono() {
        return Consola.leerCadena("Introduce el nuevo teléfono: ");
    }

    public Vehiculo leerVehiculo() {
        return new Vehiculo(Consola.leerCadena("Marca: "), Consola.leerCadena("Modelo: "), Consola.leerCadena("Matrícula: "));
    }

    public Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get(Consola.leerCadena("Introduce la matrícula: "));
    }

    public Trabajo leerRevision() {
        return new Revision(leerClienteDni(), leerVehiculoMatricula(), Consola.leerFecha("Fecha de inicio"));
    }

    public Trabajo leerMecanico() {
        return new Mecanico(leerClienteDni(), leerVehiculoMatricula(), Consola.leerFecha("Fecha de inicio"));
    }

    public Trabajo leerTrabajoVehiculo() {
        // Objeto ficticio para realizar búsquedas por vehículo
        return new Revision(Cliente.get("11111111H"), leerVehiculoMatricula(), LocalDate.now());
    }

    public int leerHoras() {
        return Consola.leerEntero("Introduce las horas: ");
    }

    public float leerPrecioMaterial() {
        return Consola.leerReal("Introduce el precio del material: ");
    }

    public LocalDate leerFechaCierre() {
        return Consola.leerFecha("Fecha de cierre");
    }

    // --- MÉTODOS DE SALIDA ---

    public void notificarResultado(Evento evento, String texto, boolean exito) {
        System.out.printf("[%s] %s: %s%n", exito ? "OK" : "ERROR", evento, texto);
    }

    public void mostrarCliente(Cliente cliente) {
        System.out.println(cliente != null ? cliente : "Cliente no encontrado.");
    }

    public void mostrarVehiculo(Vehiculo vehiculo) {
        System.out.println(vehiculo != null ? vehiculo : "Vehículo no encontrado.");
    }

    public void mostrarTrabajo(Trabajo trabajo) {
        System.out.println(trabajo != null ? trabajo : "Trabajo no encontrado.");
    }

    public void mostrarClientes(List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No hay clientes que mostrar.");
        } else {
            for (Cliente c : clientes) System.out.println(c);
        }
    }

    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        if (vehiculos == null || vehiculos.isEmpty()) {
            System.out.println("No hay vehículos que mostrar.");
        } else {
            for (Vehiculo v : vehiculos) System.out.println(v);
        }
    }

    public void mostrarTrabajos(List<Trabajo> trabajos) {
        if (trabajos == null || trabajos.isEmpty()) {
            System.out.println("No hay trabajos que mostrar.");
        } else {
            for (Trabajo t : trabajos) System.out.println(t);
        }
    }
}