package com.squidkingdom.Gavel;
/*TODO:
    ADD CANT BE AFF/NEG 4 TIMES IN A ROW
        This could be done by creating an affLeading field in TEAM class and filter into to ArrayList<Team> before .sort()ing and then instead of localTA(0) and localTA(1) make it localAFF(0) localNeg(0), needs to handle drops, and changes in bye status, TBD.
    MAKE TEAMS[] BE FIELD OF JUDGES INSTEAD OF JUDGES[] IN TEAMS
        *(THIS ALLOWS HOT SWAPPING OF JUDGES)


    FIXME:
        idk what your doing with the returns and pairings, seems half baked, ignoring for now.
 */
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;


public class Pairer {
    static Random rand = new Random();
    public static ArrayList<Room> roomArray = RoomManager.roomArray;
    public static ArrayList<Judge> judgeArray = JudgeManager.judgeArray;
    public static ArrayList<Team> teamArray = TeamManager.teamArray;

    //Round 3
    public static ArrayList<RoundData> pairRound1() throws GavelExeception {
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

                    currentBye.get().opp[0] = TeamManager.dummy;
                    currentBye.get().rounds[0] = new Round(true, TeamManager.dummy, JudgeManager.DUMMY);
                    currentBye.get().totalWins++;
                    currentBye.get().totalSpeaks = currentBye.get().totalSpeaks + 3;
                    currentBye.get().judges[0] = JudgeManager.DUMMY;
                    currentBye.get().roundComplete[0] = true;

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

        ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);

        while (localTA.size() > 1) {
            Team team1 = localTA.get(0);
            Team team2 = localTA.get(1);
            Judge judge = localJA.get(0);
            Room room = localRA.get(0);
            pairings.add(Main.pair(team1, team2, judge, room, 1));
            localTA.remove(0);
            localTA.remove(0);
            localJA.remove(0);
            localRA.remove(0);
        }

        if (currentBye.isPresent())
            pairings.add(new RoundData(currentBye.get(), TeamManager.dummy, JudgeManager.DUMMY));
        return pairings;
    }

    //Round 2
    public static ArrayList<RoundData> pairRound2() throws GavelExeception {
        Optional<Team> byeCastle = Optional.empty();
        int rNum = roomArray.size();
        ArrayList<Room> localRA = (ArrayList<Room>) roomArray.clone();
        int tNum = teamArray.size();
        ArrayList<Team> localTA = (ArrayList<Team>) teamArray.clone();
        localTA.remove(0);
        tNum--;
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
                    byeCastle = Optional.of(localTA.get(r));
                    tNum--;
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
            if (((tNum / 2) + 1) > jNum) {
                throw new GavelExeception("Error: Not enough judges");
            }

        }


        /*
        Okay Bro, here is where shit gets weird
        Whats gonna happen is we're gonna go through our list of rooms and for each time get the next two team objects.
        making sure they haven't met each other.
        Make sure that the next judge in the array has not seen either team before
         */

        ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);

        while (localRA.size() != 0) {
            int team2Offset = 1;
            Team team1 = localTA.get(0);

            //Pair Teams
            Optional<Team> optionalTeam2 = localTA.stream().filter(e -> !e.code.equalsIgnoreCase(team1.code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code)).findAny();
            if (!optionalTeam2.isPresent())
                throw new GavelExeception("TEAMS FUUUUUCCCKKK");
            Team team2 = optionalTeam2.get();

            //Pair Judges
            Optional<Judge> roundJudge = localJA.stream().filter(e -> !team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[0].code.equalsIgnoreCase(e.code)).findAny();
            if (!roundJudge.isPresent())
                throw new GavelExeception("JUDGES FUUUUUCCCKKK");



            /*Pair the room and clean array*/
            {
                Room room = localRA.get(0);
                pairings.add(Main.pair(team1, team2, roundJudge.get(), room, 2));
                localTA.remove(team1);
                localTA.remove(team2);
                localJA.remove(roundJudge.get());
                localRA.remove(room);
            }
        }

        if (byeCastle.isPresent())
            pairings.add(new RoundData(byeCastle.get(), TeamManager.dummy, JudgeManager.DUMMY));
        return pairings;
    }

    //Round 3
    public static ArrayList<String> pairRound3() throws GavelExeception {
        Optional<Team> currentBye = Optional.empty();
        int rNum = roomArray.size();
        int jNum = judgeArray.size();
        int tNum = teamArray.size();
        ArrayList<Team> localTA = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> localRA = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> localJA = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<String> pairings = new ArrayList<String>(1);
        //  ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);


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
        WinCompare winCompare = new WinCompare();
        Collections.sort(localTA, winCompare);




        //Handle Possible Bye
        if ((localTA.size() % 2) > 0) {
            boolean succeeded = false;
            int r = 0;
            while (!succeeded) {
                if (!localTA.get(r).hasHadBye) {
                    localTA.get(r).hasHadBye = true;
                    currentBye = Optional.of(localTA.get(r));
                    localTA.remove(r);
                    succeeded = true;
                } else {
                    r++;
                }
            }
        }



        //Make sure everything is good in da hood
        {
            if ((tNum / 2) > rNum) {
                throw new GavelExeception("Error: Not enough rooms");
            }
            if (((tNum / 2) + 1) > jNum) {
                throw new GavelExeception("Error: Not enough judges");
            }

        }




        // DO THE THING (Pair Teams)
        while (localRA.size() != 0) {
            int team2Offset = 1;
            Team team1 = localTA.get(0);
            Team team2 = localTA.get(team2Offset);
            //Pair Teams
            do {
                if (team1.opp[0].code.equalsIgnoreCase(team2.opp[0].code) || team1.opp[1].code.equalsIgnoreCase(team2.opp[1].code)) {
                    //TODO this will throw NullPointerExeception

                    if (localTA.size() == 2) {
                        throw new GavelExeception("FUUUUUCCCKKK");
                    } else {

                        team2Offset++;
                        team2 = localTA.get(team2Offset);
                    }
                }
            } while (team1.opp[0].code.equalsIgnoreCase(team2.opp[0].code) || team1.opp[1].code.equalsIgnoreCase(team2.opp[1].code));



            //Pair Judges
            Team finalTeam2 = team2;
            Optional<Judge> roundJudge = localJA.stream().filter(e -> team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> finalTeam2.judges[0].code.equalsIgnoreCase(e.code)).findAny();
            if (!roundJudge.isPresent())
                throw new GavelExeception("FUUUUUCCCKKK");



            /*Pair the room and clean array*/
            {
                Room room = localRA.get(0);
                //  pairings.add(Main.pair(team1, team2, roundJudge.get(), room, 2));
                localTA.remove(0);
                localTA.remove(team2Offset - 1);
                localJA.remove(0);
                localRA.remove(0);
            }
        }




        //TODO Does this need to be in round 2 or 1?

        pairings.add(currentBye.orElse(new Team("No one")).code + " has a bye");
        return pairings;
    }

    //Round 4
    public static ArrayList<String> pairRound4() throws GavelExeception {
        Optional<Team> currentBye = Optional.empty();
        int rNum = roomArray.size();
        int jNum = judgeArray.size();
        int tNum = teamArray.size();
        ArrayList<Team> localTA = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> localRA = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> localJA = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<String> pairings = new ArrayList<String>(1);
        //  ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);


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
        WinCompare winCompare = new WinCompare();
        Collections.sort(localTA, winCompare);




        //Handle Possible Bye
        if ((localTA.size() % 2) > 0) {
            boolean succeeded = false;
            int r = 0;
            while (!succeeded) {
                if (!localTA.get(r).hasHadBye) {
                    localTA.get(r).hasHadBye = true;
                    currentBye = Optional.of(localTA.get(r));
                    localTA.remove(r);
                    succeeded = true;
                } else {
                    r++;
                }
            }
        }



        //Make sure everything is good in da hood
        {
            if ((tNum / 2) > rNum) {
                throw new GavelExeception("Error: Not enough rooms");
            }
            if (((tNum / 2) + 1) > jNum) {
                throw new GavelExeception("Error: Not enough judges");
            }

        }




        // DO THE THING (Pair Teams)
        while (localRA.size() != 0) {
            int team2Offset = 1;
            Team team1 = localTA.get(0);
            Team team2 = localTA.get(team2Offset);
            //Pair Teams
            do {
                if (team1.opp[0].code.equalsIgnoreCase(team2.opp[0].code) || team1.opp[1].code.equalsIgnoreCase(team2.opp[1].code)) {
                    //TODO this will throw NullPointerExeception

                    if (localTA.size() == 2) {
                        throw new GavelExeception("FUUUUUCCCKKK");
                    } else {

                        team2Offset++;
                        team2 = localTA.get(team2Offset);
                    }
                }
            } while (team1.opp[0].code.equalsIgnoreCase(team2.opp[0].code) || team1.opp[1].code.equalsIgnoreCase(team2.opp[1].code));



            //Pair Judges
            Team finalTeam2 = team2;
            Optional<Judge> roundJudge = localJA.stream().filter(e -> team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> finalTeam2.judges[0].code.equalsIgnoreCase(e.code)).findAny();
            if (!roundJudge.isPresent())
                throw new GavelExeception("FUUUUUCCCKKK");



            /*Pair the room and clean array*/
            {
                Room room = localRA.get(0);
                //  pairings.add(Main.pair(team1, team2, roundJudge.get(), room, 2));
                localTA.remove(0);
                localTA.remove(team2Offset - 1);
                localJA.remove(0);
                localRA.remove(0);
            }
        }




        //TODO Does this need to be in round 2 or 1?

        pairings.add(currentBye.orElse(new Team("No one")).code + " has a bye");
        return pairings;


    }


    //Round 5
    public static ArrayList<String> pairRound5() throws GavelExeception {
        Optional<Team> currentBye = Optional.empty();
        int rNum = roomArray.size();
        int jNum = judgeArray.size();
        int tNum = teamArray.size();
        ArrayList<Team> localTA = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> localRA = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> localJA = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<String> pairings = new ArrayList<String>(1);
        //  ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);


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
        WinCompare winCompare = new WinCompare();
        Collections.sort(localTA, winCompare);




        //Handle Possible Bye
        if ((localTA.size() % 2) > 0) {
            boolean succeeded = false;
            int r = 0;
            while (!succeeded) {
                if (!localTA.get(r).hasHadBye) {
                    localTA.get(r).hasHadBye = true;
                    currentBye = Optional.of(localTA.get(r));
                    localTA.remove(r);
                    succeeded = true;
                } else {
                    r++;
                }
            }
        }



        //Make sure everything is good in da hood
        {
            if ((tNum / 2) > rNum) {
                throw new GavelExeception("Error: Not enough rooms");
            }
            if (((tNum / 2) + 1) > jNum) {
                throw new GavelExeception("Error: Not enough judges");
            }

        }




        // DO THE THING (Pair Teams)
        while (localRA.size() != 0) {
            int team2Offset = 1;
            Team team1 = localTA.get(0);
            Team team2 = localTA.get(team2Offset);
            //Pair Teams
            do {
                if (team1.opp[0].code.equalsIgnoreCase(team2.opp[0].code) || team1.opp[1].code.equalsIgnoreCase(team2.opp[1].code)) {
                    //TODO this will throw NullPointerExeception

                    if (localTA.size() == 2) {
                        throw new GavelExeception("FUUUUUCCCKKK");
                    } else {

                        team2Offset++;
                        team2 = localTA.get(team2Offset);
                    }
                }
            } while (team1.opp[0].code.equalsIgnoreCase(team2.opp[0].code) || team1.opp[1].code.equalsIgnoreCase(team2.opp[1].code));



            //Pair Judges
            Team finalTeam2 = team2;
            Optional<Judge> roundJudge = localJA.stream().filter(e -> team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> finalTeam2.judges[0].code.equalsIgnoreCase(e.code)).findAny();
            if (!roundJudge.isPresent())
                throw new GavelExeception("FUUUUUCCCKKK");



            /*Pair the room and clean array*/
            {
                Room room = localRA.get(0);
                //  pairings.add(Main.pair(team1, team2, roundJudge.get(), room, 2));
                localTA.remove(0);
                localTA.remove(team2Offset - 1);
                localJA.remove(0);
                localRA.remove(0);
            }
        }




        //TODO Does this need to be in round 2 or 1?

        pairings.add(currentBye.orElse(new Team("No one")).code + " has a bye");
        return pairings;


    }
}