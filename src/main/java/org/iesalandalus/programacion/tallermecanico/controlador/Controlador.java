package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.Evento;
import org.iesalandalus.programacion.tallermecanico.vista.eventos.ReceptorEventos;

import java.util.Objects;

public class Controlador implements IControlador, ReceptorEventos {

    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        this.modelo = Objects.requireNonNull(modelo, "El modelo no puede ser nulo.");
        this.vista = Objects.requireNonNull(vista, "La vista no puede ser nula.");
        this.vista.getGestorEventos().suscribir(this, Evento.values());
    }

    @Override
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    @Override
    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    @Override
    public void actualizar(Evento evento) {
        Objects.requireNonNull(evento, "El evento no puede ser nulo.");
        try {
            switch (evento) {

                // CLIENTES
                case INSERTAR_CLIENTE -> {
                    modelo.insertar(vista.leerCliente());
                    vista.notificarResultado(evento, "Cliente insertado con éxito", true);
                }
                case BUSCAR_CLIENTE -> vista.mostrarCliente(modelo.buscar(vista.leerClienteDni()));
                case BORRAR_CLIENTE -> {
                    modelo.borrar(vista.leerClienteDni());
                    vista.notificarResultado(evento, "Cliente borrado con éxito", true);
                }
                case LISTAR_CLIENTES -> vista.mostrarClientes(modelo.getClientes());
                case MODIFICAR_CLIENTE -> {
                    modelo.modificar(vista.leerClienteDni(), vista.leerNuevoNombre(), vista.leerNuevoTelefono());
                    vista.notificarResultado(evento, "Cliente modificado con éxito", true);
                }

                // VEHÍCULOS
                case INSERTAR_VEHICULO -> {
                    modelo.insertar(vista.leerVehiculo());
                    vista.notificarResultado(evento, "Vehículo insertado con éxito", true);
                }
                case BUSCAR_VEHICULO -> vista.mostrarVehiculo(modelo.buscar(vista.leerVehiculoMatricula()));
                case BORRAR_VEHICULO -> {
                    modelo.borrar(vista.leerVehiculoMatricula());
                    vista.notificarResultado(evento, "Vehículo borrado con éxito", true);
                }
                case LISTAR_VEHICULOS -> vista.mostrarVehiculos(modelo.getVehiculos());

                // TRABAJOS
                case INSERTAR_REVISION -> {
                    modelo.insertar(vista.leerRevision());
                    vista.notificarResultado(evento, "Revisión insertada con éxito", true);
                }
                case INSERTAR_MECANICO -> {
                    modelo.insertar(vista.leerMecanico());
                    vista.notificarResultado(evento, "Mecánico insertado con éxito", true);
                }
                case BUSCAR_TRABAJO -> vista.mostrarTrabajo(modelo.buscar(vista.leerTrabajoVehiculo()));
                case BORRAR_TRABAJO -> {
                    modelo.borrar(vista.leerTrabajoVehiculo());
                    vista.notificarResultado(evento, "Trabajo borrado con éxito", true);
                }
                case LISTAR_TRABAJOS -> vista.mostrarTrabajos(modelo.getTrabajos());
                case LISTAR_TRABAJOS_CLIENTE -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerClienteDni()));
                case LISTAR_TRABAJOS_VEHICULO -> vista.mostrarTrabajos(modelo.getTrabajos(vista.leerVehiculoMatricula()));

                // OPERACIONES TRABAJOS
                case ANADIR_HORAS_TRABAJO -> {
                    modelo.anadirHoras(vista.leerTrabajoVehiculo(), vista.leerHoras());
                    vista.notificarResultado(evento, "Horas añadidas con éxito", true);
                }
                case ANADIR_PRECIO_MATERIAL_TRABAJO -> {
                    modelo.anadirPrecioMaterial(vista.leerTrabajoVehiculo(), vista.leerPrecioMaterial());
                    vista.notificarResultado(evento, "Precio de material añadido con éxito", true);
                }
                case CERRAR_TRABAJO -> {
                    modelo.cerrar(vista.leerTrabajoVehiculo(), vista.leerFechaCierre());
                    vista.notificarResultado(evento, "Trabajo cerrado con éxito", true);
                }
                case SALIR -> terminar();
            }
        } catch (Exception e) {
            vista.notificarResultado(evento, e.getMessage(), false);
        }
    }
}