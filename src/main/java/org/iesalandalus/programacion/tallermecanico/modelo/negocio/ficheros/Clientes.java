package org.iesalandalus.programacion.tallermecanico.modelo.negocio.ficheros;

import org.iesalandalus.programacion.tallermecanico.modelo.TallerMecanicoExcepcion;
import org.iesalandalus.programacion.tallermecanico.modelo.dominio.Cliente;
import org.iesalandalus.programacion.tallermecanico.modelo.negocio.IClientes;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Clientes implements IClientes {

    private static final String FICHERO_CLIENTES = "src/clientes.xml";
    private static final String RAIZ = "clientes";
    private static final String CLIENTE = "cliente";
    private static final String NOMBRE = "nombre";
    private static final String DNI = "dni";
    private static final String TELEFONO = "telefono";

    private static Clientes instancia;
    private final List<Cliente> coleccionClientes;

    private Clientes() {
        coleccionClientes = new ArrayList<>();
    }

    static Clientes getInstancia() {
        if (instancia == null) {
            instancia = new Clientes();
        }
        return instancia;
    }

    @Override
    public void comenzar() {
        Document documento = UtilidadesXml.leerDocumentoXml(FICHERO_CLIENTES);
        if (documento != null) {
            procesarDocumentoXml(documento);
        }
    }

    @Override
    public void terminar() {
        Document documento = crearDocumentoXml();
        if (documento != null) {
            UtilidadesXml.escribirDocumentoXml(documento, FICHERO_CLIENTES);
        }
    }

    private void procesarDocumentoXml(Document documentoXml) {
        NodeList nodos = documentoXml.getElementsByTagName(CLIENTE);
        for (int i = 0; i < nodos.getLength(); i++) {
            Element elemento = (Element) nodos.item(i);
            String dni = elemento.getAttribute(DNI);
            String nombre = elemento.getAttribute(NOMBRE);
            String telefono = elemento.getAttribute(TELEFONO);

            try {
                insertar(new Cliente(nombre, dni, telefono));
            } catch (Exception e) {
            }
        }
    }

    private Cliente getCliente(Element elemento) {
        String nombre = elemento.getAttribute(NOMBRE);
        String dni = elemento.getAttribute(DNI);
        String telefono = elemento.getAttribute(TELEFONO);
        return new Cliente(nombre, dni, telefono);
    }

    private Document crearDocumentoXml() {
        Document documentoXml = UtilidadesXml.crearConstructorDocumentoXml().newDocument();
        Element raiz = documentoXml.createElement(RAIZ);
        documentoXml.appendChild(raiz);

        for (Cliente cliente : coleccionClientes) {
            Element elementoCliente = documentoXml.createElement(CLIENTE);
            elementoCliente.setAttribute(DNI, cliente.getDni());
            elementoCliente.setAttribute(NOMBRE, cliente.getNombre());
            elementoCliente.setAttribute(TELEFONO, cliente.getTelefono());
            raiz.appendChild(elementoCliente);
        }
        return documentoXml;
    }

    private Element getElemento(Cliente cliente, Document documentoXml) {
        Element elementoCliente = documentoXml.createElement(CLIENTE);
        // Usamos getters tradicionales ya que no es un record
        elementoCliente.setAttribute(NOMBRE, cliente.getNombre());
        elementoCliente.setAttribute(DNI, cliente.getDni());
        elementoCliente.setAttribute(TELEFONO, cliente.getTelefono());
        return elementoCliente;
    }

    @Override
    public List<Cliente> get() {
        // REQUISITO: Ordenados por nombre y DNI
        List<Cliente> clientesOrdenados = new ArrayList<>(coleccionClientes);
        clientesOrdenados.sort(Comparator.comparing(Cliente::getNombre).thenComparing(Cliente::getDni));
        return clientesOrdenados;
    }

    @Override
    public void insertar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede insertar un cliente nulo.");
        if (coleccionClientes.contains(cliente)) {
            throw new TallerMecanicoExcepcion("Ya existe un cliente con ese DNI.");
        }
        coleccionClientes.add(cliente);
    }

    @Override
    public Cliente modificar(Cliente cliente, String nombre, String telefono) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede modificar un cliente nulo.");
        Cliente clienteEncontrado = buscar(cliente);

        if (clienteEncontrado == null) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }

        if (nombre != null && !nombre.isBlank()) {
            clienteEncontrado.setNombre(nombre);
        }
        if (telefono != null && !telefono.isBlank()) {
            clienteEncontrado.setTelefono(telefono);
        }
        return clienteEncontrado;
    }

    @Override
    public Cliente buscar(Cliente cliente) {
        Objects.requireNonNull(cliente, "No se puede buscar un cliente nulo.");
        int indice = coleccionClientes.indexOf(cliente);
        return (indice == -1) ? null : coleccionClientes.get(indice);
    }

    @Override
    public void borrar(Cliente cliente) throws TallerMecanicoExcepcion {
        Objects.requireNonNull(cliente, "No se puede borrar un cliente nulo.");
        if (!coleccionClientes.remove(cliente)) {
            throw new TallerMecanicoExcepcion("No existe ningún cliente con ese DNI.");
        }
    }
}