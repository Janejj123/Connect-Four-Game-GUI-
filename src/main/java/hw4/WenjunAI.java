package hw4;

import java.util.Random;

public class WenjunAI implements CFPlayer {

    @Override
    public int nextMove(CFGame g) {
        System.out.println("Player name:" + getName() + "'s turn");
        g.printState();

        int nextMove;
        int[] lastPos = g.getLastPlayedPos();
        for (int i = 1; i < 8; i++) { //Check if I have a winning move
            nextMove = -1;
            if (g.play(i)) {
                if (g.isGameOver()) {
                    nextMove = i;
                }
                // revert game status after "virtual" play
                g.getOriginalState()[lastPos[0]][lastPos[1]] = 0;
                g.setRedTurn(!g.isRedTurn());
            }
            if (nextMove > 0) {
                return nextMove;
            }
        }

        g.setRedTurn(!g.isRedTurn());
        for (int i = 1; i < 8; i++) { //Check if I need to block my opponent's winning move
            nextMove = -1;
            if (g.play(i)) {
                if (g.isGameOver()) {
                    nextMove = i;
                }
                // revert game status after "virtual" play
                g.getOriginalState()[lastPos[0]][lastPos[1]] = 0;
                g.setRedTurn(!g.isRedTurn());
            }
            if (nextMove > 0) {
                g.setRedTurn(!g.isRedTurn());
                return nextMove;
            }
        }
        g.setRedTurn(!g.isRedTurn());

        int randCol = -1; //Play Random
        boolean legal = false;
        while (!legal) {
            randCol = new Random().nextInt(7)+1;
            legal = g.canPlay(randCol);
        }
        return randCol;
    }

    @Override
    public String getName() {
        return "Wenjun's AI";
    }

}
