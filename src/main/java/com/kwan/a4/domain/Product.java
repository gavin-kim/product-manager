package com.kwan.a4.domain;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = -7342342351452342342L;

    private String code;
    private String description;
    private double price;

    public Product() {
    }

    public Product(String code, String description, double price) {
        this.code = code;
        this.description = description;
        this.price = price;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArtistName() {

        return description.substring(0, description.indexOf(" - "));
    }

    public String getAlbumName() {

        return description.substring(description.indexOf(" - ") + 3);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 17;

        return result * prime + code.hashCode();
    }

    @Override
    public boolean equals(Object object) {
        return object == this || object instanceof Product &&
            ((Product) object).getCode().equals(code);
    }

    @Override
    public String toString() {
        return String.format("Code: %s, Description: %s, price %.2f",
            code, description, price);
    }

    private void readObject(ObjectInputStream inputStream)
        throws ClassNotFoundException, IOException {
        System.out.println("read");
        inputStream.defaultReadObject();
        price = 999;

    }

    private void writeObject(ObjectOutputStream outputStream)
        throws ClassNotFoundException, IOException {
        System.out.println("write");
        outputStream.defaultWriteObject();

    }

    private Object readResolve() {
        System.out.println("readResolve" + this.price);
        return null;
    }
}


