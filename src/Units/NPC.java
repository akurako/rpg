package Units;

import Items.Item;
import Items.ItemGenerator;
import Items.Potion;

import java.util.ArrayList;
import java.util.Random;

public class NPC extends Unit {
    ArrayList<Item> shop = new ArrayList<>();
    private final int minPotions = 20;
    private final int maxPotions = 50;
    private final int potionsAmount;
    public NPC(String name) {
        this.name = name;
        this.potionsAmount = minPotions + (new Random().nextInt(maxPotions - minPotions));
        generatePotions(potionsAmount);
    }

    public ArrayList<Item> getShopItems() {
        return this.shop;
    }

    public void removeFromShop(Item item) {
        if (item instanceof Potion && ((Potion) item).getCount() > 1) {
            ((Potion) item).useOne();
        } else {
            shop.remove(item);
        }
    }

    public void addItem(Item item) {
        shop.add(item);
    }

    public void generatePotions(int amount) {
        for (int i = 0; i < amount; i++) {
            addToShop(ItemGenerator.generatePotion());
        }
    }

    public void addToShop(Item item) {
        if (item instanceof Potion) {
            boolean stacked = false;
            for (Item potion : shop) {
                if (potion.getName().equals(item.getName())) {
                    ((Potion) potion).addOne();
                    stacked = true;
                }
            }
            if (!stacked) {
                this.shop.add(item);
            }
        } else {
            this.shop.add(item);
        }
    }
}
