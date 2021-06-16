import Units.Character;
import Units.Enemy;

public class BattleField {
    Character hero;
    Enemy enemy;
    Boolean victory = false;
    Boolean battle = true;

    public void startBattle(Character hero, Enemy enemy) {
        this.hero = hero;
        this.enemy = enemy;
        while (hero.getHp() > 0 && enemy.getHp() > 0 && battle) {
            if (hero.getHp() > 0) {
                hero.attackMelee(enemy);
                System.out.println(enemy.getHPMP());
            }
            if (enemy.getHp() > 0) {
                enemy.attackMelee(hero);
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
            System.out.println(enemy.getName()+ " Won the battle.");
        }
    }

}
