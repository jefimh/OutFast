### Game description
> Add a description of your game here.
The game is called OutFast, and is inspired by the OutLast 1 horror asylum game. 
The player wakes up in "Mount Dark Asylum" from unconscioussnes. After that, the player
has to complete an objective, being to navigate to the security room to open the main exit gate, 
and afterwards successfully escape the asylum. While navigating through the asylum, 
some cutscenes will trigger upon entering a room. The player does not have
to engage in these or complete them, and can just simply decide to go to another room.
If the player engages in the cutscenes, then the player can pick between alternatives
given on the screen to advance thru the decision tree. The cutscenes
are meant to give the player context about the asylum's horrible past. The player has to keep in mind
though that some decisions are FATAL, ie lead to GAME OVER, and some just lead to END.   


### How to win and lose
> Add a description of how to win the game, and how to lose it.
The player has to navigate to the Security Room. Once the player enters the room 
a cutscene triggers. The player has to then make certain decisions that
either lead to FAIL (GAME OVER), or ultimately to the winning decision. But, in order
to avoid the game being too long to play, the player gets to automatically replay
the last decision if it leads to FAIL. 

The win condition path chart: 
1. Start: DECISION_START
2. Disable the security cameras: DECISION_DISABLE_CAMERAS
3. Decide your next action: DECISION_CHOOSE_ACTION
4. Hide in the locker: DECISION_HIDE_IN_LOCKER
5. Make your escape: DECISION_MAKE_ESCAPE
6. Explore another path: DECISION_EXPLORE_ANOTHER_PATH
7. Find a hidden exit (Winning Decision): DECISION_FIND_HIDDEN_EXIT

### Classes added to the game
> Explain what class or classes you added to the game, and how these are used in the game.
I've added the following classes: 

1. DecisionTree Class: This class forms the backbone of the decision-making system in the game. It represents a decision tree where each node corresponds to a game choice. The tree is loaded from a file, in my case "decisionTrees.txt", and is stored in a HashMap, allowing for quick access to decision nodes. Each room in the game has an associated decision tree, making room-specific decision-making possible. Also, the system is modular, so it can be built upon by perhaps adding more alternatives
to choices or just adding more decision nodes, without having to change the code. 

2. DecisionNode Class: These nodes are the individual elements of the DecisionTree. Each node has a unique ID, a description (which provides context or outcomes of decisions), and a set of options that lead to other decision nodes. Nodes can also be end nodes, signaling the conclusion of a decision path. These nodes are autmatically accessed from the decision tree following similair pattern
as in the provided rooms.txt file. In my case, the decisionTrees.txt has the following structure:
ROOM;&DECISION ID;&DESCRIPTION;&OPTIONS

3. Typewriter Class: This class simulates a typewriter effect when printing text to the console, with the ability to print in normal, italic, and bold styles. This effect is achieved by introducing delays between the printing of each character, enhancing the storytelling aspect of the game.
