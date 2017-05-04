package com.example.seanma.inclassassignment11_zhenzhenm;

/**
 * Created by SeanMa on 5/1/17.
 */

import java.io.Serializable;

/**
 * Created by miket on 4/26/2017.
 */

public class NBATeam implements Serializable {
    private String teamName;
    private String teamStadium;
    private String fileName;

    public NBATeam(){

    }

    public NBATeam(String teamName, String teamRanking, String fileName){
        this.teamName=teamName;
        this.teamStadium =teamRanking;
        this.fileName=fileName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamStadium() {
        return teamStadium;
    }

    public void setTeamStadium(String teamStadium) {
        this.teamStadium = teamStadium;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}