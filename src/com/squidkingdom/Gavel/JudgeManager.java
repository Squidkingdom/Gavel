package com.squidkingdom.Gavel;

import java.util.Arrays;

public class JudgeManager {
    public static Judge dummy = new Judge("Dummy", "DUMMY");
    public static Judge[] judgeArray = new Judge[]{dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy, dummy};
    public static int JIDArrayLength = 0;

    public static void newJudge(String name, String code) {
        judgeArray[JIDArrayLength] = new Judge(name, code);
        JIDArrayLength++;
    }

    public static Judge getJudgeByCode(String code) {
        for (Judge judge : judgeArray) {
            if (judge.code == code) {
                return judge;
            }
        }
        return dummy;
    }
}
