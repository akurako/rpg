package Locations.Towns;
import Locations.Dungeons.Dungeon;
import Locations.Location;

public class Town extends Location {
    public Dungeon townDungeon;
    public Town(String name){
        this.name = name;
    }

}
