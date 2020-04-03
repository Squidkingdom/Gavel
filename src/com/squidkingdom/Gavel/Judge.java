package com.squidkingdom.Gavel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Judge implements Comparable<Judge>{
    String name = "Judge";
    String code = "";
    ArrayList<Team> teams = new ArrayList<>();
    boolean hasBeenFlighted = false;

    public Judge() {
    }

    public Judge(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Judge(String code) {
        this.code = code;
    }

    @Deprecated
    @Override
    public int compareTo(@NotNull Judge o) {
        return 0;
    }
}
