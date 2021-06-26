import Interfaces.Colors;
import Items.ItemGenerator;
import Locations.Dungeons.Dungeon;
import Locations.Dungeons.DungeonGenerator;
import Locations.Location;
import Locations.Towns.Town;
import Locations.Towns.TownGenerator;
import Units.*;
import Units.Character;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


public class Game {
    public static Scanner userInput = new Scanner(System.in);
    static TownGenerator townGenerator = new TownGenerator();
    ArrayList<Location> knownLocations = new ArrayList<>();
    static ItemGenerator ig = new ItemGenerator();
    static EnemyGenerator eg = new EnemyGenerator();
    static DungeonGenerator dg = new DungeonGenerator();
    public static Random random = new Random();
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
        clearScreen();
        System.out.println(Colors.CYAN_BOLD + "Choose a name for your hero." + Colors.RESET);
        name = userInput.nextLine();
        clearScreen();
        System.out.println(Colors.CYAN_BOLD + "Choose hero specialization " + Colors.CYAN + " \n 1. Swordsman\n 2. Mage (Work in progress)\n 3.Archer (Work in progress)" + Colors.RESET);
        type = Integer.parseInt(userInput.nextLine());
        clearScreen();
        switch (type) {
            case 1 -> createNewCharacter(name, "swordsman");
            case 2 -> createNewCharacter(name, "mage");
            case 3 -> createNewCharacter(name, "archer");
        }
    }

    private void createNewCharacter(String name, String type) {
        switch (type) {
            case "swordsman" -> hero = new Swordsman(name);
            case "mage" -> hero = new Mage(name);
            case "archer" -> hero = new Archer(name);
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
        System.out.println(Colors.CYAN_BOLD + "Welcome to the game." + Colors.CYAN + "\n 1. Create a new character.\n 2. Load character." + Colors.RESET);
        switch (userInput.nextLine()) {
            case "1" -> createNewCharacterDialog();
            case "2" -> loadCharacterDialog();
            default -> System.out.println("Unknown Command");
        }
    }

    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
    }

    private void dungeonDialog() {

        if (enemy == null) {
            System.out.println(Colors.CYAN + currentLocation.getName() + "\n 1. Explore\n 2. Go back to " + currentTown.getName() + Colors.RESET);
            switch (userInput.nextLine()) {
                case "1" -> exploreDungeon();
                case "2" -> currentLocation = currentTown;
                default -> {
                }
            }
        } else {
            System.out.println(Colors.CYAN + "Start a fight?\n 1. YES\n 2. RUN" + Colors.RESET);
            switch (userInput.nextLine()) {
                case "1" -> startBattle();
                case "2" -> enemy = null;
                default -> {
                }
            }
        }

    }

    private void exploreDungeon() {
        Random rand = new Random();
        enemy = ((Dungeon) currentLocation).getLocationEnemies().get(rand.nextInt(((Dungeon) currentLocation).getLocationEnemies().size()));
        System.out.println(Colors.RED + "Enemy appeared " + enemy.getName() + " LVL:" + enemy.getLevel() + Colors.RESET);
    }

    private void townDialog() {
        System.out.println(Colors.CYAN + "Welcome to the " + currentTown.getName() + " town.\n1. Go to " + currentTown.townDungeon.getName() + Colors.RESET);
        switch (userInput.nextLine()) {
            case "1" -> currentLocation = currentTown.townDungeon;
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

