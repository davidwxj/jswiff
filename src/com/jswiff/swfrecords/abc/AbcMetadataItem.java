package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcMetadataItem implements Serializable {
  private int keyIndex;
  private int valueIndex;
  
  public static AbcMetadataItem read(InputBitStream stream) throws IOException {
    AbcMetadataItem item = new AbcMetadataItem();
    item.keyIndex = stream.readAbcInt();
    item.valueIndex = stream.readAbcInt();
    return item;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(keyIndex);
    stream.writeAbcInt(valueIndex);
  }
}
