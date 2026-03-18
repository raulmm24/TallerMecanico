package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Clientes;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Revisiones;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.Vehiculos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Modelo {

    private Clientes clientes;
    private Vehiculos vehiculos;
    private Revisiones revisiones;

    public Modelo() {}

    public void comenzar() {
        clientes = new Clientes();
        vehiculos = new Vehiculos();
        revisiones = new Revisiones();
    }

    public void terminar() {
        System.out.println("El modelo ha terminado.");
    }

    public void insertar(Cliente cliente) {
        clientes.insertar(new Cliente(cliente));
    }

    public void insertar(Vehiculo vehiculo) {
        vehiculos.insertar(vehiculo);
    }

    public void insertar(Revision revision) {
        Cliente clienteEncontrado = clientes.buscar(revision.getCliente());
        Vehiculo vehiculoEncontrado = vehiculos.buscar(revision.getVehiculo());
        revisiones.insertar(new Revision(clienteEncontrado, vehiculoEncontrado, revision.getFechaInicio()));
    }

    public Cliente buscar(Cliente cliente) {
        Cliente encontrado = clientes.buscar(cliente);
        return (encontrado == null) ? null : new Cliente(encontrado);
    }

    public Vehiculo buscar(Vehiculo vehiculo) {
        return vehiculos.buscar(vehiculo);
    }

    public Revision buscar(Revision revision) {
        Revision encontrada = revisiones.buscar(revision);
        return (encontrada == null) ? null : new Revision(encontrada);
    }

    public Cliente modificar(Cliente cliente, String nombre, String telefono) {
        Cliente modificado = clientes.modificar(cliente, nombre, telefono);
        return (modificado == null) ? null : new Cliente(modificado);
    }

    public Revision anadirHoras(Revision revision, int horas) {
        Revision modificada = revisiones.anadirHoras(revision, horas);
        return (modificada == null) ? null : new Revision(modificada);
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) {
        Revision modificada = revisiones.anadirPrecioMaterial(revision, precioMaterial);
        return (modificada == null) ? null : new Revision(modificada);
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) {
        Revision cerrada = revisiones.cerrar(revision, fechaFin);
        return (cerrada == null) ? null : new Revision(cerrada);
    }

    public void borrar(Cliente cliente) {
        for (Revision revision : revisiones.get(cliente)) {
            revisiones.borrar(revision);
        }
        clientes.borrar(cliente);
    }

    public void borrar(Vehiculo vehiculo) {
        for (Revision revision : revisiones.get(vehiculo)) {
            revisiones.borrar(revision);
        }
        vehiculos.borrar(vehiculo);
    }

    public void borrar(Revision revision) {
        revisiones.borrar(revision);
    }

    public List<Cliente> getClientes() {
        List<Cliente> copia = new ArrayList<>();
        for (Cliente cliente : clientes.get()) {
            copia.add(new Cliente(cliente));
        }
        return copia;
    }

    public List<Vehiculo> getVehiculos() {
        return new ArrayList<>(vehiculos.get());
    }

    public List<Revision> getRevisiones() {
        List<Revision> copia = new ArrayList<>();
        for (Revision revision : revisiones.get()) {
            copia.add(new Revision(revision));
        }
        return copia;
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        List<Revision> copia = new ArrayList<>();
        for (Revision revision : revisiones.get(cliente)) {
            copia.add(new Revision(revision));
        }
        return copia;
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        List<Revision> copia = new ArrayList<>();
        for (Revision revision : revisiones.get(vehiculo)) {
            copia.add(new Revision(revision));
        }
        return copia;
    }
}