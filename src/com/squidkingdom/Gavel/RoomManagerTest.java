package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomManagerTest {

    @Test
    void newRoom() {
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();

        assertEquals(3, RoomManager.JIDArrayLength);
        assertEquals(1, RoomManager.roomArray[0].id);
        assertEquals(2, RoomManager.roomArray[1].id);
        assertEquals(3, RoomManager.roomArray[2].id);

    }

    @Test
    void getRoomById() {
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();

    }
}