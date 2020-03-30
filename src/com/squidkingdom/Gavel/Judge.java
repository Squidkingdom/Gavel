package com.squidkingdom.Gavel;

import java.util.ArrayList;

public class Judge {
   public String name = "Judge";
   public String code = "";
    ArrayList<Team> teams = new ArrayList<>();


    public Judge() {
    }

    public Judge(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public Judge(String code) {
        this.code = code;
    }

}
