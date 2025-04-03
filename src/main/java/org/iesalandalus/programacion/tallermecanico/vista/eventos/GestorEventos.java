package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;


public class GestorEventos {

    Map<Evento, List<ReceptorEventos>> receptores = new EnumMap<>(Evento.class);

    public GestorEventos(Evento... eventos) {
        Objects.requireNonNull(eventos, "Se debe gestionar algun evento.");
        for (Evento evento : eventos) {
            receptores.put(evento,new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptor, Evento... eventos) {
        Objects.requireNonNull(eventos,"Te debes subscribir a algun evento.");
        Objects.requireNonNull(receptor,"El receptor de eventos no puede ser nulo");
        for (Evento evento : eventos) {
            List<ReceptorEventos> usuarios = receptores.get(evento);
            usuarios.add(receptor);
        }
    }

    public void desuscribir(ReceptorEventos receptor, Evento... eventos) {
        Objects.requireNonNull(eventos,"Te debes de desuscribir de algun evento.");
        Objects.requireNonNull(receptor,"El receptor de eventos no puede ser nulo.");
        for (Evento evento : eventos) {
            List<ReceptorEventos> usuarios = receptores.get(evento);
            usuarios.remove(receptor);
        }
    }

    public void notificar(Evento evento) {
        List<ReceptorEventos> listaReceptores = receptores.get(evento);
        if (listaReceptores != null) {
            for (ReceptorEventos receptor : listaReceptores) {
                receptor.actualizar(evento);
            }
        }
    }

}