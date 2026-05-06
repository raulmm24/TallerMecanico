package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public enum TipoTrabajo {
    MECANICO("Mecanico"),
    REVISION("Revisión");

    private final String nombre;

    private TipoTrabajo(String nombre) {
        this.nombre = nombre;
    }

    public static TipoTrabajo get(Trabajo trabajo) {
        Objects.requireNonNull(trabajo,"El trabajo no puede ser nulo.");

        if (trabajo instanceof Mecanico) {
            return MECANICO;
        } else if (trabajo instanceof Revision) {
            return REVISION;
        } else {
            throw new IllegalArgumentException("Tipo de Trabajo no válido.");
        }

    }



}
