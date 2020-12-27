package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * This file relates to a Javanotes example. It animates red disks
 * bouncing within a canvas. The canvas size restricts the space within
 * which the discs can move.
 */
public class BPAnimation extends Application {

    public static void main(String[] args) {
        launch(args);
    }
  
    private GraphicsContext g;
    private Canvas canvas;
    private BouncingDisks[] disks;

    public void start(Stage stage) {
        
    	/* Creates canvas and denotes initial size. */
        canvas = new Canvas(500,500);
        g = canvas.getGraphicsContext2D();
        
       /* Creates disks and denotes how many. */
        disks = new BouncingDisks[15];
        for (int i = 0; i < 15; i++)
            disks[i] = new BouncingDisks();
        
        /* Clicking or dragging mouse on canvas changes velocity 
         * of disks towards cursor. */
        
        canvas.setOnMousePressed( e -> {
            double x = e.getX();
            double y = e.getY();
            for (BouncingDisks b : disks)
                b.headTowards(x,y);
        });
        canvas.setOnMouseDragged( e -> {
            double x = e.getX();
            double y = e.getY();
            for (BouncingDisks b : disks)
                b.headTowards(x,y);
        });
        
        Pane root = new Pane(canvas);
        stage.setScene( new Scene(root) );
        stage.setTitle("Resizable Canvas Demo");
        stage.setMinWidth(60);
        stage.setMinHeight(100);
        stage.show();
        
        
        /* Binds canvas to pane, keeping them the same size. */
        canvas.widthProperty().bind(root.widthProperty()); 
        canvas.heightProperty().bind(root.heightProperty());
        
        
        /* Timer updates positions and redraws canvas. */
        
        AnimationTimer timer = new AnimationTimer() {
            long previousTime = 0;
            public void handle(long time) {
     
                if (previousTime > 0) {
                    double width = canvas.getWidth();
                    double height = canvas.getHeight();
                    for (BouncingDisks b: disks) {
                        b.move(width,height,(time - previousTime)/1.0e9);
                    }
                }
                redraw();
                previousTime = time;
            }
        };
        timer.start();

    }
    
    /**
     * Draws canvas and disks in a frame.
     */
    private void redraw() { 
        double width = canvas.getWidth();
        double height = canvas.getHeight();
        g.setFill(Color.WHITE);
        g.fillRect(0,0,width,height);
        g.setFill(Color.RED);
        for (BouncingDisks b: disks) {
            g.fillOval(b.x-b.radius, b.y-b.radius, 2*b.radius, 2*b.radius);
        }
    }

    /**
     * Represents a red disk bouncing within the canvas. Constructor
     * creates disk with radius 10 and centre at the centre of the canvas.  
     * Disk has random velocity between 250 and 750 pps. 
     * */
    private class BouncingDisks {
        double x,y;      
        double radius;   
        double dx,dy;    
        BouncingDisks() {
            x = canvas.getWidth()/2;
            y = canvas.getHeight()/2;
            this.radius = 10;
            double velocity = 250 + 500*Math.random();
            double angle = 2 * Math.PI * Math.random();
            dx = velocity*Math.cos(angle);
            dy = velocity*Math.sin(angle);
        }
        void move(double canvasWidth, double canvasHeight, double elapsedTimeInSeconds) {
           
        	/* Move disk equal to velocity*(time elapsed since last frame).
            * Disk is kept within canvas.*/
            double w = canvasWidth;
            double h = canvasHeight;
            x += dx * elapsedTimeInSeconds;
            y += dy * elapsedTimeInSeconds;
            if (x < radius) { 
                    // bounce off left edge
                x = 2*radius - x;
                dx = Math.abs(dx);
            }
            else if (x - dx < w - radius && x > w - radius) { 
                    // bounce off right edge
                x = 2 * (w - radius) - x;
                dx = -Math.abs(dx);
            }
            else if (x > w - radius) { 
                    // Disk outside edge.
                dx = -Math.abs(dx);  // head back towards canvas.
            }
            if (y < radius) { 
                y = 2*radius - y;
                dy = Math.abs(dy);
            }
            else if (y - dy < h - radius && y > h - radius) { 
                y = 2 * (h - radius) - y;
                dy = -Math.abs(dy);
            }
            else if (y > h - radius) { 
                dy = -Math.abs(dy);
            }
        }
        void headTowards( double a, double b ) {
                // Reset direction of disk.
            if (Math.abs(a-x) < 1e-6 && Math.abs(b-y) < 1e-6)
                return;
            double velocity = Math.sqrt(dx*dx + dy*dy);
            double vecx = a - x;
            double vecy = b - y;
            double length = Math.sqrt(vecx*vecx + vecy*vecy);
            double dirx = vecx/length; // unit vector in direction from (x,y) to (a,b)
            double diry = vecy/length;
            dx = velocity * dirx;
            dy = velocity * diry;
        }
    }
    
} // end class BPAnimation