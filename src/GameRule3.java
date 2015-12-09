
public class GameRule3 extends GameRule {

	static private final int BORDER_TOP = 1;
	static private final int BORDER_BOTTOM = 1;
	static private final int BORDER_LEFT = 1;
	static private final int BORDER_RIGHT = 1;

	@Override
	public void process(byte[][] data, int height, int width, int borderType,
			byte value) {
		byte[][] tmp = DataPadding.copyMakeBorder(data, height, width,
				BORDER_TOP, BORDER_BOTTOM, BORDER_LEFT, BORDER_RIGHT,
				borderType, value);
		for (int h = 0; h < height; ++h) {
			for (int w = 0; w < width; ++w) {
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
				
				if (aliveCount%2==0) {
					data[h][w] = GameData.STATE_DEAD;
				} else {
					data[h][w] = GameData.STATE_ALIVE;
				}
			}
		}

	}

}
