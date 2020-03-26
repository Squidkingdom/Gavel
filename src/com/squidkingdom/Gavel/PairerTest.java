package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class PairerTest {

    @Test
    void pairingWorks() throws GavelExeception, IOException {
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
        JudgeManager.newJudge("James Doe", "j7");
        JudgeManager.newJudge("James Doe", "j8");

        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();

        ArrayList<RoundData> pairings = Pairer.pairRound1();
        Exporter.exportSchedule(pairings,"schedule1");
        pairings.stream().filter(roundData -> !roundData.judge.code.equalsIgnoreCase("bye")).forEach(roundData -> Main.selectedResult(roundData.room, true, roundData.affTeam.code, roundData.negTeam.code, 1, 3, 2, 4));

        ArrayList<RoundData> pairings2 = Pairer.pairRound2();
        Exporter.exportSchedule(pairings2,"schedule2");
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

        ArrayList<RoundData> pairings3 = Pairer.pairRound3();
        Exporter.exportSchedule(pairings3,"schedule3");
        pairings3.stream().filter(roundData -> !roundData.judge.code.equalsIgnoreCase("bye")).forEach(roundData -> Main.selectedResult(roundData.room, true, roundData.affTeam.code, roundData.negTeam.code, 1, 3, 2, 4));

        for (RoundData round : pairings3) {
            assertNotEquals(round.affTeam.opp[2], round.affTeam.opp[1]);
            if (!round.negTeam.code.equalsIgnoreCase("bye"))
                assertNotEquals(round.negTeam.opp[2], round.negTeam.opp[1]);

            assertNotEquals(round.affTeam.judges[2], round.affTeam.judges[1]);
            if (!round.negTeam.code.equalsIgnoreCase("bye"))
                assertNotEquals(round.negTeam.judges[2], round.negTeam.judges[1]);

            assertTrue(round.affTeam.totalWins >= 2);
            if (!round.negTeam.code.equalsIgnoreCase("bye"))
                assertTrue(round.negTeam.totalWins >= 1);

            assertTrue(round.affTeam.totalSpeaks >= 11 && round.affTeam.totalSpeaks <= 16);
            if (!round.negTeam.code.equalsIgnoreCase("bye"))
                assertTrue(round.negTeam.totalSpeaks >= 13);
        }

        Exporter.exportRounds("rounds1");
    }
}