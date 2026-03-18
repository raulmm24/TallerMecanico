package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.HashMap;
import java.util.Map;

public enum Opcion {

    INSERTAR_CLIENTE(1,"Insertar cliente"),
    INSERTAR_VEHICULO(2,"Insertar vehículo"),
    INSERTAR_REVISION(3,"Insertar revisión"),
    MODIFICAR_CLIENTE(4,"Modificar cliente"),
    BUSCAR_CLIENTE(5,"Buscar cliente"),
    BUSCAR_VEHICULO(6,"Buscar vehículo"),
    BUSCAR_REVISION(7,"Buscar revisión"),
    BORRAR_CLIENTE(8,"Borrar cliente"),
    BORRAR_VEHICULO(9,"Borrar vehículo"),
    BORRAR_REVISION(10,"Borrar revisión"),
    LISTAR_CLIENTES(11,"Listar clientes"),
    LISTAR_VEHICULOS(12,"Listar vehículos"),
    LISTAR_REVISIONES(13,"Listar revisiones"),
    LISTAR_REVISIONES_CLIENTE(14,"Listar revisiones de un cliente"),
    LISTAR_REVISIONES_VEHICULO(15,"Listar revisiones de un vehículo"),
    ANADIR_HORAS_REVISION(16,"Añadir horas a una revisión"),
    ANADIR_PRECIO_MATERIAL_REVISION(17,"Añadir precio de material a una revisión"),
    CERRAR_REVISION(18,"Cerrar revisión"),
    SALIR(0,"Salir");

    private final int numeroOpcion;
    private final String mensaje;
    private static final Map<Integer,Opcion> opciones = new HashMap<>();

    static {
        for (Opcion opcion : values()) {
            opciones.put(opcion.numeroOpcion, opcion);
        }
    }

    private Opcion(int numeroOpcion,String mensaje) {
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }

    public static boolean esValida(int numeroOpcion) {
        return opciones.containsKey(numeroOpcion);
    }

    public static Opcion get(int numeroOpcion) {
        if (!esValida(numeroOpcion)) {
            throw new IllegalArgumentException("ERROR: el número de opción no es correcto.");
        }
        return opciones.get(numeroOpcion);
    }

    @Override
    public String toString() {
        return String.format("%d. %s", numeroOpcion, mensaje);
    }
}
