package com.example.footballtalking;

import java.time.LocalDate;
import java.util.Date;

public class Match {
    private String matchDate;
    private Team firstTeam;
    private Team secondTeam;


    public Match(String matchDate, Team firstTeam, Team secondTeam) {
        this.matchDate = matchDate;
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public Team getFirstTeam() {
        return firstTeam;
    }

    public void setFirstTeam(Team firstTeam) {
        this.firstTeam = firstTeam;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public void setSecondTeam(Team secondTeam) {
        this.secondTeam = secondTeam;
    }
}
