package Items;


import java.io.Serializable;
import java.util.Random;

public class ItemGenerator implements Serializable {
 private static Random random = new Random();

    public static Potion generatePotion() {
        int rand = random.nextInt(100);
        String type;
        if (rand > 50) {
            type = "heal";
        } else {
            type = "mana";
        }
        rand = random.nextInt(100);
        int potionSize;
        if (rand <= 50) {
            potionSize = 25;
        } else if (rand <= 80 && rand > 50) {
            potionSize = 50;
        } else {
            potionSize = 100;
        }
        return new Potion(type, potionSize);
    }
}
