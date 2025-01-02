import java.util.Scanner;
/*
    The whole logic was written in procedural style. The whole process logic is squized within a main method, which
    takes away all the advantages of OOP paradigm. I suggest to move the whole logic into separate class and
    run the instance of it in the main method.
 */

class TicTacToe {
    private byte i;
    private byte winner = 0;
    private final char[] box = {'1', '2', '3', '4', '5', '6', '7', '8', '9'}; //replace C-style array declaration
    private boolean boxEmpty = false;

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

    private String buildWinString() {
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
        if (buildWinString().contains("XXX")) {
            winner = 1;
        } else if (buildWinString().contains("OOO")) {
            winner = 2;
        }
    }


    /*
     In method randomAIMove() I preserve the original logic, as it looks simple, and it works,
     However I suggest to add comments to EXPLAIN the logic. And use constants instead of just chars.
     The same comes to the expression (9 - 1 + 1) + 1), it needs to be simplified and explained.
     */
    private void randomAIMove() {
        System.out.println("Making random move");
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
        System.out.println("Getting user input");
        byte input;
        Scanner scan = new Scanner(System.in);
        while (true) {
            input = scan.nextByte();
            if (input > 0 && input < 10) {
                if (box[input - 1] == 'X' || box[input - 1] == 'O')
                    System.out.println("That one is already in use. Enter another.");
                else {
                    box[input - 1] = 'X';
                    break;
                }
            } else
                System.out.println("Invalid input. Enter again.");
        }
    }


    /*
    In order to remove unnecessary amount of breaks in the while loop, I suggest using switch:case statement with
    only one break to the big while loop
    */
    private boolean thereIsAWinner() {
        System.out.println("Checking if theres a winner");



        if (winner != 0) {
            String message = "";
            switch (winner) {
                case 1:
                    message = "You won the game!";

                    break;

                case 2:
                    message = "You lost the game!";
                    break;

                case 3:
                    message = "It's a draw!";
                    break;

                default:
            }

            System.out.println(message + "\nCreated by Shreyas Saha. Thanks for playing!");

            return true;
        }
        return false;
    }


    private void printGrid() {
        System.out.println("Printing the Grid");
        System.out.println("\n\n " + box[0] + " | " + box[1] + " | " + box[2] + " ");
        System.out.println("-----------");
        System.out.println(" " + box[3] + " | " + box[4] + " | " + box[5] + " ");
        System.out.println("-----------");
        System.out.println(" " + box[6] + " | " + box[7] + " | " + box[8] + " \n");
    }

    private void setUpArray() {
        System.out.println("Setting up array");
        if (!boxEmpty) {
            for (i = 0; i < 9; i++)
                box[i] = ' ';
            boxEmpty = true;
        }
    }

    private void checkDraw() {
        System.out.println("Checking for a draw");
        boolean boxAvailable = false;
        for (i = 0; i < 9; i++) {
            if (box[i] != 'X' && box[i] != 'O') {
                boxAvailable = true;
                break;
            }
        }
        if (!boxAvailable) {
            winner = 3;  // winner is 3 means it's a draw
        }
    }

    public void play() {
        System.out.println("Enter box number to select. Enjoy!\n");
        while (true) {
            printGrid();
            setUpArray();
            getUserInput();
            if (thereIsAWinner()) {
                break;
            }
            checkWinner();
            checkDraw();
            randomAIMove();
            checkWinner();
        }
    }
}

public class App {
    public static void main(String[] args) {
        TicTacToe ttt = new TicTacToe();
        ttt.play();
    }
}