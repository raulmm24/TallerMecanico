package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;

import java.util.List;

public interface IClientes {
    public List<Cliente> get();
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion;
    public Cliente modificar(Cliente cliente,String nombre,String telefono) throws TallerMecanicoExcepcion;
    public Cliente buscar(Cliente cliente);
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion;
}
