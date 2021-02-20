package com.prabhat.mmt.controller;


import com.prabhat.mmt.manager.Manager;
import com.prabhat.mmt.model.response.JourneyDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FlightController {

        @Autowired
        Manager manager;

        @RequestMapping(value = "/getAllFlight", method = RequestMethod.POST)
        public ResponseEntity getAllFlight(final String source, final String destination) {
            manager.getDetails(source,destination,5);
            return ResponseEntity.ok("");
        }


      @RequestMapping(value = "/getAllFlightDetails", method = RequestMethod.GET)
      public ResponseEntity getAllFlight(final String source, final String destination,int count) {
          List<JourneyDetail> data= manager.getDetails(source,destination,count);
          System.out.print(data);
          return ResponseEntity.ok("");
      }



}
