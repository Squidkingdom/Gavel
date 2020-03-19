package com.squidkingdom.Gavel;

public class Round {
    boolean didWin;
    boolean side;
    Team oppTeam;
    int affSpeaks = 0;
    int negSpeaks = 0;
    Judge judge = new Judge();

    Round(boolean side, Team oppTeam, Judge judge) {
        this.side = side;
        this.oppTeam = oppTeam;
        this.judge = judge;
    }
    Round(){

    }
}
