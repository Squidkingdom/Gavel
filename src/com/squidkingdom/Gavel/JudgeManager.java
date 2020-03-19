package com.squidkingdom.Gavel;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

public class JudgeManager {
    public static final Judge DUMMY = new Judge("Dummy", "DUMMY");
    public static ArrayList<Judge> judgeArray = new ArrayList<Judge>(9);

    public static void newJudge(String name, String code) {
        judgeArray.add(new Judge(name, code));
    }

    public static Judge getJudgeByCode(String code) {
        Optional<Judge> optionalJudge = judgeArray.stream().filter(Objects::nonNull).filter(e -> e.code.equalsIgnoreCase(code)).findAny();
        return optionalJudge.orElse(DUMMY);
    }
}
