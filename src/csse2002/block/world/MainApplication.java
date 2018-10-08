package csse2002.block.world;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Created by Administrator on 2018/10/8.
 */
public class MainApplication extends Application {
    public static void main(String[] args) {
        launch(MainApplication.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        /* File Menu */
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        // TODO: action of load and of save
        MenuItem loadMenuItem = new MenuItem("Load Game World");
        MenuItem saveMenuItem = new MenuItem("Save World Map");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event -> Platform.exit());
        fileMenu.getItems().addAll(loadMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(fileMenu);
        root.setTop(menuBar);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Block World");
        primaryStage.show();
    }
}
