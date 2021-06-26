package Units;

import Interfaces.Colors;
import Items.Armor;
import Items.Item;
import Items.Potion;
import Locations.Towns.Town;

import java.util.ArrayList;
import java.util.Scanner;

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
        return Colors.GREEN_BOLD + "[" + name + "][" + speciality + "][LVL: " + level + "][EXP: " + getExperience() + "/" + expTable[level + 1] + "][HP: " + currentHP + "/" + maxHP + "][MP: " + currentMP + "/" + maxMP + "]" + Colors.RESET;
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
        return Colors.GREEN_BOLD + "[" + name + "][" + getExperience() + "/" + expTable[level + 1] + " exp]"+ Colors.RESET;
    }

    public void addExperience(int amount) {
        experience += amount;
        System.out.println(Colors.YELLOW + "You earn " + amount + " experience." + Colors.RESET);
        checkLevelUp();
        System.out.println(getLvlExpStatus());
    }

    public void lvlUp() {
        statsAvailable += 5;
        experience = 0;
        level++;
        System.out.println("You become stronger.");
    }


    //INVENTORY AND ITEMS METHODS---------------------------------------------------------------------------------------

    public void addToInventory(Item item) {
        if ((this.inventoryCurrentWeight + item.getWeight()) <= this.inventoryMaxWeight) {
            System.out.println(Colors.YELLOW + item.getName() + " added to your inventory." + Colors.RESET);
            if (item instanceof Potion) {
                boolean stacked = false;
                for (Item potion : inventory) {
                    if (potion.getName().equals(item.getName())) {
                        ((Potion) potion).addOne();
                        stacked = true;
                    }
                }
                if (!stacked) {
                    this.inventory.add(item);
                    this.inventoryCurrentWeight += item.getWeight();
                }
            } else {
                this.inventory.add(item);
                this.inventoryCurrentWeight += item.getWeight();
            }
        } else {
            System.out.println("too heavy");
        }
    }

    public int getInventoryCurrentWeight() {
        return inventoryCurrentWeight;
    }

    public void usePotion(Potion potion) {
        if (potion.getPotionType().equals("heal")) {
            int startHP = currentHP;
            int onePercent = maxHP / 100;
            currentHP += (onePercent * potion.getRecoveryPercentage());
            if (currentHP > maxMP) {
                currentHP = maxHP;
            }
            System.out.println("You drink a healing potion and replenish " + (currentHP - startHP) + " HP");
        }
        if (potion.getPotionType().equals("mana")) {
            int startMP = currentMP;
            int onePercent = maxMP / 100;
            currentMP += (onePercent * potion.getRecoveryPercentage());
            if (currentMP > maxMP) {
                currentMP = maxMP;
            }
            System.out.println("You drink a mana potion and replenish " + (currentMP - startMP) + " MP");
        }
        if (potion.getCount() > 1) {
            potion.useOne();
        } else {
            removeFromInventory(potion);
        }
    }

    public void removeFromInventory(Item item) {
        inventoryCurrentWeight -= item.getWeight();
        inventory.remove(item);

    }

    public void usePotionDialog(Scanner sc) {
        Scanner userInput = sc;
        int inputNumber = 1;
        ArrayList<String> potionList = new ArrayList<>();
        for (Item item : inventory) {
            if (item instanceof Potion) {
                potionList.add(item.getName());
                System.out.println(inputNumber + ". " + item.getName() + " [" + ((Potion) item).getCount() + "]");
                inputNumber++;
            }
        }
        System.out.println(inputNumber + ". Back");
        if (potionList.size() > 0) {
            int chosenItem = Integer.parseInt(userInput.nextLine());

            if (chosenItem <= potionList.size()) {
                for (Item item : inventory) {
                    if (item.getName().equals(potionList.get(chosenItem - 1))) {

                        usePotion(((Potion) item));
                        break;
                    }
                }
            }
        } else {
            System.out.println("You dont have any potions.");
        }
    }
}
