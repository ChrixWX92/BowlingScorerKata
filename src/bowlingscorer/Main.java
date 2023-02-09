package bowlingscorer;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {

//    public static void main (String[] args) throws Exception {
//
//    }

    @Override
    public void start(Stage stage) {

        System.out.println("running");

        Game bowlingGame = new Game(10);

        GUI gui = new GUI(bowlingGame);

//        while (true) {
//            if (bowlingGame.play()) {
//                System.out.print("GAME OVER \n \n");
//                break;
//            }
//        }

//        int count = 1;
//        for (Frame frame : bowlingGame.getFrames()) {
//            System.out.printf("FRAME: %d | TYPE: %s | BALLS: %s || TOTAL: %d%n",
//                    count,
//                    frame.getType(),
//                    Arrays.toString(frame.getBalls()),
//                    frame.getValue());
//            count++;
//        }

    }

}
