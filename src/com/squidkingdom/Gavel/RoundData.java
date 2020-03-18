package com.squidkingdom.Gavel;

public class RoundData {
    boolean affWon;
    Team affTeam = new Team();
    Team negTeam = new Team();
    int affSpeaks = 0;
    int negSpeaks = 0;
    Judge judge = new Judge();

    public RoundData(boolean affWon, Team affTeam, Team negTeam, int affSpeaks, int negSpeaks, Judge judge) {
        this.affWon = affWon;
        this.affTeam = affTeam;
        this.negTeam = negTeam;
        this.affSpeaks = affSpeaks;
        this.negSpeaks = negSpeaks;
        this.judge = judge;
    }
}
