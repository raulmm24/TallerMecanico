package org.iesalandalus.programacion.tallermecanico.modelo.negocio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Revisiones {

    private final List<Revision> revisiones;

    public Revisiones() {
        revisiones = new ArrayList<>();
    }

    public List<Revision> get() {
        return new ArrayList<>(revisiones);
    }

    public List<Revision> get(Cliente cliente) {
        List<Revision> revisionesCliente = new ArrayList<>();
        for (Revision revision : revisiones) {
            if (revision.getCliente().equals(cliente)) {
                revisionesCliente.add(revision);
            }
        }
        return revisionesCliente;
    }

    public List<Revision> get(Vehiculo vehiculo) {
        List<Revision> revisionesVehiculo = new ArrayList<>();
        for (Revision revision : revisiones) {
            if (revision.getVehiculo().equals(vehiculo)) {
                revisionesVehiculo.add(revision);
            }
        }
        return revisionesVehiculo;
    }

    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede insertar una revisión nula.");
        comprobarRevision(revision.getCliente(), revision.getVehiculo(), revision.getFechaInicio());
        revisiones.add(revision);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        for (Revision revision : revisiones) {
            if (revision.getCliente().equals(cliente) && !revision.estaCerrado()) {
                throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
            }
            if (revision.getVehiculo().equals(vehiculo) && !revision.estaCerrado()) {
                throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
            }
            if (revision.getCliente().equals(cliente) && revision.estaCerrado() && !revision.getFechaFin().isBefore(fechaRevision)) {
                throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
            }
            if (revision.getVehiculo().equals(vehiculo) && revision.estaCerrado() && !revision.getFechaFin().isBefore(fechaRevision)) {
                throw new TallerMecanicoExcepcion("El vehículo tiene una revisión posterior.");
            }
        }
    }

    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");
        Revision revisionExistente = getRevision(revision);
        if (revisionExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        revisionExistente.anadirHoras(horas);
        return revisionExistente;
    }

    private Revision getRevision(Revision revision) {
        for (Revision rev : revisiones) {
            if (rev.equals(revision)) {
                return rev;
            }
        }
        return null;
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");
        Revision revisionExistente = getRevision(revision);
        if (revisionExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        revisionExistente.anadirPrecioMaterial(precioMaterial);
        return revisionExistente;
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");
        Revision revisionExistente = getRevision(revision);
        if (revisionExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        revisionExistente.cerrar(fechaFin);
        return revisionExistente;
    }

    public Revision buscar(Revision revision) {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        return getRevision(revision);
    }

    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede borrar una revisión nula.");
        Revision revisionExistente = getRevision(revision);
        if (revisionExistente == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        revisiones.remove(revisionExistente);
    }
}
