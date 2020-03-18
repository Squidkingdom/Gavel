package com.squidkingdom.Gavel;

import java.util.Scanner;

@SuppressWarnings("ALL")
public class Main {
	public static TeamManager manager = new TeamManager();
	public static Scanner in = new Scanner(System.in);
	public static final int roomNum = 10;

	public static void main(String[] args) {

		String arg1 = args[0].substring(1);
		String arg2 = args[1].substring(1);
		boolean running = true;
		// '-n' will be new team
		while(running) {
			print("Available options are: New | Print [Code] | Result | Export | Tab | SNR | Exit");
			String anwser = in.nextLine();
			if (anwser.toLowerCase().startsWith("new")) {
				selectedNew();
			} else if (anwser.toLowerCase().startsWith("print")) {
				String code2 = anwser.split(" ", 5)[1];

				printInfo(code2);
			} else if (anwser.toLowerCase().startsWith("result")) {
				//TODO fix this
				//selectedResult();
			} else if (anwser.toLowerCase().startsWith("export")) {

			} else if (anwser.toLowerCase().startsWith("tab")) {
				print("You chose tab.");
			} else if (anwser.toLowerCase().startsWith("snr")) {
				print("You chose tab.");
			}
			else if (anwser.toLowerCase().startsWith("exit")) {
				print("Goodbye...");
				running = false;
			}


		}
	}

	public static void selectedResult( boolean affWon, String acode, String ncode, int a1s, int a2s, int n1s, int n2s){
		Team affTeam = manager.getTeamByCode(acode);
		Team negTeam = manager.getTeamByCode(ncode);
		int round = 1;
		for (int i = 0; i < 5; i++) {
			if (!affTeam.roundComplete[i]) {
				round = i;
			}
		}
		//Validate Data
		if(!(a1s + a2s + n1s + n2s == 10)){
			print("This is not a valid speaker combo, please contact " + affTeam.rounds[round].judge.name );
			return;
		}
		else if ((a1s + a2s > 5 && affWon) || (n2s + n1s < 5 && affWon)){
			print("This is not a valid speaker combo, please contact " + affTeam.rounds[round].judge.name );
			return;
		}


		//Set Aff Data
		Round affRound = affTeam.rounds[round];
		affRound.affSpeaks = (a1s + a2s);
		affRound.didWin = affWon;
		affRound.negSpeaks = (n2s + n1s);
		affRound.oppTeam = negTeam;
		affTeam.totalSpeaks += (a1s + a2s);
		affTeam.roundComplete[round] = true;
		affTeam.totalWins += affWon ?  1 : 0;
		//Set Neg Data
		Round negRound = negTeam.rounds[round];
		negRound.affSpeaks = (a1s + a2s);
		negRound.didWin = !affWon;
		negRound.negSpeaks = (n2s + n1s);
		negRound.oppTeam = affTeam;
		negTeam.totalSpeaks += (n1s + n2s);
		negTeam.roundComplete[round] = true;
		negTeam.totalWins += !affWon ?  1 : 0;




	}


	public static void selectedNew(){
		print("Enter the code for this new team");
		String code = in.nextLine();
		if(manager.checkcode(code)){
			print("This code is taken");
			return;
		}
		print("Enter the 1st Speaker's name.");
		String player1 = in.nextLine();
		print("Enter the 2st Speaker's name.");
		String player2 = in.nextLine();
		manager.newTeam(code, player1, player2);
		print("Made new team with Code: \"" + manager.getTeamByCode(code).code + "\" and Speakers: " + manager.getTeamByCode(code).person1 +", " + manager.getTeamByCode(code).person2 + "\n");
	}

	public static void printInfo(String code){
		Team team = manager.getTeamByCode(code);
		int lastRound = 0;
		//Make Sure code is valid
		if(manager.checkcode(code)){
			print("This code is invalid");
			return;
		}
		print("Code: \"" + team.code + "\" and Speakers: " + team.person1 +", " + team.person2 + "\n");

		for (int i = 0; i < 5; i++) {
			if (!team.roundComplete[i]) {
				lastRound = i;
			}
		}





	}
	public static void print(String print){
		System.out.println(print);
	}
}



