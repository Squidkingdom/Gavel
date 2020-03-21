package com.squidkingdom.Gavel;

import java.util.Comparator;

class SpeakCompare implements Comparator<Team>
{
    public int compare(Team m1, Team m2)
    {
        if (m1.getSpeaks() < m2.getSpeaks()) return -1;
        if (m1.getSpeaks() > m2.getSpeaks()) return 1;
        else return 0;
    }
}
