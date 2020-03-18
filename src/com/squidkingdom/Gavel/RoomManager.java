package com.squidkingdom.Gavel;

public class RoomManager {
    public static Room[] roomArray = new Room[11];
    public static int JIDArrayLength = 0;
    public RoomManager(){}
    public static Room dummy;

    public static void newRoom() {
        roomArray[JIDArrayLength] = new Room(JIDArrayLength + 1);
        JIDArrayLength++;
    }

    public static Room getRoomById(int id){

        for (int i = 0; i < JIDArrayLength + 1; i++) {
            if (roomArray[i].id == id) {
                return roomArray[i];
            }

        }
        return dummy;
    }

}
