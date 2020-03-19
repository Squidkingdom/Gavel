package com.squidkingdom.Gavel;

public class Room {
    int id;

    RoundData tr = new RoundData();

    RoundData[] data = new RoundData[]{tr,tr,tr,tr,tr};;

    public Room(int id) {
        this.id = id;
    }
}
