package Locations.Towns;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TownGenerator {
    ArrayList<String> townNames = new ArrayList<>();
    Random rand = new Random();

    public TownGenerator(){
        File townNamesFile = new File("data/townnames.txt");
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(townNamesFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(townNamesFile + "Not found.");
        }
        Scanner sc = new Scanner(fis);
        while (sc.hasNext()){
            townNames.add(sc.nextLine());
        }
    }
    public Town generateTown(){
        int randomNumber = rand.nextInt(townNames.size());
        String name = townNames.get(randomNumber);
        townNames.remove(randomNumber);
        return new Town(name);
    }
}
