package Locations.Dungeons;

import Locations.Location;
import Units.Enemy;

import java.util.ArrayList;
import java.util.Random;

public class Dungeon extends Location {
    final int maxNumberOfEnemies = 25;
    final int minNumberOfEnemies = 10;
    int numberOfEnemies;
    int dungeonGoldReward;
    public Boolean finished;
    ArrayList<Enemy> locationEnemies = new ArrayList<>();

    public void setDungeonGoldReward(int dungeonGoldReward) {
        this.dungeonGoldReward = dungeonGoldReward;
    }

    public int getDungeonGoldReward() {
        return dungeonGoldReward;
    }

    public Dungeon(String name) {
        this.name = name;
        finished = false;
        numberOfEnemies = minNumberOfEnemies + (new Random().nextInt(maxNumberOfEnemies - minNumberOfEnemies));
    }

    public ArrayList<Enemy> getLocationEnemies() {
        return locationEnemies;
    }

    public void enemyDied(Enemy enemy) {
        locationEnemies.remove(enemy);
        if (locationEnemies.size() == 0) {
            this.finished = true;
        }
    }
}
