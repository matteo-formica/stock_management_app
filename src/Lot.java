import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Lot implements Serializable {
    String id;
    LocalDate date;
    Map<Product, Integer> products;
    private static final long serialVersionUID = 1L;


    public Lot(String id, LocalDate date, Product product, int quantity) {
        this.id = id;
        this.date = date;
        products = new HashMap<>();
        this.products.put(product, quantity);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getProduct() {
        Product key = products.keySet().stream().findFirst().orElse(null);
        if (products.get(key) == 0){
            return null;
        }
        else {
            return "Lot ID: " + this.id + " Product ID: " + key.getId() + " Quantity: " + products.get(key);
        }
    }

    public void addProducts(Product product, int quantity) {
        int total = this.products.get(product) + quantity;
        this.products.put(product, total);
    }

    public void subtractProducts(Product product, int quantity) {
        int total = this.products.get(product) - quantity;
        this.products.put(product, total);
    }

    public String toString(){
        String str = "";
        str += "Lot ID: " + this.id + " | ";
        str += "Date: " + this.date.format(Invoice.formatter) + " | ";
        for (Product product : this.products.keySet()) {
            str += "Product ID: " + product.getId() + " | ";
            str += "Quantity: " + this.products.get(product);
        }
        return str;
    }

}
