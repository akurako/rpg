package Locations.Dungeons;

import Units.Character;
import Units.EnemyGenerator;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DungeonGenerator implements Serializable {
    ArrayList<String> dungeonNames = new ArrayList<>();

    public DungeonGenerator(){
        File dungeonNamesFile = new File("data/dungeonnames.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(dungeonNamesFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(dungeonNamesFile + "Not found.");
        }
        Scanner sc = new Scanner(fis);
        while (sc.hasNext()){
            dungeonNames.add(sc.nextLine());
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Dungeon generateDungeon(Character hero){
        Random rand = new Random();
        int randomNumber = rand.nextInt(dungeonNames.size());
        String name = dungeonNames.get(randomNumber);
        dungeonNames.remove(randomNumber);
        Dungeon generatedDungeon = new Dungeon(name);
        for (int i = 0; i < generatedDungeon.numberOfEnemies ; i++) {
            generatedDungeon.locationEnemies.add(new EnemyGenerator().generateEnemy(hero));
        }
        return generatedDungeon;
    }

    public void fillDungeon(Character hero, Dungeon dungeon){
        for (int i = 0; i < dungeon.numberOfEnemies ; i++) {
            dungeon.locationEnemies.add(new EnemyGenerator().generateEnemy(hero));
        }
    }


}
