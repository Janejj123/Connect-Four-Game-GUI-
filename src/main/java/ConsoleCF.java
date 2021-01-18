package hw4;

import java.util.Random;
import java.util.Scanner;

public class ConsoleCF extends CFGame {

    private CFPlayer player1;
    private CFPlayer player2;
    private CFPlayer redPlayer;
    private CFPlayer blackPlayer;

    public ConsoleCF(CFPlayer ai) {
        player1 = ai;
        player2 = new HumanPlayer();
    }

    public ConsoleCF(CFPlayer ai1, CFPlayer ai2) {
        player1 = ai1;
        player2 = ai2;
    }

    public void playOut() {
        int whoGoesFirst = new Random().nextInt(2);
        if (whoGoesFirst == 1) {
            redPlayer = player1;
            blackPlayer = player2;
        }
        else {
            redPlayer = player2;
            blackPlayer = player1;
        }


        while (!isGameOver()) {
            play(redPlayer.nextMove(this));
            if (isGameOver()) break;
            play(blackPlayer.nextMove(this));
        }

        printState();
    }

    public String getWinner() {
        int result = this.winner();
        if (result == 0) {
            return "draw";
        }
        else if (result == 1) {
            return redPlayer.getName();
        }
        else {
            return blackPlayer.getName();
        }
    }

    private class HumanPlayer implements CFPlayer {

        @Override
        public int nextMove(CFGame g) {

            System.out.println("Player name:" + getName() + "'s turn");
            g.printState();

            System.out.println("Please input the column you want to play");

            while(true) {
                Scanner input = new Scanner(System.in);
                int columnToPlay = input.nextInt();
                if(columnToPlay < 1 || columnToPlay > 7 || ! g.canPlay(columnToPlay)) {
                    System.out.println("Please input a valid column");
                }
                else {
                    return columnToPlay;
                }
            }
        }

        @Override
        public String getName() {
            return "Human Player";
        }
    }
}
