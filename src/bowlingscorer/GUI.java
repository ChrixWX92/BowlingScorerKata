package bowlingscorer;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class GUI {

    Game game;
    int frameCount;
    private static List<GridPane> frames;

    public GUI(Game game) {

        this.game = game;
        this.frameCount = game.getFrames().length;
        frames = new ArrayList<>();
        this.init(new Stage());

    }

    public void init(Stage stage) {

        int framePaneCount = frameCount;
        while (framePaneCount > 0) {
            frames.add(addFramePane());
            framePaneCount--;
        }

        stage.setTitle("Bowling Scorer");
        stage.setWidth(1200);
        stage.setWidth(1200);
        Button button = new Button("Bowl Random");
        Button button2 = new Button("Bowl Set Amount:");
        Button button3 = new Button("Reset bowlingscorer.Game");
        TextField scoreInput = new TextField();

        scoreInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                scoreInput.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

//        text.setY(400);


        // Simple interface
        BorderPane borderPane = new BorderPane();
        HBox buttons = new HBox(button, button2, scoreInput, button3);
        HBox frames = new HBox(this.frames.toArray(new GridPane[0]));
        borderPane.setBottom(buttons);
        borderPane.setTop(frames);

        final int[] frameCounter = {0};
        final int[] ballCounter = {0};
        button.setOnAction(new EventHandler() {

            @Override
            public void handle(Event event) {

                game.play();
                Frame[] threadFrames = game.getFrames();
                Frame currentFrame = threadFrames[frameCounter[0]];

                System.out.println(String.valueOf(currentFrame.getBalls()[ballCounter[0]]));
                String text = "";
                if (frameCounter[0] < threadFrames.length) text = String.valueOf(currentFrame.getBalls()[ballCounter[0]]);

                switch (text) {
                    case "0" -> {
                        text = "-";
                    }
                    case "10" -> {
                        text = "X";
                    }
                    case "null" -> {
                        text = " ";

                    }
                    default -> {
                        if (currentFrame.sumBalls() == 10 && currentFrame.getType() != Frame.FrameType.FINAL) text = "/";
                    }
                }

                if (frameCounter[0] == frameCount - 1) {
                    System.out.println("FINAL");
                    // FINAL FRAME
                    switch (ballCounter[0]) {
                        case 0 -> ((Label) GUI.frames.get(frameCounter[0]).getChildren().get(2)).setText(text);
                        case 1 -> ((Label) GUI.frames.get(frameCounter[0]).getChildren().get(0)).setText(text);
                        case 2 -> {
                            if (currentFrame.getBalls()[2] != null){
                                ((Label) GUI.frames.get(frameCounter[0]).getChildren().get(1)).setText(text);
                            }
                            else {
                                ((Label) GUI.frames.get(frameCounter[0]).getChildren().get(1)).setText("-");
                            }
                        }
                    }
                    ballCounter[0]++;
                }
                else {
                    ((Label) GUI.frames.get((frameCounter[0])).getChildren().get(ballCounter[0])).setText(text);
                    if (ballCounter[0] == 0) ballCounter[0]++;
                    else {
                        ((Label) GUI.frames.get((frameCounter[0])).getChildren().get(3)).setText(String.valueOf(game.getTotal()));
                        ballCounter[0] = 0;
                        frameCounter[0]++;

                    }
                }

                for (int i = 0 ; i < frameCounter[0] ; i++) {
                    if (Integer.parseInt(((Label) GUI.frames.get((frameCounter[0])).getChildren().get(3)).getText()) != game.calculateSetFrameTotal(i)) {
                        ((Label) GUI.frames.get((frameCounter[0])).getChildren().get(3)).setText(String.valueOf(game.calculateSetFrameTotal(i)));
                    }
                }

            }


        });

        button2.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
//                try {
//                    mapStart(); // If we want to run GeoTools
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
            }
        });

        button3.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
//                try {
//                    setupServer();
//                    server[0] = true;
//                    if (client[0]) {
//                        button5.setDisable(false);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            }
        });


        Scene primaryScene = new Scene(borderPane, 600, 275);
        stage.setScene(primaryScene);
        stage.show();
    }

    public GridPane addFramePane() {
        GridPane grid = new GridPane();

        Border standardBorder = new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN));
//        grid.setHgap(10);
//        grid.setVgap(10);
        grid.setBorder(new Border(new BorderStroke(Paint.valueOf("Black"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderStroke.THIN)));

        Label ball1 = new Label();
        ball1.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        ball1.setBorder(standardBorder);
        ball1.setMinSize(35,35);
        grid.add(ball1, 1, 0);

        Label ball2 = new Label();
        ball2.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        ball2.setBorder(standardBorder);
        ball2.setMinSize(35,35);
        grid.add(ball2, 2, 0);

        Label ball3 = new Label();
        ball3.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        ball3.setMinSize(35,35);
//        ball3.setBorder(standardBorder);
        grid.add(ball3, 0, 0);

//        // Title in column 3, row 1
//        Text chartTitle = new Text("Current Year");
//        chartTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
//        grid.add(chartTitle, 2, 0);
//
//        // Subtitle in columns 2-3, row 2
//        Text chartSubtitle = new Text("Goods and Services");
//        grid.add(chartSubtitle, 1, 1, 2, 1);

        // Total box
        Label total = new Label();
        GridPane.setValignment(total, VPos.BOTTOM);
        total.setFont(Font.font("Arial", FontWeight.BOLD, 25));
        //TODO: Font alignment
        total.setMinSize(0,35);
        grid.add(total, 0, 2);


        return grid;
    }


}

