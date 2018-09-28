package a2;

import java.io.BufferedReader;

/**
 * Created by Administrator on 2018/9/28.
 */
public class Action {
    public static final int DIG = 2;
    public static final int DROP = 3;
    public static final int MOVE_BLOCK = 1;
    public static final int MOVE_BUILDER = 0;

    public Action(int primaryAction, String secondaryAction) {
    }

    public int getPrimaryAction() {
        return 0;
    }

    public String getSecondaryAction() {
        return null;
    }

    public static Action loadAction(BufferedReader reader) throws ActionFormatException {
        return null;
    }

    public static void processAction(Action action, WorldMap map) {
    }

    public static void processActions(BufferedReader reader, WorldMap startingMap) throws ActionFormatException {
    }
}
