package model;

import java.sql.Date;
import java.sql.Timestamp;

public class Vehicle {

    private int id;
    private String brand;
    private String model;
    private Date productionYear;
    private String registrationNumber;
    private Timestamp nextReview;

    public Vehicle(String brand, String model, Date productionYear, String registrationNumber, Timestamp nextReview) {
        this.brand = brand;
        this.model = model;
        this.productionYear = productionYear;
        this.registrationNumber = registrationNumber;
        this.nextReview = nextReview;
    }

    public Vehicle() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Date getProductionYear() {
        return productionYear;
    }

    public void setProductionYear(Date productionYear) {
        this.productionYear = productionYear;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Timestamp getNextReview() {
        return nextReview;
    }

    public void setNextReview(Timestamp nextReview) {
        this.nextReview = nextReview;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", productionYear=" + productionYear +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", nextReview=" + nextReview +
                '}';
    }
}
