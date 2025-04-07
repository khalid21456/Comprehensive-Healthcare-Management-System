package com.example.doctorappointments.model;

public class Test {
    private int IDTest;
    private String Name;
    private double MaxVal;
    private double MinVal;
    private double Price;

    public Test(int IDTest, String name, double maxVal, double minVal, double price) {
        this.IDTest = IDTest;
        Name = name;
        MaxVal = maxVal;
        MinVal = minVal;
        Price = price;
    }

    // Getters and Setters
    public int getIDTest() {
        return IDTest;
    }

    public void setIDTest(int IDTest) {
        this.IDTest = IDTest;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public double getMaxVal() {
        return MaxVal;
    }

    public void setMaxVal(double MaxVal) {
        this.MaxVal = MaxVal;
    }

    public double getMinVal() {
        return MinVal;
    }

    public void setMinVal(double MinVal) {
        this.MinVal = MinVal;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }
}