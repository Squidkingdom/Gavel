package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @Test
    void roundWorks() {
        Round round = new Round(true, new Team("t2", "ss1", "ss2"), new Judge("John Doe", "j1"), 0);
        assertEquals(true, round.side);
        assertEquals("ss2", round.oppTeam.person2);
        assertEquals("j1", round.judge.code);
    }
}