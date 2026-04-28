package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public abstract class Trabajo {
    public static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final float FACTOR_DIA = 10f;

    protected Cliente cliente;
    protected Vehiculo vehiculo;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private int horas;

   public Trabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) {
        setCliente(cliente);
        setVehiculo(vehiculo);
        setFechaInicio(fechaInicio);
        this.horas = 0;
        this.fechaFin = null;
    }

    public Trabajo(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        this.cliente = new Cliente(trabajo.cliente);
        this.vehiculo = trabajo.vehiculo;
        this.fechaInicio = trabajo.fechaInicio;
        this.fechaFin = trabajo.fechaFin;
        this.horas = trabajo.horas;
    }

    public static Trabajo copiar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "El trabajo no puede ser nulo.");
        if (trabajo instanceof Revision revision) {
            return new Revision(revision);
        } else {
            return new Mecanico((Mecanico) trabajo);
        }
    }

    public static Trabajo get(Vehiculo vehiculo) {
        return new Revision(Cliente.get("00000000A"), vehiculo, LocalDate.now());
    }

    public Cliente getCliente() { return cliente; }

    private void setCliente(Cliente cliente) {
        this.cliente = Objects.requireNonNull(cliente, "El cliente no puede ser nulo.");
    }

    public Vehiculo getVehiculo() { return vehiculo; }

    private void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = Objects.requireNonNull(vehiculo, "El vehículo no puede ser nulo.");
    }

    public LocalDate getFechaInicio() { return fechaInicio; }

    private void setFechaInicio(LocalDate fechaInicio) {
        Objects.requireNonNull(fechaInicio, "La fecha de inicio no puede ser nula.");
        if (fechaInicio.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser futura.");
        }
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() { return fechaFin; }

    public int getHoras() { return horas; }

    public void anadirHoras(int horas) throws TallerMecanicoExcepcion {
        if (estaCerrado()) throw new TallerMecanicoExcepcion("No se puede añadir horas, ya que el trabajo está cerrado.");
        if (horas <= 0) throw new IllegalArgumentException("Las horas a añadir deben ser mayores que cero.");
        this.horas += horas;
    }

    public boolean estaCerrado() { return fechaFin != null; }

    public void cerrar(LocalDate fechaFin) throws TallerMecanicoExcepcion {
        if (estaCerrado()) throw new TallerMecanicoExcepcion("El trabajo ya está cerrado.");
        Objects.requireNonNull(fechaFin, "La fecha de fin no puede ser nula.");
        if (fechaFin.isAfter(LocalDate.now())) throw new IllegalArgumentException("La fecha de fin no puede ser futura.");
        if (fechaFin.isBefore(fechaInicio)) throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio.");
        this.fechaFin = fechaFin;
    }

    public float getPrecio() { return getPrecioFijo() + getPrecioEspecifico(); }

    private float getPrecioFijo() { return getDias() * FACTOR_DIA; }

    private float getDias() { return estaCerrado() ? ChronoUnit.DAYS.between(fechaInicio, fechaFin) : 0; }

    public abstract float getPrecioEspecifico();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Trabajo trabajo)) return false;
        return  Objects.equals(vehiculo, trabajo.vehiculo) && Objects.equals(fechaInicio, trabajo.fechaInicio);
    }

    @Override
    public int hashCode() { return Objects.hash(vehiculo, fechaInicio); }

    @Override
    public String toString() {
        if (!estaCerrado()) {
            return String.format("%s - %s (%s - ): %d horas", cliente, vehiculo, getFechaInicio().format(FORMATO_FECHA), getHoras());
        } else {
            return String.format("%s - %s (%s - %s): %d horas", cliente, vehiculo, getFechaInicio().format(FORMATO_FECHA),
                    getFechaFin().format(FORMATO_FECHA),
                    getHoras());
        }
    }
}