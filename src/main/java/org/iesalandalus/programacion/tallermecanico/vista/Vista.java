package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.util.List;
import java.util.Objects;

public class Vista {

    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        Objects.requireNonNull(controlador,"El controlador no puede ser nulo.");
        this.controlador = controlador;
    }

    public void comenzar() {
        Opcion opcion;
        do {
            Consola.mostrarMenu();
            opcion = Consola.elegirOpcion();
            ejecutar(opcion);
        } while (opcion != Opcion.SALIR);
        controlador.terminar();
    }

    public void terminar() {
        controlador.terminar();
    }

    private void ejecutar(Opcion opcion) {
        try {
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
        } catch (Exception e) {
            System.out.printf("ERROR: %s%n", e.getMessage());
        }
    }

    /* METODOS INSERTAR */

    private void insertarCliente() {
        Consola.mostrarCabecera("Insertar Cliente");
        controlador.insertar(Consola.leerCliente());
        System.out.println("Cliente insertado correctamente");
    }

    private void insertarVehiculo() {
        Consola.mostrarCabecera("Insertar Vehículo");
        controlador.insertar(Consola.leerVehiculo());
        System.out.println("Vehiculo insertado correctamente");
    }

    private void insertarRevision() {
        Consola.mostrarCabecera("Insertar Revisión");
        controlador.insertar(Consola.leerRevision());
        System.out.println("Revisión insertada correctamente");
    }

    /* METODOS BUSCAR*/

    private void buscarCliente() {
        Consola.mostrarCabecera("Buscar cliente");
        Cliente cliente = controlador.buscar(Consola.leerClienteDni());
        System.out.println(cliente != null ? cliente : "No existe ningún cliente con ese Dni");
    }

    private void buscarVehiculo() {
        Consola.mostrarCabecera("Buscar vehículo");
        Vehiculo vehiculo = controlador.buscar(Consola.leerVehiculoMatricula());
        System.out.println(vehiculo != null ? vehiculo : "No existe ningún vehículo con esa matricula");
    }

    private void buscarRevision() {
        Consola.mostrarCabecera("Buscar revisión");
        Revision revision = controlador.buscar(Consola.leerRevision());
        System.out.println(revision != null ? revision : "No existe la revisión indicada");
    }

    private void modificarCliente() {
        Consola.mostrarCabecera("Modificar Cliente");
        controlador.modificar(Consola.leerClienteDni(), Consola.leerNuevoNombre(),Consola.leerNuevoTelefono());
        System.out.println("Cliente modificado correctamente");
    }

    private void anadirHoras() {
        Consola.mostrarCabecera("Añadir Horas");
        controlador.anadirHoras(Consola.leerRevision(), Consola.leerHoras());
        System.out.println("Las horas se han añadido correctamente");
    }

    private void anadirPrecioMaterial() {
        Consola.mostrarCabecera("Añadir Precio Material");
        controlador.anadirPrecioMaterial(Consola.leerRevision(),Consola.leerPrecioMaterial());
        System.out.println("El precio del material ha sido añadido correctamente");
    }

    private void cerrarRevision() {
        Consola.mostrarCabecera("Cerrar Revisión");
        controlador.cerrar(Consola.leerRevision(),Consola.leerFechaCierre());
        System.out.println("Revisión cerrada correctamente");
    }

    /* METODOS BORRAR */

    private void borrarCliente() {
        Consola.mostrarCabecera("Borrar cliente");
        controlador.borrar(Consola.leerClienteDni());
        System.out.println("Cliente borrado correctamente");
    }

    private void borrarVehiculo() {
        Consola.mostrarCabecera("Borrar Vehículo");
        controlador.borrar(Consola.leerVehiculoMatricula());
        System.out.println("Vehículo borrado correctamente");
    }

    private void borrarRevision() {
        Consola.mostrarCabecera("Borrar Revisión");
        controlador.borrar(Consola.leerRevision());
        System.out.println("Revisión borrada correctamente");
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

    private void salir() {}
}
