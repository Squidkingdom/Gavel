package com.squidkingdom.Gavel;

import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class DiscordHook extends ListenerAdapter {
    static MessageReceivedEvent lastEvent;
    static MessageReceivedEvent botLastEvent;
    static boolean bound = false;
    static TextChannel binding;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!bound || (event.getTextChannel().equals(binding))) {
            try {
                lastEvent = event;
                String anwser = event.getMessage().getContentDisplay();
                if (event.getAuthor().isBot()) {
                    //We said something in chat
                    botLastEvent = event;
                    System.out.println("Self: " + anwser);
                } else {
                    if (event.isFromType(ChannelType.TEXT)) {
                        Guild guild = event.getGuild();
                        Member member = event.getMember();
                        // Uncomment for verbose

                        //event.getChannel().sendMessage(anwser).queue();

                        if (anwser.toLowerCase().startsWith("!new")) {
                            if (anwser.split(" ").length != 4) {
                                print("You did not provide the correct amount of arguments.");
                            } else {

                                selectedNew(anwser);
                            }

                        } else if (anwser.toLowerCase().startsWith("!print")) {

                            String code2 = anwser.split(" ", 5)[1];
                            printInfo(code2);

                        } else if (anwser.toLowerCase().startsWith("!addresult")) {
                            String arg[] = anwser.split(" ");
                            if (arg.length != 9) {
                                print("You did not provide the correct amount of arguments.");
                            } else {
                                selectedResult(Integer.parseInt(arg[1]), Boolean.parseBoolean(arg[2]), arg[3], arg[4], Double.parseDouble(arg[5]), Double.parseDouble(arg[6]), Double.parseDouble(arg[7]), Double.parseDouble(arg[8]));
                            }
                        } else if (anwser.toLowerCase().startsWith("!help")) {
                            print("Available options are: New [Code] [Firstname.lastname](1st speaker) [firstname.lastname](2nd Speaker) | judgenew [Code] [Name]| removejudge Print [Code] | AddResult [Room] [AffWon(true)(false)] [Aff Code] [Neg Code] [1A speaks] [2A speaks] [1N speaks] [2N speaks] | Export | SNR | PairManual [team1 code] [team2 code] [judge code] [room id] [round] | Exit");


                        } else if (anwser.toLowerCase().startsWith("!snr")) {
                            switch (Main.lastRoundStarted) {
                                case 0:
                                    event.getGuild().getTextChannelsByName("results", true).get(0).sendFile(Exporter.exportSchedule(Pairer.pairRound1(), "schedule_round_1")).queue();
                                    Main.lastRoundStarted++;
                                    break;
                                case 1:
                                    event.getGuild().getTextChannelsByName("results", true).get(0).sendFile(Exporter.exportSchedule(Pairer.pairRound2(), "schedule_round_2")).queue();
                                    Main.lastRoundStarted++;
                                    break;
                                case 2://Round 3
                                    if (TeamManager.teamsFinished(3)) {
                                        event.getGuild().getTextChannelsByName("results", true).get(0).sendFile(Exporter.exportSchedule(Pairer.pairRound3(), "schedule_round_3")).queue();
                                        Main.lastRoundStarted++;
                                    } else {
                                        print("Cant start round, not all rooms finished.");
                                        break;
                                    }
                                case 3://Round 4
                                    if (TeamManager.teamsFinished(4)) {
                                        event.getGuild().getTextChannelsByName("results", true).get(0).sendFile(Exporter.exportSchedule(Pairer.pairRound4(), "schedule_round_4")).queue();
                                        Main.lastRoundStarted++;
                                    } else {
                                        print("Cant start round, not all rooms finished.");
                                        break;
                                    }
                                case 4://Round 5
                                    if (TeamManager.teamsFinished(5)) {
                                        event.getGuild().getTextChannelsByName("results", true).get(0).sendFile(Exporter.exportSchedule(Pairer.pairRound4(), "schedule_round_5")).queue();
                                        Main.lastRoundStarted++;
                                    } else {
                                        print("Cant start round, not all rooms finished.");
                                        break;
                                    }

                            }

                        } else if (anwser.toLowerCase().startsWith("!removejudge")) {
                            JudgeManager.judgeArray.remove(JudgeManager.getJudgeByCode(anwser.split(" ", 5)[1]));
                            print("Removed judge from the pool.");

                        } else if (anwser.toLowerCase().startsWith("!bulkjudge")) {
                            bulkNewJudge(anwser);

                        } else if (anwser.toLowerCase().startsWith("!removeteam")) {
                            removeTeam(anwser.split(" ")[1]);
                        } else if (anwser.toLowerCase().startsWith("!test")) {
                            print("Boi");

                        } else if (anwser.toLowerCase().startsWith("!bind")) {
                            binding = event.getTextChannel();
                            bound = true;
                            print("Successfully bound to " + binding.getAsMention());

                        } else if (anwser.toLowerCase().startsWith("!export")) {
                            TextChannel text = event.getGuild().getTextChannelsByName("tabschedule", true).get(0);
                            text.sendMessage("Results").addFile(Exporter.exportRounds("Rounds")).queue();

                        } else if (anwser.toLowerCase().startsWith("!pairmanual")) {

                            String team1Code = anwser.split(" ", 6)[1];
                            String team2Code = anwser.split(" ", 6)[2];
                            String judgeCode = anwser.split(" ", 6)[3];
                            int roomId = Integer.parseInt(anwser.split(" ", 6)[4]);
                            int roundNumber = Integer.parseInt(anwser.split(" ", 6)[5]);

                            Team team1 = TeamManager.getTeamByCode(team1Code);
                            Team team2 = TeamManager.getTeamByCode(team2Code);
                            Judge judge = JudgeManager.getJudgeByCode(judgeCode);
                            Room room = RoomManager.getRoomById(roomId);

                            pair(team1, team2, judge, room, roundNumber);
                            print("Team " + team1Code + " was paired with Team " + team2Code + " with the judge " + judgeCode + " in room " + roomId);

                        } else if (anwser.toLowerCase().startsWith("!judgenew")) {
                            String judgeCode = anwser.split(" ", 5)[1];
                            String judgeName = anwser.split(" ", 5)[2];

                            JudgeManager.newJudge(judgeName, judgeCode);
                            Judge judge = JudgeManager.getJudgeByCode(judgeCode);

                            print("Created judge with the name of " + judgeName + " and the code " + judgeCode);
                        } else if (anwser.toLowerCase().startsWith("!bulknew")) {
                            bulkNew(anwser);
                        }
                    } else {
                        //This is run when we get a DM.
                        System.out.println(event.getMessage().getContentDisplay());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void selectedResult(int id, boolean affWon, String acode, String ncode, double a1s, double a2s, double n1s, double n2s) {
        try {
            Team affTeam = TeamManager.getTeamByCode(acode);
            Team negTeam = TeamManager.getTeamByCode(ncode);
            int round = 1;
            for (int i = 0; i <= 5; i++) {
                if (!affTeam.roundComplete[i]) {
                    round = i;
                    break;
                }
            }

            if (!affTeam.inProgress[round]) {
                print("This round is not in progress, you cannot give results for it");
                return;
            }
            affTeam.inProgress[round] = false;
            negTeam.inProgress[round] = false;


            //TODO test this
            if ((((a1s > 30) || a2s < 25) || ((a1s < 25) || a2s > 30)) || (((n1s > 30) || n2s < 25) || ((n1s < 25) || n2s > 30))) {
                print("This is not a valid speaker combo, please contact " + affTeam.rounds[round].judge.name);
                return;
            }
            //  if (!(a1s + a2s + n1s + n2s == 10)) {
            //   print("This is not a valid speaker combo, please contact " + affTeam.rounds[round].judge.name);
            //    return;
            // }
            //  if ((a1s + a2s > 5 && affWon) || (n2s + n1s < 5 && affWon)) {
            //     print("This is not a valid speaker combo, please contact " + affTeam.rounds[round].judge.name);
            //     return;
            //  }


            //Set Aff Data
            Round roundAffObj = new Round(true, (a1s + a2s), affWon, id, (n2s + n1s), negTeam, affTeam.judges[round]);
            affTeam.rounds[round] = roundAffObj;
            affTeam.totalSpeaks += (a1s + a2s);
            affTeam.roundComplete[round] = true;
            affTeam.totalWins += affWon ? 1 : 0;
            affTeam.rounds[round].didWin = affWon;

            //Set Neg Data
            Round roundNegObj = new Round(false, (a1s + a2s), !affWon, id, (n2s + n1s), affTeam, affTeam.judges[round]);
            negTeam.rounds[round] = roundNegObj;
            negTeam.totalSpeaks += (n1s + n2s);
            negTeam.roundComplete[round] = true;
            negTeam.totalWins += !affWon ? 1 : 0;
            negTeam.rounds[round].didWin = !affWon;

            //Set Room Data
            RoundData roundRmObj = new RoundData(affTeam, negTeam, affTeam.rounds[round].judge, (a1s + a2s), (n1s + n2s), affWon, true);
            RoomManager.getRoomById(id).data[round] = roundRmObj;

            //Testing
            String bool = "";
            print("Spot Check: " + roundRmObj.negSpeaks + " " + roundRmObj.affSpeaks + "" + String.valueOf(bool));
        } catch (GavelExeception e) {
            print("Team Code not found");
            System.out.println(e);
        }
    }

    public static void selectedNew(String ans) {
        try {
            String[] anwser = ans.split(" ");
            String code = anwser[1];
            if (TeamManager.checkcode(code)) {
                print("This code is taken");
                return;
            }
            String player1 = anwser[2];
            String player2 = anwser[3];
            TeamManager.newTeam(code, player1, player2);
            print("Made new team with Code: \"" + TeamManager.getTeamByCode(code).code + "\" and Speakers: " + TeamManager.getTeamByCode(code).person1 + ", " + TeamManager.getTeamByCode(code).person2 + "\n");
        } catch (GavelExeception e) {
            print("Team Code not found");
            System.out.println(e);
        }
    }

    public void bulkNew(String ans) {
        try {
            List<String> anwser = new ArrayList<String>();
            Collections.addAll(anwser, ans.split(" "));


            anwser.remove(0);
            if ((anwser.size() % 3) != 0) {
                print("Invalid Arguments");
                return;
            }

            String temp = lastEvent.getTextChannel().sendMessage("Processing...").complete().getId();
            int teams = (anwser.size() / 3);
            for (int i = 0; i < teams; i++) {
                String code = anwser.get((i * 3));
                String player1 = anwser.get((i * 3) + 1);
                String player2 = anwser.get((i * 3) + 2);
                TeamManager.newTeam(code, player1, player2);

                lastEvent.getTextChannel().editMessageFormatById(temp, "Made new Team with Code: \"" + TeamManager.getTeamByCode(code).code + "\" and Names: " + TeamManager.getTeamByCode(code).person1 + ", " + TeamManager.getTeamByCode(code).person2).queue();
            }
            lastEvent.getTextChannel().editMessageFormatById(temp, "Finished").queue();
            Main.print("Self: Finished");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void bulkNewJudge(String ans) throws InterruptedException {
        List<String> anwser = new ArrayList<String>();
        Collections.addAll(anwser, ans.split(" "));

        String temp = lastEvent.getTextChannel().sendMessage("Processing...").complete().getId();
        anwser.remove(0);
        if ((anwser.size() % 2) != 0) {
            print("Invalid Arguments");
            return;
        }
        int teams = (anwser.size() / 2);

        for (int i = 0; i < teams; i++) {
            String code = anwser.get((i * 2));
            String player1 = anwser.get((i * 2) + 1);
            JudgeManager.newJudge(player1, code);
            lastEvent.getTextChannel().editMessageFormatById(temp, "Made new Judge with Code: \"" + JudgeManager.getJudgeByCode(code).code + "\" and Name: " + JudgeManager.getJudgeByCode(code).name + "...").queue();

        }
        lastEvent.getTextChannel().editMessageFormatById(temp, "Finished").queue();
    }
    public static void removeTeam(String code) throws GavelExeception {
        TeamManager.teamArray.remove(TeamManager.getTeamByCode(code));
    }

    public static void printInfo(String code) {
        try{

        int lastRound = 0;
        //Make Sure code is valid
        if (!TeamManager.checkcode(code)) {
            print("This code is invalid");
            return;
        }
        Team team = TeamManager.getTeamByCode(code);
        print("Code: \"" + team.code + "\" and Speakers: " + team.person1 + ", " + team.person2 + "\n");

        for (int i = 0; i < 5; i++) {
            if (team.roundComplete[i]) {
                Round round = team.rounds[i];
                String didWin = round.didWin ? "Won on a " : "Lost on a ";
                String side = round.side ? "Aff" : "Neg";
                double speaks = round.side ? round.affSpeaks : round.negSpeaks;
                print("Round " + (i + 1) + ": Side: \"" + side + "\" Outcome: \"" + didWin + speaks + "\" Opp Code: \"" + round.oppTeam.code + "\" Judge: \"" + round.judge.name + "\" Room: \"" + round.roomnum + "\"");
            }
        }

        }catch(GavelExeception e){
            print("Team Code not found");
            System.out.println(e);
        }
    }

    public static RoundData pair(Team team1, Team team2, Judge judge, Room room, int event) {
        RoundData roundData = new RoundData(team1, team2, judge, room.id);
        room.data[event - 1] = roundData;

        team1.rounds[event - 1] = new Round(true, team2, judge, room.id);
        team1.opp[event - 1] = team2;
        team1.judges[event - 1] = judge;
        team1.inProgress[event - 1] = true;

        team2.rounds[event - 1] = new Round(false, team1, judge, room.id);
        team2.opp[event - 1] = team1;
        team2.judges[event - 1] = judge;
        team2.inProgress[event - 1] = true;

        return roundData;
    }


    public static void test() {
    }

    public static void print(String s) {
        if (lastEvent.isFromType(ChannelType.TEXT)) {
            lastEvent.getTextChannel().sendMessage(s).queue();
        }
    }
}