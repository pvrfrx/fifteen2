package com.company;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    // constants
    final static private int DIM_MIN = 3;
    final static private int DIM_MAX = 9;

    // board
    static int[][] board;

    // dimensions
    static int d;
    public Main(int de){
        d = de;
        board = new int[d][d];
        init();
    }
    public static void main(String[] args) {
       // ensure proper usage
        if (args.length != 1) {
            System.out.println("Usage: fifteen d");
            return;//return 1;
        }

        // ensure valid dimensions
        d = Integer.parseInt(args[0]);
        if (d < DIM_MIN || d > DIM_MAX) {
            System.out.println("Board must be between " + DIM_MIN + "x" + DIM_MIN + " and " + DIM_MAX + "x" + DIM_MAX + " inclusive.");
            return;//return 2;
        }
        else board = new int[d][d];

        // open log

        FileWriter file = null;
        try {
            file = new FileWriter("C:\\Users\\pavel.rylov\\IdeaProjects\\fifteen\\log.txt", false);
        } catch (IOException e) {
            e.printStackTrace();
            return;//return 3;
        }

        // greet user with instructions
        greet();

        // initialize the board
        init();

        // accept moves until game is won
        while (true) {
            // clear the screen
            clear();

            // draw the current state of the board
            draw(board);

            // log the current state of the board (for testing)
            try {
                for (int i = 0; i < d; i++) {
                    for (int j = 0; j < d; j++) {
                        file.write(board[i][j]);
                        if (j < d - 1) {
                            file.write("|");
                        }
                    }
                    file.write("\n");
                }
                file.flush();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            // check for win
            if (won()) {
                System.out.println("ftw!");
                break;
            }

            // prompt for move
            System.out.print("Tile to move: ");
            int tile = GetInt();

            // quit if user inputs 0 (for testing)
            if (tile == 0) {
                break;
            }

            // log move (for testing)
            try {
                file.write(tile);
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // move if possible, else report illegality
            int[][] tempBoard = move(tile, board);
            if (tempBoard == null) {
                System.out.println();
                System.out.println("Illegal move.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else board = tempBoard;

            // sleep thread for animation's sake
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // close log
        try {
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // success
       // return;//return 0;
    }

/**
 * Initializes the game's board with tiles numbered 1 through d*d - 1
 * (i.e., fills 2D array with values but does not actually print them).
 */
    static private void init() {
        int k = 1;
        if (d==2){
            board[0][0] = 2;
            board[1][0] = 1;
            board[0][1] = 3;
            board[1][1] = 0;
        }
     /*   else if (d==3){
            board[0][0] = 0;
            board[1][0] = 1;
            board[2][0] = 7;
            board[0][1] = 2;
            board[1][1] = 4;
            board[2][1] = 6;
            board[0][2] = 3;
            board[1][2] = 8;
            board[2][2] = 5;
        }*/
        else {
            for(int y = 0; y < d; y++) //row - y
            {
                for(int x = 0; x < d; x++ ) // column - x
                {
                    board[y][x]=d*d-k;
                    if ((d*d)%2 == 0 && y == (d-1))
                    {
                        if (x == (d-2)) board[y][x] = 2;
                        if (x == (d-3)) board[y][x] = 1;
                    }
                    k++;
                }
            }
        }
    }
/**
 * Clears screen using ANSI escape sequences.
 */
    static private void clear() {
        for (int i = 0; i < 100; i++) {
            System.out.println();
        }
       /* System.out.println("\033[2J");
        System.out.println("\033[0;0H");*/
    }

/**
 * Greets player.
 */
    static private void greet() {
        clear();
        System.out.println("WELCOME TO GAME OF FIFTEEN");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

/**
 * Prints the board in its current state.
 */
    static void draw(int[][] board){
        for(int y = 0; y < d; y++) //row - y
        {
            for(int x = 0; x < d; x++ ) // column - x
            {
                if (board[y][x] == 0) System.out.print("_ ");
                else System.out.print(board[y][x] + " ");
            }
            System.out.println();
        }
    }

    static int[] findXYElement(int i, int[][] board) {
        int[] result = new int[]{-1,-1};
        for(int y = 0; y < d; y++) //row - y
        {
            if (result[0] != -1) break;
            for(int x = 0; x < d; x++ ) // column - x
            {
                if (board[y][x] == i)
                {
                    result[0] = y;
                    result[1] = x;
                    break;
                }
            }
        }
        return result;
    }
/**
 * If tile borders empty space, moves tile and returns true, else
 * returns false.
 */
    static int[][] move(int tile, int[][] board){
        int[][] result = new int[d][d];//*board.clone();*/
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                result[i][j] = board[i][j];
            }
        }
        int yElementForMove = findXYElement(tile, board)[0];
        int xElementForMove = findXYElement(tile, board)[1];
        int yZeroElement = findXYElement(0, board)[0];
        int xZeroElement = findXYElement(0, board)[1];

        if (yZeroElement == -1) return null;
        else
        {
            if (((yZeroElement == (yElementForMove - 1) || yZeroElement == (yElementForMove + 1)) && xZeroElement == xElementForMove)
                    || ((xZeroElement == (xElementForMove - 1) || xZeroElement == (xElementForMove + 1)) && yZeroElement == yElementForMove))
            {
                int tmp = result[yZeroElement][xZeroElement];
                result[yZeroElement][xZeroElement] = result[yElementForMove][xElementForMove];
                result[yElementForMove][xElementForMove] = tmp;
                return result;
            }
            else return null;
        }
    }

/**
 * Returns true if game is won (i.e., board is in winning configuration),
 * else false.
 */
    static private boolean won(){
        int counter = 0;
        for(int y = 0; y < d; y++) //row - y
        {
            for(int x = 0; x < d; x++ ) // column - x
            {
                counter++;
                System.out.println("counter = " + counter);
                System.out.println("x = " + x + ", y = " + y + ", board[y][x] = " + board[y][x]);
                if (y==(d-1) && x==(d-1)) break;
                if (board[y][x]!=counter) return false;
            }
        }
        return true;
    }

    static private int GetInt(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Retry");
            return GetInt();
        }
    }
    /*возвращает массив возможных движений
        */

}
