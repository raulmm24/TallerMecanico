package org.iesalandalus.programacion.tallermecanico.modelo;

import org.iesalandalus.programacion.tallermecanico.modelo.cascada.ModeloCascada;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.*;

public enum FabricaModelo {

    CASCADA;

    public Modelo crear(FabricaFuenteDatos fabricaFuenteDatos) {
        return new ModeloCascada(fabricaFuenteDatos);
    }
}
