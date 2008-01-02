package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;

public class AbcMetadata implements Serializable {
  private int nameIndex;
  private AbcMetadataItem[] items;
  
  public static AbcMetadata read(InputBitStream stream) throws IOException {
    AbcMetadata metadata = new AbcMetadata();
    metadata.nameIndex = stream.readU30();
    int count = stream.readU30();
    metadata.items = new AbcMetadataItem[count];
    for (int i = 0; i < count; i++) {
      metadata.items[i] = AbcMetadataItem.read(stream);
    }
    return metadata;
  }
}
