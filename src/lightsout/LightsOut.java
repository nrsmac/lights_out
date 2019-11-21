package lightsout;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Lights Out!
 * @author nrsmac - u1254484
 * 
 * Simple game. To win, turn all lights off, but there's a catch! Once you toggle one light, the adjacent 
 * lights also change. Once all lights are out, you will win.
 * 
 * New Game button shuffles to a random board that has a guaranteed win. 
 * 
 * Manual Mode allows you to set your own board.
 */

public class LightsOut implements ActionListener, MouseListener
{

    private boolean manualMode;

    public Light[][] board;

    /**
     * Lays out the LightsOut board
     */
    public LightsOut ()
    {
        board = new Light[5][5];

        JFrame frame = new JFrame();
        frame.setTitle("Lights Out! by nrsmac");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.add(makeContents());
        JPanel buttons = new JPanel();

        // The bottom portion contains the New Game button
        JButton newGame = new JButton("New Game");
        newGame.setFont(new Font("SansSerif", Font.BOLD, 12));
        newGame.addActionListener(this);
        buttons.add(newGame, "West");

        // The bottom portion contains the New Game button
        JButton manualModeButton = new JButton("Manual Mode");
        manualModeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        manualModeButton.addActionListener(this);
        buttons.add(manualModeButton, "East");

        frame.add(buttons, "South");

        frame.setVisible(true);
    }

    /**
     * Creates and returns a JPanel containing the components to display in the GUI
     */
    public JPanel makeContents ()
    {

        // Create a 5*5 grid of buttons
        JPanel grid = new JPanel();
        grid.setLayout(new GridLayout(5, 5));
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Light light = new Light(i, j);
                light.addMouseListener(this);
                board[i][j] = light;
                light.setBackground(Color.yellow);
                grid.add(light);
            }
        }
        newGame();
        return grid;
    }

    private void refresh ()
    {
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                Light thisLight = board[i][j];
                if (thisLight.getLightState() == Light.ON)
                {
                    thisLight.setColor(Color.YELLOW);
                }
                if (thisLight.getLightState() == Light.OFF)
                {
                    thisLight.setColor(Color.BLACK);
                }

            }
        }

    }

    /**
     * @param light takes in light
     * Toggles all adjacent lights
     */
    public void toggleAdjacentLights (Light light)
    {
        ArrayList<Light> lights = new ArrayList<Light>();

        try
        {
            lights.add(board[light.getPositionX()][light.getPositionY() - 1]);

        }
        catch (Exception e)
        {
            // Do nothing if light is invalid
        }

        try
        {
            lights.add(board[light.getPositionX()][light.getPositionY() + 1]);
        }
        catch (Exception e)
        {
            // Do nothing if light is invalid
        }

        try
        {
            lights.add(board[light.getPositionX() + 1][light.getPositionY()]);
        }
        catch (Exception e)
        {
            // Do nothing if light is invalid
        }

        try
        {
            lights.add(board[light.getPositionX() - 1][light.getPositionY()]);
        }
        catch (Exception e)
        {
            // Do nothing if light is invalid
        }

        for (Light l : lights)
        {
            l.toggleLight();
        }
    }

    /* 
     * @param light toggles itself 
     * toggles adjacent lights if not in manual mode 
     */
    private void makeMove (Light light)
    {
        light.toggleLight();
        if (!manualMode)
        {
            toggleAdjacentLights(light);
        }
    }

    /**
     * Creates a new, playable game by calling 15 random moves. 
     */
    public void newGame ()
    {
        manualMode = false;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 5; j++)
            {
                // Light thisLight = board[i][j];
                // makeMove(light);
                makeRandomMoves(15);
                refresh();
            }
        }
    }

    /* makes a random move at any given panel
     * @param times is how many moves shall be made
     */
    private void makeRandomMoves (int times)
    {
        manualMode = false;
        for (int z = 0; z < times; z++)
        {
            int x = new Random().nextInt(5);
            int y = new Random().nextInt(5);
            Light light = board[x][y];
            light.toggleLight();
        }
    }

    /*
     * Checks if the board is all off and a win.
     */
    public boolean isWin ()
    {
        boolean isWin = true;
        for (int i = 0; i < board.length; i++)
        {
            for (int j = 0; j < board[i].length; j++)
            {
                if (board[i][j].getLightState() == Light.ON)
                {
                    isWin = false;
                }
            }
        }
        return isWin;
    }

    /*
     * Action listener providing functionality to New Game action and Manual Mode toggle
     */
    @Override
    public void actionPerformed (ActionEvent e)
    {
        String command = e.getActionCommand();
        if (command == "New Game")
        {
            newGame();
        }
        if (command == "Manual Mode")
        {
            if (manualMode)
            {
                manualMode = false;
            }
            else if (!manualMode)
            {
                manualMode = true;
            }
        }
    }

    @Override
    public void mouseClicked (MouseEvent e)
    {
    }

    /*
     * responds to mouse click on a given light, calls makeMove on clicked light, and checks if click
     * resulted in a win
     */
    @Override
    public void mousePressed (MouseEvent e)
    {
        Light light = (Light) e.getSource();
        makeMove(light);
        refresh();

        if (isWin() && !manualMode)
        {
            JOptionPane.showMessageDialog(light, "You win!");
        }

    }

    @Override
    public void mouseReleased (MouseEvent e)
    {
    }

    @Override
    public void mouseEntered (MouseEvent e)
    {
        Light light = (Light) e.getSource();
        light.setColor(Color.pink);
        refresh();

    }

    @Override
    public void mouseExited (MouseEvent e)
    {
    }

    /*
     * Runs the game!
     */
    public static void main (String[] args)
    {
        SwingUtilities.invokeLater( () -> new LightsOut());
    }
}
