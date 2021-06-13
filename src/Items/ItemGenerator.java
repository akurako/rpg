package Items;

import Units.Character;

import javax.swing.*;
import java.util.Random;

public class ItemGenerator {
    Random random = new Random();

    public Potion generateHealingPotion(Character target) {
        int rand = random.nextInt(100);
        int potionSize;
        if (rand <= 50) {
            potionSize = 25;
        } else if (rand <= 80) {
            potionSize = 50;
        } else {
            potionSize = 100;
        }

        return new Potion("heal", potionSize);
    }
}
