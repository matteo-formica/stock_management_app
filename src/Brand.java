import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Brand implements Serializable {
    private String brandName;
    private String brandPassword;
    private String brandDescription;
    private ArrayList<Store> stores;
    private ArrayList<Manager> managers;
    public BrandAccounting brandAccounting;
    private static final long serialVersionUID = 1L;

    public  Brand()
    {
        this.brandName = "";
        this.brandPassword = "";
        this.stores = new ArrayList<>();
        this.managers = new ArrayList<>();
        this.brandAccounting = new BrandAccounting();
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandPassword() {
        return brandPassword;
    }

    public void setBrandPassword(String brandPassword) {
        this.brandPassword = brandPassword;
    }


    public String getBrandDescription() {
        return brandDescription;
    }
    public void setBrandDescription(String brandDescription) {
        this.brandDescription = brandDescription;
    }

    public void createStore(String storeName, String storeAddress, int storePhone)
    {
        Store store = new Store(storeName, storeAddress, storePhone);
        this.stores.add(store);
    }

    public ArrayList<Store> getStores() {
        return stores;
    }

    public void setStores(ArrayList<Store> stores) {
        this.stores = stores;
    }

    public ArrayList<Manager> getManagers() {
        return managers;
    }

    public void setManagers(ArrayList<Manager> managers) {
        this.managers = managers;
    }

    public void createManager(String managerName, String managerPassword, boolean master)
    {
        Manager manager = new Manager(managerName, managerPassword);
        if (master){
            manager.setIsMaster(true);
        }
        this.managers.add(manager);

    }

}
