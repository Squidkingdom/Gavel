package com.squidkingdom.Gavel;

public class RoundData {
    boolean isFinished = false;
    boolean affWon;
    Team affTeam = new Team();
    Team negTeam = new Team();
    int affSpeaks = 0;
    int negSpeaks = 0;
    Judge judge = new Judge();

    public RoundData(Team affTeam, Team negTeam, Judge judge) {
        this.affTeam = affTeam;
        this.negTeam = negTeam;
        this.affSpeaks = affSpeaks;
        this.negSpeaks = negSpeaks;
        this.judge = judge;
    }
}
