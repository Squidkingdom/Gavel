package com.squidkingdom.Gavel;

public class Round {
    boolean didWin;
    //Aff is True Neg is false
    boolean side;
    int roomnum;
    Team oppTeam;
    int affSpeaks = 0;
    int negSpeaks = 0;
    Judge judge = new Judge();

    Round(boolean side, int affS, boolean won, int roomid, int negSpeaks, Team oppTeam) {
        this.affSpeaks = affS;
        this.didWin = won;
        this.roomnum = roomid;
        this.negSpeaks = negSpeaks;
        this.side = side;
        this.oppTeam = oppTeam;
    }
    Round(boolean side, Team oppTeam, Judge judge){
        this.side = side;
        this.oppTeam = oppTeam;
        this.judge = judge;
    }
    Round(){

    }
}
