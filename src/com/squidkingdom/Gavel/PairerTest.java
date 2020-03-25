package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

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
    void roundTwoPairingWorks() throws GavelExeception {
        Main.manager.newTeam("t1", "s1", "s2");
        Main.manager.newTeam("t2", "s1", "s2");
        Main.manager.newTeam("t3", "s1", "s2");
        Main.manager.newTeam("t4", "s1", "s2");
        Main.manager.newTeam("t5", "s1", "s2");
        Main.manager.newTeam("t6", "s1", "s2");
        Main.manager.newTeam("t7", "s1", "s2");
        JudgeManager.newJudge("John Doe", "j1");
        JudgeManager.newJudge("Jane Doe", "j2");
        JudgeManager.newJudge("James Doe", "j3");
        JudgeManager.newJudge("John Doe", "j4");
        JudgeManager.newJudge("Jane Doe", "j5");
        JudgeManager.newJudge("James Doe", "j6");
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();

        ArrayList<RoundData> pairings = Pairer.pairRound1();
        pairings.stream().filter(roundData -> !roundData.judge.code.equalsIgnoreCase("bye")).forEach(roundData -> Main.selectedResult(roundData.room, true, roundData.affTeam.code, roundData.negTeam.code, 1, 3, 2, 4));

        ArrayList<RoundData> pairings2 = Pairer.pairRound2();
        pairings2.stream().filter(roundData -> !roundData.judge.code.equalsIgnoreCase("bye")).forEach(roundData -> Main.selectedResult(roundData.room, true, roundData.affTeam.code, roundData.negTeam.code, 1, 3, 2, 4));

        for (RoundData round : pairings2) {
            // TODO: Make sure that byes are accounted for and work here
            assertNotEquals(round.affTeam.opp[0], round.affTeam.opp[1]);
            assertNotEquals(round.negTeam.opp[0], round.negTeam.opp[1]);

            assertNotEquals(round.affTeam.judges[0], round.affTeam.judges[1]);
            assertNotEquals(round.negTeam.judges[0], round.negTeam.judges[1]);

            assertEquals(1, round.affTeam.totalWins);
            assertEquals(1, round.negTeam.totalWins);

            assertTrue(round.affTeam.totalSpeaks >= 7);
            assertTrue(round.negTeam.totalSpeaks >= 7);
        }

    }
}