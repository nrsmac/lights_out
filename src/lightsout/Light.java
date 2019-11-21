package lightsout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Represents a single light square in the LightsOut game. Extends JPanel. Has an on or off state. Can return adjacent
 * panels.
 * 
 * @author nrsmac-u1254484
 */
@SuppressWarnings("serial")
public class Light extends JPanel
{

    /** Defines constant integers for on and off states **/
    public static final int ON = 1;
    public static final int OFF = 0;

    /** Stores the state of the light -- whether it's on or off **/
    public int lightState;

    /** Stores position variables **/
    public int positionX;
    public int positionY;

    public Color color;

    /**
     * Creates a new light, an the default state is off, position is x, y passed as int parameters
     * 
     * @throws IllegalArgumentException if location is not valid.
     **/
    public Light (int x, int y)
    {
        // Make sure location is valid
        if (!isValidLocation(x, y))
        {
            throw new IllegalArgumentException();
        }

        this.positionX = x;
        this.positionY = y;
        lightState = OFF;
        color = Color.black;
        repaint();
    }

    /** Switches Light object on or off **/
    public void toggleLight ()
    {
        if (this.lightState == OFF)
        {
            this.lightState = ON;
        }
        else if (this.lightState == ON)
        {
            this.lightState = OFF;
        }
        repaint();
    }

    /** returns x-value **/
    public int getPositionX ()
    {
        return positionX;
    }

    /** returns x-value **/
    public int getPositionY ()
    {
        return positionY;
    }

    /**
     * @returns light state if its on or off
     */

    public int getLightState ()
    {
        return lightState;
    }

    public void setLightState (int lightState)
    {
        this.lightState = lightState;
    }

    /**
     * Checks if location is valid: positive int and within grid bounds
     * 
     * @param x = potiential x coordinate
     * @param y = potiential y coordinate
     * @throws IllegalArgumentException if point is out of grid bounds
     */
    private boolean isValidLocation (int x, int y) throws IllegalArgumentException
    {
        if (x < 0 || y < 0)
        { // Checking for positive parameters
            return false;
        }

        return true;
    }

    public void setColor (Color color)
    {
        this.color = color;
    }

    /**
     * Paints this light onto g
     */
    @Override
    public void paintComponent (Graphics g)
    {
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}