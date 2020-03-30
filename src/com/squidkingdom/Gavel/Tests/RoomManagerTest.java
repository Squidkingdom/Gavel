package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomManagerTest {

    @Test
    void newRoom() {
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();

        assertEquals(3, RoomManager.roomArray.size());
        assertEquals(1, RoomManager.roomArray.get(0).id);
        assertEquals(2, RoomManager.roomArray.get(1).id);
        assertEquals(3, RoomManager.roomArray.get(2).id);
    }

    @Test
    void getRoomById() {
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();

        RoomManager.roomArray.get(3).data[2].affSpeaks = 4;
        assertEquals(4, RoomManager.getRoomById(4).data[2].affSpeaks);
        assertEquals(RoomManager.DUMMY.id, RoomManager.getRoomById(8).id);
        assertEquals(RoomManager.DUMMY.id, RoomManager.getRoomById(69).id);
    }
}