/**
 * Alice Zhang
 * CS231: Project 10   Hunter.java
 * 12/03/18
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

enum Status{
    NORMAL, ARMED, DEAD
}

public class Hunter extends Agent{
    
    private Vertex currLocation;
    private Status status;  //represents the status of the hunter: normal, armed, or dead
    
    private BufferedImage hunter;
    private BufferedImage armedHunter;
    private BufferedImage deadHunter;

    public Hunter(Vertex vertex){
        super(vertex.getX(), vertex.getY());
        currLocation = vertex;
        currLocation.setVisible(true); //always visible

        status = Status.NORMAL;

        try {
            hunter = ImageIO.read(new File("hunter.png"));
            armedHunter = ImageIO.read(new File("armedHunter.png"));
            deadHunter = ImageIO.read(new File("dead.png"));
        } 
        catch (IOException e) {
			System.out.print("Unable to load image");
		}
    }

    /**
     * returns the current Vertex location of the Hunter
     */
    public Vertex getCurrLocation(){
        return currLocation;
    }

    /**
     * sets the Hunter's current Vertex locattion to the specific vertex
     */
    public void setCurrLocation(Vertex v){
        currLocation = v;
    }

    /**
     * returns the Hunter's status
     */
    public Status getStatus(){
        return status;
    }

    /**
     * sets the Hunter's status
     */
    public void setStatus(Status s){
        status = s;
    }

    /** 
     * draws the Hunter depending on what status the Hunter is in
     */
    public void draw(Graphics g, int scale){
        currLocation.draw(g, scale);
        if (status == Status.NORMAL)
            g.drawImage(hunter, currLocation.getX()+6, currLocation.getY()+6, 54, 54, null);
        if (status == Status.ARMED)
            g.drawImage(armedHunter, currLocation.getX()+6, currLocation.getY()+6, 54, 54, null);
        if (status == Status.DEAD)
            g.drawImage(deadHunter, currLocation.getX()+6, currLocation.getY()+6, 54, 54, null);
    }

    public void updateState(Landscape scape){}

    public static void main(String[] args){}
}