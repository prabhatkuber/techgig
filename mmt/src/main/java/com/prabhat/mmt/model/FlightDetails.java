package com.prabhat.mmt.model;

public class FlightDetails {
    String ticketNo;
    String source;
    String destination;
    int departureTime;
    int arrivalTime;
    public FlightDetails(
            String ticketNo,
            String source,
            String destination,
            int departureTime,
            int arrivalTime){
        this.ticketNo=ticketNo;
        this.source=source;
        this.destination=destination;
        this.departureTime=departureTime;
        this.arrivalTime=arrivalTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = departureTime;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
