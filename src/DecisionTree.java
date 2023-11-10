import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * The DecisionTree class is responsible for holding
 * decision nodes related to the tree in a hashmap. In turn,
 * the room class holds a decision tree instance
 * since each decision tree is associated with one unique room.
 * 
 * @author Jefim Holmgren
 */
public class DecisionTree {
    private HashMap<String, DecisionNode> nodes;
    private static final String FILENAME = "decisionTrees.txt";

    /**
     * Creates a new decision tree from a text file, 
     * in our case the "decisionTrees.txt" text file.
     * 
     * @param roomName The name of the room for which
     * the decision tree needs to be loaded. Each decision node
     * starts with the room that it belongs to in the "decisionTrees.txt". 
     */
    public DecisionTree(String roomName) {
        nodes = new HashMap<>();
        getDecisionTreeFromFile(roomName);
    }

    public HashMap<String, DecisionNode> getNodes() {
        return nodes;
    }

    public void addNode(DecisionNode node) {
        nodes.put(node.getId(), node);
    }

    public DecisionNode getNode(String id) {
        return nodes.get(id);
    }

    /**
     * Prints description of a decision node. Remember that the
     * decision node class has a description field. By passing in
     * a nodeId of typ string we can get a specific decision node
     * from the hashmap of the decision tree. Note,
     * 
     * @param nodeId nodeId is represented in the "decisionTrees.txt" text file as
     *               DECISION_DO_SOMETHING.
     */
    public void printDecisionNodeDescription(String nodeId) {
        DecisionNode currentDecisionNode = nodes.get(nodeId);

        if (currentDecisionNode != null) {
            System.out.println();
            Typewriter.typeBold(currentDecisionNode.getDescription(), 50);
        }
    }

    /**
     * Prints the alternative choice for a specific node in decision tree.
     * We print the letter that corresponds to the alternative based on
     * its order in the hashmap. In this case, letters up to 'j' are stored.
     * In reality, we only use up to 'e' but the option for expansion is there.
     * 
     * @param nodeId nodeId is represented in the "decisionTrees.txt" text file as
     *               DECISION_DO_SOMETHING.
     */
    public void printDecisionNodeOptions(String nodeId) {
        DecisionNode currentDecisionNode = nodes.get(nodeId);

        if (currentDecisionNode != null) {
            char[] letters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j' };
            currentDecisionNode.getOptions().keySet();

            StringBuilder sb = new StringBuilder();
            sb.append("\n");
            int i = 0;
            for (String key : currentDecisionNode.getOptions().keySet()) {
                sb.append("<");
                sb.append(letters[i]);
                sb.append("> ");
                sb.append(key);
                sb.append("\n");
                i++;
            }

            Typewriter.typeRegular(sb.toString(), 0);
        }
    }

    /**
     * Prints information about the deicison node
     * in the decision tree, specifically the options
     * and descriptions of the decision node
     * given a node id. 
     * 
     * @param nodeId The id of the decision node
     */
    public void printDecision(String nodeId) {
        printDecisionNodeDescription(nodeId);
        printDecisionNodeOptions(nodeId);
    }

    /**
     * Load decision tree from a text file which in our case
     * is 'decisionTrees.txt'. Each decision node in the 'decisionsTrees.txt' is 
     * writen on a unique line following similair pattern
     * from the provided 'rooms.txt'. First on the line
     * appears the room to which a decision node belongs to. 
     * After that comes the id of the node, then description, then it's
     * options connected to other node id's separated by ':'. This is done
     * in orther to store the String to the right of delimeter ':'
     * and to the left of delimeter in a hashmap. Using this, we can link
     * nodes in decisionTrees.txt with other nodes. 
     * 
     * @param roomName the name of the room
     * 
     * @throws if fails to open the provided file with FILENAME
     * for some reason
     */
    public void getDecisionTreeFromFile(String roomName) {
        try {
            // För Svenska bokstäver (UTF-8)
            FileInputStream fileInputStream = new FileInputStream(FILENAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
            BufferedReader file = new BufferedReader(inputStreamReader);

            String line = file.readLine();
            while (line != null) {
                boolean isEndNode = true;

                String[] splittedLine = line.split(";&");

                if (roomName.equals(splittedLine[0])) {
                    String decisionId = splittedLine[1].trim();
                    String description = splittedLine[2];
                    HashMap<String, String> options = new HashMap<>();

                    if (splittedLine.length > 3) {
                        String optionsText = splittedLine[3].trim();
                        String[] optionsArray = optionsText.split("\\], \\[");

                        for (String option : optionsArray) {
                            option = option.replaceAll("[\\[\\]]", "");
                            String[] optionParts = option.split(":");
                            options.put(optionParts[0], optionParts[1]);
                        }

                        isEndNode = false;
                    }

                    DecisionNode decisionNode = new DecisionNode(decisionId, description, options, isEndNode);
                    nodes.put(decisionId, decisionNode);
                }

                line = file.readLine();
            }

            file.close();
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
            System.exit(1);
        }
    }
}
