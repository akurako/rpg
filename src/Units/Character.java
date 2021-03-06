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
    private int gold;
    Town homeTown;
    Object savedGameState;

    //GETTERS AND SETTERS-----------------------------------------------------------------------------------------------


    public int getGold() {
        return gold;
    }

    public int getExpForNextLvl(){
        return expTable[level -1];
    }

    public void lostExperience(int amount){
        this.experience -= amount;
    }

    public Object getSavedGameState() {
        return savedGameState;
    }

    public void setSavedGameState(Object savedGameState) {
        this.savedGameState = savedGameState;
    }

    public boolean payGold(int gold) {
        if (this.gold < gold) {
            System.out.println(Colors.YELLOW + "You have not enough gold." + Colors.RESET);
            return false;
        } else {
            this.gold -= gold;
            return true;
        }
    }

    public void addGold(int gold) {
        this.gold += gold;
        System.out.println(Colors.YELLOW + gold + " gold added to your inventory.");
    }

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

    void generateExpTable() {
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
        return Colors.GREEN_BOLD + "[" + name + "][" + getExperience() + "/" + expTable[level + 1] + " exp]" + Colors.RESET;
    }

    public void addExperience(int amount) {
        experience += amount;
        System.out.println(Colors.YELLOW + "You earn " + amount + " experience." + Colors.RESET);
        checkLevelUp();
        System.out.println(getLvlExpStatus());
    }

    public void lvlUp() {
        statsAvailable += 5;
        experience = experience - expTable[getLevel()-1];
        level++;
        System.out.println(Colors.YELLOW_BOLD + "You become stronger." + Colors.RESET);
    }


    //INVENTORY AND ITEMS METHODS---------------------------------------------------------------------------------------

    public void addToInventory(Item item){
        if ((this.inventoryCurrentWeight + item.getWeight()) <= this.inventoryMaxWeight) {
            System.out.println(Colors.YELLOW + item.getName() + " added to your inventory." + Colors.RESET);
            if (item instanceof Potion) {
                boolean stacked = false;
                for (Item potion : inventory) {
                    if (potion.getName().equals(item.getName())) {
                        ((Potion) potion).addOne();
                        stacked = true;
                        break;
                    }
                }
                if (!stacked) {
                    this.inventory.add(((Potion) item).getOne());
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
            int onePercent = (int)(maxHP / 100.0f);
           currentHP += (onePercent * potion.getRecoveryPercentage());
            if (currentHP > maxHP) {
                currentHP = maxHP;
            }
            System.out.println(Colors.YELLOW + "You drink a healing potion and replenish " + (currentHP - startHP) + " HP" + Colors.RESET);
        }
        if (potion.getPotionType().equals("mana")) {
            int startMP = currentMP;
            int onePercent = (maxMP / 100);
            currentMP += (onePercent * potion.getRecoveryPercentage());
            if (currentMP > maxMP) {
                currentMP = maxMP;
            }
            System.out.println(Colors.YELLOW + "You drink a mana potion and replenish " + (currentMP - startMP) + " MP" + Colors.RESET);
        }
        if (potion.getCount() > 1) {
            potion.useOne();
        } else {
            removeFromInventory(potion);
        }
    }

    public void showGoldAmount() {
        System.out.println(Colors.YELLOW + "You have " + gold + " gold." + Colors.RESET);
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
                System.out.println(Colors.CYAN + inputNumber + ". " + item.getName() + " [" + ((Potion) item).getCount() + "]");
                inputNumber++;
            }
        }
        if (potionList.size() > 0) {
            System.out.println(inputNumber + ". Back" + Colors.RESET);
        }
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
            System.out.println(Colors.YELLOW + "You dont have any potions." + Colors.RESET);
        }
    }
}
