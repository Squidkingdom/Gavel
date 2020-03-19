package com.squidkingdom.Gavel;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class Pairer {
    static Random rand = new Random();
    public static ArrayList<Room> roomArray = RoomManager.roomArray;
    public static ArrayList<Judge> judgeArray = JudgeManager.judgeArray;
    public static ArrayList<Team> teamArray = TeamManager.teamArray;


    public static ArrayList<String> pairRound1() throws GavelExeception {
        Optional<Team> currentBye = Optional.empty();
        int rNum = roomArray.size();
        ArrayList<Room> localRA = (ArrayList<Room>) roomArray.clone();
        int tNum = teamArray.size();
        ArrayList<Team> localTA = (ArrayList<Team>) teamArray.clone();
        int jNum = judgeArray.size();
        ArrayList<Judge> localJA = (ArrayList<Judge>) judgeArray.clone();

        if ((tNum % 2) > 0) {
            boolean succeeded = false;
            while (!succeeded) {
                int r = (rand.nextInt(tNum));
                if (!localTA.get(r).hasHadBye) {
                    localTA.get(r).hasHadBye = true;
                    currentBye = Optional.of(localTA.get(r));
                    localTA.remove(r);
                    r = rand.nextInt(tNum);
                    succeeded = true;
                }
            }
        }

        if ((tNum / 2) > rNum) {
            throw new GavelExeception("Error: Not enough rooms");
        }

        if ((tNum / 2) > jNum) {
            throw new GavelExeception("Error: Not enough judges");
        }

        ArrayList<String> pairings = new ArrayList<String>(15);

        while (localTA.size() > 1) {
            Team team1 = localTA.get(0);
            Team team2 = localTA.get(1);
            Judge judge = localJA.get(0);
            Room room = localRA.get(0);
            Main.pair(team1, team2, judge, room, 1);
            pairings.add(team1.code + " + " + team2.code + " (" + judge.code + ") in room " + room.id);
            localTA.remove(0);
            localTA.remove(0);
            localJA.remove(0);
            localRA.remove(0);
        }

        pairings.add(currentBye.orElse(new Team("No one")).code + " has a bye");
        return pairings;
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
