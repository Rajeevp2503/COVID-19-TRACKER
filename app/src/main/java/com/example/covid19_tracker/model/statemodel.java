package com.example.covid19_tracker.model;

public class statemodel {
    String active,confirmed,deaths,lastupdatedtime,recovered,state;

    public statemodel(String active, String confirmed, String deaths, String lastupdatedtime, String recovered, String state) {
        this.active = active;
        this.confirmed = confirmed;
        this.deaths = deaths;
        this.lastupdatedtime = lastupdatedtime;
        this.recovered = recovered;
        this.state = state;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getLastupdatedtime() {
        return lastupdatedtime;
    }

    public void setLastupdatedtime(String lastupdatedtime) {
        this.lastupdatedtime = lastupdatedtime;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
