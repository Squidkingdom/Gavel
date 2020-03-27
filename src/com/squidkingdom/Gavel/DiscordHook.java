package com.squidkingdom.Gavel;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.*;

import javax.xml.soap.Text;
import java.io.File;
import java.io.PrintStream;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class DiscordHook extends ListenerAdapter {
    static MessageReceivedEvent lastEvent;
    static MessageReceivedEvent botLastEvent;
    static boolean bound = false;
    static TextChannel binding;
    static TextChannel schedule;
    static TextChannel results;

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (!bound || (event.getTextChannel().equals(binding))) {
            try {
                lastEvent = event;
                String anwser = event.getMessage().getContentDisplay();
                if (event.getAuthor().isBot()) {
                    //We said something in chat
                    botLastEvent = event;
                        if (!botLastEvent.getMessage().getAttachments().isEmpty()){
                            System.out.println("Self: *sent a file* " + botLastEvent.getMessage().getAttachments().get(0).getFileName());
                        }else{
                            System.out.println("Self: " + anwser);
                        }
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

                        }
                        else if (anwser.toLowerCase().startsWith("!print")) {

                            String code2 = anwser.split(" ", 5)[1];
                            printInfo(code2);

                        }
                        else if (anwser.toLowerCase().startsWith("!addresult")) {
                            String arg[] = anwser.split(" ");
                            if (arg.length != 9) {
                                print("You did not provide the correct amount of arguments.");
                            } else {
                                selectedResult(Integer.parseInt(arg[1]), Boolean.parseBoolean(arg[2]), arg[3], arg[4], Double.parseDouble(arg[5]), Double.parseDouble(arg[6]), Double.parseDouble(arg[7]), Double.parseDouble(arg[8]));
                            }
                        }
//                        else if (anwser.toLowerCase().startsWith("")) {
//                          //  print("Available options are: New [Code] [Firstname.lastname](1st speaker) [firstname.lastname](2nd Speaker) | judgenew [Code] [Name]| removejudge Print [Code] | AddResult [Room] [AffWon(true)(false)] [Aff Code] [Neg Code] [1A speaks] [2A speaks] [1N speaks] [2N speaks] | Export | SNR | PairManual [team1 code] [team2 code] [judge code] [room id] [round] | Exit");
//
//
//                        }
                        else if (anwser.toLowerCase().startsWith("!snr")) {
                            switch (Main.lastRoundStarted) {
                                case 0:
                                    if (schedule != null) {
                                        schedule.sendFile(Exporter.exportSchedule(Pairer.pairRound1(), "Schedule_Round_1")).queue();
                                    } else {
                                        event.getTextChannel().sendFile(Exporter.exportSchedule(Pairer.pairRound1(), "Schedule_Round_1")).queue();
                                    }
                                    Main.lastRoundStarted++;
                                    break;
                                case 1:
                                    if (schedule != null) {
                                        schedule.sendFile(Exporter.exportSchedule(Pairer.pairRound2(), "Schedule_Round_2")).queue();
                                    } else {
                                        event.getTextChannel().sendFile(Exporter.exportSchedule(Pairer.pairRound2(), "Schedule_Round_2")).queue();
                                    }
                                    Main.lastRoundStarted++;
                                    break;
                                case 2://Round 3
                                    if (TeamManager.teamsFinished(3)) {
                                        if (schedule != null) {
                                            schedule.sendFile(Exporter.exportSchedule(Pairer.pairRound3(), "Schedule_Round_3")).queue();
                                        } else {
                                            event.getTextChannel().sendFile(Exporter.exportSchedule(Pairer.pairRound3(), "Schedule_Round_3")).queue();
                                        }
                                        Main.lastRoundStarted++;
                                    } else {
                                        print("Cant start round, not all rooms finished.");
                                        break;
                                    }
                                case 3://Round 4
                                    if (TeamManager.teamsFinished(4)) {
                                        if (schedule != null) {
                                            schedule.sendFile(Exporter.exportSchedule(Pairer.pairRound4(), "Schedule_round_4")).queue();
                                        } else {
                                            event.getTextChannel().sendFile(Exporter.exportSchedule(Pairer.pairRound4(), "Schedule_Round_4")).queue();
                                        }
                                        Main.lastRoundStarted++;
                                    } else {
                                        print("Cant start round, not all rooms finished.");
                                        break;
                                    }
                                case 4://Round 5
                                    if (TeamManager.teamsFinished(5)) {
                                        if (schedule != null) {
                                            schedule.sendFile(Exporter.exportSchedule(Pairer.pairRound5(), "Schedule_Round_5")).queue();
                                        } else {
                                            event.getTextChannel().sendFile(Exporter.exportSchedule(Pairer.pairRound5(), "Schedule_Round5")).queue();
                                        }
                                        Main.lastRoundStarted++;
                                    } else {
                                        print("Cant start round, not all rooms finished.");
                                        break;
                                    }

                            }

                        }
                        else if (anwser.toLowerCase().startsWith("!removejudge")) {
                            JudgeManager.judgeArray.remove(JudgeManager.getJudgeByCode(anwser.split(" ", 5)[1]));
                            print("Removed judge from the pool.");

                        }
                        else if (anwser.toLowerCase().startsWith("!bulkjudge")) {
                            bulkNewJudge(anwser);

                        }
                        else if (anwser.toLowerCase().startsWith("!bound")) {
                            if (bound) {
                                print("Bound to " + binding.getAsMention());
                            }

                        }
                        else if (anwser.toLowerCase().startsWith("!setspeaks")) {
                            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                                TeamManager.getTeamByCode(anwser.split(" ")[1]).totalSpeaks = Double.parseDouble(anwser.split(" ")[2]);
                            } else {
                                print("You dont have permission.");
                            }
                        }
                        else if (anwser.toLowerCase().startsWith("!removeteam")) {
                            removeTeam(anwser.split(" ")[1]);
                            print("Removed team with code: " + anwser.split(" ")[1]);
                        }
                        else if (anwser.toLowerCase().startsWith("!bind")) {
                            binding = event.getTextChannel();
                            bound = true;
                            print("Successfully bound to " + binding.getAsMention());

                        }
                        else if (anwser.toLowerCase().startsWith("!export")) {
                            if (results != null) {
                                results.sendMessage("Results").addFile(Exporter.exportRounds("Rounds")).queue();
                            } else {
                                event.getTextChannel().sendMessage("Results").addFile(Exporter.exportRounds("Rounds")).queue();
                            }

                        }
                        else if (anwser.toLowerCase().startsWith("!pairmanual")) {
                            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {

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
                            } else {
                                print("You dont have permissions");
                            }
                        }
                        else if (anwser.toLowerCase().startsWith("!addjudge")) {
                            String[] temp = anwser.split(" ");
                            if (temp.length != 3) {
                                print("Invalid Arguments");
                                return;
                            }
                            String judgeCode = anwser.split(" ", 5)[1];
                            String judgeName = anwser.split(" ", 5)[2];

                            JudgeManager.newJudge(judgeName, judgeCode);
                            Judge judge = JudgeManager.getJudgeByCode(judgeCode);

                            print("Created judge with the name of " + judgeName + " and the code " + judgeCode);
                        }
                        else if (anwser.toLowerCase().startsWith("!bulknew")) {
                            bulkNew(anwser);
                        }
                        else if (anwser.toLowerCase().startsWith("!exit")) {
                            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                                print("Shutting Down.");
                                Main.Gavel.removeEventListener(Main.dch);
                                Main.Gavel.shutdown();
                                System.exit(5);

                            } else {
                                print("You do not have permission to do this");
                            }

                        } else if (anwser.toLowerCase().startsWith("!flipaffneg")) {
                            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                                String[] arr = anwser.split(" ");
                                if (arr.length != 2) {
                                    TeamManager.getTeamByCode(arr[1]).isAffLead = !TeamManager.getTeamByCode(arr[1]).isAffLead;
                                } else {
                                    print("Invalid arguments");
                                }

                            } else {
                                print("You do not have permission to do this");
                            }

                        } else if (anwser.toLowerCase().startsWith("!setschedule")) {
                            if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                                List<String> ans = new ArrayList<String>();
                                Collections.addAll(ans, anwser.split(" "));
                                if (ans.size() == 1) {
                                    schedule = event.getTextChannel();
                                    print("Set the results output channel to " + event.getTextChannel().getAsMention());
                                } else if (ans.size() == 2) {
                                    List<TextChannel> temp = event.getGuild().getTextChannelsByName(ans.get(1), true);
                                    if (temp.isEmpty()) {
                                        print("There is no text channel by that name");
                                    } else {
                                        schedule = temp.get(0);
                                        print("Success");
                                    }
                                } else {
                                    print("Invalid Arguments");
                                }
                            }
                        }
                        else if (anwser.toLowerCase().startsWith("!setresults")) {
                            List<String> ans = new ArrayList<String>();
                            Collections.addAll(ans, anwser.split(" "));
                            if (ans.size() == 1) {
                                results = event.getTextChannel();
                                print("Set the results output channel to " + event.getTextChannel().getAsMention());
                            } else if (ans.size() == 2) {
                                List<TextChannel> temp = event.getGuild().getTextChannelsByName(ans.get(1), true);
                                if (temp.isEmpty()) {
                                    print("There is no text channel by that name");
                                } else {
                                    results = temp.get(0);
                                    print("Success");
                                }
                            } else {
                                print("Invalid Arguments");
                            }
                        }
                        else if (anwser.toLowerCase().startsWith("!help")) {
                            print(
                                    "> ```asciidoc\n" +
                                            "> == Gavel Info ==\n" +
                                            "> !help                                  :: Display help for a command.\n" +
                                            "> !bound                                 :: Displays binding status.\n" +
                                            "> \n" +

                                            "> == Data Commands ==\n" +
                                            "> !new [Code] [Name1] [Name2]            :: Adds a new team to pool.\n" +
                                            "> !removeteam [Code]                     :: Removes a team from pool.\n" +
                                            "> !newjudge [Code] [Name]                :: Adds a new judge to pool.\n" +
                                            "> !removejudge [Code]                    :: Removes a judge from the pool.\n" +
                                            "> !print [Code]                          :: Prints result data for a code.\n" +
                                            "> \n"+
                                            ">!addresult [room] [affwon(true/false] [affcode] [negcode] [1A speaks] [1N Speaks] (2A Speaks) ::\n" +
                                            ">    - Enters results/outcome of a given room and teams\n"+

                                            "> \n" +
                                            "> == Bulk Commands ==\n" +
                                            "> !bulkjudge ([Code] [Name])x ∞          :: Adds multiple judges in one go.\n" +
                                            "> !bulknew ([Code] [Name1] [Name2])x ∞   :: Adds multiple teams in one go.\n" +
                                            "> \n" +

                                            "> == Bot Commands ==\n" +
                                            "> !bind                                  :: Binds the bot to the channel.\n" +
                                            "> !snr                                   :: Starts Next Round\n" +
                                            "> !export                                :: Exports current entered results.\n" +
                                            "> \n" +

                                            "> == Admin Commands ==\n" +
                                            "> !pairmanual                            :: Manually Overrides Parings.\n" +
                                            "> !exit                                  :: Use this command to stop the bot.\n" +
                                            "> !setschedule (textchannel)             :: Sets the schedule output channel.\n" +
                                            "> !setresult (textchannel)               :: Sets the result output channel.\n" +
                                            "> !setspeaks [code] [Total Speaks]       :: Use this command to overide speaker points." +
                                            "```" +
                                            "\u200B\n"
                            );

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


            //Bye Speaks
            if (affTeam.hasHadBye && (round == 1)) {
                affTeam.totalSpeaks += (a1s + a2s);
                affTeam.byeComp = true;
            }
            if (negTeam.hasHadBye && (round == 1)) {
                negTeam.totalSpeaks += (n1s + n2s);
                negTeam.byeComp = true;
            }
            if (affTeam.hasHadBye && (round == 2)) {
                if (!affTeam.byeComp) {
                    affTeam.totalSpeaks += (a1s + a2s);
                    affTeam.byeComp = true;
                }
            }
            if (negTeam.hasHadBye && (round == 2)) {
                if (!negTeam.byeComp) {
                    negTeam.totalSpeaks += (n1s + n2s);
                    negTeam.byeComp = true;
                }
            }


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
        } catch (Exception e) {
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
        try {

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