package com.squidkingdom.Gavel;

import java.util.Comparator;

class WinCompare implements Comparator<Team>
{
    public int compare(Team m1, Team m2)
    {
        if (m1.getTotalWins() < m2.getTotalWins()) return -1;
        if (m1.getTotalWins() > m2.getTotalWins()) return 1;
        else{
            if (m1.getSpeaks() < m2.getSpeaks()) return -1;
            if (m1.getSpeaks() > m2.getSpeaks()) return 1;
            else return 0;
        }
    }
}