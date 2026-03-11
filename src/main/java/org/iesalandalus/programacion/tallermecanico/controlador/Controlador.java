package org.iesalandalus.programacion.tallermecanico.controlador;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.vista.Vista;

import java.time.LocalDate;
import java.util.List;

public class Controlador {

    private final Modelo modelo;
    private final Vista vista;

    public Controlador(Modelo modelo, Vista vista) {
        if (modelo == null || vista == null) {
            throw new NullPointerException("ERROR: el modelo y la vista no pueden ser nulos");
        }
        this.modelo = modelo;
        this.vista = vista;
    }

    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    public void terminar() {
        modelo.terminar();
        vista.terminar();
    }

    /* METODOS INSERTAR */

    public void insertar(Cliente cliente) {modelo.insertar(cliente);}
    public void insertar(Vehiculo vehiculo) {modelo.insertar(vehiculo);}
    public void insertar(Revision revision) {modelo.insertar(revision);}


    /* METODOS BUSCAR */

    public Cliente buscar(Cliente cliente) {return modelo.buscar(cliente);}
    public Vehiculo buscar(Vehiculo vehiculo) {return modelo.buscar(vehiculo);}
    public Revision buscar(Revision revision) {return modelo.buscar(revision);}

    /* METODOS BORRAR*/

    public void borrar(Cliente cliente) { modelo.borrar(cliente); }
    public void borrar(Vehiculo vehiculo) { modelo.borrar(vehiculo); }
    public void borrar(Revision revision) { modelo.borrar(revision); }

    public void modificar(Cliente cliente, String nombre, String telefono) {
        modelo.modificar(cliente, nombre, telefono);
    }

    public void anadirHoras(Revision revision, int horas) {
        modelo.anadirHoras(revision, horas);
    }

    public void anadirPrecioMaterial(Revision revision, float precioMaterial) {
        modelo.anadirPrecioMaterial(revision, precioMaterial);
    }

    public void cerrar(Revision revision, LocalDate fechaFin) {
        modelo.cerrar(revision, fechaFin);
    }

    /* MÉTODOS LISTAR */

    public List<Cliente> getClientes() {
        return modelo.getClientes();
    }

    public List<Vehiculo> getVehiculos() {
        return modelo.getVehiculos();
    }

    public List<Revision> getRevisiones() {
        return modelo.getRevisiones();
    }

    public List<Revision> getRevisiones(Cliente cliente) {
        return modelo.getRevisiones(cliente);
    }

    public List<Revision> getRevisiones(Vehiculo vehiculo) {
        return modelo.getRevisiones(vehiculo);
    }
}
