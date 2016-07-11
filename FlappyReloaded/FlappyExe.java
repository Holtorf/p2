import java.awt.EventQueue;

import javax.swing.JFrame;
// TIM MANDELBAUM 2053403
/**
 * <h1>FLAPPY BIRD EXE</h1>
 * Execute the flappy bird game via FlappyExe
 * The ServerClient needs to be started first
 * 
 * @author TMA
 * @since 2016-06-12
 */
public class FlappyExe extends JFrame{
	// EXE
	/**
	 * <h1>CONSTRUCTOR</h1>
	 * -> calls initUI() to initialize the userinterface
	 */
	public FlappyExe(){
		initUI();
	}
	// METHODE UM DIE UI ZU INITIALISIEREN
	// GAMEPANEL HINZUFÜGEN UND JFRAME KONFIGURIEREN
	/**
	 * <h1>INITIALIZE THE USER INTERFACE<h1>
	 * -> add the GamePanel<br>
	 * -> setResizeable(false) to prevent the user to change the windowsize
	 *  
	 */
	private void initUI(){ 
		add(new GamePanel());
		setResizable(false);
		pack(); 
		setTitle("FlappyBird");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);  
	}
	// MAIN 
	public static void main(String[] args){ 
		EventQueue.invokeLater(new Runnable(){
			@Override
			public void run(){
				FlappyExe exe = new FlappyExe(); 
			}
		}); 
	}
}
