package Units;

import Locations.Towns.Town;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NPCGenerator implements Serializable {
    ArrayList<String> npcNames = new ArrayList<>();

    public NPCGenerator() {
        File npcNamesFile = new File("data/npcnames.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(npcNamesFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(npcNamesFile + "Not found.");
        }
        Scanner sc = new Scanner(fis);
        while (sc.hasNext()) {
            npcNames.add(sc.nextLine());
        }
        try {
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public NPC generateNPC() {
        Random rand = new Random();
        int randomNumber = rand.nextInt(npcNames.size());
        String name = npcNames.get(randomNumber);
        npcNames.remove(randomNumber);
        return new NPC(name);
    }
}
