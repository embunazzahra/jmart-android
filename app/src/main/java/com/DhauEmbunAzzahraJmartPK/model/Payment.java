package com.DhauEmbunAzzahraJmartPK.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * This is class for representing a payment
 *
 * @author Dhau' Embun Azzahra
 * */
public class Payment extends Invoice{
    public int productCount;
    public Shipment shipment;
    /**
     * Store all the record of the order track in the payment.
     */
    public ArrayList<Record> history = new ArrayList<Record>();

    /**
     * Creates payment.
     * @param buyerId The buyer id.
     * @param productId The product id.
     * @param productCount The products quantity.
     * @param shipment The order shipment
     */
    public Payment(int buyerId, int productId, int productCount, Shipment shipment){
        super(buyerId,productId);
        this.productCount = productCount;
        this.shipment = shipment;
    }

    /**
     * Method to get total pay from product price, quantity, discount, and shipment cost.
     * @param product The product
     * @return total pay from product price, quantity, discount, and shipment cost.
     */
    @Override
    public double getTotalPay(Product product) {
        return product.price*((100.0-product.discount)/100)*productCount + shipment.cost;
    }

    /**
     * This is a class representing an order history record.
     */
    public static class Record{
        public Status status;
        public final Date date = null;
        public String message;

        /**
         * Creates a record of the order history.
         * @param status status of order
         * @param message the message
         */
        public Record(Status status, String message){
            this.status = status;
            this.message = message;
        }
    }
}
