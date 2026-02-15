import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Accounting implements Serializable {
    public ArrayList<Invoice> incomingInvoices;
    public ArrayList<Invoice> outgoingInvoices;
    public HashMap<String, Integer> storeProductQuantity;
    public HashMap<String, Integer> storeProductVolume;
    public double storeIncome;
    public double storeOutcome;
    public double storePaidVAT4;
    public double storePaidVAT10;
    public double storePaidVAT22;
    public double storePaidTotalVAT;
    public double storeIncomeVAT4;
    public double storeIncomeVAT10;
    public double storeIncomeVAT22;
    public double storeIncomeTotalVAT;
    private static final long serialVersionUID = 1L;

    public Accounting() {
        this.incomingInvoices = new ArrayList<>();
        this.outgoingInvoices = new ArrayList<>();
        this.storeProductQuantity = new HashMap<>();
        this.storeProductVolume = new HashMap<>();
        this.storeIncome = 0.0;
        this.storeOutcome = 0.0;
        this.storePaidVAT4 = 0.0;
        this.storePaidVAT10 = 0.0;
        this.storePaidVAT22 = 0.0;
        this.storePaidTotalVAT = 0.0;
        this.storeIncomeVAT4 = 0.0;
        this.storeIncomeVAT10 = 0.0;
        this.storeIncomeVAT22 = 0.0;
        this.storeIncomeTotalVAT = 0.0;
    }

    public ArrayList<Invoice> getIncomingInvoices() {
        return incomingInvoices;
    }

    public void addIncomingInvoice(Invoice invoice) {
        this.incomingInvoices.add(invoice);
    }

    public ArrayList<Invoice> getOutgoingInvoices() {
        return outgoingInvoices;
    }

    public void addOutgoingInvoice(Invoice invoice) {
        this.outgoingInvoices.add(invoice);
    }

    public void incrementIncome(double amount) {
        this.storeIncome += amount;
    }

    public void incrementOutcome(double amount) {
        this.storeOutcome += amount;
    }

    public void updateVAT(){
        this.storeIncomeTotalVAT = this.storeIncomeVAT4 + this.storeIncomeVAT10 +  this.storeIncomeVAT22;
        this.storePaidTotalVAT = this.storePaidVAT4 + this.storePaidVAT10 + this.storePaidVAT22;
    }

    public void incrementProductQuantity(String product, int quantity) {
        int total = this.storeProductQuantity.getOrDefault(product, 0) + quantity;
        this.storeProductQuantity.put(product, total);
    }

    public void incrementProductVolume(String productType, int quantity) {
        int total = this.storeProductVolume.getOrDefault(productType, 0) + quantity;
        this.storeProductVolume.put(productType, total);
    }

    public String getStoreMoneyOverview(){
        return "Income: " + this.storeIncome + "€ | " + "Exits: " + this.storeOutcome + "€ | " + "Total Paid VAT: " + this.storePaidTotalVAT + " | Total Income VAT: " + this.storeIncomeTotalVAT;
    }

    public void getStoreTotalOverview(){
        String moneyOverview = this.getStoreMoneyOverview();
        System.out.println("Money Overview: \n" + moneyOverview);
        System.out.println("Outgoing Invoices: ");
        for (Invoice invoice: this.outgoingInvoices) {
            System.out.println("Invoice ID: " + invoice.getInvoiceNumber() + " | " +  invoice.getInvoiceDate()+ " | Total: " +  invoice.getTotal());
        }
        System.out.println("Incoming Invoices: ");
        for (Invoice invoice: this.incomingInvoices) {
            System.out.println("Invoice ID: " + invoice.getInvoiceNumber() + " | " + invoice.getInvoiceDate()+ " | Total: " +  invoice.getTotal());
        }
        System.out.println("Store Product Quantity: ");
        for(String productName: this.storeProductQuantity.keySet()){
            System.out.println("Product Type: " + productName + " | " +  this.storeProductQuantity.get(productName));
        }
        System.out.println("Store Product Volume: ");
        for(String productType: this.storeProductVolume.keySet()){
            System.out.println("Product Type: " + productType + " | " +  this.storeProductVolume.get(productType));
        }
    }
}
