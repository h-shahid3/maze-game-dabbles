package mazeProject.model;

/**
 * A class for creation of a Monster, an object to be used in the maze. The only methods are getters and setters
 * The data includes the ID of the monster and its Position.
 *
 * @author Hassan Shahid
 */
public class Monster {
    private Position pos = new Position();
    private final int ID;

    public Monster(Position inputPos, int ID) {
        this.pos = inputPos;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }
}
