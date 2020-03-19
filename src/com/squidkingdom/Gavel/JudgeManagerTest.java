package com.squidkingdom.Gavel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JudgeManagerTest {

    @Test
    void newJudge() {
        JudgeManager.newJudge("John doe", "TEST1");
        JudgeManager.newJudge("Jane doe", "TEST2");

        assertEquals("TEST1", JudgeManager.getJudgeByCode("TEST1").code);
        assertEquals("Jane doe", JudgeManager.getJudgeByCode("TEST2").name);

    }

    @Test
    void getJudgeByCode() {
        Judge[] judgeArray = JudgeManager.judgeArray;
        JudgeManager.newJudge("John doe", "TEST3");
        JudgeManager.newJudge("Jane doe", "TEST4");

        assertEquals("John doe", JudgeManager.getJudgeByCode("TEST3").name);
        assertEquals("TEST4", JudgeManager.getJudgeByCode("TEST4").code);
    }
}