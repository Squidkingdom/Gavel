package com.squidkingdom.Gavel;

import java.util.ArrayList;
import java.util.Random;

public class Pairer {
    static Random rand = new Random();
    public static ArrayList<Room> roomArray = RoomManager.roomArray;
    public static ArrayList<Judge> judgeArray = JudgeManager.judgeArray;
    public static ArrayList<Team> teamArray = TeamManager.teamArray;


    public static void pairRound1() throws GavelExeception {
        Team currentBye;
        int rNum = roomArray.size();
        Room[] localRA = (Room[]) roomArray.toArray().clone();
        int tNum = teamArray.size();
        Team[] localTA = (Team[]) teamArray.toArray().clone();
        int jNum = judgeArray.size();
        Judge[] localJA = (Judge[]) judgeArray.toArray().clone();

        if ((tNum % 2) > 0) {
            boolean succeeded = false;
            while (!succeeded) {
                int r = (rand.nextInt() % tNum);
                if (!localTA[r].hasHadBye) {
                    localTA[r].hasHadBye = true;
                    currentBye = localTA[r];
                    r = rand.nextInt();
                }
                for ()

            }
        }

        if ((tNum / 2) > rNum) {
            throw new GavelExeception("Error: Not enough rooms");
        }

        if ((tNum / 2) > jNum) {
            throw new GavelExeception("Error: Not enough judges");
        }

        for (int i = 0; i < (tNum / 2); i++) {

        }
    }

    public static void pairRound2() throws GavelExeception {

    }

    public static void pairRound3() throws GavelExeception {


    }

    public static void pairRound4() throws GavelExeception {


    }

    public static void pairRound5() throws GavelExeception {


    }


}
