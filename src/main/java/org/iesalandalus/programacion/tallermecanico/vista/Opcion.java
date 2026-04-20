package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.HashMap;
import java.util.Map;

public enum Opcion {

    INSERTAR_CLIENTE(11,"Insertar cliente"),
    BUSCAR_CLIENTE(12,"Buscar cliente"),
    MODIFICAR_CLIENTE(13,"Modificar cliente"),
    LISTAR_CLIENTES(14,"Listar clientes"),
    BORRAR_CLIENTE(15,"Borrar cliente"),
    INSERTAR_VEHICULO(21,"Insertar vehículo"),
    BUSCAR_VEHICULO(22,"Buscar vehículo"),
    LISTAR_VEHICULOS(23,"Listar vehículos"),
    BORRAR_VEHICULO(24,"Borrar vehículo"),
    INSERTAR_REVISION(31,"Insertar revisión"),
    BUSCAR_REVISION(32,"Buscar revisión"),
    LISTAR_REVISIONES(33,"Listar revisiones"),
    BORRAR_REVISION(34,"Borrar revisión"),
    LISTAR_REVISIONES_CLIENTE(35,"Listar revisiones de un cliente"),
    LISTAR_REVISIONES_VEHICULO(36,"Listar revisiones de un vehículo"),
    ANADIR_HORAS_REVISION(41,"Añadir horas a una revisión"),
    ANADIR_PRECIO_MATERIAL_REVISION(42,"Añadir precio de material a una revisión"),
    CERRAR_REVISION(50,"Cerrar revisión"),
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
            throw new IllegalArgumentException("el número de opción no es correcto.");
        }
        return opciones.get(numeroOpcion);
    }

    @Override
    public String toString() {
        return String.format("%d. %s", numeroOpcion, mensaje);
    }
}
