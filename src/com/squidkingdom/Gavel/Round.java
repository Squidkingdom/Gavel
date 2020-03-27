package com.squidkingdom.Gavel;

public class Round {
    boolean didWin;
    //Aff is True Neg is false
    boolean side;
    int roomnum;
    Team oppTeam;
    double affSpeaks = 0;
    double negSpeaks = 0;
    Judge judge = new Judge();

    Round(boolean side, double affS, boolean won, int roomid, double negSpeaks, Team oppTeam, Judge judge) {
        this.affSpeaks = affS;
        this.didWin = won;
        this.roomnum = roomid;
        this.negSpeaks = negSpeaks;
        this.side = side;
        this.oppTeam = oppTeam;
        this.judge = judge;
    }

    Round(boolean side, Team oppTeam, Judge judge, int roomId) {
        this.side = side;
        this.oppTeam = oppTeam;
        this.judge = judge;
        this.roomnum = roomId;
    }

    Round() {

    }
}
