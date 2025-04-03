package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

import static org.iesalandalus.programacion.tallermecanico.modelo.dominio.Mecanico.FACTOR_HORA;

public class Revision extends Trabajo {

    private static final float FECHA_HORA = 10.0F;

    public Revision(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente,vehiculo,fechaInicio);
    }

    public Revision(Revision revision) {
        super(revision);
    }

    @Override
    public float getPrecioEspecifico() {
        return (estaCerrado()) ? FACTOR_HORA * getHoras() : 0;
    }

    @Override
    public String toString() {
        String cadenaFechaFin = (getFechaFin() == null) ? "" : getFechaFin().format(FORMATO_FECHA);
        float precio = getPrecioEspecifico();
        String cadenaPrecio = estaCerrado() ? String.format(", %.2f € total", precio) : "";
        return String.format("%s - %s: (%s - %s), %d horas, %.2f € total%s",
                cliente, vehiculo, getFechaInicio().format(FORMATO_FECHA), cadenaFechaFin, getHoras(), precio, cadenaPrecio);
    }
}