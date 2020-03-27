package com.squidkingdom.Gavel;

//import org.jetbrains.annotations.NotNull;

// A class 'Team' that implements Comparable
public class Team implements Comparable<Team> {
    static boolean affNegFlip = true;
    boolean isAffLead;
    int totalWins = 0;
    boolean byeComp;
    double totalSpeaks = 0;
    Round tr = new Round();
    Judge judges[] = new Judge[5];
    Round rounds[] = new Round[]{tr, tr, tr, tr, tr};
    Team opp[] = new Team[5];
    boolean hasHadBye = false;
    boolean[] roundComplete = new boolean[]{false, false, false, false, false};
    boolean[] inProgress = new boolean[]{false, false, false, false, false};
    String code = "";
    String person1 = "";
    String person2 = "";

    // Used to sort Teams by year
    public int compareTo(Team o) {
        if (this.totalWins < o.totalWins) return -1;
        if (this.totalWins > o.totalWins) return 1;
        else return 0;
    }


    // Constructor
    @Deprecated
    public Team() {

    }

    @Deprecated
    public Team(String code) {
        this.code = code;

    }
//TODO uncomment this for LD
//    public Team(String code, String person1) {
//        this.code = code;
//        this.person1 = person1;
//        this.isAffLead = affNegFlip;
//        affNegFlip = !affNegFlip;
//    }

    public Team(String code, String person1, String person2) {
        this.code = code;
        this.person1 = person1;
        this.person2 = person2;
        this.isAffLead = affNegFlip;
        affNegFlip = !affNegFlip;
    }


    // Getter methods for accessing private data
    public double getSpeaks() {
        return totalSpeaks;
    }

    public int getTotalWins() {
        return totalWins;
    }
}

// Class to compare Teams by ratings


