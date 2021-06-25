package Locations.Dungeons;

import Units.Enemy;

import java.util.ArrayList;

public class Dungeon {
    String name;
    ArrayList<Enemy> locationEnemies = new ArrayList<>();

    public Dungeon(String name){
        this.name = name;
    }


}
