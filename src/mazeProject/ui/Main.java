package mazeProject.ui;

import mazeProject.model.*;

import java.util.Scanner;

/**
 * A class for the execution of the maze, using implemented methods in Maze.java and other model files to display output to the user.
 * It contains the main method of the program. The only other method checks whether the Hero has killed enough, and data includes the number of kills needed to end execution
 * This class is capable of reading user input and outputs to the user as well.
 * Monsters are also initialized here to work in conjunction with the functions within maze.
 *
 * @author Hassan Shahid
 */

public class Main {
    static int killsNeeded = 3;

    public static boolean enoughKills(Hero Arthur) { //previously named checkKills
        return Arthur.getKills() >= killsNeeded;
    }

    public static void main(String[] args) {
        Maze gameBoard = new Maze();
        int loopCount = gameBoard.getLOOP_COUNT();

        while (loopCount < 3 && gameBoard.containsSquare()) { //Min amount of loops is three.
            gameBoard = new Maze();
            loopCount = gameBoard.getLOOP_COUNT();
        }

        Hero Arthur = gameBoard.getARTHUR();
        Monster mOne = new Monster(new Position(18, 1), 1);
        Monster mTwo = new Monster(new Position(1, 13), 2);
        Monster mThree = new Monster(new Position(18, 13), 3);
        System.out.println("Welcome! :D");
        HelpMenu.printMenu();
        while (Arthur.isAlive() && !enoughKills(Arthur)) {
            RenderMaze.renderMaze(gameBoard, Arthur);
            Scanner input = new Scanner(System.in);
            System.out.println("Total number of monsters to be killed: " + (killsNeeded - Arthur.getKills()));
            System.out.println("Number of powers currently in possession: " + Arthur.getPowerLevel());
            System.out.println("Number of monsters alive: " + gameBoard.monstersLeft());
            System.out.print("Enter your move [WASD?]: ");
            int validMove = 1; //default unsuccessful entry value
            while (validMove == 1 || validMove == 5) {
                char inputChar = input.next().charAt(0);
                if (inputChar == 'm') {
                    gameBoard.revealMaze();
                    System.out.print("Enter your move [WASD?]: ");
                    inputChar = input.next().charAt(0);
                } else if (inputChar == 'c') {
                    killsNeeded = 1;
                    System.out.print("Enter your move [WASD?]: ");
                    inputChar = input.next().charAt(0);
                } else if (inputChar == '?') {
                    HelpMenu.printMenu();
                    System.out.print("Enter your move [WASD?]: ");
                    inputChar = input.next().charAt(0);
                }

                validMove = gameBoard.moveHero(inputChar);
                if (validMove == 1) {
                    System.out.println("Invalid move: you cannot move through walls!");
                    System.out.print("Enter your move [WASD?]: ");
                } else if (validMove == 5) {
                    System.out.println("Invalid character entered. Please try again.");
                    System.out.print("Enter your move [WASD?]: ");
                }
                if (validMove == -1) { //Hero is dead
                    Arthur.setAlive(false);
                }
            }
            gameBoard.moveMonster(mOne);
            gameBoard.moveMonster(mTwo);
            gameBoard.moveMonster(mThree);
            gameBoard.checkCollision(gameBoard.getHeroCell());
        }
        if (!Arthur.isAlive()) {
            gameBoard.revealMaze();
            RenderMaze.renderMaze(gameBoard, Arthur);
            System.out.println("Game Over! You were eaten by a monster. :(");
        }
        if (enoughKills(Arthur)) {
            gameBoard.revealMaze();
            RenderMaze.renderMaze(gameBoard, Arthur);
            System.out.println("Congratulations! You have won :D");
        }
    }
}
