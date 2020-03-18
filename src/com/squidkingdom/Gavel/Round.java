package com.squidkingdom.Gavel;

public class Round {
    boolean didWin;
    boolean side;
    Team oppTeam = new Team();
    int affSpeaks = 0;
    int negSpeaks = 0;
    Judge judge = new Judge();

    Round(boolean didWin, boolean side, Team oppTeam, int affSpeaks, int negSpeaks, Judge judge) {
        this.didWin = didWin;
        this.side = side;
        this.oppTeam = oppTeam;
        this.affSpeaks = affSpeaks;
        this.negSpeaks = negSpeaks;
        this.judge = judge;
    }
}
