package com.squidkingdom.Gavel;

public class Team {
	int totalWins = 0;
	int totalSpeaks = 0;
	Judge judges[]= new Judge[5];
	Round rounds[]= new Round[5];
	Team opp[] = new Team[5];
	boolean[] roundComplete = new boolean[]{false, false, false, false, false};

	String code = "";
	String person1 = "";
	String person2 = "";
	
	public Team (){

	}
	public Team(String code) {
		this.code = code;
	}
	public Team(String code, String person1) {
		this.code = code;
		this.person1 = person1;
	}
	public Team(String code, String person1, String person2) {
		this.code = code;
		this.person1 = person1;
		this.person2 = person2;
	}






	
}
