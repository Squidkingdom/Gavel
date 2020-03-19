package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundDataTest {

    @Test
    void roundDataWorks() {
        RoundData roundData = new RoundData(new Team("t1", "s1", "s2"), new Team("t2", "ss1", "ss2"), new Judge("John Doe", "j1"));
        assertEquals(false, roundData.isFinished);

        roundData.affSpeaks = 5;
        roundData.negSpeaks = 5;
        roundData.affWon = true;
        roundData.isFinished = true;
        assertEquals(true, roundData.affWon);
    }

}