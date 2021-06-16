import Items.Item;
import Items.ItemGenerator;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.createNewCharacter("Test Hero","swordsman");
        game.generateEnemy();
        BattleField bf = new BattleField();
        bf.startBattle(game.hero,game.enemy);
        game.generateEnemy();
        bf.startBattle(game.hero,game.enemy);
        game.generateEnemy();
        bf.startBattle(game.hero,game.enemy);


    }
    }
