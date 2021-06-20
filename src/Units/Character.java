package Units;

import Items.Item;
import Items.Potion;

import java.util.ArrayList;

public class Character extends Unit {
    private int[] expTable = new int[100];
    static ArrayList<Item> inventory = new ArrayList<>();
    static int inventoryMaxWeight = 10000;
    static int inventoryCurrentWeight = 0;

    public Character(){
        generateExpTable();
    }

    public void generateExpTable() {
        double startvalue = 100;
        double modificator = 1.15;
        for (int i = 0; i < 100; i++) {
            expTable[i] = (int) Math.round(startvalue);
            startvalue = startvalue * modificator;
        }
    }

    public int getInventoryCurrentWeight() {
        return inventoryCurrentWeight;
    }

    public void usePotion(Potion potion) {
        if (potion.getPotionType().equals("heal")) {
            currentHP = recovery(currentHP, maxHP, potion.getRecoveryPercentage());
        } else if (potion.getPotionType().equals("mana")) {
            currentMP = recovery(currentMP, maxMP, potion.getRecoveryPercentage());
        }

        removeFromInventory(potion);
        //TODO NOTIFY PLAYER
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public int recovery(int current, int max, int recoveryPercentage) {
        int recoveryValue;
        int onePercent = max / 100;
        if (current + (onePercent * recoveryPercentage) > max) {
            recoveryValue = max;
        } else {
            recoveryValue = current + (onePercent * recoveryPercentage);
        }
        return recoveryValue;
    }

    private void checkLevelUp() {
        if (getExperience() > expTable[getLevel() - 1]) {
            lvlUp();
        }
    }

    public String getLvlExpStatus(){
        return "["+name+"]["+getExperience()+"/"+expTable[level+1]+" exp]";
    }

    public void addExperience(int amount) {
        experience += amount;
        System.out.println("You earn "+amount+" experience.");
        checkLevelUp();
        System.out.println(getLvlExpStatus());
    }

    public void addToInventory(Item item) {
        if ((this.inventoryCurrentWeight + item.getWeight()) <= this.inventoryMaxWeight) {
            this.inventory.add(item);
            this.inventoryCurrentWeight += item.getWeight();
        } else {
            //TODO SOMETHING IF TOO HEAVY
            System.out.println("too heavy");
        }
    }

    public static void removeFromInventory(Item item) {
        inventoryCurrentWeight -= item.getWeight();
        inventory.remove(item);

    }

    public void lvlUp() {
        statsAvailable += 3;
        level++;
        System.out.println("You become stronger.");
        //TODO NOTIFY PLAYER
    }

    public String getHeroStatus(){
        return "["+name+"][LVL: "+level+"][EXP: "+getExperience()+"/"+expTable[level+1]+"][HP: "+currentHP+"/"+maxHP+"][MP: "+currentMP+"/"+maxMP+"]";
    }

}
