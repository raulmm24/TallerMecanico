package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {

    private static final String FICHERO_TRABAJOS = "datos/trabajos.xml";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RAIZ = "trabajos";
    private static final String TRABAJO = "trabajo";
    private static final String CLIENTE = "cliente";
    private static final String VEHICULO = "vehículo";
    private static final String FECHA_INICIO = "fechaInicio";
    private static final String FECHA_FIN = "fechaFin";
    private static final String HORAS = "horas";
    private static final String PRECIO_MATERIAL = "precioMaterial";
    private static final String TIPO = "tipo";
    private static final String MECANICO = "mecanico";
    private static final String REVISION = "revisión";

    private static Trabajos instancia;
    private final List<Trabajo> coleccionTrabajos;


    static Trabajos getInstancia() {
        if (instancia == null) {
            instancia = new Trabajos();
        }
        return instancia;
    }

    public void comenzar() {
        Document documento = UtilidadesXml.leerDocumentoXml(FICHERO_TRABAJOS);
        if (documento != null) {
            procesarDocumentoXml(documento);
        }
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList nodos = documentoXml.getElementsByTagName(TRABAJO);
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elemento = (Element) nodos.item(i);
            try {
                insertar(getTrabajo(elemento));
            } catch (TallerMecanicoExcepcion | NullPointerException e) {
            }
        }
    }

    private Trabajo getTrabajo(Element elemento) {
        Cliente cliente = Clientes.getInstancia().buscar(Cliente.get(elemento.getAttribute(CLIENTE)));
        Vehiculo vehiculo = Vehiculos.getInstancia().buscar(Vehiculo.get(elemento.getAttribute(VEHICULO)));

        LocalDate fechaInicio = LocalDate.parse(elemento.getAttribute(FECHA_INICIO), FORMATO_FECHA);
        String tipo = elemento.getAttribute(TIPO);

        Trabajo trabajo;

        if (tipo.equals(MECANICO)) {
            trabajo = new Mecanico(cliente, vehiculo, fechaInicio);
            float precioMaterialXml = Float.parseFloat(elemento.getAttribute(PRECIO_MATERIAL));
            try {
                if (precioMaterialXml > 0) {
                    ((Mecanico) trabajo).anadirPrecioMaterial(precioMaterialXml);
                }
            } catch (TallerMecanicoExcepcion e) {
                System.err.println("Error inesperado al cargar material: " + e.getMessage());
            }
        } else {
            trabajo = new Revision(cliente, vehiculo, fechaInicio);
        }

        String fechaFinStr = elemento.getAttribute(FECHA_FIN);
        if (fechaFinStr != null && !fechaFinStr.isBlank()) {
            try {
                int horasTotales = Integer.parseInt(elemento.getAttribute(HORAS));
                LocalDate fechaFin = LocalDate.parse(fechaFinStr, FORMATO_FECHA);

                if (horasTotales > 0) {
                    trabajo.anadirHoras(horasTotales);
                }
                trabajo.cerrar(fechaFin);

            } catch (TallerMecanicoExcepcion | IllegalArgumentException e) {
                System.err.println("Error al restaurar estado del trabajo: " + e.getMessage());
            }
        }
        return trabajo;
    }

    public void terminar() {
        Document documento = crearDocumentoXml();
        if (documento != null) {
            UtilidadesXml.escribirDocumentoXml(documento,FICHERO_TRABAJOS);
        }
    }

    private Document crearDocumentoXml() {
        Document documentoXml = UtilidadesXml.crearConstructorDocumentoXml().newDocument();
        Element raiz = documentoXml.createElement(RAIZ);
        documentoXml.appendChild(raiz);

        for (Trabajo trabajo : coleccionTrabajos) {
            Element elementoTrabajo = getElemento(documentoXml, trabajo);
            raiz.appendChild(elementoTrabajo);
        }
        return documentoXml;
    }

    private Element getElemento(Document documentoXml, Trabajo trabajo) {
        Element elementoTrabajo = documentoXml.createElement(TRABAJO);

        elementoTrabajo.setAttribute(CLIENTE, trabajo.getCliente().getDni());
        elementoTrabajo.setAttribute(VEHICULO, trabajo.getVehiculo().matricula());
        elementoTrabajo.setAttribute(FECHA_INICIO, trabajo.getFechaInicio().format(FORMATO_FECHA));
        elementoTrabajo.setAttribute(HORAS, String.valueOf(trabajo.getHoras()));

        String fechaFinStr = (trabajo.getFechaFin() != null) ? trabajo.getFechaFin().format(FORMATO_FECHA) : "";
        elementoTrabajo.setAttribute(FECHA_FIN, fechaFinStr);

        if (trabajo instanceof Mecanico mecanico) {
            elementoTrabajo.setAttribute(TIPO, MECANICO);
            elementoTrabajo.setAttribute(PRECIO_MATERIAL, String.valueOf(mecanico.getPrecioMaterial()));
        } else {
            elementoTrabajo.setAttribute(TIPO, REVISION);
        }

        return elementoTrabajo;
    }
    public Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }


    @Override
    public List<Trabajo> get() {
        return new ArrayList<>(coleccionTrabajos);
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> trabajosCliente = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getCliente().equals(cliente)) {
                trabajosCliente.add(trabajo);
            }
        }
        return trabajosCliente;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> trabajosVehiculo = new ArrayList<>();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getVehiculo().equals(vehiculo)) {
                trabajosVehiculo.add(trabajo);
            }
        }
        return trabajosVehiculo;
    }

    public Map<TipoTrabajo,Integer> getEstadisticasMensuales(LocalDate mes) {
        Objects.requireNonNull(mes,"El mes no puede ser nulo.");
        Map<TipoTrabajo, Integer> estadisticas = inicializarEstadisticas();
        for (Trabajo trabajo : coleccionTrabajos) {
            if (trabajo.getFechaInicio().getMonth() == mes.getMonth() &&
                    trabajo.getFechaInicio().getYear() == mes.getYear()) {

                TipoTrabajo tipo = TipoTrabajo.get(trabajo);

                estadisticas.put(tipo, estadisticas.get(tipo) + 1);
            }
        }

        return estadisticas;
    }

    private Map<TipoTrabajo,Integer> inicializarEstadisticas() {
        Map<TipoTrabajo,Integer> estadisticas = new EnumMap<>(TipoTrabajo.class);

        for (TipoTrabajo tipoTrabajo : TipoTrabajo.values()) {
            estadisticas.put(tipoTrabajo,0);
        }
        return estadisticas;
    }

    @Override
    public void insertar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede insertar un trabajo nulo.");
        comprobarTrabajo(trabajo.getCliente(), trabajo.getVehiculo(), trabajo.getFechaInicio());
        coleccionTrabajos.add(trabajo);
    }

    private void comprobarTrabajo(Cliente cliente, Vehiculo vehiculo, LocalDate fechaInicio) throws TallerMecanicoExcepcion {
        for (Trabajo t : coleccionTrabajos) {
            if (t.getCliente().equals(cliente)) {
                if (!t.estaCerrado()) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                } else if (!t.getFechaFin().isBefore(fechaInicio)) {
                    throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
                }
            }
            if (t.getVehiculo().equals(vehiculo)) {
                if (!t.estaCerrado()) {
                    throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                } else if (!t.getFechaFin().isBefore(fechaInicio)) {
                    throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
                }
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");

        Trabajo encontrado = getTrabajoAbierto(trabajo.getVehiculo());

        if (encontrado == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        encontrado.anadirHoras(horas);
        return encontrado;
    }

    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");

        Trabajo encontrado = getTrabajoAbierto(trabajo.getVehiculo());

        if (encontrado == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        if (encontrado instanceof Mecanico mecanico) {
            mecanico.anadirPrecioMaterial(precioMaterial);
        } else {
            throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");
        }
    }

    private Trabajo getTrabajoAbierto(Vehiculo vehiculo) {
        for (Trabajo t : coleccionTrabajos) {
            if (t.getVehiculo().equals(vehiculo) && !t.estaCerrado()) {
                return t;
            }
        }
        return null;
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");

        Trabajo encontrado = getTrabajoAbierto(trabajo.getVehiculo());

        if (encontrado == null) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");
        }

        encontrado.cerrar(fechaFin);
        return encontrado;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int indice = coleccionTrabajos.indexOf(trabajo);
        return (indice == -1) ? null : coleccionTrabajos.get(indice);
    }



    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (!coleccionTrabajos.remove(trabajo)) {
            throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
        }
    }
}