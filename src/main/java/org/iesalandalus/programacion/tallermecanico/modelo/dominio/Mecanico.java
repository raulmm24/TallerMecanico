package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import java.time.LocalDate;

public class Mecanico extends Trabajo {
    private static final float FACTOR_HORA = 30f;
    private static final float FACTOR_PRECIO_MATERIAL = 1.5f;
    private float precioMaterial;

    public Mecanico(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        super(cliente, vehiculo, fechaInicio);
        this.precioMaterial = 0;
    }

    public Mecanico(Mecanico mecanico) {
        super(mecanico);
        this.precioMaterial = mecanico.precioMaterial;
    }

    public float getPrecioMaterial() { return precioMaterial; }

    public void anadirPrecioMaterial(float precioMaterial) throws TallerMecanicoExcepcion {
        if (estaCerrado()) throw new TallerMecanicoExcepcion("No se puede añadir precio del material, ya que el trabajo mecánico está cerrado.");
        if (precioMaterial <= 0) throw new IllegalArgumentException("El precio del material a añadir debe ser mayor que cero.");
        this.precioMaterial += precioMaterial;
    }

    @Override
    public float getPrecioEspecifico() {
        return (getHoras() * FACTOR_HORA) + (precioMaterial * FACTOR_PRECIO_MATERIAL);
    }

    @Override
    public String toString() {
        String cadena = String.format("Mecánico -> %s, %.2f € en material", super.toString(), precioMaterial);

        if (estaCerrado()) {
            cadena = String.format("%s, %.2f € total", cadena, getPrecio());
        }

        return cadena;
    }
}