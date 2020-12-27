package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import java.util.Scanner;

/**
 * Shows StopWatchLabels. The elapsed time is displayed after clicking twice.
 */
public class SWLabelTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    //------------------------------------------------------------
    
    public void start(Stage stage) {
       
    	SWLabel stopWatch = new SWLabel();
        SWLabelAnimated stopWatch2 = new SWLabelAnimated();
        
        // Allows option for which timer to test.
        Scanner stopWatchType = new Scanner(System.in);
        System.out.println("Enter '1' to test original stopwatch,"
        		+ " enter anything else to test animated version:");
        String inputSWT = stopWatchType.nextLine();
        System.out.println(inputSWT);
        
        if(inputSWT.equals("1")) {
        
        //Tests non animated version.	
        stopWatch.setStyle("-fx-font: bold 30pt serif; -fx-background-color:#ffffee;"
                + "-fx-border-color:#008; -fx-border-width:3px; -fx-padding:20px;"
                + "-fx-text-fill: #008");
        stopWatch.setMaxSize(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
        stopWatch.setAlignment(Pos.CENTER);
        
        stage.setScene( new Scene(new StackPane(stopWatch)) );
        stage.setTitle("StopWatchLabel");
        stage.show(); } else {
        	
        	//Tests animated version.
        	 stopWatch2.setStyle("-fx-font: bold 30pt serif; -fx-background-color:#ffffee;"
                     + "-fx-border-color:#008; -fx-border-width:3px; -fx-padding:20px;"
                     + "-fx-text-fill: #008");
             stopWatch2.setMaxSize(Double.POSITIVE_INFINITY,Double.POSITIVE_INFINITY);
             stopWatch2.setAlignment(Pos.CENTER);
             
             stage.setScene( new Scene(new StackPane(stopWatch2)) );
             stage.setTitle("StopWatchLabel Animated");
             stage.show(); 
        }
        

       
    }
    
}