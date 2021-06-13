package Units;

public class Unit {

    private String name;
    int level;
    int experience;
    int currentHP;
    int maxHP;
    private int strength;
    private int agility;
    private int vitality;
    int statsAvailable;


    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }
    public void addExperience(int amount){
        this.experience += amount;
    }
    public int getHp() {
        return currentHP;
    }



}
