package Units;

public class Swordsman extends Character{

    public Swordsman(String name){
        generateExpTable();
        level = 1;
        this.name = name;
        strength = 50;
        agility = 25;
        intellect = 5;
        addGold(1000);
        recalculateStats();
        currentHP = maxHP;
        currentMP = maxMP;
        speciality = "Swordsman";

    }
}
