/**
 * GameRule base class
 * @author zhoulingyan
 *
 */
public abstract class GameRule {
	/**
	 * Simulate one generation.
	 * @param data The universe.
	 * @param height The height of the universe.
	 * @param width The width of the universe.
	 * @param borderType The border extrapolation mode.
	 * @param value Used as the padding value when using the constant border mode.
	 */
	abstract public void process(byte[][] data, int height, int width,
			int borderType, byte value);
}
