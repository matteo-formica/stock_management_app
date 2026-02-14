import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Stock implements Serializable {
    HashMap<Lot, Boolean> stock;
    private static final long serialVersionUID = 1L;

    public Stock() {
        stock = new HashMap<Lot, Boolean>();
    }

    public String getStock() {
        for (Map.Entry<Lot, Boolean> entry : stock.entrySet()) {
            if (entry.getValue()) {
                return entry.getKey().getProduct();
            }
        }
        return null;
    }

    public void addLotToStock(Lot lot){
        stock.put(lot, true);
    }

    public void removeLotFromStock(Lot lot){
        stock.remove(lot);
    }

    public boolean getLotFromStock(Lot lot){
        return stock.get(lot);
    }

    public void checkLot(Lot lot) {
        if (lot.getProduct() == null) {
            this.stock.put(lot, false);
        }
    }

    public void checkStock(){
        for(Lot lot: stock.keySet()){
            this.checkLot(lot);
            if(stock.get(lot) == false){
                this.removeLotFromStock(lot);
            }
        }
    }

    public String toString() {
        String str = "";
        for (Lot lot : stock.keySet()) {
            str += lot.toString() + "\n";
        }
        return str;
    }
}
