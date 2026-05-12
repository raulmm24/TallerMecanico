package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.*;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.ITrabajos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Trabajos implements ITrabajos {

    private static final String FICHERO_TRABAJOS = "datos/trabajos.xml";
    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String RAIZ = "trabajos";
    private static final String TRABAJO = "trabajo";
    private static final String CLIENTE = "cliente";
    private static final String VEHICULO = "vehiculo";
    private static final String FECHA_INICIO = "fechaInicio";
    private static final String FECHA_FIN = "fechaFin";
    private static final String HORAS = "horas";
    private static final String PRECIO_MATERIAL = "precioMaterial";
    private static final String TIPO = "tipo";
    private static final String MECANICO = "mecanico";
    private static final String REVISION = "revision";

    private static Trabajos instancia;
    private final List<Trabajo> coleccionTrabajos;

    private Trabajos() {
        coleccionTrabajos = new ArrayList<>();
    }

    static Trabajos getInstancia() {
        if (instancia == null) {
            instancia = new Trabajos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        Document documento = UtilidadesXml.leerDocumentoXml(FICHERO_TRABAJOS);
        if (documento != null) {
            procesarDocumentoXml(documento);
        }
    }

    @Override
    public void terminar() {
        Document documento = crearDocumentoXml();
        if (documento != null) {
            UtilidadesXml.escribirDocumentoXml(documento, FICHERO_TRABAJOS);
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
            String precioMatStr = elemento.getAttribute(PRECIO_MATERIAL);
            if (precioMatStr != null && !precioMatStr.isBlank()) {
                try {
                    ((Mecanico) trabajo).anadirPrecioMaterial(Float.parseFloat(precioMatStr));
                } catch (NumberFormatException | TallerMecanicoExcepcion e) {
                }
            }
        } else {
            trabajo = new Revision(cliente, vehiculo, fechaInicio);
        }

        String fechaFinStr = elemento.getAttribute(FECHA_FIN);
        String horasStr = elemento.getAttribute(HORAS);

        if (fechaFinStr != null && !fechaFinStr.isBlank()) {
            try {
                if (horasStr != null && !horasStr.isBlank()) {
                    int horas = Integer.parseInt(horasStr);
                    if (horas > 0) {
                        trabajo.anadirHoras(horas);
                    }
                }
                trabajo.cerrar(LocalDate.parse(fechaFinStr, FORMATO_FECHA));
            } catch (NumberFormatException | TallerMecanicoExcepcion e) {
            }
        }
        return trabajo;
    }

    private Document crearDocumentoXml() {
        Document documentoXml = UtilidadesXml.crearConstructorDocumentoXml().newDocument();
        Element raiz = documentoXml.createElement(RAIZ);
        documentoXml.appendChild(raiz);
        for (Trabajo trabajo : coleccionTrabajos) {
            raiz.appendChild(getElemento(documentoXml, trabajo));
        }
        return documentoXml;
    }

    private Element getElemento(Document documentoXml, Trabajo trabajo) {
        Element e = documentoXml.createElement(TRABAJO);
        e.setAttribute(CLIENTE, trabajo.getCliente().getDni());
        e.setAttribute(VEHICULO, trabajo.getVehiculo().matricula());
        e.setAttribute(FECHA_INICIO, trabajo.getFechaInicio().format(FORMATO_FECHA));
        e.setAttribute(HORAS, String.valueOf(trabajo.getHoras()));
        e.setAttribute(FECHA_FIN, trabajo.getFechaFin() != null ? trabajo.getFechaFin().format(FORMATO_FECHA) : "");

        if (trabajo instanceof Mecanico m) {
            e.setAttribute(TIPO, MECANICO);
            e.setAttribute(PRECIO_MATERIAL, String.valueOf(m.getPrecioMaterial()));
        } else {
            e.setAttribute(TIPO, REVISION);
        }
        return e;
    }

    @Override
    public List<Trabajo> get() {
        List<Trabajo> listaOrdenada = new ArrayList<>(coleccionTrabajos);

        Comparator<Trabajo> comparadorTrabajos = new Comparator<Trabajo>() {
            @Override
            public int compare(Trabajo t1, Trabajo t2) {
                int resultado = t1.getFechaInicio().compareTo(t2.getFechaInicio());
                if (resultado == 0) {
                    resultado = t1.getCliente().getNombre().compareTo(t2.getCliente().getNombre());
                }
                if (resultado == 0) {
                    resultado = t1.getCliente().getDni().compareTo(t2.getCliente().getDni());
                }
                return resultado;
            }
        };
        listaOrdenada.sort(comparadorTrabajos);
        return listaOrdenada;
    }

    @Override
    public List<Trabajo> get(Cliente cliente) {
        List<Trabajo> lista = new ArrayList<>();
        for (Trabajo t : coleccionTrabajos) {
            if (t.getCliente().equals(cliente)) lista.add(t);
        }
        return lista;
    }

    @Override
    public List<Trabajo> get(Vehiculo vehiculo) {
        List<Trabajo> lista = new ArrayList<>();
        for (Trabajo t : coleccionTrabajos) {
            if (t.getVehiculo().equals(vehiculo)) lista.add(t);
        }
        return lista;
    }

    @Override
    public Map<TipoTrabajo, Integer> getEstadisticasMensuales(LocalDate mes) {
        Objects.requireNonNull(mes, "El mes no puede ser nulo.");
        Map<TipoTrabajo, Integer> stats = inicializarEstadisticas();
        for (Trabajo t : coleccionTrabajos) {
            if (t.getFechaInicio().getMonth() == mes.getMonth() && t.getFechaInicio().getYear() == mes.getYear()) {
                TipoTrabajo tipo = TipoTrabajo.get(t);
                stats.put(tipo, stats.get(tipo) + 1);
            }
        }
        return stats;
    }

    private Map<TipoTrabajo, Integer> inicializarEstadisticas() {
        Map<TipoTrabajo, Integer> stats = new EnumMap<>(TipoTrabajo.class);
        for (TipoTrabajo tipo : TipoTrabajo.values()) stats.put(tipo, 0);
        return stats;
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
                if (!t.estaCerrado()) throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo en curso.");
                if (t.getFechaFin() != null && !t.getFechaFin().isBefore(fechaInicio)) throw new TallerMecanicoExcepcion("El cliente tiene otro trabajo posterior.");
            }
            if (t.getVehiculo().equals(vehiculo)) {
                if (!t.estaCerrado()) throw new TallerMecanicoExcepcion("El vehículo está actualmente en el taller.");
                if (t.getFechaFin() != null && !t.getFechaFin().isBefore(fechaInicio)) throw new TallerMecanicoExcepcion("El vehículo tiene otro trabajo posterior.");
            }
        }
    }

    @Override
    public Trabajo anadirHoras(Trabajo trabajo, int horas) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir horas a un trabajo nulo.");
        Trabajo encontrado = buscar(trabajo);
        if (encontrado == null) throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");

        encontrado.anadirHoras(horas);
        return encontrado;
    }

    @Override
    public void anadirPrecioMaterial(Trabajo trabajo, float precioMaterial) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo añadir precio del material a un trabajo nulo.");
        Trabajo encontrado = buscar(trabajo);
        if (encontrado == null) throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");

        if (!(encontrado instanceof Mecanico m)) throw new TallerMecanicoExcepcion("No se puede añadir precio al material para este tipo de trabajos.");

        m.anadirPrecioMaterial(precioMaterial);
    }

    @Override
    public Trabajo cerrar(Trabajo trabajo, LocalDate fechaFin) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No puedo cerrar un trabajo nulo.");
        Trabajo encontrado = buscar(trabajo);

        if (encontrado == null) throw new TallerMecanicoExcepcion("No existe ningún trabajo abierto para dicho vehículo.");

        encontrado.cerrar(fechaFin);
        return encontrado;
    }

    @Override
    public Trabajo buscar(Trabajo trabajo) {
        Objects.requireNonNull(trabajo, "No se puede buscar un trabajo nulo.");
        int i = coleccionTrabajos.indexOf(trabajo);
        return i == -1 ? null : coleccionTrabajos.get(i);
    }

    @Override
    public void borrar(Trabajo trabajo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(trabajo, "No se puede borrar un trabajo nulo.");
        if (!coleccionTrabajos.remove(trabajo)) throw new TallerMecanicoExcepcion("No existe ningún trabajo igual.");
    }
}