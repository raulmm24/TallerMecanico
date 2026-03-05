package org.iesalandalus.programacion.tallermecanico.modelo.dominio;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cliente {

    private static final String ER_NOMBRE = "^[A-ZÁÉÍÓÚÑ][a-záéíóúñ]+( [A-ZÁÉÍÓÚÑ][a-záéíóúñ]+)*$";
    private static final String ER_DNI = "^([0-9]{8})([A-Za-z])$";
    private static final String ER_TELEFONO = "^[0-9]{9}$";

    private String nombre;
    private String dni;
    private String telefono;

    public Cliente(String nombre, String dni, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setTelefono(telefono);
    }

    public Cliente(Cliente cliente) {
        Objects.requireNonNull(cliente, "No es posible copiar un cliente nulo.");
        this.nombre = cliente.nombre;
        this.dni = cliente.dni;
        this.telefono = cliente.telefono;
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

        if (!comprobarLetraDni(dni)) {
            throw new IllegalArgumentException("La letra del DNI no es correcta.");
        }

        this.dni = dni;
    }

    /* METODO COMPROBAR LETRA DNI REPASAR  */

    private boolean comprobarLetraDni(String dni) {
        Pattern pattern = Pattern.compile(ER_DNI);
        Matcher matcher = pattern.matcher(dni);

        if (matcher.find()) {
            int numero = Integer.parseInt(matcher.group(1));
            char letraProporcionada = matcher.group(2).toUpperCase().charAt(0);
            String letrasValidas = "TRWAGMYFPDXBNJZSQVHLCKE";
            char letraCorrecta = letrasValidas.charAt(numero % 23);

            return letraProporcionada == letraCorrecta;
        }
        return false;
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
        return new Cliente("Nombre Valido", dni, "950111111");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cliente cliente)) return false;
        return Objects.equals(dni, cliente.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s)", nombre, dni, telefono);
    }
}