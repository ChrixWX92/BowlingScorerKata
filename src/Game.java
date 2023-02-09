import java.util.concurrent.ThreadLocalRandom;

public class Game {

    private final Frame[] frames;
    private int frameNumber;
    private int total;
    //TODO: Add to running total

    public Game(int numberOfFrames) {
        this.frames = new Frame[numberOfFrames];
        newFrame();
        this.frameNumber = 0;
    }

    public void newFrame() {
        for (int i = 0 ; i < this.frames.length ; i++) {
            if (this.frames[i] == null) {
                if (i == this.frames.length - 1) {
                    this.frames[i] = new Frame(Frame.FrameType.FINAL);
                }
                else {
                    this.frames[i] = new Frame();
                }
                return;
            }
        }
    }

    public boolean play() {

        // If a free slot is available for a frame, we create one
        if (this.frames[frameNumber] == null) newFrame();

        if (this.frames[frameNumber].getType() == Frame.FrameType.FINAL) {return playFinalFrame();}

        // If the current frame is open, we can resolve a previous spare after the first ball and a previous strike
        // after the second
        int playerScore = ThreadLocalRandom.current().nextInt(0, frames[frameNumber].getPins() + 1);
        if (this.frames[frameNumber].addBall(playerScore)) {
            // Calculating possible strike(s)
            if (frameNumber > 0) {
                if (this.frames[frameNumber] != null) {
                    if (this.frames[frameNumber - 1].getType() != Frame.FrameType.OPEN) {
                        if (this.frames[frameNumber - 1].getType() == Frame.FrameType.STRIKE) {
                            calculateStrikes();
                        }
                    }
                }
            }

            this.frameNumber++;

        }

        // Calculating possible spare
        if (frameNumber > 0) {
            if (this.frames[frameNumber] != null) {
                if (this.frames[frameNumber - 1].getType() != Frame.FrameType.OPEN) {
                    if (this.frames[frameNumber - 1].getType() == Frame.FrameType.SPARE) {
                        calculateSpare();
                    }
                }
            }
        }

        return false;

    }

    private void calculateStrikes() {
        // Strike
        if (this.frames[frameNumber].getBalls()[1] != null) {
            this.frames[frameNumber - 1].setValue(10 + this.frames[frameNumber].getBalls()[0] + this.frames[frameNumber].getBalls()[1]);
            // Double
            if (frameNumber > 1 && this.frames[frameNumber - 2] != null && this.frames[frameNumber - 2].getType() == Frame.FrameType.STRIKE) {
                this.frames[frameNumber - 2].setValue(20);
                // Turkey+
                if (frameNumber > 2 && this.frames[frameNumber - 3] != null && this.frames[frameNumber - 3].getType() == Frame.FrameType.STRIKE) {
                    this.frames[frameNumber - 3].setValue(30);
                }
            }
        }
    }

    private void checkFrame() {

    }

    private boolean playFinalFrame() {

        boolean thirdFrame = false;
        int playerScore = ThreadLocalRandom.current().nextInt(0, frames[frameNumber].getPins() + 1);

        if (this.frames[frameNumber].getBalls()[0] == null) {
            this.frames[frameNumber].addBall(playerScore);
            if (this.frames[frameNumber - 1].getType() == Frame.FrameType.SPARE) {
                calculateSpare();
            }
        }
        else {
            if (this.frames[frameNumber].getBalls()[0] == 10) thirdFrame = true;
            if (this.frames[frameNumber].getBalls()[1] == null) {
                this.frames[frameNumber].addBall(playerScore);
                if (this.frames[frameNumber - 1].getType() == Frame.FrameType.STRIKE) {
                    calculateStrikes();
                }
            } else {
                if (this.frames[frameNumber].sumBalls() >= 10) thirdFrame = true;
                if (thirdFrame) {
                    frames[frameNumber].setPins(10);
                    frames[frameNumber].addBall(ThreadLocalRandom.current().nextInt(0, frames[frameNumber].getPins() + 1));
                }
                // Game is over
                return true;
            }
        }

        return false;

    }

    private void calculateSpare() {
        if (this.frames[frameNumber-1].getType() == Frame.FrameType.SPARE) {
            this.frames[frameNumber-1].setValue(10 + this.frames[frameNumber].getBalls()[0]);
        }
    }


    public Frame[] getFrames() {
        return frames;
    }

}
