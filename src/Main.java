
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        PriceList priceList = new PriceList();
        Scanner scan = new Scanner(System.in);
        Brand brand = PersistenceManager.loadData(priceList);
        if (brand == null) {
            brand = new Brand();
            System.out.println("Welcome to the Stock Management System!");
            System.out.println("It's your first time so we need to ask you some info..");
            System.out.print("\nWhat's your brand name? ");
            String name = scan.nextLine();
            brand.setBrandName(name);
            System.out.print("\nWhat's your brand description? ");
            String address = scan.nextLine();
            brand.setBrandDescription(address);
            System.out.print("\nChoose a password ");
            String password = scan.nextLine();
            brand.setBrandPassword(password);
            System.out.println("\nOkay! now let's create your Master manager account!");
            System.out.print("\nWhat's your name? ");
            String managerName = scan.nextLine();
            System.out.print("\nChoose a password: ");
            String managerPassword = scan.nextLine();
            brand.createManager(managerName, managerPassword, true);
        }else{
            System.out.println("Welcome back to " + brand.getBrandName() + " management system!");
            System.out.print("Please type your brand password: ");
            String password;
            do {
                password = scan.nextLine();
            } while (!(password.equals(brand.getBrandPassword())));
            System.out.println("Password is correct!");

        }
        boolean success = false;
        Session session = new Session();
        do{
            System.out.println("Manager login");
            System.out.print("Name: ");
            String name = scan.nextLine();
            System.out.print("Password: ");
            String password = scan.nextLine();
        for (Manager manager: brand.getManagers()) {
            if (name.equals(manager.getManagerName()) && password.equals(manager.getManagerPassword())){
                success = true;
                session.setManager(manager);
                break;
            }
        }
        }while(!success);
        boolean programContinue = true;
        while(programContinue) {
            boolean storeAccess = false;
            int storeSessionIndex = -1;
            boolean sessionContinue = true;
            while(sessionContinue) {
            if (session.manager.isMaster() && !storeAccess) {
                System.out.println("Menu");
                System.out.println("1. Create new Store");
                System.out.println("2. Create new Manager account");
                System.out.println("3. Create new Product");
                System.out.println("4. Delete Product");
                System.out.println("5. View Price list");
                System.out.println("6. View Stock");
                System.out.println("7. Access a store dashboard");
                System.out.println("8. View Money Overview");
                System.out.println("9. View Total Overview");
                System.out.println("10. Exit");
                System.out.print("\nAction: ");
                int action = Integer.parseInt(scan.nextLine());
                switch (action) {
                    case 1:
                        System.out.println("Store creation");
                        System.out.print("\nStore Name: ");
                        String storeName = scan.nextLine();
                        System.out.print("\nAddress: ");
                        String storeAddress = scan.nextLine();
                        System.out.print("\nPhone number: ");
                        int storePhone = Integer.parseInt(scan.nextLine());
                        brand.createStore(storeName, storeAddress, storePhone);
                        System.out.println("\nStore created successfully!");
                        break;
                    case 2:
                        System.out.println("Manager creation");
                        System.out.print("\nManager Name: ");
                        String managerName = scan.nextLine();
                        System.out.print("\nManager Password: ");
                        String managerPassword = scan.nextLine();
                        System.out.print("\nIt's a Master account? (true/false): ");
                        boolean master = Boolean.parseBoolean(scan.nextLine());
                        brand.createManager(managerName, managerPassword, master);
                        System.out.println("Manager created successfully!");
                        break;
                    case 3:
                        System.out.println("Add a Product");
                        System.out.print("Enter Product ID: ");
                        int productID = Integer.parseInt(scan.nextLine());
                        System.out.print("\nEnter Product Name: ");
                        String productName = scan.nextLine();
                        System.out.print("\nEnter Product Size: ");
                        String productSize = scan.nextLine();
                        System.out.print("\nEnter Product Package Type: ");
                        String productPackageType = scan.nextLine();
                        System.out.print("\nEnter Product Type: ");
                        String productType = scan.nextLine();
                        System.out.print("\nEnter Product VAT: ");
                        int productVAT = Integer.parseInt(scan.nextLine());
                        System.out.print("\nEnter Product Buy Price: ");
                        double buyPrice = Double.parseDouble(scan.nextLine());
                        System.out.print("\nEnter Product B2B Price: ");
                        double b2bPrice = Double.parseDouble(scan.nextLine());
                        System.out.print("Enter Product B2C Price: ");
                        double b2cPrice = Double.parseDouble(scan.nextLine());
                        priceList.addProduct(productID, productName, productSize, productPackageType, productType, productVAT, buyPrice, b2bPrice, b2cPrice);
                        System.out.println("Product ID: " + productID + " Added Successfully!");
                        System.out.println(priceList.getProduct(productID));
                        break;
                    case 4:
                        System.out.print("Delete a Product");
                        System.out.print("Enter Product ID: ");
                        int id = Integer.parseInt(scan.nextLine());
                        priceList.deleteProduct(id);
                        System.out.println("Product ID: " + id + " Deleted Successfully!");
                        break;
                    case 5:
                        System.out.println("View Price list");
                        System.out.print(priceList.toString());
                        break;
                    case 6:
                        break;
                    case 7:
                        storeAccess = true;
                        break;
                    case 8:
                        System.out.println("View Money Overview");
                        break;
                    case 9:
                        System.out.println("View Total Overview");
                        break;
                    case 10:
                        sessionContinue = false;
                        programContinue = false;
                        System.out.println("Saving...");
                        PersistenceManager.saveData(priceList.productsPriceList, brand);
                        System.out.println("Exit");
                        break;

                }
            }else {
                System.out.println("Enter a store name: ");
                String sessionStoreName = scan.nextLine();
                for (Store store : brand.getStores()) {
                    storeSessionIndex++;
                    if (sessionStoreName.equals(store.getStoreName())) {
                        break;
                    }
                    else{
                        System.out.println("Store not found!");
                        storeSessionIndex = -1;
                        break;
                    }
                }
                while(sessionContinue) {
                    System.out.println("Welcome back to " + brand.getStores().get(storeSessionIndex).getStoreName() + " Store Dashboard!");
                    System.out.println("\nMenu");
                    System.out.println("1. View Store Information");
                    System.out.println("2. View Price list");
                    System.out.println("3. New Invoice");
                    System.out.println("4. View Stock");
                    System.out.println("5. View Money Overview");
                    System.out.println("6. View Total Overview");
                    System.out.println("7. Exit");
                    System.out.print("Action: ");
                    switch (Integer.parseInt(scan.nextLine())) {
                        case 1:
                            System.out.println("View Store Information");
                            System.out.println((brand.getStores().get(storeSessionIndex)));
                            break;
                        case 2:
                            System.out.println("View Price list");
                            System.out.print(priceList.toString());
                            break;
                        case 3:
                            System.out.println("New Invoice");
                            System.out.print("Enter Invoice ID: ");
                            String invoiceID = scan.nextLine();
                            System.out.print("\nCustomer Type: ");
                            String customerType = scan.nextLine();
                            System.out.print("\nInvoice Type (Incoming/Outgoing): ");
                            String invoiceType = scan.nextLine();
                            Invoice.createInvoice(invoiceID, customerType, invoiceType, (brand.getStores().get(storeSessionIndex).storeStock), priceList, (brand.getStores().get(storeSessionIndex).storeContability));
                            break;
                        case 4:
                            System.out.println("View Stock");
                            System.out.print((brand.getStores().get(storeSessionIndex)).storeStock.toString());
                            break;
                        case 5:
                            System.out.println("View Money Overview");
                            System.out.print(((brand.getStores().get(storeSessionIndex)).storeContability).getStoreMoneyOverview());
                            break;
                        case 6:
                            (brand.getStores().get(storeSessionIndex)).storeContability.getStoreTotalOverview();
                            break;
                        case 7:
                            if (session.manager.isMaster()) {
                                sessionContinue = false;
                                storeAccess = false;
                                storeSessionIndex = 0;
                            } else {
                                sessionContinue = false;
                                programContinue = false;
                                System.out.println("Saving...");
                                PersistenceManager.saveData(priceList.productsPriceList, brand);
                                System.out.println("Exit");
                            }
                    }
                }
            }
            }
        }


    }
}