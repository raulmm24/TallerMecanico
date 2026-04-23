package org.iesalandalus.programacion.tallermecanico.modelo.cascada;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ModeloCascada implements Modelo {

    private final IClientes clientes;
    private final IVehiculos vehiculos;
    private final ITrabajos trabajos;

    public ModeloCascada(FabricaFuenteDatos fabricaFuenteDatos) {
        Objects.requireNonNull(fabricaFuenteDatos, "La fábrica de fuentes de datos no puede ser nula.");
        IFuenteDatos fuenteDatos = fabricaFuenteDatos.crear();
        this.clientes = fuenteDatos.crearClientes();
        this.vehiculos = fuenteDatos.crearVehiculos();
        this.trabajos = fuenteDatos.crearTrabajos();
    }

    @Override public void comenzar() {}
    @Override public void terminar() {}

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        clientes.insertar(new Cliente(cliente));
    }

    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        vehiculos.insertar(vehiculo);
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        clientes.buscar(trabajo.getCliente());
        vehiculos.buscar(trabajo.getVehiculo());
        trabajos.insertar(Trabajo.copiar(trabajo));
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Cliente encontrado = clientes.buscar(cliente);
        return (encontrado == null) ? null : new Cliente(encontrado);
    }

    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        return vehiculos.buscar(vehiculo);
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Trabajo encontrado = trabajos.buscar(trabajo);
        return (encontrado == null) ? null : Trabajo.copiar(encontrado);
    }

    @Override
    public void modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        clientes.modificar(cliente, nombre, telefono);
    }

    @Override
    public void anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        trabajos.anadirHoras(trabajo, horas);
    }

    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        trabajos.anadirPrecioMaterial(trabajo, precioMaterial);
    }

    @Override
    public void cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        trabajos.cerrar(trabajo, fechaFin);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        List<Trabajo> trabajosAsociados = trabajos.get(cliente);
        for (Trabajo t : trabajosAsociados) {
            trabajos.borrar(t);
        }
        clientes.borrar(cliente);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        List<Trabajo> trabajosAsociados = trabajos.get(vehiculo);
        for (Trabajo t : trabajosAsociados) {
            trabajos.borrar(t);
        }
        vehiculos.borrar(vehiculo);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        trabajos.borrar(trabajo);
    }

    @Override
    public List<Cliente> getClientes() {
        List<Cliente> copia = new ArrayList<>();
        for (Cliente c : clientes.get()) copia.add(new Cliente(c));
        return copia;
    }

    @Override
    public List<Vehiculo> getVehiculos() {
        return vehiculos.get(); // Record es inmutable
    }

    @Override
    public List<Trabajo> getTrabajos() {
        List<Trabajo> copia = new ArrayList<>();
        for (Trabajo t : trabajos.get()) copia.add(Trabajo.copiar(t));
        return copia;
    }

    @Override
    public List<Trabajo> getTrabajos(Cliente cliente) {
        List<Trabajo> copia = new ArrayList<>();
        for (Trabajo t : trabajos.get(cliente)) copia.add(Trabajo.copiar(t));
        return copia;
    }

    @Override
    public List<Trabajo> getTrabajos(Vehiculo vehiculo) {
        List<Trabajo> copia = new ArrayList<>();
        for (Trabajo t : trabajos.get(vehiculo)) copia.add(Trabajo.copiar(t));
        return copia;
    }
}