package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JudgeTest {

    @Test
    public void judgeWorks() {
        Judge judge = new Judge("John Doe", "TEST");
        assertEquals("John Doe", judge.name);
        assertEquals("TEST", judge.code);
    }
}