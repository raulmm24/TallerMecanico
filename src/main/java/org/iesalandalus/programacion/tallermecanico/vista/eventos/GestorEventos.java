package org.iesalandalus.programacion.tallermecanico.vista.eventos;

import java.util.*;

public class GestorEventos {

    private final Map<Evento, List<ReceptorEventos>> receptores;

    public GestorEventos(Evento... eventos) {
        Objects.requireNonNull(eventos, "Los eventos no pueden ser nulos.");
        this.receptores = new EnumMap<>(Evento.class);
        for (Evento evento : eventos) {
            this.receptores.put(evento, new ArrayList<>());
        }
    }

    public void suscribir(ReceptorEventos receptor, Evento... eventos) {
        Objects.requireNonNull(receptor, "El receptor no puede ser nulo.");
        Objects.requireNonNull(eventos, "Los eventos no pueden ser nulos.");
        for (Evento evento : eventos) {
            List<ReceptorEventos> suscritos = receptores.get(evento);
            if (suscritos != null && !suscritos.contains(receptor)) {
                suscritos.add(receptor);
            }
        }
    }

    public void desuscribir(ReceptorEventos receptor, Evento... eventos) {
        Objects.requireNonNull(receptor, "El receptor no puede ser nulo.");
        Objects.requireNonNull(eventos, "Los eventos no pueden ser nulos.");
        for (Evento evento : eventos) {
            List<ReceptorEventos> suscritos = receptores.get(evento);
            if (suscritos != null) {
                suscritos.remove(receptor);
            }
        }
    }

    public void notificar(Evento evento) {
        Objects.requireNonNull(evento, "El evento a notificar no puede ser nulo.");
        List<ReceptorEventos> suscritos = receptores.get(evento);
        if (suscritos != null) {
            for (ReceptorEventos receptor : suscritos) {
                receptor.actualizar(evento);
            }
        }
    }
}