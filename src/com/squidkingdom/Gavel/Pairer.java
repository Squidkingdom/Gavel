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

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings({"unchecked", "UnusedReturnValue"})
public class Pairer {
    public static ArrayList<Room> roomArray = RoomManager.roomArray;
    public static ArrayList<Judge> judgeArray = JudgeManager.judgeArray;
    public static ArrayList<Team> teamArray = TeamManager.teamArray;

    //Round 3
    public static ArrayList<RoundData> pairRound1() throws GavelExeception {
        Optional<Team> byeCastle = Optional.empty();
        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> !e.isAffLead).collect(Collectors.toCollection(ArrayList::new));


        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                //TODO set bye data from previous commit
                affPool.get(lt).hasHadBye = true;
                byeCastle = Optional.of(affPool.get(lt));
                teamPool.remove(byeCastle.get());
                affPool.remove(byeCastle.get());
                break;
            }
        }
        if ((affPool.size()) > roomPool.size()) {
            throw new GavelExeception("Error: Not enough rooms");
        }
        if ((affPool.size()) > judgePool.size()) {
            throw new GavelExeception("Error: Not enough judges");
        }


        Team team1;
        Team team2;
        Judge roundJudge;
        ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);

        while (affPool.size() > 0) {
            {
                team1 = affPool.get(0);
                team2 = negPool.get(0);
                roundJudge = judgePool.get(0);
                Room room = roomPool.get(0);
                pairings.add(Main.pair(team1, team2, roundJudge, room, 1));
                teamPool.remove(team1);
                affPool.remove(team1);
                teamPool.remove(team2);
                negPool.remove(team2);
                judgePool.remove(roundJudge);
                roomPool.remove(room);
            }
        }

        if (byeCastle.isPresent())
            pairings.add(new RoundData(byeCastle.get(), new Team("BYE", "bye", "bye"), new Judge("bye", "BYE")));

        return pairings;
    }


    //Round 2
    public static void pairRound2() throws GavelExeception {
        Optional<Team> byeCastle;
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
                    teamPool.remove(byeCastle.get());
                    affPool.remove(byeCastle.get());
            }
        }
        if (affPool.size() > roomPool.size()) {
            throw new GavelExeception("Error: Not enough rooms");
        }
        if ((affPool.size()) > judgePool.size()) {
            throw new GavelExeception("Error: Not enough judges");
        }


        Team team1 = affPool.get(0);
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


    //Round 3
    public static void pairRound3() throws GavelExeception {
        Optional<Team> byeCastle;
        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        WinCompare winCompare = new WinCompare();
        teamPool.sort(winCompare);


        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                //TODO set bye data from previous commit
                affPool.get(lt).hasHadBye = true;
                byeCastle = Optional.of(affPool.get(lt));
                teamPool.remove(byeCastle.get());
                affPool.remove(byeCastle.get());
            }
        }
        if (affPool.size() > roomPool.size()) {
            throw new GavelExeception("Error: Not enough rooms");
        }
        if ((affPool.size()) > judgePool.size()) {
            throw new GavelExeception("Error: Not enough judges");
        }


        Team team1 = affPool.get(0);
        Optional<Team> optionalTeam2 = negPool.stream().filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[1].code)).findFirst();
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



    //Round 4
    public static void pairRound4() throws GavelExeception {

        Optional<Team> byeCastle;
        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        WinCompare winCompare = new WinCompare();
        teamPool.sort(winCompare);


        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                //TODO set bye data from previous commit
                affPool.get(lt).hasHadBye = true;
                byeCastle = Optional.of(affPool.get(lt));
                teamPool.remove(byeCastle.get());
                affPool.remove(byeCastle.get());
            }
        }
        if (affPool.size() > roomPool.size()) {
            throw new GavelExeception("Error: Not enough rooms");
        }
        if ((affPool.size()) > judgePool.size()) {
            throw new GavelExeception("Error: Not enough judges");
        }


        Team team1 = affPool.get(0);
        Optional<Team> optionalTeam2 = negPool.stream().filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[1].code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[2].code)).filter(e -> !e.code.equalsIgnoreCase(team1.opp[3].code)).findFirst();
        if (!optionalTeam2.isPresent())
            throw new GavelExeception("Fuck, No more teams left.");
        Team team2 = optionalTeam2.get();

        //Pair Judges
        Optional<Judge> roundJudge = judgePool.stream().filter(e -> !team1.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[0].code.equalsIgnoreCase(e.code)).filter(e -> !team1.judges[1].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code)).filter(e -> !team1.judges[2].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[2].code.equalsIgnoreCase(e.code)).filter(e -> !team1.judges[3].code.equalsIgnoreCase(e.code)).filter(e -> !team2.judges[3].code.equalsIgnoreCase(e.code)).findAny();
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

    //Round 5
    public static void pairRound5() throws GavelExeception {

        Optional<Team> byeCastle;
        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        WinCompare winCompare = new WinCompare();
        teamPool.sort(winCompare);


        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                //TODO set bye data from previous commit
                affPool.get(lt).hasHadBye = true;
                byeCastle = Optional.of(affPool.get(lt));
                teamPool.remove(byeCastle.get());
                affPool.remove(byeCastle.get());
            }
        }
        if (affPool.size() > roomPool.size()) {
            throw new GavelExeception("Error: Not enough rooms");
        }
        if ((affPool.size()) > judgePool.size()) {
            throw new GavelExeception("Error: Not enough judges");
        }


        Team team1 = affPool.get(0);
        Optional<Team> optionalTeam2 = negPool.stream()
                .filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code))
                .filter(e -> !e.code.equalsIgnoreCase(team1.opp[1].code))
                .filter(e -> !e.code.equalsIgnoreCase(team1.opp[2].code))
                .filter(e -> !e.code.equalsIgnoreCase(team1.opp[3].code))
                .filter(e -> !e.code.equalsIgnoreCase(team1.opp[4].code))
                .findFirst();
        if (!optionalTeam2.isPresent())
            throw new GavelExeception("Fuck, No more teams left.");
        Team team2 = optionalTeam2.get();

        //Pair Judges
        Optional<Judge> roundJudge = judgePool.stream()
                .filter(e -> !team1.judges[0].code.equalsIgnoreCase(e.code))
                .filter(e -> !team2.judges[0].code.equalsIgnoreCase(e.code))
                .filter(e -> !team1.judges[1].code.equalsIgnoreCase(e.code))
                .filter(e -> !team2.judges[1].code.equalsIgnoreCase(e.code))
                .filter(e -> !team1.judges[2].code.equalsIgnoreCase(e.code))
                .filter(e -> !team2.judges[2].code.equalsIgnoreCase(e.code))
                .filter(e -> !team1.judges[3].code.equalsIgnoreCase(e.code))
                .filter(e -> !team2.judges[3].code.equalsIgnoreCase(e.code))
                .filter(e -> !team1.judges[4].code.equalsIgnoreCase(e.code))
                .filter(e -> !team2.judges[4].code.equalsIgnoreCase(e.code)).findAny();
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
