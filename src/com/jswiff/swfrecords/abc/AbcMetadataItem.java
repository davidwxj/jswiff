package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcMetadataItem implements Serializable {
  private int keyIndex;
  private int valueIndex;
  
  public static AbcMetadataItem read(InputBitStream stream) throws IOException {
    AbcMetadataItem item = new AbcMetadataItem();
    item.keyIndex = stream.readU30();
    item.valueIndex = stream.readU30();
    return item;
  }
}
