package org.iesalandalus.programacion.tallermecanico.vista.texto;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.GestorEventos;

import java.time.LocalDate;
import java.util.List;

public class VistaTexto implements Vista {

    private final GestorEventos gestorEventos = new GestorEventos(Evento.values());

    @Override
    public GestorEventos getGestorEventos() {
        return gestorEventos;
    }

    @Override
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

    @Override
    public void terminar() {
        System.out.println("Aplicación finalizada correctamente.");
    }

    // --- MÉTODOS DE LECTURA ---

    @Override
    public Cliente leerCliente() {
        return new Cliente(Consola.leerCadena("Nombre: "), Consola.leerCadena("DNI: "), Consola.leerCadena("Teléfono: "));
    }

    @Override
    public Cliente leerClienteDni() {
        return Cliente.get(Consola.leerCadena("Introduce el DNI del cliente: "));
    }

    @Override
    public String leerNuevoNombre() {
        return Consola.leerCadena("Introduce el nuevo nombre: ");
    }

    @Override
    public String leerNuevoTelefono() {
        return Consola.leerCadena("Introduce el nuevo teléfono: ");
    }

    @Override
    public Vehiculo leerVehiculo() {
        return new Vehiculo(Consola.leerCadena("Marca: "), Consola.leerCadena("Modelo: "), Consola.leerCadena("Matrícula: "));
    }

    @Override
    public Vehiculo leerVehiculoMatricula() {
        return Vehiculo.get(Consola.leerCadena("Introduce la matrícula: "));
    }

    @Override
    public Trabajo leerRevision() {
        return new Revision(leerClienteDni(), leerVehiculoMatricula(), Consola.leerFecha("Fecha de inicio"));
    }

    @Override
    public Trabajo leerMecanico() {
        return new Mecanico(leerClienteDni(), leerVehiculoMatricula(), Consola.leerFecha("Fecha de inicio"));
    }

    @Override
    public Trabajo leerTrabajoVehiculo() {
        return new Revision(Cliente.get("11111111H"), leerVehiculoMatricula(), LocalDate.now());
    }

    @Override
    public int leerHoras() {
        return Consola.leerEntero("Introduce las horas: ");
    }

    @Override
    public float leerPrecioMaterial() {
        return Consola.leerReal("Introduce el precio del material: ");
    }

    @Override
    public LocalDate leerFechaCierre() {
        return Consola.leerFecha("Fecha de cierre");
    }

    // --- MÉTODOS DE SALIDA ---

    @Override
    public void notificarResultado(Evento evento, String texto, boolean exito) {
        System.out.printf("[%s] %s: %s%n", exito ? "OK" : "ERROR", evento, texto);
    }

    @Override
    public void mostrarCliente(Cliente cliente) {
        System.out.println(cliente != null ? cliente : "Cliente no encontrado.");
    }

    @Override
    public void mostrarVehiculo(Vehiculo vehiculo) {
        System.out.println(vehiculo != null ? vehiculo : "Vehículo no encontrado.");
    }

    @Override
    public void mostrarTrabajo(Trabajo trabajo) {
        System.out.println(trabajo != null ? trabajo : "Trabajo no encontrado.");
    }

    @Override
    public void mostrarClientes(List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("No hay clientes que mostrar.");
        } else {
            for (Cliente c : clientes) System.out.println(c);
        }
    }

    @Override
    public void mostrarVehiculos(List<Vehiculo> vehiculos) {
        if (vehiculos == null || vehiculos.isEmpty()) {
            System.out.println("No hay vehículos que mostrar.");
        } else {
            for (Vehiculo v : vehiculos) System.out.println(v);
        }
    }

    @Override
    public void mostrarTrabajos(List<Trabajo> trabajos) {
        if (trabajos == null || trabajos.isEmpty()) {
            System.out.println("No hay trabajos que mostrar.");
        } else {
            for (Trabajo t : trabajos) System.out.println(t);
        }
    }
}