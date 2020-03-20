package com.squidkingdom.Gavel;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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

        ArrayList<String> pairings = new ArrayList<String>(1);

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

    public static ArrayList<String> pairRound3() throws GavelExeception {
        Optional<Team> currentBye = Optional.empty();
        int rNum = roomArray.size();
        ArrayList<Room> localRA = (ArrayList<Room>) roomArray.clone();
        int tNum = teamArray.size();
        ArrayList<Team> localTA = (ArrayList<Team>) teamArray.clone();
        int jNum = judgeArray.size();
        ArrayList<Judge> localJA = (ArrayList<Judge>) judgeArray.clone();

        if ((tNum / 2) > rNum) {
            throw new GavelExeception("Error: Not enough rooms");
        }

        if ((tNum / 2) > jNum) {
            throw new GavelExeception("Error: Not enough judges");
        }

        ArrayList<Team> wonTwo = localTA.stream().filter(e -> e.totalWins == 2).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> wonOne = localTA.stream().filter(e -> e.totalWins == 1).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> wonNone = localTA.stream().filter(e -> e.totalWins == 0).collect(Collectors.toCollection(ArrayList::new));

        if ((wonNone.size() % 2) > 0) {
            boolean succeeded = false;
            while (!succeeded) {
                int r = (rand.nextInt(tNum));
                if (!wonNone.get(r).hasHadBye) {
                    wonNone.get(r).hasHadBye = true;
                    currentBye = Optional.of(wonNone.get(r));
                    wonNone.remove(r);
                    r = rand.nextInt(tNum);
                    succeeded = true;
                }
            }
        }

        ArrayList<String> pairings = new ArrayList<String>(1);

        while (wonTwo.size() > 1) {
            Team team1 = wonTwo.get(0);
            Team team2 = wonTwo.get(1);
            Judge judge = localJA.get(0);
            Room room = localRA.get(0);
            Main.pair(team1, team2, judge, room, 1);
            pairings.add(team1.code + " + " + team2.code + " (" + judge.code + ") in room " + room.id);
            wonTwo.remove(0);
            wonTwo.remove(0);
            localJA.remove(0);
            localRA.remove(0);
        }

        while (wonOne.size() > 1) {
            Team team1 = wonOne.get(0);
            Team team2 = wonOne.get(1);
            Judge judge = localJA.get(0);
            Room room = localRA.get(0);
            Main.pair(team1, team2, judge, room, 1);
            pairings.add(team1.code + " + " + team2.code + " (" + judge.code + ") in room " + room.id);
            wonOne.remove(0);
            wonOne.remove(0);
            localJA.remove(0);
            localRA.remove(0);
        }

        while (wonNone.size() > 1) {
            Team team1 = wonNone.get(0);
            Team team2 = wonNone.get(1);
            Judge judge = localJA.get(0);
            Room room = localRA.get(0);
            Main.pair(team1, team2, judge, room, 1);
            pairings.add(team1.code + " + " + team2.code + " (" + judge.code + ") in room " + room.id);
            wonNone.remove(0);
            wonNone.remove(0);
            localJA.remove(0);
            localRA.remove(0);
        }

        pairings.add(currentBye.orElse(new Team("No one")).code + " has a bye");
        return pairings;
    }

    public static void pairRound4() throws GavelExeception {


    }

    public static void pairRound5() throws GavelExeception {


    }


}
