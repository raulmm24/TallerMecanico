package org.iesalandalus.programacion.tallermecanico;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.controlador.IControlador;
import org.iesalandalus.programacion.tallermecanico.modelo.FabricaModelo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.FabricaFuenteDatos;
import org.iesalandalus.programacion.tallermecanico.vista.FabricaVista;

public class Main {
    public static void main(String[] args) {
        try {

            IControlador controlador = new Controlador(FabricaModelo.CASCADA, FabricaFuenteDatos.FICHEROS, FabricaVista.TEXTO);

            controlador.comenzar();

        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
        }
    }
}