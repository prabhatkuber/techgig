package com.prabhat.mmt.model.response;

import java.util.Objects;

public class JourneyDetail {
    String flightRoute;
    String ticketNo;
    int duration;


    @Override
    public String toString() {
        return   "{"+
                "{'" + flightRoute + '\'' +
                ", {'" + ticketNo + '\'' +
                " ," + duration +
                '}'+"},";
    }

    public JourneyDetail(String flightRoute, String ticketNo,
                         int duration) {
        this.flightRoute=flightRoute;
        this.ticketNo=ticketNo;
        this.duration=duration;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public int getDuration() {
        return duration;
    }

    public String getFlightRoute() {
        return flightRoute;
    }

    public void setFlightRoute(String flightRoute) {
        this.flightRoute = flightRoute;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JourneyDetail wrapper = (JourneyDetail) o;
        return duration == wrapper.duration &&
                Objects.equals(ticketNo, wrapper.ticketNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketNo, duration);
    }
}
