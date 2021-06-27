package Items;

public class Item {
    String name;
    int weight;
    int buyPrice;

    public int getWeight() {
        return weight;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public String getName() {
        return name;
    }

    public int getSellPrice(){
        return (buyPrice/2);
    }
}
