package a2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2018/9/28.
 */
public class Main {
    public static void main(String[] args) {
        if (args.length != 3){
            System.err.println("Usage: program inputMap actions outputMap");
            System.exit(1);
        }
        WorldMap worldMap = null;
        try {
            worldMap = new WorldMap(args[0]);
        } catch (Exception e) {
            System.err.println(e);
            System.exit(2);
        }
        BufferedReader bufferedReader = null;
        if ("System.in".equals(args[1])){
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        }else {
            try {
                bufferedReader = new BufferedReader(new FileReader(args[1]));
            } catch (Exception e) {
                System.err.println(e);
                System.exit(3);
            }
        }
        try {
            Action.processActions(bufferedReader,worldMap);
        } catch (ActionFormatException e) {
            System.err.println(e);
            System.exit(4);
        }
        try {
            worldMap.saveMap(args[2]);
        } catch (IOException e) {
            System.err.println(e);
            System.exit(5);
        }
    }
}
