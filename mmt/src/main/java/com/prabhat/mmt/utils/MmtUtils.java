package com.prabhat.mmt.utils;

import com.prabhat.mmt.main.Main;

public class MmtUtils {

    //Get hourly difference value in mins.
    public static int getDataInMin(int hourDiff){
        //Take care of difference in date that's come down to negetive because of day change.
        if(hourDiff<0)
            hourDiff=2400+hourDiff;
        int minForHour=((hourDiff/100)*60);
        if(hourDiff%100<50) return  minForHour+(hourDiff%100);
        minForHour+=(hourDiff%100)-40;
        return minForHour;
    }
/*
    public static void main(String[] args) {
        System.out.println(""+MmtUtils.getDataInMin(1640-1400));
        System.out.println(""+MmtUtils.getDataInMin(1850-1640));
        System.out.println(""+MmtUtils.getDataInMin(2020-1850));
        System.out.println(""+MmtUtils.getDataInMin(2000-1855));
        System.out.println(""+MmtUtils.getDataInMin(2035-1850));
        System.out.println(""+MmtUtils.getDataInMin(2040-1850));
        System.out.println(""+MmtUtils.getDataInMin(2000-1810));

        System.out.println(""+MmtUtils.getDataInMin(2040-1805));
        System.out.println(""+MmtUtils.getDataInMin(2000-1815));
    }*/

}
