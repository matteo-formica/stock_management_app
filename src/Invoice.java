
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Invoice implements Serializable{
    public String invoiceNumber;
    public String invoiceType;
    public Calendar invoiceDate;
    public String customerType;
    public Map<Product, Integer> invoiceProducts;
    public double invoiceTotal;
    public double vat4 = 0;
    public double vat10 = 0;
    public double vat22 = 0;
    public double totalVAT = 0;
    private static final long serialVersionUID = 1L;

    public Invoice(String invoiceNumber,String customerType, String invoiceType) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = Calendar.getInstance();
        this.customerType = customerType;
        this.invoiceType = invoiceType;
        this.invoiceProducts = new HashMap<>();
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getInvoiceDate() {
        return "Date: " + invoiceDate.get(Calendar.DATE);
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public Map<Product, Integer> getInvoice() {
        return invoiceProducts;
    }

    public static void createInvoice(String id,String customerType, String invoiceType, Stock stock, PriceList  priceList, Contability contability) {
        Invoice invoice = new Invoice(id, customerType, invoiceType);
        boolean add = true;
        Scanner scan = new Scanner(System.in);
        while(add) {
            if (invoice.invoiceType.equals("Incoming")) {
                System.out.print("Lot id: ");
                String lotId = scan.nextLine();
                System.out.print("Product ID: ");
                int productID = Integer.parseInt(scan.nextLine());
                Product product1;
                System.out.print("Product amount: ");
                int amount = Integer.parseInt(scan.nextLine());
                for (Product product : priceList.productsPriceList){
                    if (product.getId() == productID){
                       product1 =  product;
                        System.out.print("Lot Date(dd-MM-yyyy): ");
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                        try {
                            Date date = sdf.parse(scan.nextLine());
                            Calendar lotDate = Calendar.getInstance();
                            lotDate.setTime(date);
                            invoice.addProductToIncomingInvoice(lotId, lotDate, product1, amount, stock);
                        }catch(ParseException e) {
                            System.out.println("Invalid date format");
                        }

                    }else{
                        System.out.println("Invalid product name");
                    }
                }
            }
            else {
                System.out.print("Lot id: ");
                String lotId = scan.nextLine();
                System.out.print("Product ID: ");
                int productId = Integer.parseInt(scan.nextLine());
                System.out.print("Product amount: ");
                int amount = Integer.parseInt(scan.nextLine());
                for (Lot lot: stock.stock.keySet()){
                    if(lot.getId().equals(lotId) && stock.getLotFromStock(lot)){
                        for(Product product1: lot.products.keySet()){
                            if(product1.getId() == productId && !(lot.getProduct() == null)){
                                lot.subtractProducts(product1, amount);
                                invoice.addProductToOutgoingInvoice(product1, amount);
                            }
                        }
                    }
                }
            }
            stock.checkStock();
            System.out.print("Add another product (y/n): ");
            String choice = scan.nextLine();
            if (choice.equals("n")) {
                add = false;
                invoice.invoiceTotal();
                if(invoice.invoiceType.equals("Incoming")){
                    contability.addIncomingInvoice(invoice);
                }
                else{
                    contability.addOutgoingInvoice(invoice);
                }
                System.out.println("Invoice created successfully");
                System.out.println("Invoice Summary: ");
                System.out.println(invoice);
            }
        }
    }

    public void addProductToIncomingInvoice(String id, Calendar date, Product product, int amount, Stock stock) {
        this.invoiceProducts.put(product, amount);
        if (this.invoiceType.equals("Incoming")){
            Lot lot = new Lot(id,date, product, amount);
            stock.addLotToStock(lot);
        }
    }

    public void addProductToOutgoingInvoice(Product product, int amount) {
        this.invoiceProducts.put(product, amount);
    }

    public void invoiceTotal() {
        double total = 0;
        for (Product product : this.invoiceProducts.keySet()) {
            if(this.invoiceType.equals("Incoming")){
                double productPrice = product.getBuyPrice();
                double productVat = product.getBuyPrice() * product.getVat() / 100;
                switch (product.getVat()){
                    case 4:
                        this.vat4 += productVat;
                        break;
                    case 10:
                        this.vat10 += productVat;
                        break;
                    case 22:
                        this.vat22 += productVat;
                }
                double productTotal = (productPrice+productVat) * this.invoiceProducts.get(product);
                total += productTotal;
            }
            else if (this.customerType.equals("B2C")) {
                double productPrice = product.getB2cPrice();
                double productTotal = productPrice * this.invoiceProducts.get(product);
                total += productTotal;
            } else {
                double productPrice = product.getB2bPrice();
                double productVat = product.getB2bPrice() * product.getVat() / 100;
                switch (product.getVat()){
                    case 4:
                        this.vat4 += productVat;
                        break;
                    case 10:
                         this.vat10 += productVat;
                        break;
                    case 22:
                        this.vat22 += productVat;
                }
                double productTotal = (productPrice+productVat) * this.invoiceProducts.get(product);
                total += productTotal;
            }
        }
        this.totalVAT = this.vat4 + this.vat10 + this.vat22;
        this.invoiceTotal = total;
    }

    public double getTotal() {
        return this.invoiceTotal;
    }

    public double getVat4() {
        return vat4;
    }

    public double getVat10() {
        return vat10;
    }

    public double getVat22() {
        return vat22;
    }

    public String toString(){
        String str = "";
        for (Product product : this.invoiceProducts.keySet()) {
            str = str + product.toString() + " | " +  this.invoiceProducts.get(product) + "\n";
        }
        str = str + "\n VAT 4: " + this.vat4 + "\n VAT 10: " + this.vat10 + "\n VAT 22: " + this.vat22 + "\n Total VAT: " + this.totalVAT +"\nTotal Price: " + this.invoiceTotal;
        return str;
    }
}
