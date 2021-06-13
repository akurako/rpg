package Units;

public class Unit {

    private String name;
    final int baseHP = 100;
    final int baseMP = 50;
    final int baseDodgeChance = 5;
    int dodgeChance;
    int level;
    int experience;
    int currentHP;
    int maxHP;
    int currentMP;
    int maxMP;
    int strength;
    int agility;
    int intellect;
    int statsAvailable;


    public int getLevel() {
        return level;
    }

    public void recalculateStats() {

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
