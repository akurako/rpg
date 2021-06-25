import Units.Character;
import Units.Enemy;

import java.util.Random;
import java.util.Scanner;

public class BattleField {
    private Random rand = new Random();
    private Character hero;
    private Enemy enemy;
    private Boolean victory;
    private Boolean isFinished;
    private Scanner userInput;

    public void getScannerControl(Scanner sc) {
        userInput = sc;
    }

    //BATTLE MENU-------------------------------------------------------------------------------------------------------
    private void battleMainMenu() {
        System.out.println("What you want to do next?\n1. Attack\n2. Use potion.\n3. RUN FOR YOUR LIFE");
        switch (Integer.parseInt(userInput.nextLine())) {
            case 1 -> {
                attack();
            }
            case 2 -> {
                hero.usePotionDialog(userInput);
            }
            case 3 -> {
                flee();
            }
            default -> System.out.println("Wrong input.");
        }
    }

    private void attack() {
        hero.attackMelee(enemy, rand.nextInt(1000));
        System.out.println(enemy.getHPMP());
        if (enemy.getHp() > 0) {
            enemy.attackMelee(hero, rand.nextInt(1000));
            System.out.println(hero.getHPMP());
        } else {
            isFinished = true;
            victory = true;
        }
        if (hero.getHp() <= 0) {
            isFinished = true;
            victory = false;
        }

    }

    private void flee() {
        System.out.println("You run away from " + enemy.getName());
        victory = false;
        isFinished = true;
    }

    private void finishBattle() {

        if (victory) {
            System.out.println(hero.getName() + " Won the battle.");
            hero.addExperience(enemy.getExpForKill());
        } else {
            System.out.println(enemy.getName() + " Won the battle.");
        }
    }

    //MAIN BATTLE SYSTEM------------------------------------------------------------------------------------------------
    public void startBattle(Character hero, Enemy enemy) {
        victory = false;
        isFinished = false;
        this.hero = hero;
        this.enemy = enemy;
        System.out.println(hero.getHeroStatus());
        System.out.println("Your enemy: " + enemy.getEnemyStatus());
        while (!isFinished) {
            battleMainMenu();
        }
        finishBattle();
    }

}

