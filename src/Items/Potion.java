package Items;

public class Potion extends Item {
    int recoveryPercentage;
    String potionType;

    public Potion(String type, int size) {
        potionType = type;
        recoveryPercentage = size;
        weight = size * 10;
        switch (recoveryPercentage) {
            case 25 -> this.name = "Small";
            case 50 -> this.name = "Medium";
            case 100 -> this.name = "Big";
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
}
