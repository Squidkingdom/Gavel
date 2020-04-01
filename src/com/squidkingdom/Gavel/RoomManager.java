package com.squidkingdom.Gavel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class RoomManager {
    public static final Room DUMMY = new Room();
    public static Room byeRoom = new Room(0);
    public static ArrayList<Room> roomArray = new ArrayList<Room>(1);
    public static int roomArraySize = 0;
    public RoomManager(){}

    public static void newRoom() {
        roomArraySize++;
        roomArray.add(new Room(roomArraySize));
    }

    public static Room getRoomById(int id){
        Optional<Room> optionalRoom = roomArray.stream().filter(Objects::nonNull).filter(e -> e.id == id).findAny();
        return optionalRoom.orElse(DUMMY);
    }



    public static void refreshRoomNum(int size ){
        roomArray.clear();
        roomArraySize = 0;
        for (int i= 0; i < size; i++) {
            newRoom();
        }


    }

}
