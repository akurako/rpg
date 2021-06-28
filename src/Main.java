import Interfaces.Colors;
import Units.Character;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    static Scanner userInput = new Scanner(System.in);
    static Game game;
    static Boolean closeGame = false;

    private static void loadCharacterDialog() {
        File characterFile = null;
        final File saveDirectory = new File("SavedCharacters");
        if (saveDirectory.list().length == 0) {
            System.out.println(Colors.RED_BOLD + "You dont have any saved characters." + Colors.RESET);
        }
        while (characterFile == null && saveDirectory.list().length != 0) {
            System.out.println(Colors.YELLOW_BOLD + "Choose character you want to load:\n" + Arrays.toString(saveDirectory.list()) + Colors.RESET);
            characterFile = new File(saveDirectory + "/" + userInput.nextLine());
            if (!characterFile.exists()) {
                System.out.println(Colors.RED_BOLD + "Incorrect character name." + Colors.RESET);
                characterFile = null;
            } else {
                loadCharacter(characterFile);
                game.startGame();
            }
        }
    }

    private static void loadCharacter(File characterFile) {
        try {
            FileInputStream fileIn = new FileInputStream(characterFile);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Character hero = (Character) objectIn.readObject();
            game = (Game) hero.getSavedGameState();
            fileIn.close();
            objectIn.close();

        } catch (InvalidClassException e) {
            e.printStackTrace();
            System.out.println(Colors.RED_BOLD + "Savegame version and game version mismatch." + Colors.RESET);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(Colors.YELLOW_BOLD + game.hero.getName() + " Successfully loaded" + Colors.RESET);
    }

    private static void mainMenuDialog() {
        System.out.println(Colors.CYAN_BOLD + "Welcome to the game." + Colors.CYAN + "\n 1. Create a new character.\n 2. Load character.\n 3. Exit game." + Colors.RESET);
        switch (userInput.nextLine()) {
            case "1" -> {
                game = new Game();
                game.startGame();
            }
            case "2" -> loadCharacterDialog();
            case "3" -> closeGame = true;
            default -> System.out.println(Colors.RED_BOLD + "Unknown Command" + Colors.RESET);
        }
    }

    public static void main(String[] args) {
        while (!closeGame) {
            mainMenuDialog();
        }

    }
}

//TODO OPTIMIZE CODE BEFORE DEADLINE

//TODO PASSIVE , ACTIVE SKILLS
//TODO WEARABLES ARMOR WEAPONS ETC + PRICES
//TODO INVENTORY MENU + WEIGHT
//TODO DROP SYSTEM WEARABLES (LOW CHANCE)
//TODO TELEPORT BETWEEN CITIES
//TODO SKILLBOOKS DROP
