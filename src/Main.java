import java.util.Arrays;

public class Main {

    public static void main (String[] args) {

        Game bowlingGame = new Game(10);

        while (true) {
            if (bowlingGame.play()) {
                System.out.print("GAME OVER \n \n");
                break;
            }
        }

        int count = 1;
        for (Frame frame : bowlingGame.getFrames()) {
            System.out.printf("FRAME: %d | TYPE: %s | BALLS: %s || TOTAL: %d%n",
                    count,
                    frame.getType(),
                    Arrays.toString(frame.getBalls()),
                    frame.getValue());
            count++;
        }

    }

}
