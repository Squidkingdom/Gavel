package com.squidkingdom.Gavel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class RoomManager {
    public static final Room DUMMY = new Room();
    public static ArrayList<Room> roomArray = new ArrayList<Room>(1);
    public RoomManager(){}


    public static void newRoom() {
        roomArray.add(new Room(roomArray.size() + 1));
    }

    public static Room getRoomById(int id){
        Optional<Room> optionalRoom = roomArray.stream().filter(Objects::nonNull).filter(e -> e.id == id).findAny();
        return optionalRoom.orElse(DUMMY);
    }

}
