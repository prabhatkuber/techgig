package com.prabhat.mmt.database;

import com.prabhat.mmt.exception.IncorrectDataException;
import com.prabhat.mmt.model.FlightDetails;
import com.prabhat.mmt.model.Journey;

import java.util.List;
import java.util.Map;

public interface Mapper {
    default FlightDetails createFlightDetails(String[] attributes){
        if(attributes.length==5) {
            FlightDetails flightDetails = new FlightDetails(attributes[0],attributes[1], attributes[2], Integer.parseInt(attributes[3]), Integer.parseInt(attributes[4]));
            return flightDetails;
        } else
            throw new IncorrectDataException("provided data  aren't correct");
    }
    public Map<Journey, List<FlightDetails>>  readBooksFromCSV(String filePath);
}
