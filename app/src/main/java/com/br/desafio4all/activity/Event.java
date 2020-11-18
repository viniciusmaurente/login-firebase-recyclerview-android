package com.br.desafio4all.activity;

public class Event {

    private String EventName;
    private String ImageUrl;

    public Event(String eventName, String imageUrl) {
        EventName = eventName;
        ImageUrl = imageUrl;
    }

    public Event() {
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
