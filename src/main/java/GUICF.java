package hw4;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class GUICF extends CFGame {
    private JFrame frame;
    private JButton[] arrow; //we need 7 arrow for 7 columns
    private JButton play; //play button for the AI vs. AI game
    private JLabel result; //for printing result at the end
    private GameBoard this_board; //actual game board, extends JPanel
    private CFPlayer ai1;
    private CFPlayer ai2;

    public GUICF(CFPlayer ai) {
        this_board = new GameBoard();
        this_board.setVisible(true);
        frame = new JFrame("Connect Four");
        frame.getContentPane().add(this_board, BorderLayout.CENTER);
        arrow = new JButton[7];
        result = new JLabel();
        result.setVisible(false);
        frame.getContentPane().add(result, BorderLayout.SOUTH);
        this.ai1 = ai;

        JPanel arrowContainer = new JPanel();
        GridLayout arrowLayout = new GridLayout(1, 7);
        arrowContainer.setLayout(arrowLayout);
        frame.getContentPane().add(arrowContainer, BorderLayout.NORTH);

        for (int i = 0; i < 7; i++) {
            arrow[i] = new JButton("\u2193");
            arrowContainer.add(arrow[i]);
            arrow[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Arrays.stream(arrow).forEach(a -> a.setEnabled(false)); //human cannot play again before AI's turn
                    for (int i = 0; i < arrow.length; i++) {
                        if (e.getSource() == arrow[i]) {
                            play(i+1);
                            this_board.paint(getLastPlayedPos()[1], getLastPlayedPos()[0], 1);
                            break;
                        }
                    }

                    if(!isGameOver()) {
                        play(ai1.nextMove(GUICF.this));
                        this_board.paint(getLastPlayedPos()[1], getLastPlayedPos()[0], -1);
                    }
                    for (int i = 0; i < 7; i++) { //human can play the available columns after AI's turn
                        if (canPlay(i+1)) {
                            arrow[i].setEnabled(true);
                        }
                    }

                    if(isGameOver()) {
                        result.setVisible(true);
                        if (winner() == 1) {
                            result.setText("Human player won");
                        } else if (winner() == -1) {
                            result.setText(ai1.getName() + " won");
                        } else {
                            result.setText("is a draw!");
                        }
                        Arrays.stream(arrow).forEach(a -> a.setEnabled(false));
                    }
                }
            });
        }
        frame.setSize(400,400);
        frame.setVisible(true);
    }

    public GUICF(CFPlayer ai1, CFPlayer ai2) {
        this_board = new GameBoard();
        this_board.setVisible(true);
        frame = new JFrame("Connect Four");
        frame.getContentPane().add(this_board, BorderLayout.CENTER);
        arrow = new JButton[7];
        result = new JLabel();
        result.setVisible(false);
        frame.getContentPane().add(result, BorderLayout.SOUTH);
        this.ai1 = ai1;
        this.ai2 = ai2;

        play = new JButton("play");
        frame.getContentPane().add(play, BorderLayout.NORTH);
        play.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(isRedTurn()) {
                    play(ai1.nextMove(GUICF.this));
                    this_board.paint(getLastPlayedPos()[1], getLastPlayedPos()[0], 1);
                } else {
                    play(ai2.nextMove(GUICF.this));
                    this_board.paint(getLastPlayedPos()[1], getLastPlayedPos()[0], -1);
                }

                if(isGameOver()) {
                    result.setVisible(true);
                    if (winner() == 1) {
                        result.setText(ai1.getName() + " won");
                    } else if (winner() == -1) {
                        result.setText(ai2.getName() + " won");
                    } else {
                        result.setText("is a draw!");
                    }
                    play.setEnabled(false);
                }
        }});
        frame.setSize(400,400);
        frame.setVisible(true);
    }

    private boolean playGUI(int c) {return true;}

    private class GameBoard extends JPanel {
        private GridLayout layout;
        private JLabel[][] cell;

        private GameBoard() { // initialize empty board
            layout = new GridLayout(6, 7);
            cell = new JLabel[6][7];

            this.setLayout(layout);
            Border border = BorderFactory.createLineBorder(Color.BLUE, 1);
            for(int i = 0; i < 6; i++) {
                for (int j = 0; j < 7; j++) {
                    cell[i][j] = new JLabel();
                    cell[i][j].setBorder(border);
                    cell[i][j].setOpaque(true);
                    this.add(cell[i][j]);
                }
            }
        }

        private void paint(int x, int y, int color) {
            //paints specified coordinate red or black
            if (color == 1) {
                cell[x][y].setBackground(Color.red);
            } else {
                cell[x][y].setBackground(Color.black);
            }
        }
    }

}
