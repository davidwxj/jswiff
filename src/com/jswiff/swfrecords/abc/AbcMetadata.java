package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcMetadata implements Serializable {
  private int nameIndex;
  private List<AbcMetadataItem> items = new ArrayList<AbcMetadataItem>();
  
  private AbcMetadata() {
    // empty
  }
  
  public AbcMetadata(int nameIndex) {
    this.nameIndex = nameIndex;
  }
  
  public static AbcMetadata read(InputBitStream stream) throws IOException {
    AbcMetadata metadata = new AbcMetadata();
    metadata.nameIndex = stream.readAbcInt();
    int count = stream.readAbcInt();
    for (int i = 0; i < count; i++) {
      metadata.items.add(AbcMetadataItem.read(stream));
    }
    return metadata;
  }

  public void write(OutputBitStream stream) throws IOException {
    stream.writeAbcInt(nameIndex);
    stream.writeAbcInt(items.size());
    for (Iterator<AbcMetadataItem> it = items.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
  }

  public int getNameIndex() {
    return nameIndex;
  }

  public List<AbcMetadataItem> getItems() {
    return items;
  }
}
