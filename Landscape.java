/*Alice Zhang
* CS 231
* Project 10 Landscape.java
* 12/10/18
*/

import java.awt.Graphics;
import java.util.ArrayList;
import java.lang.Math;

public class Landscape{
    private int width;
    private int height;
    private LinkedList<Agent> foregrounds;
    private LinkedList<Agent> backgrounds;

    /**
     * constructor that sets the width and height and initializes two linked list of agents
     */
    public Landscape(int w, int h){
        width = w;
        height = h;
        foregrounds = new LinkedList<Agent>();
        backgrounds = new LinkedList<Agent>();
    }

    /**
     * returns the height
     */
    public int getHeight(){
        return height;
    }

    /**
     * returns the width
     */
    public int getWidth(){
        return width;
    }
    
    /**
     * adds Agent a to the list of background agents
     */
    public void addBkgAgent(Agent a){
        backgrounds.addFirst(a);
    }

    /**
     * adds Agent a to the list of foreground agents
     */
    public void addFRGAgent(Agent a){
        foregrounds.addFirst(a);
    }

    /**
     * returns the linked list of background agents
     */
    public LinkedList<Agent> bka(){
        return backgrounds;
    }

/**
 * calls the drarw method of all the agents on the Landscape
 */
    public void draw(Graphics g, int scale){
        for (Agent a: backgrounds)
            a.draw(g, scale);
        for (Agent a: foregrounds)
            a.draw(g, scale);
    }

    public static void main(String[] args){}
    
}