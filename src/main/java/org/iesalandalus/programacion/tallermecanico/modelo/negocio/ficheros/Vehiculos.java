package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.Modelo;
import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {

    private static final String FICHERO_VEHICULOS = "datos/vehiculos.xml";
    private static final String RAIZ = "Vehículos";
    private static final String VEHICULO = "vehículo";
    private static final String MARCA = "marca";
    private static final String MODELO = "modelo";
    private static final String MATRICULA = "matricula";

    private static Vehiculos instancia;
    private final List<Vehiculo> coleccionVehiculos;

    static Vehiculos getInstancia() {
        if (instancia == null) {
            instancia = new Vehiculos();
        }
        return instancia;
    }

    public void comenzar() {
        Document documento = UtilidadesXml.leerDocumentoXml(FICHERO_VEHICULOS);
        if (documento != null) {
            procesarDocumentoXml(documento);
        }
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList nodos = documentoXml.getElementsByTagName(VEHICULO);
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elemento = (Element) nodos.item(i);
            try {
                insertar(getVehiculo(elemento));
            } catch (TallerMecanicoExcepcion | NullPointerException e) {
            }
        }
    }

    private Vehiculo getVehiculo(Element elemento) {
        String marca = elemento.getAttribute(MARCA);
        String modelo = elemento.getAttribute(MODELO);
        String matricula = elemento.getAttribute(MATRICULA);
        return new Vehiculo(marca,modelo,matricula);
    }

    public void terminar() {
        Document documento = crearDocumentoXml();
        if (documento != null) {
            UtilidadesXml.escribirDocumentoXml(documento,FICHERO_VEHICULOS);
        }
    }

    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = constructor.newDocument();

        Element raiz = documentoXml.createElement(RAIZ);
        documentoXml.appendChild(raiz);

        for (Vehiculo vehiculo : coleccionVehiculos) {
            Element elementoVehiculo = getElemento(vehiculo, documentoXml);
            raiz.appendChild(elementoVehiculo);
        }

        return documentoXml;
    }

    private Element getElemento(Vehiculo vehiculo, Document documentoXml) {
        Element elementoCliente = documentoXml.createElement(VEHICULO);
        elementoCliente.setAttribute(MARCA, vehiculo.marca());
        elementoCliente.setAttribute(MODELO, vehiculo.modelo());
        elementoCliente.setAttribute(MATRICULA, vehiculo.matricula());

        return elementoCliente;
    }


    public Vehiculos() {
        coleccionVehiculos = new ArrayList<>();
    }

    @Override
    public List<Vehiculo> get() {
        return new ArrayList<>(coleccionVehiculos);
    }


    @Override
    public void insertar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede insertar un vehículo nulo.");
        if (coleccionVehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("Ya existe un vehículo con esa matrícula.");
        }
        coleccionVehiculos.add(vehiculo);
    }


    @Override
    public Vehiculo buscar(Vehiculo vehiculo) {
        Objects.requireNonNull(vehiculo, "No se puede buscar un vehículo nulo.");
        int vehiculoEncontrado = coleccionVehiculos.indexOf(vehiculo);
        return (vehiculoEncontrado == -1) ? null : coleccionVehiculos.get(vehiculoEncontrado);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede borrar un vehículo nulo.");
        if (!coleccionVehiculos.contains(vehiculo)) {
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }
        coleccionVehiculos.remove(vehiculo);
    }
}
