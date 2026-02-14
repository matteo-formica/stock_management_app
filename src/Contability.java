import java.io.Serializable;
import java.util.ArrayList;

public class Contability implements Serializable {
    ArrayList<Invoice> incomingInvoices;
    ArrayList<Invoice> outgoingInvoices;
    private static final long serialVersionUID = 1L;

    public Contability() {
        incomingInvoices = new ArrayList<>();
        outgoingInvoices = new ArrayList<>();
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

    public double getExitsAmount() {
        double exitsAmount = 0;
        for (Invoice invoice : outgoingInvoices) {
            exitsAmount += invoice.getTotal();
        }
        return exitsAmount;
    }

    public double getIncomeAmount() {
        double incomeAmount = 0;
        for (Invoice invoice : incomingInvoices) {
            incomeAmount += invoice.getTotal();
        }
        return incomeAmount;
    }

    public double getVatAmount() {
        double TotalVat = 0;

        for (Invoice invoice : outgoingInvoices) {
            double invoiceTotalVat = invoice.getVat4() + invoice.getVat10() + invoice.getVat22();
            TotalVat += invoiceTotalVat;
        }

        for (Invoice invoice : incomingInvoices) {
            double invoiceTotalVat= invoice.getVat4() + invoice.getVat10() + invoice.getVat22();
            TotalVat -= invoiceTotalVat;
        }
        return TotalVat;
    }

    public String getStoreMoneyOverview(){
        double income = this.getIncomeAmount();
        double exits =  this.getExitsAmount();
        double vat = this.getVatAmount();
        return "Income: " + income + "€ | " + "Exits: " + exits + "€ | " + "Total Vat: " + vat;

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
    }
}
