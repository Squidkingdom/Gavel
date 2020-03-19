//package com.squidkingdom.Gavel;
//
//import java.io.FileWriter;
//import java.io.IOException;
//
//public class Exporter {
//
//    public static void exportRound(Round round, String filename) throws IOException {
//        FileWriter csvWriter = new FileWriter("new.csv");
//        csvWriter.append("Winner");
//        csvWriter.append(",");
//        csvWriter.append("Aff Team");
//        csvWriter.append(",");
//        csvWriter.append("Aff Speaks");
//        csvWriter.append(",");
//        csvWriter.append("Neg Team");
//        csvWriter.append(",");
//        csvWriter.append("Neg Speaks");
//        csvWriter.append(",");
//        csvWriter.append("Judge");
//        csvWriter.append("\n");
//
//        String winner = round.didWin ? "Aff" : "Neg";
//
//        // Need both teams in order to export this data
//        csvWriter.append(String.join(",", winner, round.oppTeam.person1 + " + " +  round.affTeam.person2, Integer.toString(round.affSpeaks), round.negTeam.person1 + " + " +  round.negTeam.person2, Integer.toString(round.negSpeaks), round.judge.name));
//        csvWriter.append("\n");
//
//        csvWriter.flush();
//        csvWriter.close();
//    }
//}
