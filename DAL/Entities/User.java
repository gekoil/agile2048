package Entities;

import java.io.Serializable;
import java.util.HashMap;
import java.util.StringJoiner;

public class User implements Serializable {
    public String  name;
    public int     gamesWon;
    public int     gamesLost;
    public HashMap<String, Integer> highScores;

    public User() {
        highScores = new HashMap<>();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Username: " + name);
        joiner.add("Games won: " + gamesWon);
        joiner.add("Games lost: " + gamesLost);
        //joiner.add("High score:" );
        return joiner.toString();
    }
}
