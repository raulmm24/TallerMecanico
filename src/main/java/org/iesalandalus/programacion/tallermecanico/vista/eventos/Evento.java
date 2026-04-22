package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {

    INSERTAR_CLIENTE(11,"Insertar cliente"),
    BUSCAR_CLIENTE(12,"Buscar cliente"),
    BORRAR_CLIENTE(13,"Borrar cliente"),
    LISTAR_CLIENTE(14,"Listar clientes"),
    MODIFICAR_CLIENTE(15,"Modificar cliente"),
    INSERTAR_VEHICULO(21,"Insertar vehiculo"),
    BUSCAR_VEHICULO(22,"Buscar vehiculo"),
    BORRAR_VEHICULO(23,"Borrar vehiculo"),
    LISTAR_VEHICULOS(24,"Listar vehículos"),
    INSERTAR_REVISION(31,"Insertar revisión"),
    INSERTAR_MECANICO(32,"Insertar mecanico"),
    BUSCAR_TRABAJO(41,"Buscar trabajos"),
    BORRAR_TRABAJO(42,"Borrar trabajos"),
    LISTAR_TRABAJOS(43,"Listar trabajos"),
    LISTAR_TRABAJOS_CLIENTE(44,"Listar trabajos de clientes"),
    LISTAR_TRABAJOS_VEHICULO(45,"Listar trabajos de vehículos"),
    ANADIR_HORAS_TRABAJO(51,"Añadir horas de trabajo"),
    ANADIR_PRECIO_MATERIAL_TRABAJO(52,"Añadir el precio de material del trabajo"),
    CERRAR_TRABAJO(60,"Cerrar trabajo"),
    SALIR(0,"Salir");

    private final int codigo;
    private final String texto;
    private static final Map<Integer, Evento> eventos = new HashMap<>();

    static {
        for (Evento evento : values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    private Evento(int codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public static boolean esValido(int codigo) {
        return eventos.containsKey(codigo);
    }

    public static Evento get(int codigo) {
        if (!esValido(codigo)) {
            throw new IllegalArgumentException("el codigo del evento no es valido.");
        }
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return String.format("%d. %s", codigo, texto);
    }
}
