package com.prabhat.mmt.manager;

import com.prabhat.mmt.model.response.JourneyDetail;

import java.util.List;

public interface Manager {
    public List<JourneyDetail> getDetails(String source, String destinatoin, int numberOfOutPut);
}
