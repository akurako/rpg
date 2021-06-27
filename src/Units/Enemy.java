package Units;

import Interfaces.Colors;

import java.util.Random;

public class Enemy extends Unit {
    private int goldForKill;
    private int expForKill;
    private final int startingStats = 7;

    public Enemy(String name, int level) {
        this.name = name;
        this.level = level;
        if (level > 1) {
            statsAvailable = (5 * (level - 1));
        }
        this.strength = startingStats;
        this.agility = startingStats;
        this.intellect = startingStats;
        Random rand = new Random();
        while (statsAvailable > 0) {
            if (rand.nextBoolean()) {
                strength++;
                statsAvailable--;
            }
            if (rand.nextBoolean() && statsAvailable > 0) {
                agility++;
                statsAvailable--;
            }
            if (rand.nextBoolean() && statsAvailable > 0) {
                intellect++;
                statsAvailable--;
            }
        }
        recalculateStats();
        calculateExpForKill();
        calculateGoldForKill();
        currentHP = maxHP;
        currentMP = maxMP;


    }

    private void calculateGoldForKill() {
        goldForKill = new Random().nextInt((strength + agility + intellect) * 2);
    }

    public int getGoldForKill() {
        return goldForKill;
    }

    public int getExpForKill() {
        return expForKill;
    }

    private void calculateExpForKill() {
        expForKill = (strength + agility + intellect);
    }

    public String getEnemyStatus() {
        return Colors.RED_BOLD + "[" + name + "][LVL: " + level + "][HP: " + currentHP + "/" + maxHP + "][MP: " + currentMP + "/" + maxMP + "]" + Colors.RESET;
    }
}
