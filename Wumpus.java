/**
 * Alice Zhang
 * CS231: Project 10   Wumpus.java
 * 12/03/18
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Wumpus extends Agent{

    private Vertex home;
    private boolean status;  //true: wumpus pose; false: wumpus dead pose
    private BufferedImage wumpusLose;
    private BufferedImage wumpus;
    private boolean visible;

    public Wumpus(Vertex vertex){
        super(vertex.getX(), vertex.getY());
        home = vertex;
        home.setVisible(false);   //initially not visible
        status = true;    //wumpus pose
        visible = false;  //intially not visible
        
        try{
            wumpus = ImageIO.read(new File("wumpus.png"));
            wumpusLose = ImageIO.read(new File("deadWumpus.png"));
        }
        catch (IOException e) {
			System.out.print("Unable to load image");
		}
    }

    /**
     * returns the location Vertex of the wumpus
     */
    public Vertex getLocation(){
        return home;
    }

    /**
     * returns the boolean status of the wumpus
     */
    public boolean getStatus(){
        return status;
    }

    /**
     * sets the status field to the boolean value passed in from the parameter
     */
    public void setStatus(boolean s){
        status = s;
    }

    /**
     * sets the visible field to the booleab value passed in from the parameter
     */
    public void setVisible(boolean visible){
        this.visible = visible;
    }

    /**
     * draws the wumpus depending on the visibility and stautus of the wumpus
     */
    public void draw(Graphics g, int scale){
        if (!visible)
            return;

        home.draw(g, 64);
        
        //if the status is "wumpus pose"
        if (status == true){
            g.drawImage(wumpus, home.getX()+6, home.getY()+6, 54, 54, null);
        }

        //if the status is "wumpus dead pose"
        else{
            g.drawImage(wumpusLose, home.getX()+6, home.getY()+6, 54, 54, null);
        }
    }

    public void updateState(Landscape scape){}

    public static void main(String[] args){}
}