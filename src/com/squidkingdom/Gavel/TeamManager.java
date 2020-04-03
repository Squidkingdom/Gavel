package com.squidkingdom.Gavel;

import java.util.ArrayList;
import java.util.Optional;

public class TeamManager {
    public static final Team dummy = new Team("DUMMY");
    public static ArrayList<Room> roomArray = new ArrayList<Room>(1);
    public static ArrayList<Team> teamArray = new ArrayList<Team>(1);

    public static int JIDArrayLength = 0;

    public TeamManager() {
    }


    public static void newTeam(String code) {
        Team team = new Team(code);
        teamArray.add(team);
        JIDArrayLength++;

    }

    //TODO un comment this for LD
//    public void newTeam(String code, String person1) {
//        Team team = new Team(code, person1);
//
//        teamArray.add(team);
//        JIDArrayLength++;
//
//    }
    public static void newTeam(String code, String person1, String person2) {
        Team team = new Team(code, person1, person2);

        teamArray.add(team);
        JIDArrayLength++;
    }


    public static Team getTeamByCode(String code) throws GavelExeception {
        for (Team team : teamArray) {
            if (team.code.equalsIgnoreCase(code)) {
                return team;
            }

        }
        throw new GavelExeception("Team Code not found");
    }



    public static boolean checkcode(String code) {
        for (Team team : teamArray) {
            if (team.code.equalsIgnoreCase(code)) {
                return true;
            }

        }
        return false;
    }
}