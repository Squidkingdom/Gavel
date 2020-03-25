package com.squidkingdom.Gavel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Exporter {

    public static void exportSchedule(ArrayList<RoundData> rounds) throws IOException {
        FileWriter csvWriter = new FileWriter("schedule.csv");
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
    }
}
