package com.squidkingdom.Gavel;
import java.util.ArrayList;
public class TeamManager {
    public static final Team dummy = new Team("DUMMY");
    public static ArrayList<Room> roomArray = new ArrayList<Room>(1);
    public static ArrayList<Team> teamArray = new ArrayList<Team>(1);

    public static int JIDArrayLength = 0;
    public TeamManager(){
        teamArray.add(dummy);
    }


    public static void newTeam(String code) {
        Team team = new Team(code);
        teamArray.add(team);
        JIDArrayLength++;

    }
    public void newTeam(String code, String person1) {
        Team team = new Team(code, person1);

        teamArray.add(team);
        JIDArrayLength++;

    }
    public void newTeam(String code, String person1, String person2) {
        Team team = new Team(code, person1, person2);

        teamArray.add(team);
        JIDArrayLength++;
    }


    public static Team getTeamByCode(String code){
        for (int i = 0; i < JIDArrayLength + 1; i++) {
            if (teamArray.get(i).code.equalsIgnoreCase(code)) {
                return teamArray.get(i);
            }

        }
        return dummy;
    }
    public static boolean checkcode(String code){
        for (int i = 0; i < JIDArrayLength + 1; i++) {
            if (teamArray.get(i).code.toLowerCase() == code.toLowerCase()) {
                return true;
            }

        }
        return false;
    }
}