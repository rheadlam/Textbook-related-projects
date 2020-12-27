package application;

import javafx.scene.control.Label;

/**
 * This file relates to a Javanotes example. It contains
 * a custom component acting as a simple stop-watch. User clicks
 * on it twice, to start and to stop, time between clicks is shown.
 * A third click starts a new timer. 
 */
public class SWLabel extends Label {

    private long startTime;   
    private boolean running; 

    /**
     * Constructor sets initial text on the label and sets up a mouse 
     * event handler.
     */
    public SWLabel() {
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
            setText("Click for elapsed time.");
        }
        else {
                // Stops timer, computing and displaying elapsed time.
            long endTime = System.currentTimeMillis();
            double seconds = (endTime - startTime) / 1000.0;
            setText( String.format("%1.3f seconds", seconds) );
        }
    }

} // end SWLabel