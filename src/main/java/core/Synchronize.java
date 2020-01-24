package core;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Synchronize {

    @JsonProperty("products")
    private List<Product> productList;
    @JsonProperty("deleted")
    private List<Integer> deleted;
    @JsonProperty("products")
    public List<Product> getProductList() {
        return productList;
    }
    @JsonProperty("products")
    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
    @JsonProperty("deleted")
    public List<Integer> getDeleted() {
        return deleted;
    }
    @JsonProperty("deleted")
    public void setDeleted(List<Integer> deleted) {
        this.deleted = deleted;
    }
    @JsonProperty("ssid")
    private int ssid;
    @JsonProperty("ssid")
    public int getSsid() {
        return ssid;
    }
    @JsonProperty("ssid")
    public void setSsid(int ssid) {
        this.ssid = ssid;
    }
}
