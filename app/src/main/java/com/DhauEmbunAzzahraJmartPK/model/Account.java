package com.DhauEmbunAzzahraJmartPK.model;

/**
 * This is class for representing an account
 *
 * @author Dhau' Embun Azzahra
 * */
public class Account extends Serializable{
    public double balance;
    public String email;
    public String name;
    public String password;
    public Store store;

    /** Creates an account with the specified information.
     * @param name The account's name.
     * @param email The account's email address.
     * @param password The account's password.
     * @param balance The account's balance.
     * @param store The account's store.
     * @param id The account id
     */
    public Account(double balance, String email, String name,
                   String password, Store store, int id){
        this.id = id;
        this.balance = balance;
        this.email = email;
        this.name = name;
        this.password = password;
        this.store = store;
    }

    @Override
    public String toString() {
        return ""+balance;
    }
}
