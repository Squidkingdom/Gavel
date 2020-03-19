package com.squidkingdom.Gavel;

public class RoundData {
    boolean isFinished = false;
    boolean affWon;
    Team affTeam;
    Team negTeam;
    int affSpeaks = 0;
    int negSpeaks = 0;
    Judge judge;

    public RoundData(Team affTeam, Team negTeam, Judge judge) {
        this.affTeam = affTeam;
        this.negTeam = negTeam;
        this.affSpeaks = affSpeaks;
        this.negSpeaks = negSpeaks;
        this.judge = judge;
    }
    public RoundData(){

    }
}
