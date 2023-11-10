import java.util.HashMap;

/**
 * Decision node is a node in a decision tree
 * that has a description, id, and options (or alternatives) that
 * lead to other decision nodes. 
 * 
 * @author Jefim Holmgren
 */
public class DecisionNode {
    private String id;
    private String description;
    private HashMap<String, String> options;
    private boolean isEndNode;

    /**
     * Create a new decision node
     * 
     * @param id The id of the decision node 
     * in the pattern DECISION_DO_SOMETHING. Winning conditions end with
     * _WIN and fatal with _FATAL. 
     * 
     * @param description Description of the decision node, ie the
     * outcome/context of the decision node depening on if it
     * is an end node. 
     * 
     * @param options The decision nodes that are connected to this deicison node
     * that the player can choose between.
     * 
     * @param isEndNode Is this a end node, meaning it is not  
     * linked to other decision nodes. 
     */
    public DecisionNode(String id, String description, HashMap<String, String> options, boolean isEndNode) {
        this.id = id;
        this.description = description;
        this.options = options;
        this.isEndNode = isEndNode;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String, String> getOptions() {
        return options;
    }

    public boolean isEndNode() {
        return isEndNode;
    }

    public void setEndNode(boolean isEndNode) {
        this.isEndNode = isEndNode;
    }
}
