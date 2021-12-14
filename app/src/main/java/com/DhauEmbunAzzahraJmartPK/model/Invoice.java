package com.DhauEmbunAzzahraJmartPK.model;

import java.util.Date;

/**
 * This is class for representing an invoice
 *
 * @author Dhau' Embun Azzahra
 * */
public class Invoice extends Serializable{
    public int buyerId;
    public int productId;
    public int complaintId = -1;
    public Rating rating = Rating.NONE;
    public Status status;

    /**
     * Status represents the order status.
     */
    public enum Status{
        WAITING_CONFIRMATION,
        CANCELLED,
        DELIVERED,
        ON_PROGRESS,
        ON_DELIVERY,
        COMPLAINT,
        FINISHED,
        FAILED;
    }

    /**
     * Rating represents the order rating from buyer.
     */
    public enum Rating{
        NONE,
        BAD,
        NEUTRAL,
        GOOD;
    }

    /**
     * Creates invoice.
     * @param buyerId The buyer id
     * @param productId The product id
     */
    protected Invoice(int buyerId, int productId){
        this.buyerId = buyerId;
        this.productId = productId;
    }

    /**
     * Method to get the total pay
     * @param product The product
     * @return the total pay
     */
    public double getTotalPay(Product product) {
        return product.price;
    }
}
