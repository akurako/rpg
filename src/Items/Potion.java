package Items;

public class Potion extends Item{
    int recoveryPercentage;
    String potionType;
    public Potion(String type, int size){
        potionType = type;
        recoveryPercentage = size;
    }
    public int getHpRecovery() {
        return recoveryPercentage;
    }
}
