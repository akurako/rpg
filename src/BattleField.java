import Interfaces.Colors;
import Items.ItemGenerator;
import Units.Character;
import Units.Enemy;

import java.util.Random;
import java.util.Scanner;

public class BattleField {
    private final Random rand = new Random();
    private Character hero;
    private Enemy enemy;
    private Boolean victory;
    private Boolean isFinished;
    private Scanner userInput;
    private static final int POTION_DROP_CHANCE = 35;

    public void getScannerControl(Scanner sc) {
        userInput = sc;
    }

    //BATTLE MENU-------------------------------------------------------------------------------------------------------
    private void battleMainMenu() {
        System.out.println(Colors.CYAN + "What you want to do next?\n 1. Attack\n 2. Use potion.\n 3. RUN FOR YOUR LIFE" + Colors.RESET);
        switch (Integer.parseInt(userInput.nextLine())) {
            case 1 -> attack();
            case 2 -> hero.usePotionDialog(userInput);
            case 3 -> flee();
            default -> System.out.println("Wrong input.");
        }
    }

    private void attack() {
        hero.attackMelee(enemy, rand.nextInt(1000));
        System.out.println(Colors.RED + enemy.getHpMp() + Colors.RESET);
        if (enemy.getHp() > 0) {
            enemy.attackMelee(hero, rand.nextInt(1000));
            System.out.println(Colors.GREEN + hero.getHpMp() + Colors.RESET);
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
            generateLoot();
        } else {
            System.out.println(enemy.getName() + " Won the battle.");
        }
    }

    private void generateLoot() {
        if (rand.nextInt(100) <= POTION_DROP_CHANCE) {
            System.out.println(Colors.YELLOW + "You looted a dead " + enemy.getName() + " and found:" + Colors.RESET);
            hero.addToInventory(ItemGenerator.generatePotion());
        }
    }

    //MAIN BATTLE SYSTEM------------------------------------------------------------------------------------------------
    public void startBattle(Character hero, Enemy enemy) {
        victory = false;
        isFinished = false;
        this.hero = hero;
        this.enemy = enemy;
        System.out.println(hero.getHeroStatus() + " VS " + enemy.getEnemyStatus());
        while (!isFinished) {
            battleMainMenu();
        }
        finishBattle();
    }

}

