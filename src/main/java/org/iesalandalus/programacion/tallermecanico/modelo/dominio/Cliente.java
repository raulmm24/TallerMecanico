package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;

public class Cliente {

    private static final String ER_NOMBRE = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$";
    private static final String ER_DNI = "([0-9]{8})([A-Za-z])";
    private static final String ER_TELEFONO = "([0-9]{9})";

    private String dni;
    private String nombre;
    private String telefono;

    public Cliente(String nombre, String dni, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "No es posible copiar un cliente nulo.");
        setNombre(cliente.getNombre());
        setDni(cliente.getDni());
        setTelefono(cliente.getTelefono());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        Objects.requireNonNull(nombre, "El nombre no puede ser nulo.");
        if (!nombre.matches(ER_NOMBRE)) {
            throw new IllegalArgumentException("El nombre no tiene un formato válido.");
        }
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    private void setDni(String dni) {
        Objects.requireNonNull(dni, "El DNI no puede ser nulo.");
        if (!dni.matches(ER_DNI)) {
            throw new IllegalArgumentException("El DNI no tiene un formato válido.");
        }
        if (!ComprobarLetraDni(dni)) {
            throw new IllegalArgumentException("La letra del DNI no es correcta.");
        }
        this.dni = dni;
    }

    private boolean ComprobarLetraDni(String dni) {
        String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
        int numero = Integer.parseInt(dni.substring(0, 8));
        return dni.charAt(8) == letras.charAt(numero % 23);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        Objects.requireNonNull(telefono, "El teléfono no puede ser nulo.");
        if (!telefono.matches(ER_TELEFONO)) {
            throw new IllegalArgumentException("El teléfono no tiene un formato válido.");
        }
        this.telefono = telefono;
    }

    public static Cliente get(String dni) {
        return new Cliente("Raul", dni, "777777777");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(getDni(), cliente.getDni());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDni());
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", nombre, dni, telefono);
    }
}
