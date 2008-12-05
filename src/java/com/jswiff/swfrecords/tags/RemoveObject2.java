/*
 * JSwiff is an open source Java API for Adobe Flash file generation
 * and manipulation.
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program; if not, see <http://www.gnu.org/licenses/>.
 */

package com.jswiff.swfrecords.tags;

import java.io.IOException;

import com.jswiff.constants.TagConstants;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;


/**
 * The RemoveObject2 tag removes the character instance at the specified depth
 * from the display list.
 *
 * @since SWF 3
 */
public final class RemoveObject2 extends Tag {
	private int depth;

	/**
	 * Creates a new RemoveObject2 tag. Supply the depth of the character
	 * instance to be removed.
	 *
	 * @param depth depth of instance to be removed
	 */
	public RemoveObject2(int depth) {
		code		   = TagConstants.REMOVE_OBJECT_2;
		this.depth     = depth;
	}

	RemoveObject2() {
		// empty
	}

	/**
	 * Sets the depth of the character instance to be removed.
	 *
	 * @param depth depth of instance to be removed
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/**
	 * Returns the depth of the character instance to be removed.
	 *
	 * @return depth of instance to be removed
	 */
	public int getDepth() {
		return depth;
	}

	protected void writeData(OutputBitStream outStream)
		throws IOException {
		outStream.writeUI16(depth);
	}

	void setData(byte[] data) throws IOException {
		InputBitStream inStream = new InputBitStream(data);
		depth = inStream.readUI16();
	}
}
