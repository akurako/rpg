import Units.Character;
import Units.Enemy;
import Units.Swordsman;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Game {
    Scanner userInput = new Scanner(System.in);
    Character hero;
    Enemy enemy;


    public void generateEnemy() {
        Random random = new Random();
        int max = 3;
        int min = -3;
        int generatedLevel = hero.getLevel() + random.nextInt((max - min) + 1) + min;
        if (generatedLevel < 1) {
            generatedLevel = 1;
        }
        enemy = new Enemy(generatedLevel);
    }

    public void createNewCharacterDialog() {
        String name;
        int type;
        System.out.println("Choose a name for a new hero.");
        name = userInput.nextLine();
        System.out.println("Choose hero specialization \n1. Swordsman\n2. Mage\n3.Archer");
        type = Integer.parseInt(userInput.nextLine());
        switch (type) {
            case 1 -> createNewCharacter(name, "swordsman");
            case 2 -> { }
            case 3 -> { }
        }
    }

    public void createNewCharacter(String name, String type) {
        switch (type) {
            case "swordsman" -> hero = new Swordsman(name);
        }
    }

    public void saveCharacter() {
        File filename = new File("SavedCharacters/" + hero.getName());
        final File saveDirectory = new File("SavedCharacters");
        if (!saveDirectory.exists()) {
            saveDirectory.mkdir();
        }
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(hero);
            objectOut.close();
            System.out.println("Hero " + hero.getName() + " saved.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void loadCharacterDialog() {
        File characterFile = null;
        final File saveDirectory = new File("SavedCharacters");
        while (characterFile == null) {
            System.out.println("Choose character you want to load:\n" + Arrays.toString(saveDirectory.list()));
            characterFile = new File(saveDirectory + "/" + userInput.nextLine());
            if (!characterFile.exists()) {
                System.out.println("Incorrect character name.");
                characterFile = null;
            } else {
                loadCharacter(characterFile);
            }
        }
    }

    public void loadCharacter(File characterFile){
        try {
            FileInputStream fileIn = new FileInputStream(characterFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            hero = (Character) objectIn.readObject();
            fileIn.close();
            objectIn.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(hero.getName()+" Successfully loaded");
    }

    public void startGame() {
        while (true) {
            if (hero == null) {
                createNewCharacterDialog();
                saveCharacter();
                loadCharacterDialog();
            }
        }
    }
}

