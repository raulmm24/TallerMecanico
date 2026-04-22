package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;

public class GestorEventos {

    private Map<Evento, List<ReceptorEventos>> receptores = new HashMap<>();

    public GestorEventos(Evento... eventos) {
        Objects.requireNonNull(eventos,"Los eventos no pueden ser nulos.");
        this.receptores = new HashMap<>();
        for (Evento evento : eventos) {
            this.receptores.put(evento, new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptor, Evento... eventos) {
        Objects.requireNonNull(receptor, "El receptor no puede ser nulo.");
        Objects.requireNonNull(eventos, "La lista de eventos no puede ser nula.");

        for (Evento evento : eventos) {
            List<ReceptorEventos> listaSuscritos = receptores.get(evento);
            if (listaSuscritos != null && !listaSuscritos.contains(receptor)) {
                listaSuscritos.add(receptor);
            }
        }
    }

    public void desuscribir(ReceptorEventos receptor, Evento... eventos) {
        Objects.requireNonNull(receptor,"El receptor no puede ser nulo.");
        Objects.requireNonNull(eventos,"La lista de eventos no puede ser nula.");

        for (Evento evento : eventos) {
            List<ReceptorEventos> listaSuscritos = receptores.get(evento);
            if (listaSuscritos != null) {
                listaSuscritos.remove(receptor);
            }
        }
    }
    public void notificar(Evento evento) {
        Objects.requireNonNull(evento, "No se puede notificar un evento nulo.");

        List<ReceptorEventos> listaSuscritos = receptores.get(evento);
        if (listaSuscritos != null) {
            for (ReceptorEventos receptor : listaSuscritos) {
                receptor.actualizar(evento);
            }
        }
    }
}
