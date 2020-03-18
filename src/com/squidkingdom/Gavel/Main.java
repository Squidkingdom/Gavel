package com.squidkingdom.Gavel;

import java.util.Scanner;

public class Main {
	private static final String FILE_NAME = "C:\\Users\\Brady\\eclipse-workspace2\\Gavel\\tmp\\MyFirstExcel.xlsx";
	public static TeamManager manager = new TeamManager();
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {

		String arg1 = args[0].substring(1);
		String arg2 = args[1].substring(1);
		boolean running = true;
		// '-n' will be new team
		while(running) {
			print("Available options are: New | Print [Code] | Result | Export | Tab | Exit");
			String anwser = in.nextLine();
			if (anwser.toLowerCase().startsWith("new")) {
				selectedNew();
			} else if (anwser.toLowerCase().startsWith("print")) {
				String code2 = anwser.split(" ", 5)[1];
				printInfo(code2);
			} else if (anwser.toLowerCase().startsWith("result")) {
				print("You chose result.");
			} else if (anwser.toLowerCase().startsWith("export")) {
				print("You chose export.");
			} else if (anwser.toLowerCase().startsWith("tab")) {
				print("You chose tab.");
			} else if (anwser.toLowerCase().startsWith("exit")) {
				print("Goodbye...");
				running = false;
			}


		}
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
		if(manager.checkcode(code)){
			print("This code is invalid");
			return;
		}
		print("Code: \"" + manager.getTeamByCode(code).code + "\" and Speakers: " + manager.getTeamByCode(code).person1 +", " + manager.getTeamByCode(code).person2 + "\n");
	}
	public static void print(String print){
		System.out.println(print);
	}
}



