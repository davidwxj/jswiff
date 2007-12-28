/*
 * JSwiff is an open source Java API for Macromedia Flash file generation
 * and manipulation
 *
 * Copyright (C) 2004-2008 Ralf Terdic (contact@jswiff.com)
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
package com.jswiff.swfrecords.tags;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.io.Serializable;


/**
 * <p>
 * This tag contains a list of symbol references, which is used to instantiate objects
 * (symbols) defined with <code>DoABCDefine</code>.
 * </p>
 * 
 * @see DoAbcDefine
 * @since SWF 9
 */
public final class SymbolClass extends Tag {
    private SymbolReference[] references;

    /**
     * Creates a new SymbolClass instance. Supply an array of export mappings
     * (for each  exported character one).
     *
     * @param references character export mappings
     */
    public SymbolClass(SymbolReference[] references) {
        code            = TagConstants.SYMBOL_CLASS;
        this.references = references;
    }

    SymbolClass() {
        // empty
    }

    /**
     * Sets the symbol references defined in this tag.
     *
     * @param references symbol references
     */
    public void setReferences(SymbolReference[] references) {
        this.references = references;
    }

    /**
     * Returns the symbol references defined in this tag.
     *
     * @return character symbol references
     */
    public SymbolReference[] getReferences() {
        return references;
    }

    protected void writeData(OutputBitStream outStream)
        throws IOException {
        int count = references.length;
        outStream.writeUI16(count);
        for (int i = 0; i < count; i++) {
            outStream.writeUI16(references[i].getCharacterId());
            outStream.writeString(references[i].getName());
        }
    }

    void setData(byte[] data) throws IOException {
        InputBitStream inStream = new InputBitStream(data);
        int count               = inStream.readUI16();
        references          = new SymbolReference[count];
        for (int i = 0; i < count; i++) {
            references[i] = new SymbolReference(
                    inStream.readUI16(), inStream.readString());
        }
    }

    /**
     * Defines an (immutable) symbol reference.
     */
    public static class SymbolReference implements Serializable {
        private int characterId;
        private String name;

        /**
         * Creates a new symbol reference. Supply character ID and symbol name
         *
         * @param characterId character ID
         * @param name symbol name
         */
        public SymbolReference(int characterId, String name) {
            this.characterId     = characterId;
            this.name            = name;
        }

        /**
         * Returns the character ID of the symbol reference
         *
         * @return character ID
         */
        public int getCharacterId() {
            return characterId;
        }

        /**
         * Returns the name of the symbol to be referenced
         *
         * @return export name
         */
        public String getName() {
            return name;
        }
    }
}
