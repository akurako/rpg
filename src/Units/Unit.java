package Units;

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

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getExperience() {
        return experience;
    }

    public String getHPMP() {
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

    //IN BATTLE METHODS-------------------------------------------------------------------------------------------------

    public void attackMelee(Unit enemy, int randomNumber) {
        if (randomNumber > enemy.dodgeChance) {
            enemy.currentHP -= this.strength;
            System.out.println(this.name + " attacks " + enemy.name + " for " + this.strength);
        } else {
            System.out.println(enemy.name + "avoided attack.");
        }
    }

}
