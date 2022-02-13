package mazeProject.ui;

import mazeProject.model.Cell;
import mazeProject.model.Hero;
import mazeProject.model.Maze;

/**
 * A class for the displaying of the maze to the user.
 */
public class RenderMaze {

    public static void renderMaze(Maze inputMaze, Hero Arthur) {
        System.out.println("Maze: ");
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 20; j++) {
                Cell currentCell = inputMaze.mazeArray[i][j];
                if (currentCell.getWallType() == 2 || currentCell.getWallType() == 1) {
                    if (currentCell.isRevealed()) {
                        System.out.print("#");
                    } else {
                        System.out.print(".");
                    }
                } else { //Open Cell
                    if (currentCell.isRevealed() && currentCell.getContainsMonster() == 0 && !currentCell.isContainsHero() && !currentCell.isContainsPower()) {
                        System.out.print(" ");
                        continue;
                    }
                    if (currentCell.isContainsHero()) {
                        if (Arthur.isAlive()) {
                            System.out.print("@");
                        } else {
                            System.out.print("X");
                        }
                        continue;
                    }
                    if (currentCell.getContainsMonster() >= 1) {
                        System.out.print("!");
                        continue;
                    }
                    if (currentCell.isContainsPower()) {
                        System.out.print("$");
                        continue;
                    }
                    if (!currentCell.isRevealed()) {
                        System.out.print(".");
                    }
                }
            }
            System.out.println();
        }
    }

}
