import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String size;
    private String packageType;
    private String type;
    private int vat;
    private double buyPrice;
    private double b2bPrice;
    private double b2cPrice;
    private static final long serialVersionUID = 1L;

    public Product(int id, String name, String size, String packageType, String type, int vat,double buyPrice, double b2bPrice, double b2cPrice) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.packageType = packageType;
        this.type = type;
        this.vat = vat;
        this.buyPrice = buyPrice;
        this.b2bPrice = b2bPrice;
        this.b2cPrice = b2cPrice;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVat() {
        return vat;
    }

    public void setVat(int vat) {
        this.vat = vat;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getB2bPrice() {
        return b2bPrice;
    }

    public void setB2bPrice(double b2bPrice) {
        this.b2bPrice = b2bPrice;
    }

    public double getB2cPrice() {
        return b2cPrice;
    }

    public void setB2cPrice(double b2cPrice) {
        this.b2cPrice = b2cPrice;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + "Size: " + size + ", PackageType: " + packageType + ", Type: " + type + "VAT: " + vat +"%"+ "B2B Price: " + b2bPrice + "€ " + "B2C Price: " + b2cPrice+ "€";
    }
}
