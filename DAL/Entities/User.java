package Entities;

import Interfaces.Difficulty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.StringJoiner;

public class User implements Serializable {

    public String  name;
    public int     gamesWon;
    public int     gamesLost;

    public HashMap<Difficulty, Integer> highScores;

    public User() {
        highScores = new HashMap<>();
        for (Difficulty dif : Difficulty.values()) {
            highScores.put(dif, 0);
        }
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Username: " + name);
        joiner.add("Games won: " + gamesWon);
        joiner.add("Games lost: " + gamesLost);
        joiner.add("High scores:");
        for (Difficulty dif : highScores.keySet()) {
            joiner.add(dif + ": " + highScores.get(dif));
        }
        return joiner.toString();
    }
}
