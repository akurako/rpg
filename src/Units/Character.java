package Units;

import Items.Item;
import Items.Potion;
import Locations.Towns.Town;

import java.util.ArrayList;

public class Character extends Unit {
    private int[] expTable = new int[100];
    String speciality;
    ArrayList<Item> inventory = new ArrayList<>();
    int inventoryMaxWeight = 10000;
    int inventoryCurrentWeight = 0;
    Town homeTown;
    boolean newCharacter;

    //GETTERS AND SETTERS-----------------------------------------------------------------------------------------------

    public Town getHomeTown() {
        return homeTown;
    }

    public void setHomeTown(Town homeTown) {
        this.homeTown = homeTown;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public String getHeroStatus() {
        return "[" + name + "]["+speciality+"][LVL: " + level + "][EXP: " + getExperience() + "/" + expTable[level + 1] + "][HP: " + currentHP + "/" + maxHP + "][MP: " + currentMP + "/" + maxMP + "]";
    }


    //EXPERIENCE METHODS------------------------------------------------------------------------------------------------

    public void generateExpTable() {
        double startValue = 100;
        double modificator = 1.15;
        for (int i = 0; i < 100; i++) {
            expTable[i] = (int) Math.round(startValue);
            startValue = startValue * modificator;
        }
    }

    private void checkLevelUp() {
        if (getExperience() > expTable[getLevel() - 1]) {
            lvlUp();
        }
    }

    public String getLvlExpStatus() {
        return "[" + name + "][" + getExperience() + "/" + expTable[level + 1] + " exp]";
    }

    public void addExperience(int amount) {
        experience += amount;
        System.out.println("You earn " + amount + " experience.");
        checkLevelUp();
        System.out.println(getLvlExpStatus());
    }

    public void lvlUp() {
        statsAvailable += 3;
        level++;
        System.out.println("You become stronger.");
    }


    //INVENTORY AND ITEMS METHODS---------------------------------------------------------------------------------------

    public void addToInventory(Item item) {
        if ((this.inventoryCurrentWeight + item.getWeight()) <= this.inventoryMaxWeight) {
            this.inventory.add(item);
            this.inventoryCurrentWeight += item.getWeight();
        } else {
            System.out.println("too heavy");
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

    public void removeFromInventory(Item item) {
        inventoryCurrentWeight -= item.getWeight();
        inventory.remove(item);

    }

}
