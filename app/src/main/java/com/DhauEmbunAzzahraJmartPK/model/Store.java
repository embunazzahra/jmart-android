package com.DhauEmbunAzzahraJmartPK.model;

/**
 * This is class for representing a store
 *
 * @author Dhau' Embun Azzahra
 * */
public class Store {
    public String address;
    public String name;
    public String phoneNumber;

    /**
     * Creates a store.
     * @param name Store name
     * @param address Store address
     * @param phoneNumber Store phone number
     */
    public Store(String address, String name, String phoneNumber){
        this.address = address;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
