/**
 * Alice Zhang
 * CS231: Project 10  Vertex.java
 * 11/27/18
 */
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Comparator;

enum Direction{
    NORTH, EAST, SOUTH, WEST;
}

public class Vertex extends Agent implements Comparable<Vertex> {

    private int cost;
    private boolean marked;
    private Vertex[] neighbors;
    private boolean visible;

    public Vertex(int x0, int y0){
        super(x0, y0);
        cost = 0;
        marked = false;
        neighbors = new Vertex[4];
        visible = false;
    }

    /**
     * returns the compass opposite of a direction
     */
    public static Direction opposite(Direction d){
        if (d.ordinal() == Direction.NORTH.ordinal())
            return Direction.SOUTH;
        if (d.ordinal() == Direction.EAST.ordinal())
            return Direction.WEST;
        if (d.ordinal() == Direction.SOUTH.ordinal())
            return Direction.NORTH;
        else 
            return Direction.EAST;
    }

    /**
     * adds Vertex other to the current Vertex's neighbors list (builds the connection)
     */
    public void connect(Vertex other, Direction d){
        neighbors[d.ordinal()] = other;
    }

    /**
     * returns the Vertex at the Direction d
     */
    public Vertex getNeighbor(Direction d){
        return neighbors[d.ordinal()];
    }

    /**
     * returns an ArrayList of all the neighbor vertices
     */
    public ArrayList<Vertex> getNeighbors(){
        ArrayList<Vertex> nb = new ArrayList<>();
        for (Vertex v: neighbors){
            if (v != null) 
                nb.add(v);
        }
        return nb;
    }

    /**
     * sets the field cost to the specified integer value
     */
    public void setCost(int cost){
        this.cost = cost;
    }

    /**
     * returns the cost field of this vertex
     */
    public int getCost(){
        return cost;
    }

    /**
     * sets the field marked to the specified boolean value
     */
    public void setMarked(boolean marked){
        this.marked = marked;
    }

    /**
     * returns if the vertex is marked or not as a boolean value
     */
    public boolean getMarked(){
        return marked;
    }

    /**
     * sets the field visible to the specific boolean value
     */
    public void setVisible(boolean visible){
        this.visible = visible;
    }

    /**
     * returns a string that reprents the status of this vertex
     */
    public String toString(){
        if (marked==true)
            return "number of neighbors: " + this.getNeighbors().size() + "\n" + "cost: " + cost + "\n" + "marked";
        else
            return "number of neighbors: " + this.getNeighbors().size() + "\n" + "cost: " + cost + "\n" + "not marketed";
    }

    /**
     * draws the room at this vertex with possible exits
     */
    public void draw(Graphics g, int scale){
        if (!this.visible)
            return;
        
        int xpos = getX() + 2;
        int ypos = getY() + 2;
        int half = scale/2;
        int eighth = scale/8;
        int sixteenth = scale/16;
        
        if (this.cost <= 2)
            //the wumpus is nearby (two rooms away or closer): draw in red
            g.setColor(Color.red);
        else
            //the wumpus is not nearby: draw in black
            g.setColor(Color.black);
        g.drawRect(xpos, ypos, scale-2, scale-2);

        g.setColor(Color.black);
        
        //draws the possible exits
        if (neighbors[0]!=null)  //north exit
            g.fillRect(xpos + half - sixteenth, ypos, eighth, eighth + sixteenth);
        if (neighbors[2]!=null)  //south exit
            g.fillRect(xpos + half - sixteenth, ypos + scale - (eighth + sixteenth), eighth, eighth + sixteenth);
        if (neighbors[3]!=null)  //west exit
            g.fillRect(xpos, ypos + half - sixteenth, eighth + sixteenth, eighth);
        if (neighbors[1]!=null)  //east exit
            g.fillRect(xpos + scale - (eighth + sixteenth), ypos + half - sixteenth, eighth + sixteenth, eighth);
    }

    //compares the vertex with vertex v by their cost
    public int compareTo(Vertex v){
        if (this.getCost() < v.getCost())
            return -1;
        if (this.getCost() > v.getCost())
            return 1;
        else
            return 0;
    }

    public void updateState(Landscape scape){}
    public void draw(Graphics g){}

    //test codes
    public static void main(String[] args){
        Vertex v0 =  new Vertex(100, 100);
        Vertex v1 = new Vertex(100, 100);
        v0.connect(v1, Direction.EAST);
        v0.setCost(10);
        v1.setMarked(true);
        System.out.println(v0);
        System.out.println(v1);
    }

}



/**
 * Comparator class for Vertex;
 * the compare method compares two vertices by their cost fields;
 * returns -1 if the first one's cost is smaller than the second one's cost;
 * returns 1 if the first one's cost is bigger than the second one's cost;
 * returns 0 if the two vertices' cost are equal;
 */
class VertexComparator implements Comparator<Vertex>{
    public int compare( Vertex v1, Vertex v2 ) {
        if (v1.getCost() < v2.getCost())
            return -1;
        if (v1.getCost() > v2.getCost())
            return 1;
        else
            return 0;
    }
}