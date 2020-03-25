package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class PairerTest {

    @Test
    void roundOnePairingWorks() {
        TeamManager.newTeam("t1", "s1", "s2");
        TeamManager.newTeam("t2", "s1", "s2");
        TeamManager.newTeam("t3", "s1", "s2");
        TeamManager.newTeam("t4", "s1", "s2");
        TeamManager.newTeam("t5", "s1", "s2");
        TeamManager.newTeam("t6", "s1", "s2");
        TeamManager.newTeam("t7", "s1", "s2");

        RoomManager.newByeRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();
        JudgeManager.newJudge("John Doe", "j1");
        JudgeManager.newJudge("Jane Doe", "j2");
        JudgeManager.newJudge("James Doe", "j3");


        assertFalse(TeamManager.getTeamByCode("t1").inProgress[0]);
        assertFalse(TeamManager.getTeamByCode("t2").inProgress[0]);
        assertFalse(TeamManager.getTeamByCode("t3").inProgress[0]);
        assertFalse(TeamManager.getTeamByCode("t4").inProgress[0]);
        assertFalse(TeamManager.getTeamByCode("t5").inProgress[0]);
        assertFalse(TeamManager.getTeamByCode("t6").inProgress[0]);
        assertFalse(TeamManager.getTeamByCode("t7").inProgress[0]);

        try {
            ArrayList<RoundData> pairings = Pairer.pairRound1();
            int a = 1;
        } catch (GavelExeception exception) {
            System.out.println(exception);
        }

    }

    @Test
    void roundTwoPairingWorks() throws GavelExeception, IOException {
        Main.manager.newTeam("t1", "s1", "s2");
        Main.manager.newTeam("t2", "s1", "s2");
        Main.manager.newTeam("t3", "s1", "s2");
        Main.manager.newTeam("t4", "s1", "s2");
        Main.manager.newTeam("t5", "s1", "s2");
        Main.manager.newTeam("t6", "s1", "s2");
        Main.manager.newTeam("t7", "s1", "s2");
        Main.manager.newTeam("t8", "s1", "s2");
        Main.manager.newTeam("t9", "s1", "s2");
        JudgeManager.newJudge("John Doe", "j1");
        JudgeManager.newJudge("Jane Doe", "j2");
        JudgeManager.newJudge("James Doe", "j3");
        JudgeManager.newJudge("John Doe", "j4");
        JudgeManager.newJudge("Jane Doe", "j5");
        JudgeManager.newJudge("James Doe", "j6");
        RoomManager.newByeRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();

        ArrayList<RoundData> pairings = Pairer.pairRound1();
        Exporter.exportSchedule(pairings,"schedule1");
        pairings.stream().filter(roundData -> !roundData.judge.code.equalsIgnoreCase("bye")).forEach(roundData -> Main.selectedResult(roundData.room, true, roundData.affTeam.code, roundData.negTeam.code, 1, 3, 2, 4));

        ArrayList<RoundData> pairings2 = Pairer.pairRound2();
        Exporter.exportSchedule(pairings,"schedule2");
        pairings2.stream().filter(roundData -> !roundData.judge.code.equalsIgnoreCase("bye")).forEach(roundData -> Main.selectedResult(roundData.room, true, roundData.affTeam.code, roundData.negTeam.code, 1, 3, 2, 4));

        for (RoundData round : pairings2) {
            assertNotEquals(round.affTeam.opp[0], round.affTeam.opp[1]);
            if (!round.negTeam.code.equalsIgnoreCase("bye"))
                assertNotEquals(round.negTeam.opp[0], round.negTeam.opp[1]);

            assertNotEquals(round.affTeam.judges[0], round.affTeam.judges[1]);
            if (!round.negTeam.code.equalsIgnoreCase("bye"))
                assertNotEquals(round.negTeam.judges[0], round.negTeam.judges[1]);

            assertTrue(round.affTeam.totalWins == 1 || (round.affTeam.hasHadBye == true && round.affTeam.totalWins >= 1 && round.affTeam.totalWins <= 2));
            if (!round.negTeam.code.equalsIgnoreCase("bye"))
                assertEquals(1, round.negTeam.totalWins);

            assertTrue(round.affTeam.totalSpeaks >= 7 && round.affTeam.totalSpeaks <= 10);
            if (!round.negTeam.code.equalsIgnoreCase("bye"))
                assertTrue(round.negTeam.totalSpeaks >= 7);
        }

        Exporter.exportRounds("rounds1");
    }
}