package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.time.LocalDate;

public class Mecanico extends Trabajo {

    static final float FACTOR_HORA = 10.0F;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5F;
    private float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaIncio) {
       super(cliente,vehiculo,fechaIncio);
    }

    public Mecanico(Mecanico mecanico) {
        super(mecanico);
        this.precioMaterial = mecanico.precioMaterial;
    }

    public float getPrecioMaterial() {
        return precioMaterial;
    }

    public void anadirPrecioMaterial(float precioMaterial) {
        if (precioMaterial <= 0) {
            throw new IllegalArgumentException("El precio del material debe ser mayor que cero");
        }
        this.precioMaterial += precioMaterial;
    }

    @Override
    public float getPrecioEspecifico() {
        return precioMaterial * FACTOR_PRECIO_MATERIAL + getHoras() * FACTOR_HORA;
    }

    @Override
    public String toString() {
        String cadenaFechaFin = getFechaFin() == null ? "" : getFechaFin().format(FORMATO_FECHA);
        String cadenaPrecio = estaCerrado() ? String.format(", %.2f € total", getPrecioEspecifico()) : "";
        return String.format("%s - %s: (%s - %s), %d horas, %.2f € en material%s", getCliente(), getVehiculo(), getFechaInicio().format(FORMATO_FECHA), cadenaFechaFin, getHoras(), getPrecioEspecifico(), cadenaPrecio);
    }
}