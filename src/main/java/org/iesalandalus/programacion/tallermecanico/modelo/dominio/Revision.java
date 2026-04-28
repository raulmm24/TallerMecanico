package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Revision extends Trabajo {
    private static final float FACTOR_HORA = 35f;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
    }

    public Revision(Revision revision) {
        super(revision);
    }

    @Override
    public float getPrecioEspecifico() {
        return getHoras() * FACTOR_HORA;
    }

    @Override
    public String toString() {
        String cadena = String.format("Revisión -> %s", super.toString());
        if (estaCerrado()) {
            cadena = String.format("%s, %.2f € total", cadena, getPrecio());
        }
        return cadena;
    }
}