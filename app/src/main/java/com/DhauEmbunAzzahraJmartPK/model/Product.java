package com.DhauEmbunAzzahraJmartPK.model;

/**
 * This is class for representing a product
 *
 * @author Dhau' Embun Azzahra
 * */
public class Product extends Serializable{
    public int accountId;
    public ProductCategory category;
    public boolean conditionUsed;
    public double discount;
    public String name;
    public double price;
    public byte shipmentPlans;
    public int weight;

    /**
     * Creates a product.
     * @param accountId The account id of user creating this product.
     * @param name Product name.
     * @param weight Product weight.
     * @param conditionUsed Product condition. False if new, true if used.
     * @param price Product price.
     * @param discount Product discount.
     * @param category Product category
     * @param shipmentPlans Product shipment plan
     */
    public Product(int accountId, String name, int weight, boolean conditionUsed, double price, double discount, ProductCategory category, byte shipmentPlans){
        this.accountId = accountId;
        this.name = name;
        this.weight = weight;
        this.conditionUsed = conditionUsed;
        this.price = price;
        this.discount = discount;
        this.category = category;
        this.shipmentPlans = shipmentPlans;
    }

    /**
     * use for the listview of products information
     * @return String name and price of the product.
     */
    @Override
    public String toString(){
        return this.name+"\n\n"+this.price;
    }
}
