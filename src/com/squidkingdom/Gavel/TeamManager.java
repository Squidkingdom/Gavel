package com.squidkingdom.Gavel;

public class TeamManager {
    public static final Team dummy = new Team("DUMMY");
    public static Team[] teamArray = new Team[30];

    public static int JIDArrayLength = 0;
    public TeamManager(){
        teamArray[0] = dummy;
    }


    public static void newTeam(String code) {
        Team team = new Team(code);
            teamArray[JIDArrayLength + 1] = team;
            JIDArrayLength++;

    }
    public void newTeam(String code, String person1) {
        Team team = new Team(code, person1);

            teamArray[JIDArrayLength + 1] = team;
             JIDArrayLength++;

    }
    public void newTeam(String code, String person1, String person2) {
        Team team = new Team(code, person1, person2);

            teamArray[JIDArrayLength + 1] = team;
            JIDArrayLength++;
    }


    public static Team getTeamByCode(String code){
        for (int i = 0; i < JIDArrayLength + 1; i++) {
                if (teamArray[i].code.equalsIgnoreCase(code)) {
                    return teamArray[i];
                }

            }
        return dummy;
    }
    public static boolean checkcode(String code){
        for (int i = 0; i < JIDArrayLength + 1; i++) {
            if (teamArray[i].code.toLowerCase() == code.toLowerCase()) {
                return true;
            }

        }
        return false;
    }
}
