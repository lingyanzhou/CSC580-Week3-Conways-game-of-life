/**
 * 
 * @author zhoulingyan
 *
 */
import java.io.File;
import java.util.Observable;
import java.util.Random;

/**
 * Represents game parameters and states.
 */
public class GameData extends Observable {
	/**
	 * The cell is dead.
	 */
	static public final byte STATE_DEAD = 0;
	/**
	 * The cell is alive.
	 */
	static public final byte STATE_ALIVE = (byte) 255;
	/**
	 * The cell is not interesting. Used for paddings.
	 */
	static public final byte STATE_DONTCARE = 2;
	/**
	 * The cell is growing.
	 */
	static public final byte STATE_GROWING = (byte) 200;
	/**
	 * The cell is dying.
	 */
	static public final byte STATE_DYING = 100;
	
	/**
	 * Border extrapolation mode: wrapping aroud the borders.
	 */
	static public final int BORDER_WRAP = 1;
	// static public final int BORDER_ALIVE = 2;
	// static public final int BORDER_DEAD = 3;
	/**
	 * Border extrapolation mode: use don't-care values for the borders.
	 */
	static public final int BORDER_DONTCARE = 4;

	/**
	 * Selected rule number.
	 */
	private int m_RuleNum;
	
	/**
	 * Selected border extrapolation mode
	 */
	private int m_BorderType;
	
	/**
	 * Cells states
	 */
	private byte[][] m_Data;
	
	/**
	 * Height of the universe
	 */
	private int m_Height;
	
	/**
	 * Width of the universe
	 */
	private int m_Width;
	
	/**
	 * Current GameRule object.
	 */
	private GameRule m_GameRule;

	/**
	 * Constructor.
	 * 
	 * @param height Height of the universe
	 * @param width Width of the universe
	 */
	public GameData(int height, int width) {
		m_Height = Math.max(1, height);
		m_Width = Math.max(1, width);
		m_Data = new byte[m_Height][m_Width];
		m_RuleNum = 0;
		m_GameRule = new GameRule1(GameRule1.BORN_3, GameRule1.STAY_ALIVE_2
				| GameRule1.STAY_ALIVE_3);
		m_BorderType = BORDER_WRAP;
		clear();
	}

	/**
	 * Get border extrapolation mode
	 * 
	 * @return Border extrapolation mode
	 */
	public int getBorderType() {
		return m_BorderType;
	}

	/**
	 * Set border extrapolation mode
	 * 
	 * @param borderType New border extrapolation mode
	 */
	public void setBorderType(int borderType) {
		m_BorderType = borderType;
		setChanged();
		notifyObservers();
	}

	/**
	 * Get current rule number
	 * 
	 * @return Rule number
	 */
	public int getRuleNum() {
		return m_RuleNum;
	}

	/**
	 * Set rule by rule number.
	 * @param ruleNum New Rule number
	 */
	public void setRuleNum(int ruleNum) {
		if (m_RuleNum != ruleNum) {
			m_RuleNum = ruleNum;
			if (0 == m_RuleNum) {
				m_GameRule = new GameRule1(GameRule1.BORN_3,
						GameRule1.STAY_ALIVE_2 | GameRule1.STAY_ALIVE_3);
			} else if (1 == m_RuleNum) {
				m_GameRule = new GameRule2(GameRule1.BORN_2| GameRule1.BORN_3,
						GameRule1.STAY_ALIVE_2 | GameRule1.STAY_ALIVE_3 | GameRule1.STAY_ALIVE_4
								);
			} else {
				m_GameRule = new GameRule3();
			}
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Get all the universe as 2D byte array
	 * @return The universe as 2D byte array
	 */
	public byte[][] getData() {
		return m_Data;
	}

	/**
	 * Reset the universe.
	 */
	public void clear() {
		for (int h = 0; h < m_Height; h++) {
			for (int w = 0; w < m_Width; w++) {
				m_Data[h][w] = 0;
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Go to the next generation.
	 */
	public void step() {
		if (BORDER_WRAP == m_BorderType) {
			m_GameRule.process(m_Data, m_Height, m_Width,
					DataPadding.BORDER_WRAP, STATE_DONTCARE);
		} else if (BORDER_DONTCARE == m_BorderType) {
			m_GameRule.process(m_Data, m_Height, m_Width,
					DataPadding.BORDER_CONSTANT, STATE_DONTCARE);
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Resize the dimension of the universe.
	 * 
	 * @param height Height of the universe
	 * @param width Width of the universe
	 */
	public void resize(int height, int width) {
		if (height == m_Height && width == m_Width) {
			clear();
			setChanged();
			notifyObservers();
		} else {
			m_Height = Math.max(1, height);
			m_Width = Math.max(1, width);
			m_Data = new byte[m_Height][m_Width];
			clear();
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Load the universe from a file.
	 * 
	 * @param file The file
	 */
	public void load(File file) {
		byte[][] tmp = ImageReadWrite.ImageRead(file);
		m_Height = tmp.length;
		m_Width = tmp[0].length;
		m_Data = tmp;
		setChanged();
		notifyObservers();
	}

	/**
	 * Save the universe to a file.
	 * 
	 * @param file The file
	 */
	public void save(File outfile) {
		ImageReadWrite.ImageWrite(m_Data, outfile);
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Randomize the universe.
	 */
	public void randomize() {
		Random rand = new Random();
		for (int h = 0; h < m_Height; h++) {
			for (int w = 0; w < m_Width; w++) {
				if (rand.nextBoolean()) {
					m_Data[h][w] = STATE_ALIVE;
				} else {
					m_Data[h][w] = STATE_DEAD;
				}
			}
		}
		setChanged();
		notifyObservers();
	}

	/**
	 * Flip the state at the specified cell.
	 * 
	 * @param y The position along the height.
	 * @param x The position along the width.
	 */
	public void flipStateAt(int y, int x) {
		if (x >= 0 && x < m_Width && y >= 0 && y < m_Height) {
			if (STATE_DONTCARE == m_Data[y][x]) {
				m_Data[y][x] = STATE_DEAD;
			} else if (STATE_DEAD == m_Data[y][x]){
				m_Data[y][x] = STATE_GROWING;
			} else if (STATE_GROWING == m_Data[y][x]){
				m_Data[y][x] = STATE_ALIVE;
			} else if (STATE_ALIVE == m_Data[y][x]){
				m_Data[y][x] = STATE_DYING;
			} else if (STATE_DYING == m_Data[y][x]){
				m_Data[y][x] = STATE_DONTCARE;
			}
			setChanged();
			notifyObservers();
		}
	}

	/**
	 * Get the height of the universe.
	 * 
	 * @return The height of the universe.
	 */
	public int getHeight() {
		return m_Height;
	}

	/**
	 * Get the width of the universe.
	 * 
	 * @return The width of the universe.
	 */
	public int getWidth() {
		return m_Width;
	}
}
