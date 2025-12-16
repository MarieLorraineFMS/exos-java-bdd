package fr.fms.entity;

public class Article {
    public int id;
    public String description;
    public String brand;
    public double unitaryPrice;

    public Article(int id, String description, String brand, double unitaryPrice) {
        this.id = id;
        this.description = description;
        this.brand = brand;
        this.unitaryPrice = unitaryPrice;
    }

    public Article(String description, String brand, double unitaryPrice) {
        this(0, description, brand, unitaryPrice);
    }

    @Override
    public String toString() {
        return id + " | " + description + " | " + brand + " | " + unitaryPrice;
    }
}
