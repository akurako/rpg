import Units.Character;
import Units.Unit;


public class Game {
    private Character hero;
    private Unit enemy;
    private int[] expTable = new int[100];

    public Game() {
        generateExpTable();
    }

    private void generateExpTable() {
        double startvalue = 100;
        double modificator = 1.15;
        for (int i = 0; i < 100; i++) {
            expTable[i] = (int) Math.round(startvalue);
            startvalue = startvalue * modificator;
        }
    }

    private void checkLevelUp(){
        if (hero.getExperience() > expTable[hero.getLevel()-1]){
            hero.lvlUp();
        }
    }

    public void createNewCharacter(String name){

    }

}
