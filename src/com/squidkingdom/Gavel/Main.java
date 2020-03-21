package com.squidkingdom.Gavel;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.Scanner;

@SuppressWarnings("ALL")

//TODO? Comment system?
public class Main {
    public static TeamManager manager = new TeamManager();
    public static Scanner in = new Scanner(System.in);
    public static final int roomNum = 10;
    public static boolean speaksOrRanks = true;  //This is speaks

    public static void main(String[] args) {
        boolean running = true;

        for (int i = 0; i < roomNum; i++) {
            RoomManager.newRoom();
        }
        while (running) {
            try {
                /*
                JudgeNew
                 */
                print("Available options are: New | JudgeNew [Name] [Code] | Print [Code] | AddResult | Export | Start | SNR | PairManual [team1 code] [team2 code] [judge code] [room id] [round] | Exit");
                String anwser = in.nextLine();
                if (anwser.toLowerCase().startsWith("new")) {
                    selectedNew();
                } else if (anwser.toLowerCase().startsWith("print")) {
                    String code2 = anwser.split(" ", 5)[1];

                    printInfo(code2);
                } else if (anwser.toLowerCase().startsWith("addresult")) {
                    String arg[] = anwser.split(" ");
                    if (arg.length != 9) {
                        print("You did not provide the correct amount of arguments.");
                        return;
                    }
                    selectedResult(Integer.parseInt(arg[1]), Boolean.valueOf(arg[2]), arg[3], arg[4], Integer.parseInt(arg[5]), Integer.parseInt(arg[6]), Integer.parseInt(arg[7]), Integer.parseInt(arg[8]));
                } else if (anwser.toLowerCase().startsWith("export")) {


                } else if (anwser.toLowerCase().startsWith("start")) {
                    print("You chose tab.");
                } else if (anwser.toLowerCase().startsWith("snr")) {
                    print("You chose tab.");
                } else if (anwser.toLowerCase().startsWith("exit")) {
                    print("Goodbye...");
                    running = false;
                } else if (anwser.toLowerCase().startsWith("pairmanual")) {
                    String team1Code = anwser.split(" ", 6)[1];
                    String team2Code = anwser.split(" ", 6)[2];
                    String judgeCode = anwser.split(" ", 6)[3];
                    int roomId = Integer.parseInt(anwser.split(" ", 6)[4]);
                    int roundNumber = Integer.parseInt(anwser.split(" ", 6)[5]);

                    Team team1 = manager.getTeamByCode(team1Code);
                    Team team2 = manager.getTeamByCode(team2Code);
                    Judge judge = JudgeManager.getJudgeByCode(judgeCode);
                    Room room = RoomManager.getRoomById(roomId);

                    pair(team1, team2, judge, room, roundNumber);
                    print("Team " + team1Code + " was paired with Team " + team2Code + " with the judge " + judgeCode + " in room " + roomId);

                } else if (anwser.toLowerCase().startsWith("judgenew")) {
                    String judgeName = anwser.split(" ", 5)[1];
                    String judgeCode = anwser.split(" ", 5)[2];

                    JudgeManager.newJudge(judgeName, judgeCode);
                    Judge judge = JudgeManager.getJudgeByCode(judgeCode);

                    print("Created judge with the name of " + judgeName + " and the code " + judgeCode);
                }
            } catch (Exception exception) {
                print("There was an error. Please retry");
            }

        }
    }

    // addresult 10 true 1 a 1 3 2 4
    public static void selectedResult(int id, boolean affWon, String acode, String ncode, int a1s, int a2s, int n1s, int n2s) {
        Team affTeam = manager.getTeamByCode(acode);
        Team negTeam = manager.getTeamByCode(ncode);

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

        //TODO make speaks double, add check between 25-30
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

        //Set Neg Data
        Round roundNegObj = new Round(false, (a1s + a2s), !affWon, id, (n2s + n1s), affTeam, affTeam.judges[round]);
        negTeam.rounds[round] = roundNegObj;
        negTeam.totalSpeaks += (n1s + n2s);
        negTeam.roundComplete[round] = true;
        negTeam.totalWins += !affWon ? 1 : 0;

        //Set Room Data
        RoundData roundRmObj = new RoundData(affTeam, negTeam, affTeam.rounds[round].judge, (a1s + a2s), (n1s + n2s), affWon, true);
        RoomManager.getRoomById(id).data[round] = roundRmObj;

        //Testing
        String bool = " ";
        bool.valueOf(bool);
        print("Spot Check: " + roundRmObj.negSpeaks + " " + roundRmObj.affSpeaks + "" + bool.valueOf(bool));
    }

    public static void selectedNew() {
        print("Enter the code for this new team");
        String code = in.nextLine();
        if (manager.checkcode(code)) {
            print("This code is taken");
            return;
        }
        print("Enter the 1st Speaker's name.");
        String player1 = in.nextLine();
        print("Enter the 2st Speaker's name.");
        String player2 = in.nextLine();
        manager.newTeam(code, player1, player2);
        print("Made new team with Code: \"" + manager.getTeamByCode(code).code + "\" and Speakers: " + manager.getTeamByCode(code).person1 + ", " + manager.getTeamByCode(code).person2 + "\n");
    }

    public static void printInfo(String code) {
        Team team = manager.getTeamByCode(code);
        int lastRound = 0;
        //Make Sure code is valid
        if (manager.checkcode(code)) {
            print("This code is invalid");
            return;
        }
        print("Code: \"" + team.code + "\" and Speakers: " + team.person1 + ", " + team.person2 + "\n");

        for (int i = 0; i < 5; i++) {
            if (team.roundComplete[i]) {
                Round round = team.rounds[i];
                String didWin = round.didWin ? "Won on a " : "Lost on a ";
                String side = round.side ? "Aff" : "Neg";
                int speaks = round.side ? round.affSpeaks : round.negSpeaks;
                print("Round " + (i + 1) + ": Side: \"" + side + "\" Outcome: \"" + didWin + speaks + "\" Opp Code: \"" + round.oppTeam.code + "\" Judge: \"" + round.judge.name + "\" Room: \"" + round.roomnum + "\"");
            }
        }


    }

    public static RoundData pair(Team team1, Team team2, Judge judge, Room room, int event) {
        RoundData roundData = new RoundData(team1, team2, judge);
        room.data[event - 1] = roundData;

        team1.rounds[event - 1] = new Round(true, team2, judge);
        team1.opp[event - 1] = team2;
        team1.judges[event - 1] = judge;
        team1.inProgress[event - 1] = true;

        team2.rounds[event - 1] = new Round(false, team1, judge);
        team2.opp[event - 1] = team1;
        team2.judges[event - 1] = judge;
        team2.inProgress[event - 1] = true;

        return roundData;
    }

    public static void print(String print) {
        System.out.println(print);
    }
}



