package com.prabhat.mmt.model;

import java.util.Objects;

public class Journey {
    String source;
    String destination;

    public Journey(String source, String destination){
        this.source=source;
        this.destination=destination;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Journey{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Journey journey = (Journey) o;
        return Objects.equals(source, journey.source) &&
                Objects.equals(destination, journey.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}
