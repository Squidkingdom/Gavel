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

//        assertTrue(TeamManager.getTeamByCode("t1").inProgress[0]);
//        assertTrue(TeamManager.getTeamByCode("t2").inProgress[0]);
//        assertTrue(TeamManager.getTeamByCode("t3").inProgress[0]);
//        assertTrue(TeamManager.getTeamByCode("t4").inProgress[0]);
//        assertFalse(TeamManager.getTeamByCode("t5").inProgress[0]);
    }

//    @Test
//    void roundTwoPairingWorks() {
//        Main.manager.newTeam("t1", "s1", "s2");
//        Team team1 = Main.manager.getTeamByCode("t1");
//        Main.manager.newTeam("t2", "ss1", "ss2");
//        Team team2 = Main.manager.getTeamByCode("t2");
//        Main.manager.newTeam("t3", "ss1", "ss2");
//        Team team3 = Main.manager.getTeamByCode("t3");
//        Main.manager.newTeam("t4", "ss1", "ss2");
//        Team team4 = Main.manager.getTeamByCode("t4");
//        JudgeManager.newJudge("John Doe", "j1");
//        JudgeManager.newJudge("Jane Doe", "j2");
//        JudgeManager.newJudge("Jane Doe", "j3");
//        JudgeManager.newJudge("John Doe", "j4");
//
//
//
//        Judge judge1 = JudgeManager.getJudgeByCode("j1");
//        Judge judge2 = JudgeManager.getJudgeByCode("j2");
//        RoomManager.newRoom();
//        RoomManager.newRoom();
//        Room room1 = RoomManager.getRoomById(1);
//        Room room2 = RoomManager.getRoomById(2);
//        int roomNum1 = 1;
//        int roomNum2 = 2;
//        int event1 = 1;
//        int event2 = 2;
//
//        // Event 1
//
//        assertEquals(false, team1.roundComplete[0]);
//        assertEquals(0, team1.rounds[0].affSpeaks);
//        assertNull(team1.judges[0]);
//        assertNull(team1.opp[0]);
//
//        Main.pair(team1, team2, judge1, room1, event1);
//        Main.pair(team3, team4, judge2, room2, event1);
//
//        assertEquals(team2, team1.opp[0]);
//        assertEquals(judge1, team1.judges[0]);
//        assertEquals(team2 , team1.rounds[0].oppTeam);
//        assertTrue(team1.rounds[0].side);
//        assertFalse(team2.rounds[0].side);
//
//        Main.selectedResult(roomNum1, true, "t1", "t2", 1 , 3, 2, 4);
//        Main.selectedResult(roomNum2, true, "t3", "t4", 1 , 3, 2, 4);
//
//        assertTrue(team1.roundComplete[0]);
//        assertTrue(team1.rounds[0].didWin);
//        assertFalse(team2.rounds[0].didWin);
//        assertEquals(team2, team1.opp[0]);
//        assertEquals(judge1, team1.judges[0]);
//        assertEquals(team2 , team1.rounds[0].oppTeam);
//
//        assertTrue(room1.data[0].affWon);
//        assertEquals(team1, room1.data[0].affTeam);
//        assertEquals(team2, room1.data[0].negTeam);
//        assertEquals(1, team1.totalWins);
//        assertEquals(0, team2.totalWins);
//        assertEquals(4, team1.totalSpeaks);
//        assertEquals(6, team2.totalSpeaks);
//
//
//        assertFalse(TeamManager.getTeamByCode("t1").inProgress[1]);
//        assertFalse(TeamManager.getTeamByCode("t2").inProgress[1]);
//        assertFalse(TeamManager.getTeamByCode("t3").inProgress[1]);
//        assertFalse(TeamManager.getTeamByCode("t4").inProgress[1]);
//
//        try {
//            ArrayList<RoundData> pairings = Pairer.pairRound2();
//            int a = 1;
//        } catch (GavelExeception exception) {
//            System.out.println(exception);
//        }
//
//        assertNotEquals(team1.opp[0], team1.opp[1]);
//        assertNotEquals(team2.opp[0], team2.opp[1]);
//        assertNotEquals(TeamManager.getTeamByCode("t3").opp[0], TeamManager.getTeamByCode("t3").opp[1]);
//        assertNotEquals(TeamManager.getTeamByCode("t4").opp[0], TeamManager.getTeamByCode("t4").opp[1]);
//
//        assertNotEquals(team1.judges[0], team1.judges[1]);
//        assertNotEquals(team2.judges[0], team2.judges[1]);
//        assertNotEquals(TeamManager.getTeamByCode("t3").judges[0], TeamManager.getTeamByCode("t3").judges[1]);
//        assertNotEquals(TeamManager.getTeamByCode("t4").judges[0], TeamManager.getTeamByCode("t4").judges[1]);
//    }
//
    @Test
    void betterRoundTwoPairingWorksTest() throws GavelExeception {
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

        int a = 1;

    }
}
//
//    ArrayList<RoundData> safePairing2() {
//        try {
//            return Pairer.pairRound2();
//        } catch (GavelExeception exception) {
//            return safePairing2();
//        }
//    }
//
//    @Test
//    void roundThreePairingWorks() {
//        Main.manager.newTeam("t1", "s1", "s2");
//        Main.manager.newTeam("t2", "s1", "s2");
//        Main.manager.newTeam("t3", "s1", "s2");
//        Main.manager.newTeam("t4", "s1", "s2");
//        Main.manager.newTeam("t5", "s1", "s2");
//        Main.manager.newTeam("t6", "s1", "s2");
//        Main.manager.newTeam("t7", "s1", "s2");
//
//        RoomManager.newRoom();
//        RoomManager.newRoom();
//        RoomManager.newRoom();
//        JudgeManager.newJudge("John Doe", "j1");
//        JudgeManager.newJudge("Jane Doe", "j2");
//        JudgeManager.newJudge("James Doe", "j3");
//
//
//        assertFalse(TeamManager.getTeamByCode("t1").inProgress[2]);
//        assertFalse(TeamManager.getTeamByCode("t2").inProgress[2]);
//        assertFalse(TeamManager.getTeamByCode("t3").inProgress[2]);
//        assertFalse(TeamManager.getTeamByCode("t4").inProgress[2]);
//        assertFalse(TeamManager.getTeamByCode("t5").inProgress[2]);
//        assertFalse(TeamManager.getTeamByCode("t6").inProgress[2]);
//        assertFalse(TeamManager.getTeamByCode("t7").inProgress[2]);
//
////        try {
//////            System.out.println(Pairer.pairRound3());
//////        } catch (GavelExeception exception) {
//////            System.out.println(exception);
//////        }
//    }
//}