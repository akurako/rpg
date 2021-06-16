import Units.Character;
import Units.Enemy;
import Units.Swordsman;

import java.util.Random;


public class Game {
    Character hero;
    Enemy enemy;





    public void  generateEnemy(){
        Random random = new Random();
        int max = 3;
        int min = -3;
        int generatedLevel = hero.getLevel()+ random.nextInt((max - min) + 1) + min;
        if (generatedLevel < 1) {
            generatedLevel = 1;
        }
        enemy = new Enemy(generatedLevel);
    }



    public void createNewCharacter(String name,String type){
        switch (type) {
            case "swordsman" -> hero = new Swordsman(name);
            }
        }
    }

