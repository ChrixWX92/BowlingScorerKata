package bowlingscorer;

public class Frame {

    private final Integer[] balls;
    private FrameType type;
    private int value;
    private int pins;

    public Frame() {
        this(null);
    }

    public Frame(FrameType type) {

        this.type = type;

        if (this.type != FrameType.FINAL) {
            this.balls = new Integer[2];
        } else {
            this.balls = new Integer[3];
        }

        this.pins = 10;

    }

    // Returns true if this is the last ball of the frame
    public boolean addBall(int score) {

        // TODO: No notification for a full array

        this.pins -= score;

        // Finding the earliest non-bowled ball to write to
        for (int i = 0 ; i < balls.length ; i++) {
            if (balls[i] == null) {
                balls[i] = score;
                if (i == 0 && this.type != FrameType.FINAL && score == 10) {
                    this.type = FrameType.STRIKE;
                    return true;
                }
                break;
            }
        }

        // If this was the last ball of the frame, perform calculation
        if (balls[balls.length-1] != null) {
            calculateFrame();
            return true;
        }

        // The final frame can update dynamically as ball results require no later context for calculation
        if (this.type == FrameType.FINAL) {
            this.value = sumBalls();
        }

        return false;

    }

    public void calculateFrame() {

        int totalScore = sumBalls();

        if (this.type == FrameType.FINAL) {
            this.value = sumBalls();
            return;
        }

        if (totalScore < 10) {
            this.type = FrameType.OPEN;
            this.value = totalScore;
        }

        if (totalScore == 10 && this.type != FrameType.STRIKE) {
            this.type = FrameType.SPARE;
        }

    }

    int sumBalls() {
        int sum = 0;
        for (Integer ball : this.balls) {
            if (ball != null)
                sum += ball;
        }
        return sum;
    }

    public Integer[] getBalls() {
        return balls;
    }

    public FrameType getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {this.value = value;}

    public int getPins() {
        return pins;
    }

    public void setPins(int pins) {
        this.pins = pins;
    }

    enum FrameType {
        OPEN,
        SPARE,
        STRIKE,
        FINAL
    }

}
