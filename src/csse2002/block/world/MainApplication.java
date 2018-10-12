package csse2002.block.world;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
        root.setStyle("-fx-border-color: black");

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

        /* direction area */
        BorderPane dirPane = new BorderPane();
        dirPane.setPadding(new Insets(45, 60, 230, 0));
        root.setRight(dirPane);

        /* north button */
        Button northButton = new Button("north");
        northButton.setPrefSize(70, 40);
        northButton.setStyle("-fx-base: #336699");
        northButton.setTextFill(Color.WHITE);

        /* east button */
        Button eastButton = new Button("east");
        eastButton.setPrefSize(70, 40);
        eastButton.setStyle("-fx-base: #336699");
        eastButton.setTextFill(Color.WHITE);

        /* south button */
        Button southButton = new Button("south");
        southButton.setPrefSize(70, 40);
        southButton.setStyle("-fx-base: #336699");
        southButton.setTextFill(Color.WHITE);

        /* west button */
        Button westButton = new Button("west");
        westButton.setPrefSize(70, 40);
        westButton.setStyle("-fx-base: #336699");
        westButton.setTextFill(Color.WHITE);

        /* dig button */
        Button digButton = new Button("Dig");
        digButton.setStyle("-fx-base: #336699");
        digButton.setTextFill(Color.WHITE);

        /* drop button */
        Button dropButton = new Button("Drop");
        dropButton.setStyle("-fx-base: #336699");
        dropButton.setTextFill(Color.WHITE);

        /* move choice */
        ObservableList<String> cursors = FXCollections.observableArrayList("Move Builder", "Move Block");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(cursors);
        choiceBox.getSelectionModel().select(0);
        choiceBox.setPrefWidth(110);

        /* drop index TextField */
        TextField dropIndexField = new TextField();
        dropIndexField.setPrefWidth(140);
        dropIndexField.setPromptText("Drop Index");

        /* locate north and locate south */
        VBox cenBox = new VBox();
        cenBox.setSpacing(40);
        cenBox.getChildren().addAll(northButton, southButton);
        dirPane.setCenter(cenBox);
        /* locate west */
        VBox leftBox = new VBox();
        leftBox.setPadding(new Insets(40, 0, 0, 0));
        leftBox.getChildren().add(westButton);
        dirPane.setLeft(leftBox);
        /* locate east */
        VBox rightBox = new VBox();
        rightBox.getChildren().add(eastButton);
        rightBox.setPadding(new Insets(40, 0, 0, 0));
        dirPane.setRight(rightBox);

        /* fix choiceBox, digButton, dropButton and dropIndexField */
        VBox otherBox = new VBox();
        otherBox.setSpacing(20);
        HBox choiceHBox = new HBox();
        choiceHBox.setPadding(new Insets(0, 0, 0, 50));
        choiceHBox.getChildren().add(choiceBox);
        HBox digHBox = new HBox();
        digHBox.getChildren().add(digButton);
        HBox dropHBox = new HBox();
        dropHBox.setSpacing(5);
        dropHBox.getChildren().addAll(dropButton, dropIndexField);
        otherBox.getChildren().addAll(choiceHBox, digHBox, dropHBox);
        dirPane.setBottom(otherBox);

        Scene scene = new Scene(root, 800, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Block World");
        primaryStage.show();
    }
}
