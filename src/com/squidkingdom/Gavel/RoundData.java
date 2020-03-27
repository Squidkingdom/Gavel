package com.squidkingdom.Gavel;

public class RoundData {
    boolean isFinished = false;
    boolean affWon;
    int room;
    Team affTeam;
    Team negTeam;
    double affSpeaks = 0;
    double negSpeaks = 0;
    Judge judge;

    public RoundData(Team affTeam, Team negTeam, Judge judge, int roomId) {
        this.affTeam = affTeam;
        this.negTeam = negTeam;
        this.affSpeaks = affSpeaks;
        this.negSpeaks = negSpeaks;
        this.judge = judge;
        this.room = roomId;
    }
    public RoundData(Team affTeam, Team negTeam, Judge judge, double affSpeaks, double negSpeaks, boolean affWon, boolean isFinished){
        this.affTeam = affTeam;
        this.negTeam = negTeam;
        this.affSpeaks = affSpeaks;
        this.negSpeaks = negSpeaks;
        this.judge = judge;
        this.affWon = affWon;
        this.isFinished = isFinished;
    }
    public RoundData(){

    }
}
