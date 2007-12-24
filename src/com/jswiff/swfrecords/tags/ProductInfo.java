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

import java.io.IOException;

import com.jswiff.io.OutputBitStream;

/**
 * <p>
 * Contains information about the product used to generate the SWF.
 * </p>
 * 
 * @since SWF 3
 */
public final class ProductInfo extends DefinitionTag {
  private byte[] productInfo;

  /**
   * Creates a new ProductInfo tag.
   * 
   * @param productInfo
   *            binary data storing product information
   */
  public ProductInfo(byte[] productInfo) {
    code             = TagConstants.PRODUCT_INFO;
    this.productInfo = productInfo;
  }

  ProductInfo() {
    // empty
  }

  /**
   * Returns the data contained in the tag.
   * 
   * @return tag data
   */
  public byte[] getProductInfo() {
    return productInfo;
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeBytes(productInfo);
  }

  void setData(byte[] data) {
    productInfo = data;
  }
}
