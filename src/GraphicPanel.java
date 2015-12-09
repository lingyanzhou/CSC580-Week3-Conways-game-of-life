import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.Timer;

// -- need to extend JPanel so that we can override some of
//    the default methods -- JPanel inherits from AWT Container
//    (can hold Components) which inherits from AWT Component
//    (can be displayed on a screen)
public class GraphicPanel extends JPanel implements Observer {

	private static final int MIN_RECT_WIDTH = 1;
	private CellularAutomataFrame gf;
	private GameData m_GameData;
	private float m_RectWidth;
	private float m_RectHeight;
	private boolean m_IsRunning;

	private Timer animationTimer = null;

	public Timer getAnimationTimer() {
		return animationTimer;
	}

	public boolean isRunning() {
		return m_IsRunning;
	}

	public void setIsRunning(boolean isRunning) {
		this.m_IsRunning = isRunning;
	}

	public GraphicPanel(CellularAutomataFrame _gf) {
		super();

		gf = _gf;

		this.setBackground(Color.gray);

		// -- The JPanel can have a mouse listener if desired
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				if (!m_IsRunning) {
					m_GameData.flipStateAt((int)(arg0.getY() / m_RectHeight),
							(int)(arg0.getX() / m_RectWidth));
				}
			}

			public void mouseEntered(MouseEvent arg0) {
				// Do not handle
			}

			public void mouseExited(MouseEvent arg0) {
				// Do not handle
			}

			public void mousePressed(MouseEvent arg0) {
				// Do not handle
			}

			public void mouseReleased(MouseEvent arg0) {
				// Do not handle
			}

		});
		// -- The JPanel can have a mouse listener if desired
		this.addMouseMotionListener(new MouseMotionListener() {

			public void mouseDragged(MouseEvent arg0) {
				if (!m_IsRunning) {
					m_GameData.flipStateAt((int)(arg0.getY() / m_RectHeight),
							(int)(arg0.getX() / m_RectWidth));
				}
			}

			public void mouseMoved(MouseEvent e) {
			}

		});

		// -- Timer will generate an event every 10mSec once it is started
		// First parameter is the delay in mSec, second is the ActionListener
		animationTimer = new Timer(10,
		// -- ActionListener for the timer event
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						m_GameData.step();
					}
				}

		);

	}

	// -- this override sets the desired size of the JPanel which is
	// used by some layout managers -- default desired size is 0,0
	// which is, in general, not good -- will pull from layout manager
	public Dimension getPreferredSize() {
		return new Dimension(50, 50);
	}

	// -- this override is where all the painting should be done.
	// DO NOT call it directly. Rather, call repaint() and let the
	// event handling system decide when to call it
	// DO NOT put graphics function call elsewhere in the code, although
	// legal, it's bad practice and could destroy the integrity of the
	// display
	public void paint(Graphics g) {
		// -- the base class paintComponent(g) method ensures
		// the drawing area will be cleared properly. Do not
		// modify any attributes of g prior to sending it to
		// the base class
		super.paintComponent(g);

		// -- for legacy reasons the parameter comes in as type Graphics
		// but it is really a Graphics2D object. Cast it up since the
		// Graphics2D class is more capable
		Graphics2D g2d = (Graphics2D) g;

		int h = this.getHeight();
		int w = this.getWidth();
		m_RectWidth = Math.max(MIN_RECT_WIDTH, (float)w / (float)m_GameData.getWidth());
		m_RectHeight = Math.max(MIN_RECT_WIDTH, (float)h / (float)m_GameData.getHeight());

		g2d.setColor(Color.gray);
		g2d.fill(new Rectangle(0, 0, w, h));
		byte[][] data = m_GameData.getData();
		for (int i = 0; i < m_GameData.getHeight(); ++i) {
			final int startY = (int)Math.round(i * m_RectHeight);
			final int height = (int)Math.round((i+1) * m_RectHeight)-startY;
			for (int j = 0; j < m_GameData.getWidth(); ++j) {
				final int startX = (int)Math.round(j * m_RectWidth);
				final int width = (int)Math.round((j+1) * m_RectWidth)-startX;
				
				if (GameData.STATE_ALIVE == data[i][j]) {
					g2d.setColor(Color.red);
				} else if (GameData.STATE_DYING == data[i][j]) {
					g2d.setColor(Color.blue);
				} else if (GameData.STATE_DEAD == data[i][j]) {
					g2d.setColor(Color.black);
				} else if (GameData.STATE_GROWING == data[i][j]) {
					g2d.setColor(Color.yellow);
				} else if (GameData.STATE_DONTCARE == data[i][j]) {
					g2d.setColor(Color.white);
				}

				g2d.fill(new Rectangle(startX, startY, width, height));
			}
		}

	}

	public void setGameData(GameData gameData) {
		this.m_GameData = gameData;
		m_GameData.addObserver(this);
		update(m_GameData, null);
	}

	@Override
	public void update(Observable o, Object arg) {
		repaint();
	}

}
