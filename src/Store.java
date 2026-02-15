import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Store implements Serializable {
    private String storeName;
    private String storeAddress;
    private int storePhone;
    public Stock storeStock;
    public Accounting storeAccounting;
    private static final long serialVersionUID = 1L;

    public Store(String storeName, String storeAddress, int storePhone)
    {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhone = storePhone;
        this.storeStock = new Stock();
        this.storeAccounting = new Accounting();
    }

    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }
    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public int getStorePhone() {
        return storePhone;
    }
    public void setStorePhone(int storePhone) {
        this.storePhone = storePhone;
    }

    public String toString()
    {
        return "Name: " + storeName + " | Address: " + storeAddress + " | Phone: " + storePhone;
    }

}

