package Units;

public class Enemy extends Unit {
    private int max_gold_drop;

    public int getExpForKill() {
        return exp_for_kill;
    }

    private int exp_for_kill;
    private int chance_for_drop_item;
    private final int startingStats = 7;

    public Enemy(int level){

        this.level =level;
        this.strength = startingStats;
        this.agility = startingStats;
        this.intellect = startingStats;
        this.name = "Test Enemy";
        recalculateStats();
        calculateExpForKill();
        this.currentHP = this.maxHP;
    }

    public void calculateExpForKill(){
        exp_for_kill = (strength+agility+intellect)*level;
    }
}
