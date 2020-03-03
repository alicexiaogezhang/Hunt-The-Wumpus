/**
 * Alice Zhang
 * CS231: Project 10  Graph.java
 * 11/27/18
 */
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Graph{
    private ArrayList<Vertex> vertices;

    public Graph(){
        vertices = new ArrayList<>();
    }

    /**
     * returns the number of vertices in the graph
     */
    public int vertexCount(){
        return vertices.size();
    }

    /**
     * adds vertices v1 and v2 to the graph and adds edges that connects v1 and v2 in both directions
     */
    public void addEdge(Vertex v1, Direction d, Vertex v2){
        if (vertices.contains(v1)==false)
            vertices.add(v1);
        if (vertices.contains(v2)==false)
            vertices.add(v2);
        v2.connect(v1, d);
        v1.connect(v2, Vertex.opposite(d)); 
    }

    /**
     * finds the shortest paths from v0 to all other vertices (Dijkstra's algorithm)
     */
    public void shortestPath(Vertex v0){
        for (Vertex v: vertices){
            v.setMarked(false);
            v.setCost(1000000);
        }
        PriorityQueue<Vertex> q = new PriorityQueue<>(vertices.size(), new VertexComparator());
        v0.setCost(0);
        q.add(v0); 
        while (q.isEmpty()==false){
            Vertex v = q.remove();
            v.setMarked(true);
            for (Vertex w: v.getNeighbors()){
                if (w.getMarked()==false && v.getCost()+1 < w.getCost()){
                    w.setCost(v.getCost()+1);
                    q.remove(w);
                    q.add(w);
                }
            }
        }
        // for (Vertex vertex: vertices)
        //     System.out.println(vertex);
    }

    //test codes
    public static void main(String[] args){
        Graph g = new Graph();
        Vertex v0 = new Vertex(100, 100);
        Vertex v1 = new Vertex(100, 110);
        Vertex v2 = new Vertex(90, 110);
        Vertex v3 = new Vertex(100, 120);
        Vertex v4 = new Vertex(110, 120);
        g.addEdge(v0, Direction.SOUTH, v1);
        g.addEdge(v1, Direction.WEST, v2);
        g.addEdge(v1, Direction.SOUTH, v3);
        g.addEdge(v3, Direction.EAST, v4);
        g.shortestPath(v0);
    }


}