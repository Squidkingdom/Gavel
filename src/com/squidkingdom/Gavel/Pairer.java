package com.squidkingdom.Gavel;
/*TODO:
    Remove TNUM and derrivitave, remove need for localTa after filter.
     MAKE TEAMS[] BE FIELD OF JUDGES INSTEAD OF JUDGES[] IN TEAMS
        *(THIS ALLOWS HOT SWAPPING OF JUDGES)


    FIXME:
        idk what your doing with the returns and pairings, seems half baked, ignoring for now.
 */
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public class Pairer {
    static Random rand = new Random();
    public static ArrayList<Room> roomArray = RoomManager.roomArray;
    public static ArrayList<Judge> judgeArray = JudgeManager.judgeArray;
    public static ArrayList<Team> teamArray = TeamManager.teamArray;

    //Round 3
    public static ArrayList<RoundData> pairRound1() throws GavelExeception {
        Optional<Team> byeCastle = Optional.empty();
        int rNum = roomArray.size();
        ArrayList<Room> localRA = (ArrayList<Room>) roomArray.clone();
        int tNum = teamArray.size();
        ArrayList<Team> localTA = (ArrayList<Team>) teamArray.clone();
        int jNum = judgeArray.size();
        ArrayList<Judge> localJA = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<Team> localAff = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> localNeg = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));

    //Handle Bye
        if ((localAff.size() % 2) > 0) {
            boolean run = true;
            int lastTeam = localAff.size();
            //TODO That just sounds like a for loop with extra steps
            while (run) {
                if (!localAff.get(lastTeam).hasHadBye) {
                    localAff.get(lastTeam).hasHadBye = true;
                    byeCastle = Optional.of(localAff.get(lastTeam));
                    localAff.remove(lastTeam);
                    localTA.remove(lastTeam);
                    run = false;
                    byeCastle.get().opp[0] = TeamManager.dummy;
                    byeCastle.get().rounds[0] = new Round(true, TeamManager.dummy, JudgeManager.DUMMY);
                    byeCastle.get().totalWins++;
                    byeCastle.get().totalSpeaks = byeCastle.get().totalSpeaks + 3;
                    byeCastle.get().judges[0] = JudgeManager.DUMMY;
                    byeCastle.get().roundComplete[0] = true;
                } else {
                    lastTeam--;
                }
            }
        }

        

        if ((tNum / 2) > rNum) {
            throw new GavelExeception("Error: Not enough rooms");
        }

        if ((tNum / 2) > jNum) {
            throw new GavelExeception("Error: Not enough judges");
        }
        
        if ((localAff.size() % 2) > 0 || (localNeg.size() % 2) > 0){

            throw new GavelExeception("Fuck, bye is not handled ");
        }

        ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);

        while (localTA.size() > 1) {
            Team team1 = localAff.get(0);
            Team team2 = localNeg.get(0);
            Judge judge = localJA.get(0);
            Room room = localRA.get(0);
            pairings.add(Main.pair(team1, team2, judge, room, 1));
            localTA.remove(team1);
            localTA.remove(team2);
            localAff.remove(team1);
            localNeg.remove(team2);
            localJA.remove(0);
            localRA.remove(0);
        }

        if (byeCastle.isPresent())
            pairings.add(new RoundData(byeCastle.get(), TeamManager.dummy, JudgeManager.DUMMY));
        
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
        ArrayList<Team> localAff = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> localNeg = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        int judgeOffset = 0;
        Judge judge = localJA.get(judgeOffset);
        boolean hasShuffledT2 = false;

        //Handle Possible bye
        if ((localTA.size() % 2) > 0) {
            boolean succeeded = false;
            int lastTeam = localAff.size();
            while (!succeeded) {
                if (!localAff.get(lastTeam).hasHadBye) {
                    localAff.get(lastTeam).hasHadBye = true;
                    byeCastle = Optional.of(localAff.get(lastTeam));
                    localAff.remove(lastTeam);
                    localTA.remove(lastTeam);
                    succeeded = true;
                    byeCastle.get().opp[0] = TeamManager.dummy;
                    byeCastle.get().rounds[0] = new Round(true, TeamManager.dummy, JudgeManager.DUMMY);
                    byeCastle.get().totalWins++;
                    byeCastle.get().totalSpeaks = byeCastle.get().totalSpeaks + 3;
                    byeCastle.get().judges[0] = JudgeManager.DUMMY;
                    byeCastle.get().roundComplete[0] = true;
                } else {
                    lastTeam--;
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
            Team team1 = localAff.get(0);

            //Pair Teams
            Optional<Team> optionalTeam2 = localNeg.stream().filter(e -> !e.code.equalsIgnoreCase(team1.code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code)).findAny();
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
                localAff.remove(team1);
                localNeg.remove(team2);
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
        Optional<Team> byeCastle = Optional.empty();
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

        ArrayList<Team> localAff = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> localNeg = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));




        //Handle Possible Bye
        if ((localAff.size() % 2) > 0) {
            boolean succeeded = false;
            int lastTeam = localAff.size();
            while (!succeeded) {
                if (!localAff.get(lastTeam).hasHadBye) {
                    localAff.get(lastTeam).hasHadBye = true;
                    byeCastle = Optional.of(localAff.get(lastTeam));
                    localTA.remove(lastTeam);
                    localAff.remove(lastTeam);
                    succeeded = true;
                } else {
                    lastTeam--;
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
            Team team1 = localAff.get(0);

            //Pair Teams
            Optional<Team> optionalTeam2 = localNeg.stream().filter(e -> !e.code.equalsIgnoreCase(team1.code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code)).findFirst();
            if (!optionalTeam2.isPresent())
                throw new GavelExeception("TEAMS FUUUUUCCCKKK");
            Team team2 = optionalTeam2.get();

            //Pair Judges
            Optional<Judge> roundJudge = localJA.stream().filter(e -> !team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).findAny();
            if (!roundJudge.isPresent())
                throw new GavelExeception("JUDGES FUUUUUCCCKKK");



            /*Pair the room and clean array*/
            {
                Room room = localRA.get(0);
               // pairings.add(Main.pair(team1, team2, roundJudge.get(), room, 2));
                localTA.remove(team1);
                localAff.remove(team1);
                localTA.remove(team2);
                localNeg.remove(team2);
                localJA.remove(roundJudge.get());
                localRA.remove(room);
            }
        }




        //TODO Does this need to be in round 2 or 1?

        pairings.add(byeCastle.orElse(new Team("No one")).code + " has a bye");
        return pairings;
    }






    //Round 4
    public static ArrayList<String> pairRound4() throws GavelExeception {
        Optional<Team> byeCastle = Optional.empty();
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

        ArrayList<Team> localAff = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> localNeg = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));




        //Handle Possible Bye
        if ((localAff.size() % 2) > 0) {
            boolean succeeded = false;
            int lastTeam = localAff.size();
            while (!succeeded) {
                if (!localAff.get(lastTeam).hasHadBye) {
                    localAff.get(lastTeam).hasHadBye = true;
                    byeCastle = Optional.of(localAff.get(lastTeam));
                    localAff.remove(lastTeam);
                    localTA.remove(lastTeam);

                    succeeded = true;
                } else {
                    lastTeam--;
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



        while (localRA.size() != 0) {
            int team2Offset = 1;
            Team team1 = localAff.get(0);

            //Pair Teams
            Optional<Team> optionalTeam2 = localNeg.stream().filter(e -> !e.code.equalsIgnoreCase(team1.code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code)).findFirst();
            if (!optionalTeam2.isPresent())
                throw new GavelExeception("TEAMS FUUUUUCCCKKK");
            Team team2 = optionalTeam2.get();

            //Pair Judges
            Optional<Judge> roundJudge = localJA.stream().filter(e -> !team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).findAny();
            if (!roundJudge.isPresent())
                throw new GavelExeception("JUDGES FUUUUUCCCKKK");



            /*Pair the room and clean array*/
            {
                Room room = localRA.get(0);
                // pairings.add(Main.pair(team1, team2, roundJudge.get(), room, 2));
                localTA.remove(team1);
                localAff.remove(team1);
                localNeg.remove(team2);
                localTA.remove(team2);
                localJA.remove(roundJudge.get());
                localRA.remove(room);
            }
        }





        //TODO Does this need to be in round 2 or 1?

        pairings.add(byeCastle.orElse(new Team("No one")).code + " has a bye");
        return pairings;


    }







    //Round 5
    public static ArrayList<String> pairRound5() throws GavelExeception {
        Optional<Team> byeCastle = Optional.empty();
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

        ArrayList<Team> localAff = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> localNeg = localTA.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));




        //Handle Possible Bye
        if ((localAff.size() % 2) > 0) {
            boolean succeeded = false;
            int lastTeam = localAff.size();
            while (!succeeded) {
                if (!localAff.get(lastTeam).hasHadBye) {
                    localAff.get(lastTeam).hasHadBye = true;
                    byeCastle = Optional.of(localAff.get(lastTeam));
                    localTA.remove(lastTeam);
                    localAff.remove(lastTeam);
                    succeeded = true;
                } else {
                    lastTeam--;
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
            Team team1 = localAff.get(0);

            //Pair Teams
            Optional<Team> optionalTeam2 = localNeg.stream().filter(e -> !e.code.equalsIgnoreCase(team1.code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code)).findFirst();
            if (!optionalTeam2.isPresent())
                throw new GavelExeception("TEAMS FUUUUUCCCKKK");
            Team team2 = optionalTeam2.get();

            //Pair Judges
            Optional<Judge> roundJudge = localJA.stream().filter(e -> !team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).findAny();
            if (!roundJudge.isPresent())
                throw new GavelExeception("JUDGES FUUUUUCCCKKK");



            /*Pair the room and clean array*/
            {
                Room room = localRA.get(0);
                // pairings.add(Main.pair(team1, team2, roundJudge.get(), room, 2));
                localTA.remove(team1);
                localAff.remove(team1);
                localTA.remove(team2);
                localNeg.remove(team2);
                localJA.remove(roundJudge.get());
                localRA.remove(room);
            }
        }





        //TODO Does this need to be in round 2 or 1?

        pairings.add(byeCastle.orElse(new Team("No one")).code + " has a bye");
        return pairings;


    }
}