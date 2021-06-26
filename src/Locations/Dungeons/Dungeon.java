package Locations.Dungeons;

import Locations.Location;
import Units.Enemy;

import java.util.ArrayList;

public class Dungeon extends Location {
    int numberOfEnemies = 25;
    ArrayList<Enemy> locationEnemies = new ArrayList<>();

    public Dungeon(String name){
        this.name = name;
    }

    public ArrayList<Enemy> getLocationEnemies() {
        return locationEnemies;
    }

    public void enemyDied(Enemy enemy){
        locationEnemies.remove(enemy);
    }
}
