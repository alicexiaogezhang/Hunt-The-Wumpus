/*
** Alice Zhang
** CS231 Project 10: HuntTheWumpus.java
 */

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.Point;

import javax.imageio.ImageIO;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.event.MouseInputAdapter;

import java.util.*;

public class HuntTheWumpus {

    private Landscape scape;
    private LandscapeDisplay display;
    private Graph graph;
    private Hunter hunter;
    private Wumpus wumpus;

    enum PlayState { NORMAL, SHOOT, OVER, QUIT }
    private PlayState state;

    /**
     * constructor that initializes the fields and sets up a game
     */
    public HuntTheWumpus() {
        scape = new Landscape(512, 512);
        display = new LandscapeDisplay(scape, 64);
        
        //set up the graph
        graph = new Graph();
        //intiate a 8*8 grid of vertices
        Vertex[][] grid = new Vertex[8][8];
        for (int i = 0; i<8; i++){
            for (int j = 0; j<8; j++){
                grid[i][j] = new Vertex(i*64, j*64);
                scape.addBkgAgent(grid[i][j]);
            }
        }
        //add edges for the vertices to establish paths
        //horizontally
        for (int i = 0; i<8; i++){
            for (int j = 1; j<8; j++)
                graph.addEdge(grid[j-1][i], Direction.WEST, grid[j][i]);
        }
        //vetically
        for (int i = 0; i<8; i++){
            for(int j = 1; j<8; j++)
                graph.addEdge(grid[i][j-1], Direction.NORTH, grid[i][j]);
        }

        Random r = new Random();
        state = PlayState.NORMAL;
        hunter = new Hunter(grid[r.nextInt(8)][r.nextInt(8)]);
        scape.addFRGAgent(hunter);
        wumpus = new Wumpus(grid[r.nextInt(8)][r.nextInt(8)]);
        scape.addFRGAgent(wumpus);
        graph.shortestPath(wumpus.getLocation());

        Control control = new Control();
        display.win.addKeyListener(control);
        display.win.setFocusable(true);
        display.win.requestFocus();

        display.quit.addActionListener(control);
        display.reset.addActionListener(control);
    }

    /**
     * restarts a new game
     */
    public void reset(){
        display.win.dispose();
        scape = new Landscape(512, 512);
        display = new LandscapeDisplay(scape, 64);
        
        //set up the graph
        graph = new Graph();
        //intiate a 8*8 grid of vertices
        Vertex[][] grid = new Vertex[8][8];
        for (int i = 0; i<8; i++){
            for (int j = 0; j<8; j++){
                grid[i][j] = new Vertex(i*64, j*64);
                scape.addBkgAgent(grid[i][j]);
            }
        }
        //add edges for the vertices to establish paths
        for (int i = 0; i<8; i++){
            for (int j = 1; j<8; j++)
                graph.addEdge(grid[j-1][i], Direction.WEST, grid[j][i]);
        }
        for (int i = 0; i<8; i++){
            for(int j = 1; j<8; j++)
                graph.addEdge(grid[i][j-1], Direction.NORTH, grid[i][j]);
        }

        Random r = new Random();
        state = PlayState.NORMAL;
        hunter = new Hunter(grid[r.nextInt(8)][r.nextInt(8)]);
        scape.addFRGAgent(hunter);
        wumpus = new Wumpus(grid[r.nextInt(8)][r.nextInt(8)]);
        scape.addFRGAgent(wumpus);
        graph.shortestPath(wumpus.getLocation());

        Control control = new Control();
        display.win.addKeyListener(control);
        display.win.setFocusable(true);
        display.win.requestFocus();

        display.quit.addActionListener(control);
        display.reset.addActionListener(control);
    }

    /**
     * keyboard control class
     */
    private class Control extends KeyAdapter implements ActionListener {

        public void keyTyped(KeyEvent e) {
            System.out.println( "Key Pressed: " + e.getKeyChar() );
            
            if (state == PlayState.NORMAL){
                // w: hunter moves up
                if (("" + e.getKeyChar()).equalsIgnoreCase("w")){
                    Vertex northNeighbor = hunter.getCurrLocation().getNeighbor(Direction.NORTH);
                    if (northNeighbor != null){
                        northNeighbor.setVisible(true);
                        hunter.setCurrLocation(northNeighbor);
                    }
                }
                // s: hunter moves down
                else if (("" + e.getKeyChar()).equalsIgnoreCase("s")){
                    Vertex southNeighbor = hunter.getCurrLocation().getNeighbor(Direction.SOUTH);
                    if (southNeighbor != null){
                        southNeighbor.setVisible(true);
                        hunter.setCurrLocation(southNeighbor);
                    }
                }
                // a: hunter moves left
                else if (("" + e.getKeyChar()).equalsIgnoreCase("a")){
                    Vertex westNeighbor = hunter.getCurrLocation().getNeighbor(Direction.WEST);
                    if (westNeighbor != null){
                        westNeighbor.setVisible(true);
                        hunter.setCurrLocation(westNeighbor);
                    }
                }
                // d: hunter moves right
                else if (("" + e.getKeyChar()).equalsIgnoreCase("d")){
                    Vertex eastNeighbor = hunter.getCurrLocation().getNeighbor(Direction.EAST);
                    if (eastNeighbor != null){
                        eastNeighbor.setVisible(true);
                        hunter.setCurrLocation(eastNeighbor);
                    }
                }
                // space: arm the hunter
                if(("" + e.getKeyChar()).equalsIgnoreCase(" ")){
                    System.out.println("Ready to shoot");
                    hunter.setStatus(Status.ARMED);
                    state = PlayState.SHOOT;
                }
                // if the hunter run into the wumpus, the hunter is eaten and loses the game;
                if (hunter.getCurrLocation().equals(wumpus.getLocation())){
                    hunterLose();
                    state = PlayState.OVER;
                }
            }

            else if (state == PlayState.SHOOT){
                //shoot to north
                if (("" + e.getKeyChar()).equalsIgnoreCase("w")){
                    if(hunter.getCurrLocation().getNeighbor(Direction.NORTH).equals(wumpus.getLocation()))
                        hunterWin();
                    else{
                        System.out.println("You missed the shot!");
                        hunterLose();
                    }
                    state = PlayState.OVER;
                }
                //shoot to south
                else if (("" + e.getKeyChar()).equalsIgnoreCase("s")){
                    if(hunter.getCurrLocation().getNeighbor(Direction.SOUTH).equals(wumpus.getLocation()))
                        hunterWin();
                    else{
                        System.out.println("You missed the shot!");
                        hunterLose();
                    }
                    state = PlayState.OVER;
                }
                //shoot to west
                else if (("" + e.getKeyChar()).equalsIgnoreCase("a")){
                    if(hunter.getCurrLocation().getNeighbor(Direction.WEST).equals(wumpus.getLocation()))
                        hunterWin();
                    else{
                        System.out.println("You missed the shot!");
                        hunterLose();
                    }
                    state = PlayState.OVER;
                }
                //shoot to east
                else if (("" + e.getKeyChar()).equalsIgnoreCase("d")){
                    if(hunter.getCurrLocation().getNeighbor(Direction.EAST).equals(wumpus.getLocation()))
                        hunterWin();
                    else{
                        System.out.println("You missed the shot!");
                        hunterLose();
                    }
                    state = PlayState.OVER;
                }
                //hunter get unarmed
                if (("" + e.getKeyChar()).equalsIgnoreCase(" ")){
                    System.out.println("Hunter unarmed");
                    hunter.setStatus(Status.NORMAL);
                    state = PlayState.NORMAL;
                }
            }

            else if (state == PlayState.OVER){
                return;
            }

        }

        /**
         * hunter loses
         */
        public void hunterLose(){
            wumpus.setStatus(true);
            wumpus.setVisible(true);
            hunter.setStatus(Status.DEAD);
            System.out.println("You are eaten by the wumpus!");
        }

        /**
         * hunter wins
         */
        public void hunterWin(){
            wumpus.setStatus(false);
            wumpus.setVisible(true);
            System.out.println("You killed the wumpus!");
        }

        /**
         * controls the buttons "Quit" and "Restart"
         */
        public void actionPerformed(ActionEvent event) {
            if (event.getActionCommand().equalsIgnoreCase("Quit")){
                state = PlayState.QUIT;
            }

            else if (event.getActionCommand().equalsIgnoreCase("Restart")){
                HuntTheWumpus.this.reset();
            }
        }
    } // end class Control

    //start a game!!!!!!!!!!!!!!!!!!!!Yay hahahahahahahahahah!!!!!!!!!!I'm so happy!!!!!!!!
    public static void main(String[] argv) throws InterruptedException {
        HuntTheWumpus game = new HuntTheWumpus();
        while(game.state != PlayState.QUIT) {
            game.display.win.repaint();
            Thread.sleep(200);
        }
        game.display.win.dispose();
    }
	
} 