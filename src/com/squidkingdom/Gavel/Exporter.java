package com.squidkingdom.Gavel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Exporter {

    public static File exportSchedule(ArrayList<RoundData> rounds, String fileName) throws IOException {
        File file = new File("tmp/" + fileName + ".csv");
        FileWriter csvWriter = new FileWriter(file);
        csvWriter.append("Round " + (Main.lastRoundStarted + 1));
        csvWriter.append("\n");
        csvWriter.append("Aff Team");
        csvWriter.append(",");
        csvWriter.append("Neg Team");
        csvWriter.append(",");
        csvWriter.append("Judge");
        csvWriter.append(",");
        csvWriter.append("Room");
        csvWriter.append("\n");

        for (RoundData round : rounds) {
            csvWriter.append(String.join(",", round.affTeam.code, round.negTeam.code, round.judge.code, Integer.toString(round.room)));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
        return file;
    }

    public static File exportRounds(String fileName) throws IOException {
        File file = new File("tmp/" + fileName + ".csv");
        FileWriter csvWriter = new FileWriter(file);
        csvWriter.append("Round");
        csvWriter.append(",");
        csvWriter.append("Winner");
        csvWriter.append(",");
        csvWriter.append("Aff Team");
        csvWriter.append(",");
        csvWriter.append("Aff Speaks");
        csvWriter.append(",");
        csvWriter.append("Neg Team");
        csvWriter.append(",");
        csvWriter.append("Neg Speaks");
        csvWriter.append(",");
        csvWriter.append("Judge");
        csvWriter.append(",");
        csvWriter.append("Room");
        csvWriter.append("\n");


        for (Room room : RoomManager.roomArray) {
            int i = 1;
            for (RoundData round : room.data) {
                if (round.isFinished) {
                    String winner = round.affWon ? "Aff" : "Neg";

                    csvWriter.append(String.join(",", Integer.toString(i), winner, round.affTeam.code, Double.toString(round.affSpeaks), round.negTeam.code, Double.toString(round.negSpeaks), round.judge.code, Integer.toString(room.id)));
                    csvWriter.append("\n");
                }
                i++;
            }
        }

        int i = 1;
        for (RoundData round : RoomManager.byeRoom.data) {
            if (round.isFinished) {

                csvWriter.append(String.join(",", Integer.toString(i), "BYE", round.affTeam.code, Double.toString(round.affSpeaks), "", "", "", ""));

                csvWriter.append("\n");
            }
            i++;
        }

        csvWriter.flush();
        csvWriter.close();
        return file;
    }
}
