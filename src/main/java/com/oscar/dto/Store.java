package com.oscar.dto;

public class Store {

    private int id;
    private String name;
    private  String address;
    private int openingYear;
    private int marketValue;

    public Store(){

    }

    public Store(int id, String name, String address, int openingYear, int marketValue){
        this.id=id;
        this.name=name;
        this.address=address;
        this.openingYear=openingYear;
        this.marketValue=marketValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getOpeningYear() {
        return openingYear;
    }

    public void setOpeningYear(int openingYear) {
        this.openingYear = openingYear;
    }

    public int getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(int marketValue) {
        this.marketValue = marketValue;
    }
}
