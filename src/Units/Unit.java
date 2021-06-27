package Units;

import Interfaces.Colors;

import java.io.Serializable;

public class Unit implements Serializable {

    String name;
    final int baseHP = 100;
    final int baseMP = 50;
    final int baseDodgeChance = 50;
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

    //GETTERS AND SETTERS-----------------------------------------------------------------------------------------------
    public int getDodgeChance(){
        return dodgeChance;
    }

    public int getStatsAvailable() {
        return statsAvailable;
    }

    public void setStatsAvailable(int statsAvailable) {
        this.statsAvailable = statsAvailable;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getIntellect() {
        return intellect;
    }

    public void setIntellect(int intellect) {
        this.intellect = intellect;
    }

    public String getHpMp() {
        return "[" + name + "][HP:" + currentHP + "/" + maxHP + "][MP:" + currentMP + "/" + maxMP + "]";
    }

    public int getHp() {
        return currentHP;
    }

    //STATS AND EXPERIENCE METHODS--------------------------------------------------------------------------------------

    public void recalculateStats() {
        this.maxHP = baseHP + (strength * 10);
        this.maxMP = baseMP + (intellect * 5);
        this.dodgeChance = baseDodgeChance + (agility * 2);
    }

    public void restoreHpMp(){
        currentMP = maxMP;
        currentHP = maxHP;
    }

    //IN BATTLE METHODS-------------------------------------------------------------------------------------------------

    public void attackMelee(Unit enemy, int randomNumber) {
        if (randomNumber > enemy.dodgeChance) {
            enemy.currentHP -= this.strength;
            System.out.println(Colors.YELLOW + this.name + " attacks " + enemy.name + " for " + this.strength + Colors.RESET);
        } else {
            System.out.println(Colors.YELLOW + enemy.name + " avoided attack." + Colors.RESET);
        }
    }


}
