package hw4;

import java.util.Random;

public class RandomAI implements CFPlayer {

    @Override
    public int nextMove(CFGame g) {
        System.out.println("Player name:" + getName() + "'s turn");
        g.printState();

        int randCol = -1;
        boolean legal = false;
        while (!legal) {
            randCol = new Random().nextInt(7) + 1;
            legal = g.canPlay(randCol);
        }
        return randCol;
    }

    @Override
    public String getName() {
        return "Random Player";
    }
}
