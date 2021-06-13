package Units;

import Items.Item;
import Items.Potion;

import java.util.ArrayList;

public class Character extends Unit {
    ArrayList<Item> inventory = new ArrayList<>();

    public void heal(Potion potion) {
        inventory.remove(potion);
        if (currentHP + ((maxHP / 100)* potion.getHpRecovery() ) > maxHP) {
            currentHP = maxHP;
        } else {
            currentHP += potion.getHpRecovery();
        }

    }

    public void lvlUp() {
        statsAvailable += 3;
        level++;

    }
}
