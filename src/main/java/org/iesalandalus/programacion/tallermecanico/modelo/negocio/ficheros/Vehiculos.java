package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Vehiculo;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IVehiculos;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Vehiculos implements IVehiculos {

    private static final String FICHERO_VEHICULOS = "datos/vehiculos.xml";
    private static final String RAIZ = "vehiculos";
    private static final String VEHICULO = "vehiculo";
    private static final String MARCA = "marca";
    private static final String MODELO = "modelo";
    private static final String MATRICULA = "matricula";

    private static Vehiculos instancia;
    private final List<Vehiculo> coleccionVehiculos;

    private Vehiculos() {
        coleccionVehiculos = new ArrayList<>();
    }

    static Vehiculos getInstancia() {
        if (instancia == null) {
            instancia = new Vehiculos();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        Document documento = UtilidadesXml.leerDocumentoXml(FICHERO_VEHICULOS);
        if (documento != null) {
            procesarDocumentoXml(documento);
        }
    }

    @Override
    public void terminar() {
        Document documento = crearDocumentoXml();
        if (documento != null) {
            UtilidadesXml.escribirDocumentoXml(documento, FICHERO_VEHICULOS);
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
        return new Vehiculo(marca, modelo, matricula);
    }

    private Document crearDocumentoXml() {
        DocumentBuilder constructor = UtilidadesXml.crearConstructorDocumentoXml();
        Document documentoXml = constructor.newDocument();

        Element raiz = documentoXml.createElement(RAIZ);
        documentoXml.appendChild(raiz);

        for (Vehiculo vehiculo : coleccionVehiculos) {
            raiz.appendChild(getElemento(vehiculo, documentoXml));
        }
        return documentoXml;
    }

    private Element getElemento(Vehiculo vehiculo, Document documentoXml) {
        Element elementoVehiculo = documentoXml.createElement(VEHICULO);
        elementoVehiculo.setAttribute(MARCA, vehiculo.marca());
        elementoVehiculo.setAttribute(MODELO, vehiculo.modelo());
        elementoVehiculo.setAttribute(MATRICULA, vehiculo.matricula());

        return elementoVehiculo;
    }

    @Override
    public List<Vehiculo> get() {
        List<Vehiculo> vehiculosOrdenados = new ArrayList<>(coleccionVehiculos);
        vehiculosOrdenados.sort(new Comparator<Vehiculo>() {
            @Override
            public int compare(Vehiculo v1, Vehiculo v2) {
                int resultado;
                resultado = v1.marca().compareTo(v2.marca());
                if (resultado == 0) {
                    resultado = v1.modelo().compareTo(v2.modelo());
                }
                if (resultado == 0) {
                    resultado = v1.matricula().compareTo(v2.matricula());
                }
                return resultado;
            }
        });
        return vehiculosOrdenados;
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
        int indice = coleccionVehiculos.indexOf(vehiculo);
        return (indice == -1) ? null : coleccionVehiculos.get(indice);
    }

    @Override
    public void borrar(Vehiculo vehiculo) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(vehiculo, "No se puede borrar un vehículo nulo.");
        if (!coleccionVehiculos.remove(vehiculo)) {
            throw new TallerMecanicoExcepcion("No existe ningún vehículo con esa matrícula.");
        }
    }
}