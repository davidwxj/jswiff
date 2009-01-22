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
import java.util.Date;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

/**
 * <p>
 * Contains information about the product used to generate the SWF.
 * </p>
 * 
 * @since SWF 3
 */
public final class ProductInfo extends DefinitionTag {

  private static final long serialVersionUID = 1L;

  private int productId;
  private int edition;
  private short majorVersion;
  private short minorVersion;
  private long buildNumber;
  private Date buildDate;

  public ProductInfo(int productId, int edition, short majorVersion, short minorVersion, long buildNumber,
      Date buildDate) {
    super(TagType.PRODUCT_INFO);
    this.productId = productId;
    this.edition = edition;
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
    this.buildNumber = buildNumber;
    this.buildDate = buildDate;
  }

  ProductInfo() {
    super(TagType.PRODUCT_INFO);
  }

  protected void writeData(OutputBitStream outStream) throws IOException {
    outStream.writeSI32(productId);
    outStream.writeSI32(edition);
    outStream.writeUI8(majorVersion);
    outStream.writeUI8(minorVersion);
    outStream.writeSI64(buildNumber);
    long buildTime = buildDate.getTime();
    outStream.writeUI32(buildTime & 0xFFFFFFFFL);
    outStream.writeUI32(buildTime >> 32);
  }

  void setData(byte[] data) throws IOException {
    InputBitStream inStream = new InputBitStream(data);
    productId = inStream.readSI32();
    edition = inStream.readSI32();
    majorVersion = inStream.readUI8();
    minorVersion = inStream.readUI8();
    buildNumber = inStream.readSI64();
    buildDate = new Date(inStream.readUI32() + (inStream.readUI32() << 32));
  }

  public int getProductId() {
    return productId;
  }

  public int getEdition() {
    return edition;
  }

  public short getMajorVersion() {
    return majorVersion;
  }

  public short getMinorVersion() {
    return minorVersion;
  }

  public long getBuildNumber() {
    return buildNumber;
  }

  public Date getBuildDate() {
    return buildDate;
  }
}
