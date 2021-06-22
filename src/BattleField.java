import Units.Character;
import Units.Enemy;

import java.util.Random;

public class BattleField {
    Random rand = new Random();
    Character hero;
    Enemy enemy;
    Boolean victory = false;
    Boolean isFinished = false;


    //MAIN BATTLE SYSTEM------------------------------------------------------------------------------------------------
    public void startBattle(Character hero, Enemy enemy) {
        this.hero = hero;
        this.enemy = enemy;
        while (hero.getHp() > 0 && enemy.getHp() > 0 && isFinished) {
            if (hero.getHp() > 0) {
                hero.attackMelee(enemy, rand.nextInt(1000));
                System.out.println(enemy.getHPMP());
            }
            if (enemy.getHp() > 0) {
                enemy.attackMelee(hero, rand.nextInt(1000));
                System.out.println(hero.getHPMP());
            }
        }
        if (hero.getHp() > enemy.getHp()) {
            victory = true;
        }
        if (victory) {
            System.out.println(hero.getName() + " Won the battle.");
            hero.addExperience(enemy.getExpForKill());
        } else {
            System.out.println(enemy.getName() + " Won the battle.");
        }
    }

}
