package com.br.desafio4all.model;

public class Evento {

    private String EventoNome;
    private String ImageUrl;

    public Evento(String eventoNome, String imageUrl) {
        EventoNome = eventoNome;
        ImageUrl = imageUrl;
    }

    public Evento() {
    }

    public String getEventoNome() {
        return EventoNome;
    }

    public void setEventoNome(String eventoNome) {
        EventoNome = eventoNome;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
