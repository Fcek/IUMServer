package core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class Product {

    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("manufacturer")
    private String manufacturer;
    @JsonProperty("price")
    private float price;
    @JsonProperty("amount")
    private int amount;
    @JsonProperty("created")
    private Date created;
    @JsonProperty("updated")
    private Date updated;
    @JsonProperty("created")
    public Date getCreated() {
        return created;
    }
    @JsonProperty("created")
    public void setCreated(Date created) {
        this.created = created;
    }
    @JsonProperty("updated")
    public Date getUpdated() {
        return updated;
    }
    @JsonProperty("updated")
    public void setUpdated(Date updated) {
        this.updated = updated;
    }
    @JsonProperty("id")
    public long getId() {
        return id;
    }
    @JsonProperty("id")
    public void setId(long id) {
        this.id = id;
    }
    @JsonProperty("name")
    public String getName() {
        return name;
    }
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }
    @JsonProperty("manufacturer")
    public String getManufacturer() {
        return manufacturer;
    }
    @JsonProperty("manufacturer")
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    @JsonProperty("price")
    public float getPrice() {
        return price;
    }
    @JsonProperty("price")
    public void setPrice(float price) {
        this.price = price;
    }
    @JsonProperty("amount")
    public int getAmount() {
        return amount;
    }
    @JsonProperty("amount")
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
