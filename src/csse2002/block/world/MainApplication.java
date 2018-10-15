package csse2002.block.world;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/8.
 */
public class MainApplication extends Application {
    private static int STARTING_X = 230;
    private static int STARTING_Y = 230;


    private WorldMap worldMap;
    private Map<Tile, Position> tilePosition = new HashMap<>();

    public static void main(String[] args) {
        launch(MainApplication.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane();

        /* display area */
        BorderPane disPane = new BorderPane();
        disPane.setStyle("-fx-border-color: red");  // highlight for debugging
        Rectangle border = new Rectangle(30, 30, 50 * 9, 50 * 9);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        disPane.getChildren().add(border);
        root.setCenter(disPane);

        /* inventory */
        VBox inventoryVBox = new VBox();
        inventoryVBox.setPadding(new Insets(0, 0, 50, 30));
        Text inventoryText = new Text("Inventory:");
        inventoryText.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        Text inventory = new Text("[]");
        inventory.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        inventoryVBox.getChildren().addAll(inventoryText, inventory);
        root.setBottom(inventoryVBox);

        /* direction area */
        BorderPane dirPane = new BorderPane();
        dirPane.setPadding(new Insets(45, 60, 180, 0));
        dirPane.setStyle("-fx-border-color: yellow");  // highlight for debugging
        root.setRight(dirPane);

        /* move choice */
        ObservableList<String> cursors = FXCollections.observableArrayList("Move Builder", "Move Block");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setItems(cursors);
        choiceBox.getSelectionModel().select(0);
        choiceBox.setPrefWidth(110);

        /* north button */
        Button northButton = new Button("north");
        northButton.setPrefSize(70, 40);
        northButton.setStyle("-fx-base: #336699");
        northButton.setTextFill(Color.WHITE);
        northButton.setOnAction(event -> {
            Action northAction;
            if ("Move Builder".equals(choiceBox.getSelectionModel().selectedItemProperty().getValue())) {
                northAction = new Action(Action.MOVE_BUILDER, "north");
                if (!alertRespMsgAfterProcessedAction(northAction, "Moved")) return;
                STARTING_Y += 50;
            } else {
                northAction = new Action(Action.MOVE_BLOCK, "north");
                if (!alertRespMsgAfterProcessedAction(northAction, "Moved")) return;
            }
            inventory.setText(builderInventory(worldMap));
            displayMap(worldMap, disPane);
        });

        /* east button */
        Button eastButton = new Button("east");
        eastButton.setPrefSize(70, 40);
        eastButton.setStyle("-fx-base: #336699");
        eastButton.setTextFill(Color.WHITE);
        eastButton.setOnAction(event -> {
            Action eastAction;
            if ("Move Builder".equals(choiceBox.getSelectionModel().selectedItemProperty().getValue())) {
                eastAction = new Action(Action.MOVE_BUILDER, "east");
                if (!alertRespMsgAfterProcessedAction(eastAction, "Moved")) return;
                STARTING_X -= 50;
            } else {
                eastAction = new Action(Action.MOVE_BLOCK, "east");
                if (!alertRespMsgAfterProcessedAction(eastAction, "Moved")) return;
            }
            inventory.setText(builderInventory(worldMap));
            displayMap(worldMap, disPane);
        });

        /* south button */
        Button southButton = new Button("south");
        southButton.setPrefSize(70, 40);
        southButton.setStyle("-fx-base: #336699");
        southButton.setTextFill(Color.WHITE);
        southButton.setOnAction(event -> {
            Action southAction;
            if ("Move Builder".equals(choiceBox.getSelectionModel().selectedItemProperty().getValue())) {
                southAction = new Action(Action.MOVE_BUILDER, "south");
                if (!alertRespMsgAfterProcessedAction(southAction, "Moved")) return;
                STARTING_Y -= 50;
            } else {
                southAction = new Action(Action.MOVE_BLOCK, "south");
                if (!alertRespMsgAfterProcessedAction(southAction, "Moved")) return;
            }
            inventory.setText(builderInventory(worldMap));
            displayMap(worldMap, disPane);
        });

        /* west button */
        Button westButton = new Button("west");
        westButton.setPrefSize(70, 40);
        westButton.setStyle("-fx-base: #336699");
        westButton.setTextFill(Color.WHITE);
        westButton.setOnAction(event -> {
            Action westAction;
            if ("Move Builder".equals(choiceBox.getSelectionModel().selectedItemProperty().getValue())) {
                westAction = new Action(Action.MOVE_BUILDER, "west");
                if (!alertRespMsgAfterProcessedAction(westAction, "Moved")) return;
                STARTING_X += 50;
            } else {
                westAction = new Action(Action.MOVE_BLOCK, "west");
                if (!alertRespMsgAfterProcessedAction(westAction, "Moved")) return;
            }
            inventory.setText(builderInventory(worldMap));
            displayMap(worldMap, disPane);
        });

        /* dig button */
        Button digButton = new Button("Dig");
        digButton.setStyle("-fx-base: #336699");
        digButton.setTextFill(Color.WHITE);
        digButton.setOnAction(event -> {
            Action digAction = new Action(Action.DIG, "");
            if (!alertRespMsgAfterProcessedAction(digAction, "Top")) return;
            inventory.setText(builderInventory(worldMap));
            displayMap(worldMap, disPane);
        });

        /* drop index TextField */
        TextField dropIndexField = new TextField();
        dropIndexField.setPrefWidth(140);
        dropIndexField.setPromptText("Drop Index");
        /* drop button */
        Button dropButton = new Button("Drop");
        dropButton.setStyle("-fx-base: #336699");
        dropButton.setTextFill(Color.WHITE);
        dropButton.setOnAction(event -> {
            Action dropAction = new Action(Action.DROP, dropIndexField.getText());
            if (!alertRespMsgAfterProcessedAction(dropAction, "Dropped")) return;
            inventory.setText(builderInventory(worldMap));
            displayMap(worldMap, disPane);
        });

        /* locate north and locate south */
        VBox cenBox = new VBox();
        cenBox.setStyle("-fx-border-color: greenyellow");
        cenBox.setSpacing(40);
        cenBox.getChildren().addAll(northButton, southButton);
        dirPane.setCenter(cenBox);
        /* locate west */
        VBox leftBox = new VBox();
        leftBox.setStyle("-fx-border-color: blue");
        leftBox.setPadding(new Insets(40, 0, 0, 0));
        leftBox.getChildren().add(westButton);
        dirPane.setLeft(leftBox);
        /* locate east */
        VBox rightBox = new VBox();
        rightBox.setStyle("-fx-border-color: deeppink");
        rightBox.getChildren().add(eastButton);
        rightBox.setPadding(new Insets(40, 0, 0, 0));
        dirPane.setRight(rightBox);

        /* fix choiceBox, digButton, dropButton and dropIndexField */
        VBox otherBox = new VBox();
        otherBox.setStyle("-fx-border-color: coral");
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

        /* File Menu */
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem loadMenuItem = new MenuItem("Load Game World");
        loadMenuItem.setOnAction(event -> {
            File file = new FileChooser().showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    STARTING_X = 230;
                    STARTING_Y = 230;
                    worldMap = new WorldMap(file.getPath());
                    setDisable(false, disPane, dirPane);
                    inventoryText.setText("Builder Inventory:");
                    inventory.setText(builderInventory(worldMap));
                    displayMap(worldMap, disPane);
                    alertInformation("map was successfully loaded");
                } catch (WorldMapFormatException | WorldMapInconsistentException | FileNotFoundException e) {
                    alertError(e.getMessage());
                }
            }
        });
        MenuItem saveMenuItem = new MenuItem("Save World Map");
        saveMenuItem.setOnAction(event -> {
            if (worldMap != null) {
                File file = new FileChooser().showSaveDialog(primaryStage);
                if (file != null) {
                    try {
                        worldMap.saveMap(file.getPath());
                        alertInformation("map was successfully saved");
                    } catch (IOException e) {
                        alertError(e.getMessage());
                    }
                }
            } else {
                alertError("U gotta load the map first");
            }
        });
        MenuItem exitMenuItem = new MenuItem("Exit");
        exitMenuItem.setOnAction(event -> Platform.exit());
        fileMenu.getItems().addAll(loadMenuItem, saveMenuItem, new SeparatorMenuItem(), exitMenuItem);
        menuBar.getMenus().addAll(fileMenu);
        root.setTop(menuBar);

        setDisable(true, disPane, dirPane);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Block World");
        primaryStage.show();
    }

    private void setDisable(boolean flag, Node... nodes) {
        for (Node node : nodes) {
            node.setDisable(flag);
        }
    }

    private void alertError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void alertInformation(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private boolean alertRespMsgAfterProcessedAction(Action action, String prefix) {
        OutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Action.processAction(action, worldMap);
        String respMsg;
        if ((respMsg = outputStream.toString().trim()).startsWith(prefix)) {
            alertInformation(respMsg);
            return true;
        } else {
            alertError(respMsg);
            return false;
        }
    }

    private String builderInventory(WorldMap worldMap) {
        List<Block> inventory = worldMap.getBuilder().getInventory();
        StringBuilder currentInventory = new StringBuilder();
        for (int i = 0; i < inventory.size(); i++) {
            Block block = inventory.get(i);
            if (i == inventory.size() - 1) {
                currentInventory.append(block.getBlockType());
                break;
            }
            currentInventory.append(block.getBlockType()).append(",");
        }
        currentInventory = new StringBuilder("[" + currentInventory + "]");
        return currentInventory.toString();
    }

    private void displayMap(WorldMap worldMap, BorderPane disPane) {
        tilePosition.clear();
        resetDisPane(disPane);
        List<Tile> tiles = worldMap.getTiles();
        tilePosition.put(tiles.get(0), new Position(STARTING_X, STARTING_Y));
        for (Tile tile : tiles) {
            Position position = tilePosition.get(tile);
            Map<String, Tile> exits = tile.getExits();
            if (exits != null && exits.size() > 0) {
                for (Map.Entry<String, Tile> exit : exits.entrySet()) {
                    switch (exit.getKey()) {
                        case "north":
                            tilePosition.put(exit.getValue(), new Position(position.getX(), position.getY() - 50));
                            break;
                        case "east":
                            tilePosition.put(exit.getValue(), new Position(position.getX() + 50, position.getY()));
                            break;
                        case "south":
                            tilePosition.put(exit.getValue(), new Position(position.getX(), position.getY() + 50));
                            break;
                        case "west":
                            tilePosition.put(exit.getValue(), new Position(position.getX() - 50, position.getY()));
                            break;
                        default:
                            throw new RuntimeException("This is 2 real Xu Zhenzhen");
                    }
                }
            }
        }
        for (Map.Entry<Tile, Position> entry : tilePosition.entrySet()) {
            Tile tile = entry.getKey();
            Position position = entry.getValue();
            if (!(position.getX() < 30 || position.getX() > 430 || position.getY() < 30 || position.getY() > 430)) {
                Rectangle rectangle = new Rectangle(position.getX(), position.getY(), 50, 50);
                rectangle.setStroke(Color.BLACK);
                try {
                    Block block = tile.getTopBlock();
                    switch (block.getBlockType()) {
                        case "grass":
                            rectangle.setFill(Color.GREEN);
                            break;
                        case "soil":
                            rectangle.setFill(Color.BLACK);
                            break;
                        case "stone":
                            rectangle.setFill(Color.GRAY);
                            break;
                        case "wood":
                            rectangle.setFill(Color.BROWN);
                            break;
                        default:
                    }
                } catch (TooLowException e) {
                    rectangle.setFill(Color.TRANSPARENT);
                }
                Map<String, Tile> exits = tile.getExits();
                Polygon northExitPolygon = new Polygon();
                Polygon eastExitPolygon = new Polygon();
                Polygon southExitPolygon = new Polygon();
                Polygon westExitPolygon = new Polygon();
                Tile exit;
                if ((exit = exits.get("north")) != null) {
                    if (canEnter(tile, exit)) {
                        northExitPolygon.getPoints().addAll(position.getX() + 25.0, (double) position.getY(),
                                position.getX() + 20.0, position.getY() + 5.0,
                                position.getX() + 30.0, position.getY() + 5.0);
                        northExitPolygon.setFill(Color.LIGHTBLUE);
                    }
                }
                if ((exit = exits.get("east")) != null) {
                    if (canEnter(tile, exit)) {
                        eastExitPolygon.getPoints().addAll(position.getX() + 50.0, (double) position.getY() + 25.0,
                                position.getX() + 45.0, position.getY() + 20.0,
                                position.getX() + 45.0, position.getY() + 30.0);
                        eastExitPolygon.setFill(Color.LIGHTBLUE);
                    }
                }
                if ((exit = exits.get("south")) != null) {
                    if (canEnter(tile, exit)) {
                        southExitPolygon.getPoints().addAll(position.getX() + 25.0, position.getY() + 50.0,
                                position.getX() + 20.0, position.getY() + 45.0,
                                position.getX() + 30.0, position.getY() + 45.0);
                        southExitPolygon.setFill(Color.LIGHTBLUE);
                    }
                }
                if ((exit = exits.get("west")) != null) {
                    if (canEnter(tile, exit)) {
                        westExitPolygon.getPoints().addAll((double) position.getX(), position.getY() + 25.0,
                                position.getX() + 5.0, position.getY() + 20.0,
                                position.getX() + 5.0, position.getY() + 30.0);
                        westExitPolygon.setFill(Color.LIGHTBLUE);
                    }
                }
                Text quantity = new Text(position.getX() + 4, position.getY() + 16, tile.getBlocks().size() + "");
                quantity.setFont(Font.font("Arial", FontWeight.BOLD, 16));
                quantity.setFill(Color.LIGHTBLUE);
                disPane.getChildren().addAll(rectangle, quantity, northExitPolygon, eastExitPolygon, southExitPolygon, westExitPolygon);
            }
        }
        Circle builder = new Circle(255, 255, 5, Color.YELLOW);
        builder.setStroke(Color.BLACK);
        disPane.getChildren().add(builder);
    }

    private boolean canEnter(Tile tile, Tile exit) {
        return Math.abs(exit.getBlocks().size() - tile.getBlocks().size()) <= 1;
    }

    private void resetDisPane(BorderPane disPane) {
        disPane.getChildren().clear();
        Rectangle border = new Rectangle(30, 30, 50 * 9, 50 * 9);
        border.setFill(Color.TRANSPARENT);
        border.setStroke(Color.BLACK);
        disPane.getChildren().add(border);
    }
}