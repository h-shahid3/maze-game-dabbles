package mazeProject.model;

/**
 * A class for creation of a Hero, an object to be used in the maze. The only methods are getters and setters
 * The data includes the number of kills, position, power level, and living status.
 *
 * @author Hassan Shahid
 */
public class Hero {
    int kills;
    Position pos;
    int powerLevel;
    boolean alive;

    public Hero() {
        kills = 0;
        pos = new Position(1, 1);
        powerLevel = 0;
        alive = true;
    }

    public int getKills() {
        return kills;
    }

    public Position getPos() {
        return pos;
    }

    public int getPowerLevel() {
        return powerLevel;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public void upPowerLevel() {
        this.powerLevel++;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }
}
