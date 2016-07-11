import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;

/**
 * @author Christopher von Bargen
 * @author Fynn Braren
 * @version 1.0
 */
public class GamePanel extends JPanel {

    private BufferedImage background;

    /**
     * used to set the background of the game
     * holds the PlayerPanels
     */

    public GamePanel(){
        setLayout(new FlowLayout());
        try {
            //packed as jar
            background = ImageIO.read(getClass().getResourceAsStream("res/backGround.png"));
        } catch (Exception e) {
            try {
                //unpacked
                background = ImageIO.read(new File("res/backGround.png"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * draws the background to the game panel
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0,0, null);
    }
}
