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
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/10/8.
 */
public class MainApplication extends Application {
    public static int STARTING_X = 230;
    public static int STARTING_Y = 230;


    private File file;
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
        disPane.getChildren().addAll(border);
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
            } else {
                northAction = new Action(Action.MOVE_BLOCK, "north");
            }
            Action.processAction(northAction, worldMap);
            inventory.setText(builderInventory(worldMap));
            //TODO:卧槽，里面catch异常了，在这怎么输出错误信息??????(下同)
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
            } else {
                eastAction = new Action(Action.MOVE_BLOCK, "east");
            }
            Action.processAction(eastAction, worldMap);
            inventory.setText(builderInventory(worldMap));
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
            } else {
                southAction = new Action(Action.MOVE_BLOCK, "south");
            }
            Action.processAction(southAction, worldMap);
            inventory.setText(builderInventory(worldMap));
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
            } else {
                westAction = new Action(Action.MOVE_BLOCK, "west");
            }
            Action.processAction(westAction, worldMap);
            inventory.setText(builderInventory(worldMap));
        });

        /* dig button */
        Button digButton = new Button("Dig");
        digButton.setStyle("-fx-base: #336699");
        digButton.setTextFill(Color.WHITE);
        digButton.setOnAction(event -> {
            Action digAction = new Action(Action.DIG, "");
            Action.processAction(digAction, worldMap);
            inventory.setText(builderInventory(worldMap));
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
            Action.processAction(dropAction, worldMap);
            inventory.setText(builderInventory(worldMap));
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
            file = new FileChooser().showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    worldMap = new WorldMap(file.getPath());
                    setDisable(false, disPane, dirPane);
                    inventoryText.setText("Builder Inventory:");
                    inventory.setText(builderInventory(worldMap));
                    displayMap(worldMap, disPane);
                    alertInformation("map was successfully loaded");
                } catch (WorldMapFormatException | WorldMapInconsistentException | FileNotFoundException e) {
                    alertError(e.getMessage());
                } finally {
                    file = null;
                }
            }
        });
        MenuItem saveMenuItem = new MenuItem("Save World Map");
        saveMenuItem.setOnAction(event -> {
            if (worldMap != null) {
                file = new FileChooser().showSaveDialog(primaryStage);
                if (file != null) {
                    try {
                        worldMap.saveMap(file.getPath());
                        alertInformation("map was successfully saved");
                    } catch (IOException e) {
                        alertError(e.getMessage());
                    }
                }
            } else {
                alertError("no file");
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

    /**
     * 中心点 黄点 是 worldMap的StartPosition
     * 从那个点开始画图
     * 这个点永远不动
     *
     * @param worldMap
     * @param disPane
     */
    private void displayMap(WorldMap worldMap, BorderPane disPane) {
//        int STARTING_X = 230 - worldMap.getStartPosition().getX() * 50;
//        int STARTING_Y = 230 - worldMap.getStartPosition().getY() * 50;
        // TODO: 你看看是改这里面的XY还是改StartPosition里的XY然后去放到worldMap构造器
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
                    }
                }
            }
        }
        for (Map.Entry<Tile, Position> entry : tilePosition.entrySet()) {
            Tile tile = entry.getKey();
            Position position = entry.getValue();
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
                e.printStackTrace();
            }
            //TODO:怎么在矩形加文字和三角，block个数为tile.getBlocks().size(),出口根据 tile.getExits()画，builder初始化其实坐标块
            disPane.getChildren().add(rectangle);
        }
        Circle builder = new Circle(255, 255, 5, Color.YELLOW);
        builder.setStroke(Color.BLACK);
        disPane.getChildren().add(builder);
    }
}
