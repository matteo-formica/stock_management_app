import java.io.Serializable;
import java.util.HashMap;

public class BrandAccounting implements Serializable {
    public HashMap<String, Double> storesIncome;
    public HashMap<String, Double> storesOutcome;
    public HashMap<String, Integer> brandProductQuantity;
    public HashMap<String, Integer> brandProductVolume;
    public double brandPaidVAT4;
    public double brandPaidVAT10;
    public double brandPaidVAT22;
    public double brandPaidTotalVAT;
    public double brandIncomeVAT4;
    public double brandIncomeVAT10;
    public double brandIncomeVAT22;
    public double brandIncomeTotalVAT;


    public BrandAccounting()
    {
        this.storesIncome = new HashMap<>();
        this.storesOutcome = new HashMap<>();
        this.brandProductQuantity = new HashMap<>();
        this.brandProductVolume = new HashMap<>();
        this.brandPaidVAT4 = 0;
        this.brandPaidVAT10 = 0;
        this.brandPaidVAT22 = 0;
        this.brandPaidTotalVAT = 0;
        this.brandIncomeVAT4 = 0;
        this.brandIncomeVAT10 = 0;
        this.brandIncomeVAT22 = 0;
        this.brandIncomeTotalVAT = 0;
    }

    public void getStoresIncome() {
        for(String storeName : storesIncome.keySet()) {
            System.out.println("Store name: " + storeName + " | Total income: " + storesIncome.get(storeName));
        }
    }

    public void addStoresIncome(String storeName, double storeIncome) {
        storesIncome.put(storeName, storeIncome);
    }

    public void getStoreOutcome() {
        for(String storeName : storesOutcome.keySet()) {
            System.out.println("Store name: " + storeName + " | Total outcome: " + storesOutcome.get(storeName));
        }
    }
    public void addStoresOutcome(String storeName, double storeOutcome) {
        storesOutcome.put(storeName, storeOutcome);
    }

    public void incrementProductQuantity(Store store) {
        for(String product: store.storeAccounting.storeProductQuantity.keySet()) {
            int total = this.brandProductQuantity.getOrDefault(product, 0) + store.storeAccounting.storeProductQuantity.get(product);
            this.brandProductQuantity.put(product, total);
        }
    }

    public void incrementProductVolume(Store store) {
        for(String productType: store.storeAccounting.storeProductVolume.keySet()) {
            int total = this.brandProductVolume.getOrDefault(productType, 0) + store.storeAccounting.storeProductVolume.get(productType);
            this.brandProductQuantity.put(productType, total);
        }
    }

    public String toString() {
        String str = "";
        str += "Stores Income\n";
        double totalIncome = 0.0;
        for(String storeName : storesIncome.keySet()) {
            str += "Store: " + storeName + " | Income: " + storesIncome.get(storeName) + "€\n";
            totalIncome += storesIncome.get(storeName);
        }
        str += "Total Income: " + totalIncome + "\n";
        str += "Stores Outcome\n";
        double totalOutcome = 0.0;
        for(String storeName : storesOutcome.keySet()) {
            str += "Store: " + storeName + " | Outcome: " + storesOutcome.get(storeName) + "€\n";
            totalOutcome += storesOutcome.get(storeName);
        }
        str += "Total Outcome: " + totalOutcome + "\n";
        str += "Products Quantity:\n";
        for(String productName : brandProductQuantity.keySet()) {
            str += "Product: " + productName + " | Quantity: " + brandProductQuantity.get(productName) + "\n";
        }
        str += "Products Volume:\n";
        for(String productType : brandProductVolume.keySet()) {
            str += "Product: " + productType + " | Volume: " + brandProductVolume.get(productType) + "\n";
        }
        return str;
    }
}
