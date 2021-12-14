package com.DhauEmbunAzzahraJmartPK.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Shipment {
    public static final SimpleDateFormat ESTIMATION_FORMAT = new SimpleDateFormat("EEE MMMM dd yyyy");
    public static final Plan INSTANT = new Plan((byte)(1<<0));
    public static final Plan SAME_DAY = new Plan((byte)(1<<1));
    public static final Plan NEXT_DAY = new Plan((byte)(1<<2));
    public static final Plan REGULER = new Plan((byte)(1<<3));
    public static final Plan KARGO = new Plan((byte)(1<<4));
    public String address;
    public int cost;
    public byte plan;
    public String receipt;

    /**
     * Creates a shipment.
     * @param address The address of destination.
     * @param cost The cost of shipment.
     * @param plan The shipment plan.
     * @param receipt The shipment receipt
     */
    public Shipment(String address, int cost, byte plan, String receipt){
        this.address = address;
        this.cost = cost;
        this.plan = plan;
        this.receipt = receipt;
    }//end constructor shipment

    /**
     * Method to get estimated date arrival
     * @param reference Date reference
     * @return a String of date estimated.
     */
    public String getEstimatedArrival(Date reference){
        Calendar cal =  Calendar.getInstance();
        cal.setTime(reference);
        if(plan==INSTANT.bit || plan==SAME_DAY.bit){
            cal.add(Calendar.DATE,0);
        }
        else if(plan==NEXT_DAY.bit){
            cal.add(Calendar.DATE,1);
        }
        else if(plan==REGULER.bit){
            cal.add(Calendar.DATE,2);
        }
        else if(plan==KARGO.bit){
            cal.add(Calendar.DATE,5);
        }
        return ESTIMATION_FORMAT.format(cal.getTime());
    }//end getEstimatedArrival

    /**
     * a Class to store bit of plans
     */
    public static class Plan{
        public final byte bit;
        private Plan(byte bit){
            this.bit = bit;
        }
    }//end Plan

    /**
     * Method to check if there is minimum 1 plan match the reference.
     * @param reference
     * @return true if there is match plan, otherwise false.
     */
    public boolean isDuration(Plan reference){
        if((this.plan & reference.bit) != 0){
            return true;
        }
        else{
            return false;
        }
    }//end isDuration

    /**
     * Method to check if there is bit in object that match the reference.
     * @param object object to compare
     * @param reference plan referance
     * @return true if there is match plan, otherwise false.
     */
    public static boolean isDuration(byte object, Plan reference){
        if((object & reference.bit) != 0){
            return true;
        }
        else{
            return false;
        }
    }//end isDuration
}
