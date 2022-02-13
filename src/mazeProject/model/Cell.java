package mazeProject.model;

/**
 * A class for creation of a Cell, an object to be used in the maze. The only methods are getters and setters
 * The data in this class includes checking of the presence of Hero,Monster or Power in a cell. It also contains variables that are needed in maze construction, such as revealed and filled.
 * Finally, it also contains the position of hte cell itself within the maze.
 *
 * @author Hassan Shahid
 */
public class Cell {
    private boolean containsHero;
    private int containsMonster;
    private boolean containsPower;
    private boolean filled;
    private int wallType; // 0 if not a wall, 1 if a wall, 2 if permanent border wall.
    private boolean revealed;
    private final Position POS = new Position();

    public Cell() {
        containsPower = false;
        filled = false;
        revealed = false;
        wallType = 1;
        containsHero = false;
        containsMonster = 0;
    }

    public Position getPOS() {
        return POS;
    }

    public int getWallType() {
        return wallType;
    }

    public boolean isContainsHero() {
        return containsHero;
    }

    public int getContainsMonster() {
        return containsMonster;
    }

    public boolean isContainsPower() {
        return containsPower;
    }

    public boolean isFilled() {
        return filled;
    }

    public boolean isRevealed() {
        return revealed;
    }

    public void setCellPos(int x, int y) {
        this.POS.setX(x);
        this.POS.setY(y);
    }

    public void setRevealed(boolean input) {
        this.revealed = input;
    }

    public void setContainsPower(boolean containsPower) {
        this.containsPower = containsPower;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public void setWallType(int wallType) {
        this.wallType = wallType;
    }

    public void setContainsHero(boolean containsHero) {
        this.containsHero = containsHero;
    }

    public void upMonsterCount() {
        containsMonster++;
    }

    public void downMonsterCount() {
        containsMonster--;
    }

    public void setContainsMonster(int containsMonster) {
        this.containsMonster = containsMonster;
    }
}

