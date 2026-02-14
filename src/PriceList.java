import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;


public class PriceList implements Serializable {
    public ArrayList<Product> productsPriceList;
    private static final long serialVersionUID = 1L;

    public PriceList() {
        productsPriceList = new ArrayList<>();
    }

    public void addProduct(int id, String name, String size, String packageType, String type, int vat,double buyPrice, double b2bPrice, double b2cPrice) {
        boolean exists = false;
        for (Product p : this.productsPriceList) {
            if (p.getId() == id && p.getName().equals(name)) {
                exists = true;
                break;
            }
        }
        if (exists) {
            System.out.println("This product already exists!");
        } else {
            Product product = new Product(id, name, size, packageType, type, vat, buyPrice, b2bPrice, b2cPrice);
            this.productsPriceList.add(product);
        }
    }

    public void modifyProduct(int id){
        Scanner scan = new Scanner(System.in);
        for (Product product1: this.productsPriceList){
            if (product1.getId() == id){
                System.out.println("Actions: Set B2b Price | Set B2c Price");
                String choice = scan.next();
                if (choice.equalsIgnoreCase("Set B2b Price")){
                    System.out.println("Actual B2B Price: " + product1.getB2bPrice());
                    System.out.print("New B2B Price: ");
                    product1.setB2bPrice(Double.parseDouble(scan.nextLine()));
                }else{
                    System.out.println("Actual B2c Price: " + product1.getB2cPrice());
                    System.out.print("New B2c Price: ");
                    product1.setB2cPrice(Double.parseDouble(scan.nextLine()));
                }

            }
        }
    }

    public Product getProduct(int id){
        Product product = null;
        for (Product product1: this.productsPriceList){
            if (product1.getId() == id){
                product = product1;
            }
        }
        return product;
    }

    public String toString() {
        String str = "";
        for (Product product : this.productsPriceList) {
            str += product.toString() + "\n";
        }
        return str;
    }

    public void deleteProduct(int id) {
        for (Product product1: this.productsPriceList) {
            if (product1.getId() == id) {
                this.productsPriceList.remove(product1);
            }
        }

    }
}
