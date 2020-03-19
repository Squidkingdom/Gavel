package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamTest {

    @Test
    void teamWorks() {
        Main.manager.newTeam("t1", "s1", "s2");
        Team team1 = Main.manager.getTeamByCode("t1");
        Main.manager.newTeam("t2", "ss1", "ss2");
        Team team2 = Main.manager.getTeamByCode("t2");
        JudgeManager.newJudge("John Doe", "j1");
        JudgeManager.newJudge("Jane Doe", "j2");
        Judge judge1 = JudgeManager.getJudgeByCode("j1");
        Judge judge2 = JudgeManager.getJudgeByCode("j2");
        Room room1 = RoomManager.getRoomById(1);
        Room room2 = RoomManager.getRoomById(2);
        int roomNum1 = 1;
        int roomNum2 = 2;
        int event1 = 1;
        int event2 = 2;

        // Event 1

        assertEquals(false, team1.roundComplete[0]);
        assertEquals(0, team1.rounds[0].affSpeaks);
        assertNull(team1.judges[0]);
        assertNull(team1.opp[0]);

        Main.pair(team1, team2, judge1, room1, event1);

        assertEquals(team2, team1.opp[0]);
        assertEquals(judge1, team1.judges[0]);
        assertEquals(team2 , team1.rounds[0].oppTeam);
        assertTrue(team1.rounds[0].side);
        assertFalse(team2.rounds[0].side);

        Main.selectedResult(roomNum1, true, "t1", "t2", 1 , 3, 2, 4);

        assertTrue(team1.roundComplete[0]);
        assertTrue(team1.rounds[0].didWin);
        assertFalse(team2.rounds[0].didWin);
        assertEquals(team2, team1.opp[0]);
        assertEquals(judge1, team1.judges[0]);
        assertEquals(team2 , team1.rounds[0].oppTeam);

        assertTrue(room1.data[0].affWon);
        assertEquals(team1, room1.data[0].affTeam);
        assertEquals(team2, room1.data[0].negTeam);
        assertEquals(1, team1.totalWins);
        assertEquals(0, team2.totalWins);
        assertEquals(4, team1.totalSpeaks);
        assertEquals(6, team2.totalSpeaks);

        // Event 2

        assertEquals(false, team1.roundComplete[1]);
        assertEquals(0, team1.rounds[1].affSpeaks);
        assertNull(team1.judges[1]);
        assertNull(team1.opp[1]);

        Main.pair(team1, team2, judge2, room2, event2);

        assertEquals(team2, team1.opp[1]);
        assertEquals(judge2, team1.judges[1]);
        assertEquals(team2 , team1.rounds[1].oppTeam);
        assertTrue(team1.rounds[1].side);
        assertFalse(team2.rounds[1].side);

        Main.selectedResult(roomNum2, false, "t1", "t2", 4, 2, 1, 3);

        assertTrue(team1.roundComplete[1]);
        assertTrue(team2.rounds[1].didWin);
        assertFalse(team1.rounds[1].didWin);
        assertEquals(team1, team2.opp[1]);
        assertEquals(judge2, team1.judges[1]);
        assertEquals(team2 , team1.rounds[1].oppTeam);

        assertFalse(room2.data[1].affWon);
        assertEquals(team1, room1.data[1].affTeam);
        assertEquals(team2, room1.data[1].negTeam);
        assertEquals(1, team1.totalWins);
        assertEquals(1, team2.totalWins);
        assertEquals(10, team1.totalSpeaks);
        assertEquals(10, team2.totalSpeaks);
    }

}