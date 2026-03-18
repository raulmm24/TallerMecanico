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

    private final List<Revision> coleccionRevisiones;

    public Revisiones() {
        coleccionRevisiones = new ArrayList<>();
    }

    public List<Revision> get() {
        return new ArrayList<>(coleccionRevisiones);
    }

    public List<Revision> get(Cliente cliente) {
        List<Revision> revisionesCliente = new ArrayList<>();
        for (Revision revision : coleccionRevisiones) {
            if (revision.getCliente().equals(cliente)) {
                revisionesCliente.add(revision);
            }
        }
        return revisionesCliente;
    }

    public List<Revision> get(Vehiculo vehiculo) {
        List<Revision> revisionesVehiculo = new ArrayList<>();
        for (Revision revision : coleccionRevisiones) {
            if (revision.getVehiculo().equals(vehiculo)) {
                revisionesVehiculo.add(revision);
            }
        }
        return revisionesVehiculo;
    }

    public void insertar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede insertar una revisión nula.");

        comprobarRevision(revision.getCliente(), revision.getVehiculo(), revision.getFechaInicio());
        coleccionRevisiones.add(revision);
    }

    private void comprobarRevision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaRevision) throws TallerMecanicoExcepcion {
        for (Revision r : coleccionRevisiones) {
            if (r.getCliente().equals(cliente)) {
                if (!r.estaCerrada()) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otra revisión en curso.");
                } else if (!r.getFechaFin().isBefore(fechaRevision)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene una revisión posterior.");
                }
            }

            if (r.getVehiculo().equals(vehiculo)) {
                if (!r.estaCerrada()) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en revisión.");
                } else if (!r.getFechaFin().isBefore(fechaRevision)) {
                    throw new TallerMecanicoExcepcion("El vehículo tiene una revisión posterior.");
                }
            }
        }
    }

    public Revision anadirHoras(Revision revision, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");
        Revision encontrada = buscar(revision);
        if (encontrada == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        encontrada.anadirHoras(horas);

        return encontrada;
    }

    private Revision getRevision(Revision revision) {
        return revision;
    }

    public Revision anadirPrecioMaterial(Revision revision, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision,"No puedo operar sobre una revisión nula.");

        Revision encontrada = buscar(revision);

        if (encontrada == null) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        encontrada.anadirPrecioMaterial(precioMaterial);
        return encontrada;
    }

    public Revision cerrar(Revision revision, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No puedo operar sobre una revisión nula.");

        int indice = coleccionRevisiones.indexOf(revision);

        if (indice == -1) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }

        Revision revisionEncontrada = coleccionRevisiones.get(indice);
        revisionEncontrada.cerrar(fechaFin);

        return revisionEncontrada;
    }

    public Revision buscar(Revision revision)  {
        Objects.requireNonNull(revision, "No se puede buscar una revisión nula.");
        int revisionEncontada = coleccionRevisiones.indexOf(revision);
        return (revisionEncontada == -1) ? null : coleccionRevisiones.get(revisionEncontada);
    }

    public void borrar(Revision revision) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(revision, "No se puede borrar una revisión nula.");
        if (!coleccionRevisiones.contains(revision)) {
            throw new TallerMecanicoExcepcion("No existe ninguna revisión igual.");
        }
        coleccionRevisiones.remove(revision);
    }
}
