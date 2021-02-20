package com.prabhat.mmt.database;


import com.prabhat.mmt.exception.IncorrectDataException;
import com.prabhat.mmt.model.FlightDetails;
import com.prabhat.mmt.model.Journey;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FileMapper implements Mapper{
    public Map<Journey, List<FlightDetails>> readBooksFromCSV(String fileName) {
        Map<Journey, List<FlightDetails>> flightDetailsCache= new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
             while (line != null) {
                 String[] attributes = line.split(",");
                 FlightDetails flightDetails=createFlightDetails(attributes);
                 if(flightDetailsCache.get(new Journey(attributes[1], attributes[2]))==null){
                      flightDetailsCache.put(new Journey(attributes[1], attributes[2]), new ArrayList<>());
                 }
                 flightDetailsCache.get(new Journey(attributes[1], attributes[2])).add(flightDetails);
                 line = br.readLine(); } }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return flightDetailsCache;
    }
}
