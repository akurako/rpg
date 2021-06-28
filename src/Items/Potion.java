package Items;

public class Potion extends Item implements Cloneable {
    int recoveryPercentage;
    String potionType;
    int count;
    int stackWeight;

    public Potion(String type, int size) {
        count = 1;
        potionType = type;
        recoveryPercentage = size;
        weight = size * 10;
        switch (recoveryPercentage) {
            case 25 -> {
                this.buyPrice = 100;
                this.name = "Small";
            }
            case 50 -> {
                this.name = "Medium";
                this.buyPrice = 300;
            }
            case 100 -> {
                this.name = "Big";
                this.buyPrice = 500;
            }
        }

        if (type.equals("heal")) {
            this.name = this.name + " Healing Potion";
        }
        if (type.equals("mana")) {
            this.name = this.name + " Mana Potion";
        }

    }

    public int getRecoveryPercentage() {
        return recoveryPercentage;
    }

    public String getPotionType() {
        return potionType;
    }

    public void addOne() {
        count++;
        stackWeight += weight;
    }

    public void useOne() {
        count--;
        stackWeight -= weight;
    }

    public int getStackWeight() {
        return stackWeight;
    }

    public int getCount() {
        return count;
    }

    public Potion getOne(){
        return new Potion(this.potionType,this.recoveryPercentage);
    }
}
