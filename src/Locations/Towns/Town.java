package Locations.Towns;
import Locations.Dungeons.Dungeon;
import Locations.Location;
import Units.NPC;

public class Town extends Location {
    public Dungeon townDungeon;
    public NPC townAlchemist;
    public Town(String name){
        this.name = name;
    }

}
