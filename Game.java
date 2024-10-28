package game;

import java.io.FileWriter;
import java.io.IOException;


public class Game {

    public enum GameType {
        SOCCER,
        HOCKEY,
        RUGBY
    }

    public static void main(String[] args) {
        writeNumOfPlayersPerTeam(GameType.HOCKEY);
        writeNumOfPlayersPerTeam(GameType.RUGBY);
        writeNumOfPlayersPerTeam(GameType.SOCCER);
    }
    public static void writeNumOfPlayersPerTeam(GameType game){
        String fileName = game.name().toLowerCase() + ".txt";
        int numOfPlayersPerTeam;

        switch (game) {
            case SOCCER:
                numOfPlayersPerTeam = 11;
                break;

            case HOCKEY:
                numOfPlayersPerTeam=6;
                break;

            case RUGBY:
                numOfPlayersPerTeam = 15;
                break;

            default:
                throw new IllegalArgumentException("Wrong game selection");
        }
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(String.valueOf(numOfPlayersPerTeam));
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }



    }



