package com.squidkingdom.Gavel;

import java.io.FileWriter;
import java.io.IOException;

public class Exporter {

    public static void exportRound(Round round, String filename) throws IOException {
        FileWriter csvWriter = new FileWriter("new.csv");
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
        csvWriter.append("\n");
    }
}
