package com.strang6.tinkofffintechschool;

/**
 * Created by Strang6 on 05.11.2017.
 */

public class State {
    private String name, capital, officialLanguage;
    private int area, population;

    public State(String name) {
        this.name = name;
    }

    public State(String name, String capital, String officialLanguage, int area, int population) {
        this.name = name;
        this.capital = capital;
        this.officialLanguage = officialLanguage;
        this.area = area;
        this.population = population;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public void setOfficialLanguage(String officialLanguage) {
        this.officialLanguage = officialLanguage;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public String getCapital() {
        return capital;
    }

    public String getOfficialLanguage() {
        return officialLanguage;
    }

    public int getArea() {
        return area;
    }

    public int getPopulation() {
        return population;
    }
}
