package com.prabhat.mmt.strategies;

import com.prabhat.mmt.cache.FlightCache;
import com.prabhat.mmt.model.FlightDetails;
import com.prabhat.mmt.model.Journey;
import com.prabhat.mmt.model.response.JourneyDetail;
import com.prabhat.mmt.utils.MmtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Component
public class TimeBasedStrategies implements Strategies {

    @Autowired
    FlightCache flightCache;

    public  void routeBasedOnTime(List<JourneyDetail> fightDetails, String source, String destinatoin){

        Set<Journey> journey=flightCache.getFlightCache().keySet();
        List<Journey> sourceJounrey= journey.stream().filter(a-> a.getSource().startsWith(source)).collect(Collectors.toList());
        List<Journey> DestinationJounrey= journey.stream().filter(a-> a.getDestination().endsWith(destinatoin)).collect(Collectors.toList());

        //Contains List of Flight starting from source
        Map<Journey, List<FlightDetails>> filterDataBySource=flightCache.getFlightCache().entrySet().stream().filter(e-> sourceJounrey.contains(e.getKey())).collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));

        ////Contains List of Flight reaching at destination
        Map<Journey, List<FlightDetails>> filterDataByDestination=flightCache.getFlightCache().entrySet().stream().filter(e-> DestinationJounrey.contains(e.getKey())).collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));

        final AtomicInteger totalDuration= new AtomicInteger(Integer.MAX_VALUE);

        for (final Journey journey1 : filterDataBySource.keySet()) {
            String destination=journey1.getDestination();
            totalDuration.set(Integer.MAX_VALUE);
            filterDataByDestination.keySet().stream().filter(journey2-> journey2.getSource().equalsIgnoreCase(destination)).forEachOrdered(journey2->{
                String ticketNo=null;
                List<FlightDetails> totalFlightForSpecificDestination=filterDataBySource.get(journey1);
                List<FlightDetails> totalFlightForfinalDestination=filterDataByDestination.get(journey2);

                for(FlightDetails flightDetail1:totalFlightForSpecificDestination){

                    for(FlightDetails flightDetail2:totalFlightForfinalDestination){

                        //this is to cater the situation when flight reaching departure date is 1 earlier from arrival date.
                        //This would make calculation very easy
                        // if(flightDetail1.getArrivalTime()<flightDetail1.getDepartureTime())   flightDetail1.setArrivalTime(flightDetail1.getArrivalTime()+2400);
                        // if(flightDetail2.getArrivalTime()<flightDetail2.getDepartureTime())flightDetail2.setArrivalTime(flightDetail2.getArrivalTime()+2400);



                        /****
                         * All possible scenario
                         *    Departure of first Flight--> Arrival of first flight-->Departure of second flight--> Arrival of second fllight
                         * 1) All in single days--> 8 ->9-->12-->15----Here difference is more than 2 between fligh--> output would be 15-8-7;
                         * 2) All in single days--> 8 ->9-->10-->12----Here difference is less than 2 between flight--> output would be 24-8+12-->28;
                         *
                         *  Multi Day event
                         * 3) 23---1--4--5--> Here difference is more than 3 hours but final out come is negetive :5-23 so output would 24+(5-23)-->6
                         * 4) 20--23--2--5-->Here difference is more than 3 hours but final out come is negetive :5-20 so output would 24+(5-20)-->9
                         *
                         *
                         * 2nd Flight fall on multi day:-
                         * 5) 20--21---2305--1
                         *   if incase of Second flight detail--> arival Value is less than deapture then add 24 in arrival so final value would
                         *     20--21---2305--2500
                         * -------------------------------------Cover all possible scenario.
                         *
                         *
                         * DEL:2200:--------:2315:--------:2120:--------:2340:-----1540
                         * DEL:2200:--------:2315:--------:2245:--------:100:-----180
                         * BLR:1110:--------:1415:--------:645:--------:900:-----1310
                         * BLR:1110:--------:1415:--------:835:--------:1110:-----1440
                         *
                         *
                         *
                         */

                        int firstFlightDurationInMinute=MmtUtils.getDataInMin(flightDetail1.getArrivalTime()-flightDetail1.getDepartureTime());
                        int secondFlightDurationInMinute=MmtUtils.getDataInMin(flightDetail2.getArrivalTime()-flightDetail2.getDepartureTime());
                        int gapBetweenTwo=MmtUtils.getDataInMin(flightDetail2.getDepartureTime()-flightDetail1.getArrivalTime());
                        if(gapBetweenTwo>120) {
                            int totalTime = firstFlightDurationInMinute + secondFlightDurationInMinute + gapBetweenTwo;
                            if (totalDuration.get() > totalTime) {
                                totalDuration.set(totalTime);
                                ticketNo = flightDetail1.getTicketNo() + "_" + flightDetail2.getTicketNo();
                                System.out.println("::::::::::::" + flightDetail2.getSource() + ":" + flightDetail1.getDepartureTime() + ":--------:" + flightDetail1.getArrivalTime() + ":--------:" + flightDetail2.getDepartureTime() + ":--------:" + flightDetail2.getArrivalTime() + ":-----" + totalDuration.get());
                            }
                        }else{
                            int totalTime = 2400+firstFlightDurationInMinute + secondFlightDurationInMinute + gapBetweenTwo;
                            if (totalDuration.get() > totalTime) {
                                totalDuration.set(totalTime);
                                ticketNo = flightDetail1.getTicketNo() + "_" + flightDetail2.getTicketNo();
                                System.out.println("::" + flightDetail2.getSource() + ":" + flightDetail1.getDepartureTime() + ":--------:" + flightDetail1.getArrivalTime() + ":--------:" + flightDetail2.getDepartureTime() + ":--------:" + flightDetail2.getArrivalTime() + ":-----" + totalDuration.get());
                            }
                        }
                       /* ///Will satisfy condition 1,3,5
                        if(flightDetail2.getDepartureTime()-flightDetail1.getArrivalTime()>200){
                            if(totalDuration.get() >(MmtUtils.getDataInMin(flightDetail2.getArrivalTime() - flightDetail1.getDepartureTime()))) {
                                totalDuration.set(MmtUtils.getDataInMin(flightDetail2.getArrivalTime() - flightDetail1.getDepartureTime()));
                                ticketNo=flightDetail1.getTicketNo()+"_"+flightDetail2.getTicketNo();
                                System.out.println(flightDetail2.getSource()+":"+flightDetail1.getDepartureTime()+":--------:"+flightDetail1.getArrivalTime()+":--------:"+flightDetail2.getDepartureTime()+":--------:"+flightDetail2.getArrivalTime()+":-----"+totalDuration.get());
                            }
                            //else block will sove 4th one
                        }else if(flightDetail2.getDepartureTime()-flightDetail1.getArrivalTime()<0){
                            int firstFlightDurationInMinute=MmtUtils.getDataInMin(flightDetail1.getArrivalTime()-flightDetail1.getDepartureTime());
                            int secondFlightDurationInMinute=MmtUtils.getDataInMin(flightDetail2.getArrivalTime()-flightDetail2.getDepartureTime());
                            int gapBetweenTwo=MmtUtils.getDataInMin(flightDetail2.getDepartureTime()-flightDetail1.getArrivalTime());
                            int totalTime=firstFlightDurationInMinute+secondFlightDurationInMinute+gapBetweenTwo;
                            if(totalDuration.get()>totalTime) {
                                totalDuration.set(totalTime);
                                ticketNo = flightDetail1.getTicketNo() + "_" + flightDetail2.getTicketNo();
                                System.out.println("::::::::::::" + flightDetail2.getSource() + ":" + flightDetail1.getDepartureTime() + ":--------:" + flightDetail1.getArrivalTime() + ":--------:" + flightDetail2.getDepartureTime() + ":--------:" + flightDetail2.getArrivalTime() + ":-----" + totalDuration.get());
                            }
                        }//8--9--10--12
                        else if(totalDuration.get() >(MmtUtils.getDataInMin(2400 - flightDetail1.getDepartureTime() +flightDetail2.getArrivalTime()))) {
                            totalDuration.set(MmtUtils.getDataInMin(2400 - flightDetail1.getDepartureTime() +flightDetail2.getArrivalTime()));
                            ticketNo=flightDetail1.getTicketNo()+"_"+flightDetail2.getTicketNo();

                            System.out.println(flightDetail2.getSource()+"----------------"+flightDetail1.getDepartureTime()+":--------:"+flightDetail1.getArrivalTime()+":--------:"+flightDetail2.getDepartureTime()+":--------:"+flightDetail2.getArrivalTime()+":-----"+totalDuration.get());
                        }
                        /**else{
                         if(totalDuration.get() > (((getDataInMin(firstFightDuration - secondFightDuration)) + 120) + getDataInMin(2400 - flightDetail1.getArrivalTime() +
                         flightDetail2.getDepartureTime())))
                         {
                         totalDuration.set( getDataInMin(2400 - flightDetail1.getDepartureTime() +flightDetail2.getArrivalTime()));
                         ticketNo=flightDetail1.getTicketNo()+"_"+flightDetail2.getTicketNo();
                         }
                         }*/
                    }
                }
                fightDetails.add(new JourneyDetail(journey1.getSource()+"_"+journey1.getDestination()+"_"+journey2.getDestination(), ticketNo, totalDuration.get()));
            });
        }
    }




}
