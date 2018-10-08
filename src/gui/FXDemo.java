package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/8.
 */
public class FXDemo extends Application {
    public static void main(String[] args) {
        launch(FXDemo.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();

        HBox hbox;
        {
            /* HBox */
            hbox = new HBox();
            hbox.setPadding(new Insets(15, 12, 15, 12));
            hbox.setSpacing(10);   // Gap between nodes
            hbox.setStyle("-fx-background-color: #336699;");

            Button buttonCurrent = new Button("Current");
            buttonCurrent.setPrefSize(100, 20);

            Button buttonProjected = new Button("Projected");
            buttonProjected.setPrefSize(100, 20);

            hbox.getChildren().addAll(buttonCurrent, buttonProjected);
        }
        border.setTop(hbox);

        VBox vbox;
        {
            /* VBox */
            vbox = new VBox();
            vbox.setPadding(new Insets(10)); // Set all sides to 10
            vbox.setSpacing(8);              // Gap between nodes

            Text title = new Text("Data");
            title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            vbox.getChildren().add(title);

            Hyperlink options[] = new Hyperlink[]{
                    new Hyperlink("Sales"),
                    new Hyperlink("Marketing"),
                    new Hyperlink("Distribution"),
                    new Hyperlink("Costs")};

            for (int i = 0; i < 4; i++) {
                // Add offset to left side to indent from title
                VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
                vbox.getChildren().add(options[i]);
            }
        }
        border.setLeft(vbox);

        {
            /* addStackPane */
            StackPane stack = new StackPane();
            Rectangle helpIcon = new Rectangle(30.0, 25.0);
            List<Stop> stopList = new LinkedList<>();
            stopList.add(new Stop(0, Color.web("#4977A3")));
            stopList.add(new Stop(.5, Color.web("#B0C6DA")));
            stopList.add(new Stop(1, Color.web("#9CB6CF")));
//        helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
//                new Stop[]{
//                        new Stop(0, Color.web("#4977A3")),
//                        new Stop(0.5, Color.web("#B0C6DA")),
//                        new Stop(1, Color.web("#9CB6CF")),}));
            helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stopList));
            helpIcon.setStroke(Color.web("#D0E6FA"));
            helpIcon.setArcHeight(3.5);
            helpIcon.setArcWidth(3.5);

            Text helpText = new Text("?");
            helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
            helpText.setFill(Color.WHITE);
            helpText.setStroke(Color.web("#7080A0"));

            stack.getChildren().addAll(helpIcon, helpText);
            stack.setAlignment(Pos.CENTER_RIGHT);
            // Add offset to right for question mark to compensate for RIGHT
            // alignment of all nodes
            StackPane.setMargin(helpText, new Insets(0, 10, 0, 0));

            hbox.getChildren().add(stack);
            HBox.setHgrow(stack, Priority.ALWAYS);
        }

//        addStackPane(hbox);         // Add stack to HBox in top region

        GridPane grid;
        {
            /* GridPane */
            grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(0, 10, 0, 10));

            // Category in column 2, row 1
            Text category = new Text("Sales:");
            category.setFont(Font.font("Arial", FontWeight.BOLD, 20));
            grid.add(category, 1, 0);
        }

        AnchorPane anchorpane;
        {
            /* AnchorPane */
            anchorpane = new AnchorPane();

            Button buttonSave = new Button("Save");
            Button buttonCancel = new Button("Cancel");

            HBox hb = new HBox();
            hb.setPadding(new Insets(0, 10, 10, 10));
            hb.setSpacing(10);
            hb.getChildren().addAll(buttonSave, buttonCancel);

            anchorpane.getChildren().addAll(grid, hb);
            // Anchor buttons to bottom right, anchor grid to top
            AnchorPane.setBottomAnchor(hb, 8.0);
            AnchorPane.setRightAnchor(hb, 5.0);
            AnchorPane.setTopAnchor(grid, 10.0);
        }
        border.setCenter(anchorpane);

        FlowPane flow;
        {
            /* FlowPane */
            flow = new FlowPane();
            flow.setPadding(new Insets(5, 0, 5, 0));
            flow.setVgap(4);
            flow.setHgap(4);
            flow.setPrefWrapLength(170); // preferred width allows for two columns
            flow.setStyle("-fx-background-color: DAE6F3;");
        }
        border.setRight(flow);

        Scene scene = new Scene(border);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Layout Sample");
        primaryStage.show();
    }

//    private void addStackPane(HBox hb) {
//
//        StackPane stack = new StackPane();
//        Rectangle helpIcon = new Rectangle(30.0, 25.0);
//        helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
//                new Stop[]{
//                        new Stop(0, Color.web("#4977A3")),
//                        new Stop(0.5, Color.web("#B0C6DA")),
//                        new Stop(1, Color.web("#9CB6CF")),}));
//        helpIcon.setStroke(Color.web("#D0E6FA"));
//        helpIcon.setArcHeight(3.5);
//        helpIcon.setArcWidth(3.5);
//
//        Text helpText = new Text("?");
//        helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
//        helpText.setFill(Color.WHITE);
//        helpText.setStroke(Color.web("#7080A0"));
//
//        stack.getChildren().addAll(helpIcon, helpText);
//        stack.setAlignment(Pos.CENTER_RIGHT);
//        // Add offset to right for question mark to compensate for RIGHT
//        // alignment of all nodes
//        StackPane.setMargin(helpText, new Insets(0, 10, 0, 0));
//
//        hb.getChildren().add(stack);
//        HBox.setHgrow(stack, Priority.ALWAYS);
//
//    }
}
