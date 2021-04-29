package gmu.Project;

import gmu.Project.model.Game;

import java.util.ArrayList;
import java.util.Collection;

public class HomepageBean
{
    private ArrayList<Game> games = null;

    public HomepageBean(){}

    public ArrayList<Game> getGames() {
        return games;
    }
    public void setGames(Collection<Game> games) {
        this.games = (ArrayList<Game>) games;
    }
}
