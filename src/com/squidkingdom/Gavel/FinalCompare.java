package com.squidkingdom.Gavel;

import java.util.Comparator;
class FinalCompare implements Comparator<Team>{
    public int compare(Team m1, Team m2)
    {
        if (m1.getTotalWins() < m2.getTotalWins()) return -1;
        if (m1.getTotalWins() > m2.getTotalWins()) return 1;
        else{
            if (m1.getSpeaks() < m2.getSpeaks()) return -1;
            if (m1.getSpeaks() > m2.getSpeaks()) return 1;
            else {
                double opp1 = m1.opp[0].totalSpeaks + m1.opp[1].totalSpeaks + m1.opp[2].totalSpeaks + m1.opp[3].totalSpeaks + m1.opp[4].totalSpeaks;
                double opp2 = m2.opp[0].totalSpeaks + m2.opp[1].totalSpeaks + m2.opp[2].totalSpeaks + m2.opp[3].totalSpeaks + m2.opp[4].totalSpeaks;
                if (opp1 < opp2) return -1;
                if (opp1 > opp2) return 1;
                else return 0;
            }
        }
    }
}
