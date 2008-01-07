package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;

public class AbcMetadata implements Serializable {
  private int nameIndex;
  private List<AbcMetadataItem> items = new ArrayList<AbcMetadataItem>();
  
  public static AbcMetadata read(InputBitStream stream) throws IOException {
    AbcMetadata metadata = new AbcMetadata();
    metadata.nameIndex = stream.readU30();
    int count = stream.readU30();
    for (int i = 0; i < count; i++) {
      metadata.items.add(AbcMetadataItem.read(stream));
    }
    return metadata;
  }
}
