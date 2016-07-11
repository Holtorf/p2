import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The PlayerPanel is the background of one player,
 * there is one panel for every player.
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class PlayerPanel extends JPanel {

    private BufferedImage image;
    private Gui gui;
    private static int[] fallSequence = {1,2,10,10,10,10,10,8,9,0};
    private int frame = 0;
    private int position = 0;
    private int bailIndex = 0;
    private int player;

    /**
     * creates a PlayerPanel with configured size (hard coded)
     */
    public PlayerPanel(int player, Gui gui){
        this.gui = gui;
        this.player = player;
        setPreferredSize(new Dimension(1200, 128));
    }

    /**
     * Paints the player's position with the current frame
     * @param g Graphics
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        try {
            //packed as jar
            this.image = ImageIO.read(getClass().getResourceAsStream("res/"+ player + "/" + frame + ".png"));
        } catch (Exception e) {
            try {
                //unpacked
                this.image = ImageIO.read(new File("res/"+ player + "/" + frame + ".png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        g.drawImage(image, position, 0, null);
    }

    /**
     * Repaints the player as next step
     */
    public void next(){
        frame++;
        if (frame >= 10){
            frame = 0;
        }
        position += 2;
        repaint();
        if (position == getPreferredSize().width-50){ //TODO replace magic number for approximation ...
            gui.win(player);
        }
    }
    /**
     * Repaints the player while falling down
     */
    public void bail(){
        if (bailIndex >= 10){
            bailIndex = 0;
        }
        frame = fallSequence[bailIndex];
        bailIndex++;
        repaint();
    }

    /**
     * sets the panel to it's initial values
     */
    public void reset(){
        frame = 0;
        position = 0;
        repaint();
    }
}
