package com.squidkingdom.Gavel;

public class Room {
    int id;
    public static final RoundData DUMMY = new RoundData();
    RoundData[] data = new RoundData[] {new RoundData(), new RoundData(), new RoundData(), new RoundData(), new RoundData()};

    public Room(int id) {
        this.id = id;
    }
    public Room() {}
}
