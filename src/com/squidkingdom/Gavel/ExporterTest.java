package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ExporterTest {

    @Test
    void exportSchedule() throws GavelExeception, IOException {
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

        ArrayList<RoundData> pairings = Pairer.pairRound1();
        Exporter.exportSchedule(pairings, "test_schedule");
    }

    @Test
    void exportRounds() throws GavelExeception, IOException {
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
        Exporter.exportRounds("test_rounds");
    }
}