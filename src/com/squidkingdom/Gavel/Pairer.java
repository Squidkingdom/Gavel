package com.squidkingdom.Gavel;

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
                affPool.get(lt).hasHadBye = true;
                byeCastle = Optional.of(affPool.get(lt));
                break;
            }
        }

        int byeOffset = byeCastle.isPresent() ? 1 : 0;

//        if (affPool.size() - byeOffset > (roomPool.size())) {
//            DiscordHook.print("Error: Not Enough judges");
//            throw new GavelExeception("Error: Not enough rooms");
//        }
//        if (affPool.size() - byeOffset > judgePool.size()) {
//            DiscordHook.print("Error: Not Enough judges");
//            throw new GavelExeception("Error: Not enough judges");
//        }

        // Update bye data
        if (byeCastle.isPresent()) {
            teamPool.remove(byeCastle.get());
            affPool.remove(byeCastle.get());

            Team ByeTeam = new Team("BYE", "bye", "bye");
            Judge ByeJudge = new Judge("bye", "BYE");
            byeCastle.get().opp[0] = ByeTeam;
            byeCastle.get().rounds[0] = new Round(true, 3, true, 0, 0, ByeTeam, ByeJudge);
            byeCastle.get().totalWins++;
            byeCastle.get().totalSpeaks = byeCastle.get().totalSpeaks + 3;
            byeCastle.get().roundComplete[0] = true;
            byeCastle.get().rounds[0].didWin = true;
            byeCastle.get().judges[0] = ByeJudge;
            RoomManager.byeRoom.data[0] = new RoundData(byeCastle.get(), ByeTeam, ByeJudge, 3, 0, true, true);
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
                team1.inProgress[0] = true;
                team2.inProgress[0] = true;
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
            pairings.add(new RoundData(byeCastle.get(), new Team("BYE", "bye", "bye"), new Judge("bye", "BYE"), 0));

        return pairings;
    }


    //Round 2
    public static ArrayList<RoundData> pairRound2() throws GavelExeception {
        Optional<Team> byeCastle = Optional.empty();
        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> !e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));

        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                if (!negPool.get(lt).hasHadBye) {
                    negPool.get(lt).hasHadBye = true;
                    byeCastle = Optional.of(negPool.get(lt));
                    break;
                }
            }
        }

        int byeOffset = byeCastle.isPresent() ? 1 : 0;

//        if (negPool.size() - byeOffset > (roomPool.size())) {
//            throw new GavelExeception("Error: Not enough rooms");
//        }
//        if (negPool.size() - byeOffset > judgePool.size()) {
//            throw new GavelExeception("Error: Not enough judges");
//        }

        if (byeCastle.isPresent()) {
            teamPool.remove(byeCastle.get());
            negPool.remove(byeCastle.get());

            Team ByeTeam = new Team("BYE", "bye", "bye");
            Judge ByeJudge = new Judge("bye", "BYE");
            byeCastle.get().opp[1] = ByeTeam;
            byeCastle.get().rounds[1] = new Round(true, 3, true, 0, 0, ByeTeam, ByeJudge);
            byeCastle.get().totalWins++;
            byeCastle.get().totalSpeaks = byeCastle.get().totalSpeaks + 3;
            byeCastle.get().judges[1] = ByeJudge;
            byeCastle.get().roundComplete[1] = true;
            byeCastle.get().rounds[1].didWin = true;
            byeCastle.get().judges[1] = ByeJudge;
            RoomManager.byeRoom.data[1] = new RoundData(byeCastle.get(), ByeTeam, ByeJudge, 3, 0, true, true);
        }

        ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);

        while (negPool.size() > 0) {
            Team team1 = negPool.get(0);
            Optional<Team> optionalTeam2 = affPool.stream()
                    .filter(e -> !e.code.equalsIgnoreCase(team1.code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code))
                    .findFirst();

            if (!optionalTeam2.isPresent())
                throw new GavelExeception("Fuck, No more teams left.");
            Team team2 = optionalTeam2.get();

            //Pair Judges
            Optional<Judge> roundJudge = judgePool.stream()
                    .filter(e -> !team1.judges[0].code.equalsIgnoreCase(e.code))
                    .filter(e -> !team2.judges[0].code.equalsIgnoreCase(e.code))
                    .findAny();

            if (!roundJudge.isPresent())
                throw new GavelExeception("Fuck, No more Judges left.");

            {

                Room room = roomPool.get(0);
                pairings.add(Main.pair(team2, team1, roundJudge.get(), room, 2));
                teamPool.remove(team1);
                negPool.remove(team1);
                teamPool.remove(team2);
                affPool.remove(team2);
                judgePool.remove(roundJudge.get());
                roomPool.remove(room);
            }
        }

        byeCastle.ifPresent(team -> pairings.add(new RoundData(team, new Team("BYE", "bye", "bye"), new Judge("bye", "BYE"), 0)));

        return pairings;
    }


    //Round 3
    public static ArrayList<RoundData> pairRound3() throws GavelExeception {
        Optional<Team> byeCastle = Optional.empty();

        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        WinCompare winCompare = new WinCompare();
        teamPool.sort(winCompare);

        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> !e.isAffLead).collect(Collectors.toCollection(ArrayList::new));

        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                if (!affPool.get(lt).hasHadBye) {
                    affPool.get(lt).hasHadBye = true;
                    byeCastle = Optional.of(affPool.get(lt));
                    break;
                }
            }
        }

        int byeOffset = byeCastle.isPresent() ? 1 : 0;

//        if (negPool.size() - byeOffset > (roomPool.size())) {
//            throw new GavelExeception("Error: Not enough rooms");
//        }
//        if (negPool.size() - byeOffset > judgePool.size()) {
//            throw new GavelExeception("Error: Not enough judges");
//        }

        if (byeCastle.isPresent()) {
            teamPool.remove(byeCastle.get());
            affPool.remove(byeCastle.get());

            Team ByeTeam = new Team("BYE", "bye", "bye");
            Judge ByeJudge = new Judge("bye", "BYE");
            byeCastle.get().opp[2] = ByeTeam;
            byeCastle.get().rounds[2] = new Round(true, 3, true, 0, 0, ByeTeam, ByeJudge);
            byeCastle.get().totalWins++;
            byeCastle.get().totalSpeaks = byeCastle.get().totalSpeaks + 3;
            byeCastle.get().judges[2] = ByeJudge;
            byeCastle.get().roundComplete[2] = true;
            byeCastle.get().rounds[2].didWin = true;
            byeCastle.get().judges[2] = ByeJudge;
            RoomManager.byeRoom.data[2] = new RoundData(byeCastle.get(), ByeTeam, ByeJudge, 3, 0, true, true);
        }

        ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);

        while (affPool.size() > 0) {
            Team team1 = affPool.get(0);
            Optional<Team> optionalTeam2 = negPool.stream()
                    .filter(e -> !e.code.equalsIgnoreCase(team1.code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[1].code))
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
                    .findAny();

            if (!roundJudge.isPresent())
                throw new GavelExeception("Fuck, No more Judges left.");

            {
                Room room = roomPool.get(0);
                pairings.add(Main.pair(team1, team2, roundJudge.get(), room, 3));
                teamPool.remove(team2);
                negPool.remove(team2);
                teamPool.remove(team1);
                affPool.remove(team1);
                judgePool.remove(roundJudge.get());
                roomPool.remove(room);
            }
        }

        if (byeCastle.isPresent())
            pairings.add(new RoundData(byeCastle.get(), new Team("BYE", "bye", "bye"), new Judge("bye", "BYE"), 0));

        return pairings;
    }

    //Round 4
    public static ArrayList<RoundData> pairRound4() throws GavelExeception {

        Optional<Team> byeCastle = Optional.empty();
        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        WinCompare winCompare = new WinCompare();
        teamPool.sort(winCompare);
        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> !e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));

        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                if (!negPool.get(lt).hasHadBye) {
                    negPool.get(lt).hasHadBye = true;
                    byeCastle = Optional.of(negPool.get(lt));
                    break;
                }
            }
        }

        int byeOffset = byeCastle.isPresent() ? 1 : 0;

//        if (negPool.size() - byeOffset > (roomPool.size())) {
//            throw new GavelExeception("Error: Not enough rooms");
//        }
//        if (negPool.size() - byeOffset > judgePool.size()) {
//            throw new GavelExeception("Error: Not enough judges");
//        }

        if (byeCastle.isPresent()) {
            teamPool.remove(byeCastle.get());
            negPool.remove(byeCastle.get());

            Team ByeTeam = new Team("BYE", "bye", "bye");
            Judge ByeJudge = new Judge("bye", "BYE");
            byeCastle.get().opp[3] = ByeTeam;
            byeCastle.get().rounds[3] = new Round(true, 3, true, 0, 0, ByeTeam, ByeJudge);
            byeCastle.get().totalWins++;
            byeCastle.get().totalSpeaks = byeCastle.get().totalSpeaks + 3;
            byeCastle.get().judges[3] = ByeJudge;
            byeCastle.get().roundComplete[3] = true;
            byeCastle.get().rounds[3].didWin = true;
            byeCastle.get().judges[3] = ByeJudge;
            RoomManager.byeRoom.data[3] = new RoundData(byeCastle.get(), ByeTeam, ByeJudge, 3, 0, true, true);
        }

        ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);

        while (negPool.size() > 0) {
            Team team1 = negPool.get(0);
            Optional<Team> optionalTeam2 = affPool.stream()
                    .filter(e -> !e.code.equalsIgnoreCase(team1.code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[1].code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[2].code))
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
                    .findAny();

            if (!roundJudge.isPresent())
                throw new GavelExeception("Fuck, No more Judges left.");

            {
                Room room = roomPool.get(0);
                pairings.add(Main.pair(team2, team1, roundJudge.get(), room, 4));
                teamPool.remove(team1);
                negPool.remove(team1);
                teamPool.remove(team2);
                affPool.remove(team2);
                judgePool.remove(roundJudge.get());
                roomPool.remove(room);
            }
        }

        byeCastle.ifPresent(team -> pairings.add(new RoundData(team, new Team("BYE", "bye", "bye"), new Judge("bye", "BYE"), 0)));

        return pairings;
    }

    //Round 5
    public static ArrayList<RoundData> pairRound5() throws GavelExeception {
        Optional<Team> byeCastle = Optional.empty();

        ArrayList<Team> teamPool = (ArrayList<Team>) teamArray.clone();
        WinCompare winCompare = new WinCompare();
        teamPool.sort(winCompare);

        ArrayList<Room> roomPool = (ArrayList<Room>) roomArray.clone();
        ArrayList<Judge> judgePool = (ArrayList<Judge>) judgeArray.clone();
        ArrayList<Team> affPool = teamPool.stream().filter(e -> e.isAffLead).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Team> negPool = teamPool.stream().filter(e -> !e.isAffLead).collect(Collectors.toCollection(ArrayList::new));

        //Handle Bye
        if (((affPool.size() + negPool.size()) % 2) > 0) {
            for (int lt = (affPool.size() - 1); lt > 0; lt--) {
                if (!affPool.get(lt).hasHadBye) {
                    affPool.get(lt).hasHadBye = true;
                    byeCastle = Optional.of(affPool.get(lt));
                    break;
                }
            }
        }

        int byeOffset = byeCastle.isPresent() ? 1 : 0;

//        if (negPool.size() - byeOffset > (roomPool.size())) {
//            throw new GavelExeception("Error: Not enough rooms");
//        }
//        if (negPool.size() - byeOffset > judgePool.size()) {
//            throw new GavelExeception("Error: Not enough judges");
//        }

        if (byeCastle.isPresent()) {
            teamPool.remove(byeCastle.get());
            affPool.remove(byeCastle.get());

            Team ByeTeam = new Team("BYE", "bye", "bye");
            Judge ByeJudge = new Judge("bye", "BYE");
            byeCastle.get().opp[4] = ByeTeam;
            byeCastle.get().rounds[4] = new Round(true, 3, true, 0, 0, ByeTeam, ByeJudge);
            byeCastle.get().totalWins++;
            byeCastle.get().totalSpeaks = byeCastle.get().totalSpeaks + 3;
            byeCastle.get().judges[4] = ByeJudge;
            byeCastle.get().roundComplete[4] = true;
            byeCastle.get().rounds[4].didWin = true;
            byeCastle.get().judges[4] = ByeJudge;
            RoomManager.byeRoom.data[4] = new RoundData(byeCastle.get(), ByeTeam, ByeJudge, 3, 0, true, true);
        }

        ArrayList<RoundData> pairings = new ArrayList<RoundData>(1);

        while (affPool.size() > 0) {
            Team team1 = affPool.get(0);
            Optional<Team> optionalTeam2 = negPool.stream()
                    .filter(e -> !e.code.equalsIgnoreCase(team1.code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[0].code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[1].code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[2].code))
                    .filter(e -> !e.code.equalsIgnoreCase(team1.opp[3].code))
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
                    .findAny();

            if (!roundJudge.isPresent())
                throw new GavelExeception("Fuck, No more Judges left.");

            {
                Room room = roomPool.get(0);
                pairings.add(Main.pair(team1, team2, roundJudge.get(), room, 5));
                teamPool.remove(team2);
                negPool.remove(team2);
                teamPool.remove(team1);
                affPool.remove(team1);
                judgePool.remove(roundJudge.get());
                roomPool.remove(room);
            }
        }

        if (byeCastle.isPresent())
            pairings.add(new RoundData(byeCastle.get(), new Team("BYE", "bye", "bye"), new Judge("bye", "BYE"), 0));

        return pairings;
    }


}
