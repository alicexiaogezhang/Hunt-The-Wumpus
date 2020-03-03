/*Alice Zhang
* CS 231
* Project 5 Agent.java
* 10/20/18
*/

import java.awt.Graphics;

public class Agent{
    int x;
    int y;
    
    /**
     * a constructor that sets the position of the agent
     */
    public Agent(int x0, int y0){
        x = x0;
        y = y0;
    }

    /**
     * returns the x position
     */
    public int getX(){
        return x;
    }

    /**
     * returns the y position
     */
    public int getY(){
        return y;
    }

    /**
     * sets the x position to newX
     */
    public void setX(int newX){
        x = newX;
    }

    /**
     * sets the y position to newY
     */
    public void setY(int newY){
        y = newY;
    }

    /**
     * returns a string that contains the x and y positions
     */
    public String toString(){
        return "( " + x + "," + y + " )";
    }

    public void updateState(Landscape scape){

    }

    public void draw(Graphics g, int scale){

    }

    public int getCategory(){
        return 0;
    }

    /**
     * test codes
     */
    public static void main(String[]args){
        Agent agent = new Agent(0, 0);
        System.out.println(agent);
        agent.setX(1);
        agent.setY(2);
        System.out.println(agent);
    }

}