package Units;

import Items.Item;
import Items.Potion;

import java.util.ArrayList;

public class Character extends Unit {
    ArrayList<Item> inventory = new ArrayList<>();
    int inventoryMaxWeight;
    int inventoryCurrentWeight;

    public void usePotion(Potion potion) {
        if (potion.getPotionType().equals("heal")) {
            currentHP =recovery(currentHP,maxHP,potion.getRecoveryPercentage());
        }
        else if (potion.getPotionType().equals("mana")) {
            currentMP =recovery(currentMP,maxMP,potion.getRecoveryPercentage());
        }

        inventory.remove(potion);
        //TODO NOTIFY PLAYER
    }

    public int recovery(int current, int max, int recoveryPercentage){
        int recoveryValue;
        int onePercent = max / 100;
        if (current + (onePercent * recoveryPercentage) > max) {
            recoveryValue = max;
        } else {
            recoveryValue = current + (onePercent*recoveryPercentage) ;
        }
        return recoveryValue;
    }

    public void addToInventory(Item item) {
        if (inventoryCurrentWeight + item.getWeight() <= inventoryMaxWeight) {
            inventory.add(item);
            inventoryCurrentWeight += item.getWeight();
        } else {
            //TODO SOMETHING IF TOO HEAVY
        }
    }

    public void lvlUp() {
        statsAvailable += 3;
        level++;
        //TODO NOTIFY PLAYER
    }


}
