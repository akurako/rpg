package Units;

import Interfaces.Colors;

import java.util.Random;

public class Enemy extends Unit {
    private int max_gold_drop;

    public int getExpForKill() {
        return exp_for_kill;
    }

    private int exp_for_kill;
    private int chance_for_drop_item;
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
        currentHP = maxHP;
        currentMP = maxMP;


    }


    public void calculateExpForKill() {
        exp_for_kill = (strength + agility + intellect) * level;
    }

    public String getEnemyStatus() {
        return Colors.RED_BOLD + "[" + name + "][LVL: " + level + "][HP: " + currentHP + "/" + maxHP + "][MP: " + currentMP + "/" + maxMP + "]" + Colors.RESET;
    }
}
