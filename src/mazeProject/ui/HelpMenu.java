package mazeProject.ui;

/**
 * A class containing the help menu for the Maze Game
 *
 * @author Hassan Shahid
 */
public class HelpMenu {
    /**
     * Prints the HelpMenu to the console, offering several options.
     */
    public static void printMenu() {
        String newLine = System.getProperty("line.separator");
        String output = "DIRECTIONS:" + newLine +
                "\tKill 3 Monsters!" + newLine +
                "LEGEND:" + newLine +
                "\t#: Wall" + newLine +
                "\t@: You (a hero)" + newLine +
                "\t!: Monster" + newLine +
                "\t$: Power" + newLine +
                "\t.: Unexplored space" + newLine +
                "MOVES:" + newLine +
                "\tUse W (up), A (left), S (down) and D (right) to move." + newLine +
                "\t(You must press enter after each move).";
        System.out.println(output);
    }
} //HelpMenu.java