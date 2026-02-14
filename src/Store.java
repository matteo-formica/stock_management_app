import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Store implements Serializable {
    private String storeName;
    private String storeAddress;
    private int storePhone;
    private ArrayList<Manager> storeManagers;
    public Stock storeStock;
    public Contability storeContability;
    private static final long serialVersionUID = 1L;

    public Store(String storeName, String storeAddress, int storePhone)
    {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storePhone = storePhone;
        this.storeManagers = new ArrayList<>();
        this.storeStock = new Stock();
        this.storeContability = new Contability();
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
        return "Name: " + storeName + ", Address: " + storeAddress + ", Phone: " + storePhone;
    }

}

