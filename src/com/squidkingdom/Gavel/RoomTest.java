package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

   @Test
    void roomWorks() {
       Room room = new Room(2);
       assertEquals(2, room.id);
       assertEquals(0, room.data[1].affSpeaks);

       room.data[3] = new RoundData(new Team("t1", "s1", "s2"), new Team("t2", "s3", "s4"), new Judge("John Doe", "TEST"), 0);
       assertEquals("t1", room.data[3].affTeam.code);
       assertEquals("s4", room.data[3].negTeam.person2);
       assertEquals("TEST", room.data[3].judge.code);
   }


}