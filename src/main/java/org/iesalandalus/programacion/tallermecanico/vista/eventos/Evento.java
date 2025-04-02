package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.HashMap;
import java.util.Map;

public enum Evento {

    INSERTAR_CLIENTE("Insertar Cliente",1),
    BUSCAR_CLIENTE("Buscar Cliente",2),
    BORRAR_CLIENTE("Borrar Cliente",3),
    LISTAR_CLIENTES("Listar Cliente",4),
    MODIFICAR_CLIENTE("Modificar Cliente",5),
    INSERTAR_VEHICULO("Insertar Vehículo",6),
    BUSCAR_VEHICULO("Buscar Vehículo",7),
    BORRAR_VEHICULO("Borrar Vehículo",8),
    LISTAR_VEHICULOS("Listar Vehículos",9),
    INSERTAR_REVISION("Insertar Revisión",10),
    INSERTAR_MECANICO("Insertar Mecanico",11),
    BUSCAR_TRABAJO("Buscar Trabajo",12),
    BORRAR_TRABAJO("Borrar Trabajo",13),
    LISTAR_TRABAJOS("Listar Trabajos",14),
    LISTAR_TRABAJOS_CLIENTE("Listar Trabajos Cliente",15),
    LISTAR_TRABAJOS_VEHICULO("Listar Trabajos Vehículo",16),
    ANADIR_HORAS_TRABAJO("Anadir Horas Trabajo",17),
    ANADIR_PRECIO_MATERIAL_TRABAJO("Anadir Precio Material Trabajo",18),
    CERRAR_TRABAJO("Cerrar",19),
    SALIR("Salir",20);

    private final int codigo;
    private final String texto;
    static final Map<Integer, Evento> eventos = new HashMap<>();

    static {
        for (Evento evento : values()) {
            eventos.put(evento.codigo, evento);
        }
    }

    Evento(String texto, int codigo) {
        this.texto = texto;
        this.codigo = codigo;
    }

    public int getCodigo() {return codigo;}

    public static boolean esValido(int codigo) {
        return eventos.containsKey(codigo);
    }
    public static Evento get(int codigo) {
        if (!esValido(codigo)) {
            throw new IllegalArgumentException("El codigo no es correcto.");
        }
        return eventos.get(codigo);
    }

    @Override
    public String toString() {
        return texto ;
    }
}
