package mazeProject.model;
/**
 * A class which creates a structure holding an X and Y coordinate
 * The only methods are getters and setters
 * @author Hassan Shahid
 */
public class Position {
    private int x; //j
    private int y; //i

    public Position(int inputX, int inputY) {
        this.x = inputX;
        this.y = inputY;
    }

    public Position() {
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
