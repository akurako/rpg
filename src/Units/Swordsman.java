package Units;

public class Swordsman extends Character{

    public Swordsman(String name){
        generateExpTable();
        level = 1;
        this.name = name;
        strength = 15;
        agility = 10;
        intellect = 5;
        addGold(1000);
        recalculateStats();
        currentHP = maxHP;
        currentMP = maxMP;
        speciality = "Swordsman";

    }
}
