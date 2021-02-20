package com.prabhat.mmt.strategies;

import com.prabhat.mmt.model.response.JourneyDetail;

import java.util.List;

public interface Strategies {
     void routeBasedOnTime(List<JourneyDetail> fightDetails, String source, String destination);
    //void routeBasedOnPrice(List<JourneyDetail> fightDetails, String source, String destination);
 }
