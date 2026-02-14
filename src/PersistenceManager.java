import java.io.*;
import java.util.*;

public class PersistenceManager {
    private static final String FILE_NAME = "database_magazzino.dat";
    public static int found;

    public static void saveData(ArrayList<Product> priceList, Brand brand) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(priceList);
            oos.writeObject(brand);
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static Brand loadData(PriceList priceList) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            priceList.productsPriceList = (ArrayList<Product>) ois.readObject();

            return (Brand) ois.readObject();
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}