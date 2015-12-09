/**
 *  @author zhoulingyan
 */
/**
 * Game rule 1 class.
 * 
 * 3 rules:
 * <ol>
 *   <li>If any born condition is met, a dead cell becomes alive.</li>
 *   <li>If any stay-alive condition is met, an alive cell stays alive.</li>
 *   <li>Otherwise, it will be dead.</li>
 * </ol>
 */
public class GameRule1 extends GameRule {

	/**
	 * Border top.
	 */
	static private final int BORDER_TOP = 1;
	/**
	 * Border bottom.
	 */
	static private final int BORDER_BOTTOM = 1;
	/**
	 * Border left.
	 */
	static private final int BORDER_LEFT = 1;
	/**
	 * Border right.
	 */
	static private final int BORDER_RIGHT = 1;


	/**
	 * If neighbors have exactly 0 alive cell(s), a dead cell will be born.
	 */
	static public final long BORN_0 = 0x1L;
	/**
	 * If neighbors have exactly 1 alive cell(s), a dead cell will be born.
	 */
	static public final long BORN_1 = 0x10L;
	/**
	 * If neighbors have exactly 2 alive cell(s), a dead cell will be born.
	 */
	static public final long BORN_2 = 0x100L;
	/**
	 * If neighbors have exactly 3 alive cell(s), a dead cell will be born.
	 */
	static public final long BORN_3 = 0x1000L;
	/**
	 * If neighbors have exactly 4 alive cell(s), a dead cell will be born.
	 */
	static public final long BORN_4 = 0x10000L;
	/**
	 * If neighbors have exactly 5 alive cell(s), a dead cell will be born.
	 */
	static public final long BORN_5 = 0x100000L;
	/**
	 * If neighbors have exactly 6 alive cell(s), a dead cell will be born.
	 */
	static public final long BORN_6 = 0x1000000L;
	/**
	 * If neighbors have exactly 7 alive cell(s), a dead cell will be born.
	 */
	static public final long BORN_7 = 0x10000000L;
	/**
	 * If neighbors have exactly 8 alive cell(s), a dead cell will be born.
	 */
	static public final long BORN_8 = 0x100000000L;
	/**
	 * If neighbors have exactly 0 alive cell(s), a n alive cell stays alive.
	 */
	static public final long STAY_ALIVE_0 = 0x1L;
	/**
	 * If neighbors have exactly 1 alive cell(s), a n alive cell stays alive.
	 */
	static public final long STAY_ALIVE_1 = 0x10L;
	/**
	 * If neighbors have exactly 2 alive cell(s), a n alive cell stays alive.
	 */
	static public final long STAY_ALIVE_2 = 0x100L;
	/**
	 * If neighbors have exactly 3 alive cell(s), a n alive cell stays alive.
	 */
	static public final long STAY_ALIVE_3 = 0x1000L;
	/**
	 * If neighbors have exactly 4 alive cell(s), a n alive cell stays alive.
	 */
	static public final long STAY_ALIVE_4 = 0x10000L;
	/**
	 * If neighbors have exactly 5 alive cell(s), a n alive cell stays alive.
	 */
	static public final long STAY_ALIVE_5 = 0x100000L;
	/**
	 * If neighbors have exactly 6 alive cell(s), a n alive cell stays alive.
	 */
	static public final long STAY_ALIVE_6 = 0x1000000L;
	/**
	 * If neighbors have exactly 7 alive cell(s), a n alive cell stays alive.
	 */
	static public final long STAY_ALIVE_7 = 0x10000000L;
	/**
	 * If neighbors have exactly 8 alive cell(s), a n alive cell stays alive.
	 */
	static public final long STAY_ALIVE_8 = 0x100000000L;

	/**
	 * Cell born conditions
	 */
	private int[] m_BornRule;
	
	/**
	 * Cell stay-alive condition
	 */
	private int[] m_StayAliveRule;

	/**
	 * Constructor
	 * @param bornCond Cell born conditions.
	 * @param stayAliveCond Cell stay-alive conditions.
	 */
	public GameRule1(long bornCond, long stayAliveCond) {
		boolean[] born = new boolean[9];
		boolean[] stay = new boolean[9];
		born[0] = (BORN_0 & bornCond) != 0 ? true : false;
		born[1] = (BORN_1 & bornCond) != 0 ? true : false;
		born[2] = (BORN_2 & bornCond) != 0 ? true : false;
		born[3] = (BORN_3 & bornCond) != 0 ? true : false;
		born[4] = (BORN_4 & bornCond) != 0 ? true : false;
		born[5] = (BORN_5 & bornCond) != 0 ? true : false;
		born[6] = (BORN_6 & bornCond) != 0 ? true : false;
		born[7] = (BORN_7 & bornCond) != 0 ? true : false;
		born[8] = (BORN_8 & bornCond) != 0 ? true : false;
		stay[0] = (STAY_ALIVE_0 & stayAliveCond) != 0 ? true : false;
		stay[1] = (STAY_ALIVE_1 & stayAliveCond) != 0 ? true : false;
		stay[2] = (STAY_ALIVE_2 & stayAliveCond) != 0 ? true : false;
		stay[3] = (STAY_ALIVE_3 & stayAliveCond) != 0 ? true : false;
		stay[4] = (STAY_ALIVE_4 & stayAliveCond) != 0 ? true : false;
		stay[5] = (STAY_ALIVE_5 & stayAliveCond) != 0 ? true : false;
		stay[6] = (STAY_ALIVE_6 & stayAliveCond) != 0 ? true : false;
		stay[7] = (STAY_ALIVE_7 & stayAliveCond) != 0 ? true : false;
		stay[8] = (STAY_ALIVE_8 & stayAliveCond) != 0 ? true : false;

		int bornRuleNum = 0;
		int stayRuleNum = 0;
		for (int i = 0; i < 9; ++i) {
			if (born[i]) {
				++bornRuleNum;
			}
			if (stay[i]) {
				++stayRuleNum;
			}
		}

		m_BornRule = new int[bornRuleNum];
		m_StayAliveRule = new int[stayRuleNum];

		int curBornRuleIndex = 0;
		int curStayAliveRuleIndex = 0;
		for (int i = 0; i < 9; ++i) {
			if (born[i]) {
				m_BornRule[curBornRuleIndex] = i;
				++curBornRuleIndex;
			}
			if (stay[i]) {
				m_StayAliveRule[curStayAliveRuleIndex] = i;
				++curStayAliveRuleIndex;
			}
		}
	}


	@Override
	public void process(byte[][] data, int height, int width, int borderType,
			byte value) {
		byte[][] tmp = DataPadding.copyMakeBorder(data, height, width,
				BORDER_TOP, BORDER_BOTTOM, BORDER_LEFT, BORDER_RIGHT,
				borderType, value);
		for (int h = 0; h < height; ++h) {
			for (int w = 0; w < width; ++w) {
				byte curCellState = tmp[h + BORDER_TOP][w + BORDER_LEFT];
				int aliveCount = 0;
				for (int kh = -BORDER_TOP; kh <= BORDER_BOTTOM; ++kh) {
					for (int kw = -BORDER_LEFT; kw <= BORDER_RIGHT; ++kw) {
						if (kh == 0 && kw == 0) {
							continue;
						}
						if (GameData.STATE_ALIVE == tmp[h + BORDER_TOP + kh][w
								+ BORDER_LEFT + kw]) {
							++aliveCount;
						}
					}
				}
				if (GameData.STATE_ALIVE == curCellState) {
					boolean stayAlive = false;
					for (int i = 0; i < m_StayAliveRule.length; ++i) {
						if (aliveCount == m_StayAliveRule[i]) {
							stayAlive = true;
							break;
						}
					}
					if (!stayAlive) {
						data[h][w] = GameData.STATE_DEAD;
					}
				} else if (GameData.STATE_DEAD == curCellState) {
					boolean born = false;
					for (int i = 0; i < m_BornRule.length; ++i) {
						if (aliveCount == m_BornRule[i]) {
							born = true;
							break;
						}
					}
					if (born) {
						data[h][w] = GameData.STATE_ALIVE;
					}
				} else if (GameData.STATE_DYING == curCellState) {
					boolean born = false;
					for (int i = 0; i < m_BornRule.length; ++i) {
						if (aliveCount == m_BornRule[i]) {
							born = true;
							break;
						}
					}
					if (born) {
						data[h][w] = GameData.STATE_ALIVE;
					}
				} else if (GameData.STATE_GROWING == curCellState) {
					boolean born = false;
					for (int i = 0; i < m_BornRule.length; ++i) {
						if (aliveCount == m_BornRule[i]) {
							born = true;
							break;
						}
					}
					if (born) {
						data[h][w] = GameData.STATE_ALIVE;
					}
				}
			}
		}
	}
}
