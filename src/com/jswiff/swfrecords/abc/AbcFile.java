package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.jswiff.io.InputBitStream;
import com.jswiff.io.OutputBitStream;

public class AbcFile implements Serializable {
  private int majorVersion;
  private int minorVersion;
  private AbcConstantPool constantPool;
  private List<AbcMethodSignature> methods = new ArrayList<AbcMethodSignature>();
  private List<AbcMetadata> metadataEntries = new ArrayList<AbcMetadata>();
  private List<AbcInstance> instances = new ArrayList<AbcInstance>();
  private List<AbcClass> classes = new ArrayList<AbcClass>();
  private List<AbcScript> scripts = new ArrayList<AbcScript>();
  private List<AbcMethodBody> methodBodies = new ArrayList<AbcMethodBody>();
  
  public static AbcFile read(InputBitStream stream) throws IOException {
    AbcFile abcFile = new AbcFile();
    abcFile.minorVersion = stream.readUI16();
    abcFile.majorVersion = stream.readUI16();
    if (abcFile.majorVersion != 46 || abcFile.minorVersion > 16) {
      throw new IOException("Unsupported abc format, version > 46.16");
    }
    abcFile.constantPool = AbcConstantPool.read(stream);
    int methodCount = stream.readAbcInt();
    for (int i = 0; i < methodCount; i++) {
      abcFile.methods.add(AbcMethodSignature.read(stream));
    }
    int metadataCount = stream.readAbcInt();
    for (int i = 0; i < metadataCount; i++) {
      abcFile.metadataEntries.add(AbcMetadata.read(stream));
    }
    int classCount = stream.readAbcInt();
    for (int i = 0; i < classCount; i++) {
      abcFile.instances.add(AbcInstance.read(stream));
    }
    for (int i = 0; i < classCount; i++) {
      abcFile.classes.add(AbcClass.read(stream));
    }
    int scriptCount = stream.readAbcInt();
    for (int i = 0; i < scriptCount; i++) {
      abcFile.scripts.add(AbcScript.read(stream));
    }
    int methodBodyCount = stream.readAbcInt();
    for (int i = 0; i < methodBodyCount; i++) {
      abcFile.methodBodies.add(AbcMethodBody.read(stream));
    }
    //stream.readBytes(1); // have we read everything?
    return abcFile;
  }
  
  public void write(OutputBitStream stream) throws IOException {
    stream.writeUI16(minorVersion);
    stream.writeUI16(majorVersion);
    constantPool.write(stream);
    stream.writeAbcInt(methods.size());
    for (Iterator<AbcMethodSignature> it = methods.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    stream.writeAbcInt(metadataEntries.size());
    for (Iterator<AbcMetadata> it = metadataEntries.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    stream.writeAbcInt(instances.size());
    for (Iterator<AbcInstance> it = instances.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    for (Iterator<AbcClass> it = classes.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    stream.writeAbcInt(scripts.size());
    for (Iterator<AbcScript> it = scripts.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
    stream.writeAbcInt(methodBodies.size());
    for (Iterator<AbcMethodBody> it = methodBodies.iterator(); it.hasNext(); ) {
      it.next().write(stream);
    }
  }
}
