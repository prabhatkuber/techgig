package com.prabhat.mmt.manager;


import com.prabhat.mmt.cache.FlightCache;
import com.prabhat.mmt.model.FlightDetails;
import com.prabhat.mmt.model.Journey;
import com.prabhat.mmt.model.response.JourneyDetail;
import com.prabhat.mmt.strategies.Strategies;
import com.prabhat.mmt.utils.MmtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class FlightSearchManager implements Manager{

    @Autowired
    FlightCache flightCache;

    @Autowired
    Strategies timeBasedStrategies;

    public List<JourneyDetail> getDetails(String source, String destinatoin, int numberOfOutPut){

        List<JourneyDetail> fightDetails=new ArrayList<>();
        Map<Journey, List<FlightDetails>> totalCachce=flightCache.getFlightCache();
        List<FlightDetails> DirectflightDetails=totalCachce.get(new Journey(source,destinatoin));

        //sort based on time spent in air
        if(null!= DirectflightDetails)
            DirectflightDetails.parallelStream().sorted(Comparator.comparingInt(a -> (a.getArrivalTime() - a.getDepartureTime()))).forEach(a ->{
                if(a.getArrivalTime()<a.getDepartureTime()) {
                    fightDetails.add(new JourneyDetail(a.getSource() + "_" + a.getDestination(), a.getTicketNo(), MmtUtils.getDataInMin(2400+a.getArrivalTime() - a.getDepartureTime())));
                }else{
                    fightDetails.add(new JourneyDetail(a.getSource() + "_" + a.getDestination(), a.getTicketNo(), MmtUtils.getDataInMin(a.getArrivalTime() - a.getDepartureTime())));
                }
            });
        System.out.println("total direct possible route possible :- "+fightDetails);


        if(fightDetails.size()<numberOfOutPut){
            timeBasedStrategies.routeBasedOnTime(fightDetails,source,destinatoin);
        }

        List<JourneyDetail> sortedOne=fightDetails.stream().sorted(Comparator.comparingInt(JourneyDetail::getDuration)).collect(Collectors.toList());


       /*
        List<OutPutWrapper>  trimmedData= sortedOne.stream().limit(numberOfOutPut).collect(Collectors.toList());
           for(OutPutWrapper l:trimmedData){
               if(mapRoutTofightDetails.get(l.getFlightRoute())==null){
                   mapRoutTofightDetails.put(l.getFlightRoute(),new ArrayList<OutPutWrapper>());
               }
               mapRoutTofightDetails.get(l.getFlightRoute()).add(l);
           }
           return
        */

        if(sortedOne.size()>=numberOfOutPut)
            return sortedOne.stream().limit(numberOfOutPut).collect(Collectors.toList());
        else
            return sortedOne;
    }

}
