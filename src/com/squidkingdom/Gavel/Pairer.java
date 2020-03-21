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

    //Round 1
    public static void pairRound1() throws GavelExeception {
        return;
    }

    //Round 2
    public static void pairRound2() throws GavelExeception {
        ArrayList<Team> localTeamsArray = (ArrayList<Team>) teamArray.clone();
        ArrayList<Team> localJudgeArray = (ArrayList<Team>) teamArray.clone();
        ArrayList<Team> localRoomArray = (ArrayList<Team>) teamArray.clone();

        if ((localTeamsArray.size() / 2) > localJudgeArray.size())
            throw new GavelExeception("Error: Not enough Judges");

        if ((localTeamsArray.size() / 2) > localRoomArray.size())
            throw new GavelExeception("Error: Not enough rooms");

        ArrayList<Team> localAff = localTeamsArray.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> localNeg = localTeamsArray.stream().filter(e -> !e.isAffLead).collect(Collectors.toCollection(ArrayList::new));

        if ((localAff.size() % 2) > 0) {

        }


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