package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import javax.naming.OperationNotSupportedException;
import java.time.LocalDate;
import java.util.List;

public interface Modelo {

    void comenzar();
    void terminar();
    void insertar(Cliente cliente) throws TallerMecanicoExcepcion;
    void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion;
    void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion;
    Cliente buscar(Cliente cliente);
    Vehiculo buscar(Vehiculo vehiculo);
    Trabajo buscar(Trabajo trabajo);
    Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion, OperationNotSupportedException;
    Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion, OperationNotSupportedException;
    Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion, OperationNotSupportedException;
    Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion, OperationNotSupportedException;
    void borrar(Cliente cliente) throws TallerMecanicoExcepcion, OperationNotSupportedException;
    void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion, OperationNotSupportedException;
    void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion, OperationNotSupportedException;
    List<Cliente> getClientes();
    List<Vehiculo> getVehiculos();
    List<Trabajo> getTrabajos();
    List<Trabajo> getTrabajos(Cliente cliente);
    List<Trabajo> getTrabajos(Vehiculo vehiculo);
}
