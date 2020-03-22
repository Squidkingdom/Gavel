package com.squidkingdom.Gavel;
/*TODO:
    Remove TNUM and derrivitave, remove need for localTa after filter.
     MAKE TEAMS[] BE FIELD OF JUDGES INSTEAD OF JUDGES[] IN TEAMS
        *(THIS ALLOWS HOT SWAPPING OF JUDGES)
    More rooms than teams available
    handle no bye combos

    FIXME:
        idk what your doing with the returns and pairings, seems half baked, ignoring for now.
 */

import javax.swing.text.html.Option;
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
    public static void pairRound1() throws GavelExeception {
        Optional<Team> byeCastle = Optional.empty();
        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));


        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                //TODO set bye data from previous commit
                affPool.get(lt).hasHadBye = true;
                byeCastle = Optional.of(affPool.get(lt));
                teamPool.remove(byeCastle);
                affPool.remove(byeCastle);

            }
        }
        if ((affPool.size()) > roomPool.size()) {
            throw new GavelExeception("Error: Not enough rooms");
        }
        if ((affPool.size()) > roomPool.size()) {
            throw new GavelExeception("Error: Not enough judges");
        }


        Team team1 =  affPool.get(0);
        Team team2 = negPool.get(0);
        Judge roundJudge = judgePool.get(0);


        while (roomPool.size() != 0) {

            {
                Room room = roomPool.get(0);
                teamPool.remove(team1);
                affPool.remove(team1);
                teamPool.remove(team2);
                negPool.remove(team2);
                judgePool.remove(roundJudge);
                roomPool.remove(room);
            }
        }


    }




    //Round 2
    public static void pairRound2() throws GavelExeception {

        Optional<Team> byeCastle = Optional.empty();
        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));


        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                //TODO set bye data from previous commit
                affPool.get(lt).hasHadBye = true;
                byeCastle = Optional.of(affPool.get(lt));
                teamPool.remove(byeCastle);
                affPool.remove(byeCastle);

            }
        }
        if ((affPool.size()) > roomPool.size()) {
            throw new GavelExeception("Error: Not enough rooms");
        }
        if ((affPool.size()) > roomPool.size()) {
            throw new GavelExeception("Error: Not enough judges");
        }


        Team team1 =  affPool.get(0);
        Optional<Team> optionalTeam2 = negPool.stream().filter(e -> !e.code.equalsIgnoreCase(team1.code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code)).findFirst();
        if (!optionalTeam2.isPresent())
            throw new GavelExeception("Fuck, No more teams left.");
        Team team2 = optionalTeam2.get();

        //Pair Judges
        Optional<Judge> roundJudge = judgePool.stream().filter(e -> !team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).findAny();
        if (!roundJudge.isPresent())
            throw new GavelExeception("Fuck, No more Judges left.");

        while (roomPool.size() != 0) {

            {
                Room room = roomPool.get(0);
                teamPool.remove(team1);
                affPool.remove(team1);
                teamPool.remove(team2);
                negPool.remove(team2);
                judgePool.remove(roundJudge.get());
                roomPool.remove(room);
            }
        }


    }
    }

    //Round 3
    public static void pairRound3() throws GavelExeception {
    }


    //Round 4
    public static void pairRound4() throws GavelExeception {
    }

    //Round 5
    public static void pairRound5() throws GavelExeception {
    }





}  /*

            //Pair Teams
            Optional<Team> optionalTeam2 = negPool.stream().filter(e -> !e.code.equalsIgnoreCase(team1.code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code)).findFirst();
            if (!optionalTeam2.isPresent())
                throw new GavelExeception("Fuck, No more teams left.");
            Team team2 = optionalTeam2.get();

            //Pair Judges
            Optional<Judge> roundJudge = judgePool.stream().filter(e -> !team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).findAny();
            if (!roundJudge.isPresent())
                throw new GavelExeception("Fuck, No more Judges left.");

             WinCompare winCompare = new WinCompare();
                  Collections.sort(localTA, winCompare);

     */
