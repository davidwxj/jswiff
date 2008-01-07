package com.jswiff.swfrecords.abc;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.jswiff.io.InputBitStream;

public class AbcFile implements Serializable {
  private int majorVersion;
  private int minorVersion;
  private AbcConstantPool constantPool;
  List<AbcMethodSignature> methods = new ArrayList<AbcMethodSignature>();
  List<AbcMetadata> metadataEntries = new ArrayList<AbcMetadata>();
  List<AbcInstance> instances = new ArrayList<AbcInstance>();
  List<AbcClass> classes = new ArrayList<AbcClass>();
  List<AbcScript> scripts = new ArrayList<AbcScript>();
  List<AbcMethodBody> methodBodies = new ArrayList<AbcMethodBody>();
  
  public static AbcFile read(InputBitStream stream) throws IOException {
    AbcFile abcFile = new AbcFile();
    abcFile.minorVersion = stream.readUI16();
    abcFile.majorVersion = stream.readUI16();
    if (abcFile.majorVersion != 46 || abcFile.minorVersion > 16) {
      throw new IOException("Unsupported abc format, version > 46.16");
    }
    abcFile.constantPool = AbcConstantPool.read(stream);
    int methodCount = stream.readU30();
    for (int i = 0; i < methodCount; i++) {
      abcFile.methods.add(AbcMethodSignature.read(stream));
    }
    int metadataCount = stream.readU30();
    for (int i = 0; i < metadataCount; i++) {
      abcFile.metadataEntries.add(AbcMetadata.read(stream));
    }
    int classCount = stream.readU30();
    for (int i = 0; i < classCount; i++) {
      abcFile.instances.add(AbcInstance.read(stream));
    }
    for (int i = 0; i < classCount; i++) {
      abcFile.classes.add(AbcClass.read(stream));
    }
    int scriptCount = stream.readU30();
    for (int i = 0; i < scriptCount; i++) {
      abcFile.scripts.add(AbcScript.read(stream));
    }
    int methodBodyCount = stream.readU30();
    for (int i = 0; i < methodBodyCount; i++) {
      abcFile.methodBodies.add(AbcMethodBody.read(stream));
    }
    return abcFile;
  }
}
