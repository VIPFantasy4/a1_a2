package a2;

import csse2002.block.world.*;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by Administrator on 2018/9/28.
 */
public class Action {
    public static final int DIG = 2;
    public static final int DROP = 3;
    public static final int MOVE_BLOCK = 1;
    public static final int MOVE_BUILDER = 0;

    private int primaryAction;
    private String secondaryAction;

    public Action(int primaryAction, String secondaryAction) {
        this.primaryAction = primaryAction;
        this.secondaryAction = secondaryAction;
    }

    public int getPrimaryAction() {
        return this.primaryAction;
    }

    public String getSecondaryAction() {
        return this.secondaryAction;
    }

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
                        // TODO:
                        tile.moveBlock(null);
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

    public static void processActions(BufferedReader reader, WorldMap startingMap) throws ActionFormatException {
        Action action = loadAction(reader);
        if (action != null) {
            processAction(action, startingMap);
            processActions(reader, startingMap);
        }
    }
}
