package org.iesalandalus.programacion.tallermecanico.vista;

import org.iesalandalus.programacion.tallermecanico.controlador.Controlador;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Revision;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;

import java.time.LocalDate;

public class Vista {

    private Controlador controlador;

    public void setControlador(Controlador controlador) {
        if (controlador != null) {
            this.controlador = controlador;
        }
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
        Consola.mostrarCabecera("¡Hasta pronto!");
    }

    private void ejecutar(Opcion opcion) {
        switch (opcion) {
            case INSERTAR_CLIENTE:
                insertarCliente();
                break;
            case INSERTAR_VEHICULO:
                insertarVehiculo();
                break;
            case INSERTAR_REVISION:
                insertarRevision();
                break;
            case BUSCAR_CLIENTE:
                buscarCliente();
                break;
            case BUSCAR_VEHICULO:
                buscarVehiculo();
                break;
            case BUSCAR_REVISION:
                buscarRevision();
                break;
            case MODIFICAR_CLIENTE:
                modificarCliente();
                break;
            case ANADIR_HORAS_REVISION:
                anadirHoras();
                break;
            case ANADIR_PRECIO_MATERIAL_REVISION:
                anadirPrecioMaterial();
                break;
            case CERRAR_REVISION:
                cerrarRevision();
                break;
            case BORRAR_CLIENTE:
                borrarCliente();
                break;
            case BORRAR_VEHICULO:
                borrarVehiculo();
                break;
            case BORRAR_REVISION:
                borrarRevision();
                break;
            case LISTAR_CLIENTES:
                listarClientes();
                break;
            case LISTAR_VEHICULOS:
                listarVehiculos();
                break;
            case LISTAR_REVISIONES:
                listarRevisiones();
                break;
            case LISTAR_REVISIONES_CLIENTE:
                listarRevisionesCliente();
                break;
            case LISTAR_REVISIONES_VEHICULO:
                listarRevisionesVehiculo();
                break;
            case SALIR:
                terminar();

        }
    }

    private void insertarCliente() {
        Consola.mostrarCabecera("Insertar Cliente");
        try {
            Cliente cliente = Consola.leerCliente();
            controlador.insertar(cliente);
            System.out.println("Cliente insertado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertarVehiculo() {
        Consola.mostrarCabecera("Insertar Vehículo");
        try {
            Vehiculo vehiculo = Consola.leerVehiculo();
            controlador.insertar(vehiculo);
            System.out.println("Vehículo insertado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertarRevision() {
        Consola.mostrarCabecera("Insertar Revisión");
        try {
            Revision revision = Consola.leerRevision();
            controlador.insertar(revision);
            System.out.println("Revisión insertada correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarCliente() {
        Consola.mostrarCabecera("Buscar Cliente");
        try {
            Cliente cliente = Consola.leerClienteDni();
            cliente = controlador.buscar(cliente);
            System.out.println(cliente != null ? cliente : "Cliente no encontrado.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarVehiculo() {
        Consola.mostrarCabecera("Buscar Vehículo");
        try {
            Vehiculo vehiculo = Consola.leerVehiculoMatricula();
            vehiculo = controlador.buscar(vehiculo);
            System.out.println(vehiculo != null ? vehiculo : "Vehículo no encontrado.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void buscarRevision() {
        Consola.mostrarCabecera("Buscar Revisión");
        try {
            Revision revision = Consola.leerRevision();
            revision = controlador.buscar(revision);
            System.out.println(revision != null ? revision : "Revisión no encontrada.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void modificarCliente() {
        Consola.mostrarCabecera("Modificar Cliente");
        try {
            Cliente cliente = Consola.leerClienteDni();
            String nuevoNombre = Consola.leerNuevoNombre();
            String nuevoTelefono = Consola.leerNuevoTelefono();
            controlador.modificar(cliente, nuevoNombre, nuevoTelefono);
            System.out.println("Cliente modificado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void anadirHoras() {
        Consola.mostrarCabecera("Añadir Horas");
        try {
            Revision revision = Consola.leerRevision();
            int horas = Consola.leerHoras();
            controlador.anadirHoras(revision, horas);
            System.out.println("Horas añadidas correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void anadirPrecioMaterial() {
        Consola.mostrarCabecera("Añadir Precio Material");
        try {
            Revision revision = Consola.leerRevision();
            float precioMaterial = Consola.leerPrecioMaterial();
            controlador.anadirPrecioMaterial(revision, precioMaterial);
            System.out.println("Precio del material añadido correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void cerrarRevision() {
        Consola.mostrarCabecera("Cerrar Revisión");
        try {
            Revision revision = Consola.leerRevision();
            LocalDate fechaCierre = Consola.leerFechaCierre();
            controlador.cerrar(revision, fechaCierre);
            System.out.println("Revisión cerrada correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void borrarCliente() {
        Consola.mostrarCabecera("Borrar Cliente");
        try {
            Cliente cliente = Consola.leerClienteDni();
            controlador.borrar(cliente);
            System.out.println("Cliente borrado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void borrarVehiculo() {
        Consola.mostrarCabecera("Borrar Vehículo");
        try {
            Vehiculo vehiculo = Consola.leerVehiculoMatricula();
            controlador.borrar(vehiculo);
            System.out.println("Vehículo borrado correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void borrarRevision() {
        Consola.mostrarCabecera("Borrar Revisión");
        try {
            Revision revision = Consola.leerRevision();
            controlador.borrar(revision);
            System.out.println("Revisión borrada correctamente.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarClientes() {
        Consola.mostrarCabecera("Listar Clientes");
        try {
            for (Cliente cliente : controlador.getClientes()) {
                System.out.println(cliente);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarVehiculos() {
        Consola.mostrarCabecera("Listar Vehículos");
        try {
            for (Vehiculo vehiculo : controlador.getVehiculos()) {
                System.out.println(vehiculo);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarRevisiones() {
        Consola.mostrarCabecera("Listar Revisiones");
        try {
            for (Revision revision : controlador.getRevisiones()) {
                System.out.println(revision);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarRevisionesCliente() {
        Consola.mostrarCabecera("Listar Revisiones por Cliente");
        try {
            Cliente cliente = Consola.leerClienteDni();
            for (Revision revision : controlador.getRevisiones(cliente)) {
                System.out.println(revision);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarRevisionesVehiculo() {
        Consola.mostrarCabecera("Listar Revisiones por Vehículo");
        try {
            Vehiculo vehiculo = Consola.leerVehiculoMatricula();
            for (Revision revision : controlador.getRevisiones(vehiculo)) {
                System.out.println(revision);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
