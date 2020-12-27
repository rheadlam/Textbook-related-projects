package application;

import javafx.scene.control.Label;
import javafx.animation.AnimationTimer;

/**
 * This file relates to a Javanotes exercise. It contains
 * a custom component acting as a simple stop-watch. User clicks
 * on it twice, to start and to stop, time between clicks is shown.
 * A third click starts a new timer. Between clicks time elapsing is
 * displayed.
 */
public class SWLabelAnimated extends Label {

    private long startTime;   
    private boolean running; 
    private AnimationTimer timer;  // Used to update timer to show elapsed time
                                   // between clicks.
                                   
    /**
     * Constructor sets initial text on the label and sets up a mouse 
     * event handler.
     */
    public SWLabelAnimated() {
        super("  Click this to start timer.  ");
        setOnMousePressed( e -> setRunning( !running ) );
    }


    /**
     * Returns boolean relating to if timer is running.
     */
    public boolean isRunning() {
        return running;
    }


    /**
     * Runs or stops timer and displays new text on the label.
     * @param running controls if timer should be running or not.
     */
    public void setRunning( boolean running ) {
        if (this.running == running)
            return;
        this.running = running;
        if (running == true) {
        	 // Records current time and starts timer.
            startTime = System.currentTimeMillis();
            if (timer == null) {
                timer = new AnimationTimer() {
                    public void handle(long now) {
                        long elapsedTime = System.currentTimeMillis() - startTime;
                        String text = String.format(
                                 "%3.1f seconds", elapsedTime/1000.0);
                        setText(text);
                    }
                };
            }
            setText("   0.0 seconds elapsed");
            timer.start();
        }
        else {
        	// Stops timer, computing and displaying elapsed time.
            running = false;
            timer.stop();
            long endTime = System.currentTimeMillis();
            double seconds = (endTime - startTime) / 1000.0;
            setText( String.format("Time elapsed: %1.3f secs", seconds) );
        }
    }

} // end SWLabelAnimated
