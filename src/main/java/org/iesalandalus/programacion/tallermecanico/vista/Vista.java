package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.util.List;

public class Vista {

    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        if (controlador == null) {
            throw new NullPointerException("ERROR: El controlador no puede ser nulo.");
        }
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while (opcion != Opcion.SALIR);
    }

    public void terminar() {
        controlador.terminar();
    }

    private void ejecutar(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_CLIENTE -> insertarCliente();
            case BUSCAR_CLIENTE -> buscarCliente();
            case BORRAR_CLIENTE -> borrarCliente();
            case LISTAR_CLIENTES -> listarClientes();
            case MODIFICAR_CLIENTE -> modificarCliente();
            case INSERTAR_VEHICULO -> insertarVehiculo();
            case BUSCAR_VEHICULO -> buscarVehiculo();
            case BORRAR_VEHICULO -> borrarVehiculo();
            case LISTAR_VEHICULOS -> listarVehiculos();
            case INSERTAR_REVISION -> insertarRevision();
            case BUSCAR_REVISION -> buscarRevision();
            case BORRAR_REVISION -> borrarRevision();
            case LISTAR_REVISIONES -> listarRevisiones();
            case LISTAR_REVISIONES_CLIENTE -> listarRevisionesCliente();
            case LISTAR_REVISIONES_VEHICULO -> listarRevisionesVehiculo();
            case ANADIR_HORAS_REVISION -> anadirHoras();
            case ANADIR_PRECIO_MATERIAL_REVISION -> anadirPrecioMaterial();
            case CERRAR_REVISION -> cerrarRevision();
            case SALIR -> salir();
        }
    }

    /* METODOS INSERTAR */

    private void insertarCliente() {
        Consola.mostrarCabecera("Insertar Cliente");
        try {
            controlador.insertar(Consola.leerCliente());
            System.out.println("Cliente insertado correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void insertarVehiculo() {
        Consola.mostrarCabecera("Insertar Vehículo");
        try {
            controlador.insertar(Consola.leerVehiculo());
            System.out.println("Vehiculo insertado correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void insertarRevision() {
        Consola.mostrarCabecera("Insertar Revisión");
        try {
            controlador.insertar(Consola.leerRevision());
            System.out.println("Revisión insertada correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /* METODOS BUSCAR*/

    private void buscarCliente() {
        Consola.mostrarCabecera("Buscar cliente");
        try {
            Cliente cliente = controlador.buscar(Consola.leerClienteDni());
            System.out.println(cliente != null ? cliente : "No existe ningún cliente con ese Dni");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void buscarVehiculo() {
        Consola.mostrarCabecera("Buscar vehículo");
        try {
            Vehiculo vehiculo = controlador.buscar(Consola.leerVehiculoMatricula());
            System.out.println(vehiculo != null ? vehiculo : "No existe ningún vehículo con esa matricula");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void buscarRevision() {
        Consola.mostrarCabecera("Buscar revisión");
        try {
            Revision revision = controlador.buscar(Consola.leerRevision());
            System.out.println(revision != null ? revision : "No existe la revisión indicada");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

    }

    private void modificarCliente() {
        Consola.mostrarCabecera("Modificar Cliente");
        try {
            controlador.modificar(Consola.leerClienteDni(), Consola.leerNuevoNombre(),Consola.leerNuevoTelefono());
            System.out.println("Cliente modificado correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void anadirHoras() {
        Consola.mostrarCabecera("Añadir Horas");
        try {
            controlador.anadirHoras(Consola.leerRevision(), Consola.leerHoras());
            System.out.println("Las horas se han añadido correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void anadirPrecioMaterial() {
        Consola.mostrarCabecera("Añadir Precio Material");
        try {
            controlador.anadirPrecioMaterial(Consola.leerRevision(),Consola.leerPrecioMaterial());
            System.out.println("El precio del material ha sido añadido correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void cerrarRevision() {
        Consola.mostrarCabecera("Cerrar Revisión");
        try {
            controlador.cerrar(Consola.leerRevision(),Consola.leerFechaCierre());
            System.out.println("Revisión cerrada correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /* METODOS BORRAR */

    private void borrarCliente() {
        Consola.mostrarCabecera("Borrar cliente");
        try {
            controlador.borrar(Consola.leerClienteDni());
            System.out.println("Cliente borrado correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void borrarVehiculo() {
        Consola.mostrarCabecera("Borrar Vehículo");
        try {
            controlador.borrar(Consola.leerVehiculoMatricula());
            System.out.println("Vehículo borrado correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void borrarRevision() {
        Consola.mostrarCabecera("Borrar Revisión");
        try {
            controlador.borrar(Consola.leerRevision());
            System.out.println("Revisión borrada correctamente");
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    /* METODOS LISTAR*/

    private void listarClientes() {
        Consola.mostrarCabecera("Listar Clientes");
        List<Cliente> clientes = controlador.getClientes();
        if (!clientes.isEmpty()) {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        } else {
            System.out.println("No hay clientes registrados");
        }
    }

    private void listarVehiculos() {
        Consola.mostrarCabecera("Listar Vehículos");
        List<Vehiculo> vehiculos = controlador.getVehiculos();
        if (!vehiculos.isEmpty()) {
            for (Vehiculo vehiculo : vehiculos) {
                System.out.println(vehiculo);
            }
        } else {
            System.out.println("No hay vehículos registrados");
        }
    }

    private void listarRevisiones() {
        Consola.mostrarCabecera("Listar Revisiones");
        for (Revision revision : controlador.getRevisiones()) {
            System.out.println(revision);
        }
    }

    private void listarRevisionesCliente() {
        Consola.mostrarCabecera("Listar Revisiones de un Cliente");
        try {
            for (Revision revision : controlador.getRevisiones(Consola.leerClienteDni())) {
                System.out.println(revision);
            }
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void listarRevisionesVehiculo() {
        Consola.mostrarCabecera("Listar Revisiones de un Vehículo");
        try {
            for (Revision revision : controlador.getRevisiones(Consola.leerVehiculoMatricula())) {
                System.out.println(revision);
            }
        } catch (TallerMecanicoExcepcion | NullPointerException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void salir() {
        System.out.println("Cerrando la aplicación... ¡Hasta pronto!");
        terminar();
    }
}
