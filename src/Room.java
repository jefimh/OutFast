import java.util.HashMap;
import java.util.Map;

/**
 * The Room class contains information about a room (or some other place) that
 * the player is currently in.
 * 
 * @author Gabriel Skoglund
 * 
 * @author Jefim Holmgren
 */
public class Room {

    /**
     * The exits map contains keys which are directions and values which are the
     * rooms that
     * the exit leads to.
     */
    private Map<String, Room> exits = new HashMap<>();
    private DecisionTree decisionTree;
    private String name;
    private String description;

    /**
     * Create a new room.
     * 
     * @param description the description of this room.
     */
    public Room(String name, String description, DecisionTree decisionTree) {
        this.name = name;
        this.description = description;
        this.decisionTree = decisionTree;
    }

    /**
     * Add an exit from this room.
     * 
     * @param direction the direction in which the exit is.
     * @param toRoom    the room that this exit leads to.
     */
    public void addExit(String direction, Room toRoom) {
        exits.put(direction, toRoom);
    }

    /**
     * Attempt to leave this room in the given direction.
     * 
     * @param direction the direction in which to move the player.
     * @return the room that the player ends up in. May be null if
     *         there is no exit from this room in that direction.
     */
    public Room go(String direction) {
        Room newRoom = exits.get(direction);
        if (newRoom == null) {
            System.out.println("You can't go that way!");
            printExits();
        }
        return newRoom;
    }

    /**
     * Print the exits that are available from this room.
     */
    public void printExits() {
        System.out.println();
        Typewriter.typeBold("There are exits in the directions: ", 0);
        for (String direction : exits.keySet())
            Typewriter.typeRegular(direction + " ", 0);
        System.out.println();
    }

    /**
     * Look around the current room, printing the room descriptions and the
     * available exits.
     */
    public void lookAround() {
        Typewriter.typeItalic(description, 50);
        printExits();
    }

    public String getDescription() {
        return description;
    }

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    public void printDecisionNode(String nodeId) {
        if (this.decisionTree != null) {
            this.decisionTree.printDecision(nodeId);
        }
    }

    public String getName() {
        return name;
    }
}