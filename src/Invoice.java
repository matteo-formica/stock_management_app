
import java.io.Serializable;
import java.text.ParseException;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Invoice implements Serializable{
    public String invoiceNumber;
    public String invoiceType;
    public LocalDate invoiceDate;
    public String customerType;
    public Map<Product, Integer> invoiceProducts;
    public double invoiceTotal;
    public double vat4 = 0;
    public double vat10 = 0;
    public double vat22 = 0;
    public double totalVAT = 0;
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final long serialVersionUID = 1L;

    public Invoice(String invoiceNumber,String customerType, String invoiceType) {
        this.invoiceNumber = invoiceNumber;
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
        return String.format("Date: " + this.invoiceDate.format(formatter));
    }
    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
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

    public static int createInvoice(String id,String customerType, String invoiceType, Stock stock, PriceList  priceList, Accounting storeAccounting) {
        Invoice invoice = new Invoice(id, customerType, invoiceType);
        int incOrOut = 0;
        boolean add = true;
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter Invoice Date (dd-MM-yyyy): ");
        boolean valid = false;
        while(!valid) {
            try {
                LocalDate invoiceDate = LocalDate.parse(scan.nextLine(), formatter);
                invoice.setInvoiceDate(invoiceDate);
                valid = true;
            }catch(DateTimeParseException e) {
                valid = false;
                System.out.println("Invalid format");

            }
        }
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
                    if (product.getId() == productID) {
                        product1 = product;
                        System.out.print("Lot Date(dd-MM-yyyy): ");
                        boolean valid1 = false;
                        while (!valid1) {
                            try {
                                LocalDate lotDate = LocalDate.parse(scan.nextLine(), formatter);
                                invoice.addProductToIncomingInvoice(lotId, lotDate, product1, amount, stock);
                                valid1 = true;
                            } catch (DateTimeParseException e) {
                                valid1 = false;
                                System.out.println("Invalid date format");

                            }
                        }

                        }else{
                            System.out.println("Invalid product ID");

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
                                storeAccounting.incrementProductQuantity(product1.getName(), amount);
                                storeAccounting.incrementProductVolume(product1.getType(), (product1.getSize()*amount));
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
                invoice.invoiceTotal(storeAccounting);
                if(invoice.invoiceType.equals("Incoming")){
                    storeAccounting.addIncomingInvoice(invoice);
                    storeAccounting.incrementOutcome(invoice.getTotal());
                    System.out.println("Invoice created successfully");
                    System.out.println("Invoice Summary: ");
                    System.out.println(invoice);
                    incOrOut = 1;
                }
                else{
                    storeAccounting.addOutgoingInvoice(invoice);
                    storeAccounting.incrementIncome(invoice.getTotal());
                    System.out.println("Invoice created successfully");
                    System.out.println("Invoice Summary: ");
                    System.out.println(invoice);
                }

            }
        }
        return incOrOut;
    }

    public void addProductToIncomingInvoice(String id, LocalDate date, Product product, int amount, Stock stock) {
        this.invoiceProducts.put(product, amount);
        if (this.invoiceType.equals("Incoming")){
            Lot lot = new Lot(id,date, product, amount);
            stock.addLotToStock(lot);
        }
    }

    public void addProductToOutgoingInvoice(Product product, int amount) {
        this.invoiceProducts.put(product, amount);
    }

    public void invoiceTotal(Accounting storeAccounting) {
        double total = 0;
        for (Product product : this.invoiceProducts.keySet()) {
            if(this.invoiceType.equals("Incoming")){
                double productPrice = product.getBuyPrice();
                double productVat = (product.getBuyPrice() * product.getVat() / 100) * this.invoiceProducts.get(product);
                switch (product.getVat()){
                    case 4:
                        this.vat4 += productVat;
                        storeAccounting.storePaidVAT4 += productVat;
                        break;
                    case 10:
                        this.vat10 += productVat;
                        storeAccounting.storePaidVAT10 += productVat;
                        break;
                    case 22:
                        this.vat22 += productVat;
                        storeAccounting.storePaidVAT22 += productVat;
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
                double productVat = (product.getB2bPrice() * product.getVat() / 100) * this.invoiceProducts.get(product);
                switch (product.getVat()){
                    case 4:
                        this.vat4 += productVat;
                        storeAccounting.storeIncomeVAT4 += productVat;
                        break;
                    case 10:
                         this.vat10 += productVat;
                        storeAccounting.storeIncomeVAT10 += productVat;
                        break;
                    case 22:
                        this.vat22 += productVat;
                        storeAccounting.storeIncomeVAT10 += productVat;
                }
                double productTotal = (productPrice+productVat) * this.invoiceProducts.get(product);
                total += productTotal;
            }
        }
        this.totalVAT = this.vat4 + this.vat10 + this.vat22;
        storeAccounting.updateVAT();
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
        str += "Invoice N°: " + invoiceNumber + "\n" + invoiceDate.format(formatter) + "\n";
        for (Product product : this.invoiceProducts.keySet()) {
            str = str + product.toString() + " | " +  this.invoiceProducts.get(product) + "\n";
        }
        str = str + "\n VAT 4: " + this.vat4 + "\n VAT 10: " + this.vat10 + "\n VAT 22: " + this.vat22 + "\n Total VAT: " + this.totalVAT +"\nTotal Price: " + this.invoiceTotal;
        return str;
    }
}
