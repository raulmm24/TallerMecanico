package org.iesalandalus.programacion.tallermecanico.modelo.negocio.memoria;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Trabajo;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Trabajos implements ITrabajos {

    private final List<Trabajo> trabajos;

    public Trabajos() {
        this.trabajos = new ArrayList<>();
    }

    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(trabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> resultado = new ArrayList<>();
        for (Trabajo t : trabajos) {
            if (t.getCliente().equals(cliente)) resultado.add(t);
        }
        return resultado;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> resultado = new ArrayList<>();
        for (Trabajo t : trabajos) {
            if (t.getVehiculo().equals(vehiculo)) resultado.add(t);
        }
        return resultado;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        if (trabajo == null) throw new NullPointerException("No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        trabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws TallerMecanicoExcepcion {
        for (Trabajo t : trabajos) {
            if (!t.estaCerrado()) {
                if (t.getCliente().equals(cliente)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                }
                if (t.getVehiculo().equals(vehiculo)) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                }
            } else {
                LocalDate fechaFinTrabajo = t.getFechaFin();
                if (fechaFinTrabajo != null) {
                    if (fechaFinTrabajo.isAfter(fechaInicio)) {
                        if (t.getCliente().equals(cliente)) {
                            throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior");
                    }
                    if (t.getVehiculo().equals(vehiculo)) {
                        throw new TallerMecanicoExcepcion("El vehiculo tiene otro trabajo posterior.");
                    }
                    } else if (fechaFinTrabajo.isEqual(fechaInicio)) {
                        if (t.getCliente().equals(cliente)) {
                            throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                        }
                        if (t.getVehiculo().equals(vehiculo)) {
                            throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
                        }
                    }
                }
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        if (trabajo == null) throw new NullPointerException("No puedo añadir horas a un trabajo nulo.");
        Trabajo t = getTrabajoAbierto(trabajo.getVehiculo());
        if (t == null) throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        t.anadirHoras(horas);
        return t;
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) {
        for (Trabajo t : trabajos) {
            if (t.getVehiculo().equals(vehiculo) && !t.estaCerrado()) return t;
        }
        return null;
    }

    @Override
    public Trabajo anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        if (trabajo == null) throw new NullPointerException("No puedo añadir precio del material a un trabajo nulo.");
        Trabajo t = getTrabajoAbierto(trabajo.getVehiculo());
        if (t == null) throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        if (!(t instanceof Mecanico)) throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        ((Mecanico) t).anadirPrecioMaterial(precioMaterial);
        return t;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        if (trabajo == null) throw new NullPointerException("No puedo cerrar un trabajo nulo.");
        Trabajo t = getTrabajoAbierto(trabajo.getVehiculo());
        if (t == null) throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        t.cerrar(fechaFin);
        return t;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        if (trabajo == null) throw new NullPointerException("No se puede buscar un trabajo nulo.");
        for (Trabajo t : trabajos) {
            if (t.equals(trabajo)) return t;
        }
        return null;
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        if (trabajo == null) throw new NullPointerException("No se puede borrar un trabajo nulo.");
        if (!trabajos.remove(trabajo)) throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
    }
}