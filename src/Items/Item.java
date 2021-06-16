package Items;

public class Item {
    String name;
    int weight;

    public int getWeight() {
        return weight;
    }

    public String getName() {
        return name;
    }

    public static void printName(Item item) {
        System.out.println(item.getName());
    }
}
