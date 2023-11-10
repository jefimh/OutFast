import java.util.ArrayList;
import java.util.HashMap;

/**
 * The CommandParser class attempts to parse ("understand") a command from the
 * user.
 * A command is defined to be of the form [command word] [modifier], where the
 * modifier
 * is optional for certain commands.
 * As an example, the command "go north" will be parsed with the command word
 * "go" and the
 * modifier "north". This structure is of course not set in stone, so feel free
 * to modifiy
 * it further. You could perhaps add a second modifier so that the user could
 * enter
 * "go north quickly".
 * 
 * @author Gabriel Skoglund
 * 
 * @author Jefim Holmgren
 */
public class CommandParser {

    /**
     * Attempt to parse a command string from the player. If the command is valid,
     * it will be executed. For instance, if the command is "go north", and the
     * current room has an exit to the north, the effect will be to move the player
     * to the room to the north.
     *
     * @param command the string entered by the player
     * @param state   the current state that the game is in. This includes for
     *                instance
     *                the current room that the player is in.
     */
    public static void parse(String command, GameState state) {
        // Perform some initial handling of the command, removing any whitespace before
        // and after, turning it to lower case and splitting the command
        // on one or more whitespace characters
        String[] splitCommand = command.trim().toLowerCase().split("\\s+");

        // Check so that the player has entered something
        if (splitCommand.length < 1) {
            printInvalidCommandMessage();
            return;
        }

        // Attempt to match the player input to a command and execute it
        switch (splitCommand[0]) {
            case "go" -> executeGoCommand(splitCommand, state);
            case "look" -> executeLookCommand(state);
            case "do" -> executeDecisionCommand(splitCommand, state);
            case "room" -> executeDecisionCommand(splitCommand, state);
            case "help" -> printHelpMessage();
            default -> printInvalidCommandMessage();
        }
    }

    private static void executeGoCommand(String[] splitCommand, GameState state) {
        // Check so that we have a direction word
        if (splitCommand.length < 2) {
            printMissingModifierMessage("go");
        } else {
            // Attempt to leave the current room in the given direction
            Room currentRoom = state.getCurrentRoom();
            Room newRoom = currentRoom.go(splitCommand[1]);
            if (newRoom != null) {
                state.setCurrentRoom(newRoom);
                newRoom.lookAround();
                state.setCurrentDecisionNodeId("DECISION_START");
                state.getCurrentRoom().printDecisionNode(state.getCurrentDecisionNodeId());
            }
        }
    }

    /**
     * Executes a decision command when user is given
     * an active choice with alternatives by typing 'decision'
     * followed by corresponding letter a, b, c, etc. The method first
     * checks if the decision command is correctly typed. If it is, then
     * it filters the input by removing whitespaces (trim) and converting
     * it all to lowercase letters. Afterwards, it finds a choice index
     * by subtracting the given letter in the console with a, which gives and index
     * from 0 onwards because each letter is encoded with a number. After that
     * we get the next decision node, check if the given alternative is within
     * the arrange of the possible alternatives. IF next node is an en node, then we
     * print
     * it's description. If it is also fatal, then we let the user restart at the
     * same decision node. Finally, we print the description and options of the
     * newly assigned currentNodeId.
     * 
     * 
     * @param splitCommand the string entered by the player separated by whitespace
     *                     into an array.
     * 
     * @param state        the current state that the game is in.
     */
    private static void executeDecisionCommand(String[] splitCommand, GameState state) {
        if (splitCommand.length < 2) {
            printMissingModifierMessage("do");
        } else {
            String choice = splitCommand[1].trim().toLowerCase();
            int choiceIndex = choice.charAt(0) - 'a'; // 'a' = 0, 'b' = 1, osv ty ASCII a = 97, b = 98.

            DecisionNode currentDecisionNode = state.getCurrentRoom().getDecisionTree().getNodes()
                    .get(state.getCurrentDecisionNodeId());

            if (currentDecisionNode != null) {
                HashMap<String, String> options = currentDecisionNode.getOptions();
                ArrayList<String> keysAsArray = new ArrayList<>(options.keySet());
                String previousDecisionNodeId = state.getCurrentDecisionNodeId();

                if (choiceIndex >= 0 && choiceIndex < keysAsArray.size()) {
                    String decisionKey = keysAsArray.get(choiceIndex);
                    String nextDecisionId = options.get(decisionKey);
                    String nextId = nextDecisionId.substring(nextDecisionId.indexOf(":") + 1);
                    state.setCurrentDecisionNodeId(nextId);

                    Typewriter.typeBold(("**You have chosen: " + decisionKey + "**"), 0);

                    DecisionNode nextDecisionNode = state.getCurrentRoom().getDecisionTree().getNodes().get(nextId);
                    DecisionTree roomDecisionTree = state.getCurrentRoom().getDecisionTree();

                    if (nextDecisionNode != null) {
                        if (nextDecisionNode.isEndNode()) {
                            roomDecisionTree.printDecisionNodeDescription(state.getCurrentDecisionNodeId());

                            if (nextDecisionNode.getId().contains("FATAL")) {
                                state.setCurrentDecisionNodeId(previousDecisionNodeId);
                                Typewriter.typeBold("GAME OVER. TRY AGAIN", 50);
                            } else if (nextDecisionNode.getId().contains("WIN")) {
                                Typewriter.typeBold("YOU HAVE WON THE GAME BY COMPLETING THE OBJECTIVE\n", 50);
                            }
                        }
                    } else {
                        Typewriter.typeBold(("No more choices!\n"), 50);
                        printHelpMessage();
                    }

                    roomDecisionTree.printDecision(state.getCurrentDecisionNodeId());
                } else {
                    System.out.println("Invalid option. Please choose a valid option.");
                }
            } else {
                System.out.println("No current decision node found.");
            }
        }
    }

    private static void executeLookCommand(GameState state) {
        state.getCurrentRoom().lookAround();
    }

    private static void printInvalidCommandMessage() {
        System.out.println("I'm sorry, that's not a valid command. " +
                "Type \"help\" for information about commands.");
    }

    private static void printMissingModifierMessage(String command) {
        System.out.println("I'm sorry, the \"" + command + "\" command requires one more command word. " +
                "Type \"help\" for information about commands.");
    }

    /**
     * Print out the commands available to the player.
     */
    public static void printHelpMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("Your available commands are:");
        sb.append("\n");
        sb.append("    \"go <room name>\" - Attempt to go to the given room.");
        sb.append("\n");
        sb.append("    \"do <a, b, or c etc>\" - Choose an alternative (a, b, or c) only if prompted to do so.");
        sb.append("\n");
        sb.append("    \"look\" - Look around the current room");
        sb.append("\n");
        sb.append("    \"help\" - Print this useful help message.");

        Typewriter.typeRegular(sb.toString(), 0);
    }
}