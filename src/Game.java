import java.util.HashMap;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * The Game class is the main class for the game
 *
 * @author Gabriel Skoglund
 * 
 * @author Jefim Holmgren
 */
public class Game {
    private static GameState state;
    private static Scanner scanner = new Scanner(System.in);
    private static HashMap<String, Room> worldModel;
    private final String STARTING_ROOM = "Main Hall";

    /**
     * g
     * A simple arrow indicating that the program is waiting for player input.
     * Why not try replacing this symbol with something else that you prefer?
     */
    private static final String PROMPT = "> ";

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();

        // Main game loop, keep reading player input and update game state based on
        // this.
        // This loop should be changed to end once the player has won.
        while (true) {
            String command = getCommand();
            CommandParser.parse(command, state);
        }
    }

    /**
     * Starts the game by generating the rooms, printing
     * introduction text and getting the decision tree
     * for the starting room (Main Hall)
     * 
     */
    private void startGame() {
        generateRoomsFromFile("rooms.txt");
        System.out.println("\n\n");
        printTextFromFile("introduction.txt");
        System.out.println();
        CommandParser.printHelpMessage();
        System.out.println();
        worldModel.get(STARTING_ROOM).printDecisionNode(state.getCurrentDecisionNodeId());
    }

        private static String getCommand() {
        System.out.print(PROMPT);
        // Read a line of user input from the terminal
        String command = scanner.nextLine();
        // Remove the prompt from the start of the line
        command = command.replaceFirst(PROMPT, "");
        return command;
    }

    /**
     * Generate the room which are stored in a text file.
     * 
     * @param filename the name of the textfile
     * 
     */
    private void generateRoomsFromFile(String filename) {
        worldModel = new HashMap<>();

        try {
            boolean isFirstRoom = true;

            // För Svenska bokstäver (UTF-8)
            FileInputStream fileInputStream = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader file = new BufferedReader(inputStreamReader);

            // Try to read the first line of the file
            String line = file.readLine();

            // Keep reading while there are lines left
            while (line != null) {

                String[] splittedLine = line.split(";");

                if ("Room".equals(splittedLine[0])) {
                    String roomName = splittedLine[1];
                    String description = splittedLine[2];
                    DecisionTree decisionTree = loadDecisionTreesForRoom(roomName);
                    Room room = new Room(roomName, description, decisionTree);
                    worldModel.put(roomName, room);

                    if (isFirstRoom) {
                        state = new GameState(room, "DECISION_START");
                        isFirstRoom = false;
                    }

                } else if ("Exit".equals(splittedLine[0])) {
                    String fromRoom = splittedLine[1];
                    String direction = splittedLine[2];
                    String toRoom = splittedLine[3];
                    worldModel.get(fromRoom).addExit(direction, worldModel.get(toRoom));
                }

                line = file.readLine();
            }

            file.close();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Prints text from text file.
     * 
     * @param filename file name of the text file whoose text should be printed.
     * 
     * @throws If fails to open the provided file name string
     */
    private void printTextFromFile(String filename) {
        try {
            // För Svenska bokstäver (UTF-8)
            FileInputStream fileInputStream = new FileInputStream(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader file = new BufferedReader(inputStreamReader);

            // Try to read the first line of the file
            String line = file.readLine();

            // Keep reading while there are lines left
            while (line != null) {
                Typewriter.typeItalic(line, 30);
                line = file.readLine();
            }

            file.close();

        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Loads decision tree based on room. The decisionTrees.txt starts with
     * the room to which a decision node belongs to.
     * 
     * @param roomName The room name for which decision tree shall be loaded
     * @return Returns a DecisionTree object.
     */
    private DecisionTree loadDecisionTreesForRoom(String roomName) {
        DecisionTree decisionTree = new DecisionTree(roomName);
        return decisionTree;
    }

}