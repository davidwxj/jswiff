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

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.swfrecords.Shape;

/**
 * This tag's structure is identical to <code>DefineFont2</code>. The difference
 * is in the precision of the glyph definition within glyphShapeTable: all EM
 * square coordinates are multiplied by 20 before being added to the tag, thus
 * enabling substantial resolution increase.
 * 
 * @since SWF 8.
 */
public final class DefineFont3 extends DefineFont2 {

  private static final long serialVersionUID = 1L;

  /**
   * @see DefineFont2#DefineFont2(int, String, Shape[], char[])
   */
  public DefineFont3(int characterId, String fontName, Shape[] glyphShapeTable, char[] codeTable) {
    super(characterId, fontName, glyphShapeTable, codeTable, TagType.DEFINE_FONT_3);
  }
  
  DefineFont3() {
    super(TagType.DEFINE_FONT_3);
  }

}
