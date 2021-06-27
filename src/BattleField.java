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
        System.out.println(Colors.CYAN_BOLD + "What you want to do next?" + Colors.CYAN + "\n 1. Attack\n 2. Use potion.\n 3. RUN FOR YOUR LIFE" + Colors.RESET);
        switch (userInput.nextLine()) {
            case "1" -> attack();
            case "2" -> hero.usePotionDialog(userInput);
            case "3" -> flee();
            default -> System.out.println(Colors.YELLOW + "Wrong input. Try again." + Colors.RESET);
        }
    }

    private void attack() {
        hero.attackMelee(enemy);
        System.out.println(Colors.RED_BOLD + enemy.getHpMp() + Colors.RESET);
        if (enemy.getHp() > 0) {
            enemy.attackMelee(hero);
            System.out.println(Colors.GREEN_BOLD + hero.getHpMp() + Colors.RESET);
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
        System.out.println(Colors.YELLOW + "You run away from " + enemy.getName() + Colors.RESET);
        victory = false;
        isFinished = true;
    }

    private boolean finishBattle() {

        if (victory) {
            System.out.println(Colors.GREEN_BOLD + hero.getName() + " won the battle." + Colors.RESET);
            hero.addExperience(enemy.getExpForKill());
            generateLoot();
            return true;
        } else {
            System.out.println(Colors.RED_BOLD + enemy.getName() + " won the battle." + Colors.RESET);
            return false;
        }
    }

    private void generateLoot() {
        System.out.println(Colors.YELLOW + "You looted a dead " + enemy.getName() + " and found:" + Colors.RESET);
        hero.addGold(enemy.getGoldForKill());
        if (rand.nextInt(100) <= POTION_DROP_CHANCE) {
            hero.addToInventory(ItemGenerator.generatePotion());
        }
    }

    //MAIN BATTLE SYSTEM------------------------------------------------------------------------------------------------
    public boolean startBattle(Character hero, Enemy enemy) {
        victory = false;
        isFinished = false;
        this.hero = hero;
        this.enemy = enemy;
        System.out.println(hero.getHeroStatus() + Colors.CYAN_BOLD + " VS " + enemy.getEnemyStatus());
        while (!isFinished) {
            battleMainMenu();
        }
        if (finishBattle()) {
            return true;
        } else {
            return false;
        }

    }

}

