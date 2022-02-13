package mazeProject.model;

import java.util.*;

/**
 * A class responsible for Maze internal operations. Contains methods allowing for creating the maze, and also moving the objects within it
 * The data in this class includes the Maze itself(a 2d array of Cells), stacks necessary for creating the maze properly,
 * a loop counter to ensure that sufficient loops are made, and the Hero of the maze.
 * This class also contains all relevant setters and getters to be used later on.
 *
 * @author Hassan Shahid
 */
public class Maze {
    //Maze fields
    private final int LOOP_COUNT;
    public Cell[][] mazeArray = new Cell[15][20];
    private final Stack<Position> tracker = new Stack<>();
    private Stack<Position> monsterTracker = new Stack<>();
    private final Stack<Position> MONSTER_ONE_TRACKER = new Stack<>();
    private final Stack<Position> MONSTER_TWO_TRACKER = new Stack<>();
    private final Stack<Position> MONSTER_THREE_TRACKER = new Stack<>();
    private final Hero ARTHUR = new Hero();

    //Maze Constructor
    public Maze() { //USES RECURSIVE BACKTRACK
        instantiateArray();
        setStructure();
        Position startingPoint = new Position(1, 1);
        tracker.push(startingPoint);
        mazeArray[1][1].setFilled(true);
        generateMaze();
        implementPath();
        LOOP_COUNT = makeLoop();
        spawnPower();
        ARTHUR.setPos(new Position(1, 1));
    }

    //Maze getters
    public Hero getARTHUR() {
        return ARTHUR;
    }

    public Cell getHeroCell() {
        return mazeArray[ARTHUR.getPos().getY()][ARTHUR.getPos().getX()];
    }

    public int getLOOP_COUNT() {
        return LOOP_COUNT;
    }

    //Methods to create maze according to requirements
    private void instantiateArray() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 20; j++) {
                mazeArray[i][j] = new Cell();
                mazeArray[i][j].setCellPos(j, i);
            }
        }
    }

    private void setStructure() {
        //First, set permanent walls
        for (int j = 0; j < 20; j++) { // Upper boundary established
            mazeArray[0][j].setWallType(2);
        }
        for (int i = 0; i < 15; i++) { // Side boundaries established
            mazeArray[i][0].setWallType(2);
            mazeArray[i][19].setWallType(2);
            if (i % 2 != 0) {
                mazeArray[i][18].setFilled(true);//eliminates natural blocks created by algorithm by changing alg path
            }
        }
        for (int j = 0; j < 20; j++) { //Lower boundary established
            mazeArray[14][j].setWallType(2);
        }

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 20; j++) {
                if (mazeArray[i][j].getWallType() == 2) {
                    mazeArray[i][j].setRevealed(true);
                }
            }
        }
        //Set four corners squares to open cells and place characters in their positions
        mazeArray[1][1].setWallType(0);
        mazeArray[1][1].setContainsHero(true);
        mazeArray[1][18].setWallType(0);
        mazeArray[1][18].setContainsMonster(1);
        mazeArray[13][1].setWallType(0);
        mazeArray[13][1].setContainsMonster(1);
        mazeArray[13][18].setWallType(0);
        mazeArray[13][18].setContainsMonster(1);
    }

    private void generateMaze() {
        if (!tracker.isEmpty()) {
            Position pos = chooseRandomOpen(tracker.peek());
            if (pos.getX() != -1 && pos.getY() != -1) {
                tracker.push(pos);
            } else {
                tracker.pop();
            }
            generateMaze();
        }
    }

    private Position chooseRandomOpen(Position inputPos) { //Assumes input is a possible position
        int inputX = inputPos.getX();
        int inputY = inputPos.getY();
        Position outputPos = new Position(-1, -1); // Default Value is to reject in case no possible movement found

        List<Integer> randList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {  //Add numbers 1-4 in list
            randList.add(i);
        }
        Collections.shuffle(randList); //List created and shuffled to traverse cells randomly

        for (int i = 0; i < 4; i++) {
            int index = randList.get(i); // selects direction to move
            if (index == 1) {
                int newX = inputX + 2; //right
                if (withinBounds(newX, inputY) && !mazeArray[inputY][newX].isFilled()) {
                    mazeArray[inputY][newX].setFilled(true);
                    mazeArray[inputY][newX - 1].setFilled(true);
                    outputPos = new Position(newX, inputY);
                    break;
                }
            }
            if (index == 2) {
                int newX = inputX - 2; //left
                if (withinBounds(newX, inputY) && !mazeArray[inputY][newX].isFilled()) {
                    mazeArray[inputY][newX].setFilled(true);
                    mazeArray[inputY][newX + 1].setFilled(true);
                    outputPos = new Position(newX, inputY);
                    break;
                }
            }
            if (index == 3) {
                int newY = inputY + 2; //down
                if (withinBounds(inputX, newY) && !mazeArray[newY][inputX].isFilled()) {
                    mazeArray[newY][inputX].setFilled(true);
                    mazeArray[newY - 1][inputX].setFilled(true);
                    outputPos = new Position(inputX, newY);
                    break;
                }
            }
            if (index == 4) {
                int newY = inputY - 2; //up
                if (withinBounds(inputX, newY) && !mazeArray[newY][inputX].isFilled()) {
                    mazeArray[newY][inputX].setFilled(true);
                    mazeArray[newY + 1][inputX].setFilled(true);
                    outputPos = new Position(inputX, newY);
                    break;
                }
            }
        }
        return outputPos;
    }

    private void implementPath() {
        for (int i = 1; i < 14; i++) {
            for (int j = 1; j < 19; j++) {
                if (mazeArray[i][j].isFilled()) {
                    mazeArray[i][j].setWallType(0);
                }
            }
        }
    }

    private int makeLoop() {
        int loopCount = 0;
        int obstructions = 0;
        for (int i = 2; i < 14; i = i + 4) {
            for (int j = 2; j < 19; j = j + 10) {
                if (loopCount >= 3) {
                    return loopCount;
                }
                if (mazeArray[i][j].getWallType() == 1) {
                    if (mazeArray[i][j + 1].getWallType() == 1) {
                        obstructions++;
                    }
                    if (mazeArray[i][j - 1].getWallType() == 1) {
                        obstructions++;
                    }
                    if (mazeArray[i + 1][j - 1].getWallType() == 1) {
                        obstructions++;
                    }
                    if (mazeArray[i - 1][j + 1].getWallType() == 1) {
                        obstructions++;
                    }
                    if (mazeArray[i - 1][j - 1].getWallType() == 1) {
                        obstructions++;
                    }
                    if (mazeArray[i + 1][j].getWallType() == 1) {
                        obstructions++;
                    }
                    if (mazeArray[i - 1][j].getWallType() == 1) {
                        obstructions++;
                    }
                    if (mazeArray[i + 1][j + 1].getWallType() == 1) {
                        obstructions++;
                    }
                    if (obstructions >= 3) {
                        mazeArray[i][j + 1].setWallType(0);
                        mazeArray[i][j - 1].setWallType(0);
                        mazeArray[i + 1][j].setWallType(0);
                        mazeArray[i - 1][j].setWallType(0);
                        mazeArray[i - 1][j + 1].setWallType(0);
                        mazeArray[i - 1][j - 1].setWallType(0);
                        mazeArray[i + 1][j - 1].setWallType(0);
                        mazeArray[i + 1][j + 1].setWallType(0);
                        loopCount++;
                        break;
                    }
                }
            }
        }
        return loopCount;
    }

    private void spawnPower() {
        Random rand = new Random();
        int min = 1;
        int max = 18;
        int randomX = rand.nextInt((max - min) + 1) + min;
        max = 13;
        int randomY = rand.nextInt((max - min) + 1) + min;
        while (mazeArray[randomY][randomX].getWallType() != 0 && !(randomX == 1 && randomY == 1)) {
            randomX = rand.nextInt((max - min) + 1) + min;
            max = 13;
            randomY = rand.nextInt((max - min) + 1) + min;
        }
        mazeArray[randomY][randomX].setContainsPower(true);
    }

    //Utilities for Main
    public boolean containsSquare() {
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 19; j++) {//Wall block checks if block of walls made, and whitespace does the same for open cells.
                boolean wallBlock = (mazeArray[i][j + 1].getWallType() == 1 || mazeArray[i][j + 1].getWallType() == 2) &&
                        (mazeArray[i][j].getWallType() == 1 || mazeArray[i][j].getWallType() == 2) &&
                        (mazeArray[i + 1][j].getWallType() == 1 || mazeArray[i + 1][j].getWallType() == 2) &&
                        (mazeArray[i + 1][j + 1].getWallType() == 1 || mazeArray[i + 1][j + 1].getWallType() == 2);

                boolean whitespaceBlock = mazeArray[i][j + 1].getWallType() == 0 && mazeArray[i][j].getWallType() == 0 &&
                        mazeArray[i + 1][j].getWallType() == 0 && mazeArray[i + 1][j + 1].getWallType() == 0;
                if (wallBlock || whitespaceBlock) {
                    return true;
                }
            }
        }
        return false;
    }

    public void revealMaze() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 20; j++) {
                mazeArray[i][j].setRevealed(true);
            }
        }
    }

    public int monstersLeft() {
        int monsterCount = 0;
        for (int i = 1; i < 14; i++) {
            for (int j = 1; j < 19; j++) {
                monsterCount = monsterCount + mazeArray[i][j].getContainsMonster();
            }
        }
        return monsterCount;
    }

    //Movement methods and their helpers
    public int moveHero(char input) { // 0 if successful move, 1 if not, -1 if killed
        Position heroPos = ARTHUR.getPos();
        int x = heroPos.getX();
        int y = heroPos.getY();
        int nextWallType;
        Cell currentCell = mazeArray[y][x];
        if (input == 'w' || input == 'W') {  //Move up
            Cell newCell = mazeArray[y - 1][x];
            nextWallType = newCell.getWallType();
            if (nextWallType == 0) { //Open
                currentCell.setContainsHero(false);
                return heroToOpenCell(newCell, x, y - 1);
            }
            return 1; // Can't move into walls
        } else if (input == 's' || input == 'S') { //Move Down
            Cell newCell = mazeArray[y + 1][x];
            nextWallType = newCell.getWallType();
            if (nextWallType == 0) {
                currentCell.setContainsHero(false);
                return heroToOpenCell(newCell, x, y + 1);
            }
            return 1;
        } else if (input == 'a' || input == 'A') { //Move left
            Cell newCell = mazeArray[y][x - 1];
            nextWallType = newCell.getWallType();
            if (nextWallType == 0) {
                currentCell.setContainsHero(false);
                return heroToOpenCell(newCell, x - 1, y);
            }
            return 1;
        } else if (input == 'd' || input == 'D') { // Move right
            Cell newCell = mazeArray[y][x + 1];
            nextWallType = newCell.getWallType();
            if (nextWallType == 0) {
                currentCell.setContainsHero(false);
                return heroToOpenCell(newCell, x + 1, y);
            }
            return 1;
        } else {
            return 5; // Invalid character inputted
        }
    }

    private void revealNeighbours(Position inputPos) {
        int x = inputPos.getX();
        int y = inputPos.getY();
        mazeArray[y][x].setRevealed(true);
        mazeArray[y + 1][x].setRevealed(true);
        mazeArray[y - 1][x].setRevealed(true);
        mazeArray[y][x + 1].setRevealed(true);
        mazeArray[y][x - 1].setRevealed(true);
        mazeArray[y + 1][x + 1].setRevealed(true);
        mazeArray[y + 1][x - 1].setRevealed(true);
        mazeArray[y - 1][x + 1].setRevealed(true);
        mazeArray[y - 1][x - 1].setRevealed(true);
    }

    public void checkCollision(Cell currentCell) {
        int result;
        if (currentCell.getContainsMonster() > 0 && currentCell.isContainsHero()) {
            result = ARTHUR.getPowerLevel() - currentCell.getContainsMonster();
            ARTHUR.setPowerLevel(result);
            if (result >= 0) {
                currentCell.setContainsMonster(0);
            } else {
                ARTHUR.setAlive(false);
            } // death check in main will end game
        }
    }

    private int heroToOpenCell(Cell newCell, int x, int y) { //returns 0 or -1
        int result = 0;
        newCell.setContainsHero(true);
        ARTHUR.setPos(newCell.getPOS());
        revealNeighbours(newCell.getPOS());
        checkCollision(newCell);// does this within because hero cannot pick up power before
        if (newCell.isContainsPower()) {
            ARTHUR.upPowerLevel();
            newCell.setContainsPower(false);
            spawnPower();
        }
        ;
        return 0;
    }

    private boolean moveInDirect(Monster monster, Position current, int xDiff, int yDiff) {
        int currentX = current.getX();
        int currentY = current.getY();
        int newX = currentX + xDiff;
        int newY = currentY + yDiff;
        if (withinBounds(newX, newY) && mazeArray[newY][newX].getWallType() == 0) {
            mazeArray[currentY][currentX].downMonsterCount();
            mazeArray[newY][newX].upMonsterCount();
            monsterTracker.push(mazeArray[currentY][currentX].getPOS());
            monster.setPos(new Position(newX, newY));
            return true;
        }
        return false;
    }

    private boolean withinBounds(int x, int y) {
        return x >= 1 && x <= 18 && y >= 1 && y <= 13;
    }

    public void moveMonster(Monster monster) {
        if (monster.getID() == 1) {
            monsterTracker = MONSTER_ONE_TRACKER;
        } else if (monster.getID() == 2) {
            monsterTracker = MONSTER_TWO_TRACKER;
        } else if (monster.getID() == 3) {
            monsterTracker = MONSTER_THREE_TRACKER;
        }
        Position current = monster.getPos();
        if (mazeArray[monster.getPos().getY()][monster.getPos().getX()].getContainsMonster() != 0) {
            if (!monsterTracker.isEmpty()) {
                //avoid a direction
                Position last = monsterTracker.pop();
                int xDiff = current.getX() - last.getX();
                int yDiff = current.getY() - last.getY();
                List<Integer> randList = new ArrayList<>();
                for (int i = 1; i < 4; i++) {  //Add numbers 1-3 in list
                    randList.add(i);
                }
                Collections.shuffle(randList); //List created and shuffled to traverse cells randomly
                if (xDiff == 1) {//avoid left
                    for (int i = 0; i < 3; i++) {
                        int index = randList.get(i); // selects direction to move
                        if (index == 1) { //right
                            if (moveInDirect(monster, current, 1, 0)) {
                                return;
                            }
                        }
                        if (index == 2) { //down
                            if (moveInDirect(monster, current, 0, 1)) {
                                return;
                            }
                        }
                        if (index == 3) { //up
                            if (moveInDirect(monster, current, 0, -1)) {
                                return;
                            }
                        }
                    }
                    moveInDirect(monster, current, -1, 0); //If others impossible
                    return;
                }
                if (xDiff == -1) { //avoid right
                    for (int i = 0; i < 3; i++) {
                        int index = randList.get(i); // selects direction to move
                        if (index == 1) { //left
                            if (moveInDirect(monster, current, -1, 0)) {
                                return;
                            }
                        }
                        if (index == 2) { //down
                            if (moveInDirect(monster, current, 0, 1)) {
                                return;
                            }
                        }
                        if (index == 3) { //up
                            if (moveInDirect(monster, current, 0, -1)) {
                                return;
                            }
                        }
                    }
                    moveInDirect(monster, current, 1, 0);//If others impossible
                    return;
                }
                if (yDiff == -1) { //avoid down
                    for (int i = 0; i < 3; i++) {
                        int index = randList.get(i); // selects direction to move
                        if (index == 1) { //left
                            if (moveInDirect(monster, current, -1, 0)) {
                                return;
                            }
                        }
                        if (index == 2) { //right
                            if (moveInDirect(monster, current, 1, 0)) {
                                return;
                            }
                        }
                        if (index == 3) { //up
                            if (moveInDirect(monster, current, 0, -1)) {
                                return;
                            }
                        }
                    }
                    moveInDirect(monster, current, 0, 1);//If others impossible
                    return;
                }
                if (yDiff == 1) { //avoid up
                    for (int i = 0; i < 3; i++) {
                        int index = randList.get(i); // selects direction to move
                        if (index == 1) { //left
                            if (moveInDirect(monster, current, -1, 0)) {
                                return;
                            }
                        }
                        if (index == 2) { //right
                            if (moveInDirect(monster, current, 1, 0)) {
                                return;
                            }
                        }
                        if (index == 3) { //down
                            if (moveInDirect(monster, current, 0, 1)) {
                                return;
                            }
                        }
                    }
                    moveInDirect(monster, current, 0, -1);//If others impossible
                }
            } else {
                List<Integer> randList = new ArrayList<>();
                for (int i = 1; i < 5; i++) {  //Add numbers 1-3 in list
                    randList.add(i);
                }
                Collections.shuffle(randList); //List created and shuffled to traverse cells randomly
                for (int i = 0; i < 4; i++) {
                    int index = randList.get(i); // selects direction to move
                    if (index == 1) { //right
                        if (moveInDirect(monster, current, 1, 0)) {
                            return;
                        }
                    }
                    if (index == 2) { //down
                        if (moveInDirect(monster, current, 0, 1)) {
                            return;
                        }
                    }
                    if (index == 3) { //up
                        if (moveInDirect(monster, current, 0, -1)) {
                            return;
                        }
                    }
                    if (index == 3) { //left
                        if (moveInDirect(monster, current, -1, 0)) {
                            return;
                        }
                    }
                }
            }
        }
    }
}
