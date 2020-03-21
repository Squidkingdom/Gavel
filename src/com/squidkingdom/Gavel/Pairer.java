package com.squidkingdom.Gavel;

import java.util.*;
import java.util.stream.Collector;
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

    //Round 2
    public static void pairRound2() throws GavelExeception {
        Team byeCastle;
        int rNum = roomArray.size();
        ArrayList<Room> localRA = (ArrayList<Room>) roomArray.clone();
        int tNum = teamArray.size();
        ArrayList<Team> localTA = (ArrayList<Team>) teamArray.clone();
        int jNum = judgeArray.size();
        ArrayList<Judge> localJA = (ArrayList<Judge>) judgeArray.clone();
        int judgeOffset = 0;
        Judge judge = localJA.get(judgeOffset);
        boolean hasShuffledT2 = false;

        //Handle Possible bye
        if ((tNum % 2) > 0) {
            boolean succeeded = false;
            while (!succeeded) {
                int r = (rand.nextInt(tNum));
                if (!localTA.get(r).hasHadBye) {
                    localTA.get(r).hasHadBye = true;
                    byeCastle = localTA.get(r);
                    r = rand.nextInt(tNum);
                    localTA.remove(r);
                    succeeded = true;
                }

            }
        }


        //Make sure everything is good in da hood
        {
            if ((tNum / 2) > rNum) {
                throw new GavelExeception("Error: Not enough rooms");
            }
            if ((tNum / 2) >= jNum) {
                throw new GavelExeception("Error: Not enough judges");
            }

        }


        /*
        Okay Bro, here is where shit gets weird
        Whats gonna happen is we're gonna go through our list of rooms and for each time get the next two team objects.
        making sure they haven't met each other.


        Make sure that the next judge in the array has not seen either team before
         */
        while (localRA.size() != 0) {

            Team team1 = localTA.get(0);
            Team team2 = localTA.get(1);
            //Pair Teams
            while (localTA.size() >= 2) {
                int team2Offset = 0;
                do {
                    if (team1.opp[0].code.equalsIgnoreCase(team2.opp[0].code)) {
                        if (localTA.size() == 2) {
                            throw new GavelExeception("FUUUUUCCCKKK");
                        } else {
                            team2 = localTA.get(2);
                        }
                    }
                } while (team1.opp[0].code.equalsIgnoreCase(team2.opp[0].code));
            }

            //Break if they have met each other before


            //Pair Judges
            do {
                if (judgeOffset > localJA.size()) {
                    throw new GavelExeception("This wouldve been an OOB execpetion, that being said \"FUCK\"");
                }
                if (team1.judges[0].code.equalsIgnoreCase(judge.code) || team2.judges[0].code.equalsIgnoreCase(judge.code)) {
                    if (localJA.size() == 2) {
                        throw new GavelExeception("FUUUUUCCCKKK");
                    } else {
                        judge = localJA.get(0 + judgeOffset);
                        judgeOffset++;
                    }
                }
            } while (team1.judges[0].code.equalsIgnoreCase(judge.code) || team2.judges[0].code.equalsIgnoreCase(judge.code));

            //Pair the room and clean arrays
            {
                Room room = localRA.get(0);
                Main.pair(team1, team2, judge, room, 1);
                localTA.remove(0);
                localTA.remove(hasShuffledT2 ? 0 : 1);
                localJA.remove(0);
                localRA.remove(0);
            }
        }
    }

    //Round 3
    public static ArrayList<String> pairRound3() throws GavelExeception {
        Optional<Team> currentBye = Optional.empty();
        int rNum = roomArray.size();
        ArrayList<Room> localRA = (ArrayList<Room>) roomArray.clone();
        int tNum = teamArray.size();
        ArrayList<Team> localTA = (ArrayList<Team>) teamArray.clone();
        int jNum = judgeArray.size();
        ArrayList<Judge> localJA = (ArrayList<Judge>) judgeArray.clone();

        //Make sure everything is good in da hood
        {
            if ((tNum / 2) > rNum) {
                throw new GavelExeception("Error: Not enough rooms");
            }

            if ((tNum / 2) > jNum) {
                throw new GavelExeception("Error: Not enough judges");
            }
        }


        //Filter into record
        ArrayList<Team> wonTwo = localTA.stream().filter(e -> e.totalWins == 2).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> wonOne = localTA.stream().filter(e -> e.totalWins == 1).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> wonNone = localTA.stream().filter(e -> e.totalWins == 0).collect(Collectors.toCollection(ArrayList::new));
        WinCompare winCompare = new WinCompare();
        Collections.sort(localTA, winCompare);

        //Handle Possible Bye
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


        if ((wonTwo.size() % 2) == 1) {

        }


        ArrayList<String> pairings = new ArrayList<String>(1);

        while (wonTwo.size() > 1) {
            Team team1 = wonTwo.get(0);
            Team team2 = wonTwo.get(0);


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



