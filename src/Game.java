import Interfaces.Colors;
import Items.Item;
import Items.ItemGenerator;
import Items.Potion;
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


public class Game implements Serializable {
    public static Scanner userInput = new Scanner(System.in);
    static TownGenerator townGenerator = new TownGenerator();
    ArrayList<Location> knownLocations = new ArrayList<>();
    static NPCGenerator ng = new NPCGenerator();
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
    Boolean gameRunning = true;


//CHARACTER CREATION, SAVE AND LOADING----------------------------------------------------------------------------------

    private void createNewCharacterDialog() {
        String name;
        int type;
        clearScreen();
        System.out.println(Colors.CYAN_BOLD + "Choose a name for your hero." + Colors.RESET);
        name = userInput.nextLine();
        clearScreen();
        System.out.println(Colors.CYAN_BOLD + "Choose hero specialization " + Colors.CYAN + " \n 1. Swordsman\n 2. Mage (Work in progress)\n 3. Archer (Work in progress)" + Colors.RESET);
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
            hero.setSavedGameState(this);
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(hero);
            objectOut.close();
            System.out.println("Hero " + hero.getName() + " saved.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void initializeNewCharacter() {
        hero.setHomeTown(townGenerator.generateTown());
        currentTown = hero.getHomeTown();
        currentLocation = currentTown;
        currentTown.townDungeon = dg.generateDungeon(hero);
        currentTown.townAlchemist = ng.generateNPC();
        knownLocations.add(currentTown);
    }

//UNSORTED--------------------------------------------------------------------------------------------------------------
    public void travelToNextTown(){
        currentTown = tg.generateTown();
        currentLocation = currentTown;
        knownLocations.add(currentTown);
        currentTown.townAlchemist = ng.generateNPC();
        currentTown.townDungeon = dg.generateDungeon(hero);
    }

    public void statsAvailableDialog() {
        Boolean closeDialog = false;
        while (hero.getStatsAvailable() > 0 && !closeDialog) {
            System.out.println(Colors.CYAN_BOLD + "You have " + hero.getStatsAvailable() + " unallocated points left." + Colors.CYAN + "\n 1. Strength [" + hero.getStrength() + "]\n 2. Agility [" + hero.getAgility() + "]\n 3. Intellect [" + hero.getIntellect() + "]\n 4. Back.");
            switch (userInput.nextLine()) {
                case "1" -> {
                    hero.setStrength(hero.getStrength() + 1);
                    hero.recalculateStats();
                    hero.setStatsAvailable(hero.getStatsAvailable() - 1);
                }
                case "2" -> {
                    hero.setAgility(hero.getAgility() + 1);
                    hero.recalculateStats();
                    hero.setStatsAvailable(hero.getStatsAvailable() - 1);
                }
                case "3" -> {
                    hero.setIntellect(hero.getIntellect() + 1);
                    hero.recalculateStats();
                    hero.setStatsAvailable(hero.getStatsAvailable() - 1);
                }
                default -> closeDialog = true;
            }
        }
    }

    public void characterMenu() {
        String statsNum;
        long criticalChance = hero.getCriticalChance() / 10;
        long dodgeChance = hero.getDodgeChance() / 10;
        System.out.println(hero.getHeroStatus());
        hero.showGoldAmount();
        System.out.println(Colors.CYAN_BOLD + " Main Stats:\n" + Colors.CYAN + " Strength [" + hero.getStrength() + "]\n Agility [" + hero.getAgility() + "]\n Intellect [" + hero.getIntellect() + "]\n Unallocated Stats [" + hero.getStatsAvailable() + "]" + Colors.RESET);
        System.out.println(Colors.CYAN_BOLD + " Secondary Stats:\n" + Colors.CYAN + " Dodge chance: " + dodgeChance + "%\n Critical chance: "+criticalChance + "%");
        if (hero.getStatsAvailable() == 0) {
            statsNum = "999";
            System.out.println(Colors.CYAN + " 1. Back");
        } else {
            statsNum = "1";
            System.out.println(" 1. Relocate available stats.");
            System.out.println(" 2. Back." + Colors.RESET);
        }
        if (userInput.nextLine().equals(statsNum)) {
            statsAvailableDialog();
        }
    }

    public void sellPotionDialog() {
        hero.showGoldAmount();
        int inputNumber = 1;
        ArrayList<String> potionList = new ArrayList<>();
        for (Item item : hero.getInventory()) {
            if (item instanceof Potion) {
                potionList.add(item.getName());
                System.out.println(Colors.CYAN + inputNumber + ". " + item.getName() + " [" + ((Potion) item).getCount() + "][Sell price: " + item.getSellPrice() + " gold]");
                inputNumber++;
            }
        }
        if (potionList.size() != 0) {
            System.out.println(inputNumber + ". Back" + Colors.RESET);
        }
        if (potionList.size() > 0) {
            int chosenItem = Integer.parseInt(userInput.nextLine());

            if (chosenItem <= potionList.size()) {
                for (Item item : hero.getInventory()) {
                    if (item.getName().equals(potionList.get(chosenItem - 1))) {

                        sellItem(item);
                        break;
                    }
                }
            }
        } else {
            System.out.println(Colors.YELLOW + "You dont have any potions." + Colors.RESET);
        }
    }

    private void sellItem(Item item) {
        if (item instanceof Potion) {
            if (((Potion) item).getCount() > 1) {
                ((Potion) item).useOne();
            } else {
                hero.removeFromInventory(item);
            }
            hero.addGold(item.getSellPrice());
        } else {
            hero.addGold(item.getSellPrice());
            hero.removeFromInventory(item);
        }
    }

    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
    }

    private void dungeonDialog() {

        if (enemy == null) {
            System.out.println(Colors.CYAN_BOLD + "You entering " + currentLocation.getName() + Colors.CYAN + "\n 1. Explore\n 2. Go back to " + currentTown.getName() + Colors.RESET);
            switch (userInput.nextLine()) {
                case "1" -> exploreDungeon();
                case "2" -> currentLocation = currentTown;
                default -> {
                }
            }
        } else {
            System.out.println(Colors.CYAN_BOLD + "Start a fight? " + Colors.CYAN + "\n 1. YES\n 2. RUN" + Colors.RESET);
            switch (userInput.nextLine()) {
                case "1" -> startBattle();
                case "2" -> enemy = null;
                default -> {
                }
            }
        }

    }

    private void exploreDungeon() {
        if (((Dungeon) currentLocation).finished){
            System.out.println(Colors.CYAN_BOLD + "You cleared this dungeon, nothing to do here for now." + Colors.RESET);
        } else {
            Random rand = new Random();
            enemy = ((Dungeon) currentLocation).getLocationEnemies().get(rand.nextInt(((Dungeon) currentLocation).getLocationEnemies().size()));
            System.out.println(Colors.RED + "Enemy appeared " + enemy.getName() + " LVL:" + enemy.getLevel() + Colors.RESET);
        }
    }

    private void cityDoctorDialog() {
        boolean closeDialog = false;
        while (!closeDialog) {
            System.out.println(Colors.CYAN_BOLD + "Town alchemist " + currentTown.townAlchemist.getName() + " welcomes you." + Colors.CYAN + "\n 1. Buy potions.\n 2. Sell potions.\n 3. Get healed. [100 gold]\n 4. Back to town." + Colors.RESET);
            switch (userInput.nextLine()) {
                case "1" -> buyPotionsDialog();
                case "2" -> sellPotionDialog();
                case "3" -> getHealed();
                default -> closeDialog = true;
            }
        }
    }

    private void buyPotionsDialog() {
        hero.showGoldAmount();
        int inputNumber = 1;
        ArrayList<String> potionList = new ArrayList<>();
        for (Item item : currentTown.townAlchemist.getShopItems()) {
            if (item instanceof Potion) {
                potionList.add(item.getName());
                System.out.println(Colors.CYAN + inputNumber + ". " + item.getName() + " [" + ((Potion) item).getCount() + "][Buy price: " + item.getBuyPrice() + " gold]");
                inputNumber++;
            }
        }
        System.out.println(inputNumber + ". Back" + Colors.RESET);
        if (potionList.size() > 0) {
            int chosenItem = Integer.parseInt(userInput.nextLine());

            if (chosenItem <= potionList.size()) {
                for (Item item : currentTown.townAlchemist.getShopItems()) {
                    if (item.getName().equals(potionList.get(chosenItem - 1))) {
                        if (hero.payGold(item.getBuyPrice())) {
                            hero.addToInventory(item);
                            currentTown.townAlchemist.removeFromShop(item);
                        }
                        break;
                    }
                }
            }
        } else {
            System.out.println("Store is empty");
        }
    }

    private void getHealed() {
        if (hero.payGold(100)) {
            hero.restoreHpMp();
        }
    }

    private void townDialog() {
        System.out.println(Colors.CYAN_BOLD + "Welcome to the " + currentTown.getName() + " town." + Colors.CYAN + "" +

                "\n 1. Go to town alchemist." +
                "\n 2. Go to " + currentTown.townDungeon.getName() + "." +
                "\n 3. Character info." +
                "\n 4. Save character and exit to main menu." );
        if (currentTown.townDungeon.finished){
            System.out.println(" 5. Travel to the next town." + Colors.RESET);
        }
        switch (userInput.nextLine()) {
            case "1" -> cityDoctorDialog();
            case "2" -> currentLocation = currentTown.townDungeon;
            case "3" -> characterMenu();
            case "4" -> {
                saveCharacter();
                gameRunning = false;
            }
            case "5" -> {
                if (currentTown.townDungeon.finished) {
                    travelToNextTown();
                }
            }
        }
    }

    private void startBattle() {
        battle = new BattleField();
        battle.getScannerControl(userInput);
        if (battle.startBattle(hero, enemy)){
            ((Dungeon) currentLocation).enemyDied(enemy);
            if (((Dungeon) currentLocation).finished){
                //TODO GET REWARD
            }
        }
        enemy = null;
        battle = null;
        if (hero.getStatsAvailable() > 0) {
            statsAvailableDialog();
        }
    }

    //MAIN GAME SYSTEM------------------------------------------------------------------------------------------------------
    public void startGame() {
        gameRunning = true;
        while (gameRunning) {
            if (hero == null) {
                createNewCharacterDialog();
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

