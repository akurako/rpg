import Items.ItemGenerator;
import Locations.Dungeons.Dungeon;
import Locations.Dungeons.DungeonGenerator;
import Locations.Location;
import Locations.Towns.Town;
import Locations.Towns.TownGenerator;
import Units.Character;
import Units.Enemy;
import Units.EnemyGenerator;
import Units.Swordsman;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Game {
    Scanner userInput = new Scanner(System.in);
    TownGenerator townGenerator = new TownGenerator();
    ArrayList<Location> knownLocations = new ArrayList<>();
    ItemGenerator ig = new ItemGenerator();
    EnemyGenerator eg = new EnemyGenerator();
    DungeonGenerator dg = new DungeonGenerator();
    TownGenerator tg = new TownGenerator();
    Character hero;
    Enemy enemy;
    BattleField battle;
    Town currentTown;
    Location currentLocation;
    Boolean inTown;


//CHARACTER CREATION, SAVE AND LOADING----------------------------------------------------------------------------------

    private void createNewCharacterDialog() {
        String name;
        int type;
        System.out.println("Choose a name for a new hero.");
        name = userInput.nextLine();
        System.out.println("Choose hero specialization \n1. Swordsman\n2. Mage\n3.Archer");
        type = Integer.parseInt(userInput.nextLine());
        switch (type) {
            case 1 -> createNewCharacter(name, "swordsman");
            case 2 -> {
            }
            case 3 -> {
            }
        }
    }

    private void createNewCharacter(String name, String type) {
        switch (type) {
            case "swordsman" -> hero = new Swordsman(name);
        }
        initializeNewCharacter();
    }

    private void saveCharacter() {
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

    private void loadCharacterDialog() {
        File characterFile = null;
        final File saveDirectory = new File("SavedCharacters");
        if (saveDirectory.length() == 0) {
            System.out.println("You dont have any saved characters.");
        }
        while (characterFile == null && saveDirectory.list().length != 0) {
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

    private void loadCharacter(File characterFile) {
        try {
            FileInputStream fileIn = new FileInputStream(characterFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            hero = (Character) objectIn.readObject();
            fileIn.close();
            objectIn.close();

        } catch (InvalidClassException e) {
            e.printStackTrace();
            System.out.println("Savegame version and game version mismatch.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(hero.getName() + " Successfully loaded");
    }

    private void initializeNewCharacter() {
        hero.setHomeTown(townGenerator.generateTown());
        currentTown = hero.getHomeTown();
        currentLocation = currentTown;
        currentTown.townDungeon = dg.generateDungeon();
        dg.fillDungeon(hero, currentTown.townDungeon);
    }

//UNSORTED--------------------------------------------------------------------------------------------------------------

    private void mainMenuDialog() {
        System.out.println("Welcome to the game.\nPlease select:\n1. Create a new character.\n2. Load character.");
        switch (userInput.nextLine()) {
            case "1" -> createNewCharacterDialog();
            case "2" -> loadCharacterDialog();
            default -> System.out.println("Unknown Command");
        }
    }

    private void dungeonDialog() {

        if (enemy == null) {
            System.out.println(currentLocation.getName() + "\n 1. Explore\n 2. Go back to " + currentTown.getName());
            switch (userInput.nextLine()) {
                case "1" -> {exploreDungeon();
                }
                case "2" -> {
                    currentLocation = currentTown;
                }
                default -> { }
            }
        } else {
            System.out.println("Start a fight?\n 1. YES\n 2. RUN");
            switch (userInput.nextLine()){
                case "1" -> {startBattle();}
                case "2" -> {enemy = null;}
                default -> {}
        }
    }

}

    private void exploreDungeon() {
        Random rand = new Random();
        enemy = ((Dungeon) currentLocation).getLocationEnemies().get(rand.nextInt(((Dungeon) currentLocation).getLocationEnemies().size()));
        System.out.println("Enemy appeared " + enemy.getName() + " LVL:" + enemy.getLevel());
    }

    private void townDialog() {
        System.out.println("Welcome to the " + currentTown.getName() + " town.\n1. Go to " + currentTown.townDungeon.getName());
        switch (userInput.nextLine()) {
            case "1" -> {
                currentLocation = currentTown.townDungeon;
            }
        }
    }

    private void startBattle() {
        battle = new BattleField();
        battle.getScannerControl(userInput);
        battle.startBattle(hero, enemy);
        enemy = null;
        battle = null;
    }

    //MAIN GAME SYSTEM------------------------------------------------------------------------------------------------------
    public void startGame() {
        while (true) {
            if (hero == null) {
                mainMenuDialog();
            } else {
                if (battle == null) {
                    if (currentLocation instanceof Town) {
                        townDialog();
                    }
                    if (currentLocation instanceof Dungeon) {
                        dungeonDialog();
                    }
                }
            }
        }
    }
}

