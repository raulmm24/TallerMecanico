package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {

    INSERTAR_CLIENTE(11, "Insertar cliente"),
    BUSCAR_CLIENTE(12, "Buscar cliente"),
    BORRAR_CLIENTE(13, "Borrar cliente"),
    LISTAR_CLIENTES(14, "Listar clientes"),
    MODIFICAR_CLIENTE(15, "Modificar cliente"),
    INSERTAR_VEHICULO(21, "Insertar vehículo"),
    BUSCAR_VEHICULO(22, "Buscar vehículo"),
    BORRAR_VEHICULO(23, "Borrar vehículo"),
    LISTAR_VEHICULOS(24, "Listar vehículos"),
    INSERTAR_REVISION(31, "Insertar revisión"),
    INSERTAR_MECANICO(32, "Insertar mecánico"),
    BUSCAR_TRABAJO(41, "Buscar trabajo"),
    BORRAR_TRABAJO(42, "Borrar trabajo"),
    LISTAR_TRABAJOS(43, "Listar trabajos"),
    LISTAR_TRABAJOS_CLIENTE(44, "Listar trabajos de cliente"),
    LISTAR_TRABAJOS_VEHICULO(45, "Listar trabajos de vehículo"),
    ANADIR_HORAS_TRABAJO(51, "Añadir horas de trabajo"),
    ANADIR_PRECIO_MATERIAL_TRABAJO(52, "Añadir precio material del trabajo"),
    CERRAR_TRABAJO(60, "Cerrar trabajo"),
    MOSTRAR_ESTADISTICAS_MENSUALES(70,"Mostrar estadisticas mensuales"),
    SALIR(0, "Salir");

    private final int codigo;
    private final String texto;
    private static final Map<Integer, Evento> eventos = new HashMap<>();

    static {
        for (Evento evento : values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    Evento(int codigo, String texto) {
        this.codigo = codigo;
        this.texto = texto;
    }

    public static boolean esValido(int codigo) {
        return eventos.containsKey(codigo);
    }

    public static Evento get(int codigo) {
        if (!esValido(codigo)) {
            throw new IllegalArgumentException("El código del evento no es válido.");
        }
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return String.format("%d. %s", codigo, texto);
    }
}