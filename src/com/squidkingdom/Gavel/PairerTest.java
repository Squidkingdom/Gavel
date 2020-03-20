package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PairerTest {

    @Test
    void roundOnePairingWorks() {
        TeamManager.newTeam("t1");
        TeamManager.newTeam("t2");
        TeamManager.newTeam("t3");
        TeamManager.newTeam("t4");
        TeamManager.newTeam("t5");
        TeamManager.newTeam("t6");
        TeamManager.newTeam("t7");

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
            System.out.println(Pairer.pairRound1());
        } catch (GavelExeception exception) {
            System.out.println(exception);
        }

//        assertTrue(TeamManager.getTeamByCode("t1").inProgress[0]);
//        assertTrue(TeamManager.getTeamByCode("t2").inProgress[0]);
//        assertTrue(TeamManager.getTeamByCode("t3").inProgress[0]);
//        assertTrue(TeamManager.getTeamByCode("t4").inProgress[0]);
//        assertFalse(TeamManager.getTeamByCode("t5").inProgress[0]);
    }

    @Test
    void roundThreePairingWorks() {
        TeamManager.newTeam("t1");
        TeamManager.newTeam("t2");
        TeamManager.newTeam("t3");
        TeamManager.newTeam("t4");
        TeamManager.newTeam("t5");
        TeamManager.newTeam("t6");
        TeamManager.newTeam("t7");

        RoomManager.newRoom();
        RoomManager.newRoom();
        RoomManager.newRoom();
        JudgeManager.newJudge("John Doe", "j1");
        JudgeManager.newJudge("Jane Doe", "j2");
        JudgeManager.newJudge("James Doe", "j3");


        assertFalse(TeamManager.getTeamByCode("t1").inProgress[1]);
        assertFalse(TeamManager.getTeamByCode("t2").inProgress[1]);
        assertFalse(TeamManager.getTeamByCode("t3").inProgress[1]);
        assertFalse(TeamManager.getTeamByCode("t4").inProgress[1]);
        assertFalse(TeamManager.getTeamByCode("t5").inProgress[1]);
        assertFalse(TeamManager.getTeamByCode("t6").inProgress[1]);
        assertFalse(TeamManager.getTeamByCode("t7").inProgress[1]);

        try {
            System.out.println(Pairer.pairRound3());
        } catch (GavelExeception exception) {
            System.out.println(exception);
        }
    }
}