package com.squidkingdom.Gavel.Tests;

import com.squidkingdom.Gavel.Judge;
import com.squidkingdom.Gavel.JudgeManager;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        ArrayList<Judge> judgeArray = JudgeManager.judgeArray;
        JudgeManager.newJudge("John doe", "TEST3");
        JudgeManager.newJudge("Jane doe", "TEST4");

        assertEquals("John doe", JudgeManager.getJudgeByCode("TEST3").name);
        assertEquals("TEST4", JudgeManager.getJudgeByCode("TEST4").code);
        assertEquals(JudgeManager.DUMMY, JudgeManager.getJudgeByCode("ldkasjdl"));
    }
}