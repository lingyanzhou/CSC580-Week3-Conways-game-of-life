/**
 * 
 * @author zhoulingyan
 * 
 */
/**
 * Class to handle paddings.
 */
public class DataPadding {
	/**
	 * Border wrap mode
	 */
	static public final int BORDER_WRAP = 1;
	
	/**
	 * Border constant mode
	 */
	static public final int BORDER_CONSTANT = 2;

	/**
	 * Find a source index of an array when given a position out of the range.
	 * @param p The position.
	 * @param len The length of the array.
	 * @param borderType Border extrapolation mode.
	 * @return The valid array index.
	 */
	static public int borderInterpolate(int p, int len, int borderType) {
		if (borderType != BORDER_WRAP && borderType != BORDER_CONSTANT) {
			throw new IllegalArgumentException("Unknown border type.");
		}
		if (len < 1) {
			throw new IllegalArgumentException("len<1.");
		}

		if (borderType == DataPadding.BORDER_WRAP) {
			if (p < 0) {
				return len + p % len;
			} else {
				return p % len;
			}
		} else if (borderType == DataPadding.BORDER_CONSTANT) {
			return -1;
		}
		return -1;
	}

	/**
	 * Padding an array.
	 * 
	 * @param src The source array
	 * @param sHeight Array height
	 * @param sWidth Array width
	 * @param top Top border size
	 * @param bottom Bottom border size
	 * @param left Left border size
	 * @param right Right border size
	 * @param borderType The border extrapolation mode
	 * @param value The border value when constant border mode is used.
	 * @return The padded array.
	 */
	static public byte[][] copyMakeBorder(byte[][] src, int sHeight,
			int sWidth, int top, int bottom, int left, int right,
			int borderType, byte value) {
		if (borderType != BORDER_WRAP && borderType != BORDER_CONSTANT) {
			throw new IllegalArgumentException("Unknown border type.");
		}
		if (sHeight < 1) {
			throw new IllegalArgumentException("len<1.");
		}
		if (sWidth < 1) {
			throw new IllegalArgumentException("sWidth<1.");
		}
		if (top < 0) {
			throw new IllegalArgumentException("top<0.");
		}
		if (bottom < 0) {
			throw new IllegalArgumentException("bottom<0.");
		}
		if (left < 0) {
			throw new IllegalArgumentException("left<0.");
		}
		if (right < 0) {
			throw new IllegalArgumentException("right<0.");
		}

		byte[][] dst = new byte[sHeight + top + bottom][sWidth + left + right];
		for (int h = 0; h < sHeight; ++h) {
			for (int w = 0; w < sWidth; ++w) {
				dst[h + top][w + left] = src[h][w];
			}
		}

		if (borderType == BORDER_CONSTANT) {
			for (int h = 0; h < top; ++h) {
				for (int w = 0; w < sWidth + left + right; ++w) {
					dst[h][w] = value;
				}
			}
			for (int h = sHeight + top; h < sHeight + top + bottom; ++h) {
				for (int w = 0; w < sWidth + left + right; ++w) {
					dst[h][w] = value;
				}
			}
			for (int h = top; h < sHeight + top; ++h) {
				for (int w = 0; w < left; ++w) {
					dst[h][w] = value;
				}
			}
			for (int h = top; h < sHeight + top; ++h) {
				for (int w = left + sWidth; w < sWidth + left + right; ++w) {
					dst[h][w] = value;
				}
			}
		} else if (borderType == BORDER_WRAP) {
			for (int h = top; h < sHeight + top; ++h) {
				for (int w = 0; w < left; ++w) {
					dst[h][w] = src[h - top][borderInterpolate(w - left,
							sWidth, borderType)];
				}
			}
			for (int h = top; h < sHeight + top; ++h) {
				for (int w = left + sWidth; w < sWidth + left + right; ++w) {
					dst[h][w] = src[h - top][borderInterpolate(w - left,
							sWidth, borderType)];
				}
			}
			for (int h = 0; h < top; ++h) {
				for (int w = 0; w < sWidth + left + right; ++w) {
					dst[h][w] = dst[top
							+ borderInterpolate(h - top, sHeight, borderType)][w];
				}
			}
			for (int h = sHeight + top; h < sHeight + top + bottom; ++h) {
				for (int w = 0; w < sWidth + left + right; ++w) {
					dst[h][w] = dst[top
							+ borderInterpolate(h - top, sHeight, borderType)][w];
				}
			}
		}
		return dst;
	}
}
