package game;

import java.util.Arrays;
import java.util.Scanner;
/*
    The whole logic was written in procedural style. The whole process logic is squeezed into a main method, which
    takes away all the advantages of OOP paradigm. I suggest to move the whole logic into separate class and
    run the instance of it in the main method.
 */

public class TicTacToe {

    private enum Winners {
        USER,
        AI,
        DRAW,
        NOT_SET
    }

    private Winners winner = Winners.NOT_SET;
    private final char[] box = {'1', '2', '3', '4', '5', '6', '7', '8', '9'}; //replace C-style array declaration
    private boolean arrayInitialized = false;
    static final String X_WINS_COMBO = "XXX";
    static final String O_WINS_COMBO = "OOO";

    private void initializeArray() {
    /*
    This method initializes array with empty spaces after it was reviled first time to user asa grid of 9 digits
    */
        if (!arrayInitialized) {
            Arrays.fill(box, ' ');
            arrayInitialized = true;
        }
    }


    private String buildWinString() {
    /*
    In order to simplify the code and avoid duplication, I suggest to replace the boolean checks, and move all the valid
    combinations for victory streaks into a separate string, and check if it contains the combination necessary to win.
                             1 | 2 | 3
                            -----------
                             4 | 5 | 6
                            -----------
                             7 | 8 | 9
    Also need to keep in mind that actual element numerations starts with 0
    */

        return "" + // This first empty string is necessary for the correct String concatenation

                //3 horizontal win streak combinations
                box[0] + box[1] + box[2] + " " +
                box[3] + box[4] + box[5] + " " +
                box[6] + box[7] + box[8] + " " +

                //3 vertical win streak combinations
                box[0] + box[3] + box[6] + " " +
                box[1] + box[4] + box[7] + " " +
                box[2] + box[5] + box[8] + " " +

                //2 diagonal win streak combinations
                box[0] + box[4] + box[8] + " " +
                box[2] + box[4] + box[6] + " ";
    }

    private void checkWinner() {

        if (buildWinString().contains(X_WINS_COMBO)) {
            winner = Winners.USER;
        } else if (buildWinString().contains(O_WINS_COMBO)) {
            winner = Winners.AI;
        }
    }


    private void randomAIMove() {
    /*
    In method randomAIMove() I preserve the original logic, as it looks simple, and it works,
    However I suggest to add comments to EXPLAIN the logic. And use constants instead of just chars.
    The same comes to the expression (9 - 1 + 1) + 1), it needs to be simplified and explained.
    */
        byte rand;
        while (true) {
            rand = (byte) (Math.random() * (9 - 1 + 1) + 1);
            if (box[rand - 1] != 'X' && box[rand - 1] != 'O') {
                box[rand - 1] = 'O';
                break;
            }
        }
    }

    private void getUserInput() {
    /*
    This method puts the X users input into one of the empty cells
    */
        byte input;
        Scanner scan = new Scanner(System.in);
        while (true) {
            input = scan.nextByte();
            if (input > 0 && input < 10) {
                if (box[input - 1] != ' ')
                    System.out.println("That one is already in use. Enter another.");
                else {
                    box[input - 1] = 'X';
                    break;
                }
            } else
                System.out.println("Invalid input. Enter again.");
        }
    }


    private boolean thereIsAWinner() {
        checkWinner();
        if (winner != Winners.NOT_SET) {
            String message = switch (winner) {
                case USER -> "You won the game!";
                case AI -> "You lost the game!";
                case DRAW -> "It's a draw!";
                default -> "";
            };
            printGrid();
            System.out.println(message + "\nCreated by Shreyas Saha. Thanks for playing!");
            return true;
        }
        return false;
    }

    private void printGrid() {
        if (!arrayInitialized) {
            System.out.println("Enter box number to select. Enjoy!\n");
        }
        System.out.println("\n\n " + box[0] + " | " + box[1] + " | " + box[2] + " ");
        System.out.println("-----------");
        System.out.println(" " + box[3] + " | " + box[4] + " | " + box[5] + " ");
        System.out.println("-----------");
        System.out.println(" " + box[6] + " | " + box[7] + " | " + box[8] + " \n");
    }

    private void checkDraw() {
        boolean boxAvailable = false;
        for (char c : box) {
            if (c != 'X' && c != 'O') {
                boxAvailable = true;
                break;
            }
        }
        if (!boxAvailable) {
            winner = Winners.DRAW;
        }
    }

    public void play() {
        while (!thereIsAWinner()) {
            printGrid();
            initializeArray();
            getUserInput();
            if (thereIsAWinner()) break;
            checkDraw();
            randomAIMove();
        }
    }
}