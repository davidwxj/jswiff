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

package com.jswiff.swfrecords.tags;

import com.jswiff.constants.TagConstants.TagType;
import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

import java.io.IOException;
import java.util.Date;

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
