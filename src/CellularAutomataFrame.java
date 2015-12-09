
import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.util.Observable;
import java.util.Observer;

public class CellularAutomataFrame extends JFrame implements Observer {

	private GraphicPanel gp;
	private ControlPanel cp;
	private GameData gameData;

	GraphicPanel getGraphicPanel() {
		return gp;
	}

	public CellularAutomataFrame(int height, int width, GameData gameData) {

		setTitle("Cellular Automata");
		// -- add some items to the content pane of the frame
		// JButton okButton = new JButton("OK");
		// frame.add(okButton);

		// -- size of the frame: width, height
		setSize(width, height);

		// -- center the frame on the screen
		setLocationRelativeTo(null);

		// -- shut down the entire application when the frame is closed
		// if you don't include this the application will continue to
		// run in the background and you'll have to kill it by pressing
		// the red square in eclipse
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// -- set the layout manager and add items
		// 5, 5 is the border around the edges of the areas
		setLayout(new BorderLayout(5, 5));

		gp = new GraphicPanel(this);
		this.add(gp, BorderLayout.CENTER);

		cp = new ControlPanel(this);
		this.add(cp, BorderLayout.EAST);

		// -- show the frame on the screen
		setVisible(true);
		this.gameData = gameData;
		gameData.addObserver(this);
		gp.setGameData(gameData);
		cp.setGameData(gameData);
	}

	public static void main(String[] args) {
		CellularAutomataFrame gf = new CellularAutomataFrame(768, 1024,
				new GameData(50, 50));
	}

	@Override
	public void update(Observable arg0, Object arg1) {
	}
}
