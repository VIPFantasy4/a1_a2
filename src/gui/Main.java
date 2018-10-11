package gui;

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

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600, 450, Color.WHITE);

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);

        // File menu - new, save, exit
        Menu fileMenu = new Menu("File");
        MenuItem newMenuItem = new MenuItem("New");
        MenuItem saveMenuItem = new MenuItem("Save");
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event -> Platform.exit());

        fileMenu.getItems().addAll(newMenuItem, saveMenuItem,
                new SeparatorMenuItem(), exitMenuItem);

        Menu webMenu = new Menu("Web");
        CheckMenuItem htmlMenuItem = new CheckMenuItem("HTML");
        htmlMenuItem.setSelected(true);
        webMenu.getItems().add(htmlMenuItem);

        CheckMenuItem cssMenuItem = new CheckMenuItem("CSS");
        cssMenuItem.setSelected(true);
        webMenu.getItems().add(cssMenuItem);

        Menu sqlMenu = new Menu("SQL");
        ToggleGroup tGroup = new ToggleGroup();
        RadioMenuItem mysqlItem = new RadioMenuItem("MySQL");
        mysqlItem.setToggleGroup(tGroup);

        RadioMenuItem oracleItem = new RadioMenuItem("Oracle");
        oracleItem.setToggleGroup(tGroup);
        oracleItem.setSelected(true);

        sqlMenu.getItems().addAll(mysqlItem, oracleItem,
                new SeparatorMenuItem());

        Menu tutorialManeu = new Menu("Tutorial");
        tutorialManeu.getItems().addAll(
                new CheckMenuItem("Java"),
                new CheckMenuItem("JavaFX"),
                new CheckMenuItem("Swing"));

        sqlMenu.getItems().add(tutorialManeu);

        menuBar.getMenus().addAll(fileMenu, webMenu, sqlMenu);

        //direction area
        BorderPane dirPane = new BorderPane();
        dirPane.setPadding(new Insets(65,60,110,0));
        root.setRight(dirPane);

        //north button
        Button northButton = new Button("north");
        northButton.setPrefSize(70,40);
        northButton.setStyle("-fx-background-color: #336699");
        //east button
        Button eastButton = new Button("east");
        eastButton.setPrefSize(70,40);
        eastButton.setStyle("-fx-background-color: #336699");
        //south button
        Button southButton = new Button("south");
        southButton.setPrefSize(70,40);
        southButton.setStyle("-fx-background-color: #336699");
        //west button
        Button westButton = new Button("west");
        westButton.setPrefSize(70,40);
        westButton.setStyle("-fx-background-color: #336699");
        //dig button
        Button digButton = new Button("Dig");
        digButton.setStyle("-fx-background-color: #336699");
        //drop button
        Button dropButton = new Button("Drop");
        dropButton.setStyle("-fx-background-color: #336699");
        //move choice
        ObservableList cursors = FXCollections.observableArrayList("Move Builder","Move Block");
        ChoiceBox choiceBox = new ChoiceBox();
        choiceBox.setItems(cursors);
        choiceBox.getSelectionModel().select(0);
        choiceBox.setPrefWidth(110);
        //drop Index textField
        TextField dropIndexField = new TextField();
        dropIndexField.setPrefWidth(140);
        dropIndexField.setPromptText("Drop Index");

        //north and south
        VBox cenBox = new VBox();
        cenBox.setSpacing(40);
        cenBox.getChildren().addAll(northButton,southButton);
        dirPane.setCenter(cenBox);
        //west
        VBox leftBox = new VBox();
        leftBox.setPadding(new Insets(40,0,0,0));
        leftBox.getChildren().add(westButton);
        dirPane.setLeft(leftBox);
        //east
        VBox rightBox = new VBox();
        rightBox.getChildren().add(eastButton);
        rightBox.setPadding(new Insets(40,0,0,0));
        dirPane.setRight(rightBox);

        //choiceBox,digButton,dropButton and dropIndexField
        VBox otherBox = new VBox();
        otherBox.setSpacing(20);
        HBox choiceHBox = new HBox();
        choiceHBox.setPadding(new Insets(0,0,0,50));
        choiceHBox.getChildren().add(choiceBox);
        HBox digHBox = new HBox();
        digHBox.getChildren().add(digButton);
        HBox dropHBox = new HBox();
        dropHBox.setSpacing(5);
        dropHBox.getChildren().addAll(dropButton,dropIndexField);
        otherBox.getChildren().addAll(choiceHBox,digHBox,dropHBox);
        dirPane.setBottom(otherBox);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}