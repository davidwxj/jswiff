/*
 * Copyright © 2004-2010 Ralf Sippl <ralf.sippl@gmail.com>
 * Copyright © 2008-2010 Ben Stock <bs.stock+jswiff@gmail.com>
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS "AS IS AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of the copyright holders.
 *
 */

package com.jswiff.util;

/**
 * This class provides methods for working with hexadecimal representations of data.
 */
public class HexUtils {
	private static final char[] HEX_DIGITS = {
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
			'E', 'F'
		};

	/**
	 * Returns the hexadecimal representation of a byte array.
	 * 
	 * @param data byte array
	 *
	 * @return hex representation
	 */
	public static String toHex(final byte[] data) {
		return toHex(data, 0, data.length);
	}

	/**
	 * Returns the hexadecimal representation of a part of a byte array.
	 *
	 * @param data byte array
	 * @param startPos start position
	 * @param length length of the relevant array portion
	 *
	 * @return hex representation
	 */
	public static String toHex(
		final byte[] data, final int startPos, final int length) {
		StringBuffer b = new StringBuffer();
		int endPos     = startPos + length;
		for (int i = startPos; i < endPos; i++) {
			if (i > 0) {
				b.append(' ');
			}
			int c = data[i];
			b.append(HEX_DIGITS[(c & 0xF0) >> 4]);
			b.append(HEX_DIGITS[(c & 0x0F) >> 0]);
		}
		return b.toString();
	}
}
