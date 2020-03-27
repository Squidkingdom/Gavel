package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TeamManagerTest {

    @Test
    void newTeam() {
        TeamManager manager = new TeamManager();
        manager.newTeam("t1", "s1", "s2");
        manager.newTeam("t2", "s3", "s4");

        // TODO: TeamManager.newTeam should throw an exception or something, you should not be able to make teams with duplicated codes.
        manager.newTeam("t1", "s1", "s2");

        assertEquals("t1", manager.teamArray.get(0).code);
        assertEquals("t2", manager.teamArray.get(1).code);
        assertEquals("t1", manager.teamArray.get(2).code);
    }

    @Test
    void getTeamByCode() {
        try {
            TeamManager manager = new TeamManager();
            manager.newTeam("t1", "s1", "s2");
            manager.newTeam("t2", "s3", "s4");


            assertEquals("t1", manager.getTeamByCode("t1").code);
            assertEquals("s3", manager.getTeamByCode("t2").person1);
            assertEquals(TeamManager.dummy, manager.getTeamByCode("non_existent_team"));
        }catch (GavelExeception e){
            System.out.println(e);
        }
    }

    @Test
    void checkcode() {
        TeamManager manager = new TeamManager();
        manager.newTeam("t1", "s1", "s2");
        manager.newTeam("t2", "s3", "s4");


        assertTrue(manager.checkcode("t1"));
        assertTrue(manager.checkcode("t2"));
        assertFalse(manager.checkcode("non_existent_team"));
    }
}