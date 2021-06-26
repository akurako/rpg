package Units;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class EnemyGenerator {
    ArrayList<String> enemyNames = new ArrayList<>();

    public EnemyGenerator(){
        File enemyNamesFile = new File("data/monsternames.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(enemyNamesFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(enemyNamesFile + "Not found.");
        }
        Scanner sc = new Scanner(fis);
        while (sc.hasNext()){
            enemyNames.add(sc.nextLine());
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Enemy generateEnemy(Character character){
        Random rand = new Random();
        final int maxLvlDifference = 3;
        final int minLvlDifference = -3;
        int randomNumber = rand.nextInt(enemyNames.size());
        String name = enemyNames.get(randomNumber);
        int generatedLevel = character.getLevel() + rand.nextInt((maxLvlDifference - minLvlDifference) + 1) + minLvlDifference;
        if (generatedLevel < 1) {
            generatedLevel = 1;
        }
        return new Enemy(name,generatedLevel);
    }


}
