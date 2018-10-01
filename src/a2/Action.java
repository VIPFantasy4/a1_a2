package a2;

import csse2002.block.world.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Represents an Action which can be performed on the block world (also called world map).
 * An action is something that a builder can do on a tile in the block world.
 * The actions include, moving the builder in a direction, moving a block in a direction, digging on the current tile the builder is standing on and dropping an item from a builder's inventory.
*/
public class Action {
    /*DIG action which is represented by integer 2*/
    public static final int DIG = 2;
    /*DROP action which is represented by integer 3*/
    public static final int DROP = 3;
    /*MOVE_BLOCK action which is represented by integer 1*/
    public static final int MOVE_BLOCK = 1;
    /*MOVE_BUILDER action which is represented by integer 0*/
    public static final int MOVE_BUILDER = 0;

    private int primaryAction;
    private String secondaryAction;

    /**
     * Create an Action that represents a manipulation of the blockworld.
     * An action is represented by a primary action (one of MOVE_BUILDER, MOVE_BLOCK, DIG or DROP), and a secondary action
     * @param primaryAction
     * @param secondaryAction
     */
    public Action(int primaryAction, String secondaryAction) {
        this.primaryAction = primaryAction;
        this.secondaryAction = secondaryAction;
    }

    /**
     *
     * @return primaryAction
     */
    public int getPrimaryAction() {
        return this.primaryAction;
    }

    /**
     *
     * @return secondaryAction
     */
    public String getSecondaryAction() {
        return this.secondaryAction;
    }

    /**
     * Create a single Action if possible from the given reader.
     * Read a line from the given reader and load the Action on that line. Only load one Action and return the created action.
     * Each line consists of a primary action, and optionally a secondary action.
     * @param reader
     * @return new Action instance according to line
     * @throws ActionFormatException
     */
    public static Action loadAction(BufferedReader reader) throws ActionFormatException {
        String line;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ActionFormatException();
        }
        if (line == null) return null;

//        line = line.trim();
        String[] tokens = line.split(" ");
        if (tokens.length == 0 || tokens.length > 2) throw new ActionFormatException();
        int primaryAction;
        switch (tokens[0]) {
            case "DIG": {
                if (tokens.length != 1) throw new ActionFormatException();
                return new Action(DIG, "");
            }
            case "MOVE_BLOCK":
                primaryAction = MOVE_BLOCK;
                break;
            case "MOVE_BUILDER":
                primaryAction = MOVE_BUILDER;
                break;
            case "DROP":
                primaryAction = DROP;
                break;
            default:
                throw new ActionFormatException();
        }
        if (tokens.length == 1) throw new ActionFormatException();
        return new Action(primaryAction, tokens[1]);
    }

    /**
     * Perform the given action on a WorldMap, and print output to System.out. After this method finishes, map should be updated.
     * @param action
     * @param map
     */
    public static void processAction(Action action, WorldMap map) {
        boolean flag = false;
        String secondaryAction = action.getSecondaryAction();
        Builder builder = map.getBuilder();
        switch (action.getPrimaryAction()) {
            case DIG: {
                try {
                    builder.digOnCurrentTile();
                } catch (TooLowException e) {
                    System.out.println("Too low");
                    break;
                } catch (InvalidBlockException e) {
                    System.out.println("Cannot use that block");
                    break;
                }
                System.out.println("Top block on current tile removed");
                break;
            }
            case DROP: {
                int inventoryIndex;
                try {
                    inventoryIndex = Integer.parseInt(secondaryAction);
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid action");
                    break;
                }
                try {
                    builder.dropFromInventory(inventoryIndex);
                } catch (InvalidBlockException e) {
                    System.out.println("Cannot use that block");
                    break;
                } catch (TooHighException e) {
                    System.out.println("Too high");
                    break;
                }
                System.out.println("Dropped a block from inventory");
                break;
            }
            case MOVE_BLOCK:
                flag = true;
            case MOVE_BUILDER: {
                if (!"north".equals(secondaryAction) && !"east".equals(secondaryAction) && !"south".equals(secondaryAction) && !"west".equals(secondaryAction)) {
                    System.out.println("Error: Invalid action");
                    break;
                }
                if (flag) {
                    Tile tile = builder.getCurrentTile();
                    try {
                        tile.moveBlock(secondaryAction);
                    } catch (TooHighException e) {
                        System.out.println("Too high");
                        break;
                    } catch (InvalidBlockException e) {
                        System.out.println("Cannot use that block");
                        break;
                    } catch (NoExitException e) {
                        System.out.println("No exit this way");
                        break;
                    }
                    System.out.println("Moved block " + secondaryAction);
                    break;
                } else {
                    try {
                        // TODO:
                        builder.moveTo(null);
                    } catch (NoExitException e) {
                        System.out.println("No exit this way");
                        break;
                    }
                    System.out.println("Moved builder " + secondaryAction);
                    break;
                }
            }
            default:
                System.out.println("Error: Invalid action");
        }
    }

    /**
     * Read all the actions from the given reader and perform them on the given block world.
     * All actions that can be performed should print an appropriate message (as outlined in processAction()),
     * any invalid actions that cannot be created or performed on the world map, should also print an error message (also described in processAction()).
     * Each message should be printed on a new line (Use System.out.println()).
     * Each action is listed on a single line, and one file can contain multiple actions.
     * Each action must be processed after it is read
     * @param reader
     * @param startingMap
     * @throws ActionFormatException
     */
    public static void processActions(BufferedReader reader, WorldMap startingMap) throws ActionFormatException {
        Action action = loadAction(reader);
        if (action != null) {
            processAction(action, startingMap);
            processActions(reader, startingMap);
        }
    }
}
