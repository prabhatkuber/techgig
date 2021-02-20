package com.prabhat.mmt.cache;


import com.prabhat.mmt.database.FileMapper;
import com.prabhat.mmt.database.Mapper;
import com.prabhat.mmt.model.FlightDetails;
import com.prabhat.mmt.model.Journey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
@PropertySource("classpath:application.properties")
public class FlightCache {

    @Value("${fileName}")
    public String fileName;

    @Autowired
    private Mapper mapper;

    private  Map<Journey, List<FlightDetails>> cache;

    @PostConstruct
    public void loadDataInCache(){
        cache=mapper.readBooksFromCSV(fileName);
    }

    public Map<Journey, List<FlightDetails>> getFlightCache(){
        return cache;
    }
}
