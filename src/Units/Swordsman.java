package Units;

public class Swordsman extends Character{

    public Swordsman(String name){
        generateExpTable();
        this.level = 1;
        this.name = name;
        this.strength = 15;
        this.agility = 5;
        this.intellect = 5;
        this.recalculateStats();
        this.currentHP = this.maxHP;
    }
}
