/**
 * Alice Zhang
 * CS231 Project 10: LandscapeDisplay.java
 */

/*
  Originally written by Bruce A. Maxwell a long time ago.
  Updated by Brian Eastwood and Stephanie Taylor more recently

  Creates a window using the JFrame class.

  Creates a drawable area in the window using the JPanel class.

  The JPanel calls the Landscape's draw method to fill in content, so the
  Landscape class needs a draw method.
*/

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.sound.sampled.Control;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class LandscapeDisplay{
    protected JFrame win;
    private Landscape scape; 
    private LandscapePanel canvas;
    private JPanel bottom;
    protected JButton quit;
    protected JButton reset;
    private int scale;

    /**
     * Initializes a display window for a Landscape.
     */
    public LandscapeDisplay(Landscape scape, int scale)
    {
        // setup the window
        this.win = new JFrame("HuntTheWumpus");
        this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.scale = scale;
        this.scape = scape;

        // create a panel in which to display the Landscape
        this.canvas = new LandscapePanel( this.scape.getWidth(),
					  this.scape.getHeight() );


        this.quit = new JButton("Quit");
        this.reset = new JButton("Restart");
        this.bottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        this.bottom.add(quit);
        this.bottom.add(reset);
        
        // add the panel to the window, layout, and display
        this.win.add(this.canvas, BorderLayout.CENTER);
        this.win.add(bottom, BorderLayout.SOUTH);
        this.win.pack();
        this.win.setVisible(true);
    }


    /**
     * This inner class provides the panel on which Landscape elements
     * are drawn.
     */
    private class LandscapePanel extends JPanel
    {
        /**
         * Creates the panel.
         * @param width     the width of the panel in pixels
         * @param height        the height of the panel in pixels
         */
        public LandscapePanel(int width, int height)
        {
	        super();
	        this.setPreferredSize(new Dimension(width, height));
	        this.setBackground(Color.WHITE);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen.  The supplied Graphics
         * object is used to draw.
         * 
         * @param g     the Graphics object used for drawing
         */
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            scape.draw( g, scale );
        } // end paintComponent
        
    } // end LandscapePanel

    public void repaint() {
	    this.win.repaint();
    }

    // test codes
    public static void main(String[] args) throws InterruptedException {
        
        Landscape scape = new Landscape(512, 512);
        Vertex v = new Vertex(0, 0);
        v.setVisible(true);
        Hunter hunter = new Hunter(v);
        scape.addBkgAgent(v);
        scape.addFRGAgent(hunter);
        LandscapeDisplay display = new LandscapeDisplay(scape, 64);
        display.repaint();
    }

}
