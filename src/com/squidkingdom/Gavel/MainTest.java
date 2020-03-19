package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    // Check the TeamTest class for a much more comprehensive test that essentially tests the same thing.
    @Test
    void manualPairingAndAddingResultsWorks() {
        Main.manager.newTeam("t1", "s1", "s2");
        Team team1 = Main.manager.getTeamByCode("t1");
        Main.manager.newTeam("t2", "ss1", "ss2");
        Team team2 = Main.manager.getTeamByCode("t2");
        JudgeManager.newJudge("John Doe", "j1");
        Judge judge1 = JudgeManager.getJudgeByCode("j1");
        Room room1 = RoomManager.getRoomById(1);

        assertEquals(false, team1.roundComplete[0]);
        assertEquals(0, team1.rounds[0].affSpeaks);
        assertNull(team1.judges[0]);
        assertNull(team1.opp[0]);

        Main.pair(team1, team2, judge1, room1, 1);

        assertEquals(team2, team1.opp[0]);
        assertEquals(judge1, team1.judges[0]);
        assertEquals(team2 , team1.rounds[0].oppTeam);
        assertTrue(team1.rounds[0].side);
        assertFalse(team2.rounds[0].side);

        Main.selectedResult(1, true, "t1", "t2", 1 , 3, 2, 4);

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

        Main.selectedResult(1, true, "t1fds", "t2sd", 1 , 3, 2, 4);

    }
}