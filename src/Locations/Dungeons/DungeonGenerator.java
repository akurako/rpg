package Locations.Dungeons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class DungeonGenerator {
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

    public Dungeon generateDungeon(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(dungeonNames.size());
        String name = dungeonNames.get(randomNumber);
        dungeonNames.remove(randomNumber);
        return new Dungeon(name);
    }
}
