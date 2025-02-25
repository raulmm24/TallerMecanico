package org.iesalandalus.programacion.tallermecanico.vista;

import java.util.HashMap;
import java.util.Map;

public enum Opcion {

    INSERTAR_CLIENTE(11,"Insertar cliente"),
    INSERTAR_VEHICULO(12,"Insertar vehiculo"),
    INSERTAR_REVISION(13,"Insertar revision"),
    BUSCAR_CLIENTE(21,"Buscar cliente"),
    BUSCAR_VEHICULO(22,"Buscar vehiculo"),
    BUSCAR_REVISION(23,"Buscar revision"),
    BORRAR_CLIENTE(31,"Borrar cliente"),
    BORRAR_VEHICULO(32,"Borrar vehiculo"),
    BORRAR_REVISION(33,"Borrar revision"),
    LISTAR_CLIENTES(41,"Listar clientes"),
    LISTAR_VEHICULOS(42,"Listar vehiculos"),
    LISTAR_REVISIONES(43,"Listar revisiones"),
    LISTAR_REVISIONES_CLIENTE(44,"Listar revisiones de clientes"),
    LISTAR_REVISIONES_VEHICULO(45,"Listar revisiones de vehiculos"),
    ANADIR_HORAS_REVISION(51,"Añadir horas de revision"),
    ANADIR_PRECIO_MATERIAL_REVISION(52,"Añadir precio de material de revision"),
    MODIFICAR_CLIENTE(60,"Modificar cliente"),
    CERRAR_REVISION(70,"Cerrar revision"),
    SALIR(80,"Salir");

    private final int numeroOpcion;
    private final String mensaje;
    private static final Map<Integer,Opcion> opciones = new HashMap<>();

    static {
        for (Opcion opcion : Opcion.values()) {
            opciones.put(opcion.numeroOpcion,opcion);
        }
    }

    Opcion(int numeroOpcion, String mensaje) {
        this.numeroOpcion = numeroOpcion;
        this.mensaje = mensaje;
    }

    public static boolean esValida(int numeroOpcion) {
        return opciones.containsKey(numeroOpcion);
    }

    public static Opcion get(int numeroOpcion) {
        Opcion opcion = opciones.get(numeroOpcion);
        if (opcion == null) {
            throw new IllegalArgumentException("Numero de opcion no valido" + numeroOpcion);
        }
        return opcion;
    }

    @Override
    public String toString() {
        return numeroOpcion + ". " + mensaje;
    }
}
