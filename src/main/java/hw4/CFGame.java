package hw4;

public class CFGame {

    //state[i][j]= 0 means the i,j slot is empty
    //state[i][j]= 1 means the i,j slot has red
    //state[i][j]=-1 means the i,j slot has black

    private final int[][] state; //actualBoard
    private boolean isRedTurn;

    private int[] lastPlayedPos; //keep track of last played index
    private boolean boardFull;

    {
        state = new int[7][6];
        lastPlayedPos = new int[2];
        for (int i=0; i<7; i++)
            for (int j=0; j<6; j++)
                state[i][j] = 0;
        isRedTurn = true; //red goes first
    }

    public int[][] getState() {
        int[][] ret_arr = new int[7][6];
        for (int i=0; i<7; i++)
            for (int j=0; j<6; j++)
                ret_arr[i][j] = state[i][j];
        return ret_arr;
    }

    public void printState() {
        for (int i=0; i<6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(String.format("%5s",state[j][i]));
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isRedTurn() {
        return isRedTurn;
    }

    public void setRedTurn(boolean redTurn) {
        isRedTurn = redTurn;
    }

    public boolean play(int column) {
        int rowToPlay = 5;
        column--;
        if (column < 0 || column > 6 || state[column][0] != 0) //if index out of bound or all rows full of the column
            return false;
        else {
            for (int i = 0; i < 6; i++) { //must play right above an already played position
                if (i < 5 && state[column][i + 1] != 0) {
                    rowToPlay = i;
                    break;
                }
            }

            if (isRedTurn)
                state[column][rowToPlay] = 1;
            else
                state[column][rowToPlay] = -1;

            isRedTurn = !isRedTurn;
            lastPlayedPos[0] = column; //keep track of played index to check if anyone won in isGameOver()
            lastPlayedPos[1] = rowToPlay;
            return true;
        }
    }

    public boolean isGameOver() {
        boardFull = true;
        for (int i = 0; i < 7; i++) {  //check if board is full
            if (state[i][0] == 0) {
                boardFull = false;
                break;
            }
        }

        if(boardFull)
            return true;

        int count = 0;
        int winnerColor;

        if(isRedTurn)
            winnerColor = -1;
        else
            winnerColor = 1;

        int currentCol = lastPlayedPos[0]; //check vertical
        int currentRow = lastPlayedPos[1];

        while (currentRow < 6) {
            if (state[currentCol][currentRow++] == winnerColor) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
        }

        currentCol = lastPlayedPos[0]; //check right horizontal
        currentRow = lastPlayedPos[1];
        count = 0;

        while (currentCol < 7) {
            if (state[currentCol++][currentRow] == winnerColor) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
        }

        currentCol = lastPlayedPos[0]; //check left horizontal excluding played position
        currentRow = lastPlayedPos[1];
        count--;


        while (currentCol >= 0) {
            if (state[currentCol--][currentRow] == winnerColor) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
        }

        currentCol = lastPlayedPos[0]; //check forward diagonal
        currentRow = lastPlayedPos[1];
        count = 0;

        while (currentCol < 7 && currentRow >= 0) {
            if (state[currentCol++][currentRow--] == winnerColor) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
        }

        currentCol = lastPlayedPos[0];
        currentRow = lastPlayedPos[1];
        count--;

        while (currentCol >= 0 && currentRow < 6) {
            if (state[currentCol--][currentRow++] == winnerColor) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
        }

        currentCol = lastPlayedPos[0]; //check backward diagonal
        currentRow = lastPlayedPos[1];
        count = 0;

        while (currentCol < 7 && currentRow < 6) {
            if (state[currentCol++][currentRow++] == winnerColor) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
        }

        currentCol = lastPlayedPos[0];
        currentRow = lastPlayedPos[1];
        count--;

        while (currentCol >=0  && currentRow >= 0) {
            if (state[currentCol--][currentRow--] == winnerColor) {
                count++;
                if (count == 4) {
                    return true;
                }
            } else {
                break;
            }
        }
        return false;
    }

    public int winner() {
        if(boardFull)
            return 0;

        else if (isRedTurn) //if isRedTurn true means last played is black
            return -1;

        else
            return 1;
    }

    public boolean canPlay(int col) {
        return state[col-1][0] == 0; //column not full
    }

    public int[] getLastPlayedPos() {
        return lastPlayedPos;
    }

    public int[][] getOriginalState() {
        return state;
    }
}

