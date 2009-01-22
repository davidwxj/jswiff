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

/**
 * Base class for definition tags, which define new characters.
 */
public abstract class DefinitionTag extends Tag {

  private static final long serialVersionUID = 1L;

  protected int characterId;

  public DefinitionTag(TagType tagType) {
    super(tagType);
  }

  /**
   * Sets the character ID. Character IDs start at 1 and have to be unique.
   * 
   * @param characterId
   *          character ID
   */
  public void setCharacterId(int characterId) {
    this.characterId = characterId;
  }

  /**
   * Returns the character ID. Character IDs start at 1 and have to be unique.
   * 
   * @return character ID
   */
  public int getCharacterId() {
    return characterId;
  }
}
